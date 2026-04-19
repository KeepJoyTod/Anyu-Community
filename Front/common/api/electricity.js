// api/electricity.js
import { request } from '@/utils/request.js'

// 获取电费余额
export const getElectricityBalance = () => {
  return request({ url: '/electricity/balance' })
}

// 创建电费充值订单
export const createElectricityOrder = (amount, payMethod) => {
  return request({
    url: '/electricity/recharge',
    method: 'POST',
    data: { amount, payMethod }
  })
}