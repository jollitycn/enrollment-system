package com.educiot.recruit.data.entity.vo;

import com.educiot.recruit.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @program: server
 * @description: 招生报表区域查询返回封装实体VO
 * @author: Mr.AI
 * @create: 2020-05-09
 **/
@Data
@ApiModel(value = "招生报表区域查询返回封装实体VO")
@EqualsAndHashCode(callSuper = true)
public class RegionVO extends BaseVO {

    @ApiModelProperty(value = "区域名称")
    private String name;

    @ApiModelProperty(value = "区域计划招生人数")
    private int targetAmount;

    @ApiModelProperty(value = "分组ID")
    private Long groupId;

    @ApiModelProperty(value = "区域下的负责人详细信息")
    private List<RegionChargerVO> regionChargerVOS;

}
