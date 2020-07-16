package com.educiot.common.base;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 所有返回外部的实体必须该BaseVO
 *
 * @author Pan Juncai
 * @version 1.0
 * @date 2019/8/7 11:19
 */
@Data
@ApiModel(value = "基础响应VO")
public abstract class BaseVO implements Serializable {
    private static final long serialVersionUID = 3757734902054074348L;

    @Override
    public String toString() {
        return JSON.toJSONString(this, true);
    }
}
