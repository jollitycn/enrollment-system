package com.educiot.recruit.data.entity.vo;

import com.educiot.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @program: server
 * @description: 报表和数据分析封装实体VO
 * @author: Mr.AI
 * @create: 2020-04-26
 **/

@Data
@ApiModel(value = "报表和数据分析返回实体")
@EqualsAndHashCode(callSuper = true)
public class ReportVO extends BaseVO {

    @ApiModelProperty(value = "完成人数")
    private Integer numberCompletions;

    @ApiModelProperty(value = "总计划人数")
    private int planAllNumber;

    @ApiModelProperty(value = "总完成度")
    private String allDegreeCompletionAmount;

    @ApiModelProperty(value = "区域招生进度列表")
    private List<RegionalEnrollmentProgressReportVO> regionalEnrollmentProgressReportVOS;

}
