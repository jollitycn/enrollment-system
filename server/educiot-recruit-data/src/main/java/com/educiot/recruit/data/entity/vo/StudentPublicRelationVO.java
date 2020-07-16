package com.educiot.recruit.data.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.educiot.recruit.common.base.BaseVO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: server
 * @description: 公关信息中的分组信息返回封装实体VO
 * @author: Mr.AI
 * @create: 2020-04-28
 **/

@Data
@ApiModel(value = "公关信息中的分组信息返回封装实体VO")
@EqualsAndHashCode(callSuper = true)
public class StudentPublicRelationVO extends BaseVO {

    @ApiModelProperty(value = "招生组Id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long groupId;

    @ApiModelProperty(value = "招生组名称")
    private String name;

    @ApiModelProperty(value = "学生公关ID")
    @TableId(value = "student_public_relation_id", type = IdType.ID_WORKER)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long studentPublicRelationId;

}
