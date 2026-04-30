import request from '@/utils/request'

export function listDianfei(query) {
  return request({
    url: '/dianfeiguanli/dianfeiguanli/list',
    method: 'get',
    params: query
  })
}

export function getDianfei(electricityId) {
  return request({
    url: '/dianfeiguanli/dianfeiguanli/' + electricityId,
    method: 'get'
  })
}

export function addDianfei(data) {
  return request({
    url: '/dianfeiguanli/dianfeiguanli',
    method: 'post',
    data
  })
}

export function updateDianfei(data) {
  return request({
    url: '/dianfeiguanli/dianfeiguanli',
    method: 'put',
    data
  })
}

export function delDianfei(electricityId) {
  return request({
    url: '/dianfeiguanli/dianfeiguanli/' + electricityId,
    method: 'delete'
  })
}
