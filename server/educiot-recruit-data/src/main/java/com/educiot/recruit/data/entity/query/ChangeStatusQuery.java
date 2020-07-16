package com.educiot.recruit.data.entity.query;

import com.educiot.common.base.BaseQuery;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liuhao
 * @program educiot-recruit
 * @description：状态更改
 * @date Create in 2020/5/8
 */
@Data
@ApiModel(value = "状态更改入参实体")
@EqualsAndHashCode(callSuper = true)
public class ChangeStatusQuery extends BaseQuery {

    @ApiModelProperty(value = "学生公关ID", required = true)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long studentPublicRelationId;

    @ApiModelProperty(value = "状态（0：已交订金，1：已交全款，2：已退款，3：强意向生，4：已报名）", required = true)
    private Integer status;
}
