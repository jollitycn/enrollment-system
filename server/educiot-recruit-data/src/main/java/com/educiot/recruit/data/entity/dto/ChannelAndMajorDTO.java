package com.educiot.recruit.data.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: server
 * @description: 渠道和专业参数集合封装类
 * @author: XuChao
 * @create: 2020-04-14 16:14
 **/
@Data
public class ChannelAndMajorDTO {

    @ApiModelProperty(value = "节点级别(1,2,3)", required = true)
    private Integer level;

    @ApiModelProperty(value = "父节点ID")
    private Long parentId;

    @ApiModelProperty(value = "类型（0：渠道，1：专业）")
    private Integer type;

    @ApiModelProperty(value = "节点ID")
    private String nodeId;

    @ApiModelProperty(value = "节点名称")
    private String nodeName;

    @ApiModelProperty(value = "目标数量")
    private Integer targetAmount;

    @ApiModelProperty(value = "说明")
    private String comment;

}
