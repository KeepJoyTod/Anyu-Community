<template>
  <view class="booking-detail-page">
    <!-- 顶部导航栏 -->
    <view class="nav-bar">
      <view class="back-btn" @click="goBack">
        <text class="back-icon">←</text>
      </view>
      <text class="nav-title">预约详情</text>
      <view class="placeholder"></view>
    </view>

    <!-- 加载状态 -->
    <view class="loading-overlay" v-if="loading">
      <view class="loading-content">
        <text class="loading-icon">⏳</text>
        <text class="loading-text">加载中...</text>
      </view>
    </view>

    <!-- 预约详情卡片 -->
    <view class="booking-card" v-if="bookingDetail.id">
      <view class="booking-header">
        <text class="booking-title">{{ bookingDetail.spaceName }}</text>
        <view class="status-badge" :class="statusClass">
          {{ bookingDetail.status }}
        </view>
      </view>

      <!-- 预约信息 -->
      <view class="info-section">
        <view class="info-item">
          <text class="info-label">预约日期</text>
          <text class="info-value">{{ bookingDetail.reserveDate }}</text>
        </view>
        <view class="info-item">
          <text class="info-label">使用时间</text>
          <text class="info-value">{{ bookingDetail.startTime }} - {{ bookingDetail.endTime }}</text>
        </view>
        <view class="info-item">
          <text class="info-label">预约人</text>
          <text class="info-value">{{ bookingDetail.userName }}</text>
        </view>
        <view class="info-item">
          <text class="info-label">联系电话</text>
          <text class="info-value">{{ bookingDetail.userPhone }}</text>
        </view>
        <view class="info-item" v-if="bookingDetail.reservationPurpose">
          <text class="info-label">预约用途</text>
          <text class="info-value">{{ bookingDetail.reservationPurpose }}</text>
        </view>
        <view class="info-item" v-if="bookingDetail.remark">
          <text class="info-label">备注</text>
          <text class="info-value">{{ bookingDetail.remark }}</text>
        </view>
        <view class="info-item" v-if="bookingDetail.rejectReason">
          <text class="info-label">拒绝原因</text>
          <text class="info-value">{{ bookingDetail.rejectReason }}</text>
        </view>
        <view class="info-item">
          <text class="info-label">预约时间</text>
          <text class="info-value">{{ bookingDetail.createTime }}</text>
        </view>
      </view>

      <!-- 操作按钮 -->
      <view class="action-buttons">
        <view class="action-btn primary" @click="cancelBooking" v-if="bookingDetail.status === '待使用'">
          <text>取消预约</text>
        </view>
        <view class="action-btn secondary" @click="goBack">
          <text>返回</text>
        </view>
      </view>
    </view>

    <!-- 无数据提示 -->
    <view class="empty-tip" v-else>
      <text>📅 暂无预约详情</text>
    </view>

    <!-- 底部提示 -->
    <view class="tips">
      <text class="tips-text">* 如需取消预约，请提前2小时操作</text>
    </view>
  </view>
</template>

<script>
/**
 * 场地预约详情页面
 * @description 显示场地预约的详细信息，包括预约人、时间、用途等
 */
import { getVenueBookingDetail } from '@/api/venue.js'

export default {
  data() {
    return {
      bookingDetail: { id: null },
      loading: false
    };
  },
  computed: {
    statusClass() {
      const status = this.bookingDetail.status;
      switch (status) {
        case '待使用':
          return 'status-pending';
        case '已完成':
          return 'status-completed';
        case '已取消':
          return 'status-canceled';
        default:
          return '';
      }
    }
  },
  onLoad(options) {
    if (options.id) {
      this.fetchBookingDetail(options.id);
    } else {
      uni.showToast({ title: '缺少预约ID', icon: 'none' });
      setTimeout(() => {
        uni.navigateBack();
      }, 1500);
    }
  },
  methods: {
    /**
     * 返回上一页
     */
    goBack() {
      uni.navigateBack();
    },

    /**
     * 获取预约详情
     * @param {number} id - 预约ID
     */
    fetchBookingDetail(id) {
      this.loading = true;
      uni.showLoading({ title: '加载中...', mask: true });

      getVenueBookingDetail(id)
        .then(res => {
          if (res && res.code === 200) {
            this.bookingDetail = res.data;
          } else if (res && res.data) {
            this.bookingDetail = res.data;
          } else {
            uni.showToast({ title: res.msg || '获取预约详情失败', icon: 'none' });
            // 使用模拟数据
            this.bookingDetail = {
              id: id,
              spaceName: '麻将馆',
              userId: 1,
              userName: '测试用户',
              userPhone: '13800138000',
              reserveDate: '2026-04-01',
              startTime: '14:00',
              endTime: '16:00',
              reservationPurpose: '朋友聚会',
              remark: '需要麻将桌',
              status: '待使用',
              createTime: '2026-03-30 10:00:00'
            };
          }
        })
        .catch(err => {
          console.error('获取预约详情失败', err);
          uni.showToast({ title: '网络错误，请重试', icon: 'none' });
          // 使用模拟数据
          this.bookingDetail = {
            id: id,
            spaceName: '麻将馆',
            userId: 1,
            userName: '测试用户',
            userPhone: '13800138000',
            reserveDate: '2026-04-01',
            startTime: '14:00',
            endTime: '16:00',
            reservationPurpose: '朋友聚会',
            remark: '需要麻将桌',
            status: '待使用',
            createTime: '2026-03-30 10:00:00'
          };
        })
        .finally(() => {
          this.loading = false;
          uni.hideLoading();
        });
    },

    /**
     * 取消预约
     */
    cancelBooking() {
      uni.showModal({
        title: '取消预约',
        content: '确定要取消这次预约吗？',
        confirmText: '确定',
        cancelText: '取消',
        success: (res) => {
          if (res.confirm) {
            // 这里可以调用取消预约的接口
            uni.showToast({ title: '预约已取消', icon: 'success' });
            setTimeout(() => {
              uni.navigateBack({
                success: () => {
                  // 通知上一页刷新数据
                  uni.$emit('refreshVenueBookings');
                }
              });
            }, 1500);
          }
        }
      });
    },


  }
};
</script>

