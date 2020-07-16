package com.educiot.recruit.data.entity.vo;

import com.educiot.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: server
 * @description: APP生源学校报名分析报表封装实体VO
 * @author: Mr.AI
 * @create: 2020-05-08
 **/
@Data
@ApiModel(value = "APP生源学校报名分析报表封装实体VO")
@EqualsAndHashCode(callSuper = true)
public class EnrollmentAnalysisVO extends BaseVO {

    @ApiModelProperty(value = "生源学校名称")
    private String sourceSchoolName;

    @ApiModelProperty(value = "报名人数")
    private int numberOfPeople;
}
