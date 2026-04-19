<template>
	<view class="profile-page">
		<!-- 头部用户卡片 -->
		<view class="user-card">
			<view class="user-header">
				<view class="avatar" :style="{ backgroundColor: avatarColor }">
					<text class="avatar-text">{{ userName.charAt(0) }}</text>
				</view>
				<view class="user-info">
					<text class="name">{{ userName }}</text>
					<view class="badge">
						<text class="tag">认证业主</text>
						<text class="id">ID:{{ userId }}</text>
					</view>
				</view>
			</view>
		</view>

		<!-- 三个数据卡片 -->
		<view class="stats-row">
			<view class="stat-item" @click="goPoints">
				<text class="stat-value">{{ points }}</text>
				<text class="stat-label">我的积分</text>
			</view>
			<view class="stat-item" @click="goMedalHall">
				<text class="stat-value">{{ medalCount }}</text>
				<text class="stat-label">勋章馆</text>
			</view>
			<view class="stat-item" @click="goCredit">
				<text class="stat-value">{{ creditScore }}</text>
				<text class="stat-label">信用分</text>
			</view>
		</view>

		<!-- 功能菜单列表 -->
		<view class="menu-list">
			<view class="menu-item" @click="goAccount">
				<view class="menu-left">
					<text class="menu-icon">🏦</text>
					<text class="menu-name">账户中心</text>
				</view>
				<text class="menu-arrow">></text>
			</view>
			<view class="menu-item" @click="goHouse">
				<view class="menu-left">
					<text class="menu-icon">🏠</text>
					<text class="menu-name">我的房屋</text>
				</view>
				<text class="menu-desc">{{ houseAddress }}</text>
				<text class="menu-arrow">></text>
			</view>
			<view class="menu-item" @click="goPayment">
				<view class="menu-left">
					<text class="menu-icon">💳</text>
					<text class="menu-name">支付中心</text>
				</view>
				<text class="menu-desc">修改支付密码与费率查询</text>
				<text class="menu-arrow">></text>
			</view>
			<view class="menu-item" @click="goMedal">
				<view class="menu-left">
					<text class="menu-icon">🎖️</text>
					<text class="menu-name">我的勋章</text>
				</view>
				<text class="menu-desc">已获得“环保卫士”等3个勋章</text>
				<text class="menu-arrow">></text>
			</view>
			<view class="menu-item" @click="goRepair">
				<view class="menu-left">
					<text class="menu-icon">🔧</text>
					<text class="menu-name">报修单据</text>
				</view>
				<text class="menu-desc">目前有1个正在处理中</text>
				<text class="menu-arrow">></text>
			</view>
			<view class="menu-item" @click="goSettings">
				<view class="menu-left">
					<text class="menu-icon">⚙️</text>
					<text class="menu-name">系统设置</text>
				</view>
				<text class="menu-desc">通知推送与隐私管理</text>
				<text class="menu-arrow">></text>
			</view>
		</view>

		<!-- 服务卡片 -->
		<view class="service-card" @click="goCarWash">
			<view class="service-left">
				<!-- 美化后的图片，仅添加圆角 -->
				<image class="service-icon-img" src="/static/hui.png" mode="aspectFit"></image>
				<view class="service-info">
					<text class="service-name">学习Java哪家强</text>
					<text class="service-sub">山东高和找杨师</text>
				</view>
			</view>
			<text class="service-btn">立即报名</text>
		</view>

		<!-- 退出登录 -->
		<view class="logout" @click="logout">
			<text>退出登录</text>
		</view>

		<!-- 底部链接与版本 -->
		<view class="footer">
			<view class="links">
				<text @click="goTerms">服务条款</text>
				<text class="divider">|</text>
				<text @click="goPrivacy">隐私协议</text>
			</view>
			<text class="version">SYSTEM VERSION 2.4.0</text>
		</view>
	</view>
</template>

<script>
export default {
	data() {
		return {
			userName: '陈宇浩',
			userId: '852703',
			avatarColor: '#6b8c42',
			points: 1520,
			medalCount: 8,
			creditScore: 'AAA',
			houseAddress: '阳光花园8栋1205室'
		};
	},
	methods: {
		goPoints() {
			uni.showToast({ title: '我的积分', icon: 'none' });
		},
		goMedalHall() {
			uni.showToast({ title: '勋章馆', icon: 'none' });
		},
		goCredit() {
			uni.showToast({ title: '信用分', icon: 'none' });
		},
		goAccount() {
			uni.showToast({ title: '账户中心', icon: 'none' });
		},
		goHouse() {
			uni.navigateTo({ url: '/pages/house/house' });
		},
		goPayment() {
			uni.showToast({ title: '支付中心', icon: 'none' });
		},
		goMedal() {
			uni.showToast({ title: '我的勋章', icon: 'none' });
		},
		goRepair() {
			uni.showToast({ title: '报修单据', icon: 'none' });
		},
		goSettings() {
			uni.showToast({ title: '系统设置', icon: 'none' });
		},
		goCarWash() {
			uni.showToast({ title: '精品洗车服务', icon: 'none' });
		},
		goTerms() {
			uni.showToast({ title: '服务条款', icon: 'none' });
		},
		goPrivacy() {
			uni.showToast({ title: '隐私协议', icon: 'none' });
		},
		logout() {
			uni.showModal({
				title: '提示',
				content: '确定退出登录吗？',
				success: res => {
					if (res.confirm) {
						uni.showToast({ title: '已退出', icon: 'none' });
					}
				}
			});
		}
	}
};
</script>

