package com.educiot.recruit.data.entity.vo;

import com.educiot.recruit.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: server
 * @description: 获取平台学期列表封装实体VO
 * @author: Mr.AI
 * @create: 2020-04-18
 **/

@Data
@ApiModel(value = "获取平台学期列表封装实体VO")
@EqualsAndHashCode(callSuper = true)
public class TermCollegeVO extends BaseVO {

    @ApiModelProperty(value = "平台获取的学期Id")
    private String id;

    @ApiModelProperty(value = "平台获取的学期名称")
    private String name;
}
