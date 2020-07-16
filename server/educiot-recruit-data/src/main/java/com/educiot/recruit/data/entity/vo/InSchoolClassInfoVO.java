package com.educiot.recruit.data.entity.vo;

import com.educiot.recruit.common.base.BaseVO;
import com.educiot.recruit.data.entity.Score;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author liuhao
 * @program educiot-recruit
 * @description：所属学校信息
 * @date Create in 2020/7/2
 */
@Data
@ApiModel(value = "所属学校信息VO")
@EqualsAndHashCode(callSuper = true)
public class InSchoolClassInfoVO extends BaseVO {

    @ApiModelProperty(value = "生源学校名称")
    private String sourceSchoolName;

    @ApiModelProperty(value = "生源学校Id")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long sourceSchoolId;

    @ApiModelProperty(value = "班级名称")
    private String className;

    @ApiModelProperty(value = "班级id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long classId;

    @ApiModelProperty(value = "班主任")
    private String adviser;

    @ApiModelProperty(value = "学生公关ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long studentPublicRelationId;
}
