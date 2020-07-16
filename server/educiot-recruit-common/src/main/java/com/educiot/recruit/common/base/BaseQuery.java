package com.educiot.recruit.common.base;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 所有外部的入参实体继承该BaseQuery
 *
 * @author Pan Juncai
 * @version 1.0
 * @date 2019/8/7 12:18
 */
@Data
@ApiModel(value = "基础入参Query")
public abstract class BaseQuery implements Serializable {
    private static final long serialVersionUID = 1834120610699366027L;
}

