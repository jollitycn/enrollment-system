import request from "../../utils/request";

export const getAllList = () => {
  return request({
    url: "/sys-region/getAllList",
    method: "get"
  });
};
//民族列表
export const nationList = () => {
  return request({
    url: "/sys-nation/list",
    method: "post"
  });
};
//上传文件
export const uploadAdd = data => {
  return request({
    url: "/upload/add",
    method: "post",
    data,
    config: {
      headers: { "Content-Type": "multipart/form-data" }
    }
  });
};

// 获取微信配置
export const getConfig = () => {
  return request({
    url: "/wechat/getConfig",
    method: "get"
  });
};


