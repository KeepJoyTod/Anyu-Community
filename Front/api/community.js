import request from './request.js'

/**
 * 获取动态列表
 * @param {Object} params - 可选参数，如 { tag: '#好人好事', pageNum: 1, pageSize: 10 }
 */
export function getPostList(params) {
  return request({
    url: '/community/post/list', // 后端联表接口地址
    method: 'get',
    data: params
  })
}

/**
 * 发表新动态
 * @param {Object} data - 包含 content, tag 等字段
 */
export function addPost(data) {
  return request({
    url: '/community/post',
    method: 'post',
    data: data
  })
}

/**
 * 点赞/点踩
 * @param {Object} data - { postId: xxx, type: 'like' 或 'dislike' }
 */
export function likePost(data) {
  return request({
    url: '/community/post/like',
    method: 'put',
    data: data
  })
}

/**
 * 发表评论
 * @param {Object} data - { postId: xxx, content: '评论内容' }
 */
export function addComment(data) {
  return request({
    url: '/community/post/comment',
    method: 'post',
    data: data
  })
}

/**
 * 获取活动列表
 */
export function getActivityList() {
  return request({
    url: '/community/activity/list',
    method: 'get'
  })
}

/**
 * 获取活动详情
 * @param {Number} activityId - 活动ID
 */
export function getActivityDetail(activityId) {
  return request({
    url: `/community/activity/detail/${activityId}`,
    method: 'get'
  })
}

/**
 * 报名活动
 * @param {Number} activityId - 活动ID
 */
export function registerActivity(activityId) {
  return request({
    url: '/community/activity/register',
    method: 'post',
    data: { activityId }
  })
}

/**
 * 取消报名
 * @param {Number} activityId - 活动ID
 */
export function cancelRegister(activityId) {
  return request({
    url: '/community/activity/cancelRegister',
    method: 'post',
    data: { activityId }
  })
}
