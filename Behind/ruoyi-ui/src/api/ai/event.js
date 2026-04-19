import request from '@/utils/request'

// 查询AI社区事件列表
export function listAiCommunityEvent(query) {
  return request({
    url: '/system/ai-event/list',
    method: 'get',
    params: query
  })
}

// 修改AI事件状态
export function updateAiEventStatus(id, status) {
  return request({
    url: `/system/ai-event/status/${id}/${status}`,
    method: 'put'
  })
}
