package com.educiot.recruit.data.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.educiot.recruit.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/4/15 16:58
 */
@Data
@ApiModel(value = "生源学校信息返回实体VO")
@EqualsAndHashCode(callSuper = true)
public class SourceSchoolVO extends BaseVO {
    @ApiModelProperty(value = "生源学校")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long sourceSchoolId;

    @ApiModelProperty(value = "生源学校名称")
    private String sourceSchoolName;
}
