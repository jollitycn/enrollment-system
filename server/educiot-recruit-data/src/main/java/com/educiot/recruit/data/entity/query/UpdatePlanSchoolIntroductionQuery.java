package com.educiot.recruit.data.entity.query;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.educiot.recruit.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @program: server
 * @description: 编辑计划招生学校入参实体类
 * @author: Mr.AI
 * @create: 2020-04-15
 **/
@Data
@ApiModel(value = "编辑计划招生学校入参实体类")
@EqualsAndHashCode(callSuper = true)
public class UpdatePlanSchoolIntroductionQuery extends BaseQuery {

    @ApiModelProperty(value = "招生计划学校ID")
    @NotNull(message = "招生计划学校ID不能为空")
    @Min(value = 1L, message = "招生计划学校ID最小为1")
    @Max(value = Long.MAX_VALUE, message = "招生计划学校ID超出最大限制")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long planSchoolIntroductionId;

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


}
