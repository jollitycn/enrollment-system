package com.educiot.common.constant;

/**
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/1/14 14:56
 */
public enum SysUserStopEnum {
    /**
     * 用户已删除
     */
    USER_HAS_STOPPED(1, "用户已禁用"),
    /**
     * 用户正常（使用中）
     */
    USER_HAS_USING(0, "用户正常（使用中）");

    /**
     * 用户状态
     */
    private Integer status;
    /**
     * 说明
     */
    private String desc;

    SysUserStopEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
