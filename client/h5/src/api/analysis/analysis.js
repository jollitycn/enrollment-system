//APP首页报表和数据分析
import request from "../../utils/request";

// 报名生源学校分析
export const enrollmentAnalysis = query => {
  return request({
    url: `/report/enrollmentAnalysis`,
    method: "get",
    params: query
  });
};
//总体招生进度报表
export const enrollmentProgress = query => {
  return request({
    url: `/report/enrollmentProgress`,
    method: "get",
    params: query
  });
};
// 负责人招人进度
export const principalProgress = () => {
  return request({
    url: `/report/principalProgress`,
    method: "get"
  });
};

//各专业报名分析
export const professionalRegistration = query => {
  return request({
    url: `/report/professionalRegistration`,
    method: "get",
    params: query
  });
};

//报名阶段分析
export const registrationStage = (query) => {
  return request({
    url: `/report/registrationStage`,
    method: "get",
    params: query
  });
};

//获取所有已发布计划信息
export const queryAllPublishPlanAPP = query => {
  return request({
    url: `/report/queryAllPublishPlanAPP`,
    method: "get",
    params: query
  });
};

//APP切换计划
export const getPlan = planId => {
  return request({
    url: `/checkPlan/getPlan/${planId}`,
    method: "get"
  });
};

//APP获取小程序码
export const generateQrCode = () => {
  return request({
    url: `/getQrcode/generateQrCode`,
    method: "get",
  });
};