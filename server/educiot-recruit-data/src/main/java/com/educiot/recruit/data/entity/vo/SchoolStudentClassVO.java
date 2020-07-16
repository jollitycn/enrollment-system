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
 * @date 2020/4/30 14:25
 */
@Data
@ApiModel(value = "小程序注册学校班级学生信息返回实体VO")
@EqualsAndHashCode(callSuper = true)
public class SchoolStudentClassVO extends BaseVO {
    @ApiModelProperty(value = "招生学校ID", name = "recruitSchoolId")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long recruitSchoolId;

    @ApiModelProperty(value = "生源学校")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long sourceSchoolId;

    @ApiModelProperty(value = "生源学校名称")
    private String sourceSchoolName;

    @ApiModelProperty(value = "班级ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long classId;

    @ApiModelProperty(value = "班级名称")
    private String className;

    @ApiModelProperty(value = "学生基本信息ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long studentInfo;

    @ApiModelProperty(value = "学生姓名")
    private String studentName;

    @ApiModelProperty(value = "手机号")
    private String parentTelephone;
}
