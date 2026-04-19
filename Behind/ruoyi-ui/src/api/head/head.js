import request from '@/utils/request'

// 查询物业信息列表
export function listHead(query) {
  return request({
    url: '/head/head/list',
    method: 'get',
    params: query
  })
}

// 查询物业信息详细
export function getHead(propertyId) {
  return request({
    url: '/head/head/' + propertyId,
    method: 'get'
  })
}

// 新增物业信息
export function addHead(data) {
  return request({
    url: '/head/head',
    method: 'post',
    data: data
  })
}

// 修改物业信息
export function updateHead(data) {
  return request({
    url: '/head/head',
    method: 'put',
    data: data
  })
}

// 删除物业信息
export function delHead(propertyId) {
  return request({
    url: '/head/head/' + propertyId,
    method: 'delete'
  })
}
