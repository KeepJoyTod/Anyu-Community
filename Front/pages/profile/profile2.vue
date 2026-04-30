<template>
	<view class="profile-page">
		<!-- 头部用户卡片 -->
		<view class="user-card">
			<view class="user-header">
				<view class="avatar" :style="{ backgroundColor: avatarColor }">
					<text class="avatar-text">{{ userName.charAt(0) }}</text>
				</view>
				<view class="user-info">
					<!-- 点击名字发送获取用户信息请求 -->
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
			<view class="stat-item" @click="go()">
				<text class="stat-value">{{ points }}</text>
				<text class="stat-label">我的积分</text>
			</view>
			<view class="stat-item" @click="getHall()">
				<text class="stat-value">{{ hall }}</text>
				<text class="stat-label">勋章馆</text>
			</view>
		</view>

		<!-- 功能菜单列表 -->
		<view class="menu-list">
			<view class="menu-item" @click="goAccount()">
				<view class="menu-left">
					<text class="menu-icon">🏦</text>
					<text class="menu-name">账户中心</text>
				</view>
				<text class="menu-arrow">></text>
			</view>
			<view class="menu-item" @click="goHouse()">
				<view class="menu-left">
					<text class="menu-icon">🏠</text>
					<text class="menu-name">我的房屋</text>
				</view>
				<text class="menu-desc">{{ houseAddress }}</text>
				<text class="menu-arrow">></text>
			</view>
			<view class="menu-item" @click="goMedal()">
				
			</view>
			<!-- 功能菜单列表 - 报修单据 修复后 -->
			<view class="menu-item" @click="goRepair()">
				<view class="menu-left">
					<text class="menu-icon">🔧</text>
					<text class="menu-name">报修单据</text>
				</view>
				<view class="menu-right">
					<text class="menu-desc">目前有1个正在处理中</text>
					<text class="menu-arrow">&gt;</text>
				</view>
			</view>
			<view class="menu-item" @click="goSettings()">
				<view class="menu-left">
					<text class="menu-icon">⚙️</text>
					<text class="menu-name">系统设置</text>
				</view>
				<text class="menu-desc">通知推送与隐私管理</text>
				<text class="menu-arrow">></text>
			</view>
		</view>

		<!-- 服务卡片 -->
		<view class="service-card" @click="goCarWash()">
			<view class="service-left">
				<image class="service-icon-img" src="/static/hui.png" mode="aspectFit"></image>
				<view class="service-info">
					<text class="service-name">学习Java哪家强</text>
					<text class="service-sub">山东高合找杨师</text>
				</view>
			</view>
			<text class="service-btn" @click="goToSignUp()">立即报名</text>
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
	import config from '@/api/config.js'
	export default {
		data() {
			return {
				userName: '陈宇浩',
				userId: '1',
				avatarColor: '#6b8c42',
				points:"320", 
				hall: "1",
				houseAddress: '阳光花园8栋1205室'
			};
		
		},
		onLoad() {
			// 页面加载时静默获取用户信息，避免展示过期的硬编码数据
			this.fetchUserInfo(true);
		},
		methods: {
			/**
			 * 获取用户信息的核心方法
			 * @param {boolean} silent 是否静默请求（不显示成功/失败的toast，只显示错误）
			 */
			fetchUserInfo(silent = false) {
				// 获取存储的token
				const token = uni.getStorageSync('community_token');
				const username = uni.getStorageSync("username")

				// 非静默模式下显示加载中
				if (!silent) {
					uni.showLoading({
						title: '获取用户信息中...',
						mask: true
					});
				}

				console.log("👉 发起用户信息GET请求");

				uni.request({
					url: config.resolveUrl('/user/user') + '?username=' + encodeURIComponent(username || ''),
					method: "GET",
					header: {
						'Content-Type': 'application/json',
						'Authorization': token ? `Bearer ${token}` : '',
					},
					
					success: (res) => {
						console.log("fetchUserInfo" +JSON.stringify(res));
						this.fetchUserInfo= res.data.data	
						console.log("✅ 获取用户信息成功", res);
						// 兼容多种后端返回格式：res.data.data 或 res.data
						let userData = res.data;
						if (res.data && res.data.code === 200) {
							userData = res.data.data || res.data;
						}

						// 更新页面数据，字段名兼容处理
						if (userData) {
							// 更新用户名（支持 userName / name / nickname）
							if (userData.userName) this.userName = userData.userName;
							else if (userData.name) this.userName = userData.name;
							else if (userData.nickname) this.userName = userData.nickname;

							// 更新用户ID（支持 userId / id）
							if (userData.userId) this.userId = userData.userId;
							else if (userData.id) this.userId = userData.id;

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
							const updatedUserInfo = {
								name: this.userName,
								id: this.userId,
								point: this.points,
								medalNum: this.hall,
								house: this.houseAddress
							};
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
			go() {
				const username = uni.getStorageSync("username")
				console.log("username: " + username);
				const token = uni.getStorageSync('community_token')
				console.log("👉 点击了我的积分")
				uni.request({
					url: config.resolveUrl('/point/myPoints') + '?username=' + encodeURIComponent(username || ''),
					method: "GET",
					header: {
						'Content-Type': 'application/json',
						'Authorization': token ? `Bearer ${token}` : '',
					},
					success: (res) => {
						console.log("points: " + JSON.stringify(res));
						this.points=res.data.data
						console.log("✅ 积分请求成功", res);
					},
					fail: (err) => {
						console.log("❌ 积分请求失败", err);
					}
				})
			},
			// 勋章馆
			getHall() {
				const username = uni.getStorageSync("username")
				console.log("username: " + username);
				const token = uni.getStorageSync('community_token')
				console.log("👉 点击了勋章馆")
				uni.request({
					url: config.resolveUrl('/medal/hall') + '?username=' + encodeURIComponent(username || ''),
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
				console.log("👉 点击账户中心，开始根据 userId 查询用户信息");
				const token = uni.getStorageSync('community_token')
				// 发送【根据ID查询用户】请求
				uni.request({
					url: config.resolveUrl('/user/info'),
					method: "GET",
					header: {
						'Content-Type': 'application/json',
						'Authorization': token ? `Bearer ${token}` : '',
					},
					data: {
						userId: this.userId
					},
					header: {
						'Content-Type': 'application/json'
					},
					success: (res) => {
						console.log("goAccount" + JSON.stringify(res));
						this.goAccount = res.data[0]
						console.log("✅ 根据ID查询用户成功：", res);

						// 请求成功后再跳转
						uni.navigateTo({
							url: `/pages/account/info?userId=${this.userId}`
						});
					},
					fail: (err) => {
						console.log("❌ 查询用户失败：", err);
						uni.showToast({
							title: "获取用户信息失败",
							icon: "none"
						});
					}
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
			goMedal() {
				uni.showToast({
					title: '我的勋章',
					icon: 'none'
				});
			},
			goRepair() {
				uni.showToast({
					title: '报修单据',
					icon: 'none'
				});
			},
			goSettings() {
				uni.showToast({
					title: '系统设置',
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
		width: 80rpx;
		height: 80rpx;
		border-radius: 20rpx;
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
		text-align: center;
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
		justify-content: center;
		align-items: center;
	}

	.service-name {
		font-size: 30rpx;
		font-weight: bold;
		margin-bottom: 8rpx;
		text-align: center;
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

	/* 保修单据css */
	.menu-item {
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 20rpx;
		border-bottom: 1rpx solid #eee;
	}

	.menu-left {
		display: flex;
		align-items: center;
	}

	.menu-icon {
		font-size: 32rpx;
		margin-right: 16rpx;
	}

	.menu-name {
		font-size: 32rpx;
		color: #333;
	}

	.menu-right {
		display: flex;
		align-items: center;
	}

	.menu-desc {
		font-size: 28rpx;
		color: #666;
		margin-right: 16rpx;
	}

	.menu-arrow {
		font-size: 32rpx;
		color: #999;
	}
</style>
