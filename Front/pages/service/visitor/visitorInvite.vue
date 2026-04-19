<template>
	<view class="invite-page">
		<!-- 背景装饰 -->
		<view class="bg-blur"></view>

		<!-- 顶部返回栏 -->
		<view class="nav-bar">
			<view class="back-btn" @click="goBack">
				<text class="back-icon">←</text>
			</view>
			<text class="nav-title">访客邀请</text>
			<view class="placeholder"></view>
		</view>

		<!-- 主要表单卡片 -->
		<view class="form-card">
			<view class="form-title">
				<text class="title-icon">👤</text>
				<text>填写访客信息</text>
			</view>

			<!-- 访客姓名 -->
			<view class="form-item">
				<text class="label">姓名</text>
				<input class="input" v-model="visitor.name" placeholder="请输入访客姓名" placeholder-class="placeholder" />
			</view>

			<!-- 手机号 -->
			<view class="form-item">
				<text class="label">手机号</text>
				<input class="input" v-model="visitor.phone" type="number" placeholder="请输入手机号" placeholder-class="placeholder" />
			</view>

			<!-- 到访时间 -->
			<view class="form-item">
				<text class="label">到访时间</text>
				<picker mode="date" :value="visitor.expectedVisitTimeStr" @change="onDateChange">
					<view class="picker">{{ visitor.expectedVisitTimeStr || '请选择日期' }}</view>
				</picker>
			</view>

			<!-- 到访事由 -->
			<view class="form-item">
				<text class="label">事由</text>
				<input class="input" v-model="visitor.reason" placeholder="如：探访亲友、送快递等" placeholder-class="placeholder" />
			</view>

			<!-- 备注 -->
			<view class="form-item">
				<text class="label">备注</text>
				<textarea class="textarea" v-model="visitor.remark" placeholder="选填，如车牌号、特殊需求" placeholder-class="placeholder" />
			</view>
		</view>

		<!-- 最近邀请记录（预览） -->
		<view class="history-card">
			<view class="history-header">
				<text class="history-title">📋 最近邀请</text>
				<text class="history-more" @click="viewAll">查看全部</text>
			</view>
			<view class="history-list" v-if="recentInvites.length">
				<view class="history-item" v-for="(item, idx) in recentInvites" :key="idx">
					<view class="history-info">
						<text class="history-name">{{ item.name }}</text>
						<text class="history-date">{{ item.expectedVisitTimeStr }}</text>
					</view>
					<text class="history-status" :class="item.status === '已签入' ? 'signed' : 'pending'">{{ item.status }}</text>
				</view>
			</view>
			<view class="empty-history" v-else>
				<text>暂无邀请记录</text>
			</view>
		</view>

		<!-- 提交按钮 -->
		<view class="submit-btn" @click="sendInvite">
			<text>发送邀请</text>
		</view>

		<!-- 底部提示 -->
		<view class="tips">
			<text class="tips-text">* 邀请发出后，访客将收到短信通知，请确保手机号正确</text>
		</view>
	</view>
</template>

<script>
import { getRecentInvites, sendVisitorInvite } from '@/api/visitor.js'
export default {
	data() {
		return {
			visitor: {
				name: '',
				phone: '',
				expectedVisitTimeStr: '',
				reason: '',
				remark: ''
			},
			recentInvites: []
		};
	},
	onShow() {
		this.fetchRecentInvites();
	},
	methods: {
		async fetchRecentInvites() {
			try {
				const res = await getRecentInvites(3);
				if (res && res.data) {
					// 处理后端返回的数据，确保expectedVisitTimeStr字段有值
					this.recentInvites = res.data.map(item => {
						// 如果后端返回了date但没有expectedVisitTimeStr，使用date
						if (!item.expectedVisitTimeStr && item.date) {
							item.expectedVisitTimeStr = item.date;
						}
						// 如果后端返回了expectedVisitTime但没有expectedVisitTimeStr，使用expectedVisitTime
						if (!item.expectedVisitTimeStr && item.expectedVisitTime) {
							item.expectedVisitTimeStr = item.expectedVisitTime;
						}
						return item;
					});
				} else if (Array.isArray(res)) {
					this.recentInvites = res.map(item => {
						if (!item.expectedVisitTimeStr && item.date) {
							item.expectedVisitTimeStr = item.date;
						}
						if (!item.expectedVisitTimeStr && item.expectedVisitTime) {
							item.expectedVisitTimeStr = item.expectedVisitTime;
						}
						return item;
					});
				}
			} catch (e) {
				console.error('获取最近邀请失败', e);
			}
		},
		goBack() {
			uni.navigateBack();
		},
		onDateChange(e) {
			this.visitor.expectedVisitTimeStr = e.detail.value;
		},
		async sendInvite() {
			if (!this.visitor.name) {
				uni.showToast({ title: '请输入访客姓名', icon: 'none' });
				return;
			}
			if (!this.visitor.phone || !/^1[3-9]\d{9}$/.test(this.visitor.phone)) {
				uni.showToast({ title: '请输入正确的手机号', icon: 'none' });
				return;
			}
			if (!this.visitor.expectedVisitTimeStr) {
				uni.showToast({ title: '请选择到访时间', icon: 'none' });
				return;
			}
			uni.showLoading({ title: '发送中...' });
			try {
				await sendVisitorInvite(this.visitor);
				uni.hideLoading();
				uni.showModal({
					title: '邀请已发送',
					content: `已向 ${this.visitor.name} 发送邀请短信，预计 ${this.visitor.expectedVisitTimeStr} 到访。`,
					showCancel: false,
					success: () => {
						// 清空表单
						this.visitor = { name: '', phone: '', expectedVisitTimeStr: '', reason: '', remark: '' };
						// 刷新最近邀请列表
						this.fetchRecentInvites();
					}
				});
			} catch (e) {
				uni.hideLoading();
				console.error('发送邀请失败', e);
			}
		},
		viewAll() {
			uni.showToast({ title: '功能开发中', icon: 'none' });
		}
	}
};
</script>