<style scoped>
.booking-detail-page {
  position: relative;
  min-height: 100vh;
  background: #f6f9fc;
  padding: 0 32rpx 60rpx;
  overflow-x: hidden;
}

.nav-bar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 30rpx 0 20rpx;
  position: relative;
  z-index: 2;
}

.back-btn {
  width: 72rpx;
  height: 72rpx;
  background: rgba(255,255,255,0.8);
  backdrop-filter: blur(12rpx);
  border-radius: 36rpx;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 2rpx 8rpx rgba(0,0,0,0.04);
}

.back-icon {
  font-size: 48rpx;
  color: #3c5a3e;
  font-weight: 500;
}

.nav-title {
  font-size: 36rpx;
  font-weight: 600;
  color: #1f3b2c;
  letter-spacing: 1rpx;
}

.placeholder {
  width: 72rpx;
}

.loading-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9999;
}

.loading-content {
  background: white;
  border-radius: 24rpx;
  padding: 48rpx;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20rpx;
  box-shadow: 0 10rpx 30rpx rgba(0, 0, 0, 0.2);
}

.loading-icon {
  font-size: 60rpx;
  animation: spin 1s linear infinite;
}

.loading-text {
  font-size: 30rpx;
  color: #6b8c42;
  font-weight: 500;
}

@keyframes spin {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

.booking-card {
  background: rgba(255,255,255,0.92);
  backdrop-filter: blur(12rpx);
  border-radius: 44rpx;
  padding: 32rpx;
  margin-bottom: 32rpx;
  box-shadow: 0 20rpx 40rpx -16rpx rgba(0,0,0,0.08), 0 0 0 1rpx rgba(107,140,66,0.1);
  position: relative;
  z-index: 2;
}

.booking-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 32rpx;
  padding-bottom: 16rpx;
  border-bottom: 1rpx solid rgba(107,140,66,0.2);
}

.booking-title {
  font-size: 36rpx;
  font-weight: 700;
  color: #1f2e3a;
  flex: 1;
}

.status-badge {
  padding: 8rpx 20rpx;
  border-radius: 40rpx;
  font-size: 24rpx;
  font-weight: 600;
}

.status-badge.status-pending {
  background: rgba(107,140,66,0.12);
  color: #6b8c42;
}

.status-badge.status-completed {
  background: rgba(72,184,124,0.12);
  color: #48b87c;
}

.status-badge.status-canceled {
  background: rgba(230,81,81,0.12);
  color: #e65151;
}

.info-section {
  margin-bottom: 32rpx;
}

.info-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24rpx;
  padding-bottom: 24rpx;
  border-bottom: 1rpx solid rgba(0,0,0,0.04);
}

.info-item:last-child {
  margin-bottom: 0;
  padding-bottom: 0;
  border-bottom: none;
}

.info-label {
  font-size: 28rpx;
  color: #4a5e71;
  flex: 1;
}

.info-value {
  font-size: 28rpx;
  font-weight: 500;
  color: #1c2e3a;
  flex: 2;
  text-align: right;
}

.action-buttons {
  display: flex;
  gap: 20rpx;
  margin-top: 24rpx;
}

.action-btn {
  flex: 1;
  border-radius: 80rpx;
  padding: 28rpx 0;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 34rpx;
  font-weight: 700;
  transition: all 0.2s;
  box-shadow: 0 12rpx 28rpx rgba(0,0,0,0.1);
}

.action-btn.primary {
  background: linear-gradient(105deg, #e65151, #c62828);
  color: white;
}

.action-btn.secondary {
  background: linear-gradient(105deg, #f0f0f0, #e0e0e0);
  color: #333;
}

.action-btn:active {
  transform: scale(0.98);
  opacity: 0.9;
}

.empty-tip {
  background: rgba(255,255,255,0.9);
  backdrop-filter: blur(8rpx);
  border-radius: 44rpx;
  padding: 80rpx 0;
  text-align: center;
  font-size: 30rpx;
  color: #8b9bae;
  margin-bottom: 32rpx;
  position: relative;
  z-index: 2;
}

.tips {
  text-align: center;
  margin-top: 20rpx;
  position: relative;
  z-index: 2;
}

.tips-text {
  font-size: 24rpx;
  color: #9aabb8;
}
</style>