package com.educiot.recruit.data.entity.query;

import com.educiot.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: server
 * @description: 招生计划学校简介入参实体类
 * @author: Mr.AI
 * @create: 2020-04-15
 **/

@Data
@ApiModel(value = "招生学校入参实体")
@EqualsAndHashCode(callSuper = true)
public class AddPlanSchoolIntroductionQuery extends BaseQuery{

    @ApiModelProperty(value = "招生学校名称")
    private String recruitSchoolName;

    @ApiModelProperty(value = "招生学校logo")
    private String recruitSchoolLogo;

    @ApiModelProperty(value = "校长名称")
    private String principal;

    @ApiModelProperty(value = "联系电话")
    private String telephoneNumber;

    @ApiModelProperty(value = "学校地址")
    private String address;

    @ApiModelProperty(value = "简介")
    private String description;

    @ApiModelProperty(value = "接口ID")
    private Long apiId;

    @ApiModelProperty(value = "招生计划ID")
    private Long planId;

}
