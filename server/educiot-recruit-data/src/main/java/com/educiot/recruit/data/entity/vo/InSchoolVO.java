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
 * @program: server
 * @description: 学生在校信息封装实体VO
 * @author: Mr.AI
 * @create: 2020-04-21
 **/

@Data
@ApiModel(value = "招生分组信息返回实体VO")
@EqualsAndHashCode(callSuper = true)
public class InSchoolVO extends BaseVO {

    @ApiModelProperty(value = "学生在校信息ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long inSchoolId;

    @ApiModelProperty(value = "学生基本信息ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long studentInfo;

    @ApiModelProperty(value = "原学籍号")
    private String schoolNo;

    @ApiModelProperty(value = "特长")
    private String talent;

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

    @ApiModelProperty(value = "学生最近在校成绩")
    private List<Score> scores;

    @ApiModelProperty(value = "学生公关ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long studentPublicRelationId;

}
