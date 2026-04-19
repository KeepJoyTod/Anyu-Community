import config from './config'

function getToken() {
	return uni.getStorageSync(config.TOKEN_KEY) || ''
}

function setToken(token) {
	uni.setStorageSync(config.TOKEN_KEY, token)
}

function removeToken() {
	uni.removeStorageSync(config.TOKEN_KEY)
}

function request(options) {
	return new Promise((resolve, reject) => {
		const token = getToken()
		
		uni.request({
			url: config.BASE_URL + options.url,
			method: options.method || 'GET',
			data: options.data || {},
			timeout: options.timeout != null ? options.timeout : config.TIMEOUT,
			header: {
				'Content-Type': 'application/json',
				'Authorization': token ? `Bearer ${token}` : '',
				...options.header
			},
			success: (res) => {
				console.log('请求响应:', res)
				if (res.statusCode === 200) {
					if (res.data.code === 0 || res.data.code === 200 || res.data.code === undefined) {
						resolve(res.data)
					} else if (res.data.code === 401) {
						removeToken()
						uni.showToast({
							title: '登录已过期，请重新登录',
							icon: 'none'
						})
						setTimeout(() => {
							uni.reLaunch({
								url: '/pages/login/login'
							})
						}, 1500)
						reject(res.data)
					} else {
						uni.showToast({
							title: res.data.msg || res.data.message || '请求失败',
							icon: 'none'
						})
						reject(res.data)
					}
				} else {
					uni.showToast({
						title: '网络请求失败',
						icon: 'none'
					})
					reject(new Error('网络请求失败'))
				}
			},
			fail: (err) => {
				console.error('请求失败:', err)
				uni.showToast({
					title: '网络连接失败',
					icon: 'none'
				})
				reject(err)
			}
		})
	})
}

function get(url, data) {
	return request({
		url,
		method: 'GET',
		data
	})
}

function post(url, data, extra = {}) {
	return request({
		url,
		method: 'POST',
		data,
		...extra
	})
}

function put(url, data) {
	return request({
		url,
		method: 'PUT',
		data
	})
}

function del(url, data) {
	return request({
		url,
		method: 'DELETE',
		data
	})
}

export default {
	request,
	get,
	post,
	put,
	del,
	getToken,
	setToken,
	removeToken
}
