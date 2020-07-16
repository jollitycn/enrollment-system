package com.educiot.recruit.data.entity.vo;

import com.educiot.common.base.BaseVO;
import com.educiot.common.constant.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @program: server
 * @description: 招生计划信息返回封装类
 * @author: Mr.AI
 * @create: 2020-04-14
 **/

@Data
@ApiModel(value = "招生计划信息返回封装类")
@EqualsAndHashCode(callSuper = true)
public class PlanVO extends BaseVO {

    @ApiModelProperty(value = "招生计划ID",name = "planId")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long planId;

    @ApiModelProperty(value = "招生学校ID",name = "recruitSchoolId")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long recruitSchoolId;

    @ApiModelProperty(value = "季度",name = "season")
    private Integer season;

    @ApiModelProperty(value = "学期",name = "semester")
    private String semester;

    @ApiModelProperty(value = "标题",name = "title")
    private String title;

    @ApiModelProperty(value = "招生开始时间", name = "startTime")
    @JsonFormat(pattern = Constant.Sys.LOCALDATETIME_FORMATTER, timezone = "GMT+8")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "招生结束时间", name = "endTime")
    @JsonFormat(pattern = Constant.Sys.LOCALDATETIME_FORMATTER, timezone = "GMT+8")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "招生负责人ID",name = "chargerId")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long chargerId;

    @ApiModelProperty(value = "招生负责人名称",name = "chargerName")
    private String chargerName;

    @ApiModelProperty(value = "联系电话",name = "telephoneNumber")
    private String telephoneNumber;

    @ApiModelProperty(value = "说明",name = "comment")
    private String comment;

    @ApiModelProperty(value = "招生计划学校简介ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long planSchoolIntroductionId;

    @ApiModelProperty(value = "招生简介ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long planRecruitInfoId;

    @ApiModelProperty(value = "学期Id")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long semesterId;
}
