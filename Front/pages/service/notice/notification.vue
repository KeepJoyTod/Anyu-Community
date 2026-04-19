<template>
	<view class="notification-page">
		<!-- 背景装饰 -->
		<view class="bg-blur"></view>

		<!-- 顶部返回栏 -->
		<view class="nav-bar">
			<view class="back-btn" @click="goBack">
				<text class="back-icon">←</text>
			</view>
			<text class="nav-title">通知提醒</text>
			<view class="placeholder"></view>
		</view>

		<!-- 全部已读按钮 -->
		<view class="action-bar">
			<text class="mark-all-read" @click="markAllRead" :disabled="loading">全部已读</text>
		</view>

		<!-- 加载状态 -->
		<view class="loading-state" v-if="loading">
			<text class="loading-icon">⏳</text>
			<text class="loading-text">加载中...</text>
		</view>

		<!-- 通知列表（已排序：未读在前，已读在后） -->
		<scroll-view scroll-y class="notification-list" v-else-if="sortedNotifications.length">
			<view
				v-for="item in sortedNotifications"
				:key="item.id"
				class="notification-card"
				:class="{ unread: !item.read }"
				@click="goDetail(item.id)"
			>
				<view class="card-icon">{{ item.icon }}</view>
				<view class="card-content">
								<view class="card-header">
									<text class="title">{{ item.title }}</text>
									<text class="status" :class="{ unread: !item.read }">{{ item.read ? '已读' : '未读' }}</text>
								</view>
								<text class="desc">{{ item.content }}</text>
								<text class="extra" v-if="item.more_info">{{ item.more_info }}</text>
								<text class="time">{{ item.time }}</text>
							</view>
			</view>
		</scroll-view>

		<!-- 空状态 -->
		<view class="empty-state" v-else>
			<text class="empty-icon">📭</text>
			<text class="empty-text">暂无通知</text>
		</view>
	</view>
</template>

<script>
import { listNotices, markAllNoticesRead } from '@/api/notice'

