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
 * @description: 区域招生进度报表封装实体VO
 * @author: Mr.AI
 * @create: 2020-04-26
 **/

@Data
@ApiModel(value = "区域招生进度报表封装实体VO")
@EqualsAndHashCode(callSuper = true)
public class RegionalAdmissionsProgressVO extends BaseVO {

    @ApiModelProperty(value = "分组ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long groupId;

    @ApiModelProperty(value = "组别名称")
    private String groupName;

    @ApiModelProperty(value = "计划招生人数")
    private Integer targetAmount;

    @ApiModelProperty(value = "实际招生人数")
    private Integer actualNumber;

}
