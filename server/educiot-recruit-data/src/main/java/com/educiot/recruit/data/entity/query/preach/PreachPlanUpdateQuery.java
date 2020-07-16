package com.educiot.recruit.data.entity.query.preach;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel(value = "更新宣讲计划")
@EqualsAndHashCode(callSuper = true)
public class PreachPlanUpdateQuery extends PreachPlanAddQuery {

    @ApiModelProperty(value = "招生宣讲计划ID", required = true)
    @TableId(value = "preach_plan_id", type = IdType.ID_WORKER)
    private Long preachPlanId;
}