<style scoped>
.profile-page {
	background-color: #f5f7fa;
	min-height: 100vh;
	padding: 30rpx 30rpx 60rpx;
}

/* 用户卡片 */
.user-card {
	background-color: #fff;
	border-radius: 32rpx;
	padding: 40rpx 30rpx;
	margin-bottom: 30rpx;
	box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.04);
}

.user-header {
	display: flex;
	align-items: center;
}

.avatar {
	width: 120rpx;
	height: 120rpx;
	border-radius: 50%;
	display: flex;
	align-items: center;
	justify-content: center;
	margin-right: 24rpx;
}

.avatar-text {
	font-size: 56rpx;
	font-weight: bold;
	color: #fff;
}

.user-info {
	flex: 1;
}

.name {
	font-size: 40rpx;
	font-weight: bold;
	color: #1f2d3d;
	display: block;
	margin-bottom: 12rpx;
}

.badge {
	display: flex;
	align-items: center;
	gap: 16rpx;
}

.tag {
	font-size: 24rpx;
	color: #6b8c42;
	background-color: #eef5e6;
	padding: 6rpx 16rpx;
	border-radius: 30rpx;
}

.id {
	font-size: 24rpx;
	color: #8a9aa8;
}

/* 三数据卡片 */
.stats-row {
	display: flex;
	gap: 24rpx;
	margin-bottom: 30rpx;
}
/* 图片美化：圆角 + 尺寸 */
.service-icon-img {
	width: 80rpx;        /* 根据原图标大小设置，可调整 */
	height: 80rpx;
	border-radius: 20rpx; /* 添加圆角 */
}

.stat-item {
	flex: 1;
	background-color: #fff;
	border-radius: 24rpx;
	padding: 32rpx 0;
	display: flex;
	flex-direction: column;
	align-items: center;
	box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.04);
}

.stat-value {
	font-size: 44rpx;
	font-weight: bold;
	color: #1f2d3d;
	margin-bottom: 12rpx;
}

.stat-label {
	font-size: 26rpx;
	color: #8a9aa8;
}

/* 菜单列表 */
.menu-list {
	background-color: #fff;
	border-radius: 32rpx;
	margin-bottom: 30rpx;
	overflow: hidden;
	box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.04);
}

.menu-item {
	display: flex;
	align-items: center;
	justify-content: space-between;
	padding: 32rpx 30rpx;
	border-bottom: 1rpx solid #f0f2f5;
}

.menu-item:last-child {
	border-bottom: none;
}

.menu-left {
	display: flex;
	align-items: center;
	gap: 20rpx;
	flex: 1;
}

.menu-icon {
	font-size: 40rpx;
}

.menu-name {
	font-size: 30rpx;
	color: #1f2d3d;
	font-weight: 500;
}

.menu-desc {
	font-size: 26rpx;
	color: #8a9aa8;
	margin-right: 20rpx;
	flex: 1;
	text-align: right;
}

.menu-arrow {
	font-size: 32rpx;
	color: #ccc;
}

/* 服务卡片 */
.service-card {
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
	border-radius: 32rpx;
	padding: 32rpx 30rpx;
	margin-bottom: 40rpx;
	display: flex;
	align-items: center;
	justify-content: space-between;
	color: #fff;
}
.service-sub {
	font-size: 30rpx;
	font-weight: bold;
	margin-bottom: 8rpx;
	text-align: center;        /* 确保文本居中 */
}

.service-left {
	display: flex;
	align-items: center;
	gap: 20rpx;
	flex: 1;
}

.service-icon {
	font-size: 56rpx;
}

.service-info {
	flex: 1;
	display: flex;
	flex-direction: column;
	justify-content: center;   /* 垂直居中 */
	align-items: center;       /* 水平居中 */
}

.service-name {
	font-size: 30rpx;
	font-weight: bold;
	margin-bottom: 8rpx;
	text-align: center;        /* 确保文本居中 */
}

.service-desc {
	font-size: 24rpx;
	opacity: 0.8;
}

.service-btn {
	background-color: rgba(255, 255, 255, 0.2);
	padding: 16rpx 28rpx;
	border-radius: 60rpx;
	font-size: 26rpx;
	font-weight: 500;
	white-space: nowrap;
}

/* 退出登录 */
.logout {
	background-color: #fff;
	border-radius: 60rpx;
	text-align: center;
	padding: 28rpx;
	margin-bottom: 30rpx;
	color: #ff6b6b;
	font-size: 30rpx;
	font-weight: 500;
	box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.04);
}

/* 底部 */
.footer {
	text-align: center;
}

.links {
	display: flex;
	justify-content: center;
	gap: 30rpx;
	margin-bottom: 20rpx;
}

.links text {
	font-size: 26rpx;
	color: #8a9aa8;
}

.divider {
	color: #ccc;
}

.version {
	font-size: 24rpx;
	color: #b0bec5;
}
</style>