package com.educiot.recruit.data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 招生简介表
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_plan_recruit_info")
@ApiModel(value="PlanRecruitInfo对象", description="招生简介表")
public class PlanRecruitInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "招生简介ID")
    @TableId(value = "plan_recruit_info_id", type = IdType.ID_WORKER)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long planRecruitInfoId;

    @ApiModelProperty(value = "招生计划ID")
    private Long planId;

    @ApiModelProperty(value = "招生对象")
    private String recruitTarget;

    @ApiModelProperty(value = "注意事项")
    private String attention;

    @ApiModelProperty(value = "报到和录取")
    private String register;

    @ApiModelProperty(value = "招生计划")
    private String planDescription;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    private Long creatorId;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

    @ApiModelProperty(value = "是否删除（0：删除，1：正常）")
    private Integer isDeleted;

    @ApiModelProperty(value = "招生简章图片")
    private String recruitmentImage;
}
