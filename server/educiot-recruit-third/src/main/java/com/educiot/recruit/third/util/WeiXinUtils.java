package com.educiot.recruit.third.util;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.educiot.recruit.common.util.HttpClientUtil;
import net.sf.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class WeiXinUtils {


    /**
     * Description: 获取微信公众号token<BR>
     *
     * @author dsn
     * @date 2018年9月21日 上午9:53:26
     * @param appid
     * @param secret
     * @return
     * @version 1.0
     */
    public static String getAccessToken(String appid, String secret) throws Exception {
        String token = "";
        String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid
                + "&secret=" + secret;
        String str = HttpClientUtil.doPost(token_url);//.httpsRequest(, "POST");
      JSONObject result=  JSONObject.fromObject(str);
        if (result.get("access_token") != null) {
            token = result.get("access_token").toString();
        }
        return token;
    }

    /**
     * Description: 获取微信ticket<BR>
     *
     * @author dsn
     * @date 2018年9月21日 上午9:54:03
     * @param token
     * @return
     * @version 1.0
     */
    public static String getTicket(String token) throws Exception {
        if ("".equalsIgnoreCase(token) || null == token) {
            return "";
        }
        String ticket_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + token + "&type=jsapi";
        String str = HttpClientUtil.doPost(ticket_url);//.httpsRequest(, "POST");
        JSONObject result=  JSONObject.fromObject(str);
        return result.get("ticket").toString();

    }
}
