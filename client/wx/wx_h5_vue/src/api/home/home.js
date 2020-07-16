import request from "../../utils/request";
//切换学生
export const listRecruitSchool = id => {
  return request({
    url: `/home/switchStudent/${id}`,
    method: "get"
  });
};
//获取招生学校列表
export const listRecruitSchoolOfLogin = () => {
  return request({
    url: `/home/listRecruitSchoolOfLogin`,
    method: "get"
  });
};
//切换招生学校
export const switchRecruitSchool = recruitSchoolId => {
  return request({
    url: `/home/switchRecruitSchool/${recruitSchoolId}`,
    method: "get"
  });
};

//获取招生学校详情
export const queryRecruitSchoolInfo = recruitSchoolId => {
  return request({
    url: `/preach-school-relation/getByRecruitSchoolId/${recruitSchoolId}`,
    method: "get"
  });
};

//获取招生学校详情

export const queryCollegeInfoById = params => {
  return request({
    url: `/college/queryCollegeInfoById`,
    method: "get",
    params
  });
};
