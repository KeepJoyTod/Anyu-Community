import { request } from '@/utils/request'

export const listNotices = (page = 1, size = 100) => {
  return request({
    url: '/notice',
    method: 'GET',
    data: {
      page,
      size
    }
  })
}

export const getNoticeDetail = (id) => {
  return request({
    url: `/notice/${id}`,
    method: 'GET'
  })
}

export const markNoticeRead = (id) => {
  return request({
    url: `/notice/${id}/read`,
    method: 'PUT'
  })
}

export const markAllNoticesRead = () => {
  return request({
    url: '/notice/read-all',
    method: 'PUT'
  })
}