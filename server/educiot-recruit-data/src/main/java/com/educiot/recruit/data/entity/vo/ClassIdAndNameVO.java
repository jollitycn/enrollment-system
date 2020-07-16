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
 * @date 2020/4/29 17:34
 */
@Data
@ApiModel(value = "小程序注册页面班级信息返回实体VO")
@EqualsAndHashCode(callSuper = true)
public class ClassIdAndNameVO extends BaseVO {
    @ApiModelProperty(value = "班级ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long classId;

    @ApiModelProperty(value = "班级名称")
    private String className;
}
