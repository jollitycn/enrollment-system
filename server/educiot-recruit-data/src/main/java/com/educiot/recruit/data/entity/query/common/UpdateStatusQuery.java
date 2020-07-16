package com.educiot.recruit.data.entity.query.common;

import com.educiot.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel(value = "通用修改状态")
@EqualsAndHashCode(callSuper = true)
public class UpdateStatusQuery extends BaseQuery {

    @ApiModelProperty(value = "通用ID", required = true)
    private Long Id;

    @ApiModelProperty(value = "状态值")
    private Integer status;
}
