import axios from 'axios';
import { M } from './message.js'
import configOptions from '@/config'
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
                //特殊处理调用平台API接口
                if( response.data.data && response.data.data.status > 200){
                    //返回了错误码，平台API接口返回错误
                    M.error(response.data.data.status, response.data.data.msg)
                    return Promise.reject(response);
                }
                return response.data;
            } else {
                //状态码错误也跳转到catch中
                M.error(response.data.retCode, response.data.message)
                return Promise.reject(response);
            }
        } else {
            //请求状态码错误也跳转到catch中
            M.error(response.data.retCode, response.data.message)
            return Promise.reject(response);
        }
    },
    error => {
        // 系统异常
        if(error&&error.response){
            M.fail(error.response.status || 500)
        }else{
            M.fail(5000)
        }
        return Promise.reject(error.response);
    }
);

export default service;