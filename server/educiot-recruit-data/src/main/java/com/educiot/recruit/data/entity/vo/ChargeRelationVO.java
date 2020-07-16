package com.educiot.recruit.data.entity.vo;

import com.educiot.common.base.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: server
 * @description: 添加学生初始化负责人封装实体VO
 * @author: Mr.AI
 * @create: 2020-05-14
 **/

@Data
@ApiModel(value = "添加学生初始化负责人封装实体VO")
@EqualsAndHashCode(callSuper = true)
public class ChargeRelationVO extends BaseVO {

    @ApiModelProperty(value = "招生负责人名称")
    private String name;

    @ApiModelProperty(value = "招生负责人ID")
    private Long chargerId;

    @ApiModelProperty(value = "分组ID")
    private Long groupId;

    @ApiModelProperty(value = "负责人APIID")
    private Long apiId;

    @ApiModelProperty(value = "是否是组长")
    private int isLeader;

}
