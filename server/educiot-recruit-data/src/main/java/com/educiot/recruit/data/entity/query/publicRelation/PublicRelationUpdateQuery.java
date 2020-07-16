package com.educiot.recruit.data.entity.query.publicRelation;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel(value = "更新学校公关")
@EqualsAndHashCode(callSuper = true)
public class PublicRelationUpdateQuery extends PublicRelationAddQuery {

    @ApiModelProperty(value = "招生学校公关ID", required = true)
    @TableId(value = "public_relation", type = IdType.ID_WORKER)
    private Long publicRelation;
}
