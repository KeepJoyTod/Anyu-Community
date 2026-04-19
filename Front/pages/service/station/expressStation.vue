<template>
	<view class="express-page">
		<!-- 背景装饰 -->
		<view class="bg-blur"></view>

		<!-- 顶部返回栏 -->
		<view class="nav-bar">
			<view class="back-btn" @click="goBack">
				<text class="back-icon">←</text>
			</view>
			<text class="nav-title">快递驿站</text>
			<view class="placeholder"></view>
		</view>

		<!-- 统计卡片：待取件数量 -->
		<view class="stats-card">
			<view class="stats-icon">📦</view>
			<view class="stats-content">
				<text class="stats-label">待取包裹</text>
				<text class="stats-number">{{ pendingCount }}</text>
			</view>
		</view>

		<!-- 包裹列表（已排序：已取件自动移到最后） -->
		<scroll-view scroll-y class="package-list" v-if="sortedPackages.length">
			<view
				v-for="item in sortedPackages"
				:key="item.id"
				class="package-card"
				:class="[item.status]"
				@click="goDetail(item.id)"
			>
				<view class="card-left">
					<view class="express-icon">{{ item.expressIcon }}</view>
					<view class="card-info">
						<text class="company">{{ item.company }}</text>
						<text v-if="item.pickupCode" class="code">取件码：{{ item.pickupCode }}</text>
						<text class="tracking" v-else>运单号：{{ item.trackingNo }}</text>
						<text class="time">{{ item.arrivalTime }}</text>
					</view>
				</view>
				<view class="card-right">
					<view class="status" :class="item.status">
						{{ statusText(item.status) }}
					</view>
					<view v-if="item.status === 'pending'" class="pickup-btn" @click.stop="pickup(item.id)">
						取件
					</view>
				</view>
			</view>
		</scroll-view>

		<!-- 空状态 -->
		<view class="empty-state" v-else>
			<text class="empty-icon">📭</text>
			<text class="empty-text">暂无包裹</text>
		</view>

		<!-- 底部提示 -->
		<view class="tips">
			<text class="tips-text">* 包裹到达驿站后，状态会更新为“待取件”，请及时取件</text>
		</view>
	</view>
</template>

