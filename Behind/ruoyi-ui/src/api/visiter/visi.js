import request from '@/utils/request'

// 查询小区访客管理列表
export function listVisi(query) {
  return request({
    url: '/visiter/visi/list',
    method: 'get',
    params: query
  })
}

// 查询小区访客管理详细
export function getVisi(id) {
  return request({
    url: '/visiter/visi/' + id,
    method: 'get'
  })
}

// 新增小区访客管理
export function addVisi(data) {
  return request({
    url: '/visiter/visi',
    method: 'post',
    data: data
  })
}

// 修改小区访客管理
export function updateVisi(data) {
  return request({
    url: '/visiter/visi',
    method: 'put',
    data: data
  })
}

// 删除小区访客管理
export function delVisi(id) {
  return request({
    url: '/visiter/visi/' + id,
    method: 'delete'
  })
}
