package com.educiot.recruit.data.entity.vo;

import com.educiot.recruit.common.base.BaseVO;
import com.educiot.recruit.data.entity.AimMajor;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @program: server
 * @description: 选择专业信息入参封装实体VO
 * @author: Mr.AI
 * @create: 2020-04-29
 **/

@Data
@ApiModel(value = "选择专业信息入参封装实体VO")
@EqualsAndHashCode(callSuper = true)
public class AimMajorInfoVO extends BaseVO {

    @ApiModelProperty(value = "选择专业信息列表",name = "aimMajors")
    private List<AimMajor> aimMajors;

    @ApiModelProperty(value = "学生公关ID")
    private Long studentPublicRelationId;

}
