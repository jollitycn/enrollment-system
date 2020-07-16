package com.educiot.recruit.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 招生组生源学校关联表
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_group_source_relation")
@ApiModel(value = "GroupSourceRelation对象", description = "招生组生源学校关联表")
public class GroupSourceRelation implements Serializable {

    public class Columns {

        public static final String GROUP_ID = "group_id";
        public static final String SOURCE_SCHOOL_ID = "source_school_id";
        public static final String STATUS = "status";
    }

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "招生组生源学校关联ID")
    @TableId(value = "group_source_relation_id", type = IdType.ID_WORKER)
    private Long groupSourceRelationId;

    @ApiModelProperty(value = "招生组ID")
    private Long groupId;

    @ApiModelProperty(value = "生源学校")
    private Long sourceSchoolId;

    @ApiModelProperty(value = "学期ID")
    private Long semesterId;

}
