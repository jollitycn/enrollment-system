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
 * 学生招生渠道表
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_recruit_way")
@ApiModel(value="RecruitWay对象", description="学生招生渠道表")
public class RecruitWay implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学生招生渠道ID")
    @TableId(value = "recruit_way_id", type = IdType.ID_WORKER)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long recruitWayId;

    @ApiModelProperty(value = "学生公关ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long studentPublicRelationId;

    @ApiModelProperty(value = "渠道类型（0：线上，1：线下）")
    private Integer recruitType;

    @ApiModelProperty(value = "线上渠道方式（0：网站招生，1：广告招生，2：朋友介绍）")
    private String onlineType;

    @ApiModelProperty(value = "推荐人名称")
    private String recommendName;

    @ApiModelProperty(value = "联系方式")
    private String telephone;

    @ApiModelProperty(value = "创建人")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long creatorId;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = Constant.Sys.LOCALDATETIME_FORMATTER, timezone = "GMT+8")
    private LocalDateTime createTime;


}
