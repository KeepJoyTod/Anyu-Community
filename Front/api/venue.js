/**
 * 场地预约API模块
 * @description 提供场地相关的接口服务，包括场地列表查询和预约创建功能
 * @module venue
 */
import { request } from '@/utils/request.js'

/**
 * 获取场地列表
 * @function listVenues
 * @returns {Promise<Object>} 包含场地列表的对象
 * @description 调用后端接口获取场地列表信息
 * @example
 * // 成功响应示例
 * {
 *   code: 200,
 *   data: [
 *     { id: 1, name: '麻将馆', icon: '🀄', desc: '自动麻将桌，环境舒适', price: 50 },
 *     { id: 2, name: '社区广场', icon: '🏞️', desc: '开阔场地，适合广场舞', price: 30 }
 *   ]
 * }
 */
export const listVenues = () => {
  return request({
    url: '/venue/list',
    method: 'GET'
  })
}

/**
 * 创建场地预约
 * @function createBooking
 * @param {Object} data - 预约信息
 * @param {string} data.spaceName - 场地名称
 * @param {number} data.userId - 预约人ID
 * @param {string} data.userName - 预约人姓名
 * @param {string} data.userPhone - 预约人电话
 * @param {string} data.reserveDate - 预约日期
 * @param {string} data.startTime - 使用开始时间
 * @param {string} data.endTime - 使用结束时间
 * @param {string} data.reservationPurpose - 预约用途
 * @param {string} data.remark - 备注
 * @returns {Promise<Object>} 预约结果
 * @description 调用后端接口创建场地预约
 * @example
 * // 成功响应示例
 * {
 *   code: 200,
 *   message: '预约提交成功，等待审核'
 * }
 */
export const createBooking = (data) => {
  return request({
    url: '/venue/booking',
    method: 'POST',
    data
  })
}

/**
 * 获取场地预约列表
 * @function getVenueBookings
 * @returns {Promise<Object>} 包含预约列表的对象
 * @description 调用后端接口获取用户的场地预约列表
 * @example
 * // 成功响应示例
 * {
 *   code: 200,
 *   data: [
 *     {
 *       id: 1,
 *       spaceName: '麻将馆',
 *       reserveDate: '2026-04-01',
 *       startTime: '14:00',
 *       endTime: '16:00',
 *       status: '待使用'
 *     }
 *   ]
 * }
 */
export const getVenueBookings = () => {
  return request({
    url: '/venue/bookings',
    method: 'GET'
  })
}

/**
 * 获取场地预约详情
 * @function getVenueBookingDetail
 * @param {number} id - 预约ID
 * @returns {Promise<Object>} 预约详情对象
 * @description 调用后端接口获取单个场地预约的详细信息
 * @example
 * // 成功响应示例
 * {
 *   code: 200,
 *   data: {
 *     id: 1,
 *     spaceName: '麻将馆',
 *     userId: 1,
 *     userName: '测试用户',
 *     userPhone: '13800138000',
 *     reserveDate: '2026-04-01',
 *     startTime: '14:00',
 *     endTime: '16:00',
 *     reservationPurpose: '朋友聚会',
 *     remark: '需要麻将桌',
 *     status: '待使用',
 *     createTime: '2026-03-30 10:00:00'
 *   }
 * }
 */
export const getVenueBookingDetail = (id) => {
  return request({
    url: `/venue/booking/${id}`,
    method: 'GET'
  })
}
