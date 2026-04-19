<template>
  <view class="donate-detail">
    <!-- 顶部封面图 -->
    <image class="cover-img" :src="getDonateImage()" mode="aspectFill" @error="imageError = true"></image>
    
    <!-- 内容区域 -->
    <view class="content-box">
      <view class="title">{{ donateInfo.title }}</view>
      
      <!-- 进度条 -->
      <view class="progress-wrap">
        <view class="progress-bar">
          <view class="progress-active" :style="{width: (donateInfo.nowMoney / donateInfo.targetMoney * 100) + '%'}"></view>
        </view>
        <view class="money-row">
          <text>已筹：{{ donateInfo.nowMoney }} 元</text>
          <text>目标：{{ donateInfo.targetMoney }} 元</text>
        </view>
      </view>
      
      <!-- 项目描述 -->
      <view class="desc">
        <text>{{ donateInfo.content || '暂无项目介绍' }}</text>
      </view>

      <!-- 项目信息栏 -->
      <view class="info-group">
        <view class="info-item">
          <text class="label">项目类型</text>
          <text class="value">公益捐赠</text>
        </view>
        <view class="info-item">
          <text class="label">发起方</text>
          <text class="value">社区居委会</text>
        </view>
        <view class="info-item">
          <text class="label">捐赠人数</text>
          <text class="value">{{ donateInfo.donorCount || 0 }} 人</text>
        </view>
      </view>
    </view>

    <!-- 捐赠记录 -->
    <view class="record-box">
      <view class="record-title">爱心捐赠记录</view>
      <view class="record-item" v-for="(item, idx) in donateRecords" :key="idx">
        <view class="record-user">
          <text class="name">{{ item.name }}</text>
          <text class="time">{{ item.time }}</text>
        </view>
        <text class="amount">+{{ item.amount }} 元</text>
      </view>
    </view>
    
    <!-- 底部捐赠按钮 -->
    <view class="footer-bar">
      <view class="donate-btn" @click="showDonateModal = true">立即捐赠</view>
    </view>

    <!-- 捐赠弹窗 -->
    <view class="donate-mask" v-if="showDonateModal" @click="showDonateModal = false">
      <view class="donate-modal" @click.stop>
        <view class="modal-header">
          <text class="modal-title">爱心捐赠</text>
          <view class="close-btn" @click="showDonateModal = false">×</view>
        </view>
        
        <!-- 捐款金额选择 -->
        <view class="amount-section">
          <text class="section-title">捐款金额</text>
          <view class="amount-options">
            <view 
              v-for="amount in amountOptions" 
              :key="amount"
              class="amount-item"
              :class="{ active: selectedAmount === amount }"
              @click="selectedAmount = amount; customAmount = ''"
            >
              {{ amount }}元
            </view>
          </view>
          <input 
            type="number" 
            v-model="customAmount" 
            placeholder="自定义金额"
            @input="selectedAmount = customAmount"
            class="custom-amount"
          />
        </view>
        
        <!-- 捐款人信息 -->
        <view class="info-section">
          <text class="section-title">捐款人信息</text>
          <input 
            type="text" 
            v-model="donorName" 
            placeholder="姓名（选填）"
            class="info-input"
          />
          <input 
            type="text" 
            v-model="donorPhone" 
            placeholder="手机号（选填）"
            class="info-input"
          />
          <textarea 
            v-model="donorMessage" 
            placeholder="留言（选填）"
            class="message-input"
          ></textarea>
        </view>
        
        <!-- 支付方式选择 -->
        <view class="pay-section">
          <text class="section-title">支付方式</text>
          <view class="pay-options">
            <view 
              class="pay-item"
              :class="{ active: payType === 'wechat' }"
              @click="payType = 'wechat'"
            >
              <image src="/static/weixin.jpg" class="pay-icon"></image>
              <text>微信支付</text>
              <view class="radio" :class="{ active: payType === 'wechat' }"></view>
            </view>
            <view 
              class="pay-item"
              :class="{ active: payType === 'alipay' }"
              @click="payType = 'alipay'"
            >
              <image src="/static/支付宝.jpg" class="pay-icon"></image>
              <text>支付宝</text>
              <view class="radio" :class="{ active: payType === 'alipay' }"></view>
            </view>
          </view>
        </view>
        
        <!-- 确认捐款按钮 -->
        <view class="confirm-btn" @click="submitDonation">
          确认捐款 {{ selectedAmount }} 元
        </view>
      </view>
    </view>

    <!-- 支付结果弹窗 -->
    <view class="result-mask" v-if="showResultModal" @click="showResultModal = false">
      <view class="result-modal" @click.stop>
        <view class="result-icon" :class="paySuccess ? 'success' : 'fail'">{{ paySuccess ? '✓' : '✗' }}</view>
        <view class="result-title">{{ paySuccess ? '捐款成功' : '捐款失败' }}</view>
        <view class="result-desc">{{ paySuccess ? '感谢您的爱心捐赠' : '请稍后重试' }}</view>
        <view class="result-btn" @click="closeResultModal">
          {{ paySuccess ? '确定' : '重试' }}
        </view>
      </view>
    </view>

  </view>
