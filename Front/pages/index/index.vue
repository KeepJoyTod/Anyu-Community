<template>
	<view class="home-page">
		<!-- 骨架屏（首屏加载） -->
		<view v-if="pageLoading" class="skeleton">
			<view class="s-greeting">
				<view class="sk sk-line w-70"></view>
				<view class="sk sk-line w-40 mt-12"></view>
			</view>

			<view class="s-focus-card">
				<view class="sk sk-line w-25"></view>
				<view class="sk sk-line w-90 mt-16"></view>
				<view class="sk sk-line w-65 mt-12"></view>
			</view>

			<view class="s-notice">
				<view class="sk sk-line w-85"></view>
			</view>

			<view class="s-banner">
				<view class="sk sk-rect"></view>
			</view>

			<view class="s-grid">
				<view class="s-grid-item" v-for="n in 8" :key="n">
					<view class="sk sk-circle"></view>
					<view class="sk sk-line w-60 mt-12"></view>
				</view>
			</view>

			<view class="s-community-life">
				<view class="s-section-header">
					<view class="sk sk-line w-25"></view>
					<view class="sk sk-line w-18"></view>
				</view>
				<view class="s-content-card">
					<view class="sk sk-line w-55"></view>
					<view class="sk sk-line w-95 mt-16"></view>
					<view class="sk sk-line w-88 mt-12"></view>
					<view class="s-tags">
						<view class="sk sk-pill w-22"></view>
						<view class="sk sk-pill w-22"></view>
					</view>
				</view>
			</view>
		</view>

		<!-- 正常内容 -->
		<view v-else>
			<view class="greeting">
				<text class="hello">{{ userInfo.nickname }}，欢迎回家！</text>
				<text class="community">{{ userInfo.communityName }}</text>
				<view class="robot-chat" @click="openRobotChat">
					<image class="robot-avatar" src="/static/robot.png" mode="aspectFill" />
					<view class="robot-bubble">
						<text class="robot-text">{{ robotTip }}</text>
					</view>
				</view>
			</view>

			<view class="focus-card" @click="openFocusPopup">
				<text class="focus-title">{{ focus.title }}</text>
				<text class="focus-desc">{{ focus.content }}</text>
			</view>

			<view class="notice">
				<text class="notice-text">{{ notice.noticeContent || '暂无重要通知' }}</text>
			</view>

			<view class="banner-section">
				<swiper class="banner-swiper" :current="currentBanner" :indicator-dots="true" :autoplay="false" :interval="3000" :duration="500" indicator-color="rgba(255,255,255,0.5)" indicator-active-color="#fff" @change="onBannerChange">
					<swiper-item v-for="(item, index) in bannerList" :key="index">
						<image class="banner-image" :src="item.image" mode="aspectFill"></image>
					</swiper-item>
				</swiper>
				<view class="banner-btn prev" @click="prevBanner">
					<text>‹</text>
				</view>
				<view class="banner-btn next" @click="nextBanner">
					<text>›</text>
				</view>
			</view>

			<view class="grid">
				<view class="grid-item" v-for="(item, index) in functionList" :key="index" @click="handleFunction(item)">
					<text class="icon">{{ item.icon }}</text>
					<text class="name">{{ item.name }}</text>
				</view>
			</view>

			<view class="community-life">
				<view class="section-header">
					<text class="title">社区生活</text>
					<text class="more" @click="goCommunity">全部探索 ></text>
				</view>
				<view class="content-card" @click="showCommunityLocation">
					<view class="card-header">
						<text class="card-title">{{ communityLife.title }}</text>
					</view>
					<text class="card-desc">{{ communityLife.description }}</text>
					<view class="tags">
						<text class="tag" v-for="(tag, index) in communityLife.tags" :key="index">{{ tag }}</text>
					</view>
					<view class="detail-btn" @click.stop="showDetailPopup">
						<text>详情</text>
					</view>
				</view>
			</view>
		</view>

		<view class="location-popup" v-if="showLocationPopup" @click.stop>
			<view class="popup-mask" @click="closeLocationPopup"></view>
			<view class="popup-content">
				<view class="popup-header">
					<text class="popup-title">{{ communityLocation.name }}</text>
					<text class="popup-close" @click="closeLocationPopup">×</text>
				</view>
				<view class="popup-map">
					<community-map
						:longitude="communityLocation.longitude"
						:latitude="communityLocation.latitude"
						:scale="mapScale"
						:markers="mapMarkers"
					></community-map>
				</view>
				<view class="popup-info">
					<view class="info-row">
						<text class="info-label">地址</text>
						<text class="info-value">{{ communityLocation.address }}</text>
					</view>
					<view class="info-row">
						<text class="info-label">电话</text>
						<text class="info-value phone" @click="callPhone">{{ communityLocation.phone }}</text>
					</view>
				</view>
				<view class="popup-actions">
					<view class="popup-btn primary full" @click="navigateToLocation">
						<text>导航前往</text>
					</view>
				</view>
			</view>
		</view>

		<view class="detail-popup" v-if="showDetail" @click.stop>
			<view class="popup-mask" @click="closeDetailPopup"></view>
			<view class="popup-content">
				<view class="popup-header">
					<text class="popup-title">详情</text>
					<text class="popup-close" @click="closeDetailPopup">×</text>
				</view>
				<view class="popup-body">
					<image src="/static/community.png"style="
					text-align: center;
					width:320px;
					height:150px;"></image>
					<p>智慧阳光社区以科技赋能、智慧服务、便民惠民为核心，依托大数据、物联网、智能终端等现代化技术，打造集智能管理、便民服务、安全防控、生态宜居于一体的新型智慧社区。
