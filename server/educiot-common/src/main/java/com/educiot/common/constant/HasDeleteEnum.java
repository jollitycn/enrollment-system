package com.educiot.common.constant;

/**
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/3/17 17:55
 */
public enum HasDeleteEnum {
    /**
     * 未删除
     */
    UN_DELETE(0, "未删除"),
    /**
     * 删除
     */
    IS_DELETED(1, "已删除");

    /**
     * 用户状态
     */
    private Integer status;
    /**
     * 说明
     */
    private String desc;

    HasDeleteEnum(Integer status, String desc) {
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