</template>

<script>
export default {
  data() {
    return {
      donateInfo: {
        nowMoney: 0,
        targetMoney: 10000,
        donorCount: 0,
        title: '爱心捐款',
        content: '为需要帮助的人贡献一份力量',
        image: '/static/logo.png'
      },
      imageError: false,
      showDonateModal: false,
      showResultModal: false,
      paySuccess: false,
      selectedAmount: 50,
      customAmount: '',
      amountOptions: [10, 20, 50, 100, 200, 500],
      donorName: '',
      donorPhone: '',
      donorMessage: '',
      payType: 'wechat',
      donateRecords: [
        { name: "李**", time: "2026-03-28 10:23", amount: 50 },
        { name: "王**", time: "2026-03-28 09:15", amount: 100 },
        { name: "张**", time: "2026-03-27 20:02", amount: 20 },
        { name: "刘**", time: "2026-03-27 18:45", amount: 200 }
      ]
    }
  },
  onLoad(options) {
    if (options.donate) {
      try {
        this.donateInfo = JSON.parse(decodeURIComponent(options.donate));
      } catch (e) {
        uni.showToast({ title: "数据加载失败", icon: "none" });
      }
    }
  },
  methods: {
    submitDonation() {
      console.log('=== 开始处理捐款 ===');
      console.log('selectedAmount:', this.selectedAmount);
      console.log('donorName:', this.donorName);
      console.log('donorPhone:', this.donorPhone);
      console.log('donorMessage:', this.donorMessage);
      console.log('payType:', this.payType);
      console.log('donateInfo:', this.donateInfo);
      
      // 验证金额
      if (!this.selectedAmount || parseFloat(this.selectedAmount) <= 0) {
        console.log('金额验证失败');
        uni.showToast({ title: "请输入有效的捐款金额", icon: "none" });
        return;
      }
      
      console.log('金额验证通过，准备发送请求');
      
      // 显示加载状态
      uni.showLoading({ title: '处理中...' });
      
      console.log('开始发送请求到后端API');
      
      // 调用后端API
      uni.request({
        url: 'http://localhost:8080/community/donation/create',
        method: 'POST',
        header: {
          'Content-Type': 'application/json'
        },
        data: {
          donationId: this.donateInfo.id || 1,
          amount: this.selectedAmount.toString(),
          donorName: this.donorName,
          donorPhone: this.donorPhone,
          message: this.donorMessage,
          payType: this.payType
        },
        success: (res) => {
          uni.hideLoading();
          console.log('后端响应:', res);
          if (res.data.code === 200) {
            // 捐赠成功
            this.paySuccess = true;
            this.showResultModal = true;
            
            // 更新本地数据
            this.donateInfo.nowMoney = (parseFloat(this.donateInfo.nowMoney) + parseFloat(this.selectedAmount)).toFixed(2);
            this.donateInfo.donorCount = (parseInt(this.donateInfo.donorCount || 0) + 1).toString();
            
            // 添加新的捐赠记录
            this.donateRecords.unshift({
              name: this.donorName || '匿名',
              time: new Date().toISOString().substring(0, 16).replace('T', ' '),
              amount: parseFloat(this.selectedAmount)
            });
          } else {
            // 捐赠失败
            this.paySuccess = false;
            this.showResultModal = true;
          }
        },
        fail: (err) => {
          uni.hideLoading();
          console.error('请求失败:', err);
          uni.showToast({ title: "网络错误，请稍后重试", icon: "none" });
        },
        complete: () => {
          console.log('请求完成');
        }
      });
    },
    closeResultModal() {
      this.showResultModal = false;
      if (this.paySuccess) {
        // 关闭捐赠弹窗
        this.showDonateModal = false;
        // 重置表单
        this.selectedAmount = 50;
        this.customAmount = '';
        this.donorName = '';
        this.donorPhone = '';
        this.donorMessage = '';
        this.payType = 'wechat';
        
        // 显示成功提示
        uni.showToast({ title: "捐款成功，感谢您的爱心！", icon: "success" });
      }
    },
    getDonateImage() {
      if (this.imageError || !this.donateInfo.image) {
        // 随机返回一张默认图片
        const fallbacks = [
          '/static/help.jpg',
          '/static/animal.jpg',
          '/static/study.jpg',
          '/static/health.jpg',
          '/static/environment.jpg'
        ];
        const randomIndex = Math.floor(Math.random() * fallbacks.length);
        return fallbacks[randomIndex];
      }
      if (this.donateInfo.image.startsWith('http')) {
        return this.donateInfo.image;
      }
      return 'http://127.0.0.1:8080' + this.donateInfo.image;
    }
  }
}
</script>

