package com.educiot.common.api;

import com.educiot.common.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Jason Hong
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Person extends BaseVO {
    private String phone;
    private String name;
    private String userid;
    private String account;
}
