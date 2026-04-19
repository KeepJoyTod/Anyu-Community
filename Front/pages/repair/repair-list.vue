<template>
	<view class="repair-page">
		<!-- 标签栏 -->
		<view class="tab-bar">
			<view class="tab-item" :class="{ active: currentTab === 0 }" @click="switchTab(0)">
				已取消
			</view>
			<view class="tab-item" :class="{ active: currentTab === 1 }" @click="switchTab(1)">
				维修中
			</view>
			<view class="tab-item" :class="{ active: currentTab === 2 }" @click="switchTab(2)">
				已完成
			</view>
		</view>

		<!-- 表格列表 -->
		<view class="table-box">
			<view v-if="currentList.length === 0" class="empty-tip">暂无数据</view>
			<view v-else class="table-list">
				<view v-for="item in currentList" :key="item.id" class="table-row" @click="goToDetail(item)">
					<view class="table-cell table-title">{{ item.title }}</view>
					<view class="table-cell table-time">{{ item.time }}</view>
					<view class="table-cell table-status">
						<text :class="['status-tag', item.status]">{{ item.statusText }}</text>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				currentTab: 0, // 0:未处理 1:进行中 2:已处理
				repairList: {
					// 模拟数据
					0: [{
							id: 1,
							title: '楼道灯损坏',
							time: '2025-03-24 10:30',
							status: 'untreated',
							statusText: '未处理'
						},
						{
							id: 2,
							title: '电梯故障',
							time: '2025-03-24 11:15',
							status: 'untreated',
							statusText: '未处理'
						}
					],
					1: [{
						id: 3,
						title: '水管漏水',
						time: '2025-03-23 09:20',
						status: 'processing',
						statusText: '进行中'
					}],
					2: [{
							id: 4,
							title: '门禁失灵',
							time: '2025-03-22 16:45',
							status: 'finished',
							statusText: '已处理'
						},
						{
							id: 5,
							title: '墙面开裂',
							time: '2025-03-21 14:10',
							status: 'finished',
							statusText: '已处理'
						}
					]
				}
			}
		},
		computed: {
			currentList() {
				return this.repairList[this.currentTab] || []
			}
		},
		methods: {

			switchTab(index) {
				this.currentTab = index
			},

			goToDetail(item) {
				uni.showToast({
					title: `查看${item.title}详情`,
					icon: 'none'
				})
				// 实际项目可跳转到详情页：
				// uni.navigateTo({ url: `/pages/repair/repair-detail?id=${item.id}` })
			}
		}
	}
</script>

<style scoped>
	.repair-page {
		background-color: #f5f7fa;
		min-height: 100vh;
	}

	/* 标签栏 */
	.tab-bar {
		display: flex;
		background-color: #fff;
		margin-bottom: 20rpx;
	}

	.tab-item {
		flex: 1;
		text-align: center;
		padding: 30rpx 0;
		font-size: 30rpx;
		color: #666;
		position: relative;
	}

	.tab-item.active {
		color: #1890ff;
		font-weight: bold;
	}

	.tab-item.active::after {
		content: '';
		position: absolute;
		bottom: 0;
		left: 50%;
		transform: translateX(-50%);
		width: 80rpx;
		height: 4rpx;
		background-color: #1890ff;
	}

	/* 表格容器 */
	.table-box {
		background-color: #fff;
		margin: 0 20rpx;
		border-radius: 16rpx;
		overflow: hidden;
	}

	.empty-tip {
		text-align: center;
		padding: 100rpx 0;
		color: #999;
		font-size: 28rpx;
	}

	/* 表格行 */
	.table-row {
		display: flex;
		padding: 30rpx;
		border-bottom: 1rpx solid #f0f0f0;
		align-items: center;
	}

	.table-row:last-child {
		border-bottom: none;
	}

	.table-cell {
		font-size: 28rpx;
	}

	.table-title {
		flex: 3;
		color: #333;
	}

	.table-time {
		flex: 2;
		color: #999;
		font-size: 24rpx;
		text-align: center;
	}

	.table-status {
		flex: 1;
		text-align: right;
	}

	/* 状态标签 */
	.status-tag {
		padding: 6rpx 12rpx;
		border-radius: 20rpx;
		font-size: 24rpx;
	}

	.status-tag.untreated {
		background-color: #fff2e8;
		color: #fa8c16;
	}

	.status-tag.processing {
		background-color: #e6f7ff;
		color: #1890ff;
	}

	.status-tag.finished {
		background-color: #f6ffed;
		color: #52c41a;
	}
</style>