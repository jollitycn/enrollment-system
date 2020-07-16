package com.educiot.recruit.data.entity.query;

import com.educiot.recruit.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: server
 * @description: 招生简介信息入参实体类
 * @author: Mr.AI
 * @create: 2020-04-13
 **/

@Data
@ApiModel(value = "招生简介信息入参实体类")
@EqualsAndHashCode(callSuper = true)
public class AddPlanRecruitInfoQuery extends BaseQuery {

    @ApiModelProperty(value = "招生计划ID",name = "planId",required = true)
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
