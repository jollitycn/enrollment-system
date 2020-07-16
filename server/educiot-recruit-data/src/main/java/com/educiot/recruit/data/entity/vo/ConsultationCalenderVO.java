package com.educiot.recruit.data.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.educiot.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * @author liuhao
 * @program educiot-recruit
 * @description：咨询人数日历
 * @date Create in 2020/4/16
 */
@Data
@ApiModel(value = "咨询人数日历返回实体VO")
@EqualsAndHashCode(callSuper = true)
public class ConsultationCalenderVO extends BaseVO {

    @ApiModelProperty(value = "接待日期")
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private LocalDate date;

    @ApiModelProperty(value = "实际人数")
    private Integer actualAmount;
}
