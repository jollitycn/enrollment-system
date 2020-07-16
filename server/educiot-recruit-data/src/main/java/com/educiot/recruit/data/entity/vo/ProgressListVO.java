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
 * @description：进度列表
 * @date Create in 2020/5/8
 */
@Data
@ApiModel(value = "进度列表返回封装实体VO")
@EqualsAndHashCode(callSuper = true)
public class ProgressListVO extends BaseVO {

    @ApiModelProperty(value = "学生报名进度ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long progressId;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = Constant.Sys.LOCALDATETIME_FORMATTER, timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "进度类型（0：报名登记，1：交费定位，2：接收通知，3：入学报名）")
    private Integer progressType;

    @ApiModelProperty(value = "进度内容")
    private String progressContent;

}
