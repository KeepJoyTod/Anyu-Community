<template>
	<view class="project-detail">
		<view class="cover">
			<image :src="project.cover" mode="aspectFill" class="cover-image"></image>
			<view class="status-tag">{{ project.status }}</view>
		</view>

		<view class="info-card">
			<text class="title">{{ project.title }}</text>
			<view class="meta">
				<text class="location">{{ project.location }}</text>
				<text class="deadline">截止：{{ project.deadline }}</text>
			</view>
			<view class="progress-section">
				<view class="progress-bar">
					<view class="progress-fill" :style="{ width: progressPercent + '%' }"></view>
				</view>
				<text class="progress-text">{{ project.current }} / {{ project.target }} 人参与</text>
			</view>
		</view>

		<view class="detail-card">
			<text class="section-title">项目详情</text>
			<text class="detail-text">{{ project.description }}</text>
		</view>

		<view class="org-card">
			<text class="section-title">发起机构</text>
			<view class="org-info">
				<text class="org-name">{{ project.orgName }}</text>
				<text class="org-desc">{{ project.orgDesc }}</text>
			</view>
		</view>

		<view class="footer-btn">
			<button class="donate-btn" @click="handleDonate">我要报名</button>
		</view>
	</view>
</template>

<script>
export default {
	data() {
		return {
			project: {
				id: 2,
				cover: "https://picsum.photos/id/20/800/400",
				status: "运行中",
				location: "恒基中心1座",
				title: "职场加油站志愿服务项目",
				current: 342,
				target: 1000,
				deadline: "2026-03-06",
				description: "为职场新人提供简历优化、面试技巧、职业规划等公益咨询。每周六下午在恒基中心举行，欢迎资深职场人报名成为志愿者导师，也欢迎求职者参与。",
				orgName: "北京职业发展促进会",
				orgDesc: "非营利性职业发展平台，已帮助超过5000名求职者找到心仪工作。"
			}
		};
	},
	computed: {
		progressPercent() {
			return (this.project.current / this.project.target) * 100;
		}
	},
	methods: {
		handleDonate() {
			uni.showToast({
				title: '报名成功！',
				icon: 'success',
				duration: 2000
			});
		}
	}
};
</script>

<style scoped>
/* 与项目1样式相同，可复用，此处省略重复代码，实际使用时可复制上面的样式 */
.project-detail {
	background-color: #f8f8f8;
	min-height: 100vh;
	padding-bottom: 120rpx;
}
.cover {
	position: relative;
	width: 100%;
	height: 400rpx;
}
.cover-image {
	width: 100%;
	height: 100%;
}
.status-tag {
	position: absolute;
	top: 30rpx;
	left: 30rpx;
	background-color: #34b950;
	color: #fff;
	padding: 8rpx 24rpx;
	border-radius: 30rpx;
	font-size: 28rpx;
}
.info-card, .detail-card, .org-card {
	background: #fff;
	margin: 24rpx;
	padding: 32rpx;
	border-radius: 24rpx;
	box-shadow: 0 4rpx 12rpx rgba(0,0,0,0.04);
}
.title {
	font-size: 40rpx;
	font-weight: bold;
	color: #1f2d3d;
	display: block;
	margin-bottom: 16rpx;
}
.meta {
	display: flex;
	justify-content: space-between;
	margin-bottom: 24rpx;
	font-size: 28rpx;
	color: #666;
}
.progress-section {
	margin-top: 20rpx;
}
.progress-bar {
	height: 12rpx;
	background-color: #e0e0e0;
	border-radius: 6rpx;
	overflow: hidden;
}
.progress-fill {
	height: 100%;
	background-color: #4caf50;
	border-radius: 6rpx;
}
.progress-text {
	font-size: 26rpx;
	color: #666;
	margin-top: 12rpx;
	display: block;
}
.section-title {
	font-size: 34rpx;
	font-weight: 500;
	color: #1f2d3d;
	display: block;
	margin-bottom: 20rpx;
}
.detail-text {
	font-size: 28rpx;
	line-height: 1.6;
	color: #3c4b5a;
}
.org-info {
	margin-top: 12rpx;
}
.org-name {
	font-size: 32rpx;
	font-weight: 500;
	color: #333;
	display: block;
	margin-bottom: 12rpx;
}
.org-desc {
	font-size: 28rpx;
	color: #666;
	line-height: 1.5;
}
.footer-btn {
	position: fixed;
	bottom: 0;
	left: 0;
	right: 0;
	padding: 24rpx 32rpx;
	background: #fff;
	box-shadow: 0 -4rpx 12rpx rgba(0,0,0,0.04);
}
.donate-btn {
	background: linear-gradient(135deg, #d9232e, #b51f28);
	color: #fff;
	border: none;
	border-radius: 48rpx;
	font-size: 32rpx;
	font-weight: 500;
	padding: 24rpx 0;
}
</style>