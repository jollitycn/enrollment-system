package com.educiot.recruit.data.entity.vo;

import com.educiot.recruit.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: server
 * @description: 渠道报表放回封装实体VO
 * @author: Mr.AI
 * @create: 2020-05-08
 **/

@Data
@ApiModel(value = "渠道报表放回封装实体VO")
@EqualsAndHashCode(callSuper = true)
public class ChannelReportVO extends BaseVO {

    @ApiModelProperty(value = "渠道名称")
    private String channelName;

    @ApiModelProperty(value = "目标人数")
    private int targetAmount;

    @ApiModelProperty(value = "退费人数")
    private int refundAmount;

    @ApiModelProperty(value = "交费人数")
    private int payAmount;

    @ApiModelProperty(value = "报名人数")
    private int applyAmount;



}
