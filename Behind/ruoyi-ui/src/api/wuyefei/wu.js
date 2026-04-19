import request from '@/utils/request'

// 查询物业费管理列表
export function listWu(query) {
  return request({
    url: '/wuyefei/wu/list',
    method: 'get',
    params: query
  })
}

// 查询物业费管理详细
export function getWu(propertyFeeId) {
  return request({
    url: '/wuyefei/wu/' + propertyFeeId,
    method: 'get'
  })
}

// 新增物业费管理
export function addWu(data) {
  return request({
    url: '/wuyefei/wu',
    method: 'post',
    data: data
  })
}

// 修改物业费管理
export function updateWu(data) {
  return request({
    url: '/wuyefei/wu',
    method: 'put',
    data: data
  })
}

// 删除物业费管理
export function delWu(propertyFeeId) {
  return request({
    url: '/wuyefei/wu/' + propertyFeeId,
    method: 'delete'
  })
}
