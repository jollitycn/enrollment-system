package com.educiot.recruit.data.entity.vo;

import com.educiot.common.base.BaseVO;
import com.educiot.common.constant.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author liuhao
 * @program educiot-recruit
 * @description：咨询签到列表
 * @date Create in 2020/4/16
 */
@Data
@ApiModel(value = "咨询签到列表返回实体VO")
@EqualsAndHashCode(callSuper = true)
public class AttendListVO extends BaseVO {

    @ApiModelProperty(value = "招生咨询登记ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long consultationRegisterId;

    @ApiModelProperty(value = "咨询人ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long attenderId;

    @ApiModelProperty(value = "咨询类型（0：个人，1：学校）")
    private Integer consultationType;

    @ApiModelProperty(value = "咨询人姓名")
    private String attenderName;

    @ApiModelProperty(value = "到校时间")
    @JsonFormat(pattern = Constant.Sys.LOCALDATETIME_FORMATTER, timezone = "GMT+8")
    private LocalDateTime attendTime;

    @ApiModelProperty(value = "联系方式")
    private String telephone;

    @ApiModelProperty(value = "是否用餐（0：是，1：否）")
    private Integer isLunch;

    @ApiModelProperty(value = "是否签到（0：否，1：是）")
    private Integer isAttend;

    @ApiModelProperty(value = "说明")
    private String comment;
}
