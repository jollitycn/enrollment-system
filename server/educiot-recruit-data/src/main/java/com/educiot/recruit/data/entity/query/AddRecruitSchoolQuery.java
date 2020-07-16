package com.educiot.recruit.data.entity.query;

import com.educiot.recruit.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: server
 * @description: 招生学校入参实体
 * @author: Mr.AI
 * @create: 2020-04-10
 **/

@Data
@ApiModel(value = "招生学校入参实体")
@EqualsAndHashCode(callSuper = true)
public class AddRecruitSchoolQuery extends BaseQuery {

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

    @ApiModelProperty(value = "接口ID",name = "apiId")
    private Long apiId;

}
