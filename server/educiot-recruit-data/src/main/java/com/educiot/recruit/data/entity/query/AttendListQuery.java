package com.educiot.recruit.data.entity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.educiot.recruit.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liuhao
 * @program educiot-recruit
 * @description：签到情况
 * @date Create in 2020/4/17
 */
@Data
@ApiModel(value = "签到情况入参实体类")
@EqualsAndHashCode(callSuper = true)
public class AttendListQuery extends BasePageQuery {

    @ApiModelProperty(value = "日期")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private String date;

    @ApiModelProperty(value = "到校咨询ID")
    private Long consultationId;

    @ApiModelProperty(value = "咨询类型（0：个人，1：学校）")
    private Integer consultationType;
}
