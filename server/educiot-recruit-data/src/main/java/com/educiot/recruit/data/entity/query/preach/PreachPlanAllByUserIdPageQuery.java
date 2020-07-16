package com.educiot.recruit.data.entity.query.preach;

import com.educiot.common.base.BasePageQuery;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel(value = "获取当前负责人相关的宣讲计划")
@EqualsAndHashCode(callSuper = true)
public class PreachPlanAllByUserIdPageQuery extends BasePageQuery {

//    @ApiModelProperty(value = "招生宣讲计划ID")
//    private Long preachPlanId;
}
