package com.educiot.recruit.data.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.educiot.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * @author liuhao
 * @program educiot-recruit
 * @description：接待日列表
 * @date Create in 2020/4/16
 */
@Data
@ApiModel(value = "接待日列表返回实体VO")
@EqualsAndHashCode(callSuper = true)
public class ConsultationDayListVO extends BaseVO {

    @ApiModelProperty(value = "接待日期")
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private LocalDate opendayDate;

    @ApiModelProperty(value = "是否是接待日（0：否，1：是）")
    private Integer isOpenday;

    @ApiModelProperty(value = "接待日信息ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long opendayId;
}
