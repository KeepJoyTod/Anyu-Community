// api/water.js
import { request } from '@/utils/request'

export const getBalance = () => {
  return request({ url: '/water/balance' })
}

export const createRechargeOrder = (amount, payMethod) => {
  return request({
    url: '/water/recharge',
    method: 'POST',
    data: { amount, payMethod }
  })
}