<style scoped>
.donate-detail {
  background: #f5f7fa;
  min-height: 100vh;
  padding-bottom: 120rpx;
}
.cover-img {
  width: 100%;
  height: 400rpx;
  object-fit: cover;
  border-radius: 0 0 24rpx 24rpx;
  box-shadow: 0 8rpx 24rpx rgba(0, 0, 0, 0.1);
}
.content-box {
  background: #fff;
  margin: 20rpx;
  border-radius: 20rpx;
  padding: 30rpx;
  box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.08);
  position: relative;
  top: -40rpx;
  z-index: 10;
}
.title {
  font-size: 36rpx;
  font-weight: bold;
  color: #333;
  margin-bottom: 24rpx;
  line-height: 1.3;
}
.progress-wrap {
  margin-bottom: 30rpx;
}
.progress-bar {
  width: 100%;
  height: 12rpx;
  background: #f8f9fa;
  border-radius: 6rpx;
  overflow: hidden;
  margin-bottom: 12rpx;
  box-shadow: inset 0 2rpx 4rpx rgba(0, 0, 0, 0.05);
  position: relative;
}
.progress-bar::after {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.6), transparent);
  animation: progressShine 2s infinite linear;
  z-index: 2;
}
.progress-active {
  height: 100%;
  background: linear-gradient(90deg, #ff6b6b, #ff8787);
  border-radius: 6rpx;
  transition: width 0.8s ease;
  box-shadow: 0 2rpx 4rpx rgba(255, 107, 107, 0.3);
  position: relative;
}
@keyframes progressShine {
  0% {
    left: -100%;
  }
  100% {
    left: 100%;
  }
}
.money-row {
  display: flex;
  justify-content: space-between;
  font-size: 24rpx;
  color: #666;
  font-weight: 500;
}
.desc {
  font-size: 28rpx;
  color: #666;
  line-height: 1.6;
  margin-bottom: 30rpx;
  padding: 20rpx;
  background: #f8f9fa;
  border-radius: 12rpx;
  border-left: 4rpx solid #ff6b6b;
}
.info-group {
  border-top: 1rpx solid #f0f0f0;
  padding-top: 20rpx;
}
.info-item {
  display: flex;
  justify-content: space-between;
  padding: 16rpx 0;
  font-size: 26rpx;
  border-bottom: 1rpx solid #f8f9fa;
}
.info-item:last-child {
  border-bottom: none;
}
.info-item .label {
  color: #999;
  font-weight: 500;
}
.info-item .value {
  color: #333;
  font-weight: 600;
}
.record-box {
  background: #fff;
  margin: 0 20rpx 20rpx;
  border-radius: 20rpx;
  padding: 30rpx;
  box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.08);
}
.record-title {
  font-size: 30rpx;
  font-weight: 600;
  color: #333;
  margin-bottom: 20rpx;
  display: flex;
  align-items: center;
}
.record-title::before {
  content: '❤️';
  margin-right: 12rpx;
  font-size: 28rpx;
}
.record-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16rpx 0;
  border-bottom: 1rpx solid #f5f5f5;
  transition: all 0.3s ease;
}
.record-item:hover {
  background: #f8f9fa;
  padding-left: 16rpx;
  border-radius: 8rpx;
}
.record-item:last-child {
  border-bottom: none;
}
.record-user {
  display: flex;
  flex-direction: column;
  gap: 4rpx;
}
.record-user .name {
  font-size: 26rpx;
  color: #333;
  font-weight: 500;
}
.record-user .time {
  font-size: 20rpx;
  color: #999;
}
.record-item .amount {
  font-size: 28rpx;
  color: #ff6b6b;
  font-weight: 600;
  background: rgba(255, 107, 107, 0.1);
  padding: 8rpx 16rpx;
  border-radius: 16rpx;
}
.footer-bar {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  background: #fff;
  padding: 20rpx 30rpx;
  box-shadow: 0 -4rpx 20rpx rgba(0,0,0,0.1);
  z-index: 999;
  border-top: 1rpx solid #f0f0f0;
}
.donate-btn {
  background: linear-gradient(135deg, #ff6b6b, #ff8787);
  color: #fff;
  font-size: 32rpx;
  text-align: center;
  padding: 24rpx;
  border-radius: 16rpx;
  font-weight: 600;
  box-shadow: 0 4rpx 16rpx rgba(255, 107, 107, 0.3);
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
}
.donate-btn::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.3), transparent);
  transition: left 0.6s ease;
}
.donate-btn:hover::before {
  left: 100%;
}
.donate-btn:active {
  transform: translateY(2rpx);
  box-shadow: 0 2rpx 8rpx rgba(255, 107, 107, 0.4);
}

