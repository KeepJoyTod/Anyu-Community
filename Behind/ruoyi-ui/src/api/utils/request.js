import request from '@/utils/request'

// 查询小区预约管理列表
export function listAppointments(query) {
  return request({
    url: '/appointments/appointments/list',
    method: 'get',
    params: query
  })
}

// 查询小区预约管理详细
export function getAppointments(id) {
  return request({
    url: '/appointments/appointments/' + id,
    method: 'get'
  })
}

// 新增小区预约管理
export function addAppointments(data) {
  return request({
    url: '/appointments/appointments',
    method: 'post',
    data: data
  })
}

// 修改小区预约管理
export function updateAppointments(data) {
  return request({
    url: '/appointments/appointments',
    method: 'put',
    data: data
  })
}

// 删除小区预约管理
export function delAppointments(id) {
  return request({
    url: '/appointments/appointments/' + id,
    method: 'delete'
  })
}
