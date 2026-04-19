<template>
	<view class="service-page">
		<!-- 装饰背景层 -->
		<view class="bg-blur"></view>
		
		<!-- 头部区域：精致留白 + 微渐变 -->
		<view class="header">
			<text class="title">服务大厅</text>
			<text class="subtitle">智慧生活 · 触手可及</text>
		</view>

		<!-- 水电余额卡片：玻璃质感 + 立体阴影 + 数据可视化动效 -->
		<view class="balance-cards">
			<view class="card water-card" @click="handleRecharge('water')">
				<view class="card-glow"></view>
				<view class="card-icon">💧</view>
				<view class="card-content">
					<view class="card-header">
						<text class="label">剩余水量</text>
						<text class="status normal">正常</text>
					</view>
					<text class="value">{{ waterBalance }}<text class="unit"> 元</text></text>
				</view>
				<view class="recharge-badge">充值</view>
			</view>
			<view class="card electricity-card" @click="handleRecharge('electricity')">
				<view class="card-glow"></view>
				<view class="card-icon">⚡</view>
				<view class="card-content">
					<view class="card-header">
						<text class="label">剩余电量</text>
						<text class="status sufficient">充足</text>
					</view>
					<text class="value">{{ electricityBalance }}<text class="unit"> 元</text></text>
				</view>
				<view class="recharge-badge">充值</view>
			</view>
		</view>

		<!-- 服务网格：微质感 + 悬浮动效 -->
		<view class="service-grid">
			<view
				v-for="(item, index) in services"
				:key="index"
				class="grid-item"
				@click="handleService(item)"
			>
				<view class="icon-bg">
					<text class="icon-text">{{ item.iconText }}</text>
				</view>
				<text class="name">{{ item.name }}</text>
			</view>
		</view>

		<!-- 我的待办：极简分割 + 优雅按钮 -->
		<view class="todo-section">
			<view class="section-title">
				<text>我的待办</text>
				<text class="badge-count">2</text>
			</view>
			<view class="todo-list">
				<view class="todo-item" @click="handleTodo('visitor')">
					<view class="todo-icon-wrapper">
						<text class="todo-icon">👥</text>
					</view>
					<view class="todo-info">
						<text class="todo-name">访客预约待签入</text>
						<text class="todo-desc">张先生 · 预计今日 14:00 到访</text>
					</view>
					<view class="todo-btn signin-btn" @click.stop="signIn">签入</view>
				</view>
				<view class="todo-item" @click="handleTodo('payment')">
					<view class="todo-icon-wrapper">
						<text class="todo-icon">💰</text>
					</view>
					<view class="todo-info">
						<text class="todo-name">费用缴纳提醒</text>
						<text class="todo-desc">3月份物业管理费已出账</text>
					</view>
					<view class="todo-btn pay-btn" @click.stop="goPay">去支付</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
import { getElectricityBalance } from '@/api/electricity.js'
import { request } from '@/utils/request.js'

export default {
	data() {
		return {
			waterBalance: 0,
			electricityBalance: 0,
			services: [
				{ name: '物业缴费', iconText: '🏢', path: '' },
				{ name: '访客邀请', iconText: '👥', path: '' },
				{ name: '场地预约', iconText: '🏟️', path: '' },
				{ name: '通知提醒', iconText: '🔔', path: '' },
				{ name: '快递驿站', iconText: '📦', path: '' },
				{ name: '投诉建议', iconText: '📝', path: '' },
				{ name: '水费充值', iconText: '💧', path: '' },
				{ name: '电费充值', iconText: '⚡', path: '' },
				{ name: '报事报修', iconText: '🔧', path: '' }
			]
		};
	},
	onShow() {
	    this.fetchBalances();
	  },
	methods: {
		async fetchBalances() {
			try {
				// 获取水费余额
				const waterRes = await request({ url: '/water/balance' })
				// 根据您 request.js 的封装，这里如果是成功，返回的已经是 res.data.data 或者是 res 本身
				// 需要根据实际返回结构调整
				if (waterRes && waterRes.code === 200) {
					this.waterBalance = waterRes.data.balance
				} else if (waterRes && waterRes.balance !== undefined) {
					// 兼容 request.js 直接 resolve(res.data.data) 的情况
					this.waterBalance = waterRes.balance
				}
				
				// 获取电费余额
				const elecRes = await getElectricityBalance()
				if (elecRes && elecRes.code === 200) {
					this.electricityBalance = elecRes.data.balance
				} else if (elecRes && elecRes.balance !== undefined) {
					this.electricityBalance = elecRes.balance
				}
			} catch (error) {
				console.error('获取余额失败:', error)
			}
		},
		 
		handleService(item) {
		  if (item.name === '物业缴费') {
		    uni.navigateTo({ url: '/pages/service/property/propertyFee' });
		    return;
		  }
		  if (item.name === '访客邀请') {
		    uni.navigateTo({ url: '/pages/service/visitor/visitorInvite' });
		    return;
		  }
		  if (item.name === '场地预约') {
		    uni.navigateTo({ url: '/pages/service/site/venueBooking' });
		    return;
		  }
		  
		  if (item.name === '快递驿站') {
		    uni.navigateTo({ url: '/pages/service/station/expressStation' });
		    return;
		  }
		  if (item.name === '电费充值') {
		    uni.navigateTo({ url: '/pages/service/electricity/electricity' });
		    return;
		  }
		  if (item.name === '水费充值') {
		    uni.navigateTo({ url: '/pages/service/water/water' });
		    return;
		  }
		  if (item.name === '投诉建议') {
		      uni.navigateTo({ url: '/pages/service/complaint/complaint' });
		      return;
		    }
			if (item.name === '报事报修') {
			    uni.navigateTo({ url: '/pages/service/repair/repair' });
			    return;
			  }
			  if (item.name === '通知提醒') {
			    uni.navigateTo({ url: '/pages/service/notice/notification' });
			    return;
			  }
		  // 其他服务...
		  uni.showToast({
		    title: `${item.name}功能开发中`,
		    icon: 'none'
		  });
		},
		handleTodo(type) {
			uni.showToast({ title: `待办项点击`, icon: 'none' });
		},
		signIn() {
			uni.showToast({ title: '签到成功', icon: 'success' });
		},
		goPay() {
			uni.showToast({ title: '跳转支付', icon: 'none' });
		},
		handleRecharge(type) {
		  if (type === 'water') {
		    uni.navigateTo({ url: '/pages/service/water/water' });
		  } else if (type === 'electricity') {
		    uni.navigateTo({ url: '/pages/service/electricity/electricity' });
		  } else {
		    uni.showToast({ title: '功能开发中', icon: 'none' });
		  }
		}
	}
};
</script>

