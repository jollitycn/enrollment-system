package com.educiot.recruit.common.api;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.RandomStringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

@Log4j2
public class WeiXinUtils {

    //微信小程序
    public static final String APPID_C = "wx432dd970a92ecf22";
    public static final String APP_SECRET_C ="b4acb14b717e77ac7722514ca16344b0";

    //微信公众号
    public static final String APPID = "wx46835dce38c30468";
    public static final String APP_SECRET ="cbf729e6442ff544ea0eafbed9f50e49";
    public static final String ticket_token="Jkjpzg1fQAfAV32132";

    private static final Map map = new HashMap();


    /**
     * 获取公众号的accessToken
     * @return accessToken
     */
    public static String getAccess_token() {
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        System.out.println("测试String长度url:"+url);
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("grant_type", "client_credential");
        paramMap.put("appid", APPID);
        paramMap.put("secret", APP_SECRET);


        String result= HttpUtil.get(url,paramMap);
        JSONObject jsonobject=JSONObject.parseObject(result);
        System.out.println("获取Access_token："+jsonobject);
        return jsonobject.getString("access_token");
    }

    /**
     * 获取小程序的accessToken
     * @return accessToken
     */
    public static String getAccess_token_wechat() {
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        System.out.println("测试String长度url:"+url);
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("grant_type", "client_credential");
        paramMap.put("appid", APPID_C);
        paramMap.put("secret", APP_SECRET_C);


        String result= HttpUtil.get(url,paramMap);
        JSONObject jsonobject=JSONObject.parseObject(result);
        System.out.println("获取Access_token："+jsonobject);
        return jsonobject.getString("access_token");
    }

    /**
     * 获取jsapi_ticket
     * @author WFJ
     * @return
     */
    public static String getJsapiTicket(){
        String accessToken = "";
        accessToken = getAccess_token();
        System.out.println("打印accessToken值："+accessToken);
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("access_token", accessToken);
        paramMap.put("type", "jsapi");
        String result= HttpUtil.get(url,paramMap);
        JSONObject jsonobject=JSONObject.parseObject(result);
        return jsonobject.getString("ticket");

    }

    /**
     * 获取微信签名signature
     * @param url jsapi_ticket
     * @return signature
     * @throws IOException
     */
    public Map<String, Object> getSignature(String url) throws IOException {
        Map<String, Object> wxMap = new HashMap<String, Object>();
        String jsapi_ticket = "";
        Long timeJt = (Long) map.get("timeJt");
        jsapi_ticket = (String) map.get("jsapi_ticket");
        Long nowDateJt = System.currentTimeMillis();
        if (jsapi_ticket != null && timeJt != null && ((nowDateJt - timeJt) < 7200 * 1000L)) {
            System.out.println("jsapi_ticket 存在");
            System.out.println("nowDateJt - timeJt=="+(nowDateJt - timeJt));
        } else {
            jsapi_ticket = getJsapiTicket();
            System.out.println("jsapi_ticket 获取"+jsapi_ticket);
            map.put("jsapi_ticket",jsapi_ticket);
            map.put("timeJt",nowDateJt);
        }

        //随机字符串和时间戳
        //随机字符串
        String new_novestr = RandomStringUtils.randomAlphanumeric(16);
        //时间戳
        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String signature = "";
        // 拼接字符串
        String string1 = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + new_novestr + "&timestamp=" + timestamp + "&url=" + url;
        signature = CheckUtil.SHA1(string1);
        System.out.println("签名=="+signature);
        System.out.println("noncestr=="+new_novestr);
        System.out.println("timesTamp=="+timestamp);
        wxMap.put("noncestr", new_novestr);
        wxMap.put("timesTamp", timestamp);
        wxMap.put("signature", signature);
        return wxMap;
    }

    public static void main(String[] args) {

//        String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx46835dce38c30468&secret=cbf729e6442ff544ea0eafbed9f50e49";
//        JSONObject jsonobject = HttpClientUtil.doGet(url);
//        System.out.println(jsonobject);


        // 当无法识别页面编码的时候，可以自定义请求页面的编码
        String access_token = getAccess_token();

    }


}
