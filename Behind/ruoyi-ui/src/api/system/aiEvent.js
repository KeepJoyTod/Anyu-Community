import request from '@/utils/request'
export function listAiEvent(query) {
  return request({ url: '/system/ai-event/list', method: 'get', params: query })
}
export function updateAiEventStatus(id, status) {
  return request({ url: `/system/ai-event/status/${id}/${status}`, method: 'put' })
}
