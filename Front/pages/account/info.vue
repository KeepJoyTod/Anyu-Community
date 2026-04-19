<template>
	<view class="account-page">
		<!-- 头像区域（点击更换） -->
		<view class="avatar-box" @click="chooseAvatar">
			<image class="avatar" :src="avatarUrl" mode="aspectFill"></image>
			<text class="change-tip">点击更换头像</text>
		</view>

		<!-- 表格样式用户信息 → 姓名、ID、手机号、密码 可输入修改 -->
		<view class="table-box">
			<view class="table-row">
				<view class="table-label">用户姓名</view>
				<input class="table-input" v-model="editForm.userName" placeholder="请输入姓名" />
			</view>
			<view class="table-row">
				<view class="table-label">用户ID</view>
				<input class="table-input" v-model="editForm.userId" placeholder="请输入ID" />
			</view>
			<view class="table-row">
				<view class="table-label">手机号码</view>
				<input class="table-input" v-model="editForm.phonenumber" placeholder="请输入手机号" />
			</view>
			
			<!-- 👇 只在这里加了一行密码项 -->
			<view class="table-row">
				<view class="table-label">登录密码</view>
				<input class="table-input" type="password" v-model="editForm.password" placeholder="不修改请留空" />
			</view>
			
		</view>

		<!-- ✅ 确认修改按钮 -->
		<view class="submit-box">
			<button class="submit-btn" @click="submitUpdate()">确认修改</button>
		</view>
	</view>
</template>

<script>
	import config from '@/api/config.js';
	export default {
		data() {
			return {
				userInfo: {
					userName: '陈宇浩',
					userId: '852703',
					phonenumber: '13800138000',
				},
				// 编辑表单数据
				editForm: {
					userName: '',
					userId: '',
					phonenumber: '',
					password: ''  // 只加了这一个字段
				},
				avatarUrl: '/static/logo.png'
			}
		},
		onLoad(option) {
			// 页面加载时，根据用户名查询用户信息
			this.getUserInfoByUsername();
		},
		methods: {
			// ==============================================
			// 新增：根据用户名查询用户信息 GET 请求
			// ==============================================
			getUserInfoByUsername() {
				const stored = uni.getStorageSync('userInfo') || {};
				const username = uni.getStorageSync("username") || stored.username || stored.userName || '';
				const token = uni.getStorageSync('community_token');
				
				// 没有用户名直接返回
				if (!username) {
					uni.showToast({
						title: '未获取到用户信息',
						icon: 'none'
					});
					return;
				}

				uni.request({
					url: config.BASE_URL + "/user/getByUsername?username=" + username,
					method: "GET",
					header: {
						'Content-Type': 'application/json',
						'Authorization': token ? `Bearer ${token}` : '',
					},
					success: (res) => {
						console.log("查询用户信息成功", res);
						// 接口返回数据正常，赋值给 userInfo
						if (res.data.code === 200) {
							this.userInfo = res.data.data;
							console.log("后端返回的用户数据", this.userInfo);
							// 同步赋值到编辑表单（字段名与后端保持一致）
							this.editForm = {
								userName: this.userInfo.userName || this.userInfo.nickName || '',
								userId: this.userInfo.userId || '',
								phonenumber: this.userInfo.phonenumber || '',
								password: ''  // 密码默认清空，不显示明文
							};
							if (this.userInfo.avatar) {
								this.avatarUrl = this.userInfo.avatar;
							}
						} else {
							uni.showToast({
								title: '获取用户信息失败',
								icon: 'none'
							});
						}
					},
					fail: (err) => {
						console.log("查询用户信息失败", err);
						uni.showToast({
							title: '网络异常，获取信息失败',
							icon: 'none'
						});
					}
				})
			},

			// 更换头像
			chooseAvatar() {
				uni.chooseImage({
					count: 1,
					sizeType: ['compressed'],
					sourceType: ['album', 'camera'],
					success: (res) => {
						this.avatarUrl = res.tempFilePaths[0]
						uni.showToast({
							title: '头像更换成功',
							icon: 'success'
						})
					}
				})
			},
			// 一键提交修改（发送请求）
	
			submitUpdate() {
				const { userName, userId, phonenumber } = this.editForm
				  // 1. 姓名不能为空
				  if (!userName || userName.trim() === '') {
				    uni.showToast({
				      title: '请输入用户姓名',
				      icon: 'none'
				    })
				    return
				  }
				
				
				
				  // 3. 手机号不能为空 + 格式校验
				  if (!phonenumber || phonenumber.trim() === '') {
				    uni.showToast({
				      title: '请输入手机号码',
				      icon: 'none'
				    })
				    return
				  }
				
				  // 手机号格式简单校验（可选，建议加上）
				  const phoneReg = /^1[3-9]\d{9}$/
				  if (!phoneReg.test(phonenumber)) {
				    uni.showToast({
				      title: '请输入正确的手机号',
				      icon: 'none'
				    })
				    return
				  }
				const _stored = uni.getStorageSync('userInfo') || {};
				const username = uni.getStorageSync("username") || _stored.username || _stored.userName || this.userInfo.userName;
				console.log("点击确认修改，发送请求")
				const token = uni.getStorageSync('community_token')
//请求接口
				uni.request({
					url: config.BASE_URL + "/user/updateAll?username=" + username, // 统一修改接口
					method: "PUT",
					header: {
						'Content-Type': 'application/json',
						'Authorization': token ? `Bearer ${token}` : '',
					},
					data: {
						username: this.editForm.userName,
						userId: this.editForm.userId,
						phonenumber: this.editForm.phonenumber,
						password: this.editForm.password  // 只加了这一行
					},
					success: (res) => {
						console.log("修改成功", res)
						// 更新本地数据
						this.userInfo.userName = this.editForm.userName
						this.userInfo.userId = this.editForm.userId
						this.userInfo.phonenumber = this.editForm.phonenumber

						uni.showToast({
							title: '信息修改成功',
							icon: 'success'
						})
					},
					fail: err => {
						console.log("修改失败", err)
						uni.showToast({
							title: '网络异常，修改失败',
							icon: 'none'
						});
					}
				})
			}
		}
	}
