// API 配置和调用函数

// 后端基础地址
const BASE_URL = 'http://127.0.0.1:8080';

/**
 * 网络请求封装
 * @param {Object} options - 请求配置
 */
function request(options) {
  // 获取token
  const token = uni.getStorageSync('community_token') || '';
  
  return new Promise((resolve, reject) => {
    uni.request({
      url: BASE_URL + options.url,
      method: options.method || 'GET',
      data: options.data || {},
      header: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + token,
        ...options.header
      },
      success: (res) => {
        if (res.statusCode === 200) {
          resolve(res.data);
        } else {
          reject(res.data);
        }
      },
      fail: (err) => {
        reject(err);
      }
    });
  });
}

// 登录相关API
export const loginApi = {
  /**
   * 系统登录
   * @param {Object} data - { username, password, code, uuid }
   */
  login: (data) => request({
    url: '/login',
    method: 'POST',
    data
  }),
  
  /**
   * 获取用户信息
   */
  getInfo: () => request({
    url: '/getInfo',
    method: 'GET'
  }),
  
  /**
   * 获取验证码
   */
  getCaptcha: () => request({
    url: '/captchaImage',
    method: 'GET'
  })
};

// 社区相关API
export const communityApi = {
  /**
   * 获取动态列表
   * @param {Object} params - 可选参数，如 { pageNum: 1, pageSize: 10 }
   */
  getPostList: (params) => request({
    url: '/community/post/list',
    method: 'GET',
    data: params
  }),

  /**
   * 发表新动态
   * @param {Object} data - 包含 content, tag 等字段
   */
  addPost: (data) => request({
    url: '/community/post',
    method: 'POST',
    data
  }),

  /**
   * 点赞/点踩
   * @param {Object} data - { postId: xxx, type: 'like' 或 'dislike' }
   */
  likePost: (data) => request({
    url: '/community/post/like',
    method: 'PUT',
    data
  }),

  /**
   * 发表评论
   * @param {Object} data - { postId: xxx, content: '评论内容' }
   */
  addComment: (data) => request({
    url: '/community/post/comment',
    method: 'POST',
    data
  })
};

// 积分相关API
export const pointsApi = {
  /**
   * 获取用户积分信息
   */
  getPoints: () => request({
    url: '/system/points/getPoints',
    method: 'GET'
  }),

  /**
   * 增加积分
   * @param {Number} userId - 用户ID
   * @param {Object} data - { points: 积分数量 }
   */
  addPoints: (userId, data) => request({
    url: `/home/points/addPoints/${userId}`,
    method: 'POST',
    data
  }),

  /**
   * 扣除积分
   * @param {Number} userId - 用户ID
   * @param {Object} data - { points: 积分数量, remark: 备注 }
   */
  deductPoints: (userId, data) => request({
    url: `/home/points/deductPoints/${userId}`,
    method: 'POST',
    data
  })
};

// 积分商城相关API
export const mallApi = {
  /**
   * 获取用户可兑换的账单列表
   * @param {Number} userId - 用户ID
   */
  getBills: (userId) => request({
    url: `/home/mall/getBills/${userId}`,
    method: 'GET'
  }),

  /**
   * 积分兑换
   * @param {Number} userId - 用户ID
   * @param {Object} data - { billType: 账单类型, billId: 账单ID, pointsUsed: 使用积分 }
   */
  exchange: (userId, data) => request({
    url: `/home/mall/exchange/${userId}`,
    method: 'POST',
    data
  }),
  
  /**
   * 积分兑换（不需要userId）
   * @param {Object} data - { billType: 账单类型, billId: 账单ID, pointsUsed: 使用积分, amount: 金额 }
   */
  exchangeNoUserId: (data) => request({
    url: '/system/points/exchange',
    method: 'POST',
    data
  }),

  /**
   * 获取用户的积分兑换记录
   * @param {Number} userId - 用户ID
   */
  getExchangeRecords: (userId) => request({
    url: `/home/mall/exchangeRecords/${userId}`,
    method: 'GET'
  }),
  
  /**
   * 获取用户的积分兑换记录（不需要userId）
   */
  getExchangeRecordsNoUserId: () => request({
    url: '/system/points/exchangeRecords',
    method: 'GET'
  })
};
