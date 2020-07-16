package com.educiot.recruit.data.entity.vo;

import com.educiot.recruit.common.base.BaseVO;
import com.educiot.recruit.common.constant.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author liuhao
 * @program educiot-recruit
 * @description：学生咨询登记信息
 * @date Create in 2020/5/11
 */
@Data
@ApiModel(value = "咨询登记列表返回实体VO")
@EqualsAndHashCode(callSuper = true)
public class StudentConsultationListVO extends BaseVO {

    @ApiModelProperty(value = "招生咨询登记ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long consultationRegisterId;

    @ApiModelProperty(value = "咨询日期")
    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private LocalDate consultationDate;

    @ApiModelProperty(value = "联系方式")
    private String telephone;

    @ApiModelProperty(value = "到校时间")
    @JsonFormat(pattern = Constant.Sys.LOCALDATETIME_FORMATTER, timezone = "GMT+8")
    private LocalDateTime attendTime;

    @ApiModelProperty(value = "是否用餐（0：是，1：否）")
    private Integer isLunch;

    @ApiModelProperty(value = "说明")
    private String comment;
}
