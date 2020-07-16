package com.educiot.recruit.data.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.educiot.common.base.BaseVO;
import com.educiot.common.constant.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @program: server
 * @description: 招生专业返回信息封装实体类VO
 * @author: Mr.AI
 * @create: 2020-05-20
 **/
@Data
@ApiModel(value = "招生专业返回信息封装实体类VO")
@EqualsAndHashCode(callSuper = true)
public class MajorVO extends BaseVO {

    @ApiModelProperty(value = "招生专业ID")
    @TableId(value = "major_id", type = IdType.ID_WORKER)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long majorId;

    @ApiModelProperty(value = "院系ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long collegeId;

    @ApiModelProperty(value = "院系名称")
    private String collegeName;

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
    private BigDecimal fee;

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

    @ApiModelProperty(value = "已报名人数")
    private int amount;
}
