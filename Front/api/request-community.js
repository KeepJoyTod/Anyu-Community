import config from './config'

export default function request(options) {
  const method = (options.method || 'get').toLowerCase()
  let url = config.resolveUrl(options.url)

  if (method === 'get' && options.data) {
    const params = Object.keys(options.data)
      .map(key => `${encodeURIComponent(key)}=${encodeURIComponent(options.data[key])}`)
      .join('&')
    if (params) {
      url += (url.includes('?') ? '&' : '?') + params
    }
  }

  const token = uni.getStorageSync(config.TOKEN_KEY)

  return new Promise((resolve, reject) => {
    uni.request({
      url,
      method,
      data: method === 'get' ? {} : (options.data || {}),
      header: {
        'content-type': 'application/json',
        ...(token ? { Authorization: `Bearer ${token}` } : {}),
        ...options.header
      },
      success: (res) => {
        if (res.statusCode === 401) {
          uni.removeStorageSync(config.TOKEN_KEY)
          uni.removeStorageSync('userInfo')
          uni.showToast({
            title: '登录已过期，请重新登录',
            icon: 'none'
          })
          setTimeout(() => {
            uni.navigateTo({
              url: '/pages/login/login'
            })
          }, 1500)
        }
        resolve(res.data)
      },
      fail: reject
    })
  })
}
