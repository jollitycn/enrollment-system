package com.educiot.recruit.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.educiot.recruit.common.constant.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 招生计划学校简介
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_plan_school_introduction")
@ApiModel(value="PlanSchoolIntroduction对象", description="招生计划学校简介")
public class PlanSchoolIntroduction implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "招生计划学校简介ID")
    @TableId(value = "plan_school_introduction_id", type = IdType.ID_WORKER)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long planSchoolIntroductionId;

    @ApiModelProperty(value = "招生学校名称")
    private String recruitSchoolName;

    @ApiModelProperty(value = "招生学校logo")
    private String recruitSchoolLogo;

    @ApiModelProperty(value = "是否删除（0：删除，1：正常）")
    private Integer isDeleted;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = Constant.Sys.LOCALDATETIME_FORMATTER, timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "校长名称")
    private String principal;

    @ApiModelProperty(value = "联系电话")
    private String telephoneNumber;

    @ApiModelProperty(value = "学校地址")
    private String address;

    @ApiModelProperty(value = "简介")
    private String description;

    @ApiModelProperty(value = "接口ID")
    private Long apiId;

    @ApiModelProperty(value = "招生计划ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long planId;


}
