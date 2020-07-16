package com.educiot.recruit.server.app.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.educiot.recruit.common.api.APIRequest;
import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.constant.Constant;
import com.educiot.recruit.common.util.JSONRedisUtil;
import com.educiot.recruit.common.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

//import com.educiot.recruit.server.app.common.LoginUser;

//import com.educiot.recruit.common.util.RedisUtil;
//import com.educiot.recruit.common.util.RedisUtil3;
//import com.educiot.recruit.server.app.common.LoginUser;

/**
 * 基础controller工具
 *
 * @author Pan Juncai
 * @version 1.0
 * @date 2019/8/23 19:59
 */
@Log4j2
public class BaseController extends com.educiot.recruit.common.base.BaseController {
    protected APIRequest initAPI(String account, String pwd) {
//        APIRequest apiRequest = getAPIRequest(account);
//        if (apiRequest != null) {
//            removeLoginUser(apiRequest.getToken());
//        }
        APIRequest apiRequest = new APIRequest(account, pwd);
//        apiRequest.setAccount(account);
//        apiRequest.setPwd(pwd);
        return apiRequest;
    }

//    protected APIRequest apiRequest;

//    @Resource
//    public CacheUtil cacheUtil;

    /**
     * 在容器里获取token
     *
     * @return java.lang.String
     * @author Pan Juncai
     * @date 2019/8/27 17:08
     */
    protected String getToken() {
        ServletRequestAttributes servletReq = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == servletReq) {
            return null;
        }
        HttpServletRequest request = servletReq.getRequest();
        return request.getHeader(Constant.Sys.TOKEN_STR);
    }

    protected APIRequest getAPIRequest() {
        return JSONRedisUtil.hGet(Constant.RedisKey.APP_API, getLoginUser().getToken(), APIRequest.class);
    }

    protected void SetAPIRequest(String token, APIRequest request) {
        JSONRedisUtil.hSet(Constant.RedisKey.APP_API, token, request);
    }
//    @Resource
//    private IChargerService chargerService;

    /**
     * 在容器里获取登录用户信息
     *
     * @return com.insigma.htfc.common.vo.LoginUserVO
     * @author Pan Juncai
     * @date 2019/8/27 17:04
     */
    protected LoginUser getLoginUser() {
        String token = this.getToken();
        if (StringUtils.isEmpty(token)) {
            log.error(CodeMsg.TOKEN_NOT_EXIST.getMessage());
            return null;
        }
//        //解析token
//        if (!StringUtils.isEmpty(token) && token.equals(Constant.Sys.DEFAULT_TOKEN)) {
//            //TODO 先模拟返回
//            LoginUser user = new LoginUser();
//            APIInfo apiInfo = new APIInfo();
//            apiInfo.setSchoolId(1L);
//            apiInfo.setSchoolName("演示学校1");
//            apiInfo.setUserId(4766904249199051078L);
//            apiInfo.setUserName("张3351");
//            user.setCurrentSchoolId(1252559728180355074L);
//            user.setCurrentSemesterId(1L);
//            user.setSemesterName("测试学期");
//            user.setApiInfo(apiInfo);
//            ChargerExt charger = chargerService.getByApiId(user.getApiInfo().getUserId());
//            user.setCharger(charger);
//            user.setToken(token);
//            /*token = user.getToken();*/
//            setLoginUser(user);
//            return user;
////            return new LoginUser();
//        }
        //解析token
        LoginUser userInfo = JSONRedisUtil.hGet(Constant.RedisKey.APP_TOKEN_KEY, token,LoginUser.class);
//        LoginUser user = JSON.parseObject(userInfo.toString(), new TypeReference<LoginUser>() {
//        });
        return userInfo;
    }

    protected void setLoginUser(LoginUser loginUser) {
        JSONRedisUtil.hSetWithTime(Constant.RedisKey.APP_TOKEN_KEY, loginUser.getToken(), loginUser, Constant.Sys.TOKEN_EXPIRE_TIME);
    }

    protected void removeLoginUser(String token) {
        if (!StringUtil.isEmpty(token)) {
            JSONRedisUtil.delHSet(Constant.RedisKey.APP_TOKEN_KEY, token);
        }
    }
    //   private  RedisUtil RedisUtil;
//    protected void init() {
//
//    }
}
