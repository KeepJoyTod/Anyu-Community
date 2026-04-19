import request from '@/utils/request'

// 查询报修单据列表
export function listBaoxiudanju(query) {
  return request({
    url: '/baoxiudanju/baoxiudanju/list',
    method: 'get',
    params: query
  })
}

// 查询报修单据详细
export function getBaoxiudanju(orderId) {
  return request({
    url: '/baoxiudanju/baoxiudanju/' + orderId,
    method: 'get'
  })
}

// 新增报修单据
export function addBaoxiudanju(data) {
  return request({
    url: '/baoxiudanju/baoxiudanju',
    method: 'post',
    data: data
  })
}

// 修改报修单据
export function updateBaoxiudanju(data) {
  return request({
    url: '/baoxiudanju/baoxiudanju',
    method: 'put',
    data: data
  })
}

// 删除报修单据
export function delBaoxiudanju(orderId) {
  return request({
    url: '/baoxiudanju/baoxiudanju/' + orderId,
    method: 'delete'
  })
}
