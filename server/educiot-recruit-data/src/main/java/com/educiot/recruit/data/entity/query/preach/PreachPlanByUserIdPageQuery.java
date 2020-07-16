package com.educiot.recruit.data.entity.query.preach;

import com.educiot.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel(value = "获取宣讲反馈信息")
@EqualsAndHashCode(callSuper = true)
public class PreachPlanByUserIdPageQuery extends BasePageQuery {
//
//    @ApiModelProperty(value = "招生宣讲计划ID")
//    private Long preachPlanId;
}
