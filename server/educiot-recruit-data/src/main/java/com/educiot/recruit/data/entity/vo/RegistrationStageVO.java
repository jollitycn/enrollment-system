package com.educiot.recruit.data.entity.vo;

import com.educiot.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: server
 * @description: APP报名阶段数据报表封装实体VO
 * @author: Mr.AI
 * @create: 2020-05-08
 **/

@Data
@ApiModel(value = "APP报名阶段数据报表封装实体VO")
@EqualsAndHashCode(callSuper = true)
public class RegistrationStageVO extends BaseVO {

    @ApiModelProperty(value = "报名阶段名称")
    private String  stageName;

    @ApiModelProperty(value = "招生人数")
    private int numberOfPeople;

}
