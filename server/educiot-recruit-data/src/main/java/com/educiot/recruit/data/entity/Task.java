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
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;

/**
 * <p>
 * 招生任务表
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_task")
@ApiModel(value="Task对象", description="招生任务表")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "招生任务ID")
    @TableId(value = "task_id", type = IdType.ID_WORKER)
    private Long taskId;

    @ApiModelProperty(value = "状态（0：未完成，1：完成）")
    private Integer status;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "类型（0：维护学校，1：维护宣讲，2：宣讲任务，3：诉求，4：咨询）")
    private Integer taskType;

    @ApiModelProperty(value = "跳转页面")
    private String jumpPage;

    @ApiModelProperty(value = "学期ID")
    private Long semesterId;

    @ApiModelProperty(value = "相关ID")
    private Long relatedId;

    @ApiModelProperty(value = "上报方式（0：系统上报，1：用户填报）")
    private Integer reportType;

    @ApiModelProperty(value = "发布人员ID")
    private Long creatorId;

    @ApiModelProperty(value = "发布人员名称")
    private String creatorName;

    @ApiModelProperty(value = "类型（0：任务，1：学生信息）")
    private Integer type;
}
