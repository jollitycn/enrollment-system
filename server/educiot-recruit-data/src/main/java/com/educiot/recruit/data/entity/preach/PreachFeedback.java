package com.educiot.recruit.data.entity.preach;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.educiot.common.constant.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 宣讲反馈表
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_preach_feedback")
@ApiModel(value = "PreachFeedback对象", description = "宣讲反馈表")
public class PreachFeedback implements Serializable {

    public class Columns {
        public static final String PREACH_FEEDBACK_ID = "preach_feedback_id";
        public static final String PREACH_PLAN_ID = "preach_plan_id";
        public static final String CREATOR_ID = "creator_id";
        public static final String CREATOR_NAME = "creator_name";
        public static final String CREATE_TIME = "create_time";
        public static final String STATUS = "status";
        public static final String FEEDBACK_CONTENT = "feedback_content";
        public static final String PERFORMANCE = "performance";
        public static final String STUDENT_PUBLIC_RELATION_ID = "student_public_relation_id";
    }

    @ApiModelProperty(value = "宣讲反馈ID")
    @TableId(value = "preach_feedback_id", type = IdType.ID_WORKER)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long preachFeedbackId;

    @ApiModelProperty(value = "招生宣讲计划ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long preachPlanId;

    @ApiModelProperty(value = "学生公关ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long studentPublicRelationId;

    @ApiModelProperty(value = "宣讲效果（0：好，1：一般，2：差）")
    private Integer performance;

    @ApiModelProperty(value = "反馈内容")
    private String feedbackContent;

    @ApiModelProperty(value = "创建人")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long creatorId;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.Sys.LOCALDATETIME_FORMATTER)
    private LocalDateTime createTime;


}
