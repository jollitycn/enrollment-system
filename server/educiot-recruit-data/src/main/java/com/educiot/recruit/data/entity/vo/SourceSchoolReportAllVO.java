package com.educiot.recruit.data.entity.vo;

import com.educiot.recruit.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @program: server
 * @description: 数据分析总生源报表返回封装实体VO
 * @author: Mr.AI
 * @create: 2020-04-30
 **/

@Data
@ApiModel(value = "数据分析中生源报表返回封装实体VO")
@EqualsAndHashCode(callSuper = true)
public class SourceSchoolReportAllVO extends BaseVO {

    @ApiModelProperty(value = "报名人数总计")
    private int applyAmountAll;

    @ApiModelProperty(value = "交费人数总计")
    private int payAmountAll;

    @ApiModelProperty(value = "退费人数总计")
    private int refundAmountAll;

    @ApiModelProperty(value = "生源学校报表信息列表")
    private List<SourceSchoolReportVO>  sourceSchoolReportVOS;

}
