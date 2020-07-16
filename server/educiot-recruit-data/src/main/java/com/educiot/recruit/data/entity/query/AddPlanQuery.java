package com.educiot.recruit.data.entity.query;

import com.educiot.common.base.BaseQuery;
import com.educiot.common.constant.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @program: server
 * @description: 招生计划信息列表入参实体
 * @author: Mr.AI
 * @create: 2020-04-13
 **/

@Data
@ApiModel(value = "招生计划信息列表入参实体")
@EqualsAndHashCode(callSuper = true)
public class AddPlanQuery extends BaseQuery {

    @ApiModelProperty(value = "招生学校ID",name = "recruitSchoolId",required = true)
    private Long recruitSchoolId;

    @ApiModelProperty(value = "季度",name = "season",required = true)
    private Integer season;

    @ApiModelProperty(value = "学期",name = "semester",required = true)
    @NotNull(message = "学期不能为空")
    private String semester;

    @ApiModelProperty(value = "标题",name = "title",required = true)
    private String title;

    @ApiModelProperty(value = "招生开始时间", name = "startTime", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.Sys.LOCALDATETIME_FORMATTER)
    private LocalDateTime startTime;

    @ApiModelProperty(value = "招生结束时间", name = "endTime", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.Sys.LOCALDATETIME_FORMATTER)
    private LocalDateTime endTime;

    @ApiModelProperty(value = "招生负责人ID",name = "chargerId",required = true)
    private Long chargerId;

    @ApiModelProperty(value = "招生负责人名称",name = "chargerName",required = true)
    private String chargerName;

    @ApiModelProperty(value = "联系电话")
    private String telephoneNumber;

    @ApiModelProperty(value = "说明")
    private String comment;

    @ApiModelProperty(value = "学期ID")
    @NotNull(message = "学期ID不能为空")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long semesterId;

    @ApiModelProperty(value = "创建人")
    private Long creatorId;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

    @ApiModelProperty(value = "学期开始使劲")
    private LocalDate semesterStart;

    @ApiModelProperty(value = "学期结束时间")
    private LocalDate semesterEnd;

}
