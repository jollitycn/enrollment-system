package com.educiot.recruit.data.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @program: server
 * @description: api请求返回数据封装类
 * @author: XuChao
 * @create: 2020-04-15 17:40
 **/
@Data
@AllArgsConstructor
public class RequestApiDTO {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("名称")
    private String name;

}