</script>

<style scoped>
	.account-page {
		background-color: #f5f7fa;
		min-height: 100vh;
		padding: 30rpx;
	}

	.avatar-box {
		display: flex;
		flex-direction: column;
		align-items: center;
		margin-bottom: 40rpx;
	}

	.avatar {
		width: 160rpx;
		height: 160rpx;
		border-radius: 50%;
		background-color: #eee;
		object-fit: cover;
		border: 4rpx solid #fff;
		box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.1);
	}

	.change-tip {
		margin-top: 16rpx;
		font-size: 26rpx;
		color: #666;
	}

	.table-box {
		background: #fff;
		border-radius: 16rpx;
		border: 1rpx solid #eee;
		overflow: hidden;
	}

	.table-row {
		display: flex;
		padding: 28rpx 30rpx;
		border-bottom: 1rpx solid #f0f0f0;
		align-items: center;
	}

	.table-row:last-child {
		border-bottom: none;
	}

	.table-label {
		width: 200rpx;
		font-size: 28rpx;
		color: #666;
	}

	/* 输入框样式 */
	.table-input {
		flex: 1;
		font-size: 28rpx;
		color: #333;
		text-align: right;
		padding: 10rpx;
	}

	/* 提交按钮 */
	.submit-box {
		margin-top: 40rpx;
		padding: 0 20rpx;
	}

	.submit-btn {
		width: 100%;
		background-color: #007aff;
		color: #fff;
		border-radius: 50rpx;
		padding: 24rpx 0;
		font-size: 32rpx;
		border: none;
	}
</style>