<template>
	<view class="booking-page">
		<!-- 背景装饰 -->
		<view class="bg-blur"></view>

		<!-- 顶部返回栏 -->
		<view class="nav-bar">
			<view class="back-btn" @click="goBack">
				<text class="back-icon">←</text>
			</view>
			<text class="nav-title">场地预约</text>
			<view class="placeholder"></view>
		</view>

		<!-- 场地选择区域 -->
		<view class="section-title">
			<text>选择场地</text>
			<text class="section-subtitle">请选择您要预约的场地</text>
		</view>
		<scroll-view scroll-x class="venue-scroll" show-scrollbar="false">
			<view class="venue-list">
				<view
					v-for="(venue, idx) in venues"
					:key="idx"
					class="venue-card"
					:class="{ active: selectedVenue.id === venue.id }"
					@click="selectVenue(venue)"
				>
					<view class="venue-icon">{{ venue.icon }}</view>
					<text class="venue-name">{{ venue.name }}</text>
					<text class="venue-desc">{{ venue.desc }}</text>
					<view class="venue-price">¥{{ venue.price }}/时</view>
				</view>
			</view>
		</scroll-view>

		<!-- 预约信息卡片 -->
		<view class="booking-card" v-if="selectedVenue.id">
			<view class="booking-header">
				<text class="booking-title">预约信息</text>
			</view>

			<!-- 日期选择 -->
			<view class="form-item">
				<text class="label">预约日期</text>
				<picker mode="date" :value="bookingDate" :start="minDate" @change="onDateChange">
					<view class="picker">{{ bookingDate || '请选择日期' }}</view>
				</picker>
			</view>

			<!-- 时间段选择 -->
			<view class="form-item">
				<text class="label">开始时间</text>
				<picker mode="time" :value="startTime" @change="onStartTimeChange">
					<view class="picker">{{ startTime || '请选择时间' }}</view>
				</picker>
			</view>

			<view class="form-item">
				<text class="label">结束时间</text>
				<picker mode="time" :value="endTime" @change="onEndTimeChange">
					<view class="picker">{{ endTime || '请选择时间' }}</view>
				</picker>
			</view>

			<!-- 备注 -->
			<view class="form-item">
				<text class="label">备注</text>
				<textarea class="textarea" v-model="remark" placeholder="选填，如人数、特殊需求" placeholder-class="placeholder" />
			</view>

			<!-- 费用预估 -->
			<view class="fee-preview" v-if="duration">
				<text>预计时长：{{ duration }} 小时</text>
				<text class="total-fee">总费用：¥{{ totalFee }}</text>
			</view>

			<!-- 提交按钮 -->
			<view class="submit-btn" @click="submitBooking" :class="{ disabled: isLoading }">
				<text>{{ isLoading ? '提交中...' : '立即预约' }}</text>
			</view>
		</view>

		<!-- 未选择场地提示 -->
		<view class="empty-tip" v-else>
			<text>👈 请先选择上方场地</text>
		</view>

		<!-- 底部提示 -->
		<view class="tips">
			<text class="tips-text">* 预约成功后，请按时到场，如需取消请提前2小时</text>
		</view>
	</view>
</template>

<script>
/**
 * 场地预约页面
 * @description 提供场地预约功能，包括场地选择、日期时间选择和预约提交
 */
import { listVenues, createBooking } from '@/api/venue.js'

