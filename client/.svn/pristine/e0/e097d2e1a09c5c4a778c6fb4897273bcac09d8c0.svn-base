import request from "../../../utils/request";

//查询学生基本信息填写完成状态
export const studentStatus = () => {
  return request({
    url: "/student-info/queryStudentInfoStatu",
    method: "get"
  });
};

//查询学生studentList2填写完成状态
export const schoolStatus = () => {
  return request({
    url: "/in-school/queryInSchoolInfoStatu",
    method: "get"
  });
};

//查询学生家庭成员信息填写完成状态
export const queryFamilyMemberStatus = () => {
  return request({
    url: "/family-member/queryFamilyMemberStatu",
    method: "get"
  });
};

//获取学生联系方式完成状态
export const queryContactDetailStatus = () => {
  return request({
    url: "/student-info/queryContactDetailStatu",
    method: "get"
  });
};

//添加学生基本信息
export const addStudentInfo = data => {
  return request({
    url: "/student-info/addStudentInfo",
    method: "post",
    data
  });
};

//获取学生基本信息
export const queryStudentInfo = () => {
  return request({
    url: "/student-info/queryStudentInfo",
    method: "get"
  });
};

//编辑学生基本信息
export const updateStudentInfo = data => {
  return request({
    url: "/student-info/updateStudentInfo",
    method: "put",
    data
  });
};

//添加或修改学生在校信息
export const saveOrUpdateInSchoolInfo = data => {
  return request({
    url: "/in-school/saveOrUpdateInSchoolInfo",
    method: "post",
    data
  });
};

//根据学生基本信息ID查询学生在校信息(包含生源学校、班级信息、成绩信息)
export const queryInSchoolInfo = () => {
  return request({
    url: "/in-school/queryInSchoolInfo",
    method: "get"
  });
};

//获取学生家庭成员信息
export const listGroupInfoPage = () => {
  return request({
    url: "/family-member/listGroupInfoPage",
    method: "get"
  });
};

//添加或修改学生家庭信息成员信息
export const saveOrUpdateFamilyMemberInfo = data => {
  return request({
    url: "/family-member/saveOrUpdateFamilyMemberInfo",
    method: "post",
    data
  });
};

// 学生签到
export const signInPreachPlanId = preachPlanId => {
  return request({
    url: `/public-register/signIn/${preachPlanId}`,
    method: "put"
  });
};
//学生报名
export const signUpPreachPlanId = preachPlanId => {
  return request({
    url: `/public-register/signUp/${preachPlanId}`,
    method: "put"
  });
};
