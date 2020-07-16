package com.educiot.recruit.data.entity.query.publicRelation;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.educiot.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel(value = "查询学校公关")
@EqualsAndHashCode(callSuper = true)
public class PublicRelationPageQuery extends BasePageQuery {
    /**
     * 查询条件：名称
     */
    @ApiModelProperty(value = "生源学校id", name = "sourceSchoolId",example = "",required = true)
    @JsonSerialize(using= ToStringSerializer.class )
    protected Long sourceSchoolId;

    private Long groupId;

    private  Long semesterId;


}
