package com.educiot.recruit.server.wechat.common;

import com.educiot.common.base.LoginInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: login
 * @description: 系统登录用户
 * @author: XuChao
 * @create: 2019-12-24 16:50
 **/
@Data
@ApiModel("登录用户信息")
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginUser extends LoginInfo {
    //    @ApiModelProperty(value = "接口登录信息")
//    private APIInfo apiInfo;
    @ApiModelProperty(value = "当前招生学校id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long currentSchoolId;
    //
    @ApiModelProperty(value = "当前招生计划id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long currentSchoolPlanId;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(value = "当前学期id")
    private Long currentSemesterId;

    @ApiModelProperty(value = "当前学期名称")
    private String semesterName;

    @ApiModelProperty(value = "学生公关id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long studentPublicRelationId;


    @ApiModelProperty(value = "学生id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    @ApiModelProperty(value = "学生名称")
    @JsonSerialize(using = ToStringSerializer.class)
    private String userName;
}
