package com.educiot.common.api;

import com.educiot.common.base.BaseVO;
import io.swagger.annotations.ApiModel;

/**
 * @program: server
 * @description: 返回登录信息封装实体类
 * @author: Mr.AI
 * @create: 2020-04-16
 **/

@ApiModel(value = "UserInfo")
public class UserInfo extends BaseVO {


    public String getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(String schoolid) {
        this.schoolid = schoolid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public void setSchoolname(String schoolname) {
        this.schoolname = schoolname;
    }

    private String schoolid;
    private String name;

    private String userid;

    private String schoolname;

}
