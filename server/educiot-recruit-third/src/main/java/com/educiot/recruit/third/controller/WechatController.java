package com.educiot.recruit.third.controller;


import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSONObject;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.common.constant.Constant;
import com.educiot.recruit.third.common.BaseController;
import com.educiot.recruit.third.config.WeConfig;
import com.educiot.recruit.third.util.Sign;
import com.educiot.recruit.third.util.WeiXinUtils;
import com.educiot.recruit.third.util.WxUtils;
import com.qq.weixin.mp.aes.SHA1;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 选择专业表 前端控制器
 * </p>
 *
 * @author Hongsifan
 * @since 2020-04-13
 */


@Api(tags = {"微信接口"}, value = "WechatController")
@RestController
@RequestMapping("/wechat")
@Configuration
@Log4j2
public class WechatController extends BaseController {

        @RequestMapping(value="/getWxConfig",method=RequestMethod.POST)
        @ResponseBody
        public ModelMap getWxConfig(HttpServletRequest request, ModelMap map){
            //http://localhost/WX/browser/member/membertrain.jsp
            String url = "http://"+ WxUtils.APP_DOMAIN+ request.getContextPath()+"/browser/member/membertrain.jsp";
            String ticket = WxUtils.getTicket();
            Map<String, String> sign = Sign.sign(ticket, url);
            for (Map.Entry entry : sign.entrySet()) {
                System.out.println(entry.getKey() + "," + entry.getValue());
            }
            map.put("wxConfig", sign);
            return map;
        }

    /**
     * Description:微信扫一扫接口 <BR>
     *
     * @author ran.chunlin
     * @date 2017年4月11日 上午10:07:35
     * @param request
     * @return
     * @throws Exception
     * @version 1.0
     */
    @RequestMapping(params = "method=getWechatSign", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> getWechatSign(HttpServletRequest request) throws Exception {
        /* 返回的json数据 */
        Map<String, Object> jsonMap = new HashMap<>();
        // 构成子数据map
        Map<String, Object> subJsonMap = new HashMap<>();

        // 1.获取参数
        String url = showNull(request.getParameter("url"));
        String t = showNull(request.getParameter("t"));
        String appId = showNull(request.getParameter("appId"));
        String appSecret = showNull(request.getParameter("appSecret"));
        if (url == null || t == null || appId == null || appSecret == null) {
            return json4Map(jsonMap, subJsonMap, "参数为空", STATUSCODE_FAILED_BADINPUT_PARAM);
        } else {
            String accessToken = WeiXinUtils.getAccessToken(appId, appSecret);
            String ticket = WeiXinUtils.getTicket(accessToken);
            Long timestamp = System.currentTimeMillis() / 1000;
            String nonceStr = RandomStringUtils.randomAlphanumeric(16);
            String sign = getSign(ticket, nonceStr, timestamp, url);
            subJsonMap.put("result", "1");
            subJsonMap.put("timestamp", timestamp);
            subJsonMap.put("nonceStr", nonceStr);
            subJsonMap.put("appId", appId);
            subJsonMap.put("sign", sign);
        }
        return json4Map(jsonMap, subJsonMap, "获取sign成功", STATUSCODE_SUCCESS);
    }


    public static String getSign(String jsapi_ticket, String noncestr, Long timestamp, String url)
            throws NoSuchAlgorithmException {
        String shaStr = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr + "×tamp=" + timestamp + "&url="
                + url;
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(shaStr.getBytes());
        StringBuffer signature = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            signature.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        return signature.toString();
    }

    @GetMapping("/getConfig")
    @ApiOperation("获取微信配置信息")
    public Result getConfig() throws Exception {
        String timestamp = String.valueOf(System.currentTimeMillis()) ;
        String token = Constant.Sys.DEFAULT_TOKEN;
//        String nonce = "xxxxxx";
        String appId = "wxc19a680911185199";
        String encodingAesKey = "RJunhluYtvox3crH31eCphpNiiKHfqGAVJoz9lNY7Of";

        WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
        WeConfig config = pc.getConfig(timestamp);
        System.out.println("加密后: " + config);
        return Result.success(config);
    }

    @GetMapping("/verifyUrl")
    @ApiOperation("验证微信配置信息")
    public Object verifyUrl(String signature, String token, String nonce, String timestamp,String echostr) throws Exception {
        log.info(
                        "token" + token + "," +
                        "nonce" + nonce + "," +
                        "timestamp" + timestamp + "," +
                        "signature" + signature + "," +
                        "echostr" + echostr
        );
        String signatureSha = SHA1.getSHA1(token, timestamp, nonce, echostr);

        if (!signatureSha.equals(signature)) {
            return true;
        }else{
            return false;
        }
    }

}
