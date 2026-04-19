import request from './request'
// 基础配置
const BASE_URL = 'http://localhost:8080' // 开发用本地IP，比如 http://192.168.1.100:8080

// 封装请求
const request = (options) => {
  return new Promise((resolve, reject) => {
    wx.request({
      url: BASE_URL + options.url,
      method: options.method || 'GET',
      data: options.data || {},
      header: {
        'Content-Type': 'application/json',
        // 若依需要token，登录后获取token存在storage里
        'Authorization': 'Bearer ' + wx.getStorageSync('token') || ''
      },
      success: (res) => {
        if (res.data.code === 200) {
          resolve(res.data)
        } else {
          wx.showToast({ title: res.data.msg || '请求失败', icon: 'none' })
          reject(res.data)
        }
      },
      fail: (err) => {
        wx.showToast({ title: '网络错误', icon: 'none' })
        reject(err)
      }
    })
  })
}

module.exports = request