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
