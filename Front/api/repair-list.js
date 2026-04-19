const request = require('./request.js') 

Page({
  data: {
    currentTab: 1,
    pendingList: [],
    processingList: [],
    finishedList: []
  },

  onLoad() {
    this.getRepairList()
  },

  // 获取报修单据列表
  getRepairList() {
    request({
      url: '/repair/list', // 若依后端接口地址
      method: 'GET',
      data: { status: 'processing' } // 按状态筛选
    }).then(res => {
      this.setData({
        processingList: res.data
      })
    }).catch(err => {
      console.error('获取列表失败', err)
    })
  }
})