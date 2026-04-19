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
				id: 3,
				cover: "https://picsum.photos/id/26/800/400",
				status: "运行中",
				location: "朝阳区养老院",
				title: "关爱空巢老人陪伴计划",
				current: 45,
				target: 50,
				deadline: "2026-04-01",
				description: "定期上门陪伴社区空巢老人，帮助打扫卫生、聊天、代购等。每周六上午组织活动，欢迎有爱心、耐心的志愿者加入。",
				orgName: "朝阳区志愿者协会",
				orgDesc: "成立于2010年，专注于社区养老志愿服务，拥有超过300名注册志愿者。"
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
/* 与项目1样式相同，此处略，实际复制项目1的样式即可 */
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