/* 捐赠弹窗样式 */
.donate-mask {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0,0,0,0.6);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9999;
  animation: fadeIn 0.3s ease;
}
@keyframes fadeIn {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}
.donate-modal {
  background: #fff;
  width: 90%;
  max-height: 80vh;
  border-radius: 24rpx;
  padding: 30rpx;
  overflow-y: auto;
  animation: bounceIn 0.4s ease;
  box-shadow: 0 12rpx 40rpx rgba(0, 0, 0, 0.2);
}
@keyframes bounceIn {
  from {
    transform: scale(0.8);
    opacity: 0;
  }
  to {
    transform: scale(1);
    opacity: 1;
  }
}
.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30rpx;
  padding-bottom: 20rpx;
  border-bottom: 1rpx solid #f0f0f0;
}
.modal-title {
  font-size: 32rpx;
  font-weight: bold;
  color: #333;
  display: flex;
  align-items: center;
}
.modal-title::before {
  content: '❤️';
  margin-right: 12rpx;
  font-size: 28rpx;
}
.close-btn {
  font-size: 40rpx;
  color: #999;
  line-height: 1;
  transition: all 0.3s ease;
  padding: 8rpx;
  border-radius: 50%;
}
.close-btn:hover {
  background: #f0f0f0;
  color: #666;
}
.section-title {
  font-size: 28rpx;
  font-weight: 600;
  color: #333;
  margin-bottom: 20rpx;
  display: block;
}
.amount-options {
  display: flex;
  flex-wrap: wrap;
  margin-bottom: 20rpx;
  gap: 16rpx;
}
.amount-item {
  width: calc(33.333% - 12rpx);
  padding: 24rpx 20rpx;
  background: #f8f9fa;
  border-radius: 12rpx;
  text-align: center;
  font-size: 26rpx;
  color: #333;
  font-weight: 500;
  transition: all 0.3s ease;
  border: 2rpx solid transparent;
}
.amount-item:hover {
  background: #f0f0f0;
  transform: translateY(-2rpx);
}
.amount-item.active {
  background: linear-gradient(135deg, #ff6b6b, #ff8787);
  color: #fff;
  border-color: #ff6b6b;
  box-shadow: 0 4rpx 12rpx rgba(255, 107, 107, 0.3);
}
.custom-amount {
  width: 100%;
  padding: 24rpx;
  border: 2rpx solid #f0f0f0;
  border-radius: 12rpx;
  font-size: 26rpx;
  margin-bottom: 30rpx;
  transition: all 0.3s ease;
}
.custom-amount:focus {
  border-color: #ff6b6b;
  box-shadow: 0 0 0 4rpx rgba(255, 107, 107, 0.1);
  outline: none;
}
.info-section {
  margin-bottom: 30rpx;
}
.info-input {
  width: 100%;
  padding: 24rpx;
  border: 2rpx solid #f0f0f0;
  border-radius: 12rpx;
  font-size: 26rpx;
  margin-bottom: 16rpx;
  transition: all 0.3s ease;
}
.info-input:focus {
  border-color: #ff6b6b;
  box-shadow: 0 0 0 4rpx rgba(255, 107, 107, 0.1);
  outline: none;
}
.message-input {
  width: 100%;
  padding: 24rpx;
  border: 2rpx solid #f0f0f0;
  border-radius: 12rpx;
  font-size: 26rpx;
  min-height: 140rpx;
  margin-bottom: 30rpx;
  transition: all 0.3s ease;
  resize: none;
}
.message-input:focus {
  border-color: #ff6b6b;
  box-shadow: 0 0 0 4rpx rgba(255, 107, 107, 0.1);
  outline: none;
}
.pay-section {
  margin-bottom: 30rpx;
}
.pay-options {
  display: flex;
  flex-direction: column;
  gap: 16rpx;
}
.pay-item {
  display: flex;
  align-items: center;
  padding: 24rpx;
  border: 2rpx solid #f0f0f0;
  border-radius: 12rpx;
  transition: all 0.3s ease;
  background: #fff;
}
.pay-item:hover {
  border-color: #ff6b6b;
  background: rgba(255, 107, 107, 0.02);
}
.pay-item.active {
  border-color: #ff6b6b;
  background: rgba(255, 107, 107, 0.05);
  box-shadow: 0 4rpx 12rpx rgba(255, 107, 107, 0.1);
}
.pay-icon {
  width: 48rpx;
  height: 48rpx;
  margin-right: 20rpx;
  border-radius: 8rpx;
}
.pay-item text {
  flex: 1;
  font-size: 26rpx;
  color: #333;
  font-weight: 500;
}
.radio {
  width: 32rpx;
  height: 32rpx;
  border: 2rpx solid #999;
  border-radius: 50%;
  position: relative;
  transition: all 0.3s ease;
}
.radio.active {
  border-color: #ff6b6b;
  background: #ff6b6b;
}
.radio.active::after {
  content: '';
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 16rpx;
  height: 16rpx;
  background: #fff;
  border-radius: 50%;
  animation: radioPulse 0.3s ease;
}
@keyframes radioPulse {
  from {
    transform: translate(-50%, -50%) scale(0);
    opacity: 0;
  }
  to {
    transform: translate(-50%, -50%) scale(1);
    opacity: 1;
  }
}
.confirm-btn {
  background: linear-gradient(135deg, #ff6b6b, #ff8787);
  color: #fff;
  font-size: 32rpx;
  text-align: center;
  padding: 24rpx;
  border-radius: 16rpx;
  margin-top: 20rpx;
  font-weight: 600;
  box-shadow: 0 4rpx 16rpx rgba(255, 107, 107, 0.3);
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
}
.confirm-btn::before {
  content: '';
  position: absolute;
  top: 0;
  left: -100%;
  width: 100%;
  height: 100%;
  background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.3), transparent);
  transition: left 0.6s ease;
}
.confirm-btn:hover::before {
  left: 100%;
}
.confirm-btn:active {
  transform: translateY(2rpx);
  box-shadow: 0 2rpx 8rpx rgba(255, 107, 107, 0.4);
}

