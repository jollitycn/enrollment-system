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
 * @description: 院系招生进度报表封装实体VO
 * @author: Mr.AI
 * @create: 2020-04-26
 **/

@Data
@ApiModel(value = "区域招生进度报表封装实体VO")
@EqualsAndHashCode(callSuper = true)
public class FacultyAdmissionsProgressVO extends BaseVO {

    @ApiModelProperty(value = "院系名称")
    private String collegeName;

    @ApiModelProperty(value = "计划招生人数")
    private Integer targetAmount;

    @ApiModelProperty(value = "实际招生人数")
    private Integer actualNumber;

    @ApiModelProperty(value = "院系Id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long collegeId;

}
