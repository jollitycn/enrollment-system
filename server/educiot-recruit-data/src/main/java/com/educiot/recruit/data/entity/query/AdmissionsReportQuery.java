package com.educiot.recruit.data.entity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.educiot.recruit.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;


/**
 * @program: server
 * @description: 招生报表查询条件封装类
 * @author: Mr.AI
 * @create: 2020-04-26
 **/

@Data
@ApiModel(value = "签到情况入参实体类")
@EqualsAndHashCode(callSuper = true)
public class AdmissionsReportQuery extends BaseQuery {

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate endTime;

    @ApiModelProperty(value = "学期ID")
    private Long semesterId;

}
