package com.educiot.recruit.data.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @program: server
 * @description: 新闻列表返回封装类
 * @author: XuChao
 * @create: 2020-04-20 17:47
 **/
@Data
public class NewsVO {

    @ApiModelProperty(value = "新闻ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long newsId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "信息类型")
    private String messageType;

    @ApiModelProperty(value = "信息类型名称")
    private String messageTypeName;

    @ApiModelProperty(value = "接收端（0：全部，1：学生，2：家长）")
    private Integer recevier;

    @ApiModelProperty(value = "所属计划（0：不限，1：已发布计划）")
    private Integer belongPlan;

    @ApiModelProperty(value = "消息内容")
    private String messageContent;

    @ApiModelProperty(value = "招生计划ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long planId;

    @ApiModelProperty(value = "招生计划名称")
    private String planName;

    @ApiModelProperty(value = "新闻状态（0：草稿，1：已发布）")
    private Integer newsStatus;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private LocalDateTime createTime;

}
