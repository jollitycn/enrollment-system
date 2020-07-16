package com.educiot.recruit.data.entity.vo;


import com.educiot.recruit.common.base.BaseVO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: server
 * @description: APP报表查询统计数据分析
 * @author: Mr.AI
 * @create: 2020-06-30
 **/

@Data
@ApiModel(value = "招生计划信息返回封装类")
@EqualsAndHashCode(callSuper = true)
public class PlanReportVO extends BaseVO {

    @ApiModelProperty(value = "招生计划ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long planId;

    @ApiModelProperty(value = "招生学校ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long recruitSchoolId;

    @ApiModelProperty(value = "学期")
    private String semester;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "招生负责人ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long chargerId;

    @ApiModelProperty(value = "招生负责人名称")
    private String chargerName;

    @ApiModelProperty(value = "状态（0：删除，1：未发布，2：已发布）")
    private Integer status;

    @ApiModelProperty(value = "学期ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long semesterId;

    @ApiModelProperty(value = "分组ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long groupId;

}
