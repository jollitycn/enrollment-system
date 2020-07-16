package com.educiot.common.api;

import io.swagger.annotations.ApiModel;

/**
 * @program: server
 * @description: 调用用户接口返回结果封装类
 * @author: Mr.AI
 * @create: 2020-04-16
 **/

@ApiModel(value = "调用用户接口返回结果封装类")
public class RequestResult extends APIResult {
    private ResultLoginData data;

    public RequestResult() {
    }

    @Override
    public ResultLoginData getData() {
        return this.data;
    }

    public void setData(ResultLoginData data) {
        this.data = data;
    }

}
