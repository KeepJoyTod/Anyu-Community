<template>
	<view class="community-page">
		<!-- 头部 -->
		<view class="header">
			<text class="title">社区广场</text>
			<text class="subtitle">发现邻里间的美好</text>
		</view>

		<!-- 超大尺寸热门话题模块 -->
		<view class="hot-topics">
			<view class="topic-card" @click="goTopic('volunteer')" hover-class="topic-card-hover">
				<image class="topic-img" src="/static/义演海报.jpg" mode="aspectFill"></image>
				<view class="topic-text-wrap">
					<text class="topic-tag">VOLUNTEER</text>
					<text class="topic-title">{{ activityData.volunteer.title }}</text>
				</view>
			</view>
			<view class="topic-card" @click="goTopic('good_deed')" hover-class="topic-card-hover">
				<image class="topic-img" src="/static/植树海报.jpg" mode="aspectFill"></image>
				<view class="topic-text-wrap">
					<text class="topic-tag">PLANTING</text>
					<text class="topic-title">{{ activityData.good_deed.title }}</text>
				</view>
			</view>
		</view>
		<!-- 滚动播报，8条消息 -->
		<view class="notice-bar">
			<view class="notice-icon">📢</view>
			<view class="notice-content">
				<view class="notice-text-wrap">
					<view class="notice-text" v-for="(item, index) in noticeList" :key="index">
						{{ item }}
					</view>
				</view>
			</view>
		</view>

		<!-- 分类标签 -->
		<scroll-view scroll-x class="tabs">
			<view v-for="(tab, index) in tabs" :key="index" class="tab-item" :class="{ active: currentTab === index }"
				@click="switchTab(index)">
				{{ tab.name }}
			</view>
		</scroll-view>

		<!-- 内容区 -->
		<view class="content">
			<!-- 日常分享 -->
			<view v-if="currentTab === 0" class="tab-content share-content">
				<!-- 发表分享按钮 -->
				<view class="publish-btn-wrap">
					<button class="publish-btn" @click="showPublishModal">
						<text class="publish-icon">✏️</text>
						<text class="publish-text">发表分享</text>
					</button>
				</view>
				
				<!-- 加载提示 -->
				<view v-if="loading" class="loading-container">
					<text class="loading-text">加载中...</text>
				</view>
				<!-- 空状态提示 -->
				<view v-else-if="sharePosts.length === 0" class="empty-container">
					<text class="empty-text">暂无动态，快来发表第一条吧！</text>
				</view>
				<!-- 动态列表 -->
				<view v-else class="post-list">
					<view class="post-item" v-for="(post, idx) in sharePosts" :key="post.id">
						<view class="post-header">
							<view class="avatar" :style="{ backgroundColor: post.avatarColor }">
								<text class="avatar-text">{{ post.nickname.charAt(0) }}</text>
							</view>
							<view class="user-info">
								<text class="nickname">{{ post.nickname }}</text>
								<text class="time">{{ post.time }}</text>
							</view>
							<text class="tag" :class="post.tagClass">{{ post.tag }}</text>
						</view>
						<text class="post-content">{{ post.content }}</text>
						<view class="actions">
							<text @click="likePost('share', idx)" :class="{ active: post.liked }">👍
								{{ post.likes }}</text>
							<text @click="dislikePost('share', idx)" :class="{ active: post.disliked }">👎
								{{ post.dislikes }}</text>
							<text @click="commentPost('share', idx)" :class="{ active: post.commented }">💬
								{{ post.comments }}</text>
							<!-- <text class="points">+{{ post.points }}积分</text> -->
						</view>
						<!-- 评论列表：最多显示3条，超出滚动 -->
						<view class="comment-list" v-if="post.commentsList && post.commentsList.length">
							<view class="comment-item" v-for="(comment, cIdx) in post.commentsList.slice(0, 3)"
								:key="comment.commentId">
								<text class="comment-user">{{ comment.user }}：</text>
								<text class="comment-text">{{ comment.content }}</text>
								<text class="comment-time">{{ comment.time }}</text>
							</view>
							<view v-if="post.commentsList.length > 3" class="more-comment">
								共 {{ post.commentsList.length }} 条评论
							</view>
						</view>
						<view class="comment-placeholder" v-else>
							<text class="placeholder-text">暂无评论，快来抢沙发～</text>
						</view>
					</view>
				</view>
			</view>

			<!-- 志愿者页面 -->
			<view v-else-if="currentTab === 1">
				<view v-if="loading" class="loading-wrap">
					<view class="loading-spinner">
						<view class="loading-dot"></view>
						<view class="loading-dot"></view>
						<view class="loading-dot"></view>
					</view>
					<text class="loading-text">加载中...</text>
				</view>
				<view v-else class="volunteer-list">
					<view class="vol-item" @click="handleGoCampus" hover-class="vol-item-hover">
						<view class="vol-top">
							<text class="status-tag">招募中</text>
							<view class="hot-tag first-tag" v-if="activityData.campus.count === 0">抢首报</view>
							<view class="hot-tag flash-tag"
								v-else-if="activityData.campus.max - activityData.campus.count <=5 && activityData.campus.max - activityData.campus.count >0">
								仅剩{{activityData.campus.max - activityData.campus.count}}个名额</view>
							<text
								class="deadline">截止：{{ activityData.campus.time ? activityData.campus.time.slice(0,10) : '暂无' }}</text>
						</view>
						<view class="vol-header">
							<image class="vol-img" src="/static/美女1.png" mode="aspectFill"></image>
							<text class="vol-title">{{ activityData.campus.title || '加载中...' }}</text>
						</view>
						<view class="vol-progress">
							<view class="progress-bar">
								<view class="progress-inner"
									:style="{width:(activityData.campus.count && activityData.campus.max ? (activityData.campus.count/activityData.campus.max*100) : 0)+'%'}">
									<view class="progress-light"></view>
								</view>
							</view>
							<text
								class="progress-text">{{ scrollNums.vol_0 || 0 }}/{{ activityData.campus.max || 0 }}人</text>
						</view>
						<view class="first-tip" v-if="activityData.campus.count === 0">
							🎁 成为第一个报名者，解锁专属公益徽章+50积分
						</view>
					</view>
					<view class="vol-item" @click="handleGoElderly" hover-class="vol-item-hover">
						<view class="vol-top">
							<text class="status-tag">招募中</text>
							<view class="hot-tag first-tag" v-if="activityData.elderly.count === 0">抢首报</view>
							<view class="hot-tag flash-tag"
								v-else-if="activityData.elderly.max - activityData.elderly.count <=5 && activityData.elderly.max - activityData.elderly.count >0">
								仅剩{{activityData.elderly.max - activityData.elderly.count}}个名额</view>
							<text
								class="deadline">截止：{{ activityData.elderly.time ? activityData.elderly.time.slice(0,10) : '暂无' }}</text>
						</view>
						<view class="vol-header">
							<image class="vol-img" src="/static/美女2.png" mode="aspectFill"></image>
							<text class="vol-title">{{ activityData.elderly.title || '加载中...' }}</text>
						</view>
						<view class="vol-progress">
							<view class="progress-bar">
								<view class="progress-inner"
									:style="{width:(activityData.elderly.count && activityData.elderly.max ? (activityData.elderly.count/activityData.elderly.max*100) : 0)+'%'}">
									<view class="progress-light"></view>
								</view>
							</view>
							<text
								class="progress-text">{{ scrollNums.vol_1 || 0 }}/{{ activityData.elderly.max || 0 }}人</text>
						</view>
						<view class="first-tip" v-if="activityData.elderly.count === 0">
							🎁 成为第一个报名者，解锁专属公益徽章+50积分
						</view>
					</view>
					<view class="vol-item" @click="handleGoPark" hover-class="vol-item-hover">
						<view class="vol-top">
							<text class="status-tag">招募中</text>
							<view class="hot-tag first-tag" v-if="activityData.park.count === 0">抢首报</view>
							<view class="hot-tag flash-tag"
								v-else-if="activityData.park.max - activityData.park.count <=5 && activityData.park.max - activityData.park.count >0">
								仅剩{{activityData.park.max - activityData.park.count}}个名额</view>
							<text
								class="deadline">截止：{{ activityData.park.time ? activityData.park.time.slice(0,10) : '暂无' }}</text>
						</view>
						<view class="vol-header">
							<image class="vol-img" src="/static/美女3.png" mode="aspectFill"></image>
							<text class="vol-title">{{ activityData.park.title || '加载中...' }}</text>
						</view>
						<view class="vol-progress">
							<view class="progress-bar">
								<view class="progress-inner"
									:style="{width:(activityData.park.count && activityData.park.max ? (activityData.park.count/activityData.park.max*100) : 0)+'%'}">
									<view class="progress-light"></view>
								</view>
							</view>
							<text
								class="progress-text">{{ scrollNums.vol_2 || 0 }}/{{ activityData.park.max || 0 }}人</text>
						</view>
						<view class="first-tip" v-if="activityData.park.count === 0">
							🎁 成为第一个报名者，解锁专属公益徽章+50积分
						</view>
					</view>
					<view class="vol-item" @click="handleGoDoctor" hover-class="vol-item-hover">
						<view class="vol-top">
							<text class="status-tag">招募中</text>
							<view class="hot-tag first-tag" v-if="activityData.doctor.count === 0">抢首报</view>
							<view class="hot-tag flash-tag"
								v-else-if="activityData.doctor.max - activityData.doctor.count <=5 && activityData.doctor.max - activityData.doctor.count >0">
								仅剩{{activityData.doctor.max - activityData.doctor.count}}个名额</view>
							<text
								class="deadline">截止：{{ activityData.doctor.time ? activityData.doctor.time.slice(0,10) : '暂无' }}</text>
						</view>
						<view class="vol-header">
							<image class="vol-img" src="/static/美女4.png" mode="aspectFill"></image>
							<text class="vol-title">{{ activityData.doctor.title || '加载中...' }}</text>
						</view>
						<view class="vol-progress">
							<view class="progress-bar">
								<view class="progress-inner"
									:style="{width:(activityData.doctor.count && activityData.doctor.max ? (activityData.doctor.count/activityData.doctor.max*100) : 0)+'%'}">
									<view class="progress-light"></view>
								</view>
							</view>
							<text
								class="progress-text">{{ scrollNums.vol_3 || 0 }}/{{ activityData.doctor.max || 0 }}人</text>
						</view>
						<view class="first-tip" v-if="activityData.doctor.count === 0">
							🎁 成为第一个报名者，解锁专属公益徽章+50积分
						</view>
					</view>
					<view class="vol-item" @click="handleGoTrash" hover-class="vol-item-hover">
						<view class="vol-top">
							<text class="status-tag">招募中</text>
							<view class="hot-tag first-tag" v-if="activityData.trash.count === 0">抢首报</view>
							<view class="hot-tag flash-tag"
								v-else-if="activityData.trash.max - activityData.trash.count <=5 && activityData.trash.max - activityData.trash.count >0">
								仅剩{{activityData.trash.max - activityData.trash.count}}个名额</view>
							<text
								class="deadline">截止：{{ activityData.trash.time ? activityData.trash.time.slice(0,10) : '暂无' }}</text>
						</view>
						<view class="vol-header">
							<image class="vol-img" src="/static/鞠婧祎.png" mode="aspectFill"></image>
							<text class="vol-title">{{ activityData.trash.title || '加载中...' }}</text>
						</view>
						<view class="vol-progress">
							<view class="progress-bar">
								<view class="progress-inner"
									:style="{width:(activityData.trash.count && activityData.trash.max ? (activityData.trash.count/activityData.trash.max*100) : 0)+'%'}">
									<view class="progress-light"></view>
								</view>
							</view>
							<text
								class="progress-text">{{ scrollNums.vol_4 || 0 }}/{{ activityData.trash.max || 0 }}人</text>
						</view>
						<view class="first-tip" v-if="activityData.trash.count === 0">
							🎁 成为第一个报名者，解锁专属公益徽章+50积分
						</view>
					</view>
					<!-- 志愿者榜，3个就够了 -->
					<view class="rank-card">
						<view class="rank-title">🏆 本周爱心邻居榜</view>
						<view class="rank-list">
							<view class="rank-item" v-for="(item, index) in topVolunteers" :key="index">
								<view class="rank-num" :class="{'top1':index===0, 'top2':index===1, 'top3':index===2}">
									{{index+1}}
								</view>
								<image class="rank-avatar" :src="item.avatar" mode="aspectFill"></image>
								<text class="rank-name">{{item.name}}</text>
								<text class="rank-desc">{{item.desc}}</text>
							</view>
						</view>
					</view>
				</view>
			</view>

			<!-- 爱心捐款页面 -->
			<view v-else-if="currentTab === 2">
				<view v-if="loading" class="loading-wrap">
					<view class="loading-spinner">
						<view class="loading-dot"></view>
						<view class="loading-dot"></view>
						<view class="loading-dot"></view>
					</view>
					<text class="loading-text">加载中...</text>
				</view>
				<view v-else-if="donateList.length === 0" class="empty-wrap">
					<image class="empty-img" src="/static/logo.png" mode="widthFix"></image>
					<text class="empty-text">暂无捐款项目</text>
					<text class="empty-desc">快来发起第一个爱心捐款吧～</text>
				</view>
				<view v-else>
					<view class="donate-grid">
						<view class="card" v-for="(item, index) in donateList" :key="item.id"
							@click="goDonateDetail(item)" hover-class="card-hover">
							<view class="card-img-wrap">
								<image class="card-img" :src="getDonateImage(item, index)" mode="aspectFill"
									lazy-load="true" @error="onDonateImgError(index)"></image>
								<view class="card-img-mask"></view>
							</view>
							<view class="card-body">
								<view class="card-title">{{ item.title || '暂无标题' }}</view>
								<view class="card-progress">
									<view class="progress-bar donate-bar">
										<view class="progress"
											:style="{width: (getRate(item) ? getRate(item) : (item.nowMoney && item.targetMoney ? (item.nowMoney / item.targetMoney * 100) : 0)) + '%'}">
											<view class="progress-light"></view>
										</view>
									</view>
								</view>
								<view class="card-info">
									<text>已筹：{{ scrollNums['donate_'+index] || 0 }}元</text>
									<text>目标：{{ item.targetMoney || 0 }}元</text>
								</view>
								<view class="card-rate">
									<text>{{ getRate(item) ? getRate(item) : (item.nowMoney && item.targetMoney ? ((item.nowMoney / item.targetMoney) * 100).toFixed(1) : 0) }}%</text>
								</view>
							</view>
						</view>
					</view>
					<!-- 捐款榜，现在只有4个，都有头像，删掉了没头像的两个 -->
					<view class="rank-card">
						<view class="rank-title">🏆 本周爱心捐款榜</view>
						<view class="rank-list">
							<view class="rank-item" v-for="(item, index) in topDonators" :key="index">
								<view class="rank-num" :class="{'top1':index===0, 'top2':index===1, 'top3':index===2}">
									{{index+1}}
								</view>
								<image class="rank-avatar" :src="item.avatar" mode="aspectFill"></image>
								<text class="rank-name">{{item.name}}</text>
								<text class="rank-desc">{{item.desc}}</text>
							</view>
						</view>
					</view>
				</view>
			</view>

			<!-- 积分商城入口 -->
			<view v-else-if="currentTab === 3" class="tab-content points-shop">
				<view class="mall-entrance" @click="goToMall">
					<view class="entrance-card">
						<view class="entrance-icon">🎁</view>
						<view class="entrance-info">
							<text class="entrance-title">积分商城</text>
							<text class="entrance-subtitle">使用积分兑换水电费</text>
						</view>
						<view class="entrance-arrow">→</view>
					</view>
				</view>
				
				<!-- 赚积分任务中心入口 -->
				<view class="task-center-entrance" @click="goToTaskCenter" hover-class="task-center-hover">
					<view class="entrance-card">
						<view class="entrance-icon">🎯</view>
						<view class="entrance-info">
							<text class="entrance-title">赚积分任务中心</text>
							<text class="entrance-subtitle">做任务，领更多积分</text>
						</view>
						<view class="entrance-arrow">→</view>
					</view>
				</view>
			</view>
		</view>
		
		<!-- 发表分享弹窗 -->
		<view class="publish-modal" v-if="showPublishDialog">
			<view class="publish-mask" @click="closePublishModal"></view>
			<view class="publish-content">
				<view class="publish-header">
					<text class="publish-title">发表分享</text>
					<text class="publish-close" @click="closePublishModal">✕</text>
				</view>
				<view class="publish-body">
					<textarea 
						class="publish-input" 
						v-model="publishContent" 
						placeholder="分享你的新鲜事..."
						maxlength="500"
						:auto-height="true"
					></textarea>
					<view class="publish-tags">
						<text class="tag-label">选择标签：</text>
						<view class="tag-list">
							<text 
								v-for="(tag, index) in publishTags" 
								:key="index"
								class="tag-item"
								:class="{ active: selectedTag === tag }"
								@click="selectedTag = tag"
							>
								{{ tag }}
							</text>
						</view>
					</view>
					<text class="publish-hint">{{ publishContent.length }}/500字</text>
				</view>
				<view class="publish-footer">
					<button class="publish-submit" @click="submitPublish" :disabled="!publishContent.trim()">
						发布
					</button>
				</view>
			</view>
		</view>
		
		<view class="heart-container" v-if="showHearts">
			<view class="heart-item" v-for="(item, index) in hearts" :key="index"
				:style="{left: item.left+'rpx', animationDelay: item.delay+'s'}">❤️</view>
		</view>
	</view>
