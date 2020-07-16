package com.educiot.recruit.data.entity.query;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.educiot.recruit.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @program: server
 * @description: 修改计划的发布状态
 * @author: Mr.AI
 * @create: 2020-04-10
 **/

@Data
@ApiModel(value = "修改计划的发布状态")
@EqualsAndHashCode(callSuper = true)
public class UpdatePlanQuery extends BaseQuery {

    @ApiModelProperty(value = "招生计划ID",name = "planId",required = true)
    @NotNull(message = "计划ID不能为空")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long planId;

    @ApiModelProperty(value = "状态（0：删除，1：未发布，2：已发布，3:已完成）",name = "status" ,required = true)
    private Integer status;

    @ApiModelProperty(value = "发布人")
    private Long publisherId;

    @ApiModelProperty(value = "发布人名称")
    private String publisherName;

    @ApiModelProperty(value = "修改人id")
    private Long modifierId;


}
