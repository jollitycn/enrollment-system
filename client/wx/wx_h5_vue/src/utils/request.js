import axios from "axios";
import { M } from "./message.js";
const filterCode = [600007, 600011, 600006, 600011];  //需要过滤的错误提示码
const service = axios.create({
  // process.env.NODE_ENV === 'development' 来判断是否开发环境
  // easy-mock服务挂了，暂时不使用了
  // baseURL: 'https://www.easy-mock.com/mock/592501a391470c0ac1fab128',如果是开发环境为/，线上环境为指定url地址
  baseURL: process.env.NODE_ENV === "development" ? "/" : "wechat/",
  timeout: 15000
});
// 添加请求拦截器
service.interceptors.request.use(
  config => {
    let token = localStorage.getItem("token");
    if (token) {
      config.headers.common["token"] = token;
    }
    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

// 添加响应拦截器
service.interceptors.response.use(
  response => {
    // 判断请求状态码是否正确
    if (response.status === 200) {
      //判断返回状态码是否正确
      if (response.data.retCode === 0) {
        return response.data;
      } else {
        //状态码错误也跳转到catch中
        if (filterCode.indexOf(response.data.retCode) === -1) {
          M.error(response.data.retCode, response.data.message);
        }
        return Promise.reject(response);
      }
    } else {
      //请求状态码错误也跳转到catch中
      M.error(response.data.retCode, response.data.message);
      return Promise.reject(response);
    }
  },
  error => {
    // 系统异常
    M.fail(error.response.status || 500);
    return Promise.reject(error.response);
  }
);

export default service;
