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
 * @description: 招生学校返回封装类
 * @author: Mr.AI
 * @create: 2020-04-14
 **/

@Data
@ApiModel(value = "招生计划信息返回封装类")
@EqualsAndHashCode(callSuper = true)
public class RecruitSchoolVO extends BaseVO {

    @ApiModelProperty(value = "招生学校ID",name = "recruitSchoolId")
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

    @ApiModelProperty(value = "平台招生学校ID" ,name = "apiId")
    private Long apiId;


}