社区通过智慧门禁、智能监控、线上办事、便民通知等数字化服务，让居民生活更便捷、更安全、更高效。同时坚持阳光透明、服务暖心、共建共治的理念，不断优化居住环境，提升服务品质，构建宜居、宜业、宜乐的现代化智慧家园。</p>
				</view>
			</view>
		</view>

		<view class="detail-popup" v-if="showPropertyPopup" @click.stop>
			<view class="popup-mask" @click="closePropertyPopup"></view>
			<view class="popup-content property-popup">
				<view class="popup-header">
					<text class="popup-title">物业服务</text>
					<text class="popup-close" @click="closePropertyPopup">×</text>
				</view>
				<view class="popup-body">
					<view class="property-info">
						<view class="property-name">{{ propertyInfo.propertyName }}</view>
						<view class="property-item">
							<text class="item-label">联系人：</text>
							<text class="item-value">{{ propertyInfo.contactPerson }}</text>
						</view>
						<view class="property-item">
							<text class="item-label">联系电话：</text>
							<text class="item-value phone" @click="callPropertyPhone">{{ propertyInfo.contactPhone }}</text>
						</view>
						<view class="property-item">
							<text class="item-label">地址：</text>
							<text class="item-value">{{ propertyInfo.address }}</text>
						</view>
						<view class="property-item">
							<text class="item-label">工作时间：</text>
							<text class="item-value">{{ propertyInfo.workTime }}</text>
						</view>
						<view class="property-item">
							<text class="item-label">服务内容：</text>
							<text class="item-value">{{ propertyInfo.serviceContent }}</text>
						</view>
					</view>
				</view>
				<view class="popup-actions">
					<view class="popup-btn primary" @click="callPropertyPhone">
						<text>拨打电话</text>
					</view>
					<view class="popup-btn" @click="closePropertyPopup">
						<text>关闭</text>
					</view>
				</view>
			</view>
		</view>

		<view class="detail-popup" v-if="showPraisePopup" @click.stop>
			<view class="popup-mask" @click="closePraisePopup"></view>
			<view class="popup-content praise-popup">
				<view class="popup-header">
					<text class="popup-title">表扬物业</text>
					<text class="popup-close" @click="closePraisePopup">×</text>
				</view>
				<view class="popup-body">
					<view class="form-item">
						<text class="form-label">表扬类型</text>
						<picker mode="selector" :range="praiseTypes" @change="onPraiseTypeChange">
							<view class="picker-input">
								<text :class="{'placeholder': !praiseForm.praiseType}">{{ praiseForm.praiseType || '请选择表扬类型' }}</text>
								<text class="picker-arrow">▼</text>
							</view>
						</picker>
					</view>
					<view class="form-item">
						<text class="form-label">表扬对象</text>
						<input class="form-input" v-model="praiseForm.praiseTarget" placeholder="请输入表扬对象（选填）" />
					</view>
					<view class="form-item">
						<text class="form-label">表扬内容</text>
						<textarea class="form-textarea" v-model="praiseForm.praiseContent" placeholder="请输入表扬内容" maxlength="500"></textarea>
					</view>
				</view>
				<view class="popup-actions">
					<view class="popup-btn primary" @click="submitPraise">
						<text>提交</text>
					</view>
					<view class="popup-btn" @click="closePraisePopup">
						<text>取消</text>
					</view>
				</view>
			</view>
		</view>

		<view class="detail-popup" v-if="showFocusPopup" @click.stop>
			<view class="popup-mask" @click="closeFocusPopup"></view>
			<view class="popup-content focus-popup">
				<view class="popup-header">
					<text class="popup-title">今日焦点</text>
					<text class="popup-close" @click="closeFocusPopup">×</text>
				</view>
				<view class="popup-body">
					<span>城管进社区全面落地
