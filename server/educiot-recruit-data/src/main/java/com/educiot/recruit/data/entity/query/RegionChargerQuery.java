package com.educiot.recruit.data.entity.query;

import com.educiot.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: server
 * @description: 招生报表时间查询条件封装实体
 * @author: Mr.AI
 * @create: 2020-05-09
 **/
@Data
@ApiModel(value = "招生报表时间查询条件封装实体")
@EqualsAndHashCode(callSuper = true)
public class RegionChargerQuery extends BaseQuery {

    @ApiModelProperty(value = "开始时间", name = "startTime")
    private String startTime;

    @ApiModelProperty(value = "结束时间", name = "endTime")
    private String endTime;

    @ApiModelProperty(value = "学期ID", name = "semesterId")
    private Long semesterId;

}
