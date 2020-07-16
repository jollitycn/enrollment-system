package com.educiot.recruit.data.entity.query.preach.register;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel(value = "更新学校宣讲报名")
@EqualsAndHashCode(callSuper = true)
public class PreachRegisterUpdateQuery extends PreachRegisterAddQuery {

    @ApiModelProperty(value = "招生学校宣讲报名ID", required = true)
    @TableId(value = "preach_register_id", type = IdType.ID_WORKER)
    private Long PreachRegisterId;
}
