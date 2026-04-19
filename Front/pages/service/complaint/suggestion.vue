// pages/suggestion/suggestion.vue
<template>
	<view class="suggestion-page">
		<!-- 背景装饰 -->
		<view class="bg-blur"></view>
		<!-- 顶部导航栏 -->
		<view class="nav-bar">
			<view class="back-btn" @click="goBack">
				<text class="back-icon">←</text>
			</view>
			<text class="nav-title">建议反馈</text>
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

		<!-- 表单卡片 -->
		<view class="form-card">
			<!-- 标题 -->
			<view class="form-item">
				<text class="label">建议标题</text>
				<input class="input" v-model="form.title" placeholder="请概括您的建议（必填）" placeholder-class="placeholder" />
			</view>

			<!-- 内容 -->
			<view class="form-item">
				<text class="label">建议内容</text>
				<textarea class="textarea" v-model="form.content" placeholder="请详细描述您的想法或改进方案（必填）"
					placeholder-class="placeholder" />
			</view>

			<!-- 联系方式 -->
			<view class="form-item">
				<text class="label">联系方式</text>
				<input class="input" v-model="form.contact" placeholder="手机号/邮箱（方便我们与您沟通）"
					placeholder-class="placeholder" />
			</view>

			<!-- 图片上传 -->
			<view class="form-item">
				<text class="label">上传图片</text>
				<view class="image-list">
					<view class="image-item" v-for="(img, idx) in images" :key="idx">
						<image :src="img" mode="aspectFill" class="uploaded-img" @click="previewImage(idx)"></image>
						<view class="delete-btn" @click="deleteImage(idx)">×</view>
					</view>
					<view class="add-image" @click="chooseImage" v-if="images.length < 4">
						<text class="add-icon">+</text>
						<text class="add-text">上传图片</text>
					</view>
				</view>
				<text class="tip-text">最多上传4张图片，支持jpg/png格式</text>
			</view>

			<!-- 提交按钮 -->
			<view class="submit-btn" @click="submit">
				<text>提交建议</text>
			</view>
		</view>

		<!-- 底部提示 -->
		<view class="tips">
			<text class="tips-text">* 您的建议是我们进步的动力，感谢您的参与</text>
		</view>
	</view>
</template>

<script>
	import { submitSuggestion } from '@/api/complaint';
	export default {
		data() {
			return {
				currentTab: 'suggestion',
				form: {
					title: '',
					content: '',
					contact: ''
				},
				images: []
			};
		},
		onLoad() {
			this.currentTab = 'suggestion';
		},
		methods: {
			switchTab(tab) {
				if (tab === 'suggestion') return;
				if (tab === 'complaint') {
					uni.redirectTo({
						url: '/pages/service/complaint/complaint'
					});
				} else if (tab === 'record') {
					uni.redirectTo({
						url: '/pages/service/complaint/my'
					});
				}
			},
			chooseImage() {
				uni.chooseImage({
					count: 4 - this.images.length,
					sizeType: ['compressed'],
					sourceType: ['album', 'camera'],
					success: (res) => {
						this.images = [...this.images, ...res.tempFilePaths];
					}
				});
			},
			deleteImage(index) {
				this.images.splice(index, 1);
			},
			previewImage(index) {
				uni.previewImage({
					urls: this.images,
					current: this.images[index]
				});
			},
			submit() {
				if (!this.form.title.trim()) {
					uni.showToast({
						title: '请填写建议标题',
						icon: 'none'
					});
					return;
				}
				if (!this.form.content.trim()) {
					uni.showToast({
						title: '请填写建议内容',
						icon: 'none'
					});
					return;
				}

				const data = {
					title: this.form.title,
					content: this.form.content,
					contact: this.form.contact,
					images: this.images,
					type: 'suggestion'
				};

				uni.showLoading({
					title: '提交中...'
				});
				submitSuggestion(data)
					.then(() => {
						uni.hideLoading();
						uni.showModal({
							title: '提交成功',
							content: '感谢您的建议，我们会认真考虑并改进。',
							showCancel: false,
							success: () => {
								this.form = {
									title: '',
									content: '',
									contact: ''
								};
								this.images = [];
								uni.redirectTo({
									url: '/pages/service/complaint/my'
								});
							}
						});
					})
					.catch(() => {
						uni.hideLoading();
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
	.suggestion-page {
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

	.form-card {
		background: rgba(255, 255, 255, 0.92);
		backdrop-filter: blur(12rpx);
		border-radius: 44rpx;
		padding: 32rpx;
		margin-bottom: 32rpx;
		box-shadow: 0 20rpx 40rpx -16rpx rgba(0, 0, 0, 0.08), 0 0 0 1rpx rgba(107, 140, 66, 0.1);
	}

	.form-item {
		margin-bottom: 32rpx;
	}

	.label {
		font-size: 28rpx;
		color: #4a5e71;
		margin-bottom: 12rpx;
		display: block;
		font-weight: 500;
	}

	.input,
	.textarea {
		width: 100%;
		background: rgba(255, 255, 255, 0.8);
		border-radius: 60rpx;
		padding: 20rpx 28rpx;
		font-size: 30rpx;
		color: #1c2e3a;
		box-shadow: inset 0 1rpx 3rpx rgba(0, 0, 0, 0.02), 0 0 0 1rpx rgba(107, 140, 66, 0.1);
	}

	.textarea {
		border-radius: 32rpx;
		min-height: 200rpx;
	}

	.placeholder {
		color: #bdc7d0;
	}

	.image-list {
		display: flex;
		flex-wrap: wrap;
		gap: 20rpx;
		margin-bottom: 12rpx;
	}

	.image-item {
		position: relative;
		width: 160rpx;
		height: 160rpx;
		border-radius: 24rpx;
		overflow: hidden;
	}

	.uploaded-img {
		width: 100%;
		height: 100%;
	}

	.delete-btn {
		position: absolute;
		top: 6rpx;
		right: 6rpx;
		width: 40rpx;
		height: 40rpx;
		background: rgba(0, 0, 0, 0.6);
		border-radius: 20rpx;
		color: white;
		text-align: center;
		line-height: 36rpx;
		font-size: 32rpx;
		font-weight: bold;
	}

	.add-image {
		width: 160rpx;
		height: 160rpx;
		background: rgba(107, 140, 66, 0.08);
		border-radius: 24rpx;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		border: 1rpx dashed rgba(107, 140, 66, 0.4);
	}

	.add-icon {
		font-size: 56rpx;
		color: #6b8c42;
		line-height: 1;
	}

	.add-text {
		font-size: 22rpx;
		color: #7c8f9e;
		margin-top: 8rpx;
	}

	.tip-text {
		font-size: 22rpx;
		color: #9aabb8;
		display: block;
		margin-top: 8rpx;
	}

	.submit-btn {
		background: linear-gradient(105deg, #6b8c42, #528a34);
		border-radius: 80rpx;
		padding: 28rpx 0;
		display: flex;
		align-items: center;
		justify-content: center;
		font-size: 34rpx;
		font-weight: 700;
		color: white;
		box-shadow: 0 12rpx 28rpx rgba(107, 140, 66, 0.3);
		transition: all 0.2s;
		margin-top: 24rpx;
	}

	.submit-btn:active {
		transform: scale(0.98);
		opacity: 0.9;
	}

	.tips {
		text-align: center;
		margin-top: 20rpx;
	}

	.tips-text {
		font-size: 24rpx;
		color: #9aabb8;
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