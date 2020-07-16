package com.educiot.recruit.data.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.educiot.recruit.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @program: server
 * @description: 查询院系下所有的专业封装实体类VO
 * @author: Mr.AI
 * @create: 2020-04-20
 **/

@Data
@ApiModel(value = "查询院系下所有的专业封装实体类VO")
@EqualsAndHashCode(callSuper = true)
public class CollegeAndMajorVO extends BaseVO {

    @ApiModelProperty(value = "院系ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long collegeId;

    @ApiModelProperty(value = "接口ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long apiId;

    @ApiModelProperty(value = "院系名称")
    private String collegeName;

    @ApiModelProperty(value = "院系下所有的专业")
    private List<MajorVO> majors;
}
