package com.educiot.recruit.data.entity.query;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.educiot.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: server
 * @description: 添加招生专业入参实体类
 * @author: Mr.AI
 * @create: 2020-04-14
 **/

@Data
@ApiModel(value = "招生计划信息列表入参实体")
@EqualsAndHashCode(callSuper = true)
public class AddMajorInfoQuery extends BaseQuery {

    @ApiModelProperty(value = "院系ID",name = "collegeId" ,required = true)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long collegeId;

    @ApiModelProperty(value = "接口ID",name = "apiId" ,required = true)
    @JsonSerialize(using= ToStringSerializer.class)
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

    @ApiModelProperty(value = "创建人")
    private Long creatorId;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

}
