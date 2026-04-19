// src/common/request.js
const baseURL = 'http://localhost:8080';

export default function request(options) {
  // 这里封装小程序的 wx.request
  let url = baseURL + options.url;
  
  // 处理GET请求的参数
  if (options.method === 'get' && options.data) {
    const params = Object.keys(options.data)
      .map(key => `${encodeURIComponent(key)}=${encodeURIComponent(options.data[key])}`)
      .join('&');
    if (params) {
      url += (url.includes('?') ? '&' : '?') + params;
    }
  }
  
  // 获取本地存储的 token
  const token = uni.getStorageSync('token')
  
  return new Promise((resolve, reject) => {
    wx.request({
      url: url,
      method: options.method || 'get',
      data: options.method === 'get' ? {} : (options.data || {}),
      header: {
        'content-type': 'application/json',
        // 如果有 token，则添加到请求头中
        ...(token && { 'Authorization': 'Bearer ' + token })
      },
      success: (res) => {
        // 如果返回 401，说明 token 过期或无效，跳转到登录页面
        if (res.statusCode === 401) {
          uni.removeStorageSync('token')
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
      fail: (err) => {
        // 处理网络错误
        if (err.statusCode === 401) {
          uni.removeStorageSync('token')
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
        reject(err)
      }
    })
  })
}