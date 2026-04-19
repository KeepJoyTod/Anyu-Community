<template>
	<view class="register-page">
		<view class="register-header">
			<text class="title">社区服务</text>
			<text class="subtitle">智慧社区，便捷生活</text>
		</view>
		
		<view class="register-form">
			<view class="form-item">
				<text class="icon">👤</text>
				<input class="input" v-model="form.username" placeholder="请输入用户账号" type="text" />
			</view>
			<view class="form-item">
				<text class="icon">🔒</text>
				<input class="input" v-model="form.password" placeholder="请输入密码" type="password" />
			</view>
			<view class="form-item">
				<text class="icon">🔒</text>
				<input class="input" v-model="form.confirmPassword" placeholder="请确认密码" type="password" />
			</view>
			<view class="form-item">
				<text class="icon">📱</text>
				<input class="input" v-model="form.phonenumber" placeholder="请输入手机号码" type="number" maxlength="11" />
			</view>
			<view class="form-item">
				<text class="icon">👤</text>
				<input class="input" v-model="form.nickName" placeholder="请输入昵称" type="text" />
			</view>
			<view class="form-item">
				<text class="icon">📧</text>
				<input class="input" v-model="form.email" placeholder="请输入邮箱（选填）" type="email" />
			</view>
			<view class="form-item captcha-item">
				<text class="icon">🔤</text>
				<input class="input captcha-input" v-model="form.code" placeholder="请输入验证码" type="text" maxlength="4" />
				<image class="captcha-image" :src="captchaImg" @click="refreshCaptcha" mode="aspectFill"></image>
			</view>
			
			<view class="register-btn" @click="handleRegister">
				<text>注 册</text>
			</view>
			
			<view class="login-link" @click="goLogin">
				<text>已有账号？立即登录</text>
			</view>
		</view>
	</view>
</template>

<script>
import config from '@/api/config.js'

export default {
	data() {
		return {
			form: {
				username: '',
				password: '',
				confirmPassword: '',
				phonenumber: '',
				nickName: '',
				email: '',
				code: '',
				uuid: ''
			},
			captchaImg: ''
		}
	},
	onLoad() {
		this.refreshCaptcha()
	},
	methods: {
		refreshCaptcha() {
			uni.request({
				url: config.BASE_URL + '/home/captchaImage',
				method: 'GET',
				success: (res) => {
					if (res.data.code === 200 || res.data.code === 0) {
						this.captchaImg = 'data:image/jpeg;base64,' + res.data.img
						this.form.uuid = res.data.uuid
					} else {
						uni.showToast({ title: '获取验证码失败', icon: 'none' })
					}
				},
				fail: (err) => {
					console.error('获取验证码失败:', err)
					uni.showToast({ title: '获取验证码失败', icon: 'none' })
				}
			})
		},
		handleRegister() {
			if (!this.form.username) {
				uni.showToast({ title: '请输入用户账号', icon: 'none' })
				return
			}
			if (this.form.username.length < 3) {
				uni.showToast({ title: '用户账号至少3位', icon: 'none' })
				return
			}
			if (!this.form.password) {
				uni.showToast({ title: '请输入密码', icon: 'none' })
				return
			}
			if (this.form.password.length < 6) {
				uni.showToast({ title: '密码至少6位', icon: 'none' })
				return
			}
			if (!this.form.confirmPassword) {
				uni.showToast({ title: '请确认密码', icon: 'none' })
				return
			}
			if (this.form.password !== this.form.confirmPassword) {
				uni.showToast({ title: '两次密码不一致', icon: 'none' })
				return
			}
			if (!this.form.phonenumber) {
				uni.showToast({ title: '请输入手机号码', icon: 'none' })
				return
			}
			if (!/^1[3-9]\d{9}$/.test(this.form.phonenumber)) {
				uni.showToast({ title: '手机号码格式不正确', icon: 'none' })
				return
			}
			if (!this.form.nickName) {
				uni.showToast({ title: '请输入昵称', icon: 'none' })
				return
			}
			if (!this.form.code) {
				uni.showToast({ title: '请输入验证码', icon: 'none' })
				return
			}
			
			uni.showLoading({ title: '注册中...' })
			
			uni.request({
				url: config.BASE_URL + '/home/auth/register',
				method: 'POST',
				data: {
					username: this.form.username,
					password: this.form.password,
					phonenumber: this.form.phonenumber,
					nickName: this.form.nickName,
					email: this.form.email,
					code: this.form.code,
					uuid: this.form.uuid
				},
				header: {
					'Content-Type': 'application/json'
				},
				success: (res) => {
					uni.hideLoading()
					if (res.data.code === 200) {
						uni.showToast({ title: '注册成功', icon: 'success' })
						setTimeout(() => {
							uni.navigateBack()
						}, 1000)
					} else {
						uni.showToast({ title: res.data.msg || '注册失败', icon: 'none' })
						this.refreshCaptcha()
					}
				},
				fail: (err) => {
					uni.hideLoading()
					uni.showToast({ title: '网络请求失败', icon: 'none' })
				}
			})
		},
		goLogin() {
			uni.navigateBack()
		}
	}
}
</script>

<style scoped>
.register-page {
	min-height: 100vh;
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
	padding: 100rpx 60rpx;
}

.register-header {
	text-align: center;
	margin-bottom: 80rpx;
}

.title {
	font-size: 48rpx;
	font-weight: bold;
	color: #fff;
	display: block;
	margin-bottom: 16rpx;
}

.subtitle {
	font-size: 28rpx;
	color: rgba(255, 255, 255, 0.8);
}

.register-form {
	background-color: #fff;
	border-radius: 32rpx;
	padding: 60rpx 40rpx;
}

.form-item {
	display: flex;
	align-items: center;
	background-color: #f5f7fa;
	border-radius: 16rpx;
	padding: 24rpx;
	margin-bottom: 30rpx;
}

.captcha-item {
	justify-content: space-between;
}

.icon {
	font-size: 36rpx;
	margin-right: 20rpx;
}

.input {
	flex: 1;
	font-size: 30rpx;
}

.captcha-input {
	flex: 1;
}

.captcha-image {
	width: 180rpx;
	height: 80rpx;
	margin-left: 20rpx;
	border-radius: 8rpx;
	background-color: #fff;
}

.register-btn {
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
	border-radius: 16rpx;
	padding: 28rpx;
	text-align: center;
	margin-top: 40rpx;
}

.register-btn text {
	font-size: 34rpx;
	color: #fff;
	font-weight: bold;
}

.login-link {
	text-align: center;
	margin-top: 40rpx;
}

.login-link text {
	font-size: 28rpx;
	color: #667eea;
}
</style>
