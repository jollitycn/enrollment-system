package com.educiot.recruit.data.entity.query;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.educiot.recruit.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: server
 * @description: 分页查询学生基本信息入参实体
 * @author: Mr.AI
 * @create: 2020-04-22
 **/

@Data
@ApiModel(value = "分页查询学生基本信息入参实体")
@EqualsAndHashCode(callSuper = true)
public class ListStudentPageQuery extends BasePageQuery {

    @ApiModelProperty(value = "班级Id")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long classId;

    @ApiModelProperty(value = "生源学校ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long sourceSchoolId;


    @ApiModelProperty(value = "学生姓名")
    private String studentName;


    @ApiModelProperty(value = "报名状态（0：已交订金，1：已交全款，2：已退款，3：强意向生）")
    private Integer registerStatus;

    @ApiModelProperty(value = "招生组Id")
    private Long groupId;

    @ApiModelProperty(value = "学期Id")
    private Long semesterId;

}
