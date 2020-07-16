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
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 招生计划表 
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_plan")
@ApiModel(value="Plan对象", description="招生计划表 ")
public class Plan implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "招生计划ID")
    @TableId(value = "plan_id", type = IdType.ID_WORKER)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long planId;

    @ApiModelProperty(value = "招生学校ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long recruitSchoolId;

    @ApiModelProperty(value = "季度")
    private Integer season;

    @ApiModelProperty(value = "学期")
    private String semester;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "招生开始时间")
    @JsonFormat(pattern = Constant.Sys.LOCALDATETIME_FORMATTER, timezone = "GMT+8")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "招生结束时间")
    @JsonFormat(pattern = Constant.Sys.LOCALDATETIME_FORMATTER, timezone = "GMT+8")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "招生负责人ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long chargerId;

    @ApiModelProperty(value = "招生负责人名称")
    private String chargerName;

    @ApiModelProperty(value = "发布人")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long publisherId;

    @ApiModelProperty(value = "发布人名称")
    private String publisherName;

    @ApiModelProperty(value = "发布时间")
    @JsonFormat(pattern = Constant.Sys.LOCALDATETIME_FORMATTER, timezone = "GMT+8")
    private LocalDateTime publishTime;

    @ApiModelProperty(value = "状态（0：删除，1：未发布，2：已发布）")
    private Integer status;

    @ApiModelProperty(value = "是否默认（0：否，1：是）")
    private Integer isDefaulted;

    @ApiModelProperty(value = "说明")
    private String comment;

    @ApiModelProperty(value = "创建人")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long creatorId;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = Constant.Sys.LOCALDATETIME_FORMATTER, timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "联系电话")
    private String telephoneNumber;

    @ApiModelProperty(value = "修改人id")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long modifierId;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern = Constant.Sys.LOCALDATETIME_FORMATTER, timezone = "GMT+8")
    private LocalDateTime modifyTime;

    @ApiModelProperty(value = "拷贝ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long copyId;

    @ApiModelProperty(value = "学期ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long semesterId;

    @ApiModelProperty(value = "学期开始使劲")
    private LocalDate semesterStart;

    @ApiModelProperty(value = "学期结束时间")
    private LocalDate semesterEnd;
}