</template>

<script>
	import {
		communityApi,
		pointsApi
	} from '../../api/index.js'

	export default {
		data() {
			return {
				currentTab: 0,
				tabs: [{
						name: '日常分享',
						type: 'share'
					},
					{
						name: '志愿者',
						type: 'volunteer'
					},
					{
						name: '爱心捐款',
						type: 'donate'
					},
					{
						name: '积分商城',
						type: 'points'
					}
				],
				posts: [],
				activityData: {
					campus: {
						id: 1,
						title: "加载中...",
						time: "2025-01-01 00:00:00",
						address: "",
						content: "",
						contact: "王社工 13800138000",
						count: 0,
						max: 1,
						imageUrl: "/static/activity01.jpg"
					},
					elderly: {
						id: 2,
						title: "加载中...",
						time: "2025-01-01 00:00:00",
						address: "",
						content: "",
						contact: "王社工 13800138000",
						count: 0,
						max: 1,
						imageUrl: "/static/activity02.webp"
					},
					park: {
						id: 3,
						title: "加载中...",
						time: "2025-01-01 00:00:00",
						address: "",
						content: "",
						contact: "王社工 13800138000",
						count: 0,
						max: 1,
						imageUrl: "/static/activity03.jpg"
					},
					doctor: {
						id: 4,
						title: "加载中...",
						time: "2025-01-01 00:00:00",
						address: "",
						content: "",
						contact: "王社工 13800138000",
						count: 0,
						max: 1,
						imageUrl: "/static/activity04.jpg"
					},
					trash: {
						id: 5,
						title: "加载中...",
						time: "2025-01-01 00:00:00",
						address: "",
						content: "",
						contact: "王社工 13800138000",
						count: 0,
						max: 1,
						imageUrl: "/static/activity05.webp"
					},
					volunteer: {
						id: 6,
						title: "加载中...",
						time: "2025-01-01 00:00:00",
						address: "",
						content: "",
						contact: "王社工 13800138000",
						count: 0,
						max: 1,
						imageUrl: "/static/activity_show.jpg"
					},
					good_deed: {
						id: 7,
						title: "加载中...",
						time: "2025-01-01 00:00:00",
						address: "",
						content: "",
						contact: "王社工 13800138000",
						count: 0,
						max: 1,
						imageUrl: "/static/activity_tree.jpg"
					}
				},
				donateList: [],
				donateImgErrors: {},
				loading: false,
				sharePosts: [],
				volunteerStats: {
					total: 156,
					activeToday: 43,
					totalHours: 2840
				},
				volunteerPosts: [{
						nickname: '热心小陈',
						time: '昨天 14:30',
						content: '参加了社区周末的环保清洁活动，捡了满满两大袋垃圾，虽然累但很有意义！希望大家一起爱护环境~',
						likes: 32,
						dislikes: 0,
						points: 8,
						liked: false,
						disliked: false,
						avatarColor: '#4caf50',
						duration: 12,
						times: 5,
						bio: '环保爱好者，喜欢户外运动，希望能让社区更美好。'
					},
					{
						nickname: '猫咪守护者',
						time: '昨天 09:15',
						content: '流浪猫绝育计划今天顺利完成第一只猫咪，感谢两位兽医志愿者的付出！还需要更多帮手，一起加入吧。',
						likes: 56,
						dislikes: 2,
						points: 12,
						liked: false,
						disliked: false,
						avatarColor: '#ff9800',
						duration: 8,
						times: 3,
						bio: '动物保护志愿者，救助流浪猫狗是我的使命。'
					}
				],
				donateList: [],
				projectList: [{
						id: 1,
						cover: "/static/donation/clearing.jpg",
						status: "运行中",
						location: "北京科技大学天津学院",
						title: '"爱环保"垃圾清除公益行动',
						current: 128,
						target: 200,
						deadline: "2026-03-18"
					},
					{
						id: 2,
						cover: "/static/donation/job.jpg",
						status: "运行中",
						location: "恒基中心1座",
						title: "职场加油站志愿服务项目",
						current: 342,
						target: 1000,
						deadline: "2026-03-06"
					},
					{
						id: 3,
						cover: "/static/donation/elderly.jpg",
						status: "运行中",
						location: "朝阳区养老院",
						title: "关爱空巢老人陪伴计划",
						current: 45,
						target: 50,
						deadline: "2026-04-01"
					},
					{
						id: 4,
						cover: "/static/donation/student.jpg",
						status: "运行中",
						location: "河北涞源希望小学",
						title: "山区儿童助学物资捐赠",
						current: 89,
						target: 200,
						deadline: "2026-03-25"
					}
				],
				userPoints: 0,
				noticeList: [
					'3号楼张阿姨报名了关爱老人活动',
					'5分钟前，李叔叔捐了100元助力山区儿童',
					'10分钟前，王同学成为环保义演第一个报名者',
					'2号楼王奶奶捐了50元给流浪动物救助',
					'7号楼的小明报名了周末亲子手工分享',
					'1号楼的刘叔叔捐了200元给紧急救灾储备',
					'5号楼的张阿姨分享了她的公益日常',
					'4号楼的李同学成为了社区环保志愿者'
				],
				topVolunteers: [{
						name: '张阿姨',
						avatar: '/static/美女1.png',
						desc: '报名3次活动'
					},
					{
						name: '李叔叔',
						avatar: '/static/美女2.png',
						desc: '报名2次活动'
					},
					{
						name: '王同学',
						avatar: '/static/美女3.png',
						desc: '分享2次公益'
					}
				],
				scrollNums: {},
				topDonators: [{
						name: '张阿姨',
						avatar: '/static/美女1.png',
						desc: '捐款200元'
					},
					{
						name: '李叔叔',
						avatar: '/static/美女2.png',
						desc: '捐款150元'
					},
					{
						name: '王奶奶',
						avatar: '/static/美女3.png',
						desc: '捐款100元'
					},
					{
						name: '刘叔叔',
						avatar: '/static/美女4.png',
						desc: '捐款80元'
					}
				],
				showHearts: false,
				hearts: [],
				showPublishDialog: false,
				publishContent: '',
				selectedTag: '#日常分享',
				publishTags: ['#日常分享', '#好人好事', '#爱心捐款']
			}
		},
		onLoad(options) {
			this.fetchPosts()
			this.loadActivityFromBackend()
			this.loadDonateData()

			if (options && options.openPublish === 'true') {
				setTimeout(() => {
					this.showPublishModal()
				}, 500)
			}

			const that = this
			setTimeout(() => {
				if (that.activityData) {
					const activities = [
						that.activityData.campus,
						that.activityData.elderly,
						that.activityData.park,
						that.activityData.doctor,
						that.activityData.trash
					]
					activities.forEach((item, index) => {
						if (item && item.count) {
							that.scrollNumber(`vol_${index}`, item.count)
						}
					})
				}
			}, 300)
		},
		computed: {
			totalPostsCount() {
				return this.sharePosts.length + this.volunteerPosts.length;
			}
		},
		mounted() {
			this.fetchPoints();
		},
		onShow() {
			if (getApp().globalData && getApp().globalData.pendingCommunityTab !== undefined) {
				const targetTab = getApp().globalData.pendingCommunityTab
				this.currentTab = targetTab
				delete getApp().globalData.pendingCommunityTab
			}

			setTimeout(() => {
				this.loadActivityFromBackend();
				this.loadPostsFromBackend();
			}, 100);
		},

		watch: {
			donateList: {
				handler(newVal) {
					if (newVal && newVal.length > 0) {
						const that = this;
						newVal.forEach((item, index) => {
							let realNow = item.nowMoney;
							let rateNum = that.getRate(item);
							if (realNow === 0 && rateNum > 0 && item.targetMoney) {
								realNow = Math.floor(item.targetMoney * rateNum / 100);
							}
							if (realNow > 0) {
								that.scrollNumber(`donate_${index}`, realNow)
							}
						})
					}
				},
				immediate: true,
				deep: true
			}
		},
		methods: {
			getRate(item) {
				if (!item.rate) return 0;
				if (typeof item.rate === 'string') {
					return parseFloat(item.rate.replace('%', ''));
				}
				return item.rate;
			},
			scrollNumber(key, targetNum, duration = 1000) {
				const that = this
				const step = targetNum / (duration / 16)
				let current = 0
				const timer = setInterval(() => {
					current += step
					if (current >= targetNum) {
						current = targetNum
						clearInterval(timer)
					}
					that.$set(that.scrollNums, key, Math.floor(current))
				}, 16)
			},
			triggerHeartRain() {
				const that = this
				that.showHearts = true
				const hearts = []
				for (let i = 0; i < 20; i++) {
					hearts.push({
						left: Math.random() * 600,
						delay: Math.random() * 1
					})
				}
				that.hearts = hearts
				setTimeout(() => {
					that.showHearts = false
				}, 2500)
			},
			updateActivityCount(type, newCount) {
				if (this.activityData[type]) {
					this.activityData[type].count = newCount;

					const indexMap = {
						campus: 0,
						elderly: 1,
						park: 2,
						doctor: 3,
						trash: 4,
						volunteer: 0,
						good_deed: 1
					};

					const index = indexMap[type];
					if (index !== undefined) {
						this.scrollNumber(`vol_${index}`, newCount, 500);
					}

					this.$forceUpdate();
				}
			},
			handleGoCampus() {
				this.goTopic('campus');
				this.triggerHeartRain();
			},
			handleGoElderly() {
				this.goTopic('elderly');
				this.triggerHeartRain();
			},
			handleGoPark() {
				this.goTopic('park');
				this.triggerHeartRain();
			},
			handleGoDoctor() {
				this.goTopic('doctor');
				this.triggerHeartRain();
			},
			handleGoTrash() {
				this.goTopic('trash');
				this.triggerHeartRain();
			},

			async fetchPosts() {
				this.loading = true;
				try {
					const res = await communityApi.getPostList({
						pageNum: 1,
						pageSize: 10
					});
					if (res.code === 200) {
						const posts = res.data || [];
						this.sharePosts = posts.map(post => ({
							id: post.postId,
							avatarColor: this.getAvatarColor(post.userId || post.postId),
							nickname: post.userName || '用户' + post.postId,
							time: post.createTime || new Date().toLocaleString(),
							tag: post.tag || '#日常分享',
							tagClass: this.getTagClass(post.tag || '#日常分享'),
							content: post.content,
							likes: post.likes || 0,
							dislikes: post.dislikes || 0,
							comments: post.comments ? post.comments.length : 0,
							commentsList: post.comments ? post.comments.map(comment => ({
								commentId: comment.commentId,
								user: comment.userName,
								content: comment.content,
								time: comment.createTime
							})) : [],
							points: post.points || 5,
							liked: false,
							disliked: false,
							commented: false
						}));
					} else {
						uni.showToast({
							title: res.msg || '获取失败',
							icon: 'none'
						});
					}
				} catch (e) {
					console.error(e);
					uni.showToast({
						title: '获取数据失败',
						icon: 'none'
					});
				} finally {
					this.loading = false;
				}
			},
			async fetchBills() {
				try {
					const res = await pointsApi.getBills();
					if (res.code === 200) {
						this.bills = res.data.map(bill => {
							let icon = '💧';
							if (bill.type === 'water') icon = '💧';
							else if (bill.type === 'electricity') icon = '⚡';
							else if (bill.type === 'property') icon = '🏠';
							return {
								...bill,
								icon
							};
						});
					} else {
						uni.showToast({
							title: '获取账单失败',
							icon: 'none'
						});
					}
				} catch (e) {
					console.error(e);
				}
			},
			goToMall() {
				uni.navigateTo({
					url: '/pages/mall/mall'
				});
			},
			
			goToTaskCenter() {
				console.log('点击了任务中心');
				uni.navigateTo({
					url: '/pages/mall/task-center',
					success: () => {
						console.log('跳转成功');
					},
					fail: (err) => {
						console.error('跳转失败:', err);
						uni.showToast({
							title: '页面跳转失败',
							icon: 'none'
						});
					}
				});
			},
			
			loadActivityFromBackend() {
				if (this.loading) return;
				this.loading = true;

				uni.request({
					url: "http://127.0.0.1:8080/community/activity/list",
					method: "GET",
					header: {
						'Content-Type': 'application/json'
					},
					success: (res) => {
						if (res.data.code === 200) {
							let dataList = res.data.data || [];
							const idToType = {
								1: 'campus',
								2: 'elderly',
								3: 'park',
								4: 'doctor',
								5: 'trash',
								6: 'volunteer',
								7: 'good_deed'
							};
							dataList.forEach(item => {
								let type = idToType[item.activityId];
								if (!type) return;

								this.activityData[type] = {
									id: item.activityId,
									title: item.activityTitle || '暂无标题',
									time: item.activityTime || '暂无时间',
									address: item.activityLocation || '暂无地址',
									content: item.activityContent || '暂无内容',
									contact: "王社工 13800138000",
									count: item.currentParticipants || 0,
									max: item.maxParticipants || 0,
									imageUrl: this.getImageById(item.activityId)
								};
							});
						} else {
							uni.showToast({
								title: '数据加载失败',
								icon: 'none'
							});
						}
					},
					fail: () => {
						console.log("请求失败");
						uni.showToast({
							title: '网络错误，请重试',
							icon: 'none'
						});
					},
					complete: () => {
						this.loading = false;
					}
				});
			},

			loadPostsFromBackend() {
				if (this.loading) return;
				this.loading = true;

				uni.request({
					url: "http://127.0.0.1:8080/community/share/list",
					method: "GET",
					header: {
						'Content-Type': 'application/json'
					},
					success: (res) => {
						if (res.data.code === 200) {
							const posts = res.data.data || [];
							this.posts = posts.map(post => ({
								id: post.postId,
								avatarColor: this.getAvatarColor(post.userId || post.postId),
								nickname: post.userName || '用户' + post.postId,
								time: post.createTime || new Date().toLocaleString(),
								tag: post.tag || '#日常分享',
								tagClass: this.getTagClass(post.tag || '#日常分享'),
								content: post.content,
								likes: post.likes || 0,
								dislikes: post.dislikes || 0,
								comments: post.comments ? post.comments.length : 0,
								commentsList: post.comments ? post.comments.map(comment => ({
									commentId: comment.commentId,
									user: comment.userName,
									content: comment.content,
									time: comment.createTime
								})) : [],
								points: post.points || 5,
								liked: false,
								disliked: false,
								commented: false
							}));
						} else {
							uni.showToast({
								title: '帖子加载失败',
								icon: 'none'
							});
						}
					},
					fail: () => {
						uni.showToast({
							title: '网络错误，请重试',
							icon: 'none'
						});
					},
					complete: () => {
						this.loading = false;
					}
				});
			},

			loadDonateData() {
				if (this.loading) return;
				this.loading = true;

				uni.request({
					url: "http://127.0.0.1:8080/community/donation/list",
					method: "GET",
					header: {
						'Content-Type': 'application/json'
					},
					success: (res) => {
						if (res.data.code === 200) {
							this.donateList = res.data.data || [];
						} else {
							uni.showToast({
								title: '捐款数据加载失败',
								icon: 'none'
							});
						}
					},
					fail: () => {
						console.log("捐款请求失败");
						uni.showToast({
							title: '网络错误，请重试',
							icon: 'none'
						});
					},
					complete: () => {
						this.loading = false;
					}
				});
			},
			goDonateDetail(item) {
				if (!item) return;
				uni.navigateTo({
					url: `/pages/community/donate-detail?donate=${encodeURIComponent(JSON.stringify(item))}`
				});
			},

			goTopic(type) {
				let act = this.activityData[type];
				if (!act || act.title === '加载中...') {
					uni.showToast({
						title: '活动数据加载中，请稍后',
						icon: 'none'
					});
					return;
				}
				uni.navigateTo({
					url: `/pages/community/topic-detail?activity=${encodeURIComponent(JSON.stringify(act))}`
				});
			},
			goCampus() {
				this.goTopic('campus');
			},
			goElderly() {
				this.goTopic('elderly');
			},
			goPark() {
				this.goTopic('park');
			},
			goDoctor() {
				this.goTopic('doctor');
			},
			goTrash() {
				this.goTopic('trash');
			},

			async fetchPoints() {
				const userInfo = uni.getStorageSync('userInfo');
				if (userInfo && userInfo.userId) {
					try {
						const res = await pointsApi.getPoints(userInfo.userId);
						if (res.code === 200) {
							this.userPoints = res.data.availablePoints || 0;
						} else {
							console.error('获取积分失败:', res.msg);
						}
					} catch (e) {
						console.error('获取积分异常:', e);
					}
				}
			},

			getTagClass(tag) {
				if (tag === '#好人好事') return 'good'
				if (tag === '#日常分享') return 'share'
				if (tag === '#爱心捐款') return 'donate'
				return ''
			},

			getAvatarColor(userId) {
				const colors = ['#6b8c42', '#2d8cf0', '#e67e22', '#4caf50', '#ff9800']
				return colors[userId % colors.length]
			},

			getDonateImage(item, index) {
				const fallbacks = [
					'/static/help.jpg',
					'/static/animal.jpg',
					'/static/study.jpg',
					'/static/health.jpg',
					'/static/environment.jpg'
				];
				if (this.donateImgErrors[index] || !item.image) {
					return fallbacks[index % fallbacks.length];
				}
				if (item.image.startsWith('http')) {
					return item.image;
				}
				return 'http://127.0.0.1:8080' + item.image;
			},
			onDonateImgError(index) {
				this.$set(this.donateImgErrors, index, true);
			},
			getImageById(id) {
				const map = {
					1: "/static/activity01.jpg",
					2: "/static/activity02.webp",
					3: "/static/activity03.jpg",
					4: "/static/activity04.jpg",
					5: "/static/activity05.webp",
					6: "/static/activity_show.jpg",
					7: "/static/activity_tree.jpg"
				};
				return map[id] || "/static/activity01.jpg";
			},

			switchTab(index) {
				if (index === 0 && this.sharePosts.length === 0) {
					this.fetchPosts();
				} else if (index === 1) {
					this.loadActivityFromBackend();
				} else if (index === 2 && this.donateList.length === 0) {
					this.loadDonateData();
				}
				this.currentTab = index;
			},

			likePost(type, idx) {
				// 统一处理日常分享和志愿者页面的点赞逻辑
				const list = type === 'share' ? this.sharePosts : this.volunteerPosts
				const post = list[idx]
				if (!post.liked) {
					post.likes++
					post.liked = true
					if (post.disliked) {
						post.dislikes--
						post.disliked = false
					}
					
					// 点赞加2积分
					const points = 2
					uni.showToast({
						title: `+${points}积分`,
						icon: 'none'
					})
					
					// 根据登录人的ID把积分加到数据库中
					const token = uni.getStorageSync('community_token')
					if (token) {
						uni.request({
							url: 'http://127.0.0.1:8080/home/points/add',
							method: 'POST',
							header: {
								'Content-Type': 'application/json',
								'Authorization': 'Bearer ' + token
							},
							data: {
								points: points,
								reason: '点赞互动'
							},
							success: (res) => {
								console.log('添加积分成功:', res.data)
							},
							fail: (err) => {
								console.error('添加积分失败:', err)
							}
						})
					}
				} else {
					post.likes--
					post.liked = false
					uni.showToast({
						title: '已取消点赞',
						icon: 'none'
					})
				}
			},

			dislikePost(type, idx) {
				// 统一处理日常分享和志愿者页面的点踩逻辑
				const list = type === 'share' ? this.sharePosts : this.volunteerPosts
				const post = list[idx]
				if (!post.disliked) {
					post.dislikes++
					post.disliked = true
					if (post.liked) {
						post.likes--
						post.liked = false
					}
					uni.showToast({
						title: '点踩成功',
						icon: 'none'
					})
				} else {
					post.dislikes--
					post.disliked = false
					uni.showToast({
						title: '已取消点踩',
						icon: 'none'
					})
				}
			},
			async commentPost(type, idx) {
				const list = type === 'share' ? this.sharePosts : this.volunteerPosts;
				const post = list[idx];

				if (post.commented) {
					uni.showToast({
						title: '您已经评论过啦',
						icon: 'none'
					});
					return;
				}

				uni.showModal({
					title: '发表评论',
					editable: true,
					placeholderText: '写下你的想法...',
					success: async (modalRes) => {
						if (modalRes.confirm && modalRes.content.trim()) {
							const content = modalRes.content.trim();
							try {
								const res = await communityApi.addComment({
									postId: post.id,
									content
								});
								if (res.code === 200) {
									post.comments++;
									post.commented = true;
									post.commentsList.unshift({
										commentId: Date.now(),
										user: '我', // 从全局 store 获取真实昵称
										content,
										time: '刚刚'
									});
									// 评论奖励积分（示例）
									this.points += 5;
								}
							} catch (e) {
								console.error(e);
								uni.showToast({
									title: '评论失败',
									icon: 'none'
								});
							}
						}
					}
				});
			},
			
			// 显示发表分享弹窗
			showPublishModal() {
				// 检查用户是否登录
				const token = uni.getStorageSync('community_token');
				if (!token) {
					uni.showToast({
						title: '请先登录',
						icon: 'none'
					});
					return;
				}
				this.showPublishDialog = true;
			},
			
			// 关闭发表分享弹窗
			closePublishModal() {
				this.showPublishDialog = false;
				this.publishContent = '';
				this.selectedTag = '#日常分享';
			},
			
			// 封装 uni.request 为 Promise
			request(options) {
				return new Promise((resolve, reject) => {
					uni.request({
						...options,
						success: (res) => {
							resolve(res);
						},
						fail: (err) => {
							reject(err);
						}
					});
				});
			},

			// 提交发表分享
			async submitPublish() {
				if (!this.publishContent.trim()) {
					uni.showToast({
						title: '请输入分享内容',
						icon: 'none'
					});
					return;
				}
				
				const token = uni.getStorageSync('community_token');
				
				uni.showLoading({ title: '发布中...' });
				
				try {
					// 1. 发表分享
					const publishRes = await this.request({
						url: 'http://127.0.0.1:8080/community/post',
						method: 'POST',
						data: {
							content: this.publishContent.trim(),
							tag: this.selectedTag
						},
						header: {
							'Content-Type': 'application/json',
							'Authorization': 'Bearer ' + token
						}
					});
					
					if (publishRes.data.code === 200) {
						// 2. 完成发帖任务
						const taskRes = await this.request({
							url: 'http://127.0.0.1:8080/community/task/complete/post',
							method: 'POST',
							header: {
								'Authorization': 'Bearer ' + token
							}
						});
						
						// 3. 显示发布成功和积分到账弹窗
						uni.showToast({
							title: '发布成功！',
							icon: 'success'
						});
						
						// 显示积分到账弹窗
						if (taskRes.data.code === 200 && taskRes.data.data) {
							this.showPointsModal(taskRes.data.data.points || 10);
						}
						
						// 4. 关闭弹窗并刷新列表
						this.closePublishModal();
						this.fetchPosts();
					} else {
						uni.showToast({
							title: publishRes.data.msg || '发布失败',
							icon: 'none'
						});
					}
				} catch (e) {
					console.error('发布失败:', e);
					uni.showToast({
						title: '发布失败，请重试',
						icon: 'none'
					});
				} finally {
					uni.hideLoading();
				}
			},

			// 显示积分到账弹窗
			showPointsModal(points) {
				// 显示积分到账弹窗
				const modal = {
					icon: '🎉',
					title: '任务完成！',
					points: points,
					content: '已发放至您的账户',
					buttonText: '知道啦'
				};
				
				// 使用 uni.showModal 显示弹窗
				uni.showModal({
					title: modal.title,
					content: `${modal.icon}\n+${modal.points} 积分\n${modal.content}`,
					showCancel: false,
					confirmText: modal.buttonText,
					success: function (res) {
						if (res.confirm) {
							console.log('用户点击了知道啦');
						}
					}
				});
			}
		}
	};
