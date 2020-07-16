package com.educiot.recruit.data.entity.query;

import com.educiot.recruit.common.base.BaseQuery;
import com.educiot.recruit.common.constant.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @program: server
 * @description: 修改招生计划信息入参实体类
 * @author: Mr.AI
 * @create: 2020-04-13
 **/

@Data
@ApiModel(value = "修改招生计划信息入参实体类")
@EqualsAndHashCode(callSuper = true)
public class UpdatePlanInfoQuery extends BaseQuery {

    @ApiModelProperty(value = "招生计划ID",name = "planId",required = true)
    @NotNull(message = "计划ID不能为空")
    private Long planId;

    @ApiModelProperty(value = "季度",name="season")
    private Integer season;

    @ApiModelProperty(value = "学期",name="semester",required = true)
    @NotNull(message = "学期不能为空")
    private String semester;

    @ApiModelProperty(value = "学期ID")
    private Long semesterId;

    @ApiModelProperty(value = "标题",name="title")
    private String title;

    @ApiModelProperty(value = "招生开始时间", name = "startTime")
    @JsonFormat(pattern = Constant.Sys.LOCALDATETIME_FORMATTER, timezone = "GMT+8")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "招生结束时间", name = "endTime")
    @JsonFormat(pattern = Constant.Sys.LOCALDATETIME_FORMATTER, timezone = "GMT+8")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "招生负责人ID",name="chargerId")
    private Long chargerId;

    @ApiModelProperty(value = "招生负责人名称",name="chargerName")
    private String chargerName;

    @ApiModelProperty(value = "联系电话")
    private String telephoneNumber;

    @ApiModelProperty(value = "说明")
    private String comment;

    @ApiModelProperty(value = "修改人id")
    private Long modifierId;

    @ApiModelProperty(value = "学期开始使劲")
    private LocalDate semesterStart;

    @ApiModelProperty(value = "学期结束时间")
    private LocalDate semesterEnd;
}
