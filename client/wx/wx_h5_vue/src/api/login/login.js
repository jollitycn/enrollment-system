import request from "../../utils/request";

/**我的诉求s*/
//获取招生学校列表
export const listRecruitSchool = params => {
  return request({
    url: "/login/listRecruitSchool",
    method: "get",
    params
  });
};

//获取招生学校对应的生源学校
export const listSourceSchoolByRecruit = recruitSchoolId => {
  return request({
    url: `/login/listSourceSchoolByRecruit/${recruitSchoolId}`,
    method: "get"
  });
};
//获取对应学校的班级信息
export const listClassBySource = data => {
  return request({
    url: `/login/listClassBySource`,
    method: "post",
    data
  });
};

//注册
export const register = data => {
  return request({
    url: `/login/register`,
    method: "post",
    data
  });
};
//刷新token
export const reLogin = () => {
  return request({
    url: `/login/reLogin`,
    method: "post"
  });
};
//

//登录
export const login = data => {
  return request({
    url: `/login/login`,
    method: "post",
    data
  });
};

//注册时学校班级姓名相同，手机号不同，绑定操作
export const registerBind = data => {
  return request({
    url: `/login/registerBind`,
    method: "post",
    data
  });
};
//注册时学校班级手机号相同，学生信息不同，直接确认操作
export const registerNewStudent = data => {
  return request({
    url: `/login/registerNewStudent`,
    method: "post",
    data
  });
};

//绑定学生
export const bindStudent = data => {
  return request({
    url: `/login/bindStudent`,
    method: "post",
    data
  });
};
//验证姓名和电话是否一致
// /login/checkStudentAndPhone
export const checkStudentAndPhone = data => {
  return request({
    url: `/login/checkStudentAndPhone`,
    method: "post",
    data
  });
};
//获取验证码
//
export const getMsgCode = phone => {
  return request({
    url: `/login/getMsgCode`,
    method: "get",
    params: {
      phone
    }
  });
};

// 绑定招生学校和学生：针对已注册的账号在招生学校无绑定关系情况
export const bindRecruitSchoolAndStudent = data => {
  return request({
    url: `/login/bindRecruitSchoolAndStudent`,
    method: "post",
    data
  });
}