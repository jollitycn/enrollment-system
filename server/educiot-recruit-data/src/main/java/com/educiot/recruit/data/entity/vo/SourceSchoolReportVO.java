package com.educiot.recruit.data.entity.vo;

import com.educiot.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: server
 * @description: 数据分析中生源报表返回封装实体VO
 * @author: Mr.AI
 * @create: 2020-04-27
 **/

@Data
@ApiModel(value = "数据分析中生源报表返回封装实体VO")
@EqualsAndHashCode(callSuper = true)
public class SourceSchoolReportVO extends BaseVO {

    @ApiModelProperty(value = "生源学校名称")
    private String schoolName;

    @ApiModelProperty(value = "报名人数")
    private int applyAmount;

    @ApiModelProperty(value = "交费人数")
    private int payAmount;

    @ApiModelProperty(value = "退费人数")
    private int refundAmount;


}
