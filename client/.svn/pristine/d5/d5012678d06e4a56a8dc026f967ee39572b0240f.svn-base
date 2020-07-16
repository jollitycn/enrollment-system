//宣讲计划管理api
import request from "../../utils/request";

// 获取宣讲计划列表
export const getList = query => {
  return request({
    url: `/preach-plan/page`,
    method: "post",
    data: query
  });
};

//新增宣讲计划
export const addPreach = data => {
  return request({
    url: `/preach-plan/add`,
    method: "put",
    data
  });
};

//删除宣讲计划
export const deleteData = id => {
  return request({
    url: `/preach-plan/delete/${id}`,
    method: "delete"
  });
};

//发布宣讲计划
export const publish = id => {
  return request({
    url: `/preach-plan/publish/${id}`,
    method: "put"
  });
};

// 获取宣讲计划详情
export const getDetail = id => {
  return request({
    url: `/preach-plan/detail/${id}`,
    method: "get"
  });
};

// 获取演讲计划列表
export const getPreachList = params => {
  return request({
    url: `/preach-plan/list`,
    method: "get",
    params
  });
};

//更新宣讲计划
export const updateData = data => {
  return request({
    url: `/preach-plan/update`,
    method: "post",
    data
  });
};

//测试接口 待删除

//获取指定学校的组织结构
export const getStruct = data => {
  return request({
    url: `/api/sys/school/org/struct`,
    method: "post",
    params: data
  });
};

//获取面向学校
export const listAllSourceSchool = () => {
  return request({
    url: `/sourceSchool/listAllSourceSchool`,
    method: "get"
  });
};
//获取面向学校
export const listAllSourceSchoolGroup = () => {
  return request({
    url: `/sourceSchool/listAllSourceSchoolGroup`,
    method: "get"
  });
};

//获取演讲反馈列表
//
export const preachSummary = data => {
  return request({
    url: `/preach-feedback/page`,
    method: "post",
    data
  });
};

//获取打分的数据
//
export const feedBackCount = data => {
  return request({
    url: `/preach-feedback/feedBackCount`,
    method: "post",
    data
  });
};

//获取所有演讲计划列表
export const pageAllPreachPlanByUserId = data => {
  return request({
    url: `/preach-plan/pageAllPreachPlanByUserId`,
    method: "post",
    data
  });
};
//根据当前主讲人获取所有演讲计划列表
export const pagePreachPlanByUserId = data => {
  return request({
    url: `/preach-plan/pagePreachPlanByUserId`,
    method: "post",
    data
  });
};

//新增宣讲总结
export const addPreachSummary = data => {
  return request({
    url: `/preach-summary/add`,
    method: "put",
    data
  });
};

  //获取宣讲计划总结列表
export const getPreachSummary = data => {
  return request({
    url: `/preach-summary/page`,
    method: "post",
    data
  });
};

  //宣讲计划报名签到列表
  export const getStudentSigned = data => {
    return request({
      url: `/public-register/page`,
      method: "post",
      data
    });
  };

  //宣讲计划签到列表
  export const queryPreachList = data => {
    return request({
      url: `/public-register/queryPreachList`,
      method: "post",
      data
    });
  };