export default {
	data() {
		return {
			notifications: [],
			loading: false
		};
	},
	computed: {
		// 未读在前，已读在后
		sortedNotifications() {
			return [...this.notifications].sort((a, b) => {
				if (!a.read && b.read) return -1;
				if (a.read && !b.read) return 1;
				return 0;
			});
		}
	},
	onLoad() {
		console.log('通知页面加载')
		this.fetchList()
		// 监听通知已读事件（来自详情页）
		uni.$on('notificationRead', this.handleNotificationRead);
	},
	onUnload() {
		uni.$off('notificationRead', this.handleNotificationRead);
	},
	methods: {
		goBack() {
			uni.navigateBack();
		},
		fetchList() {
			console.log('开始获取通知列表')
			this.loading = true
			listNotices(1, 100)
				.then(res => {
				console.log('获取通知列表成功:', res)
				if (!res || res.code !== 200) {
					uni.showToast({ title: (res && res.msg) || '加载失败', icon: 'none' })
					return
				}
				const result = res.data || {}
				this.notifications = (result.list || []).map(it => ({
							id: it.id,
							icon: it.icon,
							title: it.title,
							content: it.content,
							time: it.time,
							more_info: it.more_info,
							read: !!it.read
						}))
				console.log('通知列表处理完成:', this.notifications)
			})
				.catch(err => {
					console.error('获取通知列表失败:', err)
					uni.showToast({ title: '加载失败', icon: 'none' })
				})
				.finally(() => {
					this.loading = false
					console.log('获取通知列表完成')
				})
		},
		goDetail(id) {
			console.log('跳转到通知详情:', id)
			uni.navigateTo({
				url: `/pages/service/notice/detail?id=${id}`
			});
		},
		handleNotificationRead(data) {
			console.log('收到通知已读事件:', data)
			const index = this.notifications.findIndex(item => item.id === data.id);
			if (index !== -1 && !this.notifications[index].read) {
				this.notifications[index].read = true;
				// 因为使用了计算属性，排序会自动更新
			}
		},
		markAllRead() {
			console.log('开始标记全部已读')
			const anyUnread = this.notifications.some(n => !n.read)
			if (!anyUnread) {
				uni.showToast({ title: '暂无未读通知', icon: 'none' })
				return
			}
			this.loading = true
			markAllNoticesRead()
				.then(res => {
					console.log('标记全部已读成功', res)
					this.notifications = this.notifications.map(n => ({ ...n, read: true }))
					uni.showToast({ title: '全部已读', icon: 'success' })
				})
				.catch(err => {
					console.error('标记全部已读失败:', err)
					uni.showToast({ title: '操作失败', icon: 'none' })
				})
				.finally(() => {
					this.loading = false
					console.log('标记全部已读完成')
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

.mark-all-read[disabled] {
	opacity: 0.6;
	pointer-events: none;
}
</style>

<style scoped>
/* 样式与原 notification.vue 完全一致，此处省略，保持原有样式即可 */
.notification-page {
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
.action-bar {
	display: flex;
	justify-content: flex-end;
	margin-bottom: 24rpx;
	position: relative;
	z-index: 2;
}
.mark-all-read {
	font-size: 26rpx;
	color: #6b8c42;
	background: rgba(107,140,66,0.12);
	padding: 12rpx 28rpx;
	border-radius: 60rpx;
	font-weight: 500;
	transition: all 0.2s;
}
.mark-all-read:active {
	opacity: 0.7;
	transform: scale(0.96);
}
.notification-list {
	max-height: calc(100vh - 200rpx);
	position: relative;
	z-index: 2;
}
.notification-card {
	display: flex;
	background: rgba(255,255,255,0.92);
	backdrop-filter: blur(12rpx);
	border-radius: 44rpx;
	padding: 28rpx;
	margin-bottom: 24rpx;
	box-shadow: 0 20rpx 40rpx -16rpx rgba(0,0,0,0.08), 0 0 0 1rpx rgba(107,140,66,0.1);
	transition: all 0.2s;
	position: relative;
}
.notification-card:active {
	transform: scale(0.98);
	background: white;
}
.notification-card.unread {
	background: rgba(255,255,255,0.98);
	box-shadow: 0 20rpx 40rpx -12rpx rgba(107,140,66,0.2), 0 0 0 2rpx rgba(107,140,66,0.3);
}
.notification-card.unread::before {
	content: '';
	position: absolute;
	left: -2rpx;
	top: 28rpx;
	bottom: 28rpx;
	width: 6rpx;
	background: #6b8c42;
	border-radius: 4rpx;
}
.card-icon {
	font-size: 52rpx;
	width: 80rpx;
	height: 80rpx;
	background: rgba(107,140,66,0.1);
	border-radius: 40rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	margin-right: 24rpx;
}
.card-content {
	flex: 1;
}
.card-header {
	display: flex;
	justify-content: space-between;
	align-items: baseline;
	margin-bottom: 12rpx;
}
.title {
	font-size: 32rpx;
	font-weight: 700;
	color: #1f2e3a;
}
.status {
	font-size: 22rpx;
	padding: 4rpx 14rpx;
	border-radius: 40rpx;
	background: rgba(0,0,0,0.04);
	color: #8b9bae;
}
.status.unread {
	color: #6b8c42;
	background: rgba(107,140,66,0.12);
	font-weight: 500;
}
.desc {
			font-size: 28rpx;
			color: #4a5e71;
			line-height: 1.4;
			display: block;
			margin-bottom: 8rpx;
		}
		.extra {
			font-size: 26rpx;
			color: #6b8c42;
			line-height: 1.3;
			display: block;
			margin-bottom: 8rpx;
		}
		.time {
			font-size: 24rpx;
			color: #9aabb8;
			display: block;
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