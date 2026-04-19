import request from '@/utils/request'

// 查询小区通知管理列表
export function listNotice(query) {
  return request({
    url: '/notice/notice/list',
    method: 'get',
    params: query
  })
}

// 查询小区通知管理详细
export function getNotice(id) {
  return request({
    url: '/notice/notice/' + id,
    method: 'get'
  })
}

// 新增小区通知管理
export function addNotice(data) {
  return request({
    url: '/notice/notice',
    method: 'post',
    data: data
  })
}

// 修改小区通知管理
export function updateNotice(data) {
  return request({
    url: '/notice/notice',
    method: 'put',
    data: data
  })
}

// 删除小区通知管理
export function delNotice(id) {
  return request({
    url: '/notice/notice/' + id,
    method: 'delete'
  })
}
