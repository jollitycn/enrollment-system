package com.educiot.recruit.server.app.common;

import com.educiot.recruit.common.api.APIInfo;
import com.educiot.recruit.common.base.LoginInfo;
import com.educiot.recruit.data.entity.ext.ChargerExt;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    @ApiModelProperty(value = "接口登录信息")
    private APIInfo apiInfo;

    private String account;
    private String pwd;

    @ApiModelProperty(value = "当前招生学校id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long currentSchoolId;

    @ApiModelProperty(value = "当前招生计划id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long currentSchoolPlanId;
//    @ApiModelProperty(value = "当前招生计划")
////    @JsonFormat(shape = JsonFormat.Shape.STRING)
//    private PlanExt plan;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(value = "当前学期id")
    private Long currentSemesterId;

    @ApiModelProperty(value = "当前学期名称")
    private String SemesterName;

   /* @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty(value = "当前登录人的招生组")
    private Long currentLoginUserGroupId;*/

    @ApiModelProperty(value = "当前登录人信息")
    private ChargerExt charger;


    @ApiModelProperty(value = "小程序二维码URL")
    private String qrCodeUrl;
//
//    @ApiModelProperty(value = "当前招生计划学期信息")
//    private SemesterExt semester;
//
//    @ApiModelProperty(value = "当前招生学校信息")
//    private RecruitSchoolExt recruitSchool;
//
//    @ApiModelProperty(value = "当前招生目标的所有招生计划")
//    private List<PlanExt> plans;
//
//    @ApiModelProperty(value = "当前学期的所有宣讲计划")
//    private List<PreachPlan> preachPlans;
//
//    @ApiModelProperty(value = "当前学期的所有招生组")
//    private List<GroupInfo> semesterGroups;
//
//    @ApiModelProperty(value = "当前学期的所有招生组生源学校")
//    private List<SourceSchool> sourceSchools;


//    private List<GroupInfo> groups;
}
