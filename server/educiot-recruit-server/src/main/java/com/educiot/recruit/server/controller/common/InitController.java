package com.educiot.recruit.server.controller.common;


//import com.educiot.recruit.data.cache.CacheUtil;
import com.educiot.recruit.server.common.BaseController;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 选择专业表 前端控制器
 * </p>
 *
 * @author Hongsifan
 * @since 2020-04-13
 */


@Configuration
@RestController
public class InitController extends BaseController implements InitializingBean {
  //  @Autowired
  //  private CacheUtil cacheUtil;

    //    @Autowired
//    RedisService redisService;
    @Override
    public void afterPropertiesSet() throws Exception {
        //cacheUtil.init();
    }
}
