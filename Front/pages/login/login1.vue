<template>
	<view class="login-page">
		<view class="login-header">
			<text class="title">社区服务</text>
			<text class="subtitle">智慧社区，便捷生活</text>
		</view>
		
		<view class="login-form">
			<view class="form-item">
				<text class="icon">👤</text>
				<input class="input" v-model="form.username" placeholder="请输入用户名" />
			</view>
			<view class="form-item">
				<text class="icon">🔒</text>
				<input class="input" v-model="form.password" placeholder="请输入密码" type="password" />
			</view>
			<view class="form-item">
				<text class="icon">🔢</text>
				<input class="input code-input" v-model="form.code" placeholder="请输入验证码" />
				<view class="code-btn" @click="getCode">
					<text v-if="codeLoading">加载中...</text>
					<image v-else-if="codeUrl" class="code-img" :src="codeUrl" mode="aspectFit"></image>
					<text v-else>点击获取</text>
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
			codeUrl: '',
			codeLoading: false
		}
	},
	onLoad() {
		this.getCode()
	},
	methods: {
		getCode() {
			if (this.codeLoading) return
			this.codeLoading = true
			
			const token = uni.getStorageSync(config.TOKEN_KEY) || ''
			
			uni.request({
				url: config.BASE_URL + '/captchaImage',
				method: 'GET',
				header: {
					'Content-Type': 'application/json',
					'Authorization': token ? `Bearer ${token}` : ''
				},
				success: (res) => {
					console.log('验证码响应:', res.data)
					this.codeLoading = false
					if (res.data && res.data.img) {
						this.codeUrl = 'data:image/gif;base64,' + res.data.img
						this.form.uuid = res.data.uuid
						console.log('验证码uuid:', this.form.uuid)
					} else {
						uni.showToast({ title: '获取验证码失败', icon: 'none' })
					}
				},
				fail: (err) => {
					this.codeLoading = false
					console.error('获取验证码失败:', err)
					uni.showToast({ title: '网络请求失败', icon: 'none' })
				}
			})
		},
		handleLogin() {
			if (!this.form.username) {
				uni.showToast({ title: '请输入用户名', icon: 'none' })
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
			if (!this.form.uuid) {
				uni.showToast({ title: '请先获取验证码', icon: 'none' })
				this.getCode()
				return
			}
			
			uni.showLoading({ title: '登录中...' })
			
			uni.request({
				url: config.BASE_URL + '/login',
				method: 'POST',
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
					console.log('登录响应:', res.data)
					uni.hideLoading()
					if (res.data.code === 200) {
						uni.setStorageSync(config.TOKEN_KEY, res.data.token)
						let to = uni.getStorageSync(config.TOKEN_KEY)
						console.log("token: " + to);
						uni.showToast({ title: '登录成功', icon: 'success' })
						setTimeout(() => {
							uni.switchTab({ url: '/pages/index/index' })
						}, 1000)
					} else {
						uni.showToast({ title: res.data.msg || '登录失败', icon: 'none' })
						this.getCode()
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
			uni.showToast({ title: '请联系管理员注册', icon: 'none' })
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

.icon {
	font-size: 36rpx;
	margin-right: 20rpx;
}

.input {
	flex: 1;
	font-size: 30rpx;
}

.code-input {
	flex: 1;
}

.code-btn {
	width: 200rpx;
	height: 70rpx;
	margin-left: 20rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	background-color: #667eea;
	border-radius: 8rpx;
	overflow: hidden;
}

.code-btn text {
	font-size: 24rpx;
	color: #fff;
}

.code-img {
	width: 200rpx;
	height: 70rpx;
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
