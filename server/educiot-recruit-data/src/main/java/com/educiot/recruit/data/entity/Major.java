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
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 招生专业表
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_major")
@ApiModel(value="Major对象", description="招生专业表")
public class Major implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "招生专业ID")
    @TableId(value = "major_id", type = IdType.ID_WORKER)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long majorId;

    @ApiModelProperty(value = "院系ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long collegeId;

    @ApiModelProperty(value = "接口ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long apiId;

    @ApiModelProperty(value = "专业名称")
    private String majorName;

    @ApiModelProperty(value = "专业名称")
    private String apiMajorName;

    @ApiModelProperty(value = "招生人数")
    private Integer recruitAmount;

    @ApiModelProperty(value = "学费（元/学期）")
    private float fee;

    @ApiModelProperty(value = "学制")
    private String majorType;

    @ApiModelProperty(value = "培养目标")
    private String cultivateAim;

    @ApiModelProperty(value = "主要课程")
    private String mainCourse;

    @ApiModelProperty(value = "就业方向")
    private String careerForward;

    @ApiModelProperty(value = "专业简介")
    private String comment;

    @ApiModelProperty(value = "创建人")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long creatorId;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = Constant.Sys.LOCALDATETIME_FORMATTER, timezone = "GMT+8")
    private LocalDateTime createTime;


}
