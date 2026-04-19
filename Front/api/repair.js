import { request } from '@/utils/request'

const REPAIR_BASE = '/repairs'

/**
 * 创建维修申请
 * @function createRepair
 * @param {Object} data - 维修申请数据
 * @param {string} data.repairType - 维修类型
 * @param {string} data.repairDesc - 问题描述
 * @param {string} data.userName - 联系人
 * @param {string} data.userPhone - 联系电话
 * @param {string} data.repairAddress - 详细地址
 * @param {string} data.repairImages - 上传的图片URL数组（JSON字符串）
 * @param {string} data.orderStatus - 订单状态
 * @param {string} data.createTime - 创建时间
 * @param {string} data.updateTime - 更新时间
 * @returns {Promise<Object>} 维修申请结果
 * @description 调用后端接口创建新的维修申请
 * @example
 * // 成功响应示例
 * {
 *   code: 200,
 *   data: {
 *     orderId: 1,
 *     repairType: 'circuit',
 *     repairDesc: '电路故障',
 *     userName: '张三',
 *     userPhone: '13800138000',
 *     repairAddress: 'XX小区3栋2单元502室',
 *     repairImages: '["https://example.com/image1.jpg"]',
 *     orderStatus: 'pending',
 *     createTime: '2026-03-30 10:00:00',
 *     updateTime: '2026-03-30 10:00:00'
 *   }
 * }
 */
export const createRepair = (data) => {
  return request({
    url: REPAIR_BASE,
    method: 'POST',
    data
  })
}

/**
 * 上传维修图片
 * @function uploadRepairImage
 * @param {string} filePath - 本地图片文件路径
 * @returns {Promise<string>} 上传后的图片URL
 * @description 调用后端接口上传维修相关的图片
 * @example
 * // 成功响应示例
 * 'https://example.com/uploaded-image.jpg'
 */
export const uploadRepairImage = (filePath) => {
  return new Promise((resolve, reject) => {
    uni.uploadFile({
      url: `${REPAIR_BASE}/upload`,
      filePath,
      name: 'file',
      success: (res) => {
        if (res.statusCode === 200) {
          try {
            const data = JSON.parse(res.data)
            if (data && (data.code === 200 || data.code === 0)) {
              const url = data?.data?.url || data.url
              if (url) resolve(url)
              else reject(data)
            } else if (data !== undefined) {
              const url = data?.data?.url || data.url
              if (url) resolve(url)
              else reject(data)
            } else {
              reject(res)
            }
          } catch (e) {
            reject(e)
          }
        } else {
          reject(res)
        }
      },
      fail: reject
    })
  })
}

/**
 * 获取待处理的维修申请列表
 * @function getPendingRepairs
 * @returns {Promise<Array>} 待处理的维修申请列表
 * @description 调用后端接口获取当前用户的待处理维修申请
 * @example
 * // 成功响应示例
 * {
 *   code: 200,
 *   data: [
 *     {
 *       orderId: 1,
 *       type: 'circuit',
 *       desc: '电路故障',
 *       contactName: '张三',
 *       phone: '13800138000',
 *       address: 'XX小区3栋2单元502室',
 *       images: ['https://example.com/image1.jpg'],
 *       status: 'pending',
 *       createdAt: '2026-03-30 10:00:00'
 *     }
 *   ]
 * }
 */
export const getPendingRepairs = () => {
  return request({
    url: `${REPAIR_BASE}/pending`,
    method: 'GET'
  })
}
