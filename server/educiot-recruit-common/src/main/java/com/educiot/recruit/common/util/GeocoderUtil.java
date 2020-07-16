package com.educiot.recruit.common.util;

import com.alibaba.fastjson.JSONObject;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 逆地址解析工具类
 *
 * @author liuhao
 * @date Created in 2020/3/9
 */
@Component
@PropertySource(value = "classpath:map.properties")
public class GeocoderUtil {

    private String uri;

    private String requestPath;

    private String secretKey;

    private String key;

    @Value(value = "${uri}")
    public void setUri(String uri) {
        this.uri = uri;
    }

    @Value(value = "${requestPath}")
    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    @Value(value = "${secretKey}")
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Value(value = "${key}")
    public void setKey(String key) {
        this.key = key;
    }

    public JSONObject getAdcode(String lat, String lng) {

        //拼接请求路径
        String segment = requestPath + "key=" + key + "&location=" + lat + "," + lng + secretKey;

        //获取拼接后字符串的MD5值
        String sig = StringUtil.MD5(segment);

        //获取最终请求连接
        String finalRequest = uri + requestPath + "key=" + key + "&location=" + lat + "," + lng + "&sig=" + sig;

        String response = HttpClientUtil.doGet(finalRequest);

        JSONObject obj = JSONObject.parseObject(response);

        String message = (String) obj.get("message");

        if (!message.equals("query ok")) {
            JSONObject errorMsg = new JSONObject();
            errorMsg.put("status", "地理位置不正确");
            return errorMsg;
        } else {
            return obj.getJSONObject("result").getJSONObject("ad_info");
        }
    }

    public double getDistanceMeter(GlobalCoordinates gpsFrom, GlobalCoordinates gpsTo, Ellipsoid ellipsoid) {

        //创建GeodeticCalculator，调用计算方法，传入坐标系、经纬度用于计算距离
        GeodeticCurve geoCurve = new GeodeticCalculator().calculateGeodeticCurve(ellipsoid, gpsFrom, gpsTo);

        return geoCurve.getEllipsoidalDistance();
    }
}
