package com.educiot.recruit.data.entity.vo.publicRelation;

import com.educiot.common.base.BaseVO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel(value = "学校公关分组信息类")
@EqualsAndHashCode()
public class PublicRelationGroupCountVO extends BaseVO {

//    @ApiModelProperty(value = "学校公关ID")
//    @JsonSerialize(using = ToStringSerializer.class)
//    private Long publicRelation;

    @ApiModelProperty(value = "生源学校名称")
    private String sourceSchoolName;

    /**
     * 查询条件：名称
     */
    @ApiModelProperty(value = "生源学校id", name = "sourceSchoolId", example = "", required = true)
    @JsonSerialize(using = ToStringSerializer.class)
    protected Long sourceSchoolId;

    @ApiModelProperty(value = "访问次数")
    private Integer amount;
}
