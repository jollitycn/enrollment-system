import axios from 'axios';
import { M } from './message.js'
import configOptions from '@/config'
import { failTest } from './failText'
const service = axios.create({
  // process.env.NODE_ENV === 'development' 来判断是否开发环境
  // easy-mock服务挂了，暂时不使用了
  // baseURL: 'https://www.easy-mock.com/mock/592501a391470c0ac1fab128',
  baseURL: configOptions.rootPath,
  timeout: 5000
});

// 添加请求拦截器
service.interceptors.request.use(
  config => {
    let token = localStorage.getItem("ACCESS_TOKEN");
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
        console.log('333',response.data)
        //状态码错误也跳转到catch中
        M.error(response.data.retCode, response.data.message || failTest[response.data.retCode])
        return Promise.reject(response);
      }
    } else {
      console.log('111',response.data)
      //请求状态码错误也跳转到catch中
      M.error(response.data.retCode, response.data.message)
      return Promise.reject(response);
    }
  },
  error => {
    // 系统异常
    console.log('222',error)
    if (error && error.response) {
      M.fail(error.response.status || 500)
    } else {
      M.fail(0)
    }
    return Promise.reject(error.response);
  }
);

export default service;