package com.educiot.recruit.data.entity.query;

import com.educiot.common.base.BaseQuery;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: server
 * @description: 学生家庭信息添加入参封装实体
 * @author: Mr.AI
 * @create: 2020-04-27
 **/

@Data
@ApiModel(value = "学生家庭信息添加入参封装实体")
@EqualsAndHashCode(callSuper = true)
public class AddFamilyMemberQuery extends BaseQuery {

    @ApiModelProperty(value = "家庭成员ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long familyMemberId;

    @ApiModelProperty(value = "学生基本信息ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long studentInfo;

    @ApiModelProperty(value = "关系（0：父亲，1：母亲）")
    private Integer relation;

    @ApiModelProperty(value = "家长姓名")
    private String name;

    @ApiModelProperty(value = "工作单位")
    private String company;

    @ApiModelProperty(value = "职务")
    private String position;

    @ApiModelProperty(value = "联系方式")
    private String telephone;

    @ApiModelProperty(value = "户籍所在地")
    private String location;

}
