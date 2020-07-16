package com.educiot.recruit.third.common;


import java.util.Map;

/**
 * 基础controller工具
 *
 * @author Pan Juncai
 * @version 1.0
 * @date 2019/8/23 19:59
 */
public class BaseController extends com.educiot.common.api.base.BaseController {

    public static final Object STATUSCODE_FAILED_BADINPUT_PARAM = "STATUSCODE_FAILED_BADINPUT_PARAM";

    public static final Object STATUSCODE_SUCCESS = "STATUSCODE_FAILED_BADINPUT_PARAM";

    protected Map<String, Object> json4Map(Map<String, Object> jsonMap, Map<String, Object> subJsonMap, String msg, Object statuscodeFailedBadinputParam) {
        return subJsonMap;
    }
}
