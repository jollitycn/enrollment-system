package com.educiot.recruit.data.entity.vo;

import com.educiot.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: server
 * @description: APP负责人招生报表封装实体VO
 * @author: Mr.AI
 * @create: 2020-05-08
 **/
@Data
@ApiModel(value = "APP负责人招生报表封装实体VO")
@EqualsAndHashCode(callSuper = true)
public class PrincipalProgressVO extends BaseVO{

    @ApiModelProperty(value = "招生负责人名称",name = "principal")
    private String principal;

    @ApiModelProperty(value = "招生人数",name = "numberOfPeople")
    private int numberOfPeople;
}
