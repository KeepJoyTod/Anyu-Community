<template>
	<view class="repair-page">
		<view class="header">
			<text class="title">报事报修</text>
			<text class="subtitle">快速报修，及时响应</text>
		</view>

		<view class="action-bar">
			<view class="add-btn" @click="showAddForm = true">
				<text class="btn-icon">+</text>
				<text class="btn-text">发起报修</text>
			</view>
		</view>

		<view class="repair-list">
			<view class="list-header">
				<text class="list-title">报修记录</text>
			</view>
			
			<view v-if="repairList.length === 0" class="empty-tip">
				<text>暂无报修记录</text>
			</view>
			
			<view v-else class="repair-item" v-for="(item, index) in repairList" :key="item.orderId" @click="goDetail(item)">
				<view class="item-header">
					<text class="repair-title">{{ item.repairDesc ? item.repairDesc.substring(0, 20) + (item.repairDesc.length > 20 ? '...' : '') : '无描述' }}</text>
					<text class="status" :class="{'pending': item.orderStatus === 'pending', 'assigned': item.orderStatus === 'assigned', 'processing': item.orderStatus === 'processing', 'completed': item.orderStatus === 'completed', 'cancelled': item.orderStatus === 'cancelled'}">{{ getStatusText(item.orderStatus) }}</text>
				</view>
				<view class="item-info">
					<text class="info-row">类型：{{ getTypeText(item.repairType) }}</text>
					<text class="info-row">地址：{{ item.repairAddress || '未填写' }}</text>
					<text class="info-row">时间：{{ formatTime(item.createTime) }}</text>
				</view>
				<view class="item-actions" v-if="item.orderStatus === 'pending'">
					<view class="action-btn cancel" @click.stop="cancelRepair(item.orderId)">
						<text>取消报修</text>
					</view>
				</view>
			</view>
		</view>

		<view class="popup-mask" v-if="showAddForm" @click="showAddForm = false">
			<view class="popup-content" @click.stop>
				<view class="popup-header">
					<text class="popup-title">发起报修</text>
					<text class="close-btn" @click="showAddForm = false">×</text>
				</view>
				<view class="form">
					<view class="form-item">
						<text class="label">报修类型 *</text>
						<picker mode="selector" :range="typeOptions" range-key="label" @change="onTypeChange">
							<view class="picker">{{ selectedTypeLabel }}</view>
						</picker>
					</view>
					<view class="form-item">
						<text class="label">报修地址 *</text>
						<input class="input" v-model="form.repairAddress" placeholder="请输入详细地址" />
					</view>
					<view class="form-item">
						<text class="label">问题描述 *</text>
						<textarea class="textarea" v-model="form.repairDesc" placeholder="请详细描述问题情况"></textarea>
					</view>
					<view class="form-item">
						<text class="label">联系电话</text>
						<input class="input" v-model="form.userPhone" placeholder="请输入联系电话" type="tel" />
					</view>
					<view class="submit-btn" @click="submitForm">
						<text>提交报修</text>
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
			repairList: [],
			showAddForm: false,
			typeOptions: [
				{ value: 'water', label: '水电维修' },
				{ value: 'furniture', label: '家电维修' },
				{ value: 'decoration', label: '装修维修' },
				{ value: 'other', label: '其他' }
			],
			form: {
				repairType: '',
				repairAddress: '',
				repairDesc: '',
				userPhone: '',
				userName: '',
				userRoom: '',
				userId: ''
			}
		}
	},
	computed: {
		selectedTypeLabel() {
			if (!this.form.repairType) {
				return '请选择报修类型'
			}
			const item = this.typeOptions.find(t => t.value === this.form.repairType)
			return item ? item.label : '请选择报修类型'
		}
	},
	onLoad() {
		this.checkLogin()
		this.loadRepairList()
	},
	onShow() {
		this.loadRepairList()
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
		async loadRepairList() {
			try {
				const res = await homeApi.getRepairList({})
				console.log('报修列表响应:', res)
				this.repairList = res.rows || res.data || []
			} catch (e) {
				console.error('加载报修列表失败:', e)
				this.repairList = []
			}
		},
		formatTime(time) {
			if (!time) return '未知'
			const d = new Date(time)
			return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')} ${String(d.getHours()).padStart(2, '0')}:${String(d.getMinutes()).padStart(2, '0')}`
		},
		getStatusClass(status) {
			const map = {
				'pending': 'pending',
				'assigned': 'assigned',
				'processing': 'processing',
				'completed': 'completed',
				'cancelled': 'cancelled'
			}
			return map[status] || 'pending'
		},
		getStatusText(status) {
			const map = {
				'pending': '待派单',
				'assigned': '已派单',
				'processing': '维修中',
				'completed': '已完成',
				'cancelled': '已取消'
			}
			return map[status] || '未知'
		},
		getTypeText(type) {
			const item = this.typeOptions.find(t => t.value === type)
			return item ? item.label : '未选择'
		},
		onTypeChange(e) {
			const index = e.detail.value
			this.form.repairType = this.typeOptions[index].value
		},
		async submitForm() {
			if (!this.form.repairType) {
				uni.showToast({ title: '请选择报修类型', icon: 'none' })
				return
			}
			if (!this.form.repairAddress) {
				uni.showToast({ title: '请输入报修地址', icon: 'none' })
				return
			}
			if (!this.form.repairDesc) {
				uni.showToast({ title: '请描述问题', icon: 'none' })
				return
			}
			
			uni.showLoading({ title: '提交中...' })
			try {
				await homeApi.createRepair(this.form)
				uni.hideLoading()
				uni.showToast({ title: '提交成功', icon: 'success' })
				this.showAddForm = false
				this.resetForm()
				this.loadRepairList()
			} catch (e) {
				uni.hideLoading()
				uni.showToast({ title: '提交失败', icon: 'none' })
			}
		},
		resetForm() {
			this.form = {
				repairType: '',
				repairAddress: '',
				repairDesc: '',
				userPhone: '',
				userName: '',
				userRoom: '',
				userId: ''
			}
		},
		goDetail(item) {
			let content = `类型：${this.getTypeText(item.repairType)}\n状态：${this.getStatusText(item.orderStatus)}\n描述：${item.repairDesc || '无'}\n地址：${item.repairAddress || '无'}`
			
			if (item.repair && item.orderStatus !== 'pending') {
				content += `\n\n维修人员：${item.repair.repairmanName || '未分配'}`
				content += `\n联系电话：${item.repair.repairmanPhone || '无'}`
				if (item.repair.repairCost) {
					content += `\n维修费用：¥${item.repair.repairCost}`
				}
				if (item.repair.repairResult) {
					content += `\n维修结果：${item.repair.repairResult}`
				}
			}
			
			uni.showModal({
				title: '报修详情',
				content: content,
				showCancel: false
			})
		},
		cancelRepair(orderId) {
			uni.showModal({
				title: '确认取消',
				content: '确定要取消这条报修吗？',
				success: async (res) => {
					if (res.confirm) {
						try {
							await homeApi.cancelRepair(orderId)
							uni.showToast({ title: '取消成功', icon: 'success' })
							this.loadRepairList()
						} catch (e) {
							uni.showToast({ title: '取消失败', icon: 'none' })
						}
					}
				}
			})
		}
	}
}
</script>

