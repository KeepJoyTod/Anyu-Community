import config from './config'

export default function request(options) {
  return new Promise((resolve, reject) => {
    uni.request({
      url: config.resolveUrl(options.url),
      method: options.method || 'GET',
      data: options.data || {},
      header: {
        'Content-Type': 'application/json',
        Authorization: uni.getStorageSync(config.TOKEN_KEY)
          ? `Bearer ${uni.getStorageSync(config.TOKEN_KEY)}`
          : ''
      },
      success: (res) => {
        if (res.data.code === 200) {
          resolve(res.data)
        } else {
          uni.showToast({ title: res.data.msg || '请求失败', icon: 'none' })
          reject(res.data)
        }
      },
      fail: (err) => {
        uni.showToast({ title: '网络错误', icon: 'none' })
        reject(err)
      }
    })
  })
}
