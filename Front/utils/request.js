import {getToken} from './auth'
import config from '@/api/config.js'
// utils/request.js
const BASE_URL = config.BASE_URL

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
					uni.removeStorageSync(config.TOKEN_KEY);
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
