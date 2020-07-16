package com.educiot.recruit.server.app.exception;

import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * @program: server
 * @description: 异常过滤器
 * @author: XuChao
 * @create: 2020-04-23 17:02
 **/
@Component
public class LedgerReportFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req=(HttpServletRequest)request;
        String contentType=req.getHeader("Content-Type");



        //对于文件类型请求，不进行body转换
        //multipart/form-data; boundary=----WebKitFormBoundaryEmdQR6vGcIq9fCrU
        if(StrUtil.isNotBlank(contentType)&&contentType.contains("multipart/form-data")){
            chain.doFilter(request, response);
            return;
        }

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
        chain.doFilter(requestWrapper, response);
    }

    @Override
    public void destroy() {

    }


}
