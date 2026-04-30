const DEFAULT_BASE_URL = 'http://127.0.0.1:8080'
const DEFAULT_TIMEOUT = 10000
const TOKEN_KEY = 'community_token'

function normalizeBaseUrl(url) {
  if (!url) {
    return DEFAULT_BASE_URL
  }
  return String(url).replace(/\/+$/, '')
}

function readRuntimeBaseUrl() {
  try {
    if (typeof uni !== 'undefined' && uni.getStorageSync) {
      return uni.getStorageSync('api_base_url')
    }
  } catch (e) {
    console.warn('readRuntimeBaseUrl failed', e)
  }
  return ''
}

function readEnvBaseUrl() {
  if (typeof process !== 'undefined' && process.env) {
    return process.env.VUE_APP_BASE_API_URL || process.env.VUE_APP_BASE_API || ''
  }
  return ''
}

const BASE_URL = normalizeBaseUrl(readRuntimeBaseUrl() || readEnvBaseUrl() || DEFAULT_BASE_URL)

const config = {
  BASE_URL,
  TIMEOUT: DEFAULT_TIMEOUT,
  TOKEN_KEY,
  LOGIN_URL: '/login',
  COMMUNITY: {
    POST_LIST: '/community/post/list',
    POST_DETAIL: '/community/post',
    ADD_COMMENT: '/community/post/comment',
    LIKE_POST: '/community/post/like'
  },
  resolveUrl(path = '') {
    if (!path) {
      return BASE_URL
    }
    if (/^https?:\/\//i.test(path)) {
      return path
    }
    const normalizedPath = path.startsWith('/') ? path : `/${path}`
    return `${BASE_URL}${normalizedPath}`
  }
}

export default config