<style scoped>
.invite-page {
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

/* 表单卡片 */
.form-card {
	background: rgba(255,255,255,0.92);
	backdrop-filter: blur(12rpx);
	border-radius: 44rpx;
	padding: 32rpx;
	margin-bottom: 32rpx;
	box-shadow: 0 20rpx 40rpx -16rpx rgba(0,0,0,0.08), 0 0 0 1rpx rgba(107,140,66,0.1);
}

.form-title {
	display: flex;
	align-items: center;
	gap: 12rpx;
	font-size: 32rpx;
	font-weight: 600;
	color: #1f2e3a;
	margin-bottom: 32rpx;
	padding-bottom: 16rpx;
	border-bottom: 1rpx solid rgba(107,140,66,0.2);
}

.title-icon {
	font-size: 36rpx;
}

.form-item {
	margin-bottom: 28rpx;
}

.label {
	font-size: 28rpx;
	color: #4a5e71;
	margin-bottom: 12rpx;
	display: block;
}

.input, .picker {
	width: 100%;
	background: rgba(255,255,255,0.8);
	border-radius: 60rpx;
	padding: 20rpx 28rpx;
	font-size: 30rpx;
	color: #1c2e3a;
	box-shadow: inset 0 1rpx 3rpx rgba(0,0,0,0.02), 0 0 0 1rpx rgba(107,140,66,0.1);
}

.picker {
	color: #8b9bae;
}

.textarea {
	width: 100%;
	background: rgba(255,255,255,0.8);
	border-radius: 32rpx;
	padding: 20rpx 28rpx;
	font-size: 30rpx;
	color: #1c2e3a;
	box-shadow: inset 0 1rpx 3rpx rgba(0,0,0,0.02), 0 0 0 1rpx rgba(107,140,66,0.1);
	min-height: 160rpx;
}

.placeholder {
	color: #bdc7d0;
}

/* 历史记录卡片 */
.history-card {
	background: rgba(255,255,255,0.92);
	backdrop-filter: blur(12rpx);
	border-radius: 44rpx;
	padding: 32rpx;
	margin-bottom: 48rpx;
	box-shadow: 0 20rpx 40rpx -16rpx rgba(0,0,0,0.08), 0 0 0 1rpx rgba(107,140,66,0.1);
}

.history-header {
	display: flex;
	justify-content: space-between;
	align-items: baseline;
	margin-bottom: 24rpx;
}

.history-title {
	font-size: 30rpx;
	font-weight: 600;
	color: #1f2e3a;
}

.history-more {
	font-size: 24rpx;
	color: #6b8c42;
	background: rgba(107,140,66,0.1);
	padding: 6rpx 20rpx;
	border-radius: 40rpx;
}

.history-list {
	display: flex;
	flex-direction: column;
	gap: 20rpx;
}

.history-item {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 16rpx 0;
	border-bottom: 1rpx solid rgba(107,140,66,0.1);
}

.history-info {
	display: flex;
	flex-direction: column;
	gap: 8rpx;
}

.history-name {
	font-size: 28rpx;
	font-weight: 500;
	color: #1c2e3a;
}

.history-date {
	font-size: 24rpx;
	color: #8b9bae;
}

.history-status {
	font-size: 24rpx;
	padding: 4rpx 16rpx;
	border-radius: 40rpx;
}

.pending {
	color: #e6892e;
	background: rgba(230,137,46,0.12);
}

.signed {
	color: #2b8c5e;
	background: rgba(43,140,94,0.12);
}

.empty-history {
	text-align: center;
	padding: 40rpx 0;
	color: #9aabb8;
	font-size: 28rpx;
}

/* 提交按钮 */
.submit-btn {
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

.submit-btn:active {
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