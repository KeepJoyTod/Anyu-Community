<template>
  <view class="setting-page">
    <!-- 头部 -->
    <view class="header">
      <text class="title">系统设置</text>
    </view>
      <view class="menu-item">
        <view class="left">
          <text class="icon">🔔</text>
          <text class="name">消息通知</text>
        </view>
        <switch :checked="notice" color="#409eff" @change="notice = !notice" />
      </view>

      <view class="menu-item">
        <view class="left">
          <text class="icon">📱</text>
          <text class="name">清理缓存</text>
        </view>
        <text class="size">10.2M</text>
        <text class="arrow">></text>
      </view>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      notice: true
    };
  },
  methods: {
    // 修改密码
    editPassword() {
      uni.showToast({
        title: "修改密码",
        icon: "none"
      });
    },
  }
};
</script>

<style scoped>
.setting-page {
  background-color: #f5f7fa;
  min-height: 100vh;
}

/* 头部 */
.header {
  background-color: #fff;
  padding: 30rpx;
  text-align: center;
  border-bottom: 1rpx solid #eee;
}

.title {
  font-size: 34rpx;
  font-weight: bold;
}

/* 菜单 */
.menu-list {
  background-color: #fff;
  margin-top: 30rpx;
  border-radius: 20rpx;
  margin: 30rpx;
  overflow: hidden;
}

.menu-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 30rpx;
  border-bottom: 1rpx solid #f0f0f0;
}

.menu-item:last-child {
  border-bottom: none;
}

.left {
  display: flex;
  align-items: center;
}

.icon {
  font-size: 36rpx;
  margin-right: 20rpx;
}

.name {
  font-size: 30rpx;
  color: #333;
}

.arrow {
  font-size: 32rpx;
  color: #999;
}

.size {
  font-size: 26rpx;
  color: #999;
  margin-right: 10rpx;
}

.red {
  color: #ff4444 !important;
}
</style>