package com.educiot.recruit.common.api;

import com.educiot.common.api.base.BaseVO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Jason Hong
 */
@Data
@ApiModel("接口登录用户信息")
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIInfo extends BaseVO {
    @ApiModelProperty(value = "用户ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    @ApiModelProperty(value = "用户名称")
    private String userName;

    @ApiModelProperty(value = "学校ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long schoolId;

    @ApiModelProperty(value = "学校名称")
    private String schoolName;

    public APIInfo() {
    }

    public Long getUserId() {
        return this.userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public Long getSchoolId() {
        return this.schoolId;
    }

    public String getSchoolName() {
        return this.schoolName;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

}