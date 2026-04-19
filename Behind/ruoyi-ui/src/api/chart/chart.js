import request from '@/utils/request'

// 查询表单管理列表
export function listChart(query) {
  return request({
    url: '/chart/chart/list',
    method: 'get',
    params: query
  })
}

// 查询表单管理详细
export function getChart(chartId) {
  return request({
    url: '/chart/chart/' + chartId,
    method: 'get'
  })
}

// 新增表单管理
export function addChart(data) {
  return request({
    url: '/chart/chart',
    method: 'post',
    data: data
  })
}

// 修改表单管理
export function updateChart(data) {
  return request({
    url: '/chart/chart',
    method: 'put',
    data: data
  })
}

// 删除表单管理
export function delChart(chartId) {
  return request({
    url: '/chart/chart/' + chartId,
    method: 'delete'
  })
}
