package com.educiot.recruit.common.api;

import com.educiot.common.api.base.BaseVO;
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
