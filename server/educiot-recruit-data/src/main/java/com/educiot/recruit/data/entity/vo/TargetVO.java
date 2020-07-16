package com.educiot.recruit.data.entity.vo;

import com.educiot.recruit.common.constant.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @program: server
 * @description: 招生目标列表返回封装类
 * @author: XuChao
 * @create: 2020-04-13 16:31
 **/
@Data
public class TargetVO {

    @ApiModelProperty(value = "招生目标主键id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long targetId;

    @ApiModelProperty(value = "学校id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long schoolId;

    @ApiModelProperty(value = "学期id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long semesterId;

    @ApiModelProperty(value = "学期名称")
    private String semesterName;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "目标数量")
    private Integer targetAmount;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.Sys.LOCALDATETIME_FORMATTER)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人")
    private String creatorName;

}
