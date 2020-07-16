package com.educiot.recruit.data.cache;

import com.educiot.common.api.APIRequest;
import com.educiot.common.constant.Constant;
import com.educiot.common.util.JSONRedisUtil;
import com.educiot.common.util.StringUtil;
import com.educiot.recruit.data.entity.vo.ProvinceVO;
import com.educiot.recruit.data.service.IProvinceService;
import com.educiot.recruit.data.service.ISysNationService;
import com.educiot.recruit.data.service.ISysRegionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 系统缓存类型
 * @author JasonHongcn
 */
@Component
@Log4j2
public class CacheUtil {

//    @Autowired
//    public void SetISysNationService(ISysNationService sysNationService) {
//        Cache.testService =this.testService;
//    }


    //    private ;

    int total = 3;

    int i = 1;

    //TODO 增加消息通知
    private void stepProcessStart(String message) {
        // log.info("loading:" + i + "/" + total + ":" + message);
    }

    @Autowired
    private ISysRegionService sysRegionService;
    @Autowired
    private IProvinceService provinceService;

    @Autowired
    private ISysNationService sysNationService;

    private void stepProcessEnd(String key, Object obj, String message) {
        // log.info(i + "/" + total + ":" + message);
        JSONRedisUtil.set(key, obj);
    }

    private void stepProcessEnd(String key, String field, Object obj, String message) {
        //log.info(i + "/" + total + ":" + message);
        JSONRedisUtil.hSet(key, field, obj);
    }

    private void stepEnd(String message) {
        // log.info(message);
        //set(key, obj);
    }

    public void init() throws Exception {
       // GlobalCache cache = new GlobalCache();
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
        i++;
        //   setAPIRequest(Constant.Sys.DEFAULT_TOKEN, new APIRequest());
        stepProcessStart("系统省市区");
        if ( JSONRedisUtil.parseArrayAllField(Constant.RedisKey.PROVINCE, ProvinceVO.class)==null) {
          //  cache.sysRegions = sysRegionService.getAllList();
            List<ProvinceVO> provinces= provinceService.getAllList();
            if(provinces!=null) {
                for (ProvinceVO vo : provinces) {
                    stepProcessEnd(Constant.RedisKey.PROVINCE, String.valueOf(vo.getId()), vo, "系统省市区");
                }
            }
        }
        i++;
        stepProcessStart("系统民族信息");
        if (StringUtil.isEmpty(JSONRedisUtil.get(Constant.RedisKey.SYS_NATIONS, String.class))) {
           // cache.sysNations = sysNationService.list();
            stepProcessEnd(Constant.RedisKey.SYS_NATIONS, sysNationService.list(), "系统民族信息");
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
