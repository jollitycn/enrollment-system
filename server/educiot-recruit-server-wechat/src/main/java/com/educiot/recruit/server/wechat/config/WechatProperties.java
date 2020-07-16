package com.educiot.recruit.server.wechat.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 微信信息配置
 *
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/4/28 11:33
 */
@Data
@Configuration
public class WechatProperties {

    /**
     * 请求url
     */
    @Value("${wechat.REQUEST_URL}")
    private String REQUEST_URL;

    /**
     * 小程序 appId
     */
    @Value("${wechat.appid}")
    private String appid;

    /**
     * 小程序 appSecret
     */
    @Value("${wechat.secret}")
    private String secret;

    /**
     * 授权类型，此处只需填写 authorization_code
     */
    @Value("${wechat.grant_type}")
    private String grant_type;
}
