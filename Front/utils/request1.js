import {getToken} from './auth'
import axios from 'axios'
import config from '@/api/config.js'
// utils/request.js
const BASE_URL = 'http://localhost:8080'

export const request = (options) => {
	return new Promise((resolve, reject) => {
		const token = uni.getStorageSync(config.TOKEN_KEY);
		const isAbsolute = /^https?:\/\//i.test(options.url)
		const finalUrl = isAbsolute ? options.url : `${BASE_URL}${options.url}`
		uni.request({
			url: finalUrl,
			method: options.method || 'GET',
			data: options.data,
			header: {
				'Authorization': `Bearer ${token}`,
				'Content-Type': 'application/json',
				...options.header
			},
			success: (res) => {
				if (res.statusCode === 200) {
					const data = res.data;
					if (data && (data.code === 200 || data.code === 0)) {
						// 统一返回后端标准响应包 { code, msg, data }
						resolve(data);
					} else if (data !== undefined) {
						// 某些旧接口可能没有 code 字段，直接返回 data
						resolve(data);
					} else {
						uni.showToast({
							title: '请求失败',
							icon: 'none'
						});
						reject(res);
					}
				} else if (res.statusCode === 401) {
					uni.removeStorageSync('token');
					uni.reLaunch({
						url: '/pages/login/login'
					});
					reject(new Error('未登录'));
				} else {
					const message = (res.data && (res.data.message || res.data.msg)) || '请求失败';
					uni.showToast({
						title: message,
						icon: 'none'
					});
					reject(res.data || res);
				}
			},
			fail: (err) => {
				uni.showToast({
					title: '网络异常，请稍后重试',
					icon: 'none'
				});
				reject(err);
			}
		});
	});
};

// 创建axios实例
const service = axios.create({
  // 超时
  timeout: 10000
})

// request拦截器
service.interceptors.request.use(config => {
	// 是否需要设置 token
	const isToken = (config.headers || {}).isToken === false
	// 是否需要防止数据重复提交
	const isRepeatSubmit = (config.headers || {}).repeatSubmit === false
	// 间隔时间(ms)，小于此时间视为重复提交
	const interval = (config.headers || {}).interval || 1000
	if (getToken() && !isToken) {
		config.headers['Authorization'] = 'Bearer ' + getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
		console.log("token拦截: " + getToken())
	}
	// get请求映射params参数
	if (config.method === 'get' && config.params) {
		let url = config.url + '?' + tansParams(config.params)
		url = url.slice(0, -1)
		config.params = {}
		config.url = url
	}
	if (!isRepeatSubmit && (config.method === 'post' || config.method === 'put')) {
		const requestObj = {
			url: config.url,
			data: typeof config.data === 'object' ? JSON.stringify(config.data) : config.data,
			time: new Date().getTime()
		}
		const requestSize = Object.keys(JSON.stringify(requestObj)).length // 请求数据大小
		const limitSize = 5 * 1024 * 1024 // 限制存放数据5M
		if (requestSize >= limitSize) {
			console.warn(`[${config.url}]: ` + '请求数据大小超出允许的5M限制，无法进行防重复提交验证。')
			return config
		}
		const sessionObj = cache.session.getJSON('sessionObj')
		if (sessionObj === undefined || sessionObj === null || sessionObj === '') {
			cache.session.setJSON('sessionObj', requestObj)
		} else {
			const s_url = sessionObj.url // 请求地址
			const s_data = sessionObj.data // 请求数据
			const s_time = sessionObj.time // 请求时间
			if (s_data === requestObj.data && requestObj.time - s_time < interval && s_url === requestObj.url) {
				const message = '数据正在处理，请勿重复提交'
				console.warn(`[${s_url}]: ` + message)
				return Promise.reject(new Error(message))
			} else {
				cache.session.setJSON('sessionObj', requestObj)
			}
		}
	}
	return config
}, error => {
	console.log(error)
	Promise.reject(error)
})