export default {
	data() {
		const today = new Date();
		const yyyy = today.getFullYear();
		const mm = String(today.getMonth() + 1).padStart(2, '0');
		const dd = String(today.getDate()).padStart(2, '0');
		const minDate = `${yyyy}-${mm}-${dd}`;
		return {
			venues: [],
			selectedVenue: { id: null, name: '', price: 0 },
			bookingDate: '',
			startTime: '',
			endTime: '',
			remark: '',
			minDate: minDate,
			isLoading: false
		};
	},
	computed: {
		duration() {
			if (!this.startTime || !this.endTime) return 0;
			const start = this.startTime.split(':');
			const end = this.endTime.split(':');
			let hours = parseInt(end[0]) - parseInt(start[0]);
			const minutes = parseInt(end[1]) - parseInt(start[1]);
			if (minutes < 0) {
				hours -= 1;
			}
			return hours > 0 ? hours : 0;
		},
		totalFee() {
			return (this.duration * this.selectedVenue.price).toFixed(2);
		}
	},
	onLoad() {
		this.fetchVenues();
	},
	methods: {
		/**
		 * 返回上一页
		 */
		goBack() {
			uni.navigateBack();
		},
		
		/**
		 * 获取场地列表
		 */
		fetchVenues() {
			uni.showLoading({ title: '加载中...', mask: true });
			listVenues()
				.then(res => {
					if (res && res.code === 200) {
						this.venues = res.data;
					} else if (Array.isArray(res)) {
						this.venues = res;
					} else {
						uni.showToast({ title: res.msg || '获取场地列表失败', icon: 'none' });
						// 使用默认场地数据
						this.venues = [
							{ id: 1, name: '麻将馆', icon: '🀄', desc: '自动麻将桌，环境舒适', price: 50 },
							{ id: 2, name: '社区广场', icon: '🏞️', desc: '开阔场地，适合广场舞', price: 30 },
							{ id: 3, name: '会议室', icon: '📊', desc: '投影仪、白板，可容纳20人', price: 80 },
							{ id: 4, name: '健身房', icon: '🏋️', desc: '专业器材，私教可约', price: 60 },
							{ id: 5, name: '儿童乐园', icon: '🎠', desc: '安全设施，亲子好去处', price: 40 }
						];
					}
				})
				.catch(err => {
					console.error('获取场地列表失败', err);
					uni.showToast({ title: '网络错误，请重试', icon: 'none' });
					// 使用默认场地数据
					this.venues = [
						{ id: 1, name: '麻将馆', icon: '🀄', desc: '自动麻将桌，环境舒适', price: 50 },
						{ id: 2, name: '社区广场', icon: '🏞️', desc: '开阔场地，适合广场舞', price: 30 },
						{ id: 3, name: '会议室', icon: '📊', desc: '投影仪、白板，可容纳20人', price: 80 },
						{ id: 4, name: '健身房', icon: '🏋️', desc: '专业器材，私教可约', price: 60 },
						{ id: 5, name: '儿童乐园', icon: '🎠', desc: '安全设施，亲子好去处', price: 40 }
					];
				})
				.finally(() => {
					uni.hideLoading();
				});
		},
		
		/**
		 * 选择场地
		 * @param {Object} venue - 场地信息
		 */
		selectVenue(venue) {
			this.selectedVenue = venue;
		},
		
		/**
		 * 日期选择变化
		 * @param {Object} e - 选择事件
		 */
		onDateChange(e) {
			this.bookingDate = e.detail.value;
		},
		
		/**
		 * 开始时间选择变化
		 * @param {Object} e - 选择事件
		 */
		onStartTimeChange(e) {
			this.startTime = e.detail.value;
		},
		
		/**
		 * 结束时间选择变化
		 * @param {Object} e - 选择事件
		 */
		onEndTimeChange(e) {
			this.endTime = e.detail.value;
		},
		
		/**
		 * 提交预约
		 */
		submitBooking() {
			if (!this.selectedVenue.id) {
				uni.showToast({ title: '请选择场地', icon: 'none' });
				return;
			}
			if (!this.bookingDate) {
				uni.showToast({ title: '请选择预约日期', icon: 'none' });
				return;
			}
			if (!this.startTime || !this.endTime) {
				uni.showToast({ title: '请选择时间段', icon: 'none' });
				return;
			}
			if (this.duration <= 0) {
				uni.showToast({ title: '结束时间需晚于开始时间', icon: 'none' });
				return;
			}
			
			this.isLoading = true;
			uni.showLoading({ title: '提交中...', mask: true });
			
			const bookingData = {
				spaceName: this.selectedVenue.name,
				userId: 1, // 假设当前用户ID为1，实际应从登录信息中获取
				userName: '测试用户', // 假设当前用户名为测试用户，实际应从登录信息中获取
				userPhone: '13800138000', // 假设当前用户电话，实际应从登录信息中获取
				reserveDate: this.bookingDate,
				startTime: this.startTime,
				endTime: this.endTime,
				reservationPurpose: this.remark,
				remark: this.remark
			};
			
			createBooking(bookingData)
				.then(res => {
					uni.hideLoading();
					if (res && res.code === 200) {
					uni.showModal({
						title: '预约成功',
						content: `您已成功预约${this.selectedVenue.name}\n时间：${this.bookingDate} ${this.startTime}-${this.endTime}\n费用：¥${this.totalFee}`,
						showCancel: false,
						success: () => {
							// 重置表单
							this.selectedVenue = { id: null, name: '', price: 0 };
							this.bookingDate = '';
							this.startTime = '';
							this.endTime = '';
							this.remark = '';
							// 返回上一页并刷新数据
							setTimeout(() => {
								// 触发上一页的刷新
								uni.navigateBack({
									delta: 1,
									success: () => {
										// 通知上一页刷新数据
										uni.$emit('refreshVenueBookings');
									}
								});
							}, 1500);
						}
					});
				} else {
					uni.showToast({ title: res.msg || '预约失败', icon: 'none' });
				}
				})
				.catch(err => {
					uni.hideLoading();
					console.error('预约失败', err);
					uni.showToast({ title: '网络错误，请重试', icon: 'none' });
				})
				.finally(() => {
					this.isLoading = false;
				});
		}
	}
};
</script>

