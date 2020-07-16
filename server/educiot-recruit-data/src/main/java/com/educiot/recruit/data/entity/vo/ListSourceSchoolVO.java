package com.educiot.recruit.data.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.educiot.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/4/10 15:24
 */
@Data
@ApiModel(value = "生源学校列表信息表返回实体VO")
@EqualsAndHashCode(callSuper = true)
public class ListSourceSchoolVO extends BaseVO {
    @ApiModelProperty(value = "生源学校")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long sourceSchoolId;

    @ApiModelProperty(value = "生源学校名称")
    private String sourceSchoolName;

    @ApiModelProperty(value = "所在省份")
    private String province;

    @ApiModelProperty(value = "所在城市")
    private String city;

    @ApiModelProperty(value = "所在区域")
    private String district;
}
