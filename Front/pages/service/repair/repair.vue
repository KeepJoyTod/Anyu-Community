<template>
	<view class="repair-page">
		<!-- 背景装饰 -->
		<view class="bg-blur"></view>

		<!-- 顶部返回栏 -->
		<view class="nav-bar">
			<view class="back-btn" @click="goBack">
				<text class="back-icon">←</text>
			</view>
			<text class="nav-title">报事报修</text>
			<view class="placeholder"></view>
		</view>

		<!-- 表单卡片 -->
		<view class="form-card">
			<!-- 维修类型选择 -->
			<view class="type-section">
				<text class="section-label">维修类型</text>
				<scroll-view scroll-x class="type-scroll" show-scrollbar="false">
					<view class="type-list">
						<view
							v-for="(item, idx) in repairTypes"
							:key="idx"
							class="type-item"
							:class="{ active: selectedType === item.value }"
							@click="selectedType = item.value"
						>
							<text class="type-icon">{{ item.icon }}</text>
							<text class="type-name">{{ item.name }}</text>
						</view>
					</view>
				</scroll-view>
			</view>

			<!-- 详细描述 -->
			<view class="form-item">
				<text class="label">问题描述</text>
				<textarea
					class="textarea"
					v-model="form.desc"
					placeholder="请详细描述故障现象（必填）"
					placeholder-class="placeholder"
				/>
			</view>

			<!-- 联系人 -->
			<view class="form-item">
				<text class="label">联系人</text>
				<input
					class="input"
					v-model="form.contactName"
					placeholder="请输入姓名"
					placeholder-class="placeholder"
				/>
			</view>

			<!-- 联系电话 -->
			<view class="form-item">
				<text class="label">联系电话</text>
				<input
					class="input"
					type="number"
					v-model="form.phone"
					placeholder="请输入手机号"
					placeholder-class="placeholder"
				/>
			</view>

            <!-- 预约日期时间（兼容微信小程序） -->
            <view class="form-item">
                <text class="label">预约日期</text>
                <picker mode="date" :value="form.appointmentDate" @change="onDateChange">
                    <view class="picker">{{ form.appointmentDate || '请选择日期' }}</view>
                </picker>
            </view>
            <view class="form-item">
                <text class="label">预约时间</text>
                <picker mode="time" :value="form.appointmentClock" @change="onClockChange">
                    <view class="picker">{{ form.appointmentClock || '请选择时间' }}</view>
                </picker>
            </view>

			<!-- 地址 -->
			<view class="form-item">
				<text class="label">详细地址</text>
				<input
					class="input"
					v-model="form.address"
					placeholder="请输入详细地址"
					placeholder-class="placeholder"
				/>
			</view>

			<!-- 图片上传 -->
			<view class="form-item">
				<text class="label">上传图片</text>
				<view class="image-list">
					<view class="image-item" v-for="(img, idx) in images" :key="idx">
						<image :src="img" mode="aspectFill" class="uploaded-img" @click="previewImage(idx)"></image>
						<view class="delete-btn" @click="deleteImage(idx)">×</view>
					</view>
					<view class="add-image" @click="chooseImage" v-if="images.length < 3">
						<text class="add-icon">+</text>
						<text class="add-text">上传图片</text>
					</view>
				</view>
				<text class="tip-text">最多上传3张图片，支持jpg/png格式</text>
			</view>

			<!-- 提交按钮 -->
			<view class="submit-btn" @click="submit">
				<text>提交报修</text>
			</view>
		</view>

		<!-- 底部提示 -->
		<view class="tips">
			<text class="tips-text">* 提交后，我们会尽快安排师傅联系您，请保持电话畅通</text>
		</view>
	</view>
</template>