<style scoped>
.booking-page {
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

/* 章节标题 */
.section-title {
	margin-bottom: 24rpx;
	position: relative;
	z-index: 2;
}

.section-title text:first-child {
	font-size: 36rpx;
	font-weight: 700;
	color: #1c2e3a;
	display: block;
}

.section-subtitle {
	font-size: 26rpx;
	color: #8b9bae;
	margin-top: 8rpx;
	display: block;
}

/* 横向滚动场地卡片 */
.venue-scroll {
	white-space: nowrap;
	margin-bottom: 40rpx;
	position: relative;
	z-index: 2;
}

.venue-list {
	display: inline-flex;
	gap: 24rpx;
	padding: 8rpx 4rpx;
}

.venue-card {
	display: inline-flex;
	flex-direction: column;
	align-items: center;
	width: 280rpx;
	background: rgba(255,255,255,0.92);
	backdrop-filter: blur(12rpx);
	border-radius: 44rpx;
	padding: 28rpx 20rpx;
	box-shadow: 0 20rpx 40rpx -16rpx rgba(0,0,0,0.08), 0 0 0 1rpx rgba(107,140,66,0.1);
	transition: all 0.25s;
	text-align: center;
}

.venue-card.active {
	background: linear-gradient(135deg, rgba(107,140,66,0.2), rgba(107,140,66,0.05));
	border: 1rpx solid #6b8c42;
	transform: scale(1.02);
}

.venue-icon {
	font-size: 80rpx;
	margin-bottom: 16rpx;
}

.venue-name {
	font-size: 32rpx;
	font-weight: 600;
	color: #1f2e3a;
	margin-bottom: 8rpx;
}

.venue-desc {
	font-size: 24rpx;
	color: #8b9bae;
	margin-bottom: 12rpx;
	white-space: normal;
	word-break: break-all;
}

.venue-price {
	font-size: 28rpx;
	font-weight: 700;
	color: #6b8c42;
	background: rgba(107,140,66,0.12);
	padding: 6rpx 16rpx;
	border-radius: 40rpx;
}

/* 预约信息卡片 */
.booking-card {
	background: rgba(255,255,255,0.92);
	backdrop-filter: blur(12rpx);
	border-radius: 44rpx;
	padding: 32rpx;
	margin-bottom: 32rpx;
	box-shadow: 0 20rpx 40rpx -16rpx rgba(0,0,0,0.08), 0 0 0 1rpx rgba(107,140,66,0.1);
}

.booking-header {
	margin-bottom: 32rpx;
	padding-bottom: 16rpx;
	border-bottom: 1rpx solid rgba(107,140,66,0.2);
}

.booking-title {
	font-size: 32rpx;
	font-weight: 700;
	color: #1f2e3a;
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

.picker {
	width: 100%;
	background: rgba(255,255,255,0.8);
	border-radius: 60rpx;
	padding: 20rpx 28rpx;
	font-size: 30rpx;
	color: #1c2e3a;
	box-shadow: inset 0 1rpx 3rpx rgba(0,0,0,0.02), 0 0 0 1rpx rgba(107,140,66,0.1);
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

.fee-preview {
	background: rgba(107,140,66,0.08);
	border-radius: 40rpx;
	padding: 20rpx;
	margin: 24rpx 0;
	display: flex;
	justify-content: space-between;
	font-size: 28rpx;
	color: #1f2e3a;
}

.total-fee {
	font-weight: 700;
	color: #6b8c42;
	font-size: 32rpx;
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
	box-shadow: 0 12rpx 28rpx rgba(107,140,66,0.3);
	transition: all 0.2s;
	margin-top: 24rpx;
}

.submit-btn:active {
	transform: scale(0.98);
	opacity: 0.9;
}

.empty-tip {
	background: rgba(255,255,255,0.9);
	backdrop-filter: blur(8rpx);
	border-radius: 44rpx;
	padding: 80rpx 0;
	text-align: center;
	font-size: 30rpx;
	color: #8b9bae;
	margin-bottom: 32rpx;
}

.tips {
	text-align: center;
	margin-top: 20rpx;
}

.tips-text {
	font-size: 24rpx;
	color: #9aabb8;
}
</style>