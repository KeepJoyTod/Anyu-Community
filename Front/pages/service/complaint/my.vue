// pages/my/my.vue
<template>
	<view class="my-page">
		<!-- 背景装饰 -->
		<view class="bg-blur"></view>
		<!-- 顶部导航栏 -->
		<view class="nav-bar">
			<view class="back-btn" @click="goBack">
				<text class="back-icon">←</text>
			</view>
			<text class="nav-title">我的反馈</text>
		</view>

		<!-- 顶部选项卡 -->
		<view class="tab-bar">
			<view class="tab-item" :class="{ active: currentTab === 'complaint' }" @click="switchTab('complaint')">
				投诉反馈
			</view>
			<view class="tab-item" :class="{ active: currentTab === 'suggestion' }" @click="switchTab('suggestion')">
				建议反馈
			</view>
			<view class="tab-item" :class="{ active: currentTab === 'record' }" @click="switchTab('record')">
				我的记录
			</view>
		</view>

		<!-- 内容区域 -->
		<scroll-view class="content-scroll" scroll-y>
			<!-- 投诉区块 -->
			<view class="section">
				<view class="section-header">
					<text class="section-title">📌 我的投诉</text>
					<text class="section-count">{{ complaintList.length }}</text>
				</view>
				<view v-if="complaintList.length === 0" class="empty-state">
					<text class="empty-icon">📭</text>
					<text class="empty-text">暂无投诉记录</text>
				</view>
				<view v-else class="card-list">
					<view class="feedback-card" v-for="item in complaintList" :key="item.id">
						<view class="card-header">
							<text class="card-title">{{ item.title }}</text>
							<text class="delete-icon" @click="deleteItem(item.id)">🗑️</text>
						</view>
						<text class="card-content">{{ item.content }}</text>
						<view class="card-footer">
							<text class="card-time">{{ item.createTime }}</text>
							<text v-if="item.contact" class="card-contact">📞 {{ item.contact }}</text>
						</view>
						<view v-if="item.images && item.images.length" class="card-images">
							<image v-for="(img, idx) in item.images.slice(0, 3)" :key="idx" :src="img" mode="aspectFill"
								class="thumb-img" @click="previewImages(item.images, idx)"></image>
							<text v-if="item.images.length > 3" class="more-img">+{{ item.images.length - 3 }}</text>
						</view>
					</view>
				</view>
			</view>

			<!-- 分割线 -->
			<view class="divider-line"></view>

			<!-- 建议区块 -->
			<view class="section">
				<view class="section-header">
					<text class="section-title">✨ 我的建议</text>
					<text class="section-count">{{ suggestionList.length }}</text>
				</view>
				<view v-if="suggestionList.length === 0" class="empty-state">
					<text class="empty-icon">💭</text>
					<text class="empty-text">暂无建议记录</text>
				</view>
				<view v-else class="card-list">
					<view class="feedback-card" v-for="item in suggestionList" :key="item.id">
						<view class="card-header">
							<text class="card-title">{{ item.title }}</text>
							<text class="delete-icon" @click="deleteItem(item.id)">🗑️</text>
						</view>
						<text class="card-content">{{ item.content }}</text>
						<view class="card-footer">
							<text class="card-time">{{ item.createTime }}</text>
							<text v-if="item.contact" class="card-contact">📞 {{ item.contact }}</text>
						</view>
						<view v-if="item.images && item.images.length" class="card-images">
							<image v-for="(img, idx) in item.images.slice(0, 3)" :key="idx" :src="img" mode="aspectFill"
								class="thumb-img" @click="previewImages(item.images, idx)"></image>
							<text v-if="item.images.length > 3" class="more-img">+{{ item.images.length - 3 }}</text>
						</view>
					</view>
				</view>
			</view>
		</scroll-view>
	</view>
</template>

