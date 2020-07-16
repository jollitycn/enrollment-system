package com.educiot.recruit.data.entity.vo;

import com.educiot.recruit.common.base.BaseVO;
import com.educiot.recruit.common.constant.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/4/13 14:55
 */
@Data
@ApiModel(value = "招生区域列表信息表返回实体VO")
@EqualsAndHashCode(callSuper = true)
public class ListGroupInfoPageVO extends BaseVO {
    @ApiModelProperty(value = "招生组信息ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long groupInfoId;

    @ApiModelProperty(value = "学期ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long semesterId;

    @ApiModelProperty(value = "学期")
    private String semester;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "说明")
    private String comment;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = Constant.Sys.LOCALDATETIME_FORMATTER, timezone = "GMT+8")
    private LocalDateTime createTime;
}
