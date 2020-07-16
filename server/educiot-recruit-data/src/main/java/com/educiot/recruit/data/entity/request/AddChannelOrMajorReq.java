package com.educiot.recruit.data.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: server
 * @description: 修改渠道/专业信息节点请求参数封装类
 * @author: XuChao
 * @create: 2020-04-15 10:18
 **/
@Data
public class AddChannelOrMajorReq {

    @ApiModelProperty("目标分解ID")
    private String targetDecompositionId;

    @ApiModelProperty("目标数量")
    private Integer targetAmount;

    @ApiModelProperty("说明")
    private String comment;

}
