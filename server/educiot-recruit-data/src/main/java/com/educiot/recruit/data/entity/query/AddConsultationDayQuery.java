package com.educiot.recruit.data.entity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.educiot.recruit.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * @author liuhao
 * @program educiot-recruit
 * @description：新增咨询日程
 * @date Create in 2020/4/15
 */
@Data
@ApiModel(value = "新增咨询日程入参实体类")
@EqualsAndHashCode(callSuper = true)
public class AddConsultationDayQuery extends BaseQuery {

    @ApiModelProperty(value = "接待日信息ID")
    private Long opendayId;

    @ApiModelProperty(value = "到校咨询ID")
    private Long consultationId;

    @ApiModelProperty(value = "接待人ID")
    private Long receptionistId;

    @ApiModelProperty(value = "接待人名称")
    private String receptionistName;

    @ApiModelProperty(value = "接待日期")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDate opendayDate;

    @ApiModelProperty(value = "联系方式")
    private String telephone;

    @ApiModelProperty(value = "接待行程")
    private String schedule;

    @ApiModelProperty(value = "接待地址")
    private String address;

    @ApiModelProperty(value = "是否是接待日（0：否，1：是）")
    private Integer isOpenday;
}
