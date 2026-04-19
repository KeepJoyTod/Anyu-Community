/**
 * 电费服务API模块
 * @description 提供电费相关的接口服务，包括余额查询和充值功能
 * @module electricity
 */
import { request } from '@/utils/request.js'

/**
 * 获取电费余额
 * @function getElectricityBalance
 * @returns {Promise<Object>} 包含余额信息的对象
 * @description 调用后端接口获取用户的电费余额信息
 * @example
 * // 成功响应示例
 * {
 *   code: 200,
 *   data: {
 *     balance: 100.50,
 *     electricityAmount: 201,
 *     surplusElectricity: 50.5
 *   }
 * }
 */
export const getElectricityBalance = () => {
  return request({
    url: '/electricity/balance',
    method: 'GET'
  })
}

/**
 * 创建电费充值订单
 * @function createElectricityOrder
 * @param {number} amount - 充值金额
 * @param {string} payMethod - 支付方式 (wechat/alipay)
 * @returns {Promise<Object>} 充值订单信息
 * @description 调用后端接口创建电费充值订单
 * @example
 * // 成功响应示例
 * {
 *   code: 200,
 *   message: '充值成功'
 * }
 */
export const createElectricityOrder = (amount, payMethod) => {
  return request({
    url: '/electricity/recharge',
    method: 'POST',
    data: { amount, payMethod }
  })
}

/**
 * 获取电费账单列表
 * @function getElectricityBillList
 * @returns {Promise<Object>} 电费账单列表
 * @description 调用后端接口获取用户的电费账单列表
 * @example
 * // 成功响应示例
 * {
 *   code: 200,
 *   data: [
 *     {
 *       electricityId: 1,
 *       userId: 123,
 *       userName: '张三',
 *       userRoom: '1-101',
 *       readMonth: '2023-03',
 *       lastReading: 100,
 *       currentReading: 150,
 *       usedAmount: 50,
 *       unitPrice: 0.5,
 *       totalFee: 25,
 *       balance: 100,
 *       feeStatus: 'paid',
 *       dueDate: '2023-04-10',
 *       paymentTime: '2023-04-05'
 *     }
 *   ]
 * }
 */
export const getElectricityBillList = () => {
  return request({
    url: '/electricity/bill/list',
    method: 'GET'
  })
}

/**
 * 获取电费账单详情
 * @function getElectricityBillDetail
 * @param {number} electricityId - 电费ID
 * @returns {Promise<Object>} 电费账单详情
 * @description 调用后端接口获取用户的电费账单详情
 * @example
 * // 成功响应示例
 * {
 *   code: 200,
 *   data: {
 *     electricityId: 1,
 *     userId: 123,
 *     userName: '张三',
 *     userRoom: '1-101',
 *     readMonth: '2023-03',
 *     lastReading: 100,
 *     currentReading: 150,
 *     usedAmount: 50,
 *     unitPrice: 0.5,
 *     totalFee: 25,
 *     balance: 100,
 *     feeStatus: 'paid',
 *     dueDate: '2023-04-10',
 *     paymentTime: '2023-04-05',
 *     paymentNo: 'P20230405001',
 *     overdueAmount: 0,
 *     reader: '李四',
 *     readTime: '2023-04-01',
 *     remark: '正常缴费'
 *   }
 * }
 */
export const getElectricityBillDetail = (electricityId) => {
  return request({
    url: `/electricity/bill/detail/${electricityId}`,
    method: 'GET'
  })
}