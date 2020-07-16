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
 * 新闻表
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_news")
@ApiModel(value="News对象", description="新闻表")
public class News implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "新闻ID")
    @TableId(value = "news_id", type = IdType.ID_WORKER)
    private Long newsId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "信息类型")
    private String messageType;

    @ApiModelProperty(value = "接收端（0：全部，1：学生，2：家长）")
    private Integer recevier;

    @ApiModelProperty(value = "所属计划（0：不限，1：已发布计划）")
    private Integer belongPlan;

    @ApiModelProperty(value = "消息内容")
    private String messageContent;

    @ApiModelProperty(value = "招生学校ID")
    private Long recruitSchoolId;

    @ApiModelProperty(value = "招生计划ID")
    private Long planId;

    @ApiModelProperty(value = "新闻状态（0：草稿，1：已发布）")
    private Integer newsStatus;

    @ApiModelProperty(value = "创建人")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long creatorId;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = Constant.Sys.LOCALDATETIME_FORMATTER, timezone = "GMT+8")
    private LocalDateTime createTime;
}
