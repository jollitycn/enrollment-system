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
 * @description: 编辑招生专业信息入参实体类
 * @author: Mr.AI
 * @create: 2020-04-14
 **/

@Data
@ApiModel(value = "编辑招生专业信息入参实体类")
@EqualsAndHashCode(callSuper = true)
public class UpdateMajorInfoQuery extends BaseQuery {

    @ApiModelProperty(value = "招生专业ID")
    @NotNull(message = "招生专业ID不能为空")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long majorId;

    @ApiModelProperty(value = "院系ID")
    private Long collegeId;

    @ApiModelProperty(value = "接口ID")
    private Long apiId;

    @ApiModelProperty(value = "专业名称")
    private String majorName;

    @ApiModelProperty(value = "专业名称")
    private String apiMajorName;

    @ApiModelProperty(value = "招生人数")
    private Integer recruitAmount;

    @ApiModelProperty(value = "学费（元/学期）")
    private float fee;

    @ApiModelProperty(value = "学制")
    private String majorType;

    @ApiModelProperty(value = "培养目标")
    private String cultivateAim;

    @ApiModelProperty(value = "主要课程")
    private String mainCourse;

    @ApiModelProperty(value = "就业方向")
    private String careerForward;

    @ApiModelProperty(value = "专业简介")
    private String comment;

}
