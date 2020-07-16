package com.educiot.recruit.data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.educiot.recruit.common.constant.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 到校咨询表
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_consultation")
@ApiModel(value="Consultation对象", description="到校咨询表")
public class Consultation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "到校咨询ID")
    @TableId(value = "consultation_id", type = IdType.ID_WORKER)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long consultationId;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate endTime;

    @ApiModelProperty(value = "学期")
    private String semester;

    @ApiModelProperty(value = "日常接待人ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long dailyReceptionistId;

    @ApiModelProperty(value = "日常接待人名称")
    private String dailyReceptionistName;

    @ApiModelProperty(value = "联系方式")
    private String telephone;

    @ApiModelProperty(value = "接待行程")
    private String schedule;

    @ApiModelProperty(value = "接待地址")
    private String address;

    @ApiModelProperty(value = "创建人")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long creatorId;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "学期ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long semesterId;
}
