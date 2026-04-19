<template>
	<view class="map-container">
		<map
			id="communityMap"
			:longitude="longitude"
			:latitude="latitude"
			:scale="currentScale"
			:markers="markers"
			:show-location="showLocation"
			:enable-traffic="enableTraffic"
			@markertap="onMarkerTap"
			@regionchange="onRegionChange"
			class="map"
		></map>
		<view class="map-controls">
			<view class="control-btn" @click="zoomIn">
				<text>+</text>
			</view>
			<view class="control-btn" @click="zoomOut">
				<text>-</text>
			</view>
			<view class="control-btn location" @click="moveToLocation">
				<text>⊙</text>
			</view>
		</view>
	</view>
</template>

<script>
export default {
	name: 'CommunityMap',
	props: {
		longitude: {
			type: Number,
			default: 121.5005
		},
		latitude: {
			type: Number,
			default: 31.2305
		},
		scale: {
			type: Number,
			default: 15
		},
		markers: {
			type: Array,
			default: () => []
		}
	},
	data() {
		return {
			showLocation: true,
			enableTraffic: false,
			mapContext: null,
			currentScale: this.scale
		}
	},
	watch: {
		scale(val) {
			this.currentScale = val
		}
	},
	mounted() {
		this.mapContext = uni.createMapContext('communityMap', this)
	},
	methods: {
		onMarkerTap(e) {
			const markerId = e.detail.markerId
			const marker = this.markers.find(m => m.id === markerId)
			if (marker) {
				this.$emit('markertap', marker)
			}
		},
		onRegionChange(e) {
			console.log('地图区域变化', e)
		},
		zoomIn() {
			if (this.currentScale < 20) {
				this.currentScale++
			}
		},
		zoomOut() {
			if (this.currentScale > 5) {
				this.currentScale--
			}
		},
		moveToLocation() {
			this.mapContext.moveToLocation({
				success: () => {
					console.log('移动到当前位置成功')
				},
				fail: (err) => {
					console.error('移动到当前位置失败', err)
				}
			})
		}
	}
}
</script>

<style scoped>
.map-container {
	position: relative;
	width: 100%;
	height: 400rpx;
	border-radius: 24rpx;
	overflow: hidden;
}

.map {
	width: 100%;
	height: 100%;
}

.map-controls {
	position: absolute;
	right: 20rpx;
	top: 50%;
	transform: translateY(-50%);
	display: flex;
	flex-direction: column;
	gap: 16rpx;
	z-index: 10;
}

.control-btn {
	width: 60rpx;
	height: 60rpx;
	background-color: #fff;
	border-radius: 50%;
	display: flex;
	align-items: center;
	justify-content: center;
	box-shadow: 0 4rpx 12rpx rgba(0, 0, 0, 0.15);
}

.control-btn text {
	font-size: 36rpx;
	color: #333;
}

.control-btn.location text {
	font-size: 28rpx;
}
</style>