<script>
import { createRepair, uploadRepairImage } from '@/api/repair'
export default {
	data() {
		return {
			repairTypes: [
				{ name: '电路维修', value: 'circuit', icon: '⚡' },
				{ name: '下水道疏通', value: 'drain', icon: '🚰' },
				{ name: '空调维修', value: 'ac', icon: '❄️' },
				{ name: '家电维修', value: 'appliance', icon: '🔌' },
				{ name: '门窗维修', value: 'window', icon: '🚪' },
				{ name: '其他', value: 'other', icon: '🔧' }
			],
			selectedType: 'circuit',
            form: {
				desc: '',
				contactName: '',
				phone: '',
                appointmentTime: '',
                appointmentDate: '',
                appointmentClock: '',
				address: ''
			},
            images: []
		};
	},
	methods: {
		goBack() {
			uni.navigateBack();
		},
        onDateChange(e) {
            this.form.appointmentDate = e.detail.value
            this.combineDateTime()
        },
        onClockChange(e) {
            this.form.appointmentClock = e.detail.value
            this.combineDateTime()
        },
        combineDateTime() {
            const d = this.form.appointmentDate
            const t = this.form.appointmentClock
            this.form.appointmentTime = d && t ? `${d} ${t}` : ''
        },
        async chooseImage() {
            const maxCount = 3 - this.images.length
            if (maxCount <= 0) return
            uni.chooseImage({
                count: maxCount,
                sizeType: ['compressed'],
                sourceType: ['album', 'camera'],
                success: async (res) => {
                    const files = res.tempFilePaths || []
                    for (const filePath of files) {
                        try {
                            uni.showLoading({ title: '上传中...' })
                            const url = await uploadRepairImage(filePath)
                            this.images.push(url)
                        } catch (e) {
                            uni.showToast({ title: '图片上传失败', icon: 'none' })
                        } finally {
                            uni.hideLoading()
                        }
                    }
                }
            });
        },
		deleteImage(index) {
			this.images.splice(index, 1);
		},
		previewImage(index) {
			uni.previewImage({
				urls: this.images,
				current: this.images[index]
			});
		},
        async submit() {
			if (!this.form.desc.trim()) {
				uni.showToast({ title: '请填写问题描述', icon: 'none' });
				return;
			}
			if (!this.form.contactName.trim()) {
				uni.showToast({ title: '请填写联系人', icon: 'none' });
				return;
			}
			if (!this.form.phone.trim() || !/^1[3-9]\d{9}$/.test(this.form.phone)) {
				uni.showToast({ title: '请填写正确的手机号', icon: 'none' });
				return;
			}
			if (!this.form.address.trim()) {
				uni.showToast({ title: '请填写详细地址', icon: 'none' });
				return;
			}
            try {
                uni.showLoading({ title: '提交中...' })
                const payload = {
                    repairType: this.selectedType,
                    repairDesc: this.form.desc,
                    userName: this.form.contactName,
                    userPhone: this.form.phone,
                    repairAddress: this.form.address,
                    repairImages: JSON.stringify(this.images),
                    orderStatus: 'pending',
                    createTime: new Date().toLocaleString('zh-CN'),
                    updateTime: new Date().toLocaleString('zh-CN')
                }
                const res = await createRepair(payload)
                uni.hideLoading()
                if (res && (res.code === 200 || res.data.orderId)) {
                    uni.showModal({
                        title: '提交成功',
                        content: '您的报修申请已提交，我们会尽快处理。',
                        showCancel: false,
                        success: () => {
                            
                            setTimeout(() => {
                                uni.navigateBack();
                            }, 800);
                        }
                    })
                } else {
                    uni.showToast({ title: '提交失败，请稍后重试', icon: 'none' })
                }
            } catch (e) {
                uni.hideLoading()
                uni.showToast({ title: '提交失败，请检查网络', icon: 'none' })
            }
		}
	}
};
</script>

<style scoped>
.repair-page {
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

/* 维修类型横向滚动 */
.type-section {
	margin-bottom: 32rpx;
}

.section-label {
	font-size: 28rpx;
	font-weight: 500;
	color: #4a5e71;
	margin-bottom: 16rpx;
	display: block;
}

.type-scroll {
	white-space: nowrap;
}

.type-list {
	display: inline-flex;
	gap: 16rpx;
	padding: 4rpx 0;
}

.type-item {
	display: inline-flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	padding: 16rpx 24rpx;
	background: rgba(107,140,66,0.08);
	border-radius: 60rpx;
	transition: all 0.2s;
	min-width: 120rpx;
}

.type-item.active {
	background: linear-gradient(105deg, #6b8c42, #528a34);
	box-shadow: 0 6rpx 14rpx rgba(107,140,66,0.3);
}

.type-icon {
	font-size: 40rpx;
	margin-bottom: 6rpx;
}

.type-name {
	font-size: 24rpx;
	color: #4a5e71;
}

.type-item.active .type-name {
	color: white;
}

/* 表单字段 */
.form-item {
	margin-bottom: 28rpx;
}

.label {
	font-size: 28rpx;
	color: #4a5e71;
	margin-bottom: 12rpx;
	display: block;
}

.input, .picker, .textarea {
	width: 100%;
	background: rgba(255,255,255,0.8);
	border-radius: 60rpx;
	padding: 20rpx 28rpx;
	font-size: 30rpx;
	color: #1c2e3a;
	box-shadow: inset 0 1rpx 3rpx rgba(0,0,0,0.02), 0 0 0 1rpx rgba(107,140,66,0.1);
}

.textarea {
	border-radius: 32rpx;
	min-height: 200rpx;
}

.picker {
	color: #8b9bae;
}

.placeholder {
	color: #bdc7d0;
}

/* 图片上传区域 */
.image-list {
	display: flex;
	flex-wrap: wrap;
	gap: 20rpx;
	margin-bottom: 12rpx;
}

.image-item {
	position: relative;
	width: 160rpx;
	height: 160rpx;
	border-radius: 24rpx;
	overflow: hidden;
}

.uploaded-img {
	width: 100%;
	height: 100%;
}

.delete-btn {
	position: absolute;
	top: 6rpx;
	right: 6rpx;
	width: 40rpx;
	height: 40rpx;
	background: rgba(0,0,0,0.6);
	border-radius: 20rpx;
	color: white;
	text-align: center;
	line-height: 36rpx;
	font-size: 32rpx;
	font-weight: bold;
	cursor: pointer;
}

.add-image {
	width: 160rpx;
	height: 160rpx;
	background: rgba(107,140,66,0.08);
	border-radius: 24rpx;
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	border: 1rpx dashed rgba(107,140,66,0.4);
}

.add-icon {
	font-size: 56rpx;
	color: #6b8c42;
	line-height: 1;
}

.add-text {
	font-size: 22rpx;
	color: #7c8f9e;
	margin-top: 8rpx;
}

.tip-text {
	font-size: 22rpx;
	color: #9aabb8;
	display: block;
	margin-top: 8rpx;
}

/* 提交按钮 */
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

/* 底部提示 */
.tips {
	text-align: center;
	margin-top: 20rpx;
}

.tips-text {
	font-size: 24rpx;
	color: #9aabb8;
}
</style>
