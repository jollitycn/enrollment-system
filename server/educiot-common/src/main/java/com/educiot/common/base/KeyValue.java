package com.educiot.common.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Jason Hong
 */
@Data
public class KeyValue<T1, T2> implements Serializable {
    private T1 key;
    private T2 value;
}
