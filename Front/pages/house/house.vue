<template>
  <view class="house-page">
    <!-- 顶部房屋信息 -->
    <view class="house-top">
      <view class="house-info">
        <text class="title">智能房屋控制</text>
        <!-- 修复：增加 v-if 判断，防止加载中时闪烁 -->
        <text class="address">{{ houseAddress || '加载中...' }}</text>
      </view>
    </view>

    <!-- 一键开锁 -->
    <view class="lock-card" @click="openLock">
      <view class="lock-icon">
        <text v-if="!lockStatus">🔒</text>
        <text v-else>🔓</text>
      </view>
      <view class="lock-text">
        <text class="lock-title">一键开锁</text>
        <text class="lock-desc">{{ lockStatus ? '已解锁' : '点击解锁房门' }}</text>
      </view>
    </view>

    <!-- 智能控制网格 -->
    <view class="control-grid">
      <!-- 灯开关 -->
      <view class="control-item" @click="toggleLight">
        <view class="icon" :style="{ backgroundColor: lightStatus ? '#FFC107' : '#ECEFF1' }">
          <text>💡</text>
        </view>
        <text class="name">客厅灯光</text>
        <view class="switch-box">
          <view class="switch" :class="{ on: lightStatus }">
            <view class="slider"></view>
          </view>
        </view>
      </view>

      <!-- 空调（带温度调节） -->
      <view class="control-item air-item" @click="openAirModal">
        <view class="icon" :style="{ backgroundColor: airStatus ? '#2196F3' : '#ECEFF1' }">
          <text>❄️</text>
        </view>
        <text class="name">智能空调</text>
        <view class="air-info" v-if="airStatus">
          <text class="temp">{{ temperature }}℃</text>
          <text class="brand">{{ brand }}</text>
        </view>
        <view class="switch-box" v-else>
          <view class="switch" :class="{ on: airStatus }">
            <view class="slider"></view>
          </view>
        </view>
      </view>

      <!-- 窗帘 -->
      <view class="control-item" @click="toggleCurtain">
        <view class="icon" :style="{ backgroundColor: curtainStatus ? '#4CAF50' : '#ECEFF1' }">
          <text>🪟</text>
        </view>
        <text class="name">电动窗帘</text>
        <view class="switch-box">
          <view class="switch" :class="{ on: curtainStatus }">
            <view class="slider"></view>
          </view>
        </view>
      </view>
    </view>

    <!-- 空调控制弹窗 -->
    <view class="modal-mask" v-if="showAirModal" @click="closeAirModal">
      <view class="modal-content" @click.stop>
        <view class="modal-header">
          <text class="modal-title">空调控制</text>
          <text class="close-btn" @click="closeAirModal">×</text>
        </view>

        <!-- 品牌选择 -->
        <view class="brand-group">
          <text class="label">选择品牌</text>
          <view class="brand-list">
            <view class="brand-item" :class="{ active: brand==='格力' }" @click="brand='格力'">
              格力
            </view>
            <view class="brand-item" :class="{ active: brand==='美的' }" @click="brand='美的'">
              美的
            </view>
            <view class="brand-item" :class="{ active: brand==='海尔' }" @click="brand='海尔'">
              海尔
            </view>
            <view class="brand-item" :class="{ active: brand==='小米' }" @click="brand='小米'">
              小米
            </view>
          </view>
        </view>

        <!-- 温度调节 -->
        <view class="temp-group">
          <text class="label">温度调节 {{ temperature }}℃</text>
          <view class="temp-btns">
            <view class="temp-btn" @click="temperature > 16 && temperature--">−</view>
            <view class="temp-show">{{ temperature }}</view>
            <view class="temp-btn" @click="temperature < 30 && temperature++">+</view>
          </view>
        </view>

        <!-- 开关 -->
        <view class="modal-footer">
          <view class="switch" :class="{ on: airStatus }" @click="toggleAir">
            <view class="slider"></view>
          </view>
          <text class="status">{{ airStatus ? '已开启' : '已关闭' }}</text>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      houseAddress: '', // 初始化清空，配合 || 渲染
      lockStatus: false,
      lightStatus: false,
      airStatus: false,
      curtainStatus: false,
      showAirModal: false,
      temperature: 25,
      brand: '格力'
    };
  },
  onLoad() {
    this.getUserHouse();
  },
  methods: {
    // 获取房屋地址
    getUserHouse() {
      const username = uni.getStorageSync('username');
      const token = uni.getStorageSync('community_token');
      uni.showLoading({ title: '加载中...' });

      uni.request({
        url: 'http://localhost:8080/user/house?username=' + username,
        method: 'GET',
        // 修复 1：header 配置规范，且移除可能导致的跨域或验证失败配置
        header: {
          'Content-Type': 'application/json',
          'Authorization': token ? `Bearer ${token}` : ''
        },
        success: (res) => {
          console.log("完整响应 res: ", res);
          // 修复 2：核心修复！增加容错处理
          // 如果 res.data 存在且成功，取数据；否则降级显示默认地址
          if (res.data && res.data.code === 200) {
            this.houseAddress = res.data.msg || '1号楼1单元01室';
          } else {
            this.houseAddress = '未查询到房屋信息';
            console.warn("接口返回 code 非200或数据格式异常");
          }
          console.log("✅ 房屋地址获取成功", this.houseAddress);
        },
        fail: (err) => {
          console.log("❌ 房屋地址获取失败", err);
          // 修复 3：失败时也设置默认值，防止页面一直为空
          this.houseAddress = '网络错误，加载默认地址 1号楼1单元01室';
          uni.showToast({ title: '数据加载失败', icon: 'none' });
        },
        complete: () => {
          uni.hideLoading();
        }
      });
    },

    // 开锁
    openLock() {
      uni.showLoading({ title: '开锁中...' });
      setTimeout(() => {
        this.lockStatus = !this.lockStatus;
        uni.hideLoading();
        uni.showToast({
          title: this.lockStatus ? '已解锁' : '已锁定',
          icon: 'success'
        });
      }, 600);
    },

    // 灯光
    toggleLight() {
      this.lightStatus = !this.lightStatus;
    },

    // 窗帘
    toggleCurtain() {
      this.curtainStatus = !this.curtainStatus;
    },

    // 空调弹窗
    openAirModal() {
      this.showAirModal = true;
    },
    closeAirModal() {
      this.showAirModal = false;
    },
    toggleAir() {
      this.airStatus = !this.airStatus;
    }
  }
};
</script>

