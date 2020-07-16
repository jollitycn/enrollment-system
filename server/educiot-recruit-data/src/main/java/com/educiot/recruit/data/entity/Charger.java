package com.educiot.recruit.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 招生负责人
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_charger")
@ApiModel(value="Charger对象", description="招生负责人")
public class Charger implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "负责人ID")
    @TableId(value = "charger_id", type = IdType.ID_WORKER)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long chargerId;

    @ApiModelProperty(value = "招生组ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long groupId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "手机号")
    private String telephone;

    @ApiModelProperty(value = "状态（0：删除，1：正常）")
    private Integer status;

    @ApiModelProperty(value = "是否是组长（0：否，1：是）")
    private Integer isLeader;

    @ApiModelProperty(value = "接口ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long apiId;

    public class Columns {
        public static final String API_ID = "api_id";
        public static final String STATUS = "status";
    }

}