住建部定调，全国推进管理 + 服务 + 执法进小区，专治违建、噪音、油烟、乱停放，打通家园治理 “最后一米”。
老旧小区更新提速
多地启动楼道翻新、小微花园升级、充电桩扩容，用微改造提升居住质感。
业主自治提速
多地小区今日成立首届业委会，居民共商共管，共建和谐美好家园。</span>
					<!-- <text class="focus-content">666</text> -->
				</view>
				<view class="popup-actions">
					<view class="popup-btn primary full" @click="closeFocusPopup">
						<text>确定</text>
					</view>
				</view>
			</view>
		</view>

		<!-- 机器人对话框 -->
		<view class="detail-popup" v-if="showRobotChat" @click.stop>
			<view class="popup-mask" @click="closeRobotChat"></view>
			<view class="popup-content robot-popup">
				<view class="popup-header">
					<view class="robot-title">
						<image class="robot-avatar small" src="/static/robot.png" mode="aspectFill" />
						<text class="popup-title">小区助手</text>
					</view>
					<text class="popup-close" @click="closeRobotChat">×</text>
				</view>
				<view class="robot-body">
					<scroll-view class="robot-messages" scroll-y :scroll-top="robotScrollTop">
						<view class="msg" v-for="(m, idx) in robotMessages" :key="idx" :class="m.role">
							<view class="msg-bubble">{{ m.text }}</view>
						</view>
					</scroll-view>
					<view class="robot-input">
						<input class="robot-ipt" v-model="robotInput" placeholder="例如：怎么报修？" confirm-type="send" @confirm="sendRobotMessage" />
						<view class="robot-send" @click="sendRobotMessage"><text>发送</text></view>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
import homeApi from '@/api/home.js'
import CommunityMap from '@/components/CommunityMap.vue'