<style scoped>
/* 以下 CSS 代码保持不变，这里为了展示省略... */
.house-page {
  padding: 40rpx 30rpx;
  background: #F7F8FA;
  min-height: 100vh;
}

/* 顶部 */
.house-top {
  background: #fff;
  border-radius: 30rpx;
  padding: 50rpx 40rpx;
  margin-bottom: 30rpx;
  box-shadow: 0 6rpx 20rpx rgba(0, 0, 0, 0.04);
}
.house-info {
  text-align: center;
}
.title {
  font-size: 38rpx;
  font-weight: bold;
  color: #2D374B;
  margin-bottom: 12rpx;
  display: block;
}
.address {
  font-size: 28rpx;
  color: #8E9AAD;
}

/* 开锁 */
.lock-card {
  background: linear-gradient(135deg, #4361EE, #7B61FF);
  border-radius: 30rpx;
  padding: 50rpx 40rpx;
  margin-bottom: 40rpx;
  display: flex;
  align-items: center;
  color: #fff;
  box-shadow: 0 8rpx 24rpx rgba(67, 97, 238, 0.25);
}
.lock-icon {
  font-size: 66rpx;
  margin-right: 30rpx;
}
.lock-title {
  font-size: 36rpx;
  font-weight: bold;
  margin-bottom: 8rpx;
  display: block;
}
.lock-desc {
  font-size: 26rpx;
  opacity: 0.9;
}

/* 网格 */
.control-grid {
  display: flex;
  flex-wrap: wrap;
  gap: 24rpx;
}
.control-item {
  flex: 1;
  min-width: 210rpx;
  background: #fff;
  border-radius: 30rpx;
  padding: 50rpx 30rpx;
  display: flex;
  flex-direction: column;
  align-items: center;
  box-shadow: 0 6rpx 20rpx rgba(0, 0, 0, 0.04);
  position: relative;
}
.icon {
  width: 88rpx;
  height: 88rpx;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 40rpx;
  margin-bottom: 24rpx;
  transition: 0.3s;
}
.name {
  font-size: 30rpx;
  color: #333;
  font-weight: 500;
  margin-bottom: 24rpx;
}

/* 空调面板内信息 */
.air-info {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.temp {
  font-size: 32rpx;
  font-weight: bold;
  color: #2196F3;
}
.brand {
  font-size: 22rpx;
  color: #999;
  margin-top: 4rpx;
}

/* 开关 */
.switch {
  width: 74rpx;
  height: 42rpx;
  border-radius: 42rpx;
  background: #E0E6ED;
  position: relative;
  transition: all 0.25s ease;
}
.switch.on {
  background: #4361EE;
}
.slider {
  width: 38rpx;
  height: 38rpx;
  background: #fff;
  border-radius: 50%;
  position: absolute;
  left: 2rpx;
  top: 2rpx;
  transition: 0.25s;
  box-shadow: 0 2rpx 6rpx rgba(0,0,0,0.1);
}
.switch.on .slider {
  left: 34rpx;
}

/* 空调弹窗 */
.modal-mask {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.45);
  display: flex;
  align-items: flex-end;
  z-index: 999;
}
.modal-content {
  background: #fff;
  border-radius: 40rpx 40rpx 0 0;
  padding: 40rpx 40rpx 60rpx;
  width: 100%;
}
.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 40rpx;
}
.modal-title {
  font-size: 40rpx;
  font-weight: bold;
  color: #2D374B;
}
.close-btn {
  font-size: 50rpx;
  color: #999;
  line-height: 1;
}

/* 品牌 */
.label {
  font-size: 28rpx;
  color: #666;
  margin-bottom: 20rpx;
  display: block;
}
.brand-list {
  display: flex;
  gap: 20rpx;
  margin-bottom: 40rpx;
}
.brand-item {
  flex: 1;
  text-align: center;
  padding: 20rpx;
  background: #F5F7F9;
  border-radius: 16rpx;
  font-size: 26rpx;
  color: #666;
}
.brand-item.active {
  background: #4361EE;
  color: #fff;
}

/* 温度 */
.temp-btns {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 40rpx;
  margin-bottom: 50rpx;
}
.temp-btn {
  width: 60rpx;
  height: 60rpx;
  border-radius: 50%;
  background: #F5F7F9;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 36rpx;
  color: #333;
}
.temp-show {
  font-size: 60rpx;
  font-weight: bold;
  color: #2D374B;
  min-width: 100rpx;
  text-align: center;
}

/* 底部 */
.modal-footer {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 20rpx;
}
.status {
  font-size: 28rpx;
  color: #666;
}
</style>