// 使用本机局域网IP，确保手机和电脑在同一WiFi下可以访问
// 如果后端部署到服务器，请修改为服务器域名
// const BASE_URL = 'http://192.168.86.1:8080'

// const TIMEOUT = 10000

// const TOKEN_KEY = 'community_token'

// export default {
// 	BASE_URL,
// 	TIMEOUT,
// 	TOKEN_K
// 	}

// 修正后的完整 config.js
// 使用本机局域网IP，确保手机和电脑在同一WiFi下可以访问
// 如果后端部署到服务器，请修改为服务器域名
const BASE_URL = 'http://192.168.86.1:8080'
const TIMEOUT = 10000
const TOKEN_KEY = 'community_token'

// API 配置文件
const config = {
	// 后端基础地址
	BASE_URL: 'http://127.0.0.1:8080',
	
	// 登录接口
	LOGIN_URL: '/login',
	
	// 社区相关接口
	COMMUNITY: {
		POST_LIST: '/community/post/list',
		POST_DETAIL: '/community/post',
		ADD_COMMENT: '/community/post/comment',
		LIKE_POST: '/community/post/like'
	}
}

export default {
  BASE_URL,
  TIMEOUT,
  TOKEN_KEY,
  ...config
}