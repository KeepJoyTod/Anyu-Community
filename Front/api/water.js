// api/water.js
import { request } from '@/utils/request'

/**
 * 获取水费余额
 * @returns {Promise<Object>} 包含余额信息的对象
 * @description 调用后端接口获取用户的水费余额信息
 */
export const getWaterBalance = () => {
  return request({ url: '/water/balance' })
}

/**
 * 创建水费充值订单
 * @param {number} amount - 充值金额
 * @param {string} payMethod - 支付方式 (wechat/alipay)
 * @returns {Promise<Object>} 充值订单信息
 * @description 调用后端接口创建水费充值订单
 */
export const createWaterRechargeOrder = (amount, payMethod) => {
  return request({
    url: '/water/recharge',
    method: 'POST',
    data: { amount, payMethod }
  })
}