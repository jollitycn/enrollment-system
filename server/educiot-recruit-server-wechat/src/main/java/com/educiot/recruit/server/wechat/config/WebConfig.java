package com.educiot.recruit.server.wechat.config;


import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


/**
 * web 配置类
 *
 * @author Xuchao
 * @date 2019年11月12日 17:00
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Value("${spring.resources.static-locations}")
    private String fileImageAddress;

    @Value("${spring.mvc.static-path-pattern}")
    private String mvcPath;

    /**
     * 静态资源映射
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //swagger配置 不使用可注释掉
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

        //静态资源访问
        registry.addResourceHandler(mvcPath).addResourceLocations(fileImageAddress);
    }

    /**
     * 重写增加自定义拦截器的注册，某一个拦截器需要先注册进来，才能工作
     *
     * @param registry 拦截器注册
     * @author Pan Juncai
     * @date 2020/5/9 15:31
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> ignoreUrl = Lists.newArrayList();
        ignoreUrl.add("/error");
        ignoreUrl.add("/**/*swagger*/**");
        ignoreUrl.add("/favicon.ico");
        ignoreUrl.add("/login/**");
        // 上传的文件访问放行
        ignoreUrl.add("/static/**");
        registry.addInterceptor(new WechatTokenInterceptor()).addPathPatterns("/**").excludePathPatterns(ignoreUrl);
    }

}
