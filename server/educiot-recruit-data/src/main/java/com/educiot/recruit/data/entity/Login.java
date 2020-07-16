package com.educiot.recruit.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.educiot.common.constant.Constant;
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
 * 登录表
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_login")
@ApiModel(value = "Login对象", description = "登录表")
public class Login implements Serializable {

    public class Columns {
        public static final String LOGIN = "login";
        public static final String OPEN_ID = "openid";
        public static final String LOGIN_TYPE = "loginType";
        public static final String MOBILE = "mobile";
        public static final String CREATE_TIME = "createTime";
        public static final String LAST_TIME = "lastTime";
    }

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "登录ID")
    @TableId(value = "login", type = IdType.ID_WORKER)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long login;

    @ApiModelProperty(value = "openid")
    private String openid;

    @ApiModelProperty(value = "登录类型（0：学生，1：家长）")
    private Integer loginType;

    @ApiModelProperty(value = "第一次登录时间")
    @JsonFormat(pattern = Constant.Sys.LOCALDATETIME_FORMATTER, timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "联系方式")
    private String mobile;

    @ApiModelProperty(value = "最后一次登录时间")
    @JsonFormat(pattern = Constant.Sys.LOCALDATETIME_FORMATTER, timezone = "GMT+8")
    private LocalDateTime lastTime;


}
