import request from '@/utils/request'

// 查询通知阅读记录列表
export function listNoticeRead(query) {
  return request({
    url: '/noticeRead/noticeRead/list',
    method: 'get',
    params: query
  })
}

// 查询通知阅读记录详细
export function getNoticeRead(id) {
  return request({
    url: '/noticeRead/noticeRead/' + id,
    method: 'get'
  })
}

// 新增通知阅读记录
export function addNoticeRead(data) {
  return request({
    url: '/noticeRead/noticeRead',
    method: 'post',
    data: data
  })
}

// 修改通知阅读记录
export function updateNoticeRead(data) {
  return request({
    url: '/noticeRead/noticeRead',
    method: 'put',
    data: data
  })
}

// 删除通知阅读记录
export function delNoticeRead(id) {
  return request({
    url: '/noticeRead/noticeRead/' + id,
    method: 'delete'
  })
}
