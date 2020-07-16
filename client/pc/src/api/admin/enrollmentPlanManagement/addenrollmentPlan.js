import request from '../../../utils/request';

//新增年度招生计划管理下的基本信息
export const addPlanInfo = (data) => {
  return request({
    url: '/plan/addPlanInfo',
    method: 'post',
    data
  })
}
// 新增年度招生计划管理下的学校简介
export const addPlanSchoolIntroduction = (data) => {
  return request({
    url: '/plan-school-introduction/addPlanSchoolIntroduction',
    method: 'post',
    data
  })
}
//新增年度招生计划管理下的招生简介
export const addPlanRecruitInfo = (data) => {
  return request({
    url: '/plan-recruit-info/addPlanRecruitInfo',
    method: 'post',
    data
  })
}

//查看招生计划基本信息
export const queryPlanInfo = (planId) => {
  return request({
    url: `/plan/queryPlanInfo?planId=${planId}`,
    method: 'get',
  })
}
//编辑招生计划基本信息
export const updatePlanInfo = (data) => {
  return request({
    url: `/plan/updatePlanInfo`,
    method: 'put',
    data
  })
}
//编辑计划招生学校信息
export const updatePlanSchoolIntroduction = (data) => {
  return request({
    url: `/plan-school-introduction/updatePlanSchoolIntroduction`,
    method: 'put',
    data
  })
}
//编辑招生简介信息
export const updatePlanRecruitInfo = (data) => {
  return request({
    url: `/plan-recruit-info/updatePlanRecruitInfo`,
    method: 'put',
    data
  })
}

//获取计划招生学校
export const queryPlanSchoolIntroduction = (planId) => {
  return request({
    url: `/plan-school-introduction/queryPlanSchoolIntroduction?planId=${planId}`,
    method: 'get'
  })
}

//获取招生简介信息
export const queryRecruitSchoolInfo = (planId) => {
  return request({
    url: `/plan-recruit-info/queryRecruitSchoolInfo?planId=${planId}`,
    method: 'get'
  })
}

//添加招生院系信息
export const addCollegeInfo = (planId) => {
  return request({
    url: `/college/addCollegeInfo?planId=${planId}`,
    method: 'get'
  })
}

//获取招生院系信息
export const queryCollegeInfo = (planId) => {
  return request({
    url: `/college/queryCollegeInfo?planId=${planId}`,
    method: 'get'
  })
}

//获取招生院系详细信息
export const queryCollegeInfoById = (collegeId) => {
  return request({
    url: `/college/queryCollegeInfoById?collegeId=${collegeId}`,
    method: 'get'
  })
}

//编辑院系详细信息
export const updateCollegeInfo = (data) => {
  return request({
    url: `/college/updateCollegeInfo`,
    method: 'put',
    data
  })
}

//获取招生院系详细信息
export const queryMajorInfoByCollegeId = (collegeId) => {
  return request({
    url: `/major/queryMajorInfoByCollegeId?collegeId=${collegeId}`,
    method: 'get'
  })
}

//查看招生专业信息明细
export const queryMajorInfoByMajorId = (majorId) => {
  return request({
    url: `/major/queryMajorInfoByMajorId?majorId=${majorId}`,
    method: 'get'
  })
}
//编辑招生专业信息明细
export const updateMajorInfo = (data) => {
  return request({
    url: `/major/updateMajorInfo`,
    method: 'put',
    data
  })
}
//删除招生专业信息明细
export const deleteMajorInfo = (majorId) => {
  return request({
    url: `/major/deleteMajorInfo/${majorId}`,
    method: 'delete'
  })
}
//新增招生专业信息明细
export const addMajorInfo = (data) => {
  return request({
    url: `/major/addMajorInfo`,
    method: 'post',
    data
  })
}
//编辑计划招生学校信息
export const updateRecruitSchoolInfo = (data) => {
  return request({
    url: `/recruit-school/updateRecruitSchoolInfo`,
    method: 'put',
    data
  })
}
//获取招生学校信息
export const RecruitSchoolInfo = (id) => {
  return request({
    url: `/recruit-school/queryRecruitSchoolInfo?recruitSchoolId=${id}`,
    method: 'get',
  })
}

//根据院系id获取专业列表
export const getMajorList = (id) => {
  return request({
    url: `/api/sys/major/list`,
    method: 'get',
    params: id
  })
}
