<template>
	<view class="recharge-page water-theme">
		<!-- 背景装饰 -->
		<view class="bg-blur"></view>
		<!-- 顶部返回栏 -->
		<view class="nav-bar">
			<view class="back-btn" @click="goBack">
				<text class="back-icon">←</text>
			</view>
			<text class="nav-title">水费充值</text>
			<view class="placeholder"></view>
		</view>
		<!-- 余额卡片 -->
		<view class="balance-card">
			<view class="balance-label">当前余额</view>
			<view class="balance-value">{{ balance }}<text class="unit"> 元</text></view>
			<view class="balance-tip">可用水量 {{ waterAmount }} 吨</view>
		</view>
		<!-- 充值金额输入 -->
		<view class="input-section">
			<view class="section-title">充值金额</view>
			<view class="amount-input-wrapper">
				<text class="currency">¥</text>
				<input
					class="amount-input"
					type="number"
					v-model="amount"
					placeholder="请输入金额"
					placeholder-class="input-placeholder"
				/>
			</view>
		</view>
		<!-- 快捷金额选项 -->
		<view class="quick-section">
			<view class="section-title">快捷充值</view>
			<view class="quick-options">
				<view
					v-for="item in quickAmounts"
					:key="item"
					class="quick-item"
					:class="{ active: amount == item }"
					@click="amount = item"
				>
					¥{{ item }}
				</view>
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
		<!-- 充值按钮 -->
		<view class="recharge-btn" @click="handleRecharge" :class="{ disabled: isLoading }">
			<text>{{ isLoading ? '处理中...' : '立即充值' }}</text>
		</view>
		<!-- 充值说明 -->
		<view class="tips">
			<text class="tips-text">* 充值成功后，水量将实时到账，请留意短信通知</text>
		</view>
	</view>
</template>

<script>
// 1. 引入你封装好的 request.js (请确保路径正确)
import { request } from '@/utils/request.js';

export default {
	data() {
		return {
			balance: 0, // 改为0，从接口获取
			waterAmount: 0, // 改为0，从接口获取
			amount: '', // 充值金额
			quickAmounts: [20, 50, 100, 200],
			payMethod: 'wechat', // wechat / alipay
			isLoading: false // 防重复点击状态
		};
	},
	onLoad() {
		// 页面加载时获取余额
		this.fetchBalance();
	},
	methods: {
		goBack() {
			uni.navigateBack();
		},

		// 2. 新增：获取余额的接口请求
		async fetchBalance() {
			try {
				// 注意：这里的 '/water/balance' 请替换为你后端的真实接口地址
				const res = await request({ url: '/water/balance' });
				
				// 假设后端返回格式为 { code: 200, data: { balance: 12.5, waterAmount: 12.5 } }
				// 请根据你后端的实际返回结构修改下面的赋值
				if (res && res.code === 200) {
					this.balance = res.data.balance;
					this.waterAmount = res.data.waterAmount;
				} else if (res && res.balance !== undefined) {
					// 兼容 request.js 直接 resolve(res.data.data) 的情况
					this.balance = res.balance;
					this.waterAmount = res.waterAmount;
				} else {
					uni.showToast({ title: res.msg || '获取余额失败', icon: 'none' });
				}
			} catch (error) {
				console.error(error);
				uni.showToast({ title: '网络错误，请重试', icon: 'none' });
			}
		},

		// 3. 修改：充值逻辑对接后端
		async handleRecharge() {
			const money = parseFloat(this.amount);
			
			// 基础校验
			if (!money || money <= 0) {
				uni.showToast({ title: '请输入有效金额', icon: 'none' });
				return;
			}
			
			// 防重复点击
			if (this.isLoading) return;
			this.isLoading = true;
			uni.showLoading({ title: '正在处理...' });

			try {
				// 注意：这里的 '/water/recharge' 请替换为你后端的真实接口地址
				const res = await request({
					url: '/water/recharge',
					method: 'POST',
					data: {
						amount: money,
						payMethod: this.payMethod
					}
				});

				uni.hideLoading();
				
				if (res && res.code === 200) {
					this.balance = Number(this.balance) + money;
					uni.showToast({ title: '充值成功', icon: 'success' });
					// 充值成功后刷新一下余额
					this.fetchBalance();
					this.amount = ''; // 清空输入框
					
					setTimeout(() => {
						uni.navigateBack();
					}, 1500);
				} else if (res && res === '充值成功') { // 兼容直接返回 data 的情况
					this.balance = Number(this.balance) + money;
					uni.showToast({ title: '充值成功', icon: 'success' });
					this.fetchBalance();
					this.amount = '';
					setTimeout(() => { uni.navigateBack(); }, 1500);
				} else {
					uni.showToast({ title: res.msg || '充值失败', icon: 'none' });
				}
			} catch (error) {
				uni.hideLoading();
				console.error(error);
				uni.showToast({ title: '充值失败，请重试', icon: 'none' });
			} finally {
				this.isLoading = false;
			}
		}
	}
};
</script>

