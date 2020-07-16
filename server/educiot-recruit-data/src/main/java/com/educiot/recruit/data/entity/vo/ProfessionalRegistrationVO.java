package com.educiot.recruit.data.entity.vo;

import com.educiot.recruit.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: server
 * @description: APP各专业报名数据封装实体VO
 * @author: Mr.AI
 * @create: 2020-05-08
 **/
@Data
@ApiModel(value = "APP各专业报名数据封装实体VO")
@EqualsAndHashCode(callSuper = true)
public class ProfessionalRegistrationVO extends BaseVO {

    @ApiModelProperty(value = "专业名称")
    private String majorName;

    @ApiModelProperty(value = "目标招生数量")
    private int recruitAmount;

    @ApiModelProperty(value = "实际交费数量")
    private int numberOfPeople;
}
