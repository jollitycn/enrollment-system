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
 * @description: 修改招生学校信息入参实体
 * @author: Mr.AI
 * @create: 2020-04-13
 **/

@Data
@ApiModel(value = "修改招生学校信息入参实体")
@EqualsAndHashCode(callSuper = true)
public class UpdateRecruitSchoolQuery extends BaseQuery {

    @ApiModelProperty(value = "招生学校ID")
    @NotNull(message = "招生学校ID不能为空")
    @Min(value = 1L, message = "招生学校ID最小为1")
    @Max(value = Long.MAX_VALUE, message = "招生学校ID超出最大限制")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long recruitSchoolId;

    @ApiModelProperty(value = "招生学校名称", name="recruitSchoolName")
    private String recruitSchoolName;

    @ApiModelProperty(value = "招生学校logo",name = "recruitSchoolLogo")
    private String recruitSchoolLogo;

    @ApiModelProperty(value = "校长名称" ,name = "principal")
    private String principal;

    @ApiModelProperty(value = "联系电话" ,name = "telephoneNumber")
    private String telephoneNumber;

    @ApiModelProperty(value = "学校地址" ,name="address")
    private String address;

    @ApiModelProperty(value = "简介" ,name = "description")
    private String description;

}
