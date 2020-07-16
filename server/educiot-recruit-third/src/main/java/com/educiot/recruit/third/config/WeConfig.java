package com.educiot.recruit.third.config;

import com.qq.weixin.mp.aes.*;
import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Random;

@Data
@Configuration
public class WeConfig {

    private String token;

    //时间戳
    private String timestamp;

    //随机数
    private String nonce;

    //随机字符串
    private String echostr;

//    @Getter
//    @Value("${wechat.app.id}")
//    private String appId;
//
//    @Getter
//    @Value("${wechat.app.secret}")
//    private String appSecret;


    //微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
    private String signature;


    // 随机生成16位字符串
    String getRandomStr() {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 16; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }


}
