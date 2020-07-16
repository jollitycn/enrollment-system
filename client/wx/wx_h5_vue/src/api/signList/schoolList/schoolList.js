import request from "../../../utils/request";
//查询学生基本信息填写完成状态
export const newsList = params => {
  return request({
    url: "/news/list",
    method: "get",
    params
  });
};
//获取学校详情
export const newDetail = id => {
  return request({
    url: `/news/detail/${id}`,
    method: "get"
  });
};
