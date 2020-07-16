package com.educiot.recruit.data.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: server
 * @description: 招生目标渠道字典数据封装类
 * @author: XuChao
 * @create: 2020-04-15 13:55
 **/
@Data
public class TargetDictionaryVO {

    @ApiModelProperty(value = "目标分解ID")
    private Long targetDecompositionId;

    @ApiModelProperty(value = "父节点ID")
    private Long parentId;

    @ApiModelProperty(value = "节点名称")
    private String nodeName;

    @ApiModelProperty(value = "目标数量")
    private Integer targetAmount;

    @ApiModelProperty(value = "说明")
    private String comment;
}
