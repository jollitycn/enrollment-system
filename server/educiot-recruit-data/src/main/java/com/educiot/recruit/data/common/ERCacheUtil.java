package com.educiot.recruit.data.common;

import com.educiot.common.api.APIRequest;
import com.educiot.common.constant.Constant;
import com.educiot.common.util.JSONRedisUtil;
import com.educiot.common.util.StringUtil;
import com.educiot.recruit.data.service.ISysNationService;
import com.educiot.recruit.data.service.ISysRegionService;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 系统缓存类型
 * @author JasonHongcn
 */
@Component
@Log4j2
@Data
public class ERCacheUtil extends CacheUtil {


    @Autowired
    private ISysRegionService sysRegionService;

    @Autowired
    private ISysNationService sysNationService;

    public void init() throws Exception {
        super.init(3);
        GlobalCache cache = new GlobalCache();
        //  String token = user.getToken();
//        Object obj = RedisUtil.get(GLOBAL_CACHE);
//        if (obj != null) {
//            cache = (GlobalCache) obj;
//        }
        // RedisUtil.switchJedis(Protocol.DEFAULT_DATABASE);
        // log.info("初始化");
        // cache.loginUser = user;
        stepProcessStart("默认api接口");
        if (JSONRedisUtil.hGet(Constant.RedisKey.GLOBAL_API, Constant.Sys.DEFAULT_TOKEN, APIRequest.class)==null) {
//            cache.sysRegions = sysRegionService.getAllList();
            stepProcessEnd(Constant.RedisKey.GLOBAL_API, Constant.Sys.DEFAULT_TOKEN, new APIRequest(), "默认api接口");
        }
        //   setAPIRequest(Constant.Sys.DEFAULT_TOKEN, new APIRequest());
        stepProcessStart("系统省市区");
        if (StringUtil.isEmpty(JSONRedisUtil.get(Constant.RedisKey.SYS_REGIONS, String.class))) {
            cache.sysRegions = sysRegionService.getAllList();
            stepProcessEnd(Constant.RedisKey.SYS_REGIONS, cache.sysRegions, "系统省市区");
        }
        stepProcessStart("系统民族信息");
        if (StringUtil.isEmpty(JSONRedisUtil.get(Constant.RedisKey.SYS_NATIONS, String.class))) {
            cache.sysNations = sysNationService.list();
            stepProcessEnd(Constant.RedisKey.SYS_NATIONS, cache.sysNations, "系统民族信息");
        }
        // stepProcessStart("登录信息");
        //stepProcessEnd(LOGIN_USER + cache.loginUser.token, cache.loginUser, "登录信息");
        stepEnd("加载完毕！");
    }


//    /**
//     * 根据token删除用户信息
//     * @param token
//     */
//    public void destroy(String token) {
//        Object obj = RedisUtil.get(LOGIN_USER + token);
//    }


}
