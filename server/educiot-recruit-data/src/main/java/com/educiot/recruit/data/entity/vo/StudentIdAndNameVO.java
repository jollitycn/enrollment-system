package com.educiot.recruit.data.entity.vo;

import com.educiot.recruit.common.base.BaseVO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/4/29 10:02
 */
@Data
@ApiModel(value = "意向生id和姓名返回实体VO")
@EqualsAndHashCode(callSuper = true)
public class StudentIdAndNameVO extends BaseVO {
    @ApiModelProperty(value = "学生基本信息ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long studentInfo;

    @ApiModelProperty(value = "学生姓名")
    private String studentName;

    @ApiModelProperty(value = "登录id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long login;
}
