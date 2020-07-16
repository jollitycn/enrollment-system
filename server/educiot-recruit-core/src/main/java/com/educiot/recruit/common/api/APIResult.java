package com.educiot.recruit.common.api;

import com.educiot.recruit.common.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Jason Hong
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class APIResult extends BaseVO {

    public static final Integer SUCCCESS = 200;
    public static final Integer FAILED = 10004;
    /**
     *
     */
    public static final Integer NOT_LOGIN = 10001;
    protected Object data;
    private String msg;
    private int total;
    private boolean success;
    private Integer status;
    private String token;

    public APIResult() {
    }

    public Object getData() {
        return this.data;
    }

    public String getMsg() {
        return this.msg;
    }

    public int getTotal() {
        return this.total;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public Integer getStatus() {
        return this.status;
    }

    public String getToken() {
        return this.token;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
