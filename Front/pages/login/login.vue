<template>
	<view class="login-page">
		<view class="login-header">
			<text class="title">社区服务</text>
			<text class="subtitle">智慧社区，便捷生活</text>
		</view>
		
		<view class="login-form">
			<view class="form-item">
				<text class="icon">👤</text>
				<input class="input" v-model="form.username" placeholder="请输入用户账号" type="text" />
			</view>
			<view class="form-item">
				<text class="icon">🔒</text>
				<input class="input" v-model="form.password" placeholder="请输入密码" type="password" />
			</view>
			<view class="form-item captcha-item">
				<text class="icon">🔤</text>
				<input class="input captcha-input" v-model="form.code" placeholder="请输入验证码" type="text" maxlength="4" />
				<view class="captcha-box" @click="refreshCaptcha">
					<image v-if="captchaImg" class="captcha-image" :src="captchaImg" mode="aspectFill" />
					<view v-else class="captcha-placeholder">
						<text>点击刷新</text>
					</view>
				</view>
			</view>
			
			<view class="login-btn" @click="handleLogin">
				<text>登 录</text>
			</view>
			
			<view class="register-link" @click="goRegister">
				<text>还没有账号？立即注册</text>
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
				code: '',
				uuid: ''
			},
			captchaImg: ''
		}
	},
	onLoad() {
		console.log('登录页面加载')
		this.refreshCaptcha()
	},
	methods: {
		refreshCaptcha() {
			console.log('刷新验证码')
			uni.request({
				url: config.BASE_URL + '/home/captchaImage',
				method: 'GET',
				timeout: config.TIMEOUT,
				success: (res) => {
					console.log('验证码响应:', res)
					if (res.data.code === 200 || res.data.code === 0) {
						this.captchaImg = 'data:image/jpeg;base64,' + res.data.img
						this.form.uuid = res.data.uuid
						console.log('验证码获取成功')
					} else {
						uni.showToast({ title: '获取验证码失败', icon: 'none' })
						console.error('验证码获取失败:', res.data)
					}
				},
				fail: (err) => {
					console.error('获取验证码失败:', err)
					uni.showToast({ title: '网络请求失败', icon: 'none' })
				}
			})
		},
		handleLogin() {
			console.log('点击登录')
			if (!this.form.username) {
				uni.showToast({ title: '请输入用户账号', icon: 'none' })
				return
			}
			if (!this.form.password) {
				uni.showToast({ title: '请输入密码', icon: 'none' })
				return
			}
			if (!this.form.code) {
				uni.showToast({ title: '请输入验证码', icon: 'none' })
				return
			}
			
			uni.showLoading({ title: '登录中...' })
			
			uni.request({
				url: config.BASE_URL + '/home/auth/login',
				method: 'POST',
				timeout: config.TIMEOUT,
				data: {
					username: this.form.username,
					password: this.form.password,
					code: this.form.code,
					uuid: this.form.uuid
				},
				header: {
					'Content-Type': 'application/json'
				},
				success: (res) => {
					uni.hideLoading()
					console.log('登录响应:', res)
					if (res.data.code === 200) {
						uni.setStorageSync('userInfo', res.data.data)
						uni.setStorageSync(config.TOKEN_KEY, res.data.data.token)
						uni.setStorageSync('username', res.data.data.username)
						uni.showToast({ title: '登录成功', icon: 'success' })
						setTimeout(() => {
							uni.switchTab({ url: '/pages/index/index' })
						}, 1000)
					} else {
						uni.showToast({ title: res.data.msg || '登录失败', icon: 'none' })
						this.refreshCaptcha()
					}
				},
				fail: (err) => {
					uni.hideLoading()
					console.error('登录失败:', err)
					uni.showToast({ title: '网络请求失败', icon: 'none' })
				}
			})
		},
		goRegister() {
			console.log('点击注册')
			uni.navigateTo({
				url: '/pages/register/register',
				success: () => {
					console.log('跳转到注册页面成功')
				},
				fail: (err) => {
					console.error('跳转到注册页面失败:', err)
					uni.showToast({ title: '跳转失败', icon: 'none' })
				}
			})
		}
	}
}
</script>

<style scoped>
.login-page {
	min-height: 100vh;
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
	padding: 100rpx 60rpx;
}

.login-header {
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

.login-form {
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
	background-color: #f5f7fa;
	display: flex;
	align-items: center;
	justify-content: center;
}

.captcha-text {
	width: 180rpx;
	height: 80rpx;
	margin-left: 20rpx;
	border-radius: 8rpx;
	background-color: #f5f7fa;
	display: flex;
	align-items: center;
	justify-content: center;
	font-size: 36rpx;
	font-weight: bold;
	color: #333;
	letter-spacing: 8rpx;
}

.captcha-box {
	width: 180rpx;
	height: 80rpx;
	margin-left: 20rpx;
	border-radius: 8rpx;
	overflow: hidden;
	background-color: #f5f7fa;
	display: flex;
	align-items: center;
	justify-content: center;
}

.captcha-placeholder {
	width: 100%;
	height: 100%;
	display: flex;
	align-items: center;
	justify-content: center;
	color: #999;
	font-size: 24rpx;
}

.login-btn {
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
	border-radius: 16rpx;
	padding: 28rpx;
	text-align: center;
	margin-top: 40rpx;
}

.login-btn text {
	font-size: 34rpx;
	color: #fff;
	font-weight: bold;
}

.register-link {
	text-align: center;
	margin-top: 40rpx;
}

.register-link text {
	font-size: 28rpx;
	color: #667eea;
}
</style>
