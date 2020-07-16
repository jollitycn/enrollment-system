package com.educiot.recruit.data.entity.vo;

import com.educiot.recruit.common.base.BaseVO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/6/30 17:20
 */
@Data
@ApiModel("学生和招生学校信息VO")
@EqualsAndHashCode(callSuper = true)
public class SelectStudentAndRecruitVO extends BaseVO {
    @ApiModelProperty(value = "招生学校ID", name = "recruitSchoolId")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long recruitSchoolId;

    @ApiModelProperty(value = "获取openID", name = "openid", required = true)
    private String openid;

    @ApiModelProperty(value = "获取openID", name = "openid", required = true)
    private List<StudentIdAndNameVO> studentList;
}
