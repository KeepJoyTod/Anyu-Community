import request from './request'

// 获取用户信息
export function getOwnerInfo() {
  return request({
    url: '/owner/user/getInfo',
    method: 'get'
  })
}

// 修改头像
export function updateAvatar(file) {
  const formData = new FormData()
  formData.append('avatarFile', file)
  return request({
    url: '/owner/user/avatar',
    method: 'post',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  })
}