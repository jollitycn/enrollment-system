package com.educiot.recruit.data.common;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.common.util.JSONRedisUtil;

/**
 * @author Administrator
 */
public class MyServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    protected Boolean exists(String sysRegions) {
        return JSONRedisUtil.exists(sysRegions);
    }

    protected String getCache(String sysRegions) {
        return JSONRedisUtil.parse(sysRegions, String.class);
    }
}