<script>
import { listPackages, pickupPackage } from '@/common/api/station'
export default {
	data() {
 		return {
			packages: [],
			loading: false,
			page: 1,
			size: 100
		};
	},
	onLoad() {
		this.fetchPackages()
		uni.$on('packagePicked', this.onPackagePicked)
	},
	onUnload() {
		uni.$off('packagePicked', this.onPackagePicked)
	},
 	computed: {
		pendingCount() {
			return this.packages.filter(item => item.status === 'pending').length;
		},
		// 已取件的包裹自动排到最后
		sortedPackages() {
			return [...this.packages].sort((a, b) => {
				if (a.status === 'picked' && b.status !== 'picked') return 1;
				if (a.status !== 'picked' && b.status === 'picked') return -1;
				return 0;
			});
		}
	},
	methods: {
		goBack() {
			uni.navigateBack();
		},
 		async fetchPackages() {
			try {
				this.loading = true
				const res = await listPackages(this.page, this.size)
				const data = res.data || res
				const list = data.list || data || []
				this.packages = list.map(it => ({
					id: it.id,
					company: it.company || it.expressCompany || '',
					expressIcon: '📦',
					pickupCode: it.pickupCode,
					trackingNo: it.trackingNo,
					arrivalTime: it.arrivalTime || it.createdAt || it.time,
					status: it.status || 'pending'
				}))
			} catch (e) {
				uni.showToast({ title: '加载失败', icon: 'none' })
			} finally {
				this.loading = false
			}
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
		pickup(id) {
			const pkg = this.packages.find(item => item.id === id)
			if (!pkg || pkg.status !== 'pending') return
			uni.showModal({
				title: '取件确认',
				content: `确认取走【${pkg.company}】包裹（取件码：${pkg.pickupCode || ''}）吗？`,
				success: async (res) => {
					if (res.confirm) {
						try {
							await pickupPackage(id)
							pkg.status = 'picked'
							uni.$emit('packagePicked', { id })
							uni.showToast({ title: '取件成功', icon: 'success' })
						} catch (e) {
							uni.showToast({ title: '操作失败', icon: 'none' })
						}
					}
				}
			})
		},
		onPackagePicked({ id }) {
			const i = this.packages.findIndex(p => p.id === id)
			if (i !== -1) this.packages[i].status = 'picked'
		},
		goDetail(id) {
			uni.navigateTo({
				url: `/pages/service/station/detail?id=${id}`
			});
		}
	}
};
</script>

<style scoped>
.express-page {
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

/* 统计卡片 */
.stats-card {
	background: rgba(255,255,255,0.92);
	backdrop-filter: blur(12rpx);
	border-radius: 44rpx;
	padding: 28rpx 32rpx;
	margin-bottom: 32rpx;
	display: flex;
	align-items: center;
	gap: 24rpx;
	box-shadow: 0 20rpx 40rpx -16rpx rgba(0,0,0,0.08), 0 0 0 1rpx rgba(107,140,66,0.1);
}

.stats-icon {
	font-size: 64rpx;
	width: 96rpx;
	height: 96rpx;
	background: rgba(107,140,66,0.12);
	border-radius: 48rpx;
	display: flex;
	align-items: center;
	justify-content: center;
}

.stats-content {
	flex: 1;
}

.stats-label {
	font-size: 28rpx;
	color: #7c8f9e;
	display: block;
	margin-bottom: 8rpx;
}

.stats-number {
	font-size: 56rpx;
	font-weight: 800;
	color: #6b8c42;
}

/* 包裹列表 */
.package-list {
	max-height: calc(100vh - 380rpx);
	position: relative;
	z-index: 2;
}

.package-card {
	background: rgba(255,255,255,0.92);
	backdrop-filter: blur(12rpx);
	border-radius: 44rpx;
	padding: 28rpx;
	margin-bottom: 24rpx;
	display: flex;
	justify-content: space-between;
	align-items: center;
	box-shadow: 0 20rpx 40rpx -16rpx rgba(0,0,0,0.08), 0 0 0 1rpx rgba(107,140,66,0.1);
	transition: all 0.2s;
}

.package-card:active {
	transform: scale(0.98);
	background: white;
}

/* 已取件样式 */
.package-card.picked {
	opacity: 0.7;
}

.card-left {
	display: flex;
	align-items: center;
	gap: 20rpx;
	flex: 1;
}

.express-icon {
	font-size: 52rpx;
	width: 80rpx;
	height: 80rpx;
	background: rgba(107,140,66,0.1);
	border-radius: 40rpx;
	display: flex;
	align-items: center;
	justify-content: center;
}

.card-info {
	flex: 1;
}

.company {
	font-size: 32rpx;
	font-weight: 700;
	color: #1f2e3a;
	display: block;
	margin-bottom: 8rpx;
}

.code, .tracking {
	font-size: 26rpx;
	background: rgba(107,140,66,0.1);
	padding: 4rpx 12rpx;
	border-radius: 40rpx;
	display: inline-block;
	margin-bottom: 8rpx;
}

.code {
	color: #6b8c42;
}

.tracking {
	color: #4a5e71;
}

.time {
	font-size: 24rpx;
	color: #9aabb8;
	display: block;
}

.card-right {
	display: flex;
	flex-direction: column;
	align-items: flex-end;
	gap: 12rpx;
}

.status {
	font-size: 24rpx;
	padding: 6rpx 16rpx;
	border-radius: 40rpx;
	font-weight: 500;
}

/* 状态颜色 */
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

.pickup-btn {
	background: linear-gradient(105deg, #6b8c42, #528a34);
	padding: 12rpx 28rpx;
	border-radius: 60rpx;
	font-size: 26rpx;
	font-weight: 600;
	color: white;
	transition: all 0.2s;
}

.pickup-btn:active {
	transform: scale(0.94);
	opacity: 0.9;
}

/* 空状态 */
.empty-state {
	background: rgba(255,255,255,0.9);
	backdrop-filter: blur(8rpx);
	border-radius: 44rpx;
	padding: 100rpx 0;
	text-align: center;
	display: flex;
	flex-direction: column;
	align-items: center;
	gap: 20rpx;
	margin-top: 120rpx;
}

.empty-icon {
	font-size: 80rpx;
	opacity: 0.6;
}

.empty-text {
	font-size: 28rpx;
	color: #9aabb8;
}

/* 提示文字 */
.tips {
	text-align: center;
	margin-top: 32rpx;
}

.tips-text {
	font-size: 24rpx;
	color: #9aabb8;
}
</style>
