<template>
	<view class="detail-page">
		<!-- 背景装饰 -->
		<view class="bg-blur"></view>

		<!-- 顶部返回栏 -->
		<view class="nav-bar">
			<view class="back-btn" @click="goBack">
				<text class="back-icon">←</text>
			</view>
			<text class="nav-title">快递详情</text>
			<view class="placeholder"></view>
		</view>

		<!-- 包裹信息卡片 -->
		<view class="info-card" v-if="packageData">
			<view class="card-header">
				<text class="company">{{ packageData.company }}</text>
				<text class="status" :class="packageData.status">{{ statusText(packageData.status) }}</text>
			</view>
			<view class="divider"></view>
			<view class="info-row">
				<text class="label">运单号/取件码</text>
				<text class="value">{{ packageData.pickupCode || packageData.trackingNo }}</text>
			</view>
			<view class="info-row">
				<text class="label">到达时间</text>
				<text class="value">{{ packageData.arrivalTime }}</text>
			</view>
			<view class="info-row">
				<text class="label">当前状态</text>
				<text class="value status-desc">{{ statusDesc(packageData.status) }}</text>
			</view>
			<view class="info-row" v-if="packageData.status === 'pending'">
				<text class="label">取件地点</text>
				<text class="value">社区快递驿站 · 12栋架空层</text>
			</view>
		</view>

		<!-- 物流轨迹 -->
		<view class="track-card" v-if="packageData">
			<view class="track-title">物流轨迹</view>
			<view class="track-list">
				<view v-for="(track, idx) in tracks" :key="idx" class="track-item">
					<view class="track-dot" :class="{ active: idx === 0 }"></view>
					<view class="track-content">
						<text class="track-time">{{ track.time }}</text>
						<text class="track-desc">{{ track.desc }}</text>
					</view>
				</view>
			</view>
		</view>

		<!-- 底部操作按钮（仅待取件显示） -->
		<view class="action-btn" v-if="packageData && packageData.status === 'pending'">
			<view class="pickup-btn" @click="pickup">确认取件</view>
		</view>

		<!-- 提示文字 -->
		<view class="tips">
			<text class="tips-text">* 如有疑问请联系驿站管理员：138****1234</text>
		</view>
	</view>
</template>


<script>
import { getPackageDetail, getPackageTracks, pickupPackage } from '@/common/api/station'
export default {
	data() {
		return {
			packageData: null,
			tracks: []
		};
	},
	onLoad(options) {
		const id = parseInt(options.id)
		this.loadDetail(id)
	},
	methods: {
		goBack() {
			uni.navigateBack();
		},
		async loadDetail(id) {
			try {
				const res = await getPackageDetail(id)
				const it = res.data || res
				this.packageData = {
					id: it.id,
					company: it.company || it.expressCompany || '',
					pickupCode: it.pickupCode,
					trackingNo: it.trackingNo,
					arrivalTime: it.arrivalTime || it.createdAt || it.time,
					status: it.status || 'pending'
				}
				try {
					const tr = await getPackageTracks(id)
					const td = tr.data || tr
					this.tracks = (td.list || td || []).map(t => ({
						time: t.time,
						desc: t.desc
					}))
				} catch (_) {
					this.tracks = this.generateFallbackTracks(this.packageData)
				}
			} catch (e) {
				uni.showToast({ title: '加载失败', icon: 'none' })
			}
		},
		generateFallbackTracks(pkg) {
			const base = [
				{ time: pkg.arrivalTime, desc: '包裹已到达【社区快递驿站】' },
				{ time: this.addDays(pkg.arrivalTime, -1), desc: '包裹已到达【市分拨中心】' },
				{ time: this.addDays(pkg.arrivalTime, -2), desc: '包裹已发往【市分拨中心】' },
				{ time: this.addDays(pkg.arrivalTime, -3), desc: '【XX省转运中心】已发出' }
			]
			if (pkg.status === 'in_transit') return base.slice(2)
			if (pkg.status === 'delivering') return base.slice(1)
			return base
		},
		addDays(dateStr, days) {
			const date = new Date(dateStr);
			date.setDate(date.getDate() + days);
			const y = date.getFullYear();
			const m = String(date.getMonth() + 1).padStart(2, '0');
			const d = String(date.getDate()).padStart(2, '0');
			return `${y}-${m}-${d} 12:00`;
		},
		statusText(status) {
			const map = {
				pending: '待取件',
				in_transit: '运输中',
				delivering: '派送中',
				picked: '已取件'
			};
			return map[status] || status;
		},
		statusDesc(status) {
			const map = {
				pending: '包裹已到达驿站，请尽快取件',
				in_transit: '包裹正在运输途中',
				delivering: '快递员正在派送中',
				picked: '您已取走包裹'
			};
			return map[status] || '';
		},
		pickup() {
			if (!this.packageData || this.packageData.status !== 'pending') return
			uni.showModal({
				title: '取件确认',
				content: `确认取走【${this.packageData.company}】包裹吗？`,
				success: async (res) => {
					if (res.confirm) {
						try {
							await pickupPackage(this.packageData.id)
							uni.$emit('packagePicked', { id: this.packageData.id })
							uni.showToast({ title: '取件成功', icon: 'success' })
							setTimeout(() => { uni.navigateBack() }, 1200)
						} catch (e) {
							uni.showToast({ title: '操作失败', icon: 'none' })
						}
					}
				}
			})
		}
	}
};
</script>

