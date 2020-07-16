package com.educiot.recruit.data.entity.query;

import com.educiot.recruit.common.base.BasePageQuery;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
public class ListStudentPageByClassIdQuery extends BasePageQuery {

    @ApiModelProperty(value = "班级Id")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long classId;

}
