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
 * @date 2020/4/15 16:58
 */
@Data
@ApiModel(value = "负责人信息返回实体VO")
@EqualsAndHashCode(callSuper = true)
public class ChargerVO extends BaseVO {
    @ApiModelProperty(value = "负责人ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long chargerId;

    @ApiModelProperty(value = "接口ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long apiId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "手机号")
    private String telephone;

    @ApiModelProperty(value = "是否是组长（0：否，1：是）")
    private Integer isLeader;
}
