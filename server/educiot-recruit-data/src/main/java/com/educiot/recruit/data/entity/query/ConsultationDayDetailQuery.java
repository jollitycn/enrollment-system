package com.educiot.recruit.data.entity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.educiot.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liuhao
 * @program educiot-recruit
 * @description：咨询日程详情
 * @date Create in 2020/4/15
 */
@Data
@ApiModel(value = "咨询日程详情入参实体类")
@EqualsAndHashCode(callSuper = true)
public class ConsultationDayDetailQuery extends BaseQuery {

    @ApiModelProperty(value = "日期")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private String date;

    @ApiModelProperty(value = "到校咨询ID")
    private Long consultationId;
}
