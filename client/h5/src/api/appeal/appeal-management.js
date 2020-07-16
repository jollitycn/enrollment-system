//诉求管理api
import request from "../../utils/request";

// 根据招生学校ID查询学生的已处理和未处理的诉求信息
export const queryRequestInfo = query => {
  return request({
    url: `/request/queryRequestInfo`,
    method: "get",
    params:query
  });
};

// 根据招生学校ID查询所有诉求信息
export const queryRequestInfoByRecruitSchoolId = query => {
  return request({
    url: `/request/queryRequestInfoByRecruitSchoolId`,
    method: "get",
    params:query
  });
};

// 修改诉求信息状态
export const updateRequestStatus = requestId => {
  return request({
    url: `/request/updateRequestStatus/${requestId}`,
    method: "get"
  });
};

// 添加或修改学生诉求信息
export const saveOrUpdateRequestInfo = data => {
  return request({
    url: `/request/saveOrUpdateRequestInfo`,
    method: "post",
    data
  });
};