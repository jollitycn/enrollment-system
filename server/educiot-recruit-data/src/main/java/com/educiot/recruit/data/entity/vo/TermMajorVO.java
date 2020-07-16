package com.educiot.recruit.data.entity.vo;

import com.educiot.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: server
 * @description: 获取平台专业列表封装实体VO
 * @author: Mr.AI
 * @create: 2020-04-18
 **/

@Data
@ApiModel(value = "获取平台学期列表封装实体VO")
@EqualsAndHashCode(callSuper = true)
public class TermMajorVO extends BaseVO {

    @ApiModelProperty(value = "平台获取的专业Id")
    private String id;

    @ApiModelProperty(value = "平台获取的专业名称")
    private String name;

}
