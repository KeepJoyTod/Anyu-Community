<template>
	<view class="bill-page">
		<view class="header">
			<text class="title">生活缴费</text>
			<text class="subtitle">便捷缴费，轻松生活</text>
		</view>

		<view class="summary-card">
			<view class="summary-item">
				<text class="summary-label">待缴费</text>
				<text class="summary-value">{{ unpaidCount }}笔</text>
			</view>
			<view class="summary-item">
				<text class="summary-label">欠费金额</text>
				<text class="summary-value highlight">¥{{ totalArrears }}</text>
			</view>
		</view>

		<view class="bill-list">
			<view class="list-header">
				<text class="list-title">账单列表</text>
			</view>
			
			<view v-if="billList.length === 0" class="empty-tip">
				<text>暂无账单</text>
			</view>
			
			<view v-else class="bill-item" v-for="(item, index) in billList" :key="item.id">
				<view class="item-left">
					<view class="bill-icon" :class="{'property': item.billType === '1', 'water': item.billType === '2', 'electricity': item.billType === '3', 'gas': item.billType === '4', 'parking': item.billType === '5', 'other': item.billType === '6'}">
						<text>{{ getTypeIcon(item.billType) }}</text>
					</view>
				</view>
				<view class="item-center">
					<text class="bill-type">{{ getTypeText(item.billType) }}</text>
					<text class="bill-no">账单号：{{ item.billNo || '-' }}</text>
					<text class="bill-date">账单日期：{{ formatDate(item.billDate) }}</text>
				</view>
				<view class="item-right">
					<text class="bill-amount">¥{{ item.arrearsAmount || item.billAmount }}</text>
					<text class="bill-status" :class="{'unpaid': item.status === '0', 'partial': item.status === '1', 'paid': item.status === '2'}">{{ getStatusText(item.status) }}</text>
				</view>
			</view>
		</view>

		<view class="payment-records">
			<view class="section-header" @click="showRecords = !showRecords">
				<text class="section-title">缴费记录</text>
				<text class="toggle">{{ showRecords ? '收起' : '展开' }}</text>
			</view>
			
			<view v-if="showRecords && paymentRecords.length === 0" class="empty-tip">
				<text>暂无缴费记录</text>
			</view>
			
			<view v-if="showRecords && paymentRecords.length > 0" class="record-list">
				<view class="record-item" v-for="(item, index) in paymentRecords" :key="item.id">
					<view class="record-info">
						<text class="record-amount">¥{{ item.payAmount }}</text>
						<text class="record-method">{{ getPayMethodText(item.payMethod) }}</text>
					</view>
					<view class="record-time">
						<text>{{ formatTime(item.payTime) }}</text>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
import homeApi from '@/api/home.js'
import config from '@/api/config.js'

export default {
	data() {
		return {
			billList: [],
			paymentRecords: [],
			showRecords: false,
			typeOptions: [
				{ value: '1', label: '物业费', icon: '🏢' },
				{ value: '2', label: '水费', icon: '💧' },
				{ value: '3', label: '电费', icon: '⚡' },
				{ value: '4', label: '燃气费', icon: '🔥' },
				{ value: '5', label: '停车费', icon: '🚗' },
				{ value: '6', label: '其他', icon: '📋' }
			]
		}
	},
	computed: {
		unpaidCount() {
			return this.billList.filter(b => b.status !== '2').length
		},
		totalArrears() {
			const total = this.billList
				.filter(b => b.status !== '2')
				.reduce((sum, b) => sum + (parseFloat(b.arrearsAmount) || parseFloat(b.billAmount) || 0), 0)
			return total.toFixed(2)
		}
	},
	onLoad() {
		this.checkLogin()
		this.loadBillList()
		this.loadPaymentRecords()
	},
	onShow() {
		this.loadBillList()
	},
	methods: {
		checkLogin() {
			const token = uni.getStorageSync(config.TOKEN_KEY)
			if (!token) {
				uni.showModal({
					title: '提示',
					content: '请先登录',
					showCancel: false,
					success: () => {
						uni.redirectTo({ url: '/pages/login/login' })
					}
				})
			}
		},
		async loadBillList() {
			try {
				const res = await homeApi.getBillList({})
				console.log('账单列表响应:', res)
				this.billList = res.rows || res.data || []
			} catch (e) {
				console.error('加载账单列表失败:', e)
				this.billList = []
			}
		},
		async loadPaymentRecords() {
			try {
				const res = await homeApi.getPaymentRecords({})
				console.log('缴费记录响应:', res)
				this.paymentRecords = res.rows || res.data || []
			} catch (e) {
				console.error('加载缴费记录失败:', e)
				this.paymentRecords = []
			}
		},
		formatDate(date) {
			if (!date) return '-'
			const d = new Date(date)
			return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')}`
		},
		formatTime(time) {
			if (!time) return '-'
			const d = new Date(time)
			return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')} ${String(d.getHours()).padStart(2, '0')}:${String(d.getMinutes()).padStart(2, '0')}`
		},
		getTypeText(type) {
			const item = this.typeOptions.find(t => t.value === type)
			return item ? item.label : '其他'
		},
		getTypeIcon(type) {
			const item = this.typeOptions.find(t => t.value === type)
			return item ? item.icon : '📋'
		},
		getTypeClass(type) {
			const map = {
				'1': 'property',
				'2': 'water',
				'3': 'electricity',
				'4': 'gas',
				'5': 'parking',
				'6': 'other'
			}
			return map[type] || 'other'
		},
		getStatusClass(status) {
			const map = {
				'0': 'unpaid',
				'1': 'partial',
				'2': 'paid'
			}
			return map[status] || 'unpaid'
		},
		getStatusText(status) {
			const map = {
				'0': '待支付',
				'1': '部分支付',
				'2': '已支付'
			}
			return map[status] || '未知'
		},
		getPayMethodText(method) {
			const map = {
				'1': '微信支付',
				'2': '支付宝',
				'3': '银行卡',
				'4': '现金'
			}
			return map[method] || '其他'
		}
	}
}
</script>

