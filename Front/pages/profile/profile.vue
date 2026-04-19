<template>
	<view class="profile-page">
		<!-- 顶部渐变头部 -->
		<view class="profile-header">
			<view class="header-content">
				<view class="avatar-wrap">
					<view class="avatar" :style="{ backgroundColor: avatarColor }">
						<text class="avatar-text">{{ userName.charAt(0) }}</text>
					</view>
					<view class="avatar-verified">✓</view>
				</view>
				<view class="user-info">
					<text class="name">{{ userName }}</text>
					<view class="badge">
						<text class="tag">认证业主</text>
						<text class="id">ID: {{ userId }}</text>
					</view>
				</view>
			</view>
			<!-- 数据统计栏 -->
			<view class="stats-bar">
				<view class="stat-item" @click="goPoints()">
					<text class="stat-value">{{ points }}</text>
					<text class="stat-label">积分</text>
				</view>
				<view class="stat-divider"></view>
				<view class="stat-item" @click="goMedalHall()">
					<text class="stat-value">{{ hall }}</text>
					<text class="stat-label">勋章</text>
				</view>
				<view class="stat-divider"></view>
				<view class="stat-item" @click="goRepair()">
					<text class="stat-value">管理</text>
					<text class="stat-label">报修单</text>
				</view>
			</view>
		</view>

		<!-- 功能菜单列表 -->
		<view class="menu-list">
			<view class="menu-item" @click="goAccount()">
				<view class="menu-left">
					<view class="menu-icon-wrap" style="background:#E8F0FE;">
						<text class="menu-icon">🏦</text>
					</view>
					<text class="menu-name">账户中心</text>
				</view>
				<text class="menu-arrow">›</text>
			</view>
			<view class="menu-item" @click="goHouse()">
				<view class="menu-left">
					<view class="menu-icon-wrap" style="background:#E3F2FD;">
						<text class="menu-icon">🏠</text>
					</view>
					<text class="menu-name">我的房屋</text>
				</view>
				<view class="menu-right-group">
					<text class="menu-desc">{{ houseAddress }}</text>
					<text class="menu-arrow">›</text>
				</view>
			</view>
			<view class="menu-item" @click="goRepair()">
				<view class="menu-left">
					<view class="menu-icon-wrap" style="background:#E8EAF6;">
						<text class="menu-icon">🔧</text>
					</view>
					<text class="menu-name">报修单据</text>
				</view>
				<text class="menu-arrow">›</text>
			</view>
			<view class="menu-item" @click="goSettings()">
				<view class="menu-left">
					<view class="menu-icon-wrap" style="background:#F0F2F5;">
						<text class="menu-icon">⚙️</text>
					</view>
					<text class="menu-name">系统设置</text>
				</view>
				<view class="menu-right-group">
					<text class="menu-desc">通知推送与隐私管理</text>
					<text class="menu-arrow">›</text>
				</view>
			</view>
		</view>

		<!-- 服务卡片 -->
		<view class="service-card" @click="goCarWash()">
			<view class="service-deco-circle"></view>
			<view class="service-left">
				<image class="service-icon-img" src="/static/hui.png" mode="aspectFit"></image>
				<view class="service-info">
					<text class="service-name">学习Java哪家强</text>
					<text class="service-sub">山东高合找杨师</text>
				</view>
			</view>
			<text class="service-btn" @click.stop="goToSignUp()">立即报名</text>
		</view>

		<!-- 退出登录 -->
		<view class="logout" @click="logout()">
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
	import request from '../../api/request';
	import config from '@/api/config.js';
	export default {
		data() {
			return {
				userName: '',
				userId: '',
				avatarColor: '#6b8c42',
				points: '0',
				hall: '0',
				houseAddress: ''
			};
		},
		onShow() {
			// 从 userInfo 对象（login.vue 存入）或独立 username key（login2.vue 存入）立即同步填充
			const stored = uni.getStorageSync('userInfo') || {};
			this.userName = stored.username || stored.userName || stored.nickname || stored.name || '';
			this.userId   = stored.userId   || stored.id       || '';
			// 再异步刷新完整用户信息和积分
			this.fetchUserInfo(true);
			this.fetchPoints();
			this.getHall();
		},
		methods: {
			/**
			 * 获取用户信息的核心方法
			 * @param {boolean} silent 是否静默请求（不显示成功/失败的toast，只显示错误）
			 */
			fetchUserInfo(silent = false) {
				// 与账户中心保持一致：从独立 key 读取 username
				const token = uni.getStorageSync('community_token');
				const stored = uni.getStorageSync('userInfo') || {};
				const username = uni.getStorageSync('username') || stored.username || stored.userName || '';

				if (!username) {
					console.warn('未获取到登录用户名，跳过刷新');
					return;
				}

				// 非静默模式下显示加载中
				if (!silent) {
					uni.showLoading({
						title: '获取用户信息中...',
						mask: true
					});
				}

				console.log("👉 发起用户信息GET请求, username=", username);

				uni.request({
					url: config.BASE_URL + "/user/getByUsername?username=" + username,
					method: "GET",
					header: {
						'Content-Type': 'application/json',
						'Authorization': token ? `Bearer ${token}` : '',
					},
					
					success: (res) => {
						console.log("fetchUserInfo" +JSON.stringify(res));
						console.log("✅ 获取用户信息成功", res);
						// 兼容多种后端返回格式：res.data.data 或 res.data
						let userData = res.data;
						if (res.data && res.data.code === 200) {
							userData = res.data.data || res.data;
						}

						// 更新页面数据，字段名兼容处理
						if (userData) {
							// 更新用户名：优先显示登录名
							this.userName = userData.userName || userData.username || userData.nickName || userData.nickname || userData.name || '';

							// 更新用户ID
							this.userId = userData.userId || userData.id || '';

							// 更新积分（支持 points / point / pointBalance）
							if (userData.points !== undefined) this.points = userData.points;
							else if (userData.point !== undefined) this.points = userData.point;
							else if (userData.pointBalance !== undefined) this.points = userData.pointBalance;

							// 更新勋章数量（支持 medalCount / medalNum）
							if (userData.hall !== undefined) this.hall = userData.hall;
							else if (userData.medalNum !== undefined) this.hall = userData.medalNum;

							// 可选：头像背景色可以根据用户名首字符生成固定色，此处可保持原色或由后端返回
							if (userData.avatarColor) this.avatarColor = userData.avatarColor;

							// 将最新用户信息存入本地存储，供其他页面使用
							const updatedUserInfo = Object.assign({}, uni.getStorageSync('userInfo') || {}, {
								userName: this.userName,
								userId: this.userId,
								points: this.points,
								hall: this.hall,
								houseAddress: this.houseAddress
							});
							uni.setStorageSync('userInfo', updatedUserInfo);
						}

						if (!silent) {
							uni.hideLoading();
							uni.showToast({
								title: '用户信息已更新',
								icon: 'success'
							});
						}
					},
					fail: (err) => {
						console.log("❌ 获取用户信息失败", err);
						if (!silent) {
							uni.hideLoading();
							uni.showToast({
								title: '请求失败，请检查网络',
								icon: 'none'
							});
						} else {
							// 静默模式下仅控制台报错，不打扰用户
							console.error("静默获取用户信息失败，可能未登录或网络异常");
						}
					}
				});
			},
			fetchPoints() {
							const token = uni.getStorageSync('community_token');
							if (!token) return;
							uni.request({
								url: config.BASE_URL + "/system/points/getPoints",
								method: "GET",
								header: {
									'Content-Type': 'application/json',
									'Authorization': `Bearer ${token}`,
								},
								success: (res) => {
									console.log("points: " + JSON.stringify(res));
									if (res.data && res.data.data) {
										this.points = res.data.data.availablePoints ?? res.data.data.totalPoints ?? 0;
									}
								},
								fail: (err) => {
									console.log("❌ 积分请求失败", err);
								}
							});
						},
			go() {
				this.fetchPoints();
			},
			// 勋章馆
			getHall() {
				const username = uni.getStorageSync("username")
				console.log("username: " + username);
				const token = uni.getStorageSync('community_token')
				console.log("👉 点击了勋章馆")
				uni.request({
					url: config.BASE_URL + "/medal/hall?username=" + username,
					method: "GET",
					header: {
						'Content-Type': 'application/json',
						'Authorization': token ? `Bearer ${token}` : '',
					},
					success: (res) => {
						 console.log("hall" + JSON.stringify(res));
						      this.hall = res.data.data
						console.log("✅ 勋章馆请求成功", res);
					}
				})
			},

			goPoints() {
				uni.showToast({
					title: '我的积分',
					icon: 'none'
				});
			},
			goMedalHall() {
				uni.showToast({
					title: '勋章馆',
					icon: 'none'
				});
			},
			goAccount() {
				uni.navigateTo({
					url: '/pages/account/info'
				});
			},

			goHouse() {
				uni.navigateTo({ url: '/pages/house/house' });
			},
			goPayment() {
				uni.showToast({
					title: '支付中心',
					icon: 'none'
				});
			},
			goCarWash() {
				uni.showToast({
					title: '杨老师马上到',
					icon: 'none'
				});
			},
			goSettings() {
							uni.navigateTo({
								url: '/pages/setting/setting'
							});
						},
			goToSignUp() {
				uni.navigateTo({
					url: '/pages/sign-up/sign-up'
				});
			},
			goTerms() {
				uni.showToast({
					title: '服务条款',
					icon: 'none'
				});
			},
			goPrivacy() {
				uni.showToast({
					title: '隐私协议',
					icon: 'none'
				});
			},
			// 点击报修单据 → 发送查询请求 + 跳转页面
			goRepair() {
			  uni.navigateTo({
			    url: '/pages/repair/repair-list' // 改成你真实的报修页面路径
			  });
			},
			logout() {
				uni.showModal({
					title: '提示',
					content: '确定退出登录吗？',
					success: res => {
						if (res.confirm) {
							uni.removeStorageSync('userInfo');
							uni.removeStorageSync('community_token');
							uni.redirectTo({
								url: '/pages/login/login'
							});
						}
					}
				});
			}
		}
	};
