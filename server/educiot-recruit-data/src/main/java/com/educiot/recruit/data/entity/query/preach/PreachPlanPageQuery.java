package com.educiot.recruit.data.entity.query.preach;

import com.educiot.recruit.common.base.BasePageQuery;
import com.educiot.recruit.common.constant.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "查询宣讲计划")
@EqualsAndHashCode(callSuper = true)
public class PreachPlanPageQuery extends BasePageQuery {
    /**
     * 查询条件：名称
     */
    @ApiModelProperty(value = "主讲人" ,example = "")
    protected String speakerName;

//    @JsonFormat(pattern=Constant.Sys.LOCALDATETIME_FORMATTER, timezone="GMT+

    @ApiModelProperty(value = "宣讲开始时间", example = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.Sys.LOCALDATETIME_FORMATTER)
    private LocalDateTime preachTime;

//    @JsonFormat(pattern=Constant.Sys.LOCALDATETIME_FORMATTER, timezone="GMT+8")

    @ApiModelProperty(value = "宣讲结束时间", example = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.Sys.LOCALDATETIME_FORMATTER)
    private LocalDateTime endTime;
//
//    @ApiModelProperty(value = "招生计划id" )
//    private Long planId;

    @ApiModelProperty(value = "学期ID", example = "semesterId")
    private Long semesterId;

}
