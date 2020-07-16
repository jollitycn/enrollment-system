//学校咨询管理
import request from "../../utils/request";

// 获取咨询ID
export const getConsultationId = () => {
  return request({
    url: `/consultation/getConsultationId`,
    method: "get"
  });
};

// 获取咨询注册列表
export const getConsultationRegisterList = query => {
  return request({
    url: `/consultation/getConsultationRegisterList`,
    method: "get",
    params: query
  });
};

// 获取接待日信息列表
export const getConsultationDayList = consultationId => {
  return request({
    url: `/consultation/getConsultationDayList/${consultationId}`,
    method: "get"
  });
};

// 获取咨询日程详情(行程安排)
export const getConsultationDayDetail = query => {
  return request({
    url: `/consultation/getConsultationDayDetail`,
    method: "get",
    params: query
  });
};

// 增加咨询登记
export const getList = data => {
  return request({
    url: `/consultation/addConsultationRegister`,
    method: "post",
    data
  });
};

// 获取咨询人数日历
export const getConsultationCalender = consultationId => {
  return request({
    url: `/consultation/getConsultationCalender/${consultationId}`,
    method: "get"
  });
};

// 增加咨询登记
export const addConsultationRegister = data => {
  return request({
    url: `/consultation/addConsultationRegister`,
    method: "post",
    data
  });
};

//获取学生的咨询信息
export const getConsultationListByStudent = studentPublicRelationId => {
  return request({
    url: `/consultation/getConsultationListByStudent/${studentPublicRelationId}`,
    method: "get"
  });
};