export default {
	components: {
		CommunityMap
	},
	data() {
		return {
			userInfo: {
				greeting: '欢迎回家',
				nickname: '智慧业主',
				communityName: '阳光家园社区'
			},
			focus: {
				title: '今日焦点',
				content: ''
			},
			showFocusPopup: false,
			notice: {
				noticeId: '',
				noticeTitle: '',
				noticeContent: '',
				publishTime: ''
			},
			noticeList: [],
			bannerList: [
				{ image: '/static/1.jpg' },
				{ image: '/static/2.jpg' },
				{ image: '/static/3.jpg' }
			],
			currentBanner: 0,
			functionList: [],
			communityLife: {
				title: '智慧阳光家园 (2026)',
				description: '我们致力于打造数字化、人性化的现代居住空间。在这里，科技与温暖交织，为您提供全方位的智能管家服务。',
				tags: ['#数字化转型', '#智慧物联']
			},
			showLocationPopup: false,
			showDetail: false,
			showPropertyPopup: false,
			showPraisePopup: false,
			propertyInfo: {
				propertyName: '',
				contactPerson: '',
				contactPhone: '',
				address: '',
				workTime: '',
				serviceContent: ''
			},
			praiseForm: {
				praiseType: '',
				praiseContent: '',
				praiseTarget: ''
			},
			praiseTypes: ['服务态度', '工作效率', '环境卫生', '安全保障', '其他'],
			communityLocation: {
				name: '智慧阳光家园',
				address: '北京市东城区阳光家园社区',
				longitude: 121.5005,
				latitude: 31.2305,
				phone: '888-666666'
			},
			mapScale: 15,
			mapMarkers: [
				{
					id: 1,
					longitude: 121.5005,
					latitude: 31.2305,
					title: '智慧阳光家园',
					address: '北京市东城区阳光家园社区',
					phone: '888-666666',
					width: 30,
					height: 30
				}
			],
			loading: false,
			pageLoading: true,
			robotTip: '我是小区助手，点我问问题',
			showRobotChat: false,
			robotMessages: [
				{ role: 'bot', text: '欢迎回家！我是接入 DeepSeek 的小区助手，可直接用自然语言提问（报修、缴费、访客等）。' }
			],
			robotInput: '',
			robotScrollTop: 0
		};
	},
	async onLoad() {
		if (!this.checkLogin()) return
		await this.initPage()
	},
	onShow() {
		if (!this.checkLogin()) return
		// 已经完成首屏加载后，onShow 只做静默刷新，避免反复闪烁骨架屏
		if (!this.pageLoading) {
			this.loadFocus()
			this.loadNotice()
		}
	},
	methods: {
		async initPage() {
			this.pageLoading = true
			try {
				await Promise.all([this.loadFocus(), this.loadNotice(), this.loadHomeData()])
			} finally {
				this.pageLoading = false
			}
		},
		checkLogin() {
			const userInfo = uni.getStorageSync('userInfo')
			if (!userInfo) {
				uni.redirectTo({
					url: '/pages/login/login'
				})
				return false
			}
			return true
		},
		onBannerChange(e) {
			this.currentBanner = e.detail.current
		},
		prevBanner() {
			if (this.currentBanner > 0) {
				this.currentBanner--
			} else {
				this.currentBanner = this.bannerList.length - 1
			}
		},
		nextBanner() {
			if (this.currentBanner < this.bannerList.length - 1) {
				this.currentBanner++
			} else {
				this.currentBanner = 0
			}
		},
		async loadFocus() {
			try {
				const res = await homeApi.getFocus()
				if (res.data) {
					this.focus = res.data
				}
			} catch (e) {
				console.error('加载今日焦点失败:', e)
				this.focus = {
					title: '今日焦点',
					content: '2026年度文明社区评选展示 - 共建美好家园'
				}
			}
		},
		async loadNotice() {
			try {
				const res = await homeApi.getNoticeList({ pageNum: 1, pageSize: 5 })
				console.log('通知API返回:', res)
				// 后端返回结构: { code: 200, data: { rows: [...], total: ... } }
				const rows = res.data && res.data.rows ? res.data.rows : (res.rows || [])
				if (rows.length > 0) {
					// 取最新一条通知展示
					const latestNotice = rows[0]
					this.notice = {
						noticeId: latestNotice.id || latestNotice.noticeId,
						noticeContent: latestNotice.noticeContent || latestNotice.noticeTitle || latestNotice.title || '暂无重要通知'
					}
					console.log('当前通知:', this.notice)
				}
			} catch (e) {
				console.error('加载重要通知失败:', e)
			}
		},
		async loadHomeData() {
			if (this.loading) return
			this.loading = true
			
			try {
				const res = await homeApi.getHomeData()
				if (res.data) {
					const data = res.data
					if (data.userInfo) {
						this.userInfo = data.userInfo
					}
					if (data.functionList && data.functionList.length > 0) {
						this.functionList = data.functionList
					} else {
						this.loadDefaultData()
					}
					if (data.communityLife) {
						this.communityLife = data.communityLife
					}
				}
			} catch (e) {
				console.error('加载首页数据失败:', e)
				this.loadDefaultData()
			} finally {
				this.loading = false
			}
		},
		loadDefaultData() {
			this.functionList = [
				{ icon: '🔍', name: '找物业', action: 'property' },
				{ icon: '🌟', name: '表扬物业', action: 'praise' },
				{ icon: '📱', name: '手机开门', action: 'openDoor' },
				{ icon: '👥', name: '访客邀请', action: 'visitor' },
				{ icon: '🔧', name: '报事报修', action: 'repair' },
				{ icon: '💰', name: '生活缴费', action: 'bill' },
				{ icon: '🎉', name: '活动报名', action: 'activity' },
				{ icon: '📦', name: '快递驿站', action: 'express' }
			]
		},
		openFocusPopup() {
			this.showFocusPopup = true
		},
		closeFocusPopup() {
			this.showFocusPopup = false
		},
		openRobotChat() {
			this.showRobotChat = true
			this.$nextTick(() => this.scrollRobotToBottom())
		},
		closeRobotChat() {
			this.showRobotChat = false
		},
		async sendRobotMessage() {
			const text = (this.robotInput || '').trim()
			if (!text) return
			this.robotMessages.push({ role: 'user', text })
			this.robotInput = ''
			uni.showLoading({ title: '思考中...', mask: true })
			try {
				const messages = this.robotMessages
					.filter(m => m.role === 'user' || m.role === 'bot')
					.map(m => ({
						role: m.role === 'bot' ? 'assistant' : 'user',
						content: m.text
					}))
				const res = await homeApi.assistantChat({ messages })
				uni.hideLoading()
				if (res && res.data && res.data.reply) {
					this.robotMessages.push({ role: 'bot', text: res.data.reply })
				} else {
					this.robotMessages.push({ role: 'bot', text: this.getRobotReply(text) })
				}
			} catch (e) {
				uni.hideLoading()
				this.robotMessages.push({ role: 'bot', text: this.getRobotReply(text) })
			}
			this.$nextTick(() => this.scrollRobotToBottom())
		},
		getRobotReply(text) {
			const t = text.toLowerCase()
			if (t.includes('报修') || t.includes('维修') || t.includes('修理')) return '你可以在首页点击“报事报修”，填写位置与问题描述后提交；也可以到“服务”页找到报修入口。'
			if (t.includes('缴费') || t.includes('交费') || t.includes('账单')) return '你可以点击“生活缴费”进入账单列表，选择项目后完成支付。'
			if (t.includes('访客') || t.includes('邀请')) return '你可以点击“访客邀请”填写来访信息，生成邀请记录后分享给访客。'
			if (t.includes('物业') || t.includes('电话') || t.includes('联系')) return '你可以在首页点击“找物业”查看联系方式并一键拨打。'
			return '我已经收到啦！你也可以试试问：怎么报修、怎么缴费、怎么邀请访客。'
		},
		scrollRobotToBottom() {
			// 简单方式：持续增大 scrollTop 触底
			this.robotScrollTop = this.robotScrollTop + 9999
		},
		handleFunction(item) {
			switch (item.action) {
				case 'openDoor':
					this.handleOpenDoor()
					break
				case 'visitor':
				case 'repair':
				case 'express':
					this.goService()
					break
				case 'bill':
					this.goBill()
					break
				case 'property':
					this.openPropertyPopup()
					break
				case 'praise':
					this.openPraisePopup()
					break
				case 'activity':
					this.goCommunity()
					break
				default:
					uni.showToast({
						title: `${item.name}功能开发中`,
						icon: 'none'
					})
			}
		},
		goService() {
			uni.switchTab({
				url: '/pages/service/service'
			})
		},
		async handleOpenDoor() {
			uni.showLoading({ title: '正在开门...' })
			await new Promise(resolve => setTimeout(resolve, 800))
			uni.hideLoading()
			uni.showModal({
				title: '开门成功',
				content: '门禁已开启，请通行',
				showCancel: false,
				confirmText: '确定'
			})
		},
		goVisitor() {
			uni.navigateTo({
				url: '/pages/visitor/visitor'
			})
		},
		goRepair() {
			uni.navigateTo({
				url: '/pages/repair/repair'
			})
		},
		goBill() {
			uni.switchTab({
				url: '/pages/service/service'
			})
		},
		goCommunity() {
			uni.switchTab({
				url: '/pages/community/community'
			});
		},
		async openPropertyPopup() {
			try {
				const res = await homeApi.getPropertyInfo()
				if (res.code === 200 && res.data) {
					this.propertyInfo = res.data
				} else {
					this.propertyInfo = {
						propertyName: '阳光家园物业服务中心',
						contactPerson: '张经理',
						contactPhone: '010-88888888',
						address: '北京市东城区阳光家园社区物业楼1层',
						workTime: '周一至周五 8:00-18:00，周六日 9:00-17:00',
						serviceContent: '日常维修、环境卫生、安全保卫、停车场管理、绿化养护等服务'
					}
				}
				this.showPropertyPopup = true
			} catch (e) {
				this.propertyInfo = {
					propertyName: '阳光家园物业服务中心',
					contactPerson: '张经理',
					contactPhone: '010-88888888',
					address: '北京市东城区阳光家园社区物业楼1层',
					workTime: '周一至周五 8:00-18:00，周六日 9:00-17:00',
					serviceContent: '日常维修、环境卫生、安全保卫、停车场管理、绿化养护等服务'
				}
				this.showPropertyPopup = true
			}
		},
		closePropertyPopup() {
			this.showPropertyPopup = false
		},
		callPropertyPhone() {
			if (this.propertyInfo.contactPhone) {
				uni.makePhoneCall({
					phoneNumber: this.propertyInfo.contactPhone,
					fail: () => {
						uni.showToast({
							title: '拨打电话失败',
							icon: 'none'
						})
					}
				})
			}
		},
		openPraisePopup() {
			this.praiseForm = {
				praiseType: '',
				praiseContent: '',
				praiseTarget: ''
			}
			this.showPraisePopup = true
		},
		closePraisePopup() {
			this.showPraisePopup = false
		},
		onPraiseTypeChange(e) {
			this.praiseForm.praiseType = this.praiseTypes[e.detail.value]
		},
		async submitPraise() {
			if (!this.praiseForm.praiseType) {
				uni.showToast({ title: '请选择表扬类型', icon: 'none' })
				return
			}
			if (!this.praiseForm.praiseContent) {
				uni.showToast({ title: '请填写表扬内容', icon: 'none' })
				return
			}
			try {
				uni.showLoading({ title: '提交中...' })
				const res = await homeApi.addPropertyPraise({
					praiseType: this.praiseForm.praiseType,
					praiseContent: this.praiseForm.praiseContent,
					praiseTarget: this.praiseForm.praiseTarget,
					ownerName: '业主',
					status: '0'
				})
				uni.hideLoading()
				if (res.code === 200) {
					uni.showToast({ title: '提交成功', icon: 'success' })
					this.closePraisePopup()
				} else {
					uni.showToast({ title: res.msg || '提交失败', icon: 'none' })
				}
			} catch (e) {
				uni.hideLoading()
				uni.showToast({ title: '提交失败', icon: 'none' })
			}
		},
		showCommunityLocation() {
			this.showLocationPopup = true
		},
		closeLocationPopup() {
			this.showLocationPopup = false
		},
		navigateToLocation() {
			uni.openLocation({
				latitude: this.communityLocation.latitude,
				longitude: this.communityLocation.longitude,
				name: this.communityLocation.name,
				address: this.communityLocation.address,
				scale: 16
			})
			this.closeLocationPopup()
		},
		callPhone() {
			uni.makePhoneCall({
				phoneNumber: this.communityLocation.phone
			})
		},
		showDetailPopup() {
			this.showDetail = true
		},
		closeDetailPopup() {
			this.showDetail = false
		}
	}
};
</script>

