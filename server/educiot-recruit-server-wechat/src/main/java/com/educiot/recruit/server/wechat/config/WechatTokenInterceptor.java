package com.educiot.recruit.server.wechat.config;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.common.constant.Constant;
import com.educiot.recruit.common.util.JSONRedisUtil;
import com.educiot.recruit.data.entity.vo.MiniProgramLoginVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 微信端token拦截
 *
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/5/9 15:28
 */
public class WechatTokenInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(WechatTokenInterceptor.class);

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object object) throws Exception {
        String requestUri = request.getRequestURI();
        if("/scan/prov".equals(requestUri)){
            return true;
        }
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
        MiniProgramLoginVO loginObj = JSONRedisUtil.hGet(Constant.RedisKey.WECHAT_TOKEN_KEY, token,MiniProgramLoginVO.class);
        if (null == loginObj) {
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
       // MiniProgramLoginVO loginVO = JSON.parseObject(loginObj.toString(), new TypeReference<MiniProgramLoginVO>() {
       // });
        if (null == loginObj) {
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

        logger.info("登录用户为：{}", loginObj.toString());
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
