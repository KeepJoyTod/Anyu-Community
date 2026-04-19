import request from '@/utils/request'

// 查询小区缴费管理列表
export function listCos(query) {
  return request({
    url: '/cost/cos/list',
    method: 'get',
    params: query
  })
}

// 查询小区缴费管理详细
export function getCos(id) {
  return request({
    url: '/cost/cos/' + id,
    method: 'get'
  })
}

// 新增小区缴费管理
export function addCos(data) {
  return request({
    url: '/cost/cos',
    method: 'post',
    data: data
  })
}

// 修改小区缴费管理
export function updateCos(data) {
  return request({
    url: '/cost/cos',
    method: 'put',
    data: data
  })
}

// 删除小区缴费管理
export function delCos(id) {
  return request({
    url: '/cost/cos/' + id,
    method: 'delete'
  })
}
