package com.educiot.recruit.server.wechat.aop;

import java.lang.annotation.*;

/**
 * @author qgs
 * @Description: 日志管理, 可以注解上方法说明
 * @date 2018年10月17日
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoggerManage {

    String description() default "";
}
