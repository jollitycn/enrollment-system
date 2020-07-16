package com.educiot.recruit.server.app.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Aspect
@Service
public class LoggerAdvice {

    private final Logger logger = LoggerFactory.getLogger(LoggerAdvice.class);

    @Before("within(com.educiot.recruit.server.app..*) && @annotation(lm)")
    public void addBeforeLogger(JoinPoint joinPoint, LoggerManage lm) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            logger.info("url={}---------->{}", request.getRequestURI(), "开始");
            parseParames();
        }
    }

    @AfterReturning("within(com.educiot.recruit.server.app..*) && @annotation(lm)")
    public void addAfterReturningLogger(JoinPoint joinPoint, LoggerManage lm) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            logger.info("url={}---------->{}", request.getRequestURI(), "结束");
        }
    }

    @AfterThrowing(pointcut = "within(com.educiot.recruit.server.app..*) && @annotation(lm)", throwing = "ex")
    public void addAfterThrowingLogger(JoinPoint joinPoint, Exception ex, LoggerManage lm) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            logger.info("url={}---------->异常：{}", request.getRequestURI(), ex.toString());
        }
    }

    private void parseParames() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            StringBuilder sb = new StringBuilder();
            Enumeration<String> requestKey = request.getParameterNames();
            while (requestKey.hasMoreElements()) {
                String key = requestKey.nextElement();
                sb.append("key:" + key + "," + "value:" + request.getParameter(key) + "   ");
            }
            logger.info("ip={}", request.getRemoteAddr());
            logger.info("requestParams:" + sb.toString());
        }
    }

}
