import { request } from '@/utils/request'

export const sendVisitorInvite = (data) => {
  return request({ url: '/visitor/invite', method: 'POST', data })
}

export const getRecentInvites = (limit = 3) => {
  return request({ url: `/visitor/recent?limit=${limit}` })
}

// 新增：访客签入接口
export const signInVisitor = (id) => {
  return request({ 
    url: `/visitor/signin/${id}`, 
    method: 'PUT' 
  })
}