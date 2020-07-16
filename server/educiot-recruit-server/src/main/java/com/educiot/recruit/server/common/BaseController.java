package com.educiot.recruit.server.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.educiot.recruit.common.api.APIRequest;
import com.educiot.recruit.common.api.UserInfo;
import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.base.MyExcption;
import com.educiot.recruit.common.constant.Constant;
import com.educiot.recruit.common.util.JSONRedisUtil;
import com.educiot.recruit.common.util.StringUtil;
import com.educiot.recruit.data.LoginUser;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

//import com.educiot.recruit.common.util.RedisUtil;
//import com.educiot.recruit.common.util.RedisUtil3;
//import com.educiot.recruit.data.LoginUser;

/**
 * 基础controller工具
 *
 * @author Pan Juncai
 * @version 1.00.1.8.O
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

    protected APIRequest getAPIRequest() throws MyExcption {
        if (getLoginUser() != null) {
            return super.getAPIRequest(getLoginUser().getToken());
        } else {
            throw new MyExcption(CodeMsg.TOKEN_NOT_EXIST);
        }
    }

    protected void destoryAPIRequest() {
        super.destoryAPIRequest(getLoginUser().getToken());
    }
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
        //解析token
        LoginUser userInfo = JSONRedisUtil.hGet(Constant.RedisKey.TOKEN_KEY, token, LoginUser.class);
        return userInfo;
    }

    protected void setLoginUser(LoginUser loginUser) {
        JSONRedisUtil.hSetWithTime(Constant.RedisKey.TOKEN_KEY, loginUser.getToken(), loginUser, Constant.Sys.TOKEN_EXPIRE_TIME);
    }

    protected void removeLoginUser(String token) {
        if (!StringUtil.isEmpty(token)) {
            JSONRedisUtil.delHSet(Constant.RedisKey.TOKEN_KEY, token);
        }
    }
}
