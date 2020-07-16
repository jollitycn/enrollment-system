package com.educiot.common.constant;

/**
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/3/30 18:17
 */
public enum DiscountUseTypeEnum {

    /**
     * 通用
     */
    COMMON(0, "通用"),
    /**
     * 满减
     */
    FULL_REDUCTION(1, "满减");

    /**
     * 优惠券类型
     */
    private Integer useTyoe;
    /**
     * 说明
     */
    private String desc;

    DiscountUseTypeEnum(Integer useTyoe, String desc) {
        this.useTyoe = useTyoe;
        this.desc = desc;
    }

    public Integer getUseTyoe() {
        return useTyoe;
    }

    public void setUseTyoe(Integer useTyoe) {
        this.useTyoe = useTyoe;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