/* 支付结果弹窗样式 */
.result-mask {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0,0,0,0.6);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 9999;
  animation: fadeIn 0.3s ease;
}
.result-modal {
  background: #fff;
  width: 70%;
  border-radius: 24rpx;
  padding: 40rpx;
  text-align: center;
  animation: bounceIn 0.4s ease;
  box-shadow: 0 12rpx 40rpx rgba(0, 0, 0, 0.2);
}
.result-icon {
  width: 120rpx;
  height: 120rpx;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 72rpx;
  font-weight: bold;
  margin: 0 auto 24rpx;
  animation: scaleIn 0.5s ease;
}
@keyframes scaleIn {
  from {
    transform: scale(0);
  }
  to {
    transform: scale(1);
  }
}
.result-icon.success {
  background: linear-gradient(135deg, #4caf50, #66bb6a);
  color: #fff;
  box-shadow: 0 8rpx 24rpx rgba(76, 175, 80, 0.4);
}
.result-icon.fail {
  background: linear-gradient(135deg, #f44336, #ef5350);
  color: #fff;
  box-shadow: 0 8rpx 24rpx rgba(244, 67, 54, 0.4);
}
.result-title {
  font-size: 32rpx;
  font-weight: bold;
  color: #333;
  margin-bottom: 12rpx;
}
.result-desc {
  font-size: 26rpx;
  color: #666;
  margin-bottom: 36rpx;
  line-height: 1.4;
}
.result-btn {
  background: linear-gradient(135deg, #ff6b6b, #ff8787);
  color: #fff;
  font-size: 28rpx;
  text-align: center;
  padding: 20rpx;
  border-radius: 12rpx;
  font-weight: 600;
  transition: all 0.3s ease;
  box-shadow: 0 4rpx 12rpx rgba(255, 107, 107, 0.3);
}
.result-btn:hover {
  transform: translateY(-2rpx);
  box-shadow: 0 6rpx 16rpx rgba(255, 107, 107, 0.4);
}
.result-btn:active {
  transform: translateY(2rpx);
  box-shadow: 0 2rpx 8rpx rgba(255, 107, 107, 0.4);
}
</style>