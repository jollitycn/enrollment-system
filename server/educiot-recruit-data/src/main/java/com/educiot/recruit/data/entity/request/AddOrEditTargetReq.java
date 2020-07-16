package com.educiot.recruit.data.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: server
 * @description: 添加或者编辑招生目标请求参数封装类
 * @author: XuChao
 * @create: 2020-04-13 17:29
 **/
@Data
public class AddOrEditTargetReq {

//    @ApiModelProperty(value = "招生学校ID", required = true)
//    private Long recruitSchoolId;

    @ApiModelProperty("招生目标id")
    private Long targetId;

    @ApiModelProperty(value = "学期id",required = true)
    private Long semesterId;

    @ApiModelProperty(value = "学期名称",required = true)
    private String semester;

    @ApiModelProperty(value = "标题",required = true)
    private String title;

    @ApiModelProperty(value = "目标数量",required = true)
    private Integer targetAmount;

}
