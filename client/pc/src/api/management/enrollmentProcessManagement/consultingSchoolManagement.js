import request from '../../../utils/request';
//到校咨询

//新增咨询反馈
export const addConsultationFeedback = (data) => {
    return request({
        url: `/consultation/addConsultationFeedback`,
        method: 'post',
        data
    })
};

//新增、编辑咨询
export const addOrEditConsultation = (data) => {
    return request({
        url: `/consultation/addOrEditConsultation`,
        method: 'post',
        data
    })
};

//新增、编辑咨询日程
export const addOrEditConsultationDay = (data) => {
    return request({
        url: `/consultation/addOrEditConsultationDay`,
        method: 'post',
        data
    })
};
//获取咨询人数日历
export const getConsultationCalender = (consultationId) => {
    return request({
        url:`/consultation/getConsultationCalender/${consultationId}`,
        method: 'get',
    })
};

//获取咨询人数日历
export const getConsultationDayDetail = (params) => {
    return request({
        url:`/consultation/getConsultationDayDetail`,
        method: 'get',
        params: params
    })
};

//获取接待日信息列表
export const getConsultationDayList = (consultationId) => {
    return request({
        url:`/consultation/getConsultationDayList/${consultationId}`,
        method: 'get',
    })
};

//获取接待日信息列表
export const getRegisterCount = (consultationId) => {
  return request({
      url:`/consultation/getRegisterCount/${consultationId}`,
      method: 'get',
  })
};

//获取到校咨询详情
export const getConsultationDetail = (consultationId) => {
    return request({
        url:`/consultation/getConsultationDetail/${consultationId}`,
        method: 'get',
    })
};
//获取默认计划id
export const getDefaultPlanId = () => {
  return request({
      url:`/consultation/getDefaultPlanId`,
      method: 'get',
  })
};
// 获取咨询ID
export const getConsultationId = (semesterId) => {
    return request({
        url:`/consultation/getConsultationId/${semesterId}`,
        method: 'get',
    })
};

//获取咨询签到列表
export const getAttendList = (params) => {
    return request({
        url:`/consultation/getAttendList`,
        method: 'get',
        params: params
    })
};

//确定签到
export const addAttend = (data) => {
    return request({
        url:`/consultation/attend`,
        method: 'post',
        data
    })
};


