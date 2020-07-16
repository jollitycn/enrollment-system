package com.educiot.recruit.data.entity.vo;

import com.educiot.common.base.BaseVO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author liuhao
 * @program educiot-recruit
 * @description：任务列表
 * @date Create in 2020/5/7
 */
@Data
@ApiModel(value = "任务列表返回实体VO")
@EqualsAndHashCode(callSuper = true)
public class TaskListVO extends BaseVO {

    @ApiModelProperty(value = "任务接收信息ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long taskReceiverId;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "状态（0：未完成，1：完成）")
    private Integer status;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "类型（0：维护学校，1：维护宣讲，2：宣讲任务，3：诉求，4：咨询）")
    private Integer taskType;

    @ApiModelProperty(value = "跳转页面")
    private String jumpPage;

    @ApiModelProperty(value = "上报方式（0：系统上报，1：用户填报）")
    private Integer reportType;

    @ApiModelProperty(value = "发布人员名称")
    private String creatorName;

    @ApiModelProperty(value = "查看状态（0：未读，1：已读）")
    private Integer readStatus;
}
