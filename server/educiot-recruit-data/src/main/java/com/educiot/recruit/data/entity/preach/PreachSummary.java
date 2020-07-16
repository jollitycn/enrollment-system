package com.educiot.recruit.data.entity.preach;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.educiot.recruit.common.constant.Constant;
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
 * 宣讲总结表
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_preach_summary")
@ApiModel(value = "PreachSummary对象", description = "宣讲总结表")
public class PreachSummary implements Serializable {

    public class Columns {
        public static final String PREACH_SUMMARY_ID = "preach_summary_id";
        public static final String PREACH_PLAN_ID = "preach_plan_id";
        public static final String PERFORMANCE = "performance";
        public static final String SUMMERY = "summery";
    }

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "宣讲总结ID")
    @TableId(value = "preach_summary_id", type = IdType.ID_WORKER)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long preachSummaryId;

    @ApiModelProperty(value = "招生宣讲计划ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long preachPlanId;

    @ApiModelProperty(value = "宣讲效果（0：好，1：一般，2：差）")
    private Integer performance;

    @ApiModelProperty(value = "反馈内容")
    private String feedbackContent;

    @ApiModelProperty(value = "宣讲总结")
    private String summery;

    @ApiModelProperty(value = "创建人")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long creatorId;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.Sys.LOCALDATETIME_FORMATTER)
    private LocalDateTime createTime;

}
