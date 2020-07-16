package com.educiot.recruit.data.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.educiot.common.base.BaseVO;
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
@ApiModel(value = "咨询日程详情返回实体VO")
@EqualsAndHashCode(callSuper = true)
public class ConsultationDayDetailVO extends BaseVO {

    @ApiModelProperty(value = "接待日信息ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long opendayId;

    @ApiModelProperty(value = "接待人ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long receptionistId;

    @ApiModelProperty(value = "接待人名称")
    private String receptionistName;

    @ApiModelProperty(value = "接待日期")
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private String opendayDate;

    @ApiModelProperty(value = "联系方式")
    private String telephone;

    @ApiModelProperty(value = "接待行程")
    private String schedule;

    @ApiModelProperty(value = "是否是接待日（0：否，1：是）")
    private Integer isOpenday;

    @ApiModelProperty(value = "接待地址")
    private String address;

    @ApiModelProperty(value = "实际人数")
    private Integer actualAmount;

    @ApiModelProperty(value = "是否有总结（0：无，1：有）")
    private Integer isSummary;

    @ApiModelProperty(value = "总结")
    private String summary;

    @ApiModelProperty(value = "反馈内容")
    private String consultationContent;
}
