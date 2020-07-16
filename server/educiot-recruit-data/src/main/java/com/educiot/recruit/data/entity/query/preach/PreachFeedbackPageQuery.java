package com.educiot.recruit.data.entity.query.preach;

import com.educiot.recruit.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel(value = "获取宣讲反馈信息")
@EqualsAndHashCode(callSuper = true)
public class PreachFeedbackPageQuery extends BasePageQuery {

    @ApiModelProperty(value = "招生宣讲计划ID" , required = true)
    private Long preachPlanId;
}
