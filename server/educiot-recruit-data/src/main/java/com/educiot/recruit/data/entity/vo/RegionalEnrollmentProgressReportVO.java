package com.educiot.recruit.data.entity.vo;

import com.educiot.recruit.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: server
 * @description: 区域招生进度报表封装实体VO
 * @author: Mr.AI
 * @create: 2020-04-26
 **/

@Data
@ApiModel(value = "区域招生进度报表封装实体VO")
@EqualsAndHashCode(callSuper = true)
public class RegionalEnrollmentProgressReportVO extends BaseVO {

    @ApiModelProperty(value = "组别名称")
    private String groupName;

    @ApiModelProperty(value = "计划招生人数")
    private int targetAmount;

    @ApiModelProperty(value = "报名人数")
    private int applyAmount;

    @ApiModelProperty(value = "交费人数")
    private int payAmount;

    @ApiModelProperty(value = "退费人数")
    private int refundAmount;

    @ApiModelProperty(value = "完成度")
    private String DegreeCompletionAmount;

}
