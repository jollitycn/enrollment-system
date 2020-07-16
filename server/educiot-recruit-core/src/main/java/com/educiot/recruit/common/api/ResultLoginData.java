package com.educiot.recruit.common.api;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @program: server
 * @description: 返回登录信息封装实体类
 * @author: Mr.AI
 * @create: 2020-04-16
 **/

@ApiModel(value = "ResultLoginData")
public class ResultLoginData extends UserInfo {

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String account;

    private String token;
}