<style scoped>
.home-page {
	background-color: #f5f7fa;
	min-height: 100vh;
	padding: 40rpx 30rpx 100rpx;
}

/* 骨架屏 */
.skeleton {
	min-height: 100vh;
}

.sk {
	position: relative;
	background-color: #e9eef5;
	overflow: hidden;
	border-radius: 12rpx;
}

.sk::after {
	content: '';
	position: absolute;
	top: 0;
	left: -60%;
	width: 60%;
	height: 100%;
	background: linear-gradient(90deg, rgba(233, 238, 245, 0) 0%, rgba(255, 255, 255, 0.7) 50%, rgba(233, 238, 245, 0) 100%);
	animation: shimmer 1.1s ease-in-out infinite;
}

@keyframes shimmer {
	0% {
		left: -60%;
	}
	100% {
		left: 120%;
	}
}

.sk-line {
	height: 28rpx;
}

.sk-rect {
	width: 100%;
	height: 550rpx;
	border-radius: 24rpx;
}

.sk-circle {
	width: 88rpx;
	height: 88rpx;
	border-radius: 50%;
}

.sk-pill {
	height: 44rpx;
	border-radius: 999rpx;
}

.mt-12 {
	margin-top: 12rpx;
}

.mt-16 {
	margin-top: 16rpx;
}