</script>

<style scoped>
.profile-page {
	background-color: #F2F5FA;
	min-height: 100vh;
	padding-bottom: 80rpx;
}

/* 顶部头部 */
.profile-header {
	background: linear-gradient(160deg, #5B9FE8 0%, #1A6FD4 100%);
	padding: 60rpx 30rpx 0;
	border-radius: 0 0 48rpx 48rpx;
	margin-bottom: 24rpx;
}

.header-content {
	display: flex;
	align-items: center;
	margin-bottom: 36rpx;
}

.avatar-wrap {
	position: relative;
	margin-right: 24rpx;
}

.avatar {
	width: 120rpx;
	height: 120rpx;
	border-radius: 50%;
	border: 4rpx solid rgba(255, 255, 255, 0.8);
	display: flex;
	align-items: center;
	justify-content: center;
	box-shadow: 0 4rpx 16rpx rgba(0, 0, 0, 0.2);
}

.avatar-text {
	font-size: 56rpx;
	font-weight: bold;
	color: #fff;
}

.avatar-verified {
	position: absolute;
	bottom: 0;
	right: 0;
	width: 36rpx;
	height: 36rpx;
	background: #fff;
	border-radius: 50%;
	display: flex;
	align-items: center;
	justify-content: center;
	font-size: 20rpx;
	color: #1A6FD4;
	font-weight: bold;
	box-shadow: 0 2rpx 8rpx rgba(0, 0, 0, 0.15);
}

.user-info {
	flex: 1;
}

.name {
	font-size: 40rpx;
	font-weight: 700;
	color: #fff;
	display: block;
	margin-bottom: 12rpx;
}

.badge {
	display: flex;
	align-items: center;
	gap: 16rpx;
}

.tag {
	font-size: 22rpx;
	color: #1A6FD4;
	background: rgba(255, 255, 255, 0.92);
	padding: 4rpx 18rpx;
	border-radius: 30rpx;
}

.id {
	font-size: 24rpx;
	color: rgba(255, 255, 255, 0.75);
}

/* 数据统计栏 */
.stats-bar {
	background: rgba(255, 255, 255, 0.2);
	border-radius: 24rpx 24rpx 0 0;
	display: flex;
	padding: 28rpx 0;
}

.stat-item {
	flex: 1;
	display: flex;
	flex-direction: column;
	align-items: center;
}

.stat-divider {
	width: 2rpx;
	background: rgba(255, 255, 255, 0.4);
	margin: 8rpx 0;
}

.stat-value {
	font-size: 36rpx;
	font-weight: bold;
	color: #fff;
	margin-bottom: 6rpx;
}

.stat-label {
	font-size: 24rpx;
	color: rgba(255, 255, 255, 0.75);
}


/* 菜单列表 */
.menu-list {
	background-color: #fff;
	border-radius: 24rpx;
	margin: 0 24rpx 24rpx;
	overflow: hidden;
	box-shadow: 0 4rpx 20rpx rgba(26, 111, 212, 0.08);
}

.menu-item {
	display: flex;
	align-items: center;
	justify-content: space-between;
	padding: 28rpx 30rpx;
	border-bottom: 1rpx solid #f5f5f5;
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

.menu-icon-wrap {
	width: 72rpx;
	height: 72rpx;
	border-radius: 20rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	flex-shrink: 0;
}

.menu-icon {
	font-size: 36rpx;
}

.menu-name {
	font-size: 30rpx;
	color: #1c2f4a;
	font-weight: 500;
}

.menu-right-group {
	display: flex;
	align-items: center;
	gap: 8rpx;
}

.menu-desc {
	font-size: 26rpx;
	color: #9aafc7;
}

.menu-arrow {
	font-size: 44rpx;
	color: #c2d0e0;
	font-weight: 300;
	line-height: 1;
}

/* 图片美化 */
.service-icon-img {
	width: 80rpx;
	height: 80rpx;
	border-radius: 16rpx;
}

/* 服务卡片 */
.service-card {
	background: linear-gradient(135deg, #5B9FE8 0%, #1A6FD4 100%);
	border-radius: 24rpx;
	padding: 32rpx 30rpx;
	margin: 0 24rpx 24rpx;
	display: flex;
	align-items: center;
	justify-content: space-between;
	color: #fff;
	position: relative;
	overflow: hidden;
}

.service-deco-circle {
	position: absolute;
	right: -50rpx;
	top: -50rpx;
	width: 220rpx;
	height: 220rpx;
	border-radius: 50%;
	background: rgba(255, 255, 255, 0.1);
	pointer-events: none;
}

.service-left {
	display: flex;
	align-items: center;
	gap: 20rpx;
	flex: 1;
}

.service-info {
	flex: 1;
}

.service-name {
	font-size: 30rpx;
	font-weight: bold;
	margin-bottom: 8rpx;
	display: block;
}

.service-sub {
	font-size: 24rpx;
	opacity: 0.85;
	display: block;
}

.service-btn {
	background: #fff;
	color: #1A6FD4;
	padding: 16rpx 32rpx;
	border-radius: 60rpx;
	font-size: 26rpx;
	font-weight: 600;
	white-space: nowrap;
	position: relative;
	z-index: 1;
	flex-shrink: 0;
}

/* 退出登录 */
.logout {
	margin: 0 24rpx 32rpx;
	text-align: center;
	padding: 20rpx;
	color: #9aafc7;
	font-size: 28rpx;
}

/* 底部 */
.footer {
	text-align: center;
	padding: 0 0 20rpx;
}

.links {
	display: flex;
	justify-content: center;
	gap: 30rpx;
	margin-bottom: 16rpx;
}

.links text {
	font-size: 26rpx;
	color: #9aafc7;
}

.divider {
	color: #ccd8e8;
}

.version {
	font-size: 22rpx;
	color: #aabdd4;
	letter-spacing: 2rpx;
}
</style>