// js/request.js
import axios from 'axios';

// 创建 axios 实例
const request = axios.create({
  baseURL: 'http://your-backend-domain.com/api', // 替换为你的后端地址
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json'
  }
});

// 请求拦截器（可选）
request.interceptors.request.use(
  config => {
    // 可在此添加 token 等
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

// 响应拦截器（可选）
request.interceptors.response.use(
  response => {
    // 直接返回 response.data，简化调用
    return response.data;
  },
  error => {
    console.error('API Error:', error);
    return Promise.reject(error);
  }
);

export default request;