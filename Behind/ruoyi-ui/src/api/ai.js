import request from '@/utils/request'

// 发送消息给AI
export function sendChatMessage(data) {
  return request({
    url: '/ai/chat',
    method: 'post',
    data: data
  })
}
