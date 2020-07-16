package com.educiot.recruit.third.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import net.sf.json.JSONObject;

public class WxUtils {
    private static String APP_ID = "XXXXXX";
    private static String AppSecret = "XXXXXXXXXXXX";
    public static String APP_DOMAIN ="www.oacloudapp.cn";
    public static String getTicket(){
        String urlToken="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APP_ID+"&secret="+AppSecret+"";
        String backToken = sendGet(urlToken,"utf-8",60000);
        System.out.println("token:"+backToken);
        String accessToken = (String) JSONObject.fromObject(backToken).get("access_token");
        String url="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi";
        String backTicket = sendGet(url,"utf-8",60000);
        System.out.println("Ticket:"+backTicket);
        String ticket = (String) JSONObject.fromObject(backTicket).get("ticket");
        return ticket;
    }
    /**
     *
     * @title  getAccessToken
     * @Description 获取访问令牌
     * @author sss
     * @Date 2018-5-18上午11:07:18
     * @return
     */
    public static String getAccessToken(){
        String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APP_ID+"&secret="+AppSecret+"";
        String backData = sendGet(url,"utf-8",10000);
        String accessToken = (String) JSONObject.fromObject(backData).get("access_token");
        return accessToken;
    }
    /**
     *
     * @title  sendGet
     * @Description
     * @author sss
     * @Date 2018-5-18上午11:15:33
     * @param url
     * @param charset
     * @param timeout
     * @return
     */
    public static String sendGet(String url, String charset, int timeout)
    {
        String result = "";
        try
        {
            URL u = new URL(url);
            try
            {
                URLConnection conn = u.openConnection();
                conn.connect();
                conn.setConnectTimeout(timeout);
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
                String line="";
                while ((line = in.readLine()) != null)
                {
                    result = result + line;
                }
                in.close();
            } catch (IOException e) {
                return result;
            }
        }
        catch (MalformedURLException e)
        {
            return result;
        }
        return result;
    }
}
