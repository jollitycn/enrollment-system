package com.educiot.recruit.data.entity.query;

import com.educiot.recruit.common.base.BasePageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: server
 * @description: 新闻列表查询参数请求封装类
 * @author: XuChao
 * @create: 2020-04-20 17:47
 **/
@Data
public class ListNewsPageQuery extends BasePageQuery {

    @ApiModelProperty("信息类型")
    private String messageType;

    @ApiModelProperty("接收端（0：全部，1：学生，2：家长）")
    private Integer recevier;

    @ApiModelProperty("所属计划（0：不限）")
    private Long belongPlan;

    @ApiModelProperty("搜索标题")
    private String title;
}
