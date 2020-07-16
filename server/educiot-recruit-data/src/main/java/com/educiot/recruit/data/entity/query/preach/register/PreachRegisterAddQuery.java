package com.educiot.recruit.data.entity.query.preach.register;

import com.educiot.recruit.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@ApiModel(value = "新增学校宣讲报名")
@EqualsAndHashCode(callSuper = true)
public class PreachRegisterAddQuery extends BaseQuery {

    @ApiModelProperty(value = "招生宣讲计划ID")
    private Long preachPlanId;

    @ApiModelProperty(value = "学生公关id")
    private Long studentPublicRelationId;
}