<script>
	import { getFeedbackList, deleteFeedback } from '@/api/complaint';
	export default {
		data() {
			return {
				currentTab: 'record',
				feedbackList: []
			};
		},
		computed: {
			complaintList() {
				return this.feedbackList.filter(item => item.type === 'complaint');
			},
			suggestionList() {
				return this.feedbackList.filter(item => item.type === 'suggestion');
			}
		},
		onLoad() {
			this.currentTab = 'record';
			this.loadData();
		},
		onShow() {
			this.loadData();
		},
		methods: {
			switchTab(tab) {
				if (tab === 'record') return;
				if (tab === 'complaint') {
					uni.redirectTo({
						url: '/pages/service/complaint/complaint'
					});
				} else if (tab === 'suggestion') {
					uni.redirectTo({
						url: '/pages/service/complaint/suggestion'
					});
				}
			},
			loadData() {
				uni.showLoading({
					title: '加载中...'
				});
				getFeedbackList()
					.then(res => {
						uni.hideLoading();
						this.feedbackList = res.data || [];
					})
					.catch(() => {
						uni.hideLoading();
					});
			},
			deleteItem(id) {
				uni.showModal({
					title: '删除反馈',
					content: '确定要删除这条记录吗？',
					success: (res) => {
						if (res.confirm) {
							uni.showLoading({
								title: '删除中...'
							});
							deleteFeedback(id)
								.then(() => {
									uni.hideLoading();
									this.loadData();
									uni.showToast({
										title: '已删除',
										icon: 'success'
									});
								})
								.catch(() => {
									uni.hideLoading();
								});
						}
					}
				});
			},
			previewImages(urls, currentIdx) {
				uni.previewImage({
					urls: urls,
					current: urls[currentIdx]
				});
			},
			goBack() {
			uni.navigateBack({
				delta: 1
			});
		}
		}
	};
</script>

