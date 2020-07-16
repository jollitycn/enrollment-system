package com.educiot.common.api;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import static cn.hutool.json.JSONUtil.isJson;

public class HttpClientUtil {

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数,单个参数
     * @return URL 所代表远程资源的响应结果
     */

    public static JSONObject doGet(String url, Map<String, String> param) {

        System.out.println("urlllll:"+url);
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String resultString = "";
        JSONObject retJson = new JSONObject();
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            System.out.println("uri:"+uri);
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            System.out.println("httpGet:"+httpGet);
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }

            if (!resultString.isEmpty()&& isJson(resultString)) {
                retJson = JSONObject.parseObject(resultString);
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
            retJson.put("status", "102");
            retJson.put("msg", "发送GET请求出现异常：" + e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return retJson;
    }

    public static JSONObject doGet(String url) {
        return doGet(url, null);
    }

//    public static JSONObject sendGet(String url, String param) {
//        String result = "";
//        JSONObject retJson = new JSONObject();
//        BufferedReader in = null;
//        try {
//            URL realUrl = new URL(url);
//            // 打开和URL之间的连接
//            URLConnection connection = realUrl.openConnection();
//            // 设置通用的请求属性
//            connection.setRequestProperty("accept", "*/*");
//            connection.setRequestProperty("connection", "Keep-Alive");
//            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//            // 建立实际的连接
//            connection.connect();
//            // 获取所有响应头字段
//            // 定义 BufferedReader输入流来读取URL的响应
//            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
//            String line;
//            while ((line = in.readLine()) != null) {
//                result += line;
//            }
//            if (!result.isEmpty()&& isJson(result)) {
//                retJson = JSONObject.parseObject(result);
//            }
//        } catch (Exception e) {
//            System.out.println("发送GET请求出现异常！" + e);
//            e.printStackTrace();
//            retJson.put("status", "102");
//            retJson.put("msg", "发送GET请求出现异常：" + e);
//        }
//        // 使用finally块来关闭输入流
//        finally {
//            try {
//                if (in != null) {
//                    in.close();
//                }
//            } catch (Exception e2) {
//                e2.printStackTrace();
//            }
//        }
//        return retJson;
//    }

}
