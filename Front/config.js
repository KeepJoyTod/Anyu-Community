// config.js
const isDev = process.env.NODE_ENV === 'development' // uni-app 中可通过 process.env.NODE_ENV 判断
export const API_BASE_URL = isDev ? 'https://dev-api.example.com' : 'https://api.example.com'