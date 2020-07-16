package com.educiot.recruit.common.constant;

/**
 * 是否组长枚举
 *
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/4/16 10:03
 */
public enum LeaderFlagEnum {
    /**
     * 否
     */
    NOT_LEADER(0, "否"),
    /**
     * 正常
     */
    LEADER(1, "是");
    /**
     * 是
     */
    private Integer status;
    /**
     * 说明
     */
    private String desc;

    LeaderFlagEnum(Integer status, String desc) {
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
