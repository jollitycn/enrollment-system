package com.educiot.recruit.data.entity.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: server
 * @description: 新增学期参数封装类
 * @author: XuChao
 * @create: 2020-04-15 10:10
 **/
@Data
public class AddSemesterReq {

    @ApiModelProperty(value = "学校id", required = true)
    private Long schoolId;

    @ApiModelProperty(value = "学期信息" ,required = true)
    private String semesterName;

}
