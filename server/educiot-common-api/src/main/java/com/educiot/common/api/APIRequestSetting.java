package com.educiot.common.api;

import com.educiot.common.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Jason Hong
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class APIRequestSetting extends BaseVO {
    private String account;
    private String pwd;
    private String token;

    public APIRequestSetting(String account, String pwd) {
        this.account = account;
        this.pwd = pwd;
    }

    public APIRequestSetting() {
    }
}
