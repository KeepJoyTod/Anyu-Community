import request from '@/utils/request' // 对应你项目里的request工具
import { parseStrEmpty } from "@/utils/ruoyi" // 若依框架里的工具函数
//查询面板数据（用户、通知、缴费、预约）
export function getPanelData() {
  return request({
    url: '/api/home/getPanelData', // 你的接口地址
    method: 'get'
  })
}
// 获取面板统计数据+折线图newVisitis数据（合并接口，也可拆分，此处贴合原代码逻辑）
export function getLinelData() {
  return request({
    url: '/api/home/getLinelData',
    method: 'get'
  })
}
// 查询社区住户信息列表
export function listUser(query) {
  return request({
    url: '/api/home/list',
    method: 'get',
    params: query
  })
}

// 查询社区住户信息详细
export function getUser(id) {
  return request({
    url: '/api/home/user/' + id,
    method: 'get'
  })
}

// 新增社区住户信息
export function addUser(data) {
  return request({
    url: '/api/home/user',
    method: 'post',
    data: data
  })
}

// 修改社区住户信息
export function updateUser(data) {
  return request({
    url: '/api/home/user',
    method: 'put',
    data: data
  })
}

// 删除社区住户信息
export function delUser(id) {
  return request({
    url: '/api/home/user/' + id,
    method: 'delete'
  })
}
