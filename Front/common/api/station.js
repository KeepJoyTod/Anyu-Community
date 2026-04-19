 import { request } from '@/utils/request'
export const listPackages = (page = 1, size = 20) => {
  return request({ url: `/station/packages`, data: { page, size } })
}
export const getPackageDetail = (id) => {
  return request({ url: `/station/packages/${id}` })
}
export const getPackageTracks = (id) => {
  return request({ url: `/station/packages/${id}/tracks` })
}
export const pickupPackage = (id) => {
  return request({ url: `/station/packages/${id}/pickup`, method: 'PUT' })
}

// 获取包裹列表

// export function getPackages() {
//   return request({
//     url: '/packages/list',
//     method: 'get'
//   });
// }

// /**
//  * 获取包裹详情及轨迹
//  */
// export function getPackageDetail(id) {
//   return request({
//     url: `/packages/detail/${id}`,
//     method: 'get'
//   });
// }

// /**
//  * 确认取件
//  */
// export function pickupPackage(id) {
//   return request({
//     url: `/packages/pickup/${id}`,
//     method: 'post'
//   });
// }