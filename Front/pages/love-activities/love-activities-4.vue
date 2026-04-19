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
			<button class="donate-btn" @click="handleDonate">我要捐赠</button>
		</view>
	</view>
</template>

<script>
export default {
	data() {
		return {
			project: {
				id: 4,
				cover: "https://picsum.photos/id/132/800/400",
				status: "运行中",
				location: "河北涞源希望小学",
				title: "山区儿童助学物资捐赠",
				current: 89,
				target: 200,
				deadline: "2026-03-25",
				description: "为山区儿童募集课外读物、文具、体育用品等物资。所有物资将统一送往河北涞源希望小学，欢迎爱心人士捐赠或参与物资整理志愿工作。",
				orgName: "助学公益基金会",
				orgDesc: "专注山区教育扶贫12年，已帮助超过100所乡村学校改善教学条件。"
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
				title: '感谢您的爱心！',
				icon: 'success',
				duration: 2000
			});
			// 可跳转到捐赠表单页
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