import request from '@/utils/request'

export function listShuifei(query) {
  return request({
    url: '/shuifei/shuifei/list',
    method: 'get',
    params: query
  })
}

export function getShuifei(waterId) {
  return request({
    url: '/shuifei/shuifei/' + waterId,
    method: 'get'
  })
}

export function addShuifei(data) {
  return request({
    url: '/shuifei/shuifei',
    method: 'post',
    data
  })
}

export function updateShuifei(data) {
  return request({
    url: '/shuifei/shuifei',
    method: 'put',
    data
  })
}

export function delShuifei(waterId) {
  return request({
    url: '/shuifei/shuifei/' + waterId,
    method: 'delete'
  })
}
