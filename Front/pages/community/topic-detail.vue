<template>
  <view class="topic-detail">
    <image class="activity-img" :src="activity.imageUrl" mode="widthFix" @click="previewImage"></image>
    
    <view class="header">
      <text class="header-title">{{ activity.title }}</text>
    </view>

    <view class="info-card">
      <view class="info-item">
        <text class="info-label">🕒 活动时间</text>
        <text class="info-value">{{ activity.time }}</text>
      </view>
      <view class="info-item">
        <text class="info-label">📍 活动地点</text>
        <text class="info-value">{{ activity.address }}</text>
      </view>
      <view class="info-item">
        <text class="info-label">👥 招募人数</text>
        <text class="info-value">{{ activity.count }}人</text>
      </view>
      <view class="info-item">
        <text class="info-label">📞 联系人员</text>
        <text class="info-value">{{ activity.contact }}</text>
      </view>
    </view>

    <view class="content-card">
      <text class="content-title">📝 活动详情</text>
      <view class="content-text">{{ activity.content }}</view>
    </view>

    <view class="register-wrap">
      <button class="register-btn" @click="toggleRegister" :class="{active: hasRegistered}">
        {{ hasRegistered ? '取消报名' : '立即报名' }}
      </button>
    </view>

    <!-- 精美积分奖励弹窗 -->
    <view class="points-toast" v-if="showPointsToast">
      <view class="points-toast-mask" @click="showPointsToast = false"></view>
      <view class="points-toast-box">
        <!-- 顶部装饰 -->
        <view class="points-toast-top"></view>
        <!-- 积分图标 -->
        <view class="points-icon">
          <text class="icon">🎁</text>
        </view>
        <!-- 数字动画 -->
        <view class="points-number">
          <text class="plus">+</text>
          <text class="num">{{ pointsNum }}</text>
          <text class="unit">积分</text>
        </view>
        <!-- 提示文字 -->
        <view class="points-desc">
          <text class="title">报名成功！</text>
          <text class="sub-title">积分已发放至您的账户</text>
        </view>
        <!-- 底部按钮 -->
        <button class="points-close-btn" @click="showPointsToast = false">
          知道啦
        </button>
      </view>
    </view>

    <!-- 积分扣除弹窗 -->
    <view class="points-toast" v-if="showDeductToast">
      <view class="points-toast-mask" @click="showDeductToast = false"></view>
      <view class="points-toast-box deduct-box">
        <!-- 顶部装饰 -->
        <view class="points-toast-top deduct-top"></view>
        <!-- 积分图标 -->
        <view class="points-icon">
          <text class="icon">💔</text>
        </view>
        <!-- 数字动画 -->
        <view class="points-number">
          <text class="plus deduct-plus">-</text>
          <text class="num deduct-num">{{ pointsNum }}</text>
          <text class="unit">积分</text>
        </view>
        <!-- 提示文字 -->
        <view class="points-desc">
          <text class="title">取消报名！</text>
          <text class="sub-title">积分已从您的账户扣除</text>
        </view>
        <!-- 底部按钮 -->
        <button class="points-close-btn deduct-btn" @click="showDeductToast = false">
          知道啦
        </button>
      </view>
    </view>
  </view>
</template>

<script src="../../api/topic-detail.js"></script>

<style scoped>
.topic-detail {
  background: #f2f5f9;
  min-height: 100vh;
  padding-bottom: 140rpx;
}

.activity-img {
  width: 100%;
  border-radius: 0 0 30rpx 30rpx;
  box-shadow: 0 10rpx 30rpx rgba(0, 0, 0, 0.08);
}

.header {
  background: linear-gradient(135deg, #4b79f4, #6fa3f7);
  color: #fff;
  padding: 40rpx 30rpx;
  margin-bottom: 30rpx;
  border-radius: 0 0 30rpx 30rpx;
  box-shadow: 0 10rpx 30rpx rgba(75, 121, 244, 0.15);
}

.header-title {
  font-size: 40rpx;
  font-weight: bold;
  line-height: 1.4;
}

.info-card {
  background: #fff;
  border-radius: 24rpx;
  padding: 30rpx;
  margin: 0 30rpx 30rpx;
  box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.04);
}

.info-item {
  display: flex;
  padding: 24rpx 0;
  border-bottom: 1rpx solid #f5f7fa;
}

