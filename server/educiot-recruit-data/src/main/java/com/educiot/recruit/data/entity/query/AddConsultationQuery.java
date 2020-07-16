package com.educiot.recruit.data.entity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.educiot.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * @author liuhao
 * @program educiot-recruit
 * @description：添加到校咨询
 * @date Create in 2020/4/15
 */
@Data
@ApiModel(value = "添加到校咨询入参实体类")
@EqualsAndHashCode(callSuper = true)
public class AddConsultationQuery extends BaseQuery {

    @ApiModelProperty(value = "到校咨询ID")
    private Long consultationId;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate endTime;

    @ApiModelProperty(value = "日常接待人ID")
    private Long dailyReceptionistId;

    @ApiModelProperty(value = "日常接待人名称")
    private String dailyReceptionistName;

    @ApiModelProperty(value = "联系方式")
    private String telephone;

    @ApiModelProperty(value = "接待行程")
    private String schedule;

    @ApiModelProperty(value = "接待地址")
    private String address;
}
