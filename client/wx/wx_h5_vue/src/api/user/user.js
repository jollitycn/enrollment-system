import request from "../../utils/request";

/**我的诉求s*/
//获取诉求列表
export const queryRequestInfo = params => {
  return request({
    url: "/request/queryRequestInfo",
    method: "get",
    params
  });
};

//新增或保存诉求
export const saveOrUpdateRequestInfo = data => {
  return request({
    url: "/request/saveOrUpdateRequestInfo",
    method: "post",
    data
  });
};
/**我的诉求e*/
/**宣讲评价s*/
//获取宣讲计划列表
export const listPageByStudentPublicRelationId = data => {
  return request({
    url: "/preach-plan/listPageByStudentPublicRelationId",
    method: "post",
    data
  });
};

//新增宣讲计划评价
export const addFeedback = data => {
  return request({
    url: "/preach-feedback/add",
    method: "post",
    data
  });
};
/**宣讲评价e*/
/**学生报名进度信息查询s*/
//学生报名进度信息查询
export const queryProgressInfo = () => {
  return request({
    url: "/progress/getProgress",
    method: "get"
  });
};
/**学生报名进度信息查询e*/
/** 到校咨询接口s*/
//获取咨询ID
export const getConsultationId = () => {
  return request({
    url: "/consultation/getConsultationId",
    method: "get"
  });
};
//获取接待日信息列表
export const getConsultationDayList = (id) => {
  return request({
    url: `/consultation/getConsultationDayList/${id}`,
    method: "get"
  });
};
//获取我的预约日期
export const getMyRegister = () => {
  return request({
    url: `/consultation/getMyRegister`,
    method: "get"
  });
};
//获取咨询日期详情
export const getConsultationDayDetail = params => {
  return request({
    url: `/consultation/getConsultationDayDetail`,
    method: "get",
    params
  });
};
//新增到校咨询
export const addRegister = data => {
  return request({
    url: `/consultation/addRegister`,
    method: "post",
    data
  });
};
//删除到校咨询
export const deleteRegister = data => {
  return request({
    url: `/consultation/deleteRegister/${data}`,
    method: "delete",
  });
};
/** 到校咨询接口e*/
//用户绑定校验
export const checkBindStudent = data => {
  return request({
    url: `/student-info/checkBindStudent`,
    method: "post",
    data
  });
};
//添加新学生
export const addNewStudent = data => {
  return request({
    url: `/student-info/addNewStudent`,
    method: "post",
    data
  });
};
//绑定该学生
export const bindStudent = data => {
  return request({
    url: `/student-info/bindStudent`,
    method: "post",
    data
  });
};
//获取消息列表
export const getMessageList = () => {
  return request({
    url: `/message/getMessageList`,
    method: "get"
  });
};
//获取入选通知信息
export const getAdmissionInfo = taskReceiverId => {
  return request({
    url: `/message/getAdmissionInfo/${taskReceiverId}`,
    method: "get"
  });
};

//确认通知
export const confirm = taskReceiverId => {
  return request({
    url: `/message/confirm/${taskReceiverId}`,
    method: "post"
  });
};

// 获取学校简介
export const getByRecruitSchoolId = recruitSchoolId => {
  return request({
    url: `/plan-school-introduction/getByRecruitSchoolId/${recruitSchoolId}`,
    method: "get"
  });
};
// 招生简介
export const queryPlanRecruitInfoByRecruitSchoolId = recruitSchoolId => {
  return request({
    url: `/plan-recruit-info/queryPlanRecruitInfoByRecruitSchoolId/${recruitSchoolId}`,
    method: "get"
  });
};


// 获取招生院系列表信息
export const queryCollegeInfo = recruitSchoolId => {
  return request({
    url: `/college/queryCollegeInfo/${recruitSchoolId}`,
    method: "get"
  });
};

// 获取具体某一个招生院系信息
export const queryCollegeInfoById = paramsId => {
  return request({
    url: `/college/queryCollegeInfoById`,
    method: "get",
    params: {
      collegeId: paramsId
    }
  });
};

//获取某一个招生院系下的所有专业列表
export const queryMajorInfoByCollegeId = paramsId => {
  return request({
    url: `/major/queryMajorInfoByCollegeId`,
    method: "get",
    params: {
      collegeId: paramsId
    }
  });
};

//查看某个专业的详细信息
export const queryMajorInfoByMajorId = params => {
  return request({
    url: `/aim-major/queryMajorInfoByMajorId`,
    method: "get",
    params
  });
};

// 获取到校咨询有预约的日期列表
export const getConsultationCalender = consultationId => {
  return request({
    url: `/consultation/getConsultationCalender/${consultationId}`,
    method: "get",
  });
}
