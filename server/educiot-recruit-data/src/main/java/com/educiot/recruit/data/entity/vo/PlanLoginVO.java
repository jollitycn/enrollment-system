package com.educiot.recruit.data.entity.vo;

import com.educiot.common.base.BaseVO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: server
 * @description: 登录返回招生计划信息封装类
 * @author: Mr.AI
 * @create: 2020-05-28
 **/
@Data
public class PlanLoginVO extends BaseVO {

    @ApiModelProperty(value = "招生计划ID",name = "planId")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long planId;

    @ApiModelProperty(value = "招生学校ID",name = "recruitSchoolId")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long recruitSchoolId;

    @ApiModelProperty(value = "学期",name = "semester")
    private String semester;

    @ApiModelProperty(value = "招生负责人ID",name = "chargerId")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long chargerId;

    @ApiModelProperty(value = "招生负责人名称",name = "chargerName")
    private String chargerName;

    @ApiModelProperty(value = "学期Id")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long semesterId;

}
