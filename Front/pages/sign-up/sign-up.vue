<template>
  <view class="sign-up-page">
    <view class="bg-wrapper">
      <image class="bg-img" src="/static/bg-samurai.png" mode="aspectFill"></image>
    </view>
    <view class="form-card">
      <text class="title">Java 课程报名</text>
      <view class="form-item">
        <text class="label">姓名</text>
        <input class="input" placeholder="请输入你的姓名" v-model="form.name" />
      </view>
      <view class="form-item">
        <text class="label">手机号</text>
        <input class="input" placeholder="请输入你的手机号" v-model="form.phone" />
      </view>
      <view class="form-item">
        <text class="label">意向班级</text>
        <picker :range="classList" @change="onClassChange" :value="form.classIndex">
          <view class="picker">{{ classList[form.classIndex] || '请选择' }}</view>
        </picker>
      </view>
      <button class="submit-btn" @click="submitForm">提交报名</button>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      form: {
        name: '',
        phone: '',
        classIndex: 0
      },
      classList: ['Java 基础班', 'Java 进阶班', 'Java 就业班']
    }
  },
  methods: {
    onClassChange(e) {
      this.form.classIndex = e.detail.value;
    },
    submitForm() {
		 if (!this.form.name || this.form.name.trim() === '') {
		        uni.showToast({
		          title: '请输入姓名',
		          icon: 'none'
		        });
		        return;
		      }
		      if (!this.form.phone || this.form.phone.trim() === '') {
		        uni.showToast({
		          title: '请输入手机号',
		          icon: 'none'
		        });
		        return;
		      }
		      // 手机号格式校验
		      if (!/^1[3-9]\d{9}$/.test(this.form.phone)) {
		        uni.showToast({
		          title: '手机号格式不正确',
		          icon: 'none'
		        });
		        return;
		      }
		const token = uni.getStorageSync('community_token')
      uni.request({
        url: 'http://localhost:8080/signup/submit',
        method: 'POST',
		header: {
			'Content-Type': 'application/json',
			'Authorization': token ? `Bearer ${token}` : '',
		},
        data: this.form,
        success: (res) => {
          uni.showToast({ title: '报名成功', icon: 'success' });
          setTimeout(() => uni.navigateBack(), 1500);
        },
        fail: () => {
          uni.showToast({ title: '报名失败', icon: 'none' });
        }
      });
    }
  }
}
</script>

<style scoped>
.sign-up-page {
  min-height: 100vh;
  position: relative;
}
.bg-wrapper {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: -1;
}
.bg-img {
  width: 100%;
  height: 100%;
}
.form-card {
  background: rgba(255,255,255,0.9);
  margin: 200rpx 40rpx 0;
  padding: 40rpx;
  border-radius: 20rpx;
}
.title {
  font-size: 40rpx;
  font-weight: bold;
  text-align: center;
  margin-bottom: 40rpx;
}
.form-item {
  margin-bottom: 30rpx;
}
.label {
  font-size: 28rpx;
  color: #333;
  margin-bottom: 10rpx;
  display: block;
}
.input, .picker {
  border: 1rpx solid #eee;
  border-radius: 10rpx;
  padding: 20rpx;
  font-size: 28rpx;
  background: #fff;
}
.submit-btn {
  width: 100%;
  background: #6a4c93;
  color: #fff;
  border-radius: 50rpx;
  padding: 24rpx 0;
  font-size: 32rpx;
  border: none;
  margin-top: 20rpx;
}
</style>