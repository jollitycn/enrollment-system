package com.educiot.recruit.data.entity.vo;

import com.educiot.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: server
 * @description: 招生报表区域下的负责人返回封装实体VO
 * @author: Mr.AI
 * @create: 2020-05-09
 **/
@Data
@ApiModel(value = "招生报表区域下的负责人返回封装实体VO")
@EqualsAndHashCode(callSuper = true)
public class RegionChargerVO extends BaseVO {

    @ApiModelProperty(value = "负责人姓名")
    private String name;

    @ApiModelProperty(value = "拜访学校数")
    private int visitedSchoolNumber;

    @ApiModelProperty(value = "来校参观学校数")
    private int schoolNumber;

    @ApiModelProperty(value = "来校参观人数")
    private int peopleNumber;

    @ApiModelProperty(value = "报名人数小计")
    private int payAmount;

    @ApiModelProperty(value = "退费人数")
    private int refundAmount;

    @ApiModelProperty(value = "报名总人数")
    private int totalAmount;

    @ApiModelProperty(value = "根据时间查询的报名人数")
    private int timeAmount;

}
