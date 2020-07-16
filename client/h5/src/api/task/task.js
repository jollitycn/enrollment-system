import request from '../../utils/request';

/**
 * 获取当前登录用户的任务列表
 * @param {*} query 
 */
export const getTaskList = (query) => {
  return request({
    url: '/task/getTaskList',
    method: 'get',
    params: query
  })
}

/**
 * 完成任务
 * @param {*} id 任务id
 */
export const finishTask = taskReceiverId => {
  return request({
    url: `/task/jumpPage/${taskReceiverId}`,
    method: 'post'
  })
}

/**
 * 关闭任务
 * @param {*} id 任务id
 */
export const closeTask = id => {
  return request({
    url: `/task/closeTask/${id}`,
    method: 'post'
  })
}
/**
 * 跳转已读
 * @param {*} id
 */
export const jumpPage = id => {
  return request({
    url: `/task/jumpPage/${id}`,
    method: 'post'
  })
}