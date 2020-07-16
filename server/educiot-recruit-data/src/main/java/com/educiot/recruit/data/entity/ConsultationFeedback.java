package com.educiot.recruit.data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
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
 * 咨询反馈表
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_consultation_feedback")
@ApiModel(value="ConsultationFeedback对象", description="咨询反馈表")
public class ConsultationFeedback implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "咨询反馈ID")
    @TableId(value = "consultation_feedback_id", type = IdType.ID_WORKER)
    private Long consultationFeedbackId;

    @ApiModelProperty(value = "到校咨询ID")
    private Long consultationId;

    @ApiModelProperty(value = "咨询日期")
    private LocalDate consultationDate;

    @ApiModelProperty(value = "反馈内容")
    private String consultationContent;

    @ApiModelProperty(value = "创建人")
    private Long creatorId;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "总结")
    private String summary;

}
