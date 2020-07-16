package com.educiot.recruit.data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 任务接收人表
 * </p>
 *
 * @author LiuHao
 * @since 2020-05-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_task_receiver")
@ApiModel(value="TaskReceiver对象", description="任务接收信息表")
public class TaskReceiver implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务接收信息ID")
    @TableId(value = "task_receiver_id", type = IdType.ID_WORKER)
    private Long taskReceiverId;

    @ApiModelProperty(value = "招生任务ID")
    private Long taskId;

    @ApiModelProperty(value = "状态（0：未完成，1：完成）")
    private Integer status;

    @ApiModelProperty(value = "接收人ID")
    private Long receiverId;

    @ApiModelProperty(value = "学期ID")
    private Long semesterId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "查看状态（0：未读，1：已读）")
    private Integer readStatus;
}
