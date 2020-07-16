package com.educiot.recruit.data.common.cache;

import com.educiot.common.api.APIRequest;
import com.educiot.common.constant.Constant;
import com.educiot.common.util.JSONRedisUtil;
import com.educiot.common.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;


/**
 * 系统缓存类型
 * @author JasonHongcn
 */
public class CacheUtil  {

//    @Autowired
//    public void SetISysNationService(ISysNationService sysNationService) {
//        Cache.testService =this.testService;
//    }


    //    private ;

    int total = 3;

    int i = 1;

    //TODO 增加消息通知
    protected void stepProcessStart(String message) {
        // log.info("loading:" + i + "/" + total + ":" + message);
    }


    protected void stepProcessEnd(String key, Object obj, String message) {
        // log.info(i + "/" + total + ":" + message);
        JSONRedisUtil.set(key, obj);
    }

    protected void stepProcessEnd(String key, String field, Object obj, String message) {
        //log.info(i + "/" + total + ":" + message);
        JSONRedisUtil.hSet(key, field, obj);
    }

    protected void stepEnd(String message) {
        // log.info(message);
        //set(key, obj);
    }

    protected void init(int total) throws Exception {
        this.total = total;
        this.i = 1;
    }


//    /**
//     * 根据token删除用户信息
//     * @param token
//     */
//    public void destroy(String token) {
//        Object obj = RedisUtil.get(LOGIN_USER + token);
//    }


}
