package com.educiot.recruit.data.entity.query.preach.register;

import com.educiot.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel(value = "查询学校宣讲报名")
@EqualsAndHashCode(callSuper = true)
public class PreachRegisterPageQuery extends BasePageQuery {

    @ApiModelProperty(value = "招生宣讲计划ID")
    private Long preachPlanId;
}
