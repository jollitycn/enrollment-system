package com.educiot.recruit.common.base;

/**
 * @program: hd
 * @description: 返回封装类
 * @author: XuChao
 * @create: 2019-12-24 14:57
 **/

public class Result<T> extends BaseVO {

    private String message;
    private int retCode;
    private T data;
    private int type;

    private Result(T data) {
        this.retCode = 0;
        this.message = "成功";
        this.data = data;
    }

    private Result(CodeMsg cm) {
        if (cm == null) {
            return;
        }
        this.retCode = cm.getRetCode();
        this.message = cm.getMessage();
    }

    private Result(CodeMsg cm, T data) {
        if (cm == null) {
            return;
        }
        this.retCode = cm.getRetCode();
        this.message = cm.getMessage();
        this.data = data;
    }

    /**
     * 成功时候的调用
     *
     * @return
     */
    public static <T> Result<T> success(T data) {
        return new Result<T>(data);
    }

    /**
     * 成功，不需要传入参数
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> Result<T> success() {
        return (Result<T>) success("");
    }

    /**
     * 失败时候的调用
     *
     * @return
     */
    public static <T> Result<T> error(CodeMsg cm) {
        return new Result<T>(cm);
    }


    public static <T> Result<T> error(int retCode,String message) {
        return new Result<T>(retCode,message);
    }




    private Result(int retCode,String message) {
        this.retCode = retCode;
        this.message = message;
    }

    /**
     * 失败时候的调用,扩展消息参数
     *
     * @param cm
     * @param msg
     * @return
     */
//    public static <T> Result<T> error(CodeMsg cm, String msg) {
//        Result<T> result = new Result<T>(cm);
//        result.message = cm.getMessage() + "--" + msg;
//        return result;
//    }

    /**
     * 失败时候的调用,返回错误消息同时返回错误数据
     *
     * @param cm   错误码信息
     * @param data 返回数据
     * @return com.educiot.recruit.common.base.Result&lt;T&gt;
     * @author Pan Juncai
     * @date 2020/4/30 14:19
     */
    public static <T> Result<T> error(CodeMsg cm,  T data) {
        return new Result<T>(cm, data);
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public int getRetCode() {
        return retCode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    /*
//result = Result.success(dataMap);// 成功，并返回数据和retCode和message

// result = Result.success();// 成功，不返回数据，只返回retCode和message

// result = Result.error(CodeMsg.SERVER_EXCEPTION);// 失败返回错误信息

// result = Result.error(CodeMsg.SERVER_EXCEPTION,e.toString());// 失败返回错误+扩展错误信息
 */

}
