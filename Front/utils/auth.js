import config from '@/api/config.js'

export function getToken() {
  return uni.getStorageSync(config.TOKEN_KEY)
}

export function setToken(token) {
  return uni.setStorageSync(config.TOKEN_KEY, token)
}

export function removeToken() {
  return uni.removeStorageSync(config.TOKEN_KEY)
}
