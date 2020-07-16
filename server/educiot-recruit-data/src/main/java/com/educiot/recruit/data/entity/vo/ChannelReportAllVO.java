package com.educiot.recruit.data.entity.vo;

import com.educiot.recruit.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @program: server
 * @description: 渠道报表总计封装返回实体VO
 * @author: Mr.AI
 * @create: 2020-05-11
 **/
@Data
@ApiModel(value = "渠道报表总计封装返回实体VO")
@EqualsAndHashCode(callSuper = true)
public class ChannelReportAllVO extends BaseVO {

    @ApiModelProperty(value = "渠道名称")
    private String name;

    @ApiModelProperty(value = "目标人数总计")
    private int targetAmountAll;

    @ApiModelProperty(value = "报名人数总计")
    private int applyAmountAll;

    @ApiModelProperty(value = "交费人数总计")
    private int payAmountAll;

    @ApiModelProperty(value = "退费人数总计")
    private int refundAmountAll;

    @ApiModelProperty(value = "渠道报表详情信息")
    private List<ChannelReportVO> channelReportVOS;

}
