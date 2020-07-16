import request from '../../../utils/request';

//新增专业同步到平台
export const major = (data) => {
  return request({
    url: '/api/sys/add/major',
    method: 'post',
    data
  })
}
//根据学校id获取院系列表信息
export const depart = (data) => {
  return request({
    url: '/api/sys/depart/info',
    method: 'post',
    data
  })
}
//根据员工id或者模糊名称，获取负责人信息
export const superior = (data) => {
  return request({
    url: '/api/sys/get/superior/info',
    method: 'post',
    data
  })
}
//根据院系的apiid获取专业列表
export const majorlist = (query) => {
  return request({
    url: '/api/sys/major/list',
    method: 'post',
    params:query
  })
}
//获取学校id获取当前组织结构
export const struct = (data) => {
  return request({
    url: `/api/sys/school/org/struct?schoolId=${data}`,
    method: 'post',
    data
  })
}
//获取当前登录学校信息和账号信息
export const schoolinfo = (data) => {
  return request({
    url: '/api/sys/school/user/info',
    method: 'post',
    data
  })
}
//根据学校id获取学期列表信息
export const termlist = (data) => {
  return request({
    url: `/api/sys/term/info?schoolId=${data}`,
    method: 'post'
  })
}
//新增学期返回学期id和名称
export const term = (data) => {
  return request({
    url: '/api/sys/term/add',
    method: 'post',
    data
  })
}
//上传文件
export const uploadAdd = (data) => {
  return request({
      url:'/upload/add',
      method: 'post',
      data,
      config : {
          headers:{'Content-Type': 'multipart/form-data'}
      }
  })
}