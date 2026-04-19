<template>
	<view class="visitor-page">
		<view class="header">
			<text class="title">访客邀请</text>
			<text class="subtitle">邀请访客，便捷通行</text>
		</view>

		<view class="action-bar">
			<view class="add-btn" @click="showAddForm = true">
				<text class="btn-icon">+</text>
				<text class="btn-text">新增邀请</text>
			</view>
		</view>

		<view class="visitor-list">
			<view class="list-header">
				<text class="list-title">邀请记录</text>
			</view>
			
			<view v-if="visitorList.length === 0" class="empty-tip">
				<text>暂无访客邀请记录</text>
			</view>
			
			<view v-else class="visitor-item" v-for="(item, index) in visitorList" :key="item.id">
				<view class="item-header">
					<text class="visitor-name">{{ item.visitorName }}</text>
					<text class="status" :class="{'pending': item.status === '0', 'visited': item.status === '1', 'left': item.status === '2', 'cancelled': item.status === '3'}">{{ getStatusText(item.status) }}</text>
				</view>
				<view class="item-info">
					<text class="info-row">电话：{{ item.visitorPhone || '未填写' }}</text>
					<text class="info-row">事由：{{ item.visitReason || '未填写' }}</text>
					<text class="info-row">预计来访：{{ formatTime(item.expectedVisitTime) }}</text>
					<text class="info-row" v-if="item.inviteCode">邀请码：{{ item.inviteCode }}</text>
				</view>
				<view class="item-actions">
					<view class="action-btn delete" @click="deleteVisitor(item.id)" v-if="item.status === '0'">
						<text>删除</text>
					</view>
				</view>
			</view>
		</view>

		<view class="popup-mask" v-if="showAddForm" @click="showAddForm = false">
			<view class="popup-content" @click.stop>
				<view class="popup-header">
					<text class="popup-title">新增访客邀请</text>
					<text class="close-btn" @click="showAddForm = false">×</text>
				</view>
				<view class="form">
					<view class="form-item">
						<text class="label">访客姓名 *</text>
						<input class="input" v-model="form.visitorName" placeholder="请输入访客姓名" />
					</view>
					<view class="form-item">
						<text class="label">访客电话</text>
						<input class="input" v-model="form.visitorPhone" placeholder="请输入访客电话" type="tel" />
					</view>
					<view class="form-item">
						<text class="label">身份证号</text>
						<input class="input" v-model="form.visitorIdCard" placeholder="请输入身份证号（选填）" />
					</view>
					<view class="form-item">
						<text class="label">来访事由</text>
						<textarea class="textarea" v-model="form.visitReason" placeholder="请输入来访事由"></textarea>
					</view>
					<view class="form-item">
						<text class="label">预计来访时间</text>
						<picker mode="multiSelector" :range="dateTimeRange" :value="dateTimeValue" @change="onDateTimeChange" @columnchange="onColumnChange">
							<view class="picker">{{ selectedDateTime }}</view>
						</picker>
					</view>
					<view class="submit-btn" @click="submitForm">
						<text>提交邀请</text>
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
			visitorList: [],
			showAddForm: false,
			form: {
				visitorName: '',
				visitorPhone: '',
				visitorIdCard: '',
				visitReason: '',
				expectedVisitTime: ''
			},
			dateTimeRange: [[], [], [], []],
			dateTimeValue: [0, 0, 0, 0]
		}
	},
	computed: {
		selectedDateTime() {
			if (!this.form.expectedVisitTime) {
				return '请选择时间'
			}
			const d = new Date(this.form.expectedVisitTime)
			return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')} ${String(d.getHours()).padStart(2, '0')}:${String(d.getMinutes()).padStart(2, '0')}`
		}
	},
	onLoad() {
		this.checkLogin()
		this.initDateTimeRange()
		this.loadVisitorList()
	},
	onShow() {
		this.loadVisitorList()
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
		async loadVisitorList() {
			try {
				const res = await homeApi.getVisitorList({})
				console.log('访客列表响应:', res)
				this.visitorList = res.rows || res.data || []
			} catch (e) {
				console.error('加载访客列表失败:', e)
				this.visitorList = []
			}
		},
		initDateTimeRange() {
			const now = new Date()
			const dates = []
			const hours = []
			const minutes = []
			
			for (let i = 0; i < 7; i++) {
				const d = new Date(now.getTime() + i * 24 * 60 * 60 * 1000)
				dates.push(`${d.getMonth() + 1}月${d.getDate()}日`)
			}
			for (let i = 0; i < 24; i++) {
				hours.push(`${i}时`)
			}
			for (let i = 0; i < 60; i += 10) {
				minutes.push(`${i}分`)
			}
			
			this.dateTimeRange = [dates, hours, minutes]
		},
		onDateTimeChange(e) {
			const val = e.detail.value
			const now = new Date()
			const selectedDate = new Date(now.getTime() + val[0] * 24 * 60 * 60 * 1000)
			const year = selectedDate.getFullYear()
			const month = selectedDate.getMonth()
			const day = selectedDate.getDate()
			const hour = val[1]
			const minute = val[2] * 10
			
			this.form.expectedVisitTime = new Date(year, month, day, hour, minute)
			this.dateTimeValue = val
		},
		onColumnChange(e) {
			this.dateTimeValue[e.detail.column] = e.detail.value
		},
		formatTime(time) {
			if (!time) return '未设置'
			const d = new Date(time)
			return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}-${String(d.getDate()).padStart(2, '0')} ${String(d.getHours()).padStart(2, '0')}:${String(d.getMinutes()).padStart(2, '0')}:${String(d.getSeconds()).padStart(2, '0')}`
		},
		formatDateTime(time) {
			if (!time) return ''
			return this.formatTime(time)
		},
		getStatusClass(status) {
			const map = {
				'0': 'pending',
				'1': 'arrived',
				'2': 'left',
				'3': 'cancelled'
			}
			return map[status] || 'pending'
		},
		getStatusText(status) {
			const map = {
				'0': '待来访',
				'1': '已到访',
				'2': '已离开',
				'3': '已取消'
			}
			return map[status] || '未知'
		},
		async submitForm() {
			if (!this.form.visitorName) {
				uni.showToast({ title: '请输入访客姓名', icon: 'none' })
				return
			}
			
			uni.showLoading({ title: '提交中...' })
			try {
				const data = {
					visitorName: this.form.visitorName,
					visitorPhone: this.form.visitorPhone,
					visitorIdCard: this.form.visitorIdCard,
					visitReason: this.form.visitReason
				}
				if (this.form.expectedVisitTime) {
					data.expectedVisitTime = this.formatTime(this.form.expectedVisitTime)
				}
				console.log('提交访客数据:', data)
				await homeApi.createVisitor(data)
				uni.hideLoading()
				uni.showToast({ title: '邀请成功', icon: 'success' })
				this.showAddForm = false
				this.resetForm()
				this.loadVisitorList()
			} catch (e) {
				uni.hideLoading()
				console.error('提交访客失败:', e)
				uni.showToast({ title: e.msg || '提交失败', icon: 'none' })
			}
		},
		resetForm() {
			this.form = {
				visitorName: '',
				visitorPhone: '',
				visitorIdCard: '',
				visitReason: '',
				expectedVisitTime: ''
			}
		},
		deleteVisitor(id) {
			uni.showModal({
				title: '确认删除',
				content: '确定要删除这条访客邀请吗？',
				success: async (res) => {
					if (res.confirm) {
						try {
							await homeApi.deleteVisitor(id)
							uni.showToast({ title: '删除成功', icon: 'success' })
							this.loadVisitorList()
						} catch (e) {
							uni.showToast({ title: '删除失败', icon: 'none' })
						}
					}
				}
			})
		}
	}
}
</script>

<style scoped>
.visitor-page {
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
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
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

.visitor-list {
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

.visitor-item {
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

.visitor-name {
	font-size: 32rpx;
	font-weight: bold;
	color: #1f2d3d;
}

.status {
	font-size: 24rpx;
	padding: 6rpx 16rpx;
	border-radius: 20rpx;
}

.status.pending {
	background-color: #fff3e0;
	color: #e67e22;
}

.status.arrived {
	background-color: #e3f2fd;
	color: #1976d2;
}

.status.left {
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

.action-btn.delete {
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
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
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
