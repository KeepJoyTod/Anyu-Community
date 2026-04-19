import request from './request'

function getUserInfo() {
	return request.get('/home/user-info')
}

function getFocus() {
	return request.get('/home/focus')
}

function getNotice() {
	return request.get('/home/notice')
}

function getNoticeList(params) {
	return request.get('/home/notice/v2/list', params)
}

function getNoticeDetail(id) {
	return request.get(`/home/notice/${id}`)
}

function getFunctionList() {
	return request.get('/home/function-list')
}

function getCommunityLife() {
	return request.get('/home/community-life')
}

function getHomeData() {
	return request.get('/home/data')
}

function openDoor() {
	return request.post('/home/open-door')
}

function getVisitorList(params) {
	return request.get('/home/visitor/list', params)
}

function createVisitor(data) {
	return request.post('/home/visitor/create', data)
}

function deleteVisitor(id) {
	return request.del(`/home/visitor/${id}`)
}

function getRepairList(params) {
	return request.get('/home/repair/list', params)
}

function createRepair(data) {
	return request.post('/home/repair/create', data)
}

function getRepairDetail(id) {
	return request.get(`/home/repair/${id}`)
}

function cancelRepair(id) {
	return request.put(`/home/repair/cancel/${id}`)
}

function getBillList(params) {
	return request.get('/home/bill/list', params)
}

function getBillDetail(id) {
	return request.get(`/home/bill/${id}`)
}

function payBill(data) {
	return request.post('/home/bill/pay', data)
}

function getPaymentRecords(params) {
	return request.get('/home/bill/payment/records', params)
}

function getPropertyInfo() {
	return request.get('/home/property/info')
}

function getPropertyPraiseList(params) {
	return request.get('/home/property/praise/list', params)
}

function addPropertyPraise(data) {
	return request.post('/home/property/praise/add', data)
}

/** 小区助手（DeepSeek），需登录 */
function assistantChat(data) {
	return request.post('/home/assistant/chat', data, { timeout: 90000 })
}

export default {
	getUserInfo,
	getFocus,
	getNotice,
	getNoticeList,
	getNoticeDetail,
	getFunctionList,
	getCommunityLife,
	getHomeData,
	openDoor,
	getVisitorList,
	createVisitor,
	deleteVisitor,
	getRepairList,
	createRepair,
	getRepairDetail,
	cancelRepair,
	getBillList,
	getBillDetail,
	payBill,
	getPaymentRecords,
	getPropertyInfo,
	getPropertyPraiseList,
	addPropertyPraise,
	assistantChat
}
