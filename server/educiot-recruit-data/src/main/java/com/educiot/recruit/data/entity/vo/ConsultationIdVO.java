package com.educiot.recruit.data.entity.vo;

import com.educiot.common.base.BaseVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * @author liuhao
 * @program educiot-recruit
 * @description：咨询ID
 * @date Create in 2020/5/12
 */
@Data
@ApiModel(value = "咨询ID返回实体VO")
@EqualsAndHashCode(callSuper = true)
public class ConsultationIdVO extends BaseVO {

    @ApiModelProperty(value = "到校咨询ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long consultationId;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate endTime;
}
