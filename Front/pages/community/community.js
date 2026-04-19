// pages/community/index.js
// 1. 导入您已在 community.js 中封装好的API函数
import { getPostList, addPost, likePost, addComment } from '@/common/apis/community.js'

Page({
  /**
   * 页面的初始数据
   * 此对象内的数据可与WXML绑定
   */
  data: {
    postList: [],       // 用于存储从后台获取的动态列表
    loading: false,     // 加载状态，用于显示加载提示
    pageNum: 1,         // 当前页码 （为后续分页准备）
    pageSize: 10,       // 每页条数
    hasMore: true,      // 是否有更多数据
  },
// 活动跳转
		goTopic(type) {
			let act = this.activityData[type];
			if (!act || act.title === '加载中...') {
				uni.showToast({ title: '活动数据加载中，请稍后', icon: 'none' });
				return;
			}
			uni.navigateTo({
				url: `/pages/community/topic-detail?activity=${encodeURIComponent(JSON.stringify(act))}`
			});
		},
		
		goCampus() { this.goTopic('campus'); },
		goElderly() { this.goTopic('elderly'); },
		goPark() { this.goTopic('park'); },
		goDoctor() { this.goTopic('doctor'); },
		goTrash() { this.goTopic('trash'); },
  /**
   * 生命周期函数--监听页面加载
   * 这是触发初始数据加载的关键位置
   */
  onLoad(options) {
    this.loadPostList();
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   * 实现下拉刷新
   */
  onPullDownRefresh() {
    // 刷新时，重置为第一页
    this.setData({
      pageNum: 1,
      hasMore: true,
      postList: []
    });
    this.loadPostList(1).finally(() => {
      wx.stopPullDownRefresh(); // 停止下拉刷新动画
    });
  },

  /**
   * 页面上拉触底事件的处理函数
   * 实现上拉加载更多
   */
  onReachBottom() {
    if (!this.data.hasMore || this.data.loading) return;
    this.loadPostList(this.data.pageNum + 1);
  },

  /**
   * 核心方法：加载动态列表数据
   * @param {number} pageNum - 要加载的页码
   */
  async loadPostList(pageNum = this.data.pageNum) {
    // 防止重复请求
    if (this.data.loading) return;
    if (pageNum !== 1 && !this.data.hasMore) return;

    this.setData({ loading: true });

    try {
      // 2. 调用您封装好的API函数，发送网络请求
      // 注意：您提供的 getPostList() 函数暂不支持分页参数，这里先按无参数调用。
      // 若需按标签筛选，可传入: getPostList({ tag: &#39;#日常分享&#39; })
      const res = await getPostList();

      // 3. 处理响应数据
      // 重要：需要根据您后端 Controller 返回的实际数据结构来调整。
      // 若依框架的 AjaxResult.success(data) 通常返回 { code: 200, msg: &quot;成功&quot;, rows: data }
      if (res.code === 200) {
        const newList = res.rows || res.data || []; // 从响应中提取列表数据

        this.setData({
          // 如果是第一页，则替换；如果是加载更多，则追加
          postList: pageNum === 1 ? newList : [...this.data.postList, ...newList],
          pageNum: pageNum,
          // 简单判断是否还有更多：如果返回数量小于请求数量，则认为无更多
          hasMore: newList.length >= this.data.pageSize
        });
      } else {
        // 接口返回业务错误
        wx.showToast({
          title: res.msg || &#39;获取数据失败&#39;,
          icon: &#39;none&#39;
        });
      }
    } catch (err) {
      // 网络错误或请求异常
      console.error(&#39;加载动态列表失败:&#39;, err);
      wx.showToast({
        title: &#39;网络请求失败，请重试&#39;,
        icon: &#39;none&#39;
      });
    } finally {
      this.setData({ loading: false });
    }
  },

  /**
   * 处理点赞/点踩事件 (示例)
   */
  async handleLike(e) {
    const { postid, type } = e.currentTarget.dataset;
    try {
      const res = await likePost({ postId: postid, type: type });
      if (res.code === 200) {
        // 更新本地数据，优化体验
        const updatedList = this.data.postList.map(item => {
          if (item.postId == postid) { // 注意类型比较
            if (type === &#39;like&#39;) {
              item.likes = (item.likes || 0) + 1;
            } else if (type === &#39;dislike&#39;) {
              item.dislikes = (item.dislikes || 0) + 1;
            }
          }
          return item;
        });
        this.setData({ postList: updatedList });
        wx.showToast({ title: &#39;操作成功&#39;, icon: &#39;success&#39; });
      }
    } catch (error) {
      wx.showToast({ title: &#39;操作失败&#39;, icon: &#39;none&#39; });
    }
  },

  /**
   * 跳转到发布页面
   */
  goToPublish() {
    wx.navigateTo({
      url: &#39;/pages/community/publish&#39;
    });
  }
})