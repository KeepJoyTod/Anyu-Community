<template>
	<view class="detail-page">
		<!-- 背景装饰 -->
		<view class="bg-blur"></view>

		<!-- 顶部返回栏 -->
		<view class="nav-bar">
			<view class="back-btn" @click="goBack">
				<text class="back-icon">←</text>
			</view>
			<text class="nav-title">通知详情</text>
			<view class="placeholder"></view>
		</view>

		<!-- 加载状态 -->
		<view class="loading-state" v-if="loading">
			<text class="loading-icon">⏳</text>
			<text class="loading-text">加载中...</text>
		</view>

		<!-- 内容卡片 -->
		<view class="detail-card" v-else-if="notification">
			<view class="card-header">
				<text class="title">{{ notification.title }}</text>
				<text class="time">{{ notification.time }}</text>
			</view>
			<view class="divider"></view>
			<view class="content">
				<text class="content-text">{{ notification.content }}</text>
			</view>
			<view class="extra-info" v-if="notification.more_info">
					<text class="extra-label">更多信息：</text>
					<text class="extra-text">{{ notification.more_info }}</text>
				</view>
		</view>

		<!-- 空状态 -->
		<view class="empty-state" v-else>
			<text class="empty-icon">📭</text>
			<text class="empty-text">通知不存在</text>
		</view>
	</view>
</template>

<script>
import { getNoticeDetail, markNoticeRead } from '@/api/notice'

export default {
	data() {
		return {
			notification: null,
			loading: true
		};
	},
	onLoad(options) {
		console.log('通知详情页面加载，参数:', options)
		const id = parseInt(options.id)
		console.log('通知ID:', id)
		this.loadDetail(id)
	},
	methods: {
		goBack() {
			uni.navigateBack();
		},
		loadDetail(id) {
			console.log('开始获取通知详情:', id)
			this.loading = true
			getNoticeDetail(id)
				.then(res => {
					console.log('获取通知详情成功:', res)
					const data = res.data
					this.notification = {
						id: data.id,
						title: data.title,
						content: data.content,
						time: data.time,
						more_info: data.more_info,
						read: !!data.read
					}
					console.log('通知详情处理完成:', this.notification)
					if (!this.notification.read) {
						console.log('标记通知已读:', id)
						markNoticeRead(id)
							.then(res => {
								console.log('标记已读成功', res)
								this.notification.read = true
								uni.$emit('notificationRead', { id })
							})
							.catch(err => {
								console.error('标记已读失败:', err)
								uni.showToast({ title: '标记已读失败', icon: 'none' })
							})
					}
				})
				.catch(err => {
					console.error('获取通知详情失败:', err)
					uni.showToast({ title: '加载失败', icon: 'none' })
				})
				.finally(() => {
					this.loading = false
					console.log('获取通知详情完成')
				})
		}
	}
};
</script>

<style scoped>
/* 加载状态样式 */
.loading-state {
	background: rgba(255,255,255,0.9);
	backdrop-filter: blur(8rpx);
	border-radius: 44rpx;
	padding: 100rpx 0;
	text-align: center;
	display: flex;
	flex-direction: column;
	align-items: center;
	gap: 20rpx;
	margin-top: 120rpx;
}

.loading-icon {
	font-size: 80rpx;
	animation: spin 1s linear infinite;
}

.loading-text {
	font-size: 28rpx;
	color: #6b8c42;
}

@keyframes spin {
	from { transform: rotate(0deg); }
	to { transform: rotate(360deg); }
}
</style>

<style scoped>
.detail-page {
	position: relative;
	min-height: 100vh;
	background: #f6f9fc;
	padding: 0 32rpx 60rpx;
	overflow-x: hidden;
}

.bg-blur {
	position: fixed;
	top: -20%;
	right: -20%;
	width: 140%;
	height: 140%;
	background: radial-gradient(circle at 80% 20%, rgba(107, 140, 66, 0.08) 0%, rgba(107, 140, 66, 0.02) 60%, transparent 90%);
	pointer-events: none;
	z-index: 0;
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

.detail-card {
	background: rgba(255,255,255,0.92);
	backdrop-filter: blur(12rpx);
	border-radius: 44rpx;
	padding: 32rpx;
	margin-bottom: 32rpx;
	box-shadow: 0 20rpx 40rpx -16rpx rgba(0,0,0,0.08), 0 0 0 1rpx rgba(107,140,66,0.1);
}

.card-header {
	margin-bottom: 20rpx;
}

.title {
	font-size: 36rpx;
	font-weight: 700;
	color: #1f2e3a;
	display: block;
	margin-bottom: 12rpx;
}

.time {
	font-size: 26rpx;
	color: #9aabb8;
}

.divider {
	height: 1rpx;
	background: rgba(107,140,66,0.2);
	margin: 20rpx 0;
}

.content {
	margin: 20rpx 0;
}

.content-text {
	font-size: 30rpx;
	color: #1c2e3a;
	line-height: 1.5;
}

.extra-info {
	margin-top: 28rpx;
	padding: 20rpx;
	background: rgba(107,140,66,0.08);
	border-radius: 32rpx;
}

.extra-label {
	font-size: 28rpx;
	font-weight: 500;
	color: #6b8c42;
	display: block;
	margin-bottom: 8rpx;
}

.extra-text {
	font-size: 28rpx;
	color: #4a5e71;
	line-height: 1.4;
}

.empty-state {
	background: rgba(255,255,255,0.9);
	backdrop-filter: blur(8rpx);
	border-radius: 44rpx;
	padding: 100rpx 0;
	text-align: center;
	display: flex;
	flex-direction: column;
	align-items: center;
	gap: 20rpx;
	margin-top: 120rpx;
}

.empty-icon {
	font-size: 80rpx;
	opacity: 0.6;
}

.empty-text {
	font-size: 28rpx;
	color: #9aabb8;
}
</style>