//生源学校管理api
import request from "../../utils/request";

//获取招生学校下的学校类型（数据字典配置）
export const listSourceSchoolType = () => {
  return request({
    url: `/sourceSchool/listSourceSchoolType`,
    method: "get"
  });
};
//新增生源学校
export const addSourceSchool = data => {
  return request({
    url: `/sourceSchool/addSourceSchool`,
    method: "post",
    data
  });
};
//获取完成度统计
export const listCompleteRate = () => {
  return request({
    url: `/sourceSchool/listCompleteRate`,
    method: "get"
  });
};
//获取生源学校详情
export const getSourceSchoolDetail = id => {
  return request({
    url: `/sourceSchool/getSourceSchoolDetail/${id}`,
    method: "get"
  });
};
//分页获取生源学校下的班级
export const listSchoolClassPage = query => {
  return request({
    url: `/sourceSchool/listSchoolClassPage`,
    method: "get",
    params: query
  });
};
//分页获取生源学校信息列表
export const listSourceSchoolPage = query => {
  return request({
    url: `/in-school/listSourceSchoolPage`,
    method: "get",
    params: query
  });
};
//获取班级信息
export const getClassInfo = id => {
  return request({
    url: `/sourceSchool/getClassInfo/${id}`,
    method: "get"
  });
};
//编辑生源学校信息
export const updateSourceSchool = data => {
  return request({
    url: `/sourceSchool/updateSourceSchool`,
    method: "put",
    data
  });
};
//新增班级
export const addClass = data => {
  return request({
    url: `/sourceSchool/addClass`,
    method: "post",
    data
  });
};
//编辑班级信息
export const updateClass = data => {
  return request({
    url: `/sourceSchool/updateClass`,
    method: "put",
    data
  });
};

//编辑班级信息
export const deleteClass = classId => {
  return request({
    url: `/sourceSchool/deleteClass/${classId}`,
    method: "put"
  });
};

// 到校咨询获取学校
export const listSourceSchoolPage1 = () => {
  return request({
    url: `/group-source-relation/listSourceSchoolVOResult`,
    method: "get"
  });
};
