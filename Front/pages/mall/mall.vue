<template>
  <view class="mall-container">
    <!-- 积分信息 -->
    <view class="points-info">
      <view class="points-card">
        <text class="points-label">当前积分</text>
        <text class="points-value">{{ userPoints.availablePoints || 0 }}</text>
        <text class="points-total">总积分: {{ userPoints.totalPoints || 0 }}</text>
      </view>
    </view>

    <!-- 功能导航 -->
    <view class="nav-tabs">
      <view 
        v-for="tab in tabs" 
        :key="tab.key"
        class="nav-tab"
        :class="{ active: activeTab === tab.key }"
        @click="switchTab(tab.key)"
      >
        <text>{{ tab.name }}</text>
      </view>
    </view>

    <!-- 内容区域 -->
    <view class="content-area">
      <!-- 积分兑换 -->
      <view v-if="activeTab === 'exchange'" class="exchange-section">
        <view class="section-title">
          <text>积分兑换</text>
        </view>
        
        <view class="exchange-options">
          <!-- 电费兑换 -->
          <view class="exchange-option">
            <view class="option-icon electricity">⚡</view>
            <view class="option-info">
              <text class="option-title">电费兑换</text>
              <text class="option-desc">100积分 = ¥10.00 电费</text>
            </view>
            <view class="option-actions">
              <button 
                class="exchange-btn"
                :disabled="(userPoints.availablePoints || 0) < 100"
                @click="exchangeElectricity"
              >
                {{ (userPoints.availablePoints || 0) < 100 ? '积分不足' : '立即兑换' }}
              </button>
            </view>
          </view>

          <!-- 水费兑换 -->
          <view class="exchange-option">
            <view class="option-icon water">💧</view>
            <view class="option-info">
              <text class="option-title">水费兑换</text>
              <text class="option-desc">100积分 = ¥10.00 水费</text>
            </view>
            <view class="option-actions">
              <button 
                class="exchange-btn"
                :disabled="(userPoints.availablePoints || 0) < 100"
                @click="exchangeWater"
              >
                {{ (userPoints.availablePoints || 0) < 100 ? '积分不足' : '立即兑换' }}
              </button>
            </view>
          </view>
        </view>
      </view>

      <!-- 兑换记录 -->
      <view v-else-if="activeTab === 'records'" class="records-section">
        <view class="section-title">
          <text>兑换记录</text>
        </view>
        
        <view v-if="loading" class="loading">
          <text>加载中...</text>
        </view>
        
        <view v-else-if="records.length === 0" class="empty">
          <text>暂无兑换记录</text>
        </view>
        
        <view v-else class="records-list">
          <view v-for="record in records" :key="record.id" class="record-item">
            <view class="record-info">
              <text class="record-type">{{ record.billTypeName }}</text>
              <text class="record-amount">¥{{ record.amountOffset }}</text>
            </view>
            <view class="record-details">
              <text class="record-points">使用积分: {{ record.pointsUsed }}</text>
              <text class="record-time">{{ formatDate(record.exchangeTime) }}</text>
            </view>
          </view>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
  import { pointsApi, mallApi } from '../../api/index.js'
  
  export default {
    data() {
      return {
        activeTab: 'exchange',
        tabs: [
          { key: 'exchange', name: '积分兑换' },
          { key: 'records', name: '兑换记录' }
        ],
        userInfo: {},
        userPoints: {},
        records: [],
        loading: false,
        tabs: [
          { key: 'exchange', name: '积分兑换' },
          { key: 'records', name: '兑换记录' }
        ],
        activeTab: 'exchange'
      }
    },
    onLoad() {
      this.userInfo = uni.getStorageSync('userInfo') || {}
      // 不管是否有userId，都尝试加载数据
      // 后端会从当前登录用户中获取userId
      this.loadUserData()
    },
    onShow() {
      this.loadUserData()
    },
    methods: {
      switchTab(key) {
        this.activeTab = key
        if (key === 'records' && this.records.length === 0) {
          this.loadRecords()
        }
      },
      async loadUserData() {
        this.loading = true
        try {
          // 获取用户积分
          const pointsRes = await pointsApi.getPoints()
          if (pointsRes.code === 200) {
            this.userPoints = pointsRes.data
          }
        } catch (error) {
          console.error('加载用户数据失败:', error)
        } finally {
          this.loading = false
        }
      },
      async loadRecords() {
        this.loading = true
        try {
          const res = await mallApi.getExchangeRecordsNoUserId()
          if (res.code === 200) {
            this.records = res.data
          }
        } catch (error) {
          console.error('加载兑换记录失败:', error)
        } finally {
          this.loading = false
        }
      },
      async exchangeElectricity() {
        uni.showModal({
          title: '积分兑换',
          content: '确认使用 100 积分兑换 ¥10.00 电费吗？',
          success: async (res) => {
            if (res.confirm) {
              uni.showLoading({ title: '兑换中...' })
              try {
                const exchangeRes = await mallApi.exchangeNoUserId({
                  billType: 'electricity',
                  billId: 1, // 暂时使用固定值，实际应该从账单列表中获取
                  pointsUsed: 100
                })
                
                if (exchangeRes.code === 200) {
                  uni.showToast({ title: '兑换成功', icon: 'success' })
                  // 重新加载数据
                  this.loadUserData()
                  // 重新加载兑换记录
                  this.loadRecords()
                } else {
                  uni.showToast({ title: exchangeRes.msg || '兑换失败', icon: 'none' })
                }
              } catch (error) {
                const msg = (error && error.msg) ? error.msg : '网络错误，请重试'
                uni.showToast({ title: msg, icon: 'none' })
              } finally {
                uni.hideLoading()
              }
            }
          }
        })
      },
      async exchangeWater() {
        uni.showModal({
          title: '积分兑换',
          content: '确认使用 100 积分兑换 ¥10.00 水费吗？',
          success: async (res) => {
            if (res.confirm) {
              uni.showLoading({ title: '兑换中...' })
              try {
                const exchangeRes = await mallApi.exchangeNoUserId({
                  billType: 'water',
                  billId: 1, // 暂时使用固定值，实际应该从账单列表中获取
                  pointsUsed: 100
                })
                
                if (exchangeRes.code === 200) {
                  uni.showToast({ title: '兑换成功', icon: 'success' })
                  // 重新加载数据
                  this.loadUserData()
                  // 重新加载兑换记录
                  this.loadRecords()
                } else {
                  uni.showToast({ title: exchangeRes.msg || '兑换失败', icon: 'none' })
                }
              } catch (error) {
                const msg = (error && error.msg) ? error.msg : '网络错误，请重试'
                uni.showToast({ title: msg, icon: 'none' })
              } finally {
                uni.hideLoading()
              }
            }
          }
        })
      },
      formatDate(dateStr) {
        if (!dateStr) return ''
        const date = new Date(dateStr)
        return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`
      }
    }
  }
</script>

<style scoped>
.mall-container {
  min-height: 100vh;
  background-color: #f5f7fa;
}

/* 积分信息 */
.points-info {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 40rpx;
  color: white;
}

.points-card {
  text-align: center;
}

.points-label {
  font-size: 28rpx;
  opacity: 0.8;
  display: block;
  margin-bottom: 10rpx;
}

.points-value {
  font-size: 60rpx;
  font-weight: bold;
  display: block;
  margin-bottom: 10rpx;
}

.points-total {
  font-size: 24rpx;
  opacity: 0.8;
}

/* 导航标签 */
.nav-tabs {
  display: flex;
  background-color: white;
  border-bottom: 1rpx solid #e5e5e5;
  margin-bottom: 20rpx;
}

.nav-tab {
  flex: 1;
  padding: 30rpx 0;
  text-align: center;
  font-size: 30rpx;
  color: #666;
  position: relative;
}

.nav-tab.active {
  color: #667eea;
  font-weight: bold;
}

.nav-tab.active::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 25%;
  width: 50%;
  height: 4rpx;
  background-color: #667eea;
}

/* 内容区域 */
.content-area {
  padding: 0 20rpx;
}

.section-title {
  font-size: 32rpx;
  font-weight: bold;
  margin: 30rpx 0;
  color: #333;
}

.loading, .empty {
  text-align: center;
  padding: 100rpx 0;
  color: #999;
  font-size: 28rpx;
}

/* 积分兑换选项 */
.exchange-options {
  display: flex;
  flex-direction: column;
  gap: 20rpx;
  margin-bottom: 20rpx;
}

.exchange-option {
  background-color: white;
  border-radius: 20rpx;
  padding: 30rpx;
  display: flex;
  align-items: center;
  box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.04);
}

.option-icon {
  font-size: 80rpx;
  margin-right: 30rpx;
  width: 120rpx;
  height: 120rpx;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 20rpx;
}

.option-icon.electricity {
  background-color: #e3f2fd;
  color: #1976d2;
}

.option-icon.water {
  background-color: #e1f5fe;
  color: #0288d1;
}

.option-info {
  flex: 1;
}

.option-title {
  font-size: 32rpx;
  font-weight: bold;
  color: #333;
  display: block;
  margin-bottom: 10rpx;
}

.option-desc {
  font-size: 24rpx;
  color: #666;
  display: block;
}

.option-actions {
  text-align: right;
}

.exchange-btn {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  border-radius: 10rpx;
  padding: 15rpx 40rpx;
  font-size: 28rpx;
  font-weight: bold;
}

.exchange-btn:disabled {
  background: #ccc;
  color: #fff;
}

/* 记录列表 */
.records-list {
  background-color: white;
  border-radius: 20rpx;
  overflow: hidden;
  margin-bottom: 20rpx;
}

.record-item {
  padding: 30rpx;
  border-bottom: 1rpx solid #f0f0f0;
}

.record-item:last-child {
  border-bottom: none;
}

.record-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20rpx;
}

.record-type {
  font-size: 30rpx;
  color: #333;
  font-weight: bold;
}

.record-amount {
  font-size: 32rpx;
  color: #4ecdc4;
  font-weight: bold;
}

.record-details {
  display: flex;
  justify-content: space-between;
  font-size: 24rpx;
  color: #666;
}
</style>
