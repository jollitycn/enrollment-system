import request from "../../utils/request";

export const Login = data => {
  return request({
    url: "/login",
    method: "post",
    data: data
  });
};