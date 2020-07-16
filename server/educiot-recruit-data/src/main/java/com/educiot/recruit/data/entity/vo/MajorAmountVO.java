package com.educiot.recruit.data.entity.vo;

import com.educiot.recruit.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: server
 * @description: 新增专业获取所属专业下拉列表封装返回实体VO
 * @author: Mr.AI
 * @create: 2020-05-21
 **/
@Data
@ApiModel(value = "新增专业获取所属专业下拉列表封装返回实体VO")
@EqualsAndHashCode(callSuper = true)
public class MajorAmountVO extends BaseVO {

    private String id;
    private String name;
    private Integer amount;
}
