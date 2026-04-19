<template>
	<view class="project-detail">
		<!-- 封面图 -->
		<view class="cover">
			<image :src="project.cover" mode="aspectFill" class="cover-image"></image>
			<view class="status-tag">{{ project.status }}</view>
		</view>

		<!-- 基本信息 -->
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

		<!-- 项目详情 -->
		<view class="detail-card">
			<text class="section-title">项目详情</text>
			<text class="detail-text">{{ project.description }}</text>
		</view>

		<!-- 发起机构 -->
		<view class="org-card">
			<text class="section-title">发起机构</text>
			<view class="org-info">
				<text class="org-name">{{ project.orgName }}</text>
				<text class="org-desc">{{ project.orgDesc }}</text>
			</view>
		</view>

		<!-- 底部按钮 -->
		<view class="footer-btn">			<button class="donate-btn" @click="handleDonate" v-if="!isRegistered">我要报名</button>			<button class="donate-btn registered" disabled v-else>报名成功</button>		</view>
	</view>
</template>

<script>
import { registerActivity } from '../../api/community.js'
export default {
	data() {
		return {
			project: {
				id: 1,
				cover: "https://picsum.photos/id/104/800/400",
				status: "运行中",
				location: "北京科技大学天津学院",
				title: "“爱环保”垃圾清除公益行动",
				current: 128,
				target: 200,
				deadline: "2026-03-18",
				description: "本项目旨在通过志愿者团队清理校园及周边社区的垃圾，宣传环保理念。活动内容包括垃圾分类宣传、捡拾垃圾、环保手工制作等。欢迎同学们加入，共同守护绿色家园。",
				orgName: "北京科技大学天津学院环保社",
				orgDesc: "成立于2015年，致力于校园及社区环保公益事业，已组织超过50场环保活动。"
			},
			isRegistered: false
		};
	},
	computed: {
		progressPercent() {
			return (this.project.current / this.project.target) * 100;
		}
	},
	methods: {
		handleDonate() {
			// 检查用户是否登录
			const token = uni.getStorageSync('token');
			if (!token) {
				uni.showToast({
					title: '请先登录',
					icon: 'none',
					duration: 2000
				});
				uni.navigateTo({
					url: '/pages/login/login'
				});
				return;
			}
			
			// 调用后端报名接口
			uni.showLoading({ title: '报名中...' });
			registerActivity(this.project.id).then(res => {
				uni.hideLoading();
				if (res.code === 200) {
					this.isRegistered = true;
					uni.showToast({
						title: res.msg || '报名成功！',
						icon: 'success',
						duration: 2000
					});
				} else {
					uni.showToast({
						title: res.msg || '报名失败',
						icon: 'none',
						duration: 2000
					});
				}
			}).catch(err => {
				uni.hideLoading();
				uni.showToast({
					title: '网络错误，请稍后重试',
					icon: 'none',
					duration: 2000
				});
				console.error('报名失败:', err);
			});
		}
	}
};
</script>

<style scoped>
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
		.donate-btn.registered {
			background: #4caf50;
			cursor: not-allowed;
		}
</style>