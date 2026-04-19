import request from '@/utils/request'

// 查询小区外卖订单列表
export function listTakeout(query) {
  return request({
    url: '/takeout/takeout/list',
    method: 'get',
    params: query
  })
}

// 查询小区外卖订单详细
export function getTakeout(id) {
  return request({
    url: '/takeout/takeout/' + id,
    method: 'get'
  })
}

// 新增小区外卖订单
export function addTakeout(data) {
  return request({
    url: '/takeout/takeout',
    method: 'post',
    data: data
  })
}

// 修改小区外卖订单
export function updateTakeout(data) {
  return request({
    url: '/takeout/takeout',
    method: 'put',
    data: data
  })
}

// 删除小区外卖订单
export function delTakeout(id) {
  return request({
    url: '/takeout/takeout/' + id,
    method: 'delete'
  })
}
