package com.educiot.common.api.base;


import com.educiot.common.api.APIRequest;
import com.educiot.common.constant.Constant;
import com.educiot.common.util.JSONRedisUtil;
import com.educiot.common.util.StringUtil;

import java.util.List;

/**
 * @program: hd
 * @description: 请求基础封装类
 * @author: Jason Hong
 * @create: 2019-12-30 17:58
 **/
public class BaseController extends com.educiot.common.base.BaseController {

    protected APIRequest getAPIRequest(String token) {
        APIRequest request = null;
        if (token == null || token == Constant.Sys.DEFAULT_TOKEN) {
            request = JSONRedisUtil.hGet(Constant.RedisKey.GLOBAL_API, token, APIRequest.class);
            if (request == null) {
                request = new APIRequest();
                setAPIRequest(token, request);
            }
        } else {
            request = JSONRedisUtil.hGet(Constant.RedisKey.API, token, APIRequest.class);
        }
        return request;
    }

    protected void setAPIRequest(String token, APIRequest request) {
        if (token == null || token == Constant.Sys.DEFAULT_TOKEN) {
            JSONRedisUtil.hSet(Constant.RedisKey.GLOBAL_API, token, request);
        } else {
            JSONRedisUtil.hSetWithTime(Constant.RedisKey.API, token, request, Constant.Sys.TOKEN_EXPIRE_TIME);
        }
    }

    /**
     * @param token
     */
    protected void destoryAPIRequest(String token) {
        JSONRedisUtil.delHSet(Constant.RedisKey.API, token);
    }

}
