import request from '@/utils/request'

// 查询AI社区主动事件列表
export function listEvent(query) {
  return request({
    url: '/system/event/list',
    method: 'get',
    params: query
  })
}

// 查询AI社区主动事件详细
export function getEvent(id) {
  return request({
    url: '/system/event/' + id,
    method: 'get'
  })
}

// 新增AI社区主动事件
export function addEvent(data) {
  return request({
    url: '/system/event',
    method: 'post',
    data: data
  })
}

// 修改AI社区主动事件
export function updateEvent(data) {
  return request({
    url: '/system/event',
    method: 'put',
    data: data
  })
}

// 删除AI社区主动事件
export function delEvent(id) {
  return request({
    url: '/system/event/' + id,
    method: 'delete'
  })
}
