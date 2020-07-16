package com.educiot.recruit.server.config;


import com.alibaba.fastjson.JSONObject;
import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.common.constant.Constant;
import com.educiot.recruit.common.util.JSONRedisUtil;
import com.educiot.recruit.data.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * PC端token拦截
 *
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/5/9 15:28
 */
public class PCTokenInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(PCTokenInterceptor.class);

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object object) throws Exception {
        String requestUri = request.getRequestURI();
        logger.info("请求uri为：---> {}", requestUri);
        String token = request.getHeader(Constant.Sys.TOKEN_STR);
        if (null == token) {
            // 未登录:重置response
            response.reset();
            //设置编码格式
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");

            PrintWriter pw = response.getWriter();
            pw.write(JSONObject.toJSONString(Result.error(CodeMsg.TOKEN_NOT_EXIST)));
            pw.flush();
            pw.close();
            return false;
        }

        //解析token
        LoginUser loginVO = JSONRedisUtil.hGet(Constant.RedisKey.TOKEN_KEY, token, LoginUser.class);
        if (null == loginVO) {
            // 未登录:重置response
            response.reset();
            //设置编码格式
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");

            PrintWriter pw = response.getWriter();
            pw.write(JSONObject.toJSONString(Result.error(CodeMsg.TOKEN_NOT_EXIST)));
            pw.flush();
            pw.close();
            return false;
        }

        // 转换成登录用户
        // LoginUser loginVO = JSON.parseObject(loginObj.toString(), new TypeReference<LoginUser>() {});
        if (null == loginVO) {
            // 未登录:重置response
            response.reset();
            //设置编码格式
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");

            PrintWriter pw = response.getWriter();
            pw.write(JSONObject.toJSONString(Result.error(CodeMsg.TOKEN_NOT_EXIST)));
            pw.flush();
            pw.close();
            return false;
        }

        logger.info("登录用户为：{}", loginVO.toString());
        return true;
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
                           ModelAndView modelAndView) {

    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) {

    }
}