<style scoped>
	.my-page {
		position: relative;
		min-height: 100vh;
		background: #f6f9fc;
		padding: 0 32rpx 40rpx;
		display: flex;
		flex-direction: column;
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

	/* 选项卡样式 */
	.tab-bar {
		display: flex;
		background: rgba(255, 255, 255, 0.9);
		backdrop-filter: blur(12rpx);
		border-radius: 60rpx;
		padding: 8rpx;
		margin: 10rpx 0 20rpx;
		position: relative;
		z-index: 2;
		box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.04);
	}

	.tab-item {
		flex: 1;
		text-align: center;
		padding: 18rpx 0;
		font-size: 28rpx;
		font-weight: 500;
		color: #7e8d9e;
		border-radius: 48rpx;
		transition: all 0.2s;
	}

	.tab-item.active {
		background: linear-gradient(105deg, #6b8c42, #528a34);
		color: white;
		box-shadow: 0 4rpx 12rpx rgba(107, 140, 66, 0.3);
	}

	.content-scroll {
		flex: 1;
		position: relative;
		z-index: 2;
		height: calc(100vh - 200rpx);
	}

	.section {
		margin-bottom: 32rpx;
	}

	.section-header {
		display: flex;
		justify-content: space-between;
		align-items: baseline;
		padding: 16rpx 8rpx 16rpx 0;
	}

	.section-title {
		font-size: 32rpx;
		font-weight: 600;
		color: #2c4b3a;
		position: relative;
		padding-left: 16rpx;
	}

	.section-title::before {
		content: '';
		position: absolute;
		left: 0;
		top: 50%;
		transform: translateY(-50%);
		width: 6rpx;
		height: 28rpx;
		background: linear-gradient(135deg, #6b8c42, #528a34);
		border-radius: 6rpx;
	}

	.section-count {
		background: rgba(107, 140, 66, 0.15);
		padding: 6rpx 18rpx;
		border-radius: 40rpx;
		font-size: 24rpx;
		color: #528a34;
		font-weight: 500;
	}

	.empty-state {
		background: rgba(255, 255, 255, 0.7);
		backdrop-filter: blur(8rpx);
		border-radius: 32rpx;
		padding: 60rpx 0;
		display: flex;
		flex-direction: column;
		align-items: center;
		gap: 16rpx;
	}

	.empty-icon {
		font-size: 64rpx;
		opacity: 0.6;
	}

	.empty-text {
		font-size: 26rpx;
		color: #9aabb8;
	}

	.card-list {
		display: flex;
		flex-direction: column;
		gap: 24rpx;
	}

	.feedback-card {
		background: rgba(255, 255, 255, 0.85);
		backdrop-filter: blur(12rpx);
		border-radius: 32rpx;
		padding: 28rpx;
		box-shadow: 0 8rpx 20rpx rgba(0, 0, 0, 0.04), 0 0 0 1rpx rgba(107, 140, 66, 0.08);
		transition: all 0.2s;
	}

	.card-header {
		display: flex;
		justify-content: space-between;
		align-items: center;
		margin-bottom: 16rpx;
	}

	.card-title {
		font-size: 32rpx;
		font-weight: 600;
		color: #1f3b2c;
		line-height: 1.3;
		flex: 1;
	}

	.delete-icon {
		font-size: 40rpx;
		color: #c0ccd6;
		padding: 8rpx;
		transition: color 0.2s;
	}

	.delete-icon:active {
		color: #e67e22;
	}

	.card-content {
		font-size: 28rpx;
		color: #5c6f82;
		line-height: 1.5;
		display: -webkit-box;
		-webkit-box-orient: vertical;
		-webkit-line-clamp: 2;
		overflow: hidden;
		margin-bottom: 20rpx;
	}

	.card-footer {
		display: flex;
		justify-content: space-between;
		align-items: center;
		flex-wrap: wrap;
		gap: 12rpx;
		margin-bottom: 16rpx;
	}

	.card-time {
		font-size: 22rpx;
		color: #9aabb8;
	}

	.card-contact {
		font-size: 24rpx;
		color: #6b8c42;
		background: rgba(107, 140, 66, 0.1);
		padding: 4rpx 16rpx;
		border-radius: 30rpx;
	}

	.card-images {
		display: flex;
		gap: 12rpx;
		flex-wrap: wrap;
		align-items: center;
		margin-top: 8rpx;
	}

	.thumb-img {
		width: 100rpx;
		height: 100rpx;
		border-radius: 16rpx;
		background: #eef3e6;
	}

	.more-img {
		font-size: 24rpx;
		color: #7c8f9e;
		background: rgba(0, 0, 0, 0.05);
		padding: 4rpx 12rpx;
		border-radius: 30rpx;
	}

	.divider-line {
		height: 2rpx;
		background: linear-gradient(90deg, transparent, rgba(107, 140, 66, 0.3), rgba(107, 140, 66, 0.6), rgba(107, 140, 66, 0.3), transparent);
		margin: 24rpx 0 32rpx;
	}
	/* 顶部导航栏样式 */
	.nav-bar {
		display: flex;
		align-items: center;
		padding: 20rpx 0;
		position: relative;
		z-index: 3;
		margin-top: var(--status-bar-height, 0);
	}
	.back-btn {
		width: 64rpx;
		height: 64rpx;
		background: rgba(255, 255, 255, 0.9);
		backdrop-filter: blur(12rpx);
		border-radius: 50%;
		display: flex;
		align-items: center;
		justify-content: center;
		box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.06);
		transition: all 0.2s;
	}
	.back-btn:active {
		transform: scale(0.92);
	}
	.back-icon {
		font-size: 32rpx;
		color: #6b8c42;
		font-weight: bold;
	}
	.nav-title {
		position: absolute;
		left: 50%;
		top: 50%;
		transform: translate(-50%, -50%);
		font-size: 34rpx;
		font-weight: 600;
		color: #2c4b3a;
	}
</style>