import request from '../../../utils/request';

// 分页获取年度招生计划管理列表
export const listGroupInfoPage = (query) => {
  return request({
      url: '/plan/listGroupInfoPage',
      method: 'get',
      params: query
  })
}
// 编辑计划的默认状态
export const updateIsDefaulted = (query) => {
  return request({
      url: '/plan/updateIsDefaulted',
      method: 'put',
      params: query
  })
}

export const updatePlanStatusEnd = (data) => {
  return request({
      url: '/plan/updatePlanStatusEnd',
      method: 'put',
      data
  })
}

// 发布与删除的操作
export const updatePlanStatus = (data) => {
  return request({
      url: '/plan/updatePlanStatus',
      method: 'put',
      data
  })
}

// 复制计划信息
export const addCopyPlanInfo = (query) => {
  return request({
      url: '/plan/addCopyPlanInfo',
      method: 'post',
      data: query
  })
}

// 获取所有已发布计划信息
export const queryAllPublishPlan = (query) => {
  return request({
      url: '/plan/queryAllPublishPlan',
      method: 'get',
      params: query
  })
}

// 获取所有已发布计划信息
export const queryAllFinishedPlan = () => {
  return request({
      url: '/plan/queryAllFinishedPlan',
      method: 'get'
  })
}