import request from '@/utils/request'

// 查询小区投诉管理列表
export function listComplaint(query) {
  return request({
    url: '/complaint/complaint/list',
    method: 'get',
    params: query
  })
}

// 查询小区投诉管理详细
export function getComplaint(id) {
  return request({
    url: '/complaint/complaint/' + id,
    method: 'get'
  })
}

// 新增小区投诉管理
export function addComplaint(data) {
  return request({
    url: '/complaint/complaint',
    method: 'post',
    data: data
  })
}

// 修改小区投诉管理
export function updateComplaint(data) {
  return request({
    url: '/complaint/complaint',
    method: 'put',
    data: data
  })
}

// 删除小区投诉管理
export function delComplaint(id) {
  return request({
    url: '/complaint/complaint/' + id,
    method: 'delete'
  })
}

// ========== 我帮你新加的：获取投诉类型字典（连接数据库用）==========
export function getComplaintType() {
  return request({
    url: '/system/dict/data/type/community_complaint_type',
    method: 'get'
  })
}
