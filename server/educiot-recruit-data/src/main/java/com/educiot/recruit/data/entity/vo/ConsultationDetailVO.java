package com.educiot.recruit.data.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.educiot.recruit.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * @author liuhao
 * @program educiot-recruit
 * @description：到校咨询详情
 * @date Create in 2020/4/16
 */
@Data
@ApiModel(value = "到校咨询详情返回实体VO")
@EqualsAndHashCode(callSuper = true)
public class ConsultationDetailVO extends BaseVO {

    @ApiModelProperty(value = "到校咨询ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long consultationId;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate endTime;

    @ApiModelProperty(value = "日常接待人ID")
    @JsonSerialize(using = ToStringSerializer.class)
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
