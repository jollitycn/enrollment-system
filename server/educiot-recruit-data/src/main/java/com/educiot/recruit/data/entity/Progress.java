package com.educiot.recruit.data.entity;

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
 * 学生报名进度表
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_progress")
@ApiModel(value="Progress对象", description="学生报名进度表")
public class Progress implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "学生报名进度ID")
    @TableId(value = "progress_id", type = IdType.ID_WORKER)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long progressId;

    @ApiModelProperty(value = "学生公关ID")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long studentPublicRelationId;

    @ApiModelProperty(value = "进度类型（0：报名登记，1：交费定位，2：接收通知，3：入学报名）")
    private Integer progressType;

    @ApiModelProperty(value = "进度内容")
    private String progressContent;

    @ApiModelProperty(value = "创建人")
    @JsonSerialize(using= ToStringSerializer.class)
    private Long creatorId;

    @ApiModelProperty(value = "创建人名称")
    private String creatorName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = Constant.Sys.LOCALDATETIME_FORMATTER, timezone = "GMT+8")
    private LocalDateTime createTime;


}
