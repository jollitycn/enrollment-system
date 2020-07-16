package com.educiot.recruit.data.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.educiot.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: server
 * @description: 招生简介返回封装类
 * @author: Mr.AI
 * @create: 2020-04-14
 **/

@Data
@ApiModel(value = "招生分组信息返回实体VO")
@EqualsAndHashCode(callSuper = true)
public class PlanRecruitInfoVO extends BaseVO {

    @ApiModelProperty(value = "招生简介ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long planRecruitInfoId;

    @ApiModelProperty(value = "招生计划ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long planId;

    @ApiModelProperty(value = "招生对象")
    private String recruitTarget;

    @ApiModelProperty(value = "注意事项")
    private String attention;

    @ApiModelProperty(value = "报到和录取")
    private String register;

    @ApiModelProperty(value = "招生计划")
    private String planDescription;

    @ApiModelProperty(value = "招生简章图片")
    private String recruitmentImage;
}
