import { request } from '@/utils/request.js'

export const listVenues = () => {
  return request({ url: '/venue/list' })
}

export const createBooking = (data) => {
  return request({ url: '/booking', method: 'POST', data })
}
