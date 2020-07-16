package com.educiot.recruit.data.entity.query;

import com.educiot.recruit.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @program: server
 * @description: 修改计划的默认状态
 * @author: Mr.AI
 * @create: 2020-04-10
 **/

@Data
@ApiModel(value = "修改计划的默认状态")
@EqualsAndHashCode(callSuper = true)
public class UpdatePlanIsDefaultecQuery extends BaseQuery {

    @ApiModelProperty(value = "招生计划ID",name = "planId",required = true)
    @NotNull(message = "计划ID不能为空")
    private Long planId;

    @ApiModelProperty(value = "是否默认（0：否，1：是）",name = "isDefaulted" ,required = true)
    private Integer isDefaulted;
}