<style scoped>
/* 这里的样式保持原样，我只加了一个按钮禁用态的样式 */
.recharge-page {
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
/* 余额卡片 */
.balance-card {
	background: rgba(255,255,255,0.92);
	backdrop-filter: blur(12rpx);
	border-radius: 44rpx;
	padding: 40rpx 32rpx;
	margin-top: 20rpx;
	margin-bottom: 48rpx;
	box-shadow: 0 20rpx 40rpx -16rpx rgba(0,0,0,0.08), 0 0 0 1rpx rgba(107,140,66,0.1);
	text-align: center;
}
.balance-label {
	font-size: 28rpx;
	color: #7c8f9e;
	margin-bottom: 16rpx;
}
.balance-value {
	font-size: 72rpx;
	font-weight: 800;
	color: #2b6e3f;
	letter-spacing: -1rpx;
}
.unit {
	font-size: 32rpx;
	font-weight: 500;
	color: #8b9bae;
}
.balance-tip {
	font-size: 26rpx;
	color: #8b9bae;
	margin-top: 16rpx;
}
/* 通用区域样式 */
.section-title {
	font-size: 32rpx;
	font-weight: 600;
	color: #1f2e3a;
	margin-bottom: 24rpx;
}
/* 金额输入 */
.input-section {
	margin-bottom: 48rpx;
}
.amount-input-wrapper {
	background: rgba(255,255,255,0.9);
	backdrop-filter: blur(8rpx);
	padding: 20rpx 32rpx;
	display: flex;
	align-items: center;
	box-shadow: 0 4rpx 12rpx rgba(0,0,0,0.02), 0 0 0 1rpx rgba(107,140,66,0.1);
}
.currency {
	font-size: 48rpx;
	font-weight: 600;
	color: #6b8c42;
	margin-right: 16rpx;
}
.amount-input {
	flex: 1;
	font-size: 44rpx;
	font-weight: 500;
	color: #1c2e3a;
}
.input-placeholder {
	color: #bdc7d0;
	font-size: 36rpx;
}
/* 快捷金额选项 */
.quick-section {
	margin-bottom: 48rpx;
}
.quick-options {
	display: flex;
	flex-wrap: wrap;
	gap: 20rpx;
}
.quick-item {
	background: rgba(255,255,255,0.8);
	backdrop-filter: blur(8rpx);
	padding: 18rpx 32rpx;
	border-radius: 60rpx;
	font-size: 30rpx;
	font-weight: 500;
	color: #3c5a3e;
	transition: all 0.2s;
	box-shadow: 0 2rpx 6rpx rgba(0,0,0,0.02), 0 0 0 1rpx rgba(107,140,66,0.08);
}
.quick-item.active {
	background: linear-gradient(105deg, #6b8c42, #528a34);
	color: white;
	box-shadow: 0 6rpx 14rpx rgba(107,140,66,0.3);
}
/* 支付方式 */
.payment-section {
	margin-bottom: 60rpx;
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
/* 充值按钮 */
.recharge-btn {
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
.recharge-btn:active {
	transform: scale(0.98);
	opacity: 0.9;
}
/* 新增：按钮禁用态样式 */
.recharge-btn.disabled {
	opacity: 0.6;
	pointer-events: none;
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
