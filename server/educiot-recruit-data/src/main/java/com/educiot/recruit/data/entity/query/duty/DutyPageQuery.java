package com.educiot.recruit.data.entity.query.duty;

import com.educiot.common.base.BasePageQuery;
import com.educiot.common.constant.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "查询值班计划")
@EqualsAndHashCode(callSuper = true)
public class DutyPageQuery extends BasePageQuery {
    /**
     * 查询条件：名称
     */
    @ApiModelProperty(value = "值班人", name = "userName",example = "" )
    protected String userName;

    @ApiModelProperty(value = "值班开始时间", name = "startTime", example = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.Sys.LOCALDATETIME_FORMATTER)
    private LocalDateTime startTime;

    @ApiModelProperty(value = "值班结束时间", name = "endTime", example = "")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.Sys.LOCALDATETIME_FORMATTER)
    private LocalDateTime endTime;

    @ApiModelProperty(value = "计划ID")
    private Long planId;
}
