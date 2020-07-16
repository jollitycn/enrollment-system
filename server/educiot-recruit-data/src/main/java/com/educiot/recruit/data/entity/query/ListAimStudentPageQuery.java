package com.educiot.recruit.data.entity.query;

import com.educiot.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: server
 * @description: 目标生分页查询列表封装参数
 * @author: Mr.AI
 * @create: 2020-04-23
 **/

@Data
@ApiModel(value = "分页查询计划列表入参实体")
@EqualsAndHashCode(callSuper = true)
public class ListAimStudentPageQuery extends BasePageQuery {

    @ApiModelProperty("班级Id")
    private Long classId;

    @ApiModelProperty("学生姓名")
    private String name;

    @ApiModelProperty("创建人ID")
    private Long creatorId;

    @ApiModelProperty("学期ID")
    private Long semesterId;

}
