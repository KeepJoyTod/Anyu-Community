<template>
  <view class="task-center-container">
    <!-- 头部信息 -->
    <view class="task-header">
      <view class="header-bg">
        <view class="task-title">🎯 赚积分任务中心</view>
        <view class="task-subtitle">做任务，领更多积分</view>
        
        <!-- 签到卡片 -->
        <view class="signin-card" @click="handleSignin">
          <view class="signin-content">
            <view class="signin-left">
              <text class="signin-icon">📅</text>
              <view class="signin-info">
                <text class="signin-title">每日签到</text>
                <text class="signin-desc">{{ consecutiveDays > 0 ? '已连续签到 ' + consecutiveDays + ' 天' : '开始第1天签到' }}</text>
              </view>
            </view>
            <view class="signin-right">
              <button 
                class="signin-btn" 
                :class="{ 'signed': hasSignin }"
                :disabled="hasSignin"
              >
                {{ hasSignin ? '已签到' : '签到' }}
              </button>
              <text class="signin-points">+{{ signinPoints }}积分</text>
            </view>
          </view>
          <!-- 连续签到进度 -->
          <view class="signin-progress">
            <view 
              v-for="day in [1,2,3,4,5,6,7]" 
              :key="day"
              class="progress-item"
              :class="{ 
                'active': day <= consecutiveDays % 7 || (consecutiveDays > 0 && consecutiveDays % 7 === 0 && day === 7),
                'current': day === (consecutiveDays % 7 + 1 > 7 ? 1 : consecutiveDays % 7 + 1) && !hasSignin
              }"
            >
              <text class="progress-day">{{ day }}天</text>
              <text class="progress-reward">+{{ 5 + (day - 1) * 2 }}分</text>
            </view>
          </view>
        </view>
      </view>
    </view>

    <!-- 任务列表 -->
    <view class="task-list">
      <!-- 日常任务 -->
      <view class="task-section">
        <view class="section-header">
          <text class="section-icon">📋</text>
          <text class="section-title">日常任务</text>
          <text class="section-desc">每日刷新，简单好做</text>
        </view>
        
        <view class="task-items">
          <!-- 社区发帖任务 -->
          <view 
            v-for="task in dailyTasks.filter(t => t.taskAction === 'post')" 
            :key="task.taskId"
            class="task-item"
            :class="{ 'completed': task.completed }"
            @click="handlePostTask(task)"
          >
            <view class="task-icon">{{ task.taskIcon }}</view>
            <view class="task-info">
              <text class="task-name">{{ task.taskName }}</text>
              <text class="task-desc">{{ task.taskDesc }}</text>
              <view class="task-progress">
                <text class="progress-text">{{ task.todayCompleteTimes || 0 }}/{{ task.maxTimes }}</text>
                <view class="progress-bar">
                  <view 
                    class="progress-fill" 
                    :style="{ width: ((task.todayCompleteTimes || 0) / task.maxTimes * 100) + '%' }"
                  ></view>
                </view>
              </view>
            </view>
            <view class="task-action">
              <text class="task-points">+{{ task.pointsReward }}积分</text>
              <button 
                class="task-btn" 
                :class="{ 'completed': task.completed }"
                :disabled="task.completed"
                @click.stop="handlePostTask(task)"
              >
                {{ task.completed ? '已完成' : '去完成' }}
              </button>
            </view>
          </view>

          <!-- 发表评论任务 -->
         <!-- <view 
            v-for="task in dailyTasks.filter(t => t.taskAction === 'comment')" 
            :key="task.taskId"
            class="task-item"
            :class="{ 'completed': task.completed }"
            @click="handleCommentTask(task)"
          >
            <view class="task-icon">{{ task.taskIcon }}</view>
            <view class="task-info">
              <text class="task-name">{{ task.taskName }}</text>
              <text class="task-desc">{{ task.taskDesc }}</text>
              <view class="task-progress">
                <text class="progress-text">{{ task.todayCompleteTimes || 0 }}/{{ task.maxTimes }}</text>
                <view class="progress-bar">
                  <view 
                    class="progress-fill" 
                    :style="{ width: ((task.todayCompleteTimes || 0) / task.maxTimes * 100) + '%' }"
                  ></view>
                </view>
              </view>
            </view>
            <view class="task-action">
              <text class="task-points">+{{ task.pointsReward }}积分</text>
              <button 
                class="task-btn" 
                :class="{ 'completed': task.completed }"
                :disabled="task.completed"
                @click.stop="handleCommentTask(task)"
              >
                {{ task.completed ? '已完成' : '去完成' }}
              </button>
            </view>
          </view> -->

          <!-- 点赞互动任务 -->
          <view 
            v-for="task in dailyTasks.filter(t => t.taskAction === 'like')" 
            :key="task.taskId"
            class="task-item"
            :class="{ 'completed': task.completed }"
            @click="handleLikeTask(task)"
          >
            <view class="task-icon">{{ task.taskIcon }}</view>
            <view class="task-info">
              <text class="task-name">{{ task.taskName }}</text>
              <text class="task-desc">{{ task.taskDesc }}</text>
              <view class="task-progress">
                <text class="progress-text">{{ task.todayCompleteTimes || 0 }}/{{ task.maxTimes }}</text>
                <view class="progress-bar">
                  <view 
                    class="progress-fill" 
                    :style="{ width: ((task.todayCompleteTimes || 0) / task.maxTimes * 100) + '%' }"
                  ></view>
                </view>
              </view>
            </view>
            <view class="task-action">
              <text class="task-points">+{{ task.pointsReward }}积分</text>
              <button 
                class="task-btn" 
                :class="{ 'completed': task.completed }"
                :disabled="task.completed"
                @click.stop="handleLikeTask(task)"
              >
                {{ task.completed ? '已完成' : '去完成' }}
              </button>
            </view>
          </view>
        </view>
      </view>

      <!-- 专属任务 -->
      <view class="task-section">
        <view class="section-header">
          <text class="section-icon">⭐</text>
          <text class="section-title">专属任务</text>
          <text class="section-desc">积分丰厚，限时完成</text>
        </view>
        
        <view class="task-items">
          <!-- 满意度调研任务 -->
          <view 
            v-for="task in specialTasks.filter(t => t.taskAction === 'survey')" 
            :key="task.taskId"
            class="task-item"
            :class="{ 'completed': task.completed }"
            @click="handleSurveyTask(task)"
          >
            <view class="task-icon">{{ task.taskIcon }}</view>
            <view class="task-info">
              <text class="task-name">{{ task.taskName }}</text>
              <text class="task-desc">{{ task.taskDesc }}</text>
              <view class="task-progress">
                <text class="progress-text">{{ task.todayCompleteTimes || 0 }}/{{ task.maxTimes }}</text>
                <view class="progress-bar">
                  <view 
                    class="progress-fill" 
                    :style="{ width: ((task.todayCompleteTimes || 0) / task.maxTimes * 100) + '%' }"
                  ></view>
                </view>
              </view>
            </view>
            <view class="task-action">
              <text class="task-points">+{{ task.pointsReward }}积分</text>
              <button 
                class="task-btn" 
                :class="{ 'completed': task.completed }"
                :disabled="task.completed"
                @click.stop="handleSurveyTask(task)"
              >
                {{ task.completed ? '已完成' : '去完成' }}
              </button>
            </view>
          </view>

          <!-- 活动报名任务 -->
          <view 
            v-for="task in specialTasks.filter(t => t.taskAction === 'activity')" 
            :key="task.taskId"
            class="task-item"
            :class="{ 'completed': task.completed }"
            @click="handleActivityTask(task)"
          >
            <view class="task-icon">{{ task.taskIcon }}</view>
            <view class="task-info">
              <text class="task-name">{{ task.taskName }}</text>
              <text class="task-desc">{{ task.taskDesc }}</text>
              <view class="task-progress">
                <text class="progress-text">{{ task.todayCompleteTimes || 0 }}/{{ task.maxTimes }}</text>
                <view class="progress-bar">
                  <view 
                    class="progress-fill" 
                    :style="{ width: ((task.todayCompleteTimes || 0) / task.maxTimes * 100) + '%' }"
                  ></view>
                </view>
              </view>
            </view>
            <view class="task-action">
              <text class="task-points">+{{ task.pointsReward }}积分</text>
              <button 
                class="task-btn" 
                :class="{ 'completed': task.completed }"
                :disabled="task.completed"
                @click.stop="handleActivityTask(task)"
              >
                {{ task.completed ? '已完成' : '去完成' }}
              </button>
            </view>
          </view>

          <!-- 其他专属任务 -->
          <view 
            v-for="task in specialTasks.filter(t => t.taskAction !== 'survey' && t.taskAction !== 'activity')" 
            :key="task.taskId"
            class="task-item"
            :class="{ 'completed': task.completed }"
            @click="handleTask(task)"
          >
            <view class="task-icon">{{ task.taskIcon }}</view>
            <view class="task-info">
              <text class="task-name">{{ task.taskName }}</text>
              <text class="task-desc">{{ task.taskDesc }}</text>
              <view class="task-progress">
                <text class="progress-text">{{ task.todayCompleteTimes || 0 }}/{{ task.maxTimes }}</text>
                <view class="progress-bar">
                  <view 
                    class="progress-fill" 
                    :style="{ width: ((task.todayCompleteTimes || 0) / task.maxTimes * 100) + '%' }"
                  ></view>
                </view>
              </view>
            </view>
            <view class="task-action">
              <text class="task-points">+{{ task.pointsReward }}积分</text>
              <button 
                class="task-btn" 
                :class="{ 'completed': task.completed }"
                :disabled="task.completed"
                @click.stop="completeTask(task)"
              >
                {{ task.completed ? '已完成' : '去完成' }}
              </button>
            </view>
          </view>
        </view>
      </view>
    </view>

    <!-- 积分到账弹窗 -->
    <view class="points-modal" v-if="showPointsModal">
      <view class="points-mask" @click="closePointsModal"></view>
      <view class="points-content">
        <view class="points-icon">🎉</view>
        <view class="points-title">任务完成！</view>
        <view class="points-num">
          <text class="plus">+</text>
          <text class="num">{{ earnedPoints }}</text>
          <text class="unit">积分</text>
        </view>
        <text class="points-desc">已发放至您的账户</text>
        <button class="points-btn" @click="closePointsModal">知道啦</button>
      </view>
    </view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        dailyTasks: [],
        specialTasks: [],
        hasSignin: false,
        consecutiveDays: 0,
        signinPoints: 5,
        showPointsModal: false,
        earnedPoints: 0,
        loading: false
      }
    },
    onLoad() {
      this.loadTasks().catch(() => {})
      this.checkSigninStatus().catch(() => {})
    },
    onShow() {
      // 页面显示时刷新数据
      this.loadTasks().catch(() => {})
      this.checkSigninStatus().catch(() => {})
    },
    methods: {
      // 封装 uni.request 为 Promise
      request(options) {
        return new Promise((resolve, reject) => {
          uni.request({
            timeout: 10000,
            ...options,
            success: (res) => {
              resolve(res)
            },
            fail: (err) => {
              reject(err)
            }
          })
        })
      },

      // 加载任务列表
      async loadTasks() {
        const token = uni.getStorageSync('community_token')
        if (!token) {
          uni.showToast({
            title: '请先登录',
            icon: 'none'
          })
          return
        }

        try {
          const res = await this.request({
            url: 'http://127.0.0.1:8080/community/task/category',
            method: 'GET',
            header: {
              'Authorization': 'Bearer ' + token
            }
          })

          if (res.data.code === 200 && res.data.data) {
            // 过滤掉完善资料任务
            this.dailyTasks = (res.data.data.daily || []).filter(task => task.taskAction !== 'complete_profile')
            // 过滤掉邀请邻居任务
            this.specialTasks = (res.data.data.special || []).filter(task => task.taskAction !== 'invite')
          }
        } catch (e) {
          console.error('加载任务失败:', e)
        }
      },

      // 检查签到状态
      async checkSigninStatus() {
        const token = uni.getStorageSync('community_token')
        if (!token) return

        try {
          const res = await this.request({
            url: 'http://127.0.0.1:8080/community/task/hasSignin',
            method: 'GET',
            header: {
              'Authorization': 'Bearer ' + token
            }
          })

          if (res.data.code === 200 && res.data.data) {
            this.hasSignin = res.data.data.hasSignin
            this.consecutiveDays = res.data.data.consecutiveDays || 0
            // 计算今日签到积分（基于下一次签到的天数）
            const nextDay = this.consecutiveDays + (this.hasSignin ? 0 : 1)
            this.signinPoints = 5 + Math.min((nextDay - 1) * 2, 20)
          }
        } catch (e) {
          console.error('检查签到状态失败:', e)
        }
      },

      // 处理签到
      async handleSignin() {
        if (this.hasSignin) {
          uni.showToast({
            title: '今日已签到',
            icon: 'none'
          })
          return
        }

        const token = uni.getStorageSync('community_token')
        if (!token) {
          uni.showToast({
            title: '请先登录',
            icon: 'none'
          })
          return
        }

        uni.showLoading({ title: '签到中...' })

        try {
          const res = await this.request({
            url: 'http://127.0.0.1:8080/community/task/signin',
            method: 'POST',
            header: {
              'Authorization': 'Bearer ' + token
            }
          })

          if (res.data.code === 200) {
            this.hasSignin = true
            this.consecutiveDays = res.data.data.consecutiveDays || 1
            this.earnedPoints = res.data.data.points || 5
            this.showPointsModal = true
            uni.showToast({
              title: '签到成功',
              icon: 'success'
            })
          } else {
            uni.showToast({
              title: res.data.msg || '签到失败',
              icon: 'none'
            })
          }
        } catch (e) {
          console.error('签到失败:', e)
          uni.showToast({
            title: '签到失败',
            icon: 'none'
          })
        } finally {
          uni.hideLoading()
        }
      },

      // 社区发帖任务 - 跳转到社区页面并打开发表分享弹窗
      handlePostTask(task) {
        if (task.completed) return
		getApp().globalData.pendingCommunityTab = 0
        uni.switchTab({
          url: '/pages/community/community?openPublish=true'
        })
      },

      // 发表评论任务 - 跳转到社区页面
      handleCommentTask(task) {
        if (task.completed) return
		getApp().globalData.pendingCommunityTab = 0
        uni.switchTab({
          url: '/pages/community/community'
        })
      },

      // 点赞互动任务 - 跳转到社区页面
      handleLikeTask(task) {
        if (task.completed) return
        // 设置全局标记：跳转到社区页后选中索引为 0 的标签（日常分享）
        getApp().globalData.pendingCommunityTab = 0
        uni.switchTab({
          url: '/pages/community/community'
        })
      },

      // 活动报名任务 - 跳转到志愿者的活动报名页面
      handleActivityTask(task) {
        if (task.completed) return
        // 设置全局标记：跳转到社区页后选中索引为 1 的标签（志愿者）
        getApp().globalData.pendingCommunityTab = 1
        uni.switchTab({
          url: '/pages/community/community'
        })
      },

      // 满意度调研任务 - 跳转到调研页面
      handleSurveyTask(task) {
        if (task.completed) return
        uni.navigateTo({
          url: '/pages/mall/survey'
        })
      },

      // 完善资料任务 - 跳转到个人资料页面
      handleProfileTask(task) {
        if (task.completed) return
        uni.navigateTo({
          url: '/pages/profile/profile'
        })
      },

      // 邀请邻居任务 - 显示分享菜单
      handleInviteTask(task) {
        if (task.completed) return
        uni.showShareMenu()
      },

      // 处理任务点击
      handleTask(task) {
        if (task.completed) return

        // 根据任务类型调用对应的处理方法
        switch (task.taskAction) {
          case 'post':
            this.handlePostTask(task)
            break
          case 'comment':
            this.handleCommentTask(task)
            break
          case 'like':
            this.handleLikeTask(task)
            break
          case 'activity':
            this.handleActivityTask(task)
            break
          case 'survey':
            this.handleSurveyTask(task)
            break
          case 'complete_profile':
            this.handleProfileTask(task)
            break
          case 'invite':
            this.handleInviteTask(task)
            break
          default:
            break
        }
      },

      // 完成任务并添加积分
      async completeTask(task) {
        if (task.completed) return

        const token = uni.getStorageSync('community_token')
        if (!token) {
          uni.showToast({
            title: '请先登录',
            icon: 'none'
          })
          return
        }

        // 调用完成接口
        uni.showLoading({ title: '提交中...' })

        try {
          const res = await this.request({
            url: `http://127.0.0.1:8080/community/task/complete/${task.taskAction}`,
            method: 'POST',
            header: {
              'Authorization': 'Bearer ' + token
            }
          })

          if (res.data.code === 200) {
            this.earnedPoints = res.data.data.points || task.pointsReward
            this.showPointsModal = true
            // 刷新任务列表
            this.loadTasks()
          } else {
            uni.showToast({
              title: res.data.msg || '任务完成失败',
              icon: 'none'
            })
          }
        } catch (e) {
          console.error('完成任务失败:', e)
          uni.showToast({
            title: '任务完成失败',
            icon: 'none'
          })
        } finally {
          uni.hideLoading()
        }
      },

      // 关闭积分弹窗
      closePointsModal() {
        this.showPointsModal = false
      }
    }
  }
