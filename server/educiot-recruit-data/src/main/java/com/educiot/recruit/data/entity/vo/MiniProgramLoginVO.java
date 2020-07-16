package com.educiot.recruit.data.entity.vo;

import com.educiot.common.base.BaseVO;
import com.educiot.recruit.data.entity.Login;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/4/29 11:10
 */
@Data
@ApiModel(value = "小程序登录信息返回实体VO")
@EqualsAndHashCode(callSuper = true)
public class MiniProgramLoginVO extends BaseVO {
    @ApiModelProperty(value = "openid等相关信息")
    private Login openidInfo;

    @ApiModelProperty(value = "登录类型（0：学生，1：家长）")
    private Integer loginType;

    @ApiModelProperty(value = "token")
    private String token;

    @ApiModelProperty(value = "当前账号（openid）所关注的学生集合")
    private List<StudentIdAndNameVO> studentList;

    @ApiModelProperty(value = "当前选择（默认）的招生学校")
    private RecruitSchoolIdAndNameVO currentRecruitSchool;

    @ApiModelProperty(value = "当前选择（默认）的学生")
    private StudentIdAndNameVO currentStudent;

    //    @ApiModelProperty(value = "学生公关信息")
//    private StudentPublicRelationVO studentPublicId;
    @ApiModelProperty(value = "学生公关ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long studentPublicRelationId;

    @ApiModelProperty(value = "招生计划信息")
    private PlanLoginVO plan;

    private Long timestamp;
//
//    public void setStudentPublicId(Long studentPublicId) {
//        this.studentPublicId = studentPublicId;
//    }
//
//    public Long getStudentPublicId() {
//        return studentPublicId;
//    }

//    @ApiModelProperty(value = "需要跳转的页面：0---学校列表页面  1---学生信息页面 2---宣讲页面")
//    private Integer pageFlag;
}
