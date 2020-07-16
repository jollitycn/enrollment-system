package com.educiot.recruit.data.entity;

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
 * 登录信息关联表
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_login_relation")
@ApiModel(value="LoginRelation对象", description="登录信息关联表")
public class LoginRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "login_relation_id", type = IdType.ID_WORKER)
    private Long loginRelationId;

    @ApiModelProperty(value = "学生基本信息ID")
    private Long studentInfo;

    @ApiModelProperty(value = "登录ID")
    private Long login;


}