</script>

<style scoped>
  .task-center-container {
    min-height: 100vh;
    background-color: #f5f7fa;
    padding-bottom: 40rpx;
  }

  /* 头部 */
  .task-header {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    padding: 40rpx 30rpx 60rpx;
    border-radius: 0 0 40rpx 40rpx;
  }

  .task-title {
    font-size: 40rpx;
    font-weight: bold;
    color: #fff;
    text-align: center;
    margin-bottom: 10rpx;
  }

  .task-subtitle {
    font-size: 26rpx;
    color: rgba(255, 255, 255, 0.8);
    text-align: center;
    margin-bottom: 40rpx;
  }

  /* 签到卡片 */
  .signin-card {
    background: #fff;
    border-radius: 24rpx;
    padding: 30rpx;
    box-shadow: 0 8rpx 30rpx rgba(0, 0, 0, 0.1);
  }

  .signin-content {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 30rpx;
  }

  .signin-left {
    display: flex;
    align-items: center;
  }

  .signin-icon {
    font-size: 60rpx;
    margin-right: 20rpx;
  }

  .signin-info {
    flex: 1;
  }

  .signin-title {
    display: block;
    font-size: 32rpx;
    font-weight: bold;
    color: #333;
    margin-bottom: 8rpx;
  }

  .signin-desc {
    display: block;
    font-size: 24rpx;
    color: #666;
  }

  .signin-right {
    text-align: center;
  }

  .signin-btn {
    background: linear-gradient(135deg, #ff6b6b 0%, #ee5a5a 100%);
    color: #fff;
    border: none;
    border-radius: 30rpx;
    padding: 16rpx 40rpx;
    font-size: 28rpx;
    font-weight: bold;
    margin-bottom: 10rpx;
  }

  .signin-btn.signed {
    background: #ccc;
  }

  .signin-points {
    display: block;
    font-size: 24rpx;
    color: #ff6b6b;
    font-weight: bold;
  }

  /* 签到进度 */
  .signin-progress {
    display: flex;
    justify-content: space-between;
    padding-top: 20rpx;
    border-top: 2rpx solid #f0f0f0;
  }

  .progress-item {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 10rpx;
    border-radius: 12rpx;
    background: #f5f7fa;
    min-width: 70rpx;
  }

  .progress-item.active {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  }

  .progress-item.current {
    background: linear-gradient(135deg, #ff6b6b 0%, #ee5a5a 100%);
    animation: pulse 1.5s infinite;
  }

  @keyframes pulse {
    0%, 100% {
      transform: scale(1);
    }
    50% {
      transform: scale(1.05);
    }
  }

  .progress-day {
    font-size: 22rpx;
    color: #666;
    margin-bottom: 4rpx;
  }

  .progress-item.active .progress-day,
  .progress-item.current .progress-day {
    color: #fff;
  }

  .progress-reward {
    font-size: 20rpx;
    color: #999;
  }

  .progress-item.active .progress-reward,
  .progress-item.current .progress-reward {
    color: rgba(255, 255, 255, 0.9);
  }

  /* 任务列表 */
  .task-list {
    padding: 30rpx;
    margin-top: -30rpx;
  }

  .task-section {
    background: #fff;
    border-radius: 24rpx;
    padding: 30rpx;
    margin-bottom: 30rpx;
    box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.05);
  }

  .section-header {
    display: flex;
    align-items: center;
    margin-bottom: 30rpx;
    padding-bottom: 20rpx;
    border-bottom: 2rpx solid #f5f7fa;
  }

  .section-icon {
    font-size: 40rpx;
    margin-right: 16rpx;
  }

  .section-title {
    font-size: 32rpx;
    font-weight: bold;
    color: #333;
    flex: 1;
  }

  .section-desc {
    font-size: 24rpx;
    color: #999;
  }

  /* 任务项 */
  .task-items {
    display: flex;
    flex-direction: column;
    gap: 20rpx;
  }

  .task-item {
    display: flex;
    align-items: center;
    padding: 24rpx;
    background: #f8f9fa;
    border-radius: 16rpx;
    transition: all 0.3s;
  }

  .task-item.completed {
    opacity: 0.6;
    background: #f0f0f0;
  }

  .task-item:active {
    transform: scale(0.98);
  }

  .task-icon {
    font-size: 48rpx;
    margin-right: 20rpx;
    width: 80rpx;
    height: 80rpx;
    display: flex;
    align-items: center;
    justify-content: center;
    background: #fff;
    border-radius: 16rpx;
  }

  .task-info {
    flex: 1;
  }

  .task-name {
    display: block;
    font-size: 30rpx;
    font-weight: bold;
    color: #333;
    margin-bottom: 8rpx;
  }

  .task-desc {
    display: block;
    font-size: 24rpx;
    color: #999;
    margin-bottom: 12rpx;
  }

  .task-progress {
    display: flex;
    align-items: center;
  }

  .progress-text {
    font-size: 22rpx;
    color: #667eea;
    margin-right: 12rpx;
    min-width: 60rpx;
  }

  .progress-bar {
    flex: 1;
    height: 8rpx;
    background: #e0e0e0;
    border-radius: 4rpx;
    overflow: hidden;
  }

  .progress-fill {
    height: 100%;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    border-radius: 4rpx;
    transition: width 0.3s;
  }

  .task-action {
    text-align: right;
    margin-left: 20rpx;
  }

  .task-points {
    display: block;
    font-size: 26rpx;
    color: #ff6b6b;
    font-weight: bold;
    margin-bottom: 10rpx;
  }

  .task-btn {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: #fff;
    border: none;
    border-radius: 24rpx;
    padding: 12rpx 30rpx;
    font-size: 24rpx;
    font-weight: bold;
  }

  .task-btn.completed {
    background: #ccc;
  }

  /* 积分弹窗 */
  .points-modal {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: 9999;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .points-mask {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.6);
  }

  .points-content {
    position: relative;
    background: #fff;
    border-radius: 30rpx;
    padding: 60rpx;
    text-align: center;
    animation: scaleIn 0.3s ease-out;
    z-index: 1;
  }

  @keyframes scaleIn {
    0% {
      transform: scale(0.8);
      opacity: 0;
    }
    100% {
      transform: scale(1);
      opacity: 1;
    }
  }

  .points-icon {
    font-size: 100rpx;
    margin-bottom: 20rpx;
  }

  .points-title {
    font-size: 36rpx;
    font-weight: bold;
    color: #333;
    margin-bottom: 30rpx;
  }

  .points-num {
    margin-bottom: 20rpx;
  }

  .plus {
    font-size: 48rpx;
    color: #ff6b6b;
    font-weight: bold;
  }

  .num {
    font-size: 80rpx;
    font-weight: bold;
    background: linear-gradient(135deg, #ff6b6b 0%, #ee5a5a 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    margin: 0 10rpx;
  }

  .unit {
    font-size: 32rpx;
    color: #666;
  }

  .points-desc {
    display: block;
    font-size: 28rpx;
    color: #999;
    margin-bottom: 40rpx;
  }

  .points-btn {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: #fff;
    border: none;
    border-radius: 40rpx;
    padding: 24rpx 80rpx;
    font-size: 32rpx;
    font-weight: bold;
  }
</style>
