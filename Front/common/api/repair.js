import { request } from '@/utils/request'
export const createRepair = (data) => {
  return request({ url: `${REPAIR_BASE}/repairs`, method: 'POST', data })
}

export const uploadRepairImage = (filePath) => {
  return new Promise((resolve, reject) => {
    uni.uploadFile({
      url: `${REPAIR_BASE}/repairs/upload`,
      filePath,
      name: 'file',
      success: (res) => {
        if (res.statusCode === 200) {
          try {
            const data = JSON.parse(res.data)
            const url = data?.data?.url || data.url
            if (url) resolve(url)
            else reject(data)
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
