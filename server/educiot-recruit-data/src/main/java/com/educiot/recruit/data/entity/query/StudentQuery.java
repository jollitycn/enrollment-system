package com.educiot.recruit.data.entity.query;


import com.educiot.recruit.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel(value = "查询学生信息，判断是否重复")
@EqualsAndHashCode(callSuper = true)
public class StudentQuery extends BaseQuery {

    @ApiModelProperty(value = "班级ID")
    private Long classId;

    @ApiModelProperty(value = "招生学校ID")
    private Long recruitSchoolId;

    @ApiModelProperty(value = "学生名称")
    private String studentName;

    @ApiModelProperty(value = "生源学校ID")
    private Long sourceSchoolId;
}