.w-95 {
	width: 95%;
}

.w-90 {
	width: 90%;
}

.w-88 {
	width: 88%;
}

.w-85 {
	width: 85%;
}

.w-70 {
	width: 70%;
}

.w-65 {
	width: 65%;
}

.w-60 {
	width: 60%;
}

.w-55 {
	width: 55%;
}

.w-40 {
	width: 40%;
}

.w-25 {
	width: 25%;
}

.w-22 {
	width: 22%;
}

.w-18 {
	width: 18%;
}

.s-greeting {
	margin-bottom: 40rpx;
}

.s-focus-card {
	background: #fff;
	border-radius: 24rpx;
	padding: 32rpx;
	margin-bottom: 30rpx;
	box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.04);
}

.s-notice {
	background: #fff;
	border-radius: 16rpx;
	padding: 24rpx 30rpx;
	margin-bottom: 40rpx;
	box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.04);
}

.s-banner {
	margin-bottom: 30rpx;
}

.s-grid {
	display: flex;
	flex-wrap: wrap;
	background-color: #fff;
	border-radius: 32rpx;
	padding: 20rpx 0;
	margin-bottom: 50rpx;
	box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.04);
}

.s-grid-item {
	width: 25%;
	display: flex;
	flex-direction: column;
	align-items: center;
	padding: 24rpx 0;
}

.s-community-life {
	margin-top: 20rpx;
}

.s-section-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 24rpx;
}

.s-content-card {
	background-color: #fff;
	border-radius: 24rpx;
	padding: 32rpx;
	box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.04);
}

.s-tags {
	display: flex;
	gap: 20rpx;
	margin-top: 24rpx;
}

/* 头部 */
.greeting {
	margin-bottom: 40rpx;
}

.hello {
	font-size: 48rpx;
	font-weight: bold;
	color: #1f2d3d;
	display: block;
}

.community {
	font-size: 28rpx;
	color: #8a9aa8;
	margin-top: 8rpx;
	display: block;
}

.robot-chat {
	margin-top: 22rpx;
	display: flex;
	align-items: center;
	gap: 20rpx;
}

.robot-avatar {
	flex-shrink: 0;
	width: 88rpx;
	height: 88rpx;
	border-radius: 50%;
	background-color: #fff;
	border: 4rpx solid rgba(255, 255, 255, 0.95);
	box-shadow: 0 6rpx 22rpx rgba(102, 126, 234, 0.22);
}

.robot-avatar.small {
	width: 56rpx;
	height: 56rpx;
	border-width: 2rpx;
	box-shadow: 0 4rpx 14rpx rgba(102, 126, 234, 0.15);
}

.robot-bubble {
	flex: 1;
	min-width: 0;
	max-width: 540rpx;
	background: #ffffff;
	border-radius: 22rpx;
	padding: 18rpx 22rpx;
	box-shadow: 0 6rpx 20rpx rgba(0, 0, 0, 0.06);
	position: relative;
}

.robot-bubble::before {
	content: '';
	position: absolute;
	left: -12rpx;
	top: 34rpx;
	width: 0;
	height: 0;
	border-top: 12rpx solid transparent;
	border-bottom: 12rpx solid transparent;
	border-right: 12rpx solid #ffffff;
}

.robot-text {
	font-size: 28rpx;
	color: #3c4b5a;
	line-height: 1.45;
}

/* 今日焦点卡片 */
.focus-card {
	background-color: #fff9e6;
	border-left: 8rpx solid #ffb74d;
	border-radius: 16rpx;
	padding: 24rpx 30rpx;
	margin-bottom: 40rpx;
}

.focus-title {
	font-size: 28rpx;
	opacity: 0.9;
	display: block;
	margin-bottom: 12rpx;
}

