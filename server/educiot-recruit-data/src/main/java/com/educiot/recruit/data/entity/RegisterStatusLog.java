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
 * 报名状态修改日志表
 * </p>
 *
 * @author LiuHao
 * @since 2020-05-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_register_status_log")
@ApiModel(value="RegisterStatusLog对象", description="报名状态修改日志表")
public class RegisterStatusLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "报名状态修改日志ID")
    @TableId(value = "register_status_log_id", type = IdType.ID_WORKER)
    private Long registerStatusLogId;

    @ApiModelProperty(value = "学生公关ID")
    private Long studentPublicRelationId;

    @ApiModelProperty(value = "状态结果（0：已交订金，1：已交全款，2：已退款，3：强意向生，4：已报名）")
    private Integer statusResult;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime modifyTime;

    @ApiModelProperty(value = "修改人ID")
    private Long modifyId;

    @ApiModelProperty(value = "修改人名称")
    private String modifyName;


}