</script>

<style scoped>
	.progress-bar {
		flex: 1;
		height: 16rpx;
		background: #f0f2f5;
		border-radius: 8rpx;
		overflow: hidden;
		box-shadow: inset 0 4rpx 8rpx rgba(0, 0, 0, 0.08);
		position: relative;
	}

	.progress-bar::after {
		content: '';
		position: absolute;
		top: 0;
		left: -100%;
		width: 100%;
		height: 100%;
		background: linear-gradient(90deg, transparent, rgba(255, 255, 255, 0.6), transparent);
		animation: progressShine 2s infinite linear;
		z-index: 2;
	}

	.community-page {
		background-color: #f5f7fa;
		min-height: 100vh;
		overflow-y: visible;
		padding: 40rpx 30rpx 100rpx;
		box-sizing: border-box;
	}

	.header {
		margin-bottom: 40rpx;
	}

	.title {
		font-size: 48rpx;
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

	.hot-topics {
		display: flex;
		gap: 24rpx;
		margin-bottom: 40rpx;
	}

	.topic-card {
		flex: 1;
		border-radius: 24rpx;
		color: #fff;
		box-shadow: 0 8rpx 24rpx rgba(0, 0, 0, 0.15);
		transition: all 0.3s ease;
		position: relative;
		overflow: hidden;
		height: 400rpx;
	}

	.topic-card:hover {
		transform: translateY(-8rpx);
		box-shadow: 0 12rpx 32rpx rgba(0, 0, 0, 0.2);
	}

	.topic-card:active {
		transform: translateY(-4rpx);
		box-shadow: 0 6rpx 20rpx rgba(0, 0, 0, 0.15);
	}

	.topic-img {
		width: 100%;
		height: 100%;
		position: absolute;
		top: 0;
		left: 0;
		z-index: 0;
	}

	.topic-text-wrap {
		position: absolute;
		bottom: 0;
		left: 0;
		width: 100%;
		padding: 30rpx;
		background: linear-gradient(to top, rgba(0, 0, 0, 0.7) 0%, rgba(0, 0, 0, 0.3) 60%, transparent 100%);
		z-index: 1;
	}

	.topic-tag {
		font-size: 22rpx;
		opacity: 0.9;
		margin-bottom: 12rpx;
		display: inline-block;
		background: rgba(255, 255, 255, 0.25);
		padding: 4rpx 12rpx;
		border-radius: 12rpx;
		color: #fff;
	}

	.topic-title {
		font-size: 32rpx;
		font-weight: 600;
		line-height: 1.4;
		display: -webkit-box;
		-webkit-line-clamp: 2;
		-webkit-box-orient: vertical;
		overflow: hidden;
		text-overflow: ellipsis;
		color: #fff;
	}

	.notice-bar {
		display: flex;
		align-items: center;
		background: #fff;
		border-radius: 20rpx;
		padding: 20rpx 24rpx;
		margin-bottom: 30rpx;
		box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.04);
	}

	.notice-icon {
		font-size: 32rpx;
		margin-right: 16rpx;
	}

	.notice-content {
		flex: 1;
		overflow: hidden;
		position: relative;
		height: 40rpx;
	}

	.notice-text-wrap {
		display: flex;
		white-space: nowrap;
		animation: scroll-left 30s linear infinite;
	}

	.notice-text {
		margin-right: 60rpx;
		font-size: 24rpx;
		color: #666;
		display: inline-block;
		line-height: 40rpx;
	}

	@keyframes scroll-left {
		0% {
			transform: translateX(100%);
		}

		100% {
			transform: translateX(-100%);
		}
	}

	.tabs {
		white-space: nowrap;
		margin-bottom: 30rpx;
		padding: 10rpx 0;
	}

	.tab-item {
		display: inline-block;
		font-size: 28rpx;
		color: #666;
		margin-right: 40rpx;
		padding: 12rpx 0;
		position: relative;
	}

	.tab-item.active {
		color: #6b8c42;
		font-weight: 500;
	}

	.tab-item.active::after {
		content: '';
		position: absolute;
		bottom: 0;
		left: 0;
		width: 100%;
		height: 4rpx;
		background-color: #6b8c42;
		border-radius: 4rpx;
	}

	.content {
		min-height: 100rpx;
	}

	.loading-container {
		display: flex;
		justify-content: center;
		align-items: center;
		padding: 100rpx 0;
	}

	.loading-text {
		font-size: 28rpx;
		color: #8a9aa8;
	}

	.empty-container {
		display: flex;
		justify-content: center;
		align-items: center;
		padding: 100rpx 0;
		flex-direction: column;
	}

	.empty-text {
		font-size: 28rpx;
		color: #8a9aa8;
		text-align: center;
	}

	.post-list {
		margin-bottom: 30rpx;
	}

	.post-item {
		background: #fff;
		border-radius: 20rpx;
		padding: 24rpx;
		margin-bottom: 20rpx;
		box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.04);
	}

	.post-header {
		display: flex;
		align-items: center;
		margin-bottom: 16rpx;
	}

	.avatar {
		width: 60rpx;
		height: 60rpx;
		border-radius: 50%;
		display: flex;
		justify-content: center;
		align-items: center;
		margin-right: 16rpx;
	}

	.avatar-text {
		color: #fff;
		font-size: 28rpx;
		font-weight: 500;
	}

	.user-info {
		flex: 1;
	}

	.nickname {
		font-size: 28rpx;
		font-weight: 500;
		color: #333;
		display: block;
		margin-bottom: 4rpx;
	}

	.time {
		font-size: 20rpx;
		color: #999;
		display: block;
	}

	.tag {
		font-size: 20rpx;
		padding: 4rpx 12rpx;
		border-radius: 12rpx;
		margin-left: 12rpx;
	}

	.tag.share {
		background: #e6f7ff;
		color: #1890ff;
	}

	.tag.good {
		background: #f6ffed;
		color: #52c41a;
	}

	.tag.donate {
		background: #fff7e6;
		color: #fa8c16;
	}

	.post-content {
		font-size: 26rpx;
		color: #333;
		line-height: 1.5;
		margin-bottom: 20rpx;
		word-break: break-word;
	}

	.actions {
		display: flex;
		align-items: center;
		margin-bottom: 16rpx;
	}

	.actions text {
		font-size: 24rpx;
		color: #666;
		margin-right: 32rpx;
		display: flex;
		align-items: center;
	}

	.actions text.active {
		color: #6b8c42;
	}

	.points {
		margin-left: auto;
		color: #ff9500;
		font-weight: 500;
	}

	.comment-list {
		border-top: 1rpx solid #f0f2f5;
		padding-top: 16rpx;
	}

	.comment-item {
		font-size: 24rpx;
		color: #666;
		margin-bottom: 12rpx;
		line-height: 1.4;
	}

	.comment-user {
		font-weight: 500;
		color: #333;
	}

	.comment-time {
		font-size: 20rpx;
		color: #999;
		margin-left: 12rpx;
	}

	.more-comment {
		font-size: 22rpx;
		color: #999;
		text-align: center;
		margin-top: 8rpx;
	}

	.comment-placeholder {
		border-top: 1rpx solid #f0f2f5;
		padding-top: 16rpx;
	}

	.placeholder-text {
		font-size: 24rpx;
		color: #999;
		text-align: center;
	}

	/* 志愿者页面样式 */
	.volunteer-list {
		margin-bottom: 30rpx;
	}

	.vol-item {
		background: #fff;
		border-radius: 20rpx;
		padding: 24rpx;
		margin-bottom: 20rpx;
		box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.04);
		transition: all 0.3s ease;
	}

	.vol-item-hover {
		transform: translateY(-4rpx);
		box-shadow: 0 8rpx 24rpx rgba(0, 0, 0, 0.08);
	}

	.vol-top {
		display: flex;
		justify-content: space-between;
		align-items: center;
		margin-bottom: 16rpx;
	}

	.status-tag {
		font-size: 20rpx;
		padding: 4rpx 12rpx;
		background: #f6ffed;
		color: #52c41a;
		border-radius: 12rpx;
		font-weight: 500;
	}

	.deadline {
		font-size: 20rpx;
		color: #999;
	}

	.vol-header {
		display: flex;
		align-items: center;
		margin-bottom: 16rpx;
	}

	.vol-img {
		width: 80rpx;
		height: 80rpx;
		border-radius: 16rpx;
		margin-right: 16rpx;
	}

	.vol-title {
		flex: 1;
		font-size: 26rpx;
		font-weight: 500;
		color: #333;
		line-height: 1.4;
	}

	.vol-progress {
		display: flex;
		align-items: center;
		margin-bottom: 12rpx;
	}

	.progress-bar {
		flex: 1;
		height: 8rpx;
		background: #f0f2f5;
		border-radius: 4rpx;
		overflow: hidden;
		margin-right: 16rpx;
	}

	.progress-inner {
		height: 100%;
		background: #6b8c42;
		border-radius: 4rpx;
		position: relative;
	}

	.progress-light {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background: linear-gradient(90deg, rgba(255, 255, 255, 0) 0%, rgba(255, 255, 255, 0.6) 50%, rgba(255, 255, 255, 0) 100%);
		animation: shine 2s infinite;
	}

	@keyframes shine {
		0% {
			transform: translateX(-100%);
		}

		100% {
			transform: translateX(100%);
		}
	}

	.progress-text {
		font-size: 24rpx;
		color: #6b8c42;
		font-weight: 500;
		min-width: 120rpx;
		text-align: right;
	}

	.first-tip {
		font-size: 20rpx;
		color: #ff9500;
		background: #fff7e6;
		padding: 12rpx;
		border-radius: 12rpx;
		margin-top: 12rpx;
	}

	.hot-tag {
		font-size: 18rpx;
		padding: 2rpx 8rpx;
		border-radius: 8rpx;
		font-weight: 500;
		margin-left: 8rpx;
	}

	.first-tag {
		background: #fff2e8;
		color: #ff7a45;
	}

	.flash-tag {
		background: #fff1f0;
		color: #ff4d4f;
	}

	/* 排行榜样式 */
	.rank-card {
		background: #fff;
		border-radius: 20rpx;
		padding: 24rpx;
		margin-bottom: 20rpx;
		box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.04);
	}

	.rank-title {
		font-size: 28rpx;
		font-weight: 500;
		color: #333;
		margin-bottom: 20rpx;
	}

	.rank-list {
		display: flex;
		flex-direction: column;
		gap: 16rpx;
	}

	.rank-item {
		display: flex;
		align-items: center;
		padding: 12rpx 0;
	}

	.rank-num {
		width: 40rpx;
		height: 40rpx;
		display: flex;
		justify-content: center;
		align-items: center;
		border-radius: 50%;
		font-size: 20rpx;
		font-weight: bold;
		color: #666;
		margin-right: 16rpx;
	}

	.rank-num.top1 {
		background: #ffd700;
		color: #fff;
	}

	.rank-num.top2 {
		background: #c0c0c0;
		color: #fff;
	}

	.rank-num.top3 {
		background: #cd7f32;
		color: #fff;
	}

	.rank-avatar {
		width: 60rpx;
		height: 60rpx;
		border-radius: 50%;
		margin-right: 16rpx;
	}

	.rank-name {
		flex: 1;
		font-size: 24rpx;
		font-weight: 500;
		color: #333;
	}

	.rank-desc {
		font-size: 22rpx;
		color: #999;
		margin-left: 16rpx;
	}

	/* 爱心捐款页面样式 */
	.loading-wrap {
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		padding: 100rpx 0;
	}

	.loading-spinner {
		display: flex;
		align-items: center;
		margin-bottom: 20rpx;
	}

	.loading-dot {
		width: 16rpx;
		height: 16rpx;
		border-radius: 50%;
		background: #6b8c42;
		margin: 0 8rpx;
		animation: bounce 1.4s ease-in-out infinite;
	}

	.loading-dot:nth-child(2) {
		animation-delay: 0.2s;
	}

	.loading-dot:nth-child(3) {
		animation-delay: 0.4s;
	}

	@keyframes bounce {

		0%,
		60%,
		100% {
			transform: translateY(0);
		}

		30% {
			transform: translateY(-20rpx);
		}
	}

	.empty-wrap {
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		padding: 100rpx 0;
	}

	.empty-img {
		width: 200rpx;
		height: 200rpx;
		margin-bottom: 20rpx;
	}

	.empty-desc {
		font-size: 24rpx;
		color: #999;
		margin-top: 8rpx;
	}

	.donate-grid {
		display: grid;
		grid-template-columns: 1fr 1fr;
		gap: 20rpx;
		margin-bottom: 30rpx;
	}

	.card {
		background: #fff;
		border-radius: 20rpx;
		overflow: hidden;
		box-shadow: 0 4rpx 20rpx rgba(0, 0, 0, 0.04);
		transition: all 0.3s ease;
		position: relative;
	}

	.card-hover {
		transform: translateY(-4rpx);
		box-shadow: 0 8rpx 24rpx rgba(0, 0, 0, 0.08);
	}

	.card-img-wrap {
		position: relative;
		height: 160rpx;
		overflow: hidden;
	}

	.card-img {
		width: 100%;
		height: 100%;
	}

	.card-img-mask {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background: linear-gradient(to bottom, rgba(0, 0, 0, 0) 0%, rgba(0, 0, 0, 0.6) 100%);
	}

	.card-body {
		padding: 20rpx;
	}

	.card-title {
		font-size: 24rpx;
		font-weight: 500;
		color: #333;
		margin-bottom: 12rpx;
		line-height: 1.4;
		display: -webkit-box;
		-webkit-line-clamp: 2;
		-webkit-box-orient: vertical;
		overflow: hidden;
		text-overflow: ellipsis;
	}

	.card-progress {
		margin-bottom: 12rpx;
	}

	.progress-bar.donate-bar {
		margin-right: 12rpx;
	}

	.progress {
		height: 100%;
		background: #ff4d4f;
		border-radius: 4rpx;
		position: relative;
	}

	.card-info {
		display: flex;
		justify-content: space-between;
		font-size: 20rpx;
		color: #999;
		margin-bottom: 8rpx;
	}

	.card-rate {
		position: absolute;
		right: 20rpx;
		top: -30rpx;
		background: rgba(255, 255, 255, 0.95);
		padding: 8rpx 16rpx;
		border-radius: 16rpx;
		font-size: 22rpx;
		font-weight: 600;
		color: #ff4d4f;
		box-shadow: 0 8rpx 20rpx rgba(255, 77, 79, 0.3);
	}

	/* 积分商城入口样式 */
	.mall-entrance {
		margin-bottom: 30rpx;
	}

	.entrance-card {
		display: flex;
		align-items: center;
		background: linear-gradient(135deg, #6b8c42 0%, #4a6728 100%);
		border-radius: 24rpx;
		padding: 30rpx;
		color: #fff;
		box-shadow: 0 8rpx 24rpx rgba(107, 140, 66, 0.3);
		transition: all 0.3s ease;
	}

	.entrance-card:hover {
		transform: translateY(-4rpx);
		box-shadow: 0 12rpx 32rpx rgba(107, 140, 66, 0.4);
	}

	.entrance-icon {
		font-size: 64rpx;
		margin-right: 24rpx;
	}

	.entrance-info {
		flex: 1;
	}

	.entrance-title {
		font-size: 32rpx;
		font-weight: 500;
		display: block;
		margin-bottom: 8rpx;
	}

	.entrance-subtitle {
		font-size: 24rpx;
		opacity: 0.9;
		display: block;
	}

	.entrance-arrow {
		font-size: 32rpx;
		opacity: 0.8;
	}

	/* 赚积分任务中心入口样式 */
	.task-center-entrance {
		margin-bottom: 30rpx;
	}

	.task-center-entrance .entrance-card {
		display: flex;
		align-items: center;
		background: linear-gradient(135deg, #ff6b6b 0%, #ee5a5a 100%);
		border-radius: 24rpx;
		padding: 30rpx;
		color: #fff;
		box-shadow: 0 8rpx 24rpx rgba(255, 107, 107, 0.3);
		transition: all 0.3s ease;
	}

	.task-center-hover .entrance-card {
		transform: translateY(-4rpx);
		box-shadow: 0 12rpx 32rpx rgba(255, 107, 107, 0.4);
	}

	.task-center-entrance .entrance-card:active {
		transform: translateY(-4rpx);
		box-shadow: 0 12rpx 32rpx rgba(255, 107, 107, 0.4);
	}

	/* 发表分享按钮样式 */
	.publish-btn-wrap {
		padding: 20rpx 30rpx;
		background: #fff;
		margin-bottom: 20rpx;
		border-radius: 0 0 24rpx 24rpx;
	}

	.publish-btn {
		display: flex;
		align-items: center;
		justify-content: center;
		width: 100%;
		height: 80rpx;
		background: linear-gradient(135deg, #6b8c42 0%, #4a6728 100%);
		border-radius: 40rpx;
		border: none;
		box-shadow: 0 8rpx 20rpx rgba(107, 140, 66, 0.3);
	}

	.publish-icon {
		font-size: 32rpx;
		margin-right: 12rpx;
	}

	.publish-text {
		font-size: 30rpx;
		color: #fff;
		font-weight: 500;
	}

	/* 发表分享弹窗样式 */
	.publish-modal {
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		z-index: 9999;
		display: flex;
		align-items: flex-end;
	}

	.publish-mask {
		position: absolute;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background: rgba(0, 0, 0, 0.5);
	}

	.publish-content {
		position: relative;
		width: 100%;
		background: #fff;
		border-radius: 40rpx 40rpx 0 0;
		padding: 40rpx 30rpx;
		animation: slideUp 0.3s ease-out;
	}

	@keyframes slideUp {
		0% {
			transform: translateY(100%);
		}
		100% {
			transform: translateY(0);
		}
	}

	.publish-header {
		display: flex;
		justify-content: space-between;
		align-items: center;
		margin-bottom: 30rpx;
	}

	.publish-title {
		font-size: 36rpx;
		font-weight: bold;
		color: #333;
	}

	.publish-close {
		font-size: 40rpx;
		color: #999;
		padding: 10rpx;
	}

	.publish-body {
		margin-bottom: 30rpx;
	}

	.publish-input {
		width: 100%;
		min-height: 200rpx;
		padding: 20rpx;
		background: #f5f7fa;
		border-radius: 16rpx;
		font-size: 28rpx;
		color: #333;
		line-height: 1.6;
		box-sizing: border-box;
	}

	.publish-tags {
		margin-top: 20rpx;
	}

	.tag-label {
		font-size: 26rpx;
		color: #666;
		margin-bottom: 16rpx;
		display: block;
	}

	.tag-list {
		display: flex;
		flex-wrap: wrap;
		gap: 16rpx;
	}

	.tag-item {
		padding: 12rpx 24rpx;
		background: #f0f2f5;
		border-radius: 30rpx;
		font-size: 24rpx;
		color: #666;
		transition: all 0.3s;
	}

	.tag-item.active {
		background: #6b8c42;
		color: #fff;
	}

	.publish-hint {
		display: block;
		text-align: right;
		font-size: 24rpx;
		color: #999;
		margin-top: 16rpx;
	}

	.publish-footer {
		padding-top: 20rpx;
	}

	.publish-submit {
		width: 100%;
		height: 88rpx;
		background: linear-gradient(135deg, #6b8c42 0%, #4a6728 100%);
		border-radius: 44rpx;
		font-size: 32rpx;
		color: #fff;
		font-weight: 500;
		border: none;
	}

	.publish-submit[disabled] {
		background: #ccc;
	}

	/* 爱心效果样式 */
	.heart-container {
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		pointer-events: none;
		z-index: 9999;
	}

	.heart-item {
		position: absolute;
		top: 50%;
		left: 50%;
		font-size: 32rpx;
		animation: heart-fall 2.5s ease-in-out forwards;
	}

	@keyframes heart-fall {
		0% {
			transform: translate(-50%, -50%) scale(0);
			opacity: 1;
		}

		20% {
			transform: translate(-50%, -150%) scale(1);
			opacity: 1;
		}

		100% {
			transform: translate(-50%, 500rpx) scale(1);
			opacity: 0;
		}
	}
</style>