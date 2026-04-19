import request from '@/utils/request'

// 查询小区驿站服务列表
export function listStation(query) {
  return request({
    url: '/station/station/list',
    method: 'get',
    params: query
  })
}

// 查询小区驿站服务详细
export function getStation(id) {
  return request({
    url: '/station/station/' + id,
    method: 'get'
  })
}

// 新增小区驿站服务
export function addStation(data) {
  return request({
    url: '/station/station',
    method: 'post',
    data: data
  })
}

// 修改小区驿站服务
export function updateStation(data) {
  return request({
    url: '/station/station',
    method: 'put',
    data: data
  })
}

// 删除小区驿站服务
export function delStation(id) {
  return request({
    url: '/station/station/' + id,
    method: 'delete'
  })
}
