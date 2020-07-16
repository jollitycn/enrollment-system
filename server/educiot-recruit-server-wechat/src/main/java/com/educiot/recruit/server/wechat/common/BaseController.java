package com.educiot.recruit.server.wechat.common;

import com.educiot.common.api.APIRequest;
import com.educiot.common.constant.Constant;
import com.educiot.common.util.JSONRedisUtil;
import com.educiot.recruit.data.entity.vo.MiniProgramLoginVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

//import com.educiot.common.util.RedisUtil;
//import com.educiot.common.util.RedisUtil3;
//import com.educiot.recruit.server.wechat.common.LoginUser;

/**
 * 基础controller工具
 *
 * @author Pan Juncai
 * @version 1.0
 * @date 2019/8/23 19:59
 */
@Log4j2
public class BaseController extends com.educiot.common.api.base.BaseController {

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

    protected APIRequest getAPIRequest(boolean useToken) {
        if (useToken) {
            return super.getAPIRequest(getToken());
        } else {
            return super.getAPIRequest(Constant.Sys.DEFAULT_TOKEN);
        }
    }

    protected APIRequest getAPIRequest() {
        return getAPIRequest(true);
    }

    /**
     * 在容器里获取登录用户信息
     *
     * @return com.insigma.htfc.common.vo.LoginUserVO
     * @author Pan Juncai
     * @date 2019/8/27 17:04
     */
//    protected LoginUser getLoginUser() {
//        String token = this.getToken();
//        //解析token
//        //解析token
////        if (!StringUtils.isEmpty(token) && token.equals(Constant.Sys.DEFAULT_TOKEN)) {
//        //TODO 先模拟返回
//        LoginUser user = new LoginUser();
////            APIInfo apiInfo = new APIInfo();
////            apiInfo.setSchoolId(1L);
////            apiInfo.setSchoolName("演示学校1");
//        user.setUserId(1255692727098052609L);
//        user.setUserName("小陈");
//        user.setCurrentSchoolId(1L);
//        user.setCurrentSemesterId(1L);
//        user.setSemesterName("测试学期");
//        user.setStudentPublicRelationId(1255692727706226690L);
//        user.setToken(token);
////            user.setApiInfo(apiInfo);
//        return user;
////            return new LoginUser();
////        }
//        //解析token
////        Object userInfo = RedisUtil.hGet(Constant.RedisKey.WECHAT_TOKEN_KEY, token);
////        return JSON.parseObject(userInfo.toString(), new TypeReference<LoginUser>() {
////        });
//    }

    /**
     * 获取小程序的登录信息
     *
     * @return com.educiot.recruit.data.entity.vo.MiniProgramLoginVO
     * @author Pan Juncai
     * @date 2020/5/8 16:46
     */
    protected MiniProgramLoginVO getLoginVO() {
        String token = this.getToken();
        //解析token
        if (StringUtils.isEmpty(token)) {
            return new MiniProgramLoginVO();
        }
        //解析token
        return JSONRedisUtil.hGet(Constant.RedisKey.WECHAT_TOKEN_KEY, token, MiniProgramLoginVO.class);
//        return JSON.parseObject(loginObj.toString(), new TypeReference<MiniProgramLoginVO>() {
//        });
    }

    protected void setLoginUser(LoginUser loginUser) {
        JSONRedisUtil.hSetWithTime(Constant.RedisKey.WECHAT_TOKEN_KEY, loginUser.getToken(), loginUser, Constant.Sys.TOKEN_EXPIRE_TIME);
    }


//    //   private  RedisUtil RedisUtil;
////    protected void init() {
////
////    }
}