.focus-desc {
	font-size: 32rpx;
	font-weight: 500;
	display: block;
	line-height: 1.4;
}

/* 通知 */
.notice {
	background-color: #fff9e6;
	border-left: 8rpx solid #ffb74d;
	border-radius: 16rpx;
	padding: 24rpx 30rpx;
	margin-bottom: 40rpx;
}

.notice-text {
	font-size: 28rpx;
	color: #e67e22;
	line-height: 1.5;
}

/* 轮播图 */
.banner-section {
	position: relative;
	margin-bottom: 30rpx;
}

.banner-swiper {
	width: 100%;
	height: 550rpx;
	border-radius: 24rpx;
	overflow: hidden;
}

.banner-image {
	width: 100%;
	height: 100%;
}

.banner-btn {
	position: absolute;
	top: 50%;
	transform: translateY(-50%);
	width: 60rpx;
	height: 60rpx;
	background-color: rgba(0, 0, 0, 0.3);
	border-radius: 50%;
	display: flex;
	align-items: center;
	justify-content: center;
	z-index: 10;
}

.banner-btn.prev {
	left: 20rpx;
}

.banner-btn.next {
	right: 20rpx;
}

.banner-btn text {
	font-size: 40rpx;
	color: #fff;
	line-height: 1;
}

/* 功能网格 */
.grid {
	display: flex;
	flex-wrap: wrap;
	background-color: #fff;
	border-radius: 32rpx;
	padding: 20rpx 0;
	margin-bottom: 50rpx;
	box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.04);
}

.grid-item {
	width: 25%;
	display: flex;
	flex-direction: column;
	align-items: center;
	padding: 24rpx 0;
}

.icon {
	font-size: 48rpx;
	margin-bottom: 12rpx;
}

.name {
	font-size: 26rpx;
	color: #3c4b5a;
}

/* 社区生活板块 */
.community-life {
	margin-top: 20rpx;
}

.section-header {
	display: flex;
	justify-content: space-between;
	align-items: baseline;
	margin-bottom: 24rpx;
}

.section-header .title {
	font-size: 34rpx;
	font-weight: bold;
	color: #1f2d3d;
}

.section-header .more {
	font-size: 26rpx;
	color: #8a9aa8;
}

.content-card {
	position: relative;
	background-color: #fff;
	border-radius: 24rpx;
	padding: 32rpx;
	box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.04);
}

.card-title {
	font-size: 32rpx;
	font-weight: bold;
	color: #1f2d3d;
	display: block;
	margin-bottom: 20rpx;
}

.card-desc {
	font-size: 28rpx;
	color: #5a6e7c;
	line-height: 1.5;
	display: block;
	margin-bottom: 24rpx;
}

.tags {
	display: flex;
	gap: 20rpx;
}

.tag {
	font-size: 24rpx;
	color: #6b8c42;
	background-color: #eef5e6;
	padding: 8rpx 20rpx;
	border-radius: 30rpx;
}

.detail-btn {
	position: absolute;
	right: 20rpx;
	bottom: 20rpx;
}

.detail-btn text {
	font-size: 26rpx;
	color: #8a9aa8;
}

.location-popup {
	position: fixed;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	z-index: 999;
	display: flex;
	align-items: flex-end;
}

.popup-mask {
	position: absolute;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background-color: rgba(0, 0, 0, 0.5);
}

.popup-content {
	position: relative;
	width: 100%;
	background-color: #fff;
	border-radius: 32rpx 32rpx 0 0;
	padding: 40rpx 30rpx;
	padding-bottom: calc(40rpx + env(safe-area-inset-bottom));
	animation: slideUp 0.3s ease;
	max-height: 80vh;
	overflow-y: auto;
}

@keyframes slideUp {
	from {
		transform: translateY(100%);
	}
	to {
		transform: translateY(0);
	}
}

.popup-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 30rpx;
}

.popup-title {
	font-size: 36rpx;
	font-weight: bold;
	color: #1f2d3d;
}

.popup-close {
	font-size: 48rpx;
	color: #8a9aa8;
	line-height: 1;
}

.popup-map {
	margin-bottom: 20rpx;
}

.popup-info {
	margin-bottom: 20rpx;
	padding: 20rpx;
	background-color: #f8f9fa;
	border-radius: 16rpx;
}

.popup-body {
	margin-bottom: 30rpx;
}

.info-row {
	display: flex;
	margin-bottom: 20rpx;
}

.info-label {
	width: 120rpx;
	font-size: 28rpx;
	color: #8a9aa8;
	flex-shrink: 0;
}

.info-value {
	flex: 1;
	font-size: 28rpx;
	color: #1f2d3d;
}

.info-value.phone {
	color: #667eea;
}

.popup-actions {
	display: flex;
	gap: 20rpx;
}

.popup-btn {
	flex: 1;
	height: 88rpx;
	border-radius: 44rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	border: 2rpx solid #667eea;
}

