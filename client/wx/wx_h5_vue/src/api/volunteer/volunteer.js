import request from "../../utils/request";

// 根据学生ID查询学生选择的专业信息
export const queryAimMajorInfo = params => {
  return request({
    url: "/aim-major/queryAimMajorInfo",
    method: "get",
    params
  });
};
// 获取招生院系信息以及所有的专业信息
export const queryCollegeAndMajor = () => {
  return request({
    url: "/aim-major/queryCollegeAndMajor",
    method: "get"
  });
};
//添加或修改专业选择
export const saveOrUpdateAimMajorInfo = data => {
  return request({
    url: "/aim-major/saveOrUpdateAimMajorInfo",
    method: "post",
    data
  });
};
//查询对应专业详情信息
export const queryMajorInfoByMajorId = params => {
  return request({
    url: "/aim-major/queryMajorInfoByMajorId",
    methods: "get",
    params
  });
}