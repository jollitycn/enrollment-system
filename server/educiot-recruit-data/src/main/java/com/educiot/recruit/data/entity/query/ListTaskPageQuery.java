package com.educiot.recruit.data.entity.query;

import com.educiot.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liuhao
 * @program educiot-recruit
 * @description：任务列表
 * @date Create in 2020/5/7
 */
@Data
@ApiModel(value = "任务列表入参实体")
@EqualsAndHashCode(callSuper = true)
public class ListTaskPageQuery extends BasePageQuery {

    @ApiModelProperty(value = "状态（0：未完成，1：完成）", required = true)
    private Integer status;
}
