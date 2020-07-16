package com.educiot.recruit.data.entity.query;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.educiot.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @program: server
 * @description: 编辑院系信息入参实体类
 * @author: Mr.AI
 * @create: 2020-04-14
 **/

@Data
@ApiModel(value = "编辑院系信息入参实体类")
@EqualsAndHashCode(callSuper = true)
public class UpdateCollegeQuery extends BaseQuery {

    @ApiModelProperty(value = "院系ID")
    @NotNull(message = "招生院系ID不能为空")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long collegeId;

    @ApiModelProperty(value = "院系名称")
    private String collegeName;

    @ApiModelProperty(value = "院系负责人")
    private String collegeManager;

    @ApiModelProperty(value = "招生人数")
    private Integer recruitAmount;

    @ApiModelProperty(value = "招生对象")
    private String recruitTarget;

    @ApiModelProperty(value = "培养目标")
    private String cultivateAim;

    @ApiModelProperty(value = "主要课程")
    private String mainCourse;

    @ApiModelProperty(value = "就业方向")
    private String careerForward;

    @ApiModelProperty(value = "院系简介")
    private String comment;


}
