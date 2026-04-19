import request from './request'

// 提交投诉
export function submitComplaint(data) {
  return request.post('/feedback/complaint', data)
}

// 提交建议
export function submitSuggestion(data) {
  return request.post('/feedback/suggestion', data)
}

// 获取反馈列表
export function getFeedbackList() {
  return request.get('/feedback/list')
}

// 删除反馈
export function deleteFeedback(id) {
  return request.del(`/feedback/${id}`)
}