<style scoped>
.bill-page {
	background-color: #f5f7fa;
	min-height: 100vh;
	padding: 30rpx;
}

.header {
	margin-bottom: 30rpx;
}

.title {
	font-size: 44rpx;
	font-weight: bold;
	color: #1f2d3d;
	display: block;
}

.subtitle {
	font-size: 28rpx;
	color: #8a9aa8;
	display: block;
	margin-top: 8rpx;
}

.summary-card {
	display: flex;
	background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
	border-radius: 24rpx;
	padding: 32rpx;
	margin-bottom: 30rpx;
}

.summary-item {
	flex: 1;
	text-align: center;
}

.summary-label {
	font-size: 26rpx;
	color: rgba(255, 255, 255, 0.8);
	display: block;
	margin-bottom: 12rpx;
}

.summary-value {
	font-size: 40rpx;
	font-weight: bold;
	color: #fff;
}

.summary-value.highlight {
	color: #fff;
}

.bill-list {
	background-color: #fff;
	border-radius: 24rpx;
	padding: 30rpx;
	margin-bottom: 30rpx;
}

.list-header {
	margin-bottom: 24rpx;
}

.list-title {
	font-size: 32rpx;
	font-weight: bold;
	color: #1f2d3d;
}

.empty-tip {
	text-align: center;
	padding: 60rpx 0;
	color: #8a9aa8;
}

.bill-item {
	display: flex;
	align-items: center;
	padding: 24rpx 0;
	border-bottom: 1rpx solid #f0f2f5;
}

.bill-item:last-child {
	border-bottom: none;
}

.item-left {
	margin-right: 24rpx;
}

.bill-icon {
	width: 80rpx;
	height: 80rpx;
	border-radius: 20rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	font-size: 36rpx;
}

.bill-icon.property { background-color: #e3f2fd; }
.bill-icon.water { background-color: #e0f7fa; }
.bill-icon.electricity { background-color: #fff8e1; }
.bill-icon.gas { background-color: #ffebee; }
.bill-icon.parking { background-color: #f3e5f5; }
.bill-icon.other { background-color: #f5f5f5; }

.item-center {
	flex: 1;
}

.bill-type {
	font-size: 30rpx;
	font-weight: bold;
	color: #1f2d3d;
	display: block;
	margin-bottom: 8rpx;
}

.bill-no, .bill-date {
	font-size: 24rpx;
	color: #8a9aa8;
	display: block;
	line-height: 1.6;
}

.item-right {
	text-align: right;
}

.bill-amount {
	font-size: 36rpx;
	font-weight: bold;
	color: #e53935;
	display: block;
	margin-bottom: 8rpx;
}

.bill-status {
	font-size: 24rpx;
	padding: 4rpx 12rpx;
	border-radius: 20rpx;
}

.bill-status.unpaid {
	background-color: #ffebee;
	color: #e53935;
}

.bill-status.partial {
	background-color: #fff3e0;
	color: #e67e22;
}

.bill-status.paid {
	background-color: #e8f5e9;
	color: #388e3c;
}

.payment-records {
	background-color: #fff;
	border-radius: 24rpx;
	padding: 30rpx;
}

.section-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 24rpx;
}

.section-title {
	font-size: 32rpx;
	font-weight: bold;
	color: #1f2d3d;
}

.toggle {
	font-size: 26rpx;
	color: #667eea;
}

.record-list {
	border-top: 1rpx solid #f0f2f5;
	padding-top: 20rpx;
}

.record-item {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 20rpx 0;
	border-bottom: 1rpx solid #f5f5f5;
}

.record-item:last-child {
	border-bottom: none;
}

.record-info {
	display: flex;
	flex-direction: column;
}

.record-amount {
	font-size: 30rpx;
	font-weight: bold;
	color: #1f2d3d;
}

.record-method {
	font-size: 24rpx;
	color: #8a9aa8;
	margin-top: 4rpx;
}

.record-time {
	font-size: 24rpx;
	color: #8a9aa8;
}
</style>
