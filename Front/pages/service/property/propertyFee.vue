<template>
	<view class="fee-page">
		<!-- 背景装饰 -->
		<view class="bg-blur"></view>

		<!-- 顶部返回栏 -->
		<view class="nav-bar">
			<view class="back-btn" @click="goBack">
				<text class="back-icon">←</text>
			</view>
			<text class="nav-title">物业缴费</text>
			<view class="placeholder"></view>
		</view>

		<!-- 房屋信息卡片 -->
		<view class="house-card">
			<view class="house-icon">🏠</view>
			<view class="house-info">
				<text class="house-name">悦景湾 · 12栋 203室</text>
				<text class="house-owner">业主：张先生</text>
			</view>
		</view>

		<!-- 缴费详情卡片 -->
		<view class="fee-card">
			<view class="fee-header">
				<text class="fee-label">缴费月份</text>
				<text class="fee-month">2025年3月</text>
			</view>
			<view class="fee-divider"></view>
			<view class="fee-item">
				<text>物业管理费</text>
				<text class="fee-amount">¥{{ fixedAmount }}</text>
			</view>
			<view class="fee-item total">
				<text>合计</text>
				<text class="total-amount">¥{{ fixedAmount }}</text>
			</view>
		</view>

		<!-- 支付方式 -->
		<view class="payment-section">
			<view class="section-title">支付方式</view>
			<view class="payment-options">
				<view
					class="payment-item"
					:class="{ active: payMethod === 'wechat' }"
					@click="payMethod = 'wechat'"
				>
					<text class="pay-icon">💬</text>
					<text class="pay-name">微信支付</text>
				</view>
				<view
					class="payment-item"
					:class="{ active: payMethod === 'alipay' }"
					@click="payMethod = 'alipay'"
				>
					<text class="pay-icon">💰</text>
					<text class="pay-name">支付宝</text>
				</view>
			</view>
		</view>

		<!-- 支付按钮 -->
		<view class="pay-btn" @click="handlePay">
			<text>立即支付 ¥{{ fixedAmount }}</text>
		</view>

		<!-- 提示信息 -->
		<view class="tips">
			<text class="tips-text">* 缴费成功后，物业费将实时更新，请留意系统通知</text>
		</view>
	</view>
</template>

<script>
export default {
	data() {
		return {
			fixedAmount: 268.00, // 固定物业费
			payMethod: 'wechat'
		};
	},
	methods: {
		goBack() {
			uni.navigateBack();
		},
		handlePay() {
			uni.showLoading({ title: '正在发起支付...' });
			setTimeout(() => {
				uni.hideLoading();
				uni.showModal({
					title: '提示',
					content: `确认支付 ¥${this.fixedAmount} 元？`,
					success: (res) => {
						if (res.confirm) {
							uni.showToast({ title: '缴费成功', icon: 'success' });
							setTimeout(() => {
								uni.navigateBack();
							}, 1500);
						}
					}
				});
			}, 800);
		}
	}
};
</script>

<style scoped>
.fee-page {
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

/* 顶部导航栏 */
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

/* 房屋信息卡片 */
.house-card {
	background: rgba(255,255,255,0.92);
	backdrop-filter: blur(12rpx);
	border-radius: 44rpx;
	padding: 32rpx;
	margin-bottom: 32rpx;
	display: flex;
	align-items: center;
	gap: 24rpx;
	box-shadow: 0 20rpx 40rpx -16rpx rgba(0,0,0,0.08), 0 0 0 1rpx rgba(107,140,66,0.1);
}

.house-icon {
	font-size: 64rpx;
	width: 88rpx;
	height: 88rpx;
	background: rgba(107,140,66,0.12);
	border-radius: 44rpx;
	display: flex;
	align-items: center;
	justify-content: center;
}

.house-info {
	flex: 1;
}

.house-name {
	font-size: 32rpx;
	font-weight: 700;
	color: #1f2e3a;
	display: block;
	margin-bottom: 8rpx;
}

.house-owner {
	font-size: 26rpx;
	color: #7c8f9e;
}

/* 缴费详情卡片 */
.fee-card {
	background: rgba(255,255,255,0.92);
	backdrop-filter: blur(12rpx);
	border-radius: 44rpx;
	padding: 32rpx;
	margin-bottom: 48rpx;
	box-shadow: 0 20rpx 40rpx -16rpx rgba(0,0,0,0.08), 0 0 0 1rpx rgba(107,140,66,0.1);
}

.fee-header {
	display: flex;
	justify-content: space-between;
	align-items: baseline;
	margin-bottom: 24rpx;
}

.fee-label {
	font-size: 28rpx;
	color: #7c8f9e;
}

.fee-month {
	font-size: 28rpx;
	font-weight: 600;
	color: #6b8c42;
	background: rgba(107,140,66,0.12);
	padding: 6rpx 16rpx;
	border-radius: 60rpx;
}

.fee-divider {
	height: 1rpx;
	background: rgba(107,140,66,0.2);
	margin: 24rpx 0;
}

.fee-item {
	display: flex;
	justify-content: space-between;
	align-items: baseline;
	margin-bottom: 20rpx;
	font-size: 30rpx;
	color: #1f2e3a;
}

.fee-amount {
	font-weight: 600;
	color: #1c2e3a;
}

.total {
	margin-top: 24rpx;
	padding-top: 20rpx;
	border-top: 1rpx dashed rgba(107,140,66,0.2);
	font-weight: 700;
	font-size: 34rpx;
}

.total-amount {
	color: #6b8c42;
	font-size: 40rpx;
	font-weight: 800;
}

/* 支付方式区域 */
.payment-section {
	margin-bottom: 60rpx;
}

.section-title {
	font-size: 32rpx;
	font-weight: 600;
	color: #1f2e3a;
	margin-bottom: 24rpx;
}

.payment-options {
	display: flex;
	gap: 24rpx;
}

.payment-item {
	flex: 1;
	background: rgba(255,255,255,0.85);
	backdrop-filter: blur(8rpx);
	border-radius: 32rpx;
	padding: 24rpx 0;
	display: flex;
	flex-direction: column;
	align-items: center;
	gap: 12rpx;
	transition: all 0.2s;
	box-shadow: 0 4rpx 12rpx rgba(0,0,0,0.02), 0 0 0 1rpx rgba(107,140,66,0.08);
}

.payment-item.active {
	background: rgba(107,140,66,0.12);
	border: 1rpx solid #6b8c42;
	box-shadow: 0 8rpx 20rpx rgba(107,140,66,0.15);
}

.pay-icon {
	font-size: 56rpx;
}

.pay-name {
	font-size: 26rpx;
	font-weight: 500;
	color: #2c4d3a;
}

/* 支付按钮 */
.pay-btn {
	background: linear-gradient(105deg, #6b8c42, #528a34);
	border-radius: 80rpx;
	padding: 32rpx 0;
	display: flex;
	align-items: center;
	justify-content: center;
	font-size: 36rpx;
	font-weight: 700;
	color: white;
	box-shadow: 0 12rpx 28rpx rgba(107,140,66,0.3);
	transition: all 0.2s;
	margin-bottom: 40rpx;
}

.pay-btn:active {
	transform: scale(0.98);
	opacity: 0.9;
}

/* 提示文字 */
.tips {
	text-align: center;
}

.tips-text {
	font-size: 24rpx;
	color: #9aabb8;
}
</style>