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
 * @description: 学生对应招生负责人返回封装VO
 * @author: Mr.AI
 * @create: 2020-04-27
 **/

@Data
@ApiModel(value = "学生对应招生负责人返回封装VO")
@EqualsAndHashCode(callSuper = true)
public class StudentChargerVO extends BaseVO {

    @ApiModelProperty(value = "招生负责人名称")
    private String name;

    @ApiModelProperty(value = "招生负责人联系方式")
    private String telephone;

    @ApiModelProperty(value = "招生负责人Id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long chargerId;
}