<style scoped>
.repair-page {
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

.action-bar {
	margin-bottom: 30rpx;
}

.add-btn {
	background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
	border-radius: 20rpx;
	padding: 24rpx;
	display: flex;
	align-items: center;
	justify-content: center;
}

.btn-icon {
	font-size: 36rpx;
	color: #fff;
	margin-right: 12rpx;
}

.btn-text {
	font-size: 30rpx;
	color: #fff;
	font-weight: 500;
}

.repair-list {
	background-color: #fff;
	border-radius: 24rpx;
	padding: 30rpx;
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

.repair-item {
	background-color: #f8f9fc;
	border-radius: 16rpx;
	padding: 24rpx;
	margin-bottom: 20rpx;
}

.item-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 16rpx;
}

.repair-title {
	font-size: 32rpx;
	font-weight: bold;
	color: #1f2d3d;
	flex: 1;
	margin-right: 20rpx;
}

.status {
	font-size: 24rpx;
	padding: 6rpx 16rpx;
	border-radius: 20rpx;
	white-space: nowrap;
}

.status.pending {
	background-color: #fff3e0;
	color: #e67e22;
}

.status.assigned {
	background-color: #e8eaf6;
	color: #3f51b5;
}

.status.processing {
	background-color: #e3f2fd;
	color: #1976d2;
}

.status.completed {
	background-color: #e8f5e9;
	color: #388e3c;
}

.status.cancelled {
	background-color: #f5f5f5;
	color: #9e9e9e;
}

.item-info {
	margin-bottom: 16rpx;
}

.info-row {
	font-size: 26rpx;
	color: #5a6e7c;
	display: block;
	line-height: 1.8;
}

.item-actions {
	display: flex;
	justify-content: flex-end;
}

.action-btn {
	padding: 12rpx 32rpx;
	border-radius: 30rpx;
	font-size: 26rpx;
}

.action-btn.cancel {
	background-color: #ffebee;
	color: #e53935;
}

.popup-mask {
	position: fixed;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background-color: rgba(0, 0, 0, 0.5);
	display: flex;
	align-items: flex-end;
	z-index: 999;
}

.popup-content {
	background-color: #fff;
	border-radius: 32rpx 32rpx 0 0;
	width: 100%;
	max-height: 80vh;
	overflow-y: auto;
}

.popup-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 30rpx;
	border-bottom: 1rpx solid #eee;
}

.popup-title {
	font-size: 34rpx;
	font-weight: bold;
	color: #1f2d3d;
}

.close-btn {
	font-size: 48rpx;
	color: #8a9aa8;
	line-height: 1;
}

.form {
	padding: 30rpx;
}

.form-item {
	margin-bottom: 30rpx;
}

.label {
	font-size: 28rpx;
	color: #3c4b5a;
	display: block;
	margin-bottom: 12rpx;
}

.input {
	background-color: #f5f7fa;
	border-radius: 12rpx;
	padding: 24rpx;
	font-size: 28rpx;
}

.textarea {
	background-color: #f5f7fa;
	border-radius: 12rpx;
	padding: 24rpx;
	font-size: 28rpx;
	width: 100%;
	height: 150rpx;
	box-sizing: border-box;
}

.picker {
	background-color: #f5f7fa;
	border-radius: 12rpx;
	padding: 24rpx;
	font-size: 28rpx;
	color: #3c4b5a;
}

.submit-btn {
	background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
	border-radius: 20rpx;
	padding: 28rpx;
	text-align: center;
	margin-top: 40rpx;
}

.submit-btn text {
	font-size: 32rpx;
	color: #fff;
	font-weight: 500;
}
</style>
