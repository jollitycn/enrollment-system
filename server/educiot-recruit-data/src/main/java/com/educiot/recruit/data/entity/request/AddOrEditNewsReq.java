package com.educiot.recruit.data.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: server
 * @description: 添加或编辑新闻参数封装类
 * @author: XuChao
 * @create: 2020-04-21 14:17
 **/
@Data
public class AddOrEditNewsReq {

    @ApiModelProperty("新闻主键ID")
    private Long newsId;

    @ApiModelProperty(value = "新闻标题",required = true)
    private String title;

    @ApiModelProperty("信息类型")
    private String messageType;

    @ApiModelProperty("接收端（0：全部，1：学生，2：家长）")
    private Integer recevier;

    @ApiModelProperty("所属计划（0：不限）")
    private Integer belongPlan;

    @ApiModelProperty("'招生计划ID'")
    private Long planId;

    @ApiModelProperty("新闻内容")
    private String messageContent;

    @ApiModelProperty("新闻状态（0：草稿，1：已发布）")
    private Integer newsStatus;
}