<style scoped>
.service-page {
	position: relative;
	min-height: 100vh;
	background: #f6f9fc;
	padding: 0 32rpx 80rpx;
	overflow-x: hidden;
}

/* 动态模糊背景层，增加深度 */
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

/* 头部区域 */
.header {
	position: relative;
	z-index: 2;
	padding-top: 80rpx;
	padding-bottom: 40rpx;
}

.title {
	font-size: 64rpx;
	font-weight: 700;
	background: linear-gradient(115deg, #1c3e2b 0%, #2b6e3f 100%);
	background-clip: text;
	-webkit-background-clip: text;
	color: transparent;
	letter-spacing: -1rpx;
}

.subtitle {
	font-size: 28rpx;
	color: #7e8d9e;
	margin-top: 12rpx;
	font-weight: 450;
	letter-spacing: 0.3rpx;
}

/* 余额卡片 */
.balance-cards {
	display: flex;
	gap: 24rpx;
	margin-bottom: 48rpx;
	position: relative;
	z-index: 2;
}

.card {
	flex: 1;
	background: rgba(255, 255, 255, 0.92);
	backdrop-filter: blur(12rpx);
	border-radius: 44rpx;
	padding: 28rpx 24rpx 24rpx;
	box-shadow: 0 20rpx 40rpx -16rpx rgba(0, 0, 0, 0.08), 0 0 0 1rpx rgba(107, 140, 66, 0.08);
	transition: all 0.35s cubic-bezier(0.2, 0.9, 0.4, 1.1);
	position: relative;
	overflow: hidden;
}

.card:active {
	transform: scale(0.97);
	box-shadow: 0 12rpx 28rpx -12rpx rgba(0, 0, 0, 0.12);
}

.card-glow {
	position: absolute;
	top: -20%;
	left: -20%;
	width: 140%;
	height: 140%;
	background: radial-gradient(circle, rgba(255,255,240,0.4) 0%, rgba(255,255,255,0) 70%);
	opacity: 0;
	transition: opacity 0.4s;
}

.card:active .card-glow {
	opacity: 0.6;
}

.water-card {
	border-left: 6rpx solid #48b87c;
}

.electricity-card {
	border-left: 6rpx solid #f6a85b;
}

.card-icon {
	font-size: 52rpx;
	margin-bottom: 20rpx;
	width: 72rpx;
	height: 72rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	background: rgba(72, 184, 124, 0.12);
	border-radius: 36rpx;
}

.electricity-card .card-icon {
	background: rgba(246, 168, 91, 0.12);
}

.card-content {
	flex: 1;
}

.card-header {
	display: flex;
	justify-content: space-between;
	align-items: baseline;
	margin-bottom: 16rpx;
	flex-wrap: wrap;
}

.label {
	font-size: 28rpx;
	font-weight: 500;
	color: #4a5e71;
}

.status {
	font-size: 22rpx;
	padding: 4rpx 14rpx;
	border-radius: 40rpx;
	font-weight: 500;
	background: rgba(0,0,0,0.04);
}

.status.normal {
	color: #2b8c5e;
	background: rgba(43, 140, 94, 0.12);
}

.status.sufficient {
	color: #e6892e;
	background: rgba(230, 137, 46, 0.12);
}

.value {
	font-size: 56rpx;
	font-weight: 800;
	color: #1c2e3a;
	letter-spacing: -1rpx;
}

.unit {
	font-size: 26rpx;
	font-weight: 500;
	color: #8b9bae;
	margin-left: 6rpx;
}

.recharge-badge {
	align-self: flex-end;
	margin-top: 18rpx;
	background: rgba(107, 140, 66, 0.12);
	padding: 10rpx 20rpx;
	border-radius: 60rpx;
	font-size: 24rpx;
	font-weight: 600;
	color: #6b8c42;
	transition: all 0.2s;
	text-align: center;
	width: fit-content;
}

.electricity-card .recharge-badge {
	background: rgba(246, 168, 91, 0.14);
	color: #e6892e;
}

/* 服务网格 */
.service-grid {
	display: flex;
	flex-wrap: wrap;
	justify-content: space-between;
	margin-bottom: 48rpx;
	position: relative;
	z-index: 2;
}

.grid-item {
	width: calc(33.33% - 18rpx);
	background: rgba(255, 255, 255, 0.88);
	backdrop-filter: blur(8rpx);
	border-radius: 32rpx;
	padding: 32rpx 0 28rpx;
	margin-bottom: 24rpx;
	display: flex;
	flex-direction: column;
	align-items: center;
	transition: all 0.25s ease;
	box-shadow: 0 6rpx 18rpx rgba(0, 0, 0, 0.02), 0 0 0 1rpx rgba(107, 140, 66, 0.06);
}

.grid-item:active {
	transform: translateY(-6rpx);
	background: white;
	box-shadow: 0 20rpx 30rpx -14rpx rgba(107, 140, 66, 0.2), 0 0 0 1rpx rgba(107, 140, 66, 0.2);
}

.icon-bg {
	width: 100rpx;
	height: 100rpx;
	background: linear-gradient(135deg, #f4f9f0 0%, #eaf2e3 100%);
	border-radius: 52rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	margin-bottom: 20rpx;
	transition: all 0.25s;
	box-shadow: inset 0 1rpx 2rpx rgba(255,255,255,0.8), 0 4rpx 12rpx rgba(0,0,0,0.02);
}

.grid-item:active .icon-bg {
	background: linear-gradient(135deg, #e2f0d9 0%, #d4e6c8 100%);
	transform: scale(0.96);
}

.icon-text {
	font-size: 56rpx;
	line-height: 1;
}

.name {
	font-size: 26rpx;
	font-weight: 500;
	color: #2a4458;
	letter-spacing: 0.3rpx;
}

/* 待办区域 */
.todo-section {
	position: relative;
	z-index: 2;
}

.section-title {
	display: flex;
	justify-content: space-between;
	align-items: baseline;
	font-size: 36rpx;
	font-weight: 700;
	color: #1c2e3a;
	margin-bottom: 28rpx;
	padding-left: 8rpx;
}

.badge-count {
	background: #eef3e8;
	padding: 6rpx 18rpx;
	border-radius: 40rpx;
	font-size: 24rpx;
	font-weight: 600;
	color: #6b8c42;
}

.todo-list {
	display: flex;
	flex-direction: column;
	gap: 24rpx;
}

.todo-item {
	background: rgba(255, 255, 255, 0.9);
	backdrop-filter: blur(12rpx);
	border-radius: 40rpx;
	padding: 28rpx 28rpx;
	display: flex;
	align-items: center;
	gap: 24rpx;
	transition: all 0.25s;
	box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.02), 0 0 0 1rpx rgba(107, 140, 66, 0.06);
}

.todo-item:active {
	transform: scale(0.98);
	background: white;
	box-shadow: 0 8rpx 28rpx rgba(0, 0, 0, 0.08);
}

.todo-icon-wrapper {
	width: 88rpx;
	height: 88rpx;
	background: linear-gradient(145deg, #f5f9f0 0%, #edf4e6 100%);
	border-radius: 44rpx;
	display: flex;
	align-items: center;
	justify-content: center;
}

.todo-icon {
	font-size: 52rpx;
}

.todo-info {
	flex: 1;
}

.todo-name {
	font-size: 30rpx;
	font-weight: 700;
	color: #1c2e3a;
	display: block;
	margin-bottom: 10rpx;
}

.todo-desc {
	font-size: 26rpx;
	color: #8092a2;
	display: block;
	line-height: 1.4;
}

.todo-btn {
	padding: 14rpx 36rpx;
	border-radius: 60rpx;
	font-size: 26rpx;
	font-weight: 600;
	transition: all 0.2s;
	box-shadow: 0 2rpx 6rpx rgba(0, 0, 0, 0.05);
}

.todo-btn:active {
	transform: scale(0.94);
	opacity: 0.85;
}

.signin-btn {
	background: linear-gradient(105deg, #6b8c42 0%, #528a34 100%);
	color: white;
	box-shadow: 0 6rpx 14rpx rgba(107, 140, 66, 0.3);
}

.pay-btn {
	background: linear-gradient(105deg, #f7b05e 0%, #e67e22 100%);
	color: white;
	box-shadow: 0 6rpx 14rpx rgba(230, 126, 34, 0.25);
}
</style>