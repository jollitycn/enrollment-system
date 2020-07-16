package com.educiot.recruit.data.entity.preach;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 宣讲协助人关联表
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_preach_assistant_relation")
@ApiModel(value="PreachAssistantRelation对象", description="宣讲协助人关联表")
public class PreachAssistantRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "宣讲协助人关联ID")
    @TableId(value = "preach_assistant_relationt_id", type = IdType.ID_WORKER)
    private Long preachAssistantRelationtId;

    @ApiModelProperty(value = "招生宣讲计划ID")
    private Long preachPlanId;

    @ApiModelProperty(value = "负责人ID")
    private Long chargerId;

    @ApiModelProperty(value = "负责人名称")
    private String chargerName;

}