<style scoped>
.detail-page {
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

/* 信息卡片 */
.info-card, .track-card {
	background: rgba(255,255,255,0.92);
	backdrop-filter: blur(12rpx);
	border-radius: 44rpx;
	padding: 32rpx;
	margin-bottom: 32rpx;
	box-shadow: 0 20rpx 40rpx -16rpx rgba(0,0,0,0.08), 0 0 0 1rpx rgba(107,140,66,0.1);
}

.card-header {
	display: flex;
	justify-content: space-between;
	align-items: baseline;
	margin-bottom: 24rpx;
}

.company {
	font-size: 36rpx;
	font-weight: 700;
	color: #1f2e3a;
}

.status {
	font-size: 26rpx;
	padding: 6rpx 20rpx;
	border-radius: 60rpx;
	font-weight: 500;
}

.status.pending {
	color: #e6892e;
	background: rgba(230,137,46,0.12);
}
.status.in_transit {
	color: #3b82f6;
	background: rgba(59,130,246,0.12);
}
.status.delivering {
	color: #8b5cf6;
	background: rgba(139,92,246,0.12);
}
.status.picked {
	color: #8b9bae;
	background: rgba(0,0,0,0.04);
}

.divider {
	height: 1rpx;
	background: rgba(107,140,66,0.2);
	margin: 20rpx 0;
}

.info-row {
	display: flex;
	justify-content: space-between;
	align-items: baseline;
	padding: 16rpx 0;
	border-bottom: 1rpx solid rgba(107,140,66,0.1);
}

.info-row:last-child {
	border-bottom: none;
}

.label {
	font-size: 28rpx;
	color: #7c8f9e;
}

.value {
	font-size: 28rpx;
	color: #1c2e3a;
	font-weight: 500;
}

.status-desc {
	color: #6b8c42;
}

/* 物流轨迹 */
.track-title {
	font-size: 32rpx;
	font-weight: 700;
	color: #1f2e3a;
	margin-bottom: 24rpx;
}

.track-list {
	display: flex;
	flex-direction: column;
	gap: 28rpx;
}

.track-item {
	display: flex;
	gap: 24rpx;
}

.track-dot {
	width: 16rpx;
	height: 16rpx;
	border-radius: 8rpx;
	background: #d4e0cc;
	margin-top: 8rpx;
	flex-shrink: 0;
}

.track-dot.active {
	background: #6b8c42;
	width: 20rpx;
	height: 20rpx;
	border-radius: 10rpx;
	box-shadow: 0 0 0 6rpx rgba(107,140,66,0.2);
}

.track-content {
	flex: 1;
}

.track-time {
	font-size: 24rpx;
	color: #9aabb8;
	display: block;
	margin-bottom: 8rpx;
}

.track-desc {
	font-size: 28rpx;
	color: #1f2e3a;
	line-height: 1.4;
}

/* 操作按钮 */
.action-btn {
	position: relative;
	z-index: 2;
	margin: 40rpx 0 20rpx;
}

.pickup-btn {
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
}

.pickup-btn:active {
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
</style>
