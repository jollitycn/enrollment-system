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
 * @author liuhao
 * @program educiot-recruit
 * @description：消息列表
 * @date Create in 2020/5/11
 */
@Data
@ApiModel(value = "消息列表返回实体VO")
@EqualsAndHashCode(callSuper = true)
public class MessageListVO extends BaseVO {

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = Constant.Sys.LOCALDATETIME_FORMATTER, timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("消息类型：0：学校新闻，1：系统消息")
    private Integer messageType;

    @ApiModelProperty(value = "新闻ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long newsId;

    @ApiModelProperty(value = "任务接收信息ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long taskReceiverId;

    @ApiModelProperty(value = "消息标题")
    private String title;
}
