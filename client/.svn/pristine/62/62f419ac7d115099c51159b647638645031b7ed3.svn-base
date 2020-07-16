import request from "../../utils/request";

//获取学校列表
export const listSourceSchoolPage = params => {
  return request({
    url: "/in-school/listSourceSchoolPage",
    method: "get",
    params
  });
};

//获取对应学校下的班级列表
export const listClassPage = params => {
  return request({
    url: "/in-school/listClassPage",
    method: "get",
    params
  });
};

//获取学生负责老师信息
export const queryTeacherCharge = () => {
  return request({
    url: "/family-member/queryTeacherCharge",
    method: "get"
  });
};

//验证必填字段是否填完
export const queryRequiredFieldStatu = () => {
  return request({
    url: "/student-info/queryRequiredFieldStatu",
    method: "get"
  });
};

// 获取学校班级信息
export const getSourceSchoolInfo = () => {
  return request({
    url: `/in-school/getSourceSchoolInfo`,
    method: "get"
  });
};
