package com.educiot.common.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: server
 * @description: 自定义异常类
 * @author: XuChao
 * @create: 2020-04-24 15:56
 **/
@Data

@EqualsAndHashCode(callSuper = false)
public class MyExcption extends Exception {

    private int code;

    private String msg;

    public MyExcption(CodeMsg codeMsg) {
        super(codeMsg.getMessage());
        this.code = codeMsg.getRetCode();
        this.msg = codeMsg.getMessage();
    }

    public MyExcption(CodeMsg codeMsg, String message) {
        super(codeMsg.getMessage());
        this.code = codeMsg.getRetCode();
        this.msg = message;
    }

    public MyExcption(CodeMsg codeMsg, Exception e) {
        super(codeMsg.getMessage() + e.getMessage());
        this.code = codeMsg.getRetCode();
        this.msg = codeMsg.getMessage() + e.getMessage();
    }

    public MyExcption(String msg) {
        super(msg);
        this.code = CodeMsg.SERVER_EXCEPTION.getRetCode();
        this.msg = msg;
    }

    public MyExcption(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
