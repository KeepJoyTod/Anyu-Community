// js/api.js
import request from './request';

/**
 * 获取剩余水费余额
 * @returns {Promise<{code: number, data: number, msg: string}>}
 */
export function getWaterBalance() {
  return request({
    url: '/water/balance',   // 你的后端接口地址
    method: 'get'
  });
}

/**
 * 获取剩余电费余额
 * @returns {Promise<{code: number, data: number, msg: string}>}
 */
export function getElectricityBalance() {
  return request({
    url: '/electricity/balance',
    method: 'get'
  });
}

/**
 * 获取包裹列表
 */
export function getPackages() {
  return request({
    url: '/packages/list',
    method: 'get'
  });
}

/**
 * 获取包裹详情及轨迹
 */
export function getPackageDetail(id) {
  return request({
    url: `/packages/detail/${id}`,
    method: 'get'
  });
}

/**
 * 确认取件
 */
export function pickupPackage(id) {
  return request({
    url: `/packages/pickup/${id}`,
    method: 'post'
  });
}