.info-item:last-child {
  border-bottom: none;
}

.info-label {
  font-size: 28rpx;
  color: #666;
  width: 180rpx;
}

.info-value {
  font-size: 28rpx;
  color: #222;
  font-weight: 500;
}

.content-card {
  background: #fff;
  border-radius: 24rpx;
  padding: 40rpx 30rpx;
  margin: 0 30rpx 30rpx;
  box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.04);
}

.content-title {
  font-size: 32rpx;
  font-weight: bold;
  color: #222;
  margin-bottom: 20rpx;
  display: block;
}

.content-text {
  font-size: 28rpx;
  line-height: 1.7;
  color: #555;
}

.register-wrap {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  padding: 30rpx;
  background: #fff;
  box-shadow: 0 -6rpx 25rpx rgba(0, 0, 0, 0.06);
}

.register-btn {
  width: 100%;
  height: 96rpx;
  border-radius: 50rpx;
  background: linear-gradient(135deg, #4b79f4, #6fa3f7);
  color: #fff;
  font-size: 32rpx;
  font-weight: bold;
  border: none;
  box-shadow: 0 10rpx 25rpx rgba(75, 121, 244, 0.25);
}

.register-btn.active {
  background: linear-gradient(135deg, #ff6b9d, #ff8da8);
  box-shadow: 0 10rpx 25rpx rgba(255, 107, 157, 0.25);
}

/* 积分奖励弹窗样式 */
.points-toast {
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

.points-toast-mask {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  backdrop-filter: blur(8rpx);
}

.points-toast-box {
  position: relative;
  width: 600rpx;
  background: #fff;
  border-radius: 40rpx;
  padding: 60rpx 40rpx 40rpx;
  box-shadow: 0 20rpx 60rpx rgba(0, 0, 0, 0.15);
  text-align: center;
  animation: toastUp 0.5s ease-out;
}

@keyframes toastUp {
  0% {
    opacity: 0;
    transform: scale(0.8) translateY(50rpx);
  }
  100% {
    opacity: 1;
    transform: scale(1) translateY(0);
  }
}

.points-toast-top {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 40rpx;
  background: linear-gradient(135deg, #4b79f4, #6fa3f7);
  border-radius: 40rpx 40rpx 0 0;
}

.points-icon {
  margin-bottom: 30rpx;
}

.points-icon .icon {
  font-size: 100rpx;
  display: inline-block;
  animation: iconBounce 2s infinite;
}

@keyframes iconBounce {
  0%, 100% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.2);
  }
}

.points-number {
  margin-bottom: 20rpx;
}

.points-number .plus {
  font-size: 60rpx;
  color: #ff6b9d;
  font-weight: bold;
  margin-right: 10rpx;
}

.points-number .num {
  font-size: 90rpx;
  color: #4b79f4;
  font-weight: bold;
  background: linear-gradient(135deg, #4b79f4, #6fa3f7);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

.points-number .unit {
  font-size: 40rpx;
  color: #666;
  margin-left: 10rpx;
}

.points-desc {
  margin-bottom: 50rpx;
}

.points-desc .title {
  font-size: 36rpx;
  font-weight: bold;
  color: #222;
  display: block;
  margin-bottom: 10rpx;
}

.points-desc .sub-title {
  font-size: 28rpx;
  color: #666;
}

.points-close-btn {
  width: 80%;
  height: 80rpx;
  background: linear-gradient(135deg, #4b79f4, #6fa3f7);
  color: #fff;
  border: none;
  border-radius: 40rpx;
  font-size: 32rpx;
  box-shadow: 0 8rpx 20rpx rgba(75, 121, 244, 0.2);
}

.points-close-btn:active {
  transform: scale(0.98);
}

/* 积分扣除弹窗样式 */
.deduct-top {
  background: linear-gradient(135deg, #ff6b6b, #ee5a5a) !important;
}

.deduct-plus {
  color: #ff6b6b !important;
}

.deduct-num {
  background: linear-gradient(135deg, #ff6b6b, #ee5a5a) !important;
  -webkit-background-clip: text !important;
  -webkit-text-fill-color: transparent !important;
}

.deduct-btn {
  background: linear-gradient(135deg, #ff6b6b, #ee5a5a) !important;
  box-shadow: 0 8rpx 20rpx rgba(255, 107, 107, 0.2) !important;
}
</style>