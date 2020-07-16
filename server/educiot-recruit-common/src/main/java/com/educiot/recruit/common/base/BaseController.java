package com.educiot.recruit.common.base;


import cn.hutool.http.HttpRequest;
import com.educiot.recruit.common.api.APIRequest;
import com.educiot.recruit.common.constant.Constant;
import com.educiot.recruit.common.util.JSONRedisUtil;
import com.educiot.recruit.common.util.StringUtil;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @program: hd
 * @description: 请求基础封装类
 * @author: Jason Hong
 * @create: 2019-12-30 17:58
 **/
public class BaseController {
    public static final String METHOD_DETAIL = "/detail/{id}";
    public static final String METHOD_DELETE = "/delete/{id}";
    public static final String METHOD_UPDATE = "/update";
    public static final String METHOD_ADD = "/add";
    public static final String METHOD_LIST = "/list";
    public static final String METHOD_PAGE = "/page";
    public static final String METHOD_UPDATE_STATUS = "/updateStatus";
    public static final String METHOD_PAGE_GROUP_COUNT = "/pageGroupCount";
    public static final String METHOD_DETAIL_NAME = "详情";
    public static final String METHOD_DELETE_NAME = "删除";
    public static final String METHOD_UPDATE_NAME = "/更新";
    public static final String METHOD_ADD_NAME = "/新增";
    public static final String METHOD_LIST_NAME = "列表";
    public static final String METHOD_PAGE_NAME = "列表（分页）";
    public static final String METHOD_PAGE_GROUP_COUNT_NAME = "列表分组（分页）";

    public static final String METHOD_UPDATE_STATUS_NAME = "更新状态";


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

    protected void setCache(String key, String value) {
        JSONRedisUtil.set(key, value);
    }

    protected void setCache(String key, String field, String value) {
        JSONRedisUtil.hSet(key, field, value);
    }

//    protected void setCache(String key, String field, String value, int time) {
//        JSONRedisUtil.hSetWithTime(key, field, value, time);
//    }

    protected void setCache(String key, String field, Object value, int time) {
        JSONRedisUtil.hSetWithTime(key, field, value, time);
    }

    protected String getCache(String key, String field) {
        return JSONRedisUtil.hGet(key, field, String.class);
    }

    protected <T> T getCache(String key, String field, Class<T> clazz) {
        return JSONRedisUtil.hGet(key, field, clazz);
    }

    protected <T> List<T> getCacheArray(String key, Class<T> clazz) {
        return JSONRedisUtil.parseArrayAllField(key, clazz);
    }

    protected String getCache(String key) {
        return JSONRedisUtil.get(key, String.class);
    }

    protected void setCache(String key, String value, int time) {
        JSONRedisUtil.hSetWithTime(key, value, time);
    }

    protected String showNull(String value) {
        if (StringUtil.isNotEmpty(value)) {
            return null;
        } else {
            return value;
        }
    }
}
