import { request } from '@/utils/request'

export const listPackages = (page = 1, size = 20) => {
  return request({ url: `/station/packages?page=${page}&size=${size}` })
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