.popup-btn text {
	font-size: 30rpx;
	color: #667eea;
}

.popup-btn.primary {
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
	border: none;
}

.popup-btn.primary text {
	color: #fff;
}

.popup-btn.full {
	flex: none;
	width: 100%;
}

.detail-popup {
	position: fixed;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	z-index: 999;
	display: flex;
	align-items: center;
	justify-content: center;
}

.detail-popup .popup-mask {
	position: absolute;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background-color: rgba(0, 0, 0, 0.5);
}

.detail-popup .popup-content {
	position: relative;
	width: 600rpx;
	background-color: #fff;
	border-radius: 24rpx;
	padding: 40rpx;
	animation: fadeIn 0.3s ease;
}

.robot-popup {
	width: 680rpx;
	padding: 32rpx 28rpx 28rpx;
}

.robot-title {
	display: flex;
	align-items: center;
	gap: 16rpx;
}

.robot-body {
	margin-top: 10rpx;
}

.robot-messages {
	height: 560rpx;
	background: #f8f9fa;
	border-radius: 16rpx;
	padding: 18rpx;
	box-sizing: border-box;
}

.msg {
	display: flex;
	margin-bottom: 16rpx;
}

.msg.user {
	justify-content: flex-end;
}

.msg.bot {
	justify-content: flex-start;
}

.msg-bubble {
	max-width: 520rpx;
	padding: 16rpx 18rpx;
	border-radius: 18rpx;
	font-size: 28rpx;
	line-height: 1.45;
	color: #1f2d3d;
	background: #ffffff;
}

.msg.user .msg-bubble {
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
	color: #fff;
}

.robot-input {
	display: flex;
	gap: 16rpx;
	margin-top: 18rpx;
}

.robot-ipt {
	flex: 1;
	height: 80rpx;
	padding: 0 22rpx;
	background: #fff;
	border-radius: 14rpx;
	border: 2rpx solid #e8e8e8;
	font-size: 28rpx;
	box-sizing: border-box;
}

.robot-send {
	width: 140rpx;
	height: 80rpx;
	border-radius: 14rpx;
	display: flex;
	align-items: center;
	justify-content: center;
	background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.robot-send text {
	color: #fff;
	font-size: 28rpx;
	font-weight: 500;
}

@keyframes fadeIn {
	from {
		opacity: 0;
		transform: scale(0.9);
	}
	to {
		opacity: 1;
		transform: scale(1);
	}
}

.detail-text {
	font-size: 48rpx;
	font-weight: bold;
	color: #1f2d3d;
	text-align: center;
	display: block;
	padding: 40rpx 0;
}

.property-popup .popup-body {
	padding: 0;
}

.property-info {
	padding: 20rpx;
	background-color: #f8f9fa;
	border-radius: 16rpx;
}

.property-name {
	font-size: 32rpx;
	font-weight: bold;
	color: #1f2d3d;
	margin-bottom: 20rpx;
	padding-bottom: 20rpx;
	border-bottom: 1rpx solid #e8e8e8;
}

.property-item {
	display: flex;
	margin-bottom: 16rpx;
}

.property-item:last-child {
	margin-bottom: 0;
}

.item-label {
	font-size: 28rpx;
	color: #8a9aa8;
	flex-shrink: 0;
}

.item-value {
	font-size: 28rpx;
	color: #1f2d3d;
	flex: 1;
}

.item-value.phone {
	color: #667eea;
}

.praise-popup .popup-body {
	padding: 0;
}

.form-item {
	margin-bottom: 30rpx;
}

.form-label {
	display: block;
	font-size: 28rpx;
	color: #1f2d3d;
	margin-bottom: 16rpx;
	font-weight: 500;
}

.form-input {
	width: 100%;
	height: 80rpx;
	padding: 0 24rpx;
	font-size: 28rpx;
	border: 2rpx solid #e8e8e8;
	border-radius: 12rpx;
	box-sizing: border-box;
}

.form-textarea {
	width: 100%;
	height: 200rpx;
	padding: 20rpx 24rpx;
	font-size: 28rpx;
	border: 2rpx solid #e8e8e8;
	border-radius: 12rpx;
	box-sizing: border-box;
}

.picker-input {
	display: flex;
	justify-content: space-between;
	align-items: center;
	height: 80rpx;
	padding: 0 24rpx;
	border: 2rpx solid #e8e8e8;
	border-radius: 12rpx;
}

.picker-input text {
	font-size: 28rpx;
	color: #1f2d3d;
}

.picker-input text.placeholder {
	color: #999;
}

.picker-arrow {
	font-size: 24rpx;
	color: #999;
}

.focus-popup .popup-body {
	padding: 40rpx;
	/* text-align: center; */
}

.focus-content {
	font-size: 48rpx;
	font-weight: bold;
	color: #1f2d3d;
}
</style>