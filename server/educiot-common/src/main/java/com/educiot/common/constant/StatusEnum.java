package com.educiot.common.constant;

/**
 * status对应的枚举
 *
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/4/15 17:10
 */
public enum StatusEnum {

    /**
     * 删除
     */
    IS_DELETE(0, "删除"),

    /**
     * 正常
     */
    NORMAL(1, "正常");

    /**
     * 状态
     */
    private Integer status;
    /**
     * 说明
     */
    private String desc;

    StatusEnum(Integer status, String desc) {
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
