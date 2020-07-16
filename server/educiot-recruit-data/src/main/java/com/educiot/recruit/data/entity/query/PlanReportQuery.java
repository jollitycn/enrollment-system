package com.educiot.recruit.data.entity.query;

import com.educiot.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel(value = "招生报表查询封装实体")
@EqualsAndHashCode(callSuper = true)
public class PlanReportQuery extends BaseQuery {

    private Long semesterId;

    private Long groupId;
}
