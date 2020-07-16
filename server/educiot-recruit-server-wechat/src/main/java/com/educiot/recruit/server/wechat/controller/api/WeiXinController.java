package com.educiot.recruit.server.wechat.controller.api;

import com.educiot.common.base.Result;
import com.educiot.common.util.WeiXinUtil;
import com.educiot.recruit.server.wechat.common.BaseController;
import com.educiot.recruit.server.wechat.util.CheckoutUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;

@Api(tags = {"微信API接口"}, value = "WeiXinController")
@Controller
@RequestMapping("/scan")
public class WeiXinController extends BaseController {

    @ApiOperation("获取微信签名接口")
    @GetMapping("/wx")
    @ResponseBody
    public Result wx(HttpServletRequest request,@RequestParam("urlSigna") String urlSigna) throws IOException {
        WeiXinUtil wx = new WeiXinUtil();
        urlSigna = URLDecoder.decode(urlSigna, "UTF-8");
        Map<String, Object> wxMap = wx.getSignature(urlSigna);
        wxMap.put("wxAppId", WeiXinUtil.APPID);
        return Result.success(wxMap);
    }

    @ApiOperation("验证token")
    @GetMapping("/prov")
    @ResponseBody
    public String prov(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("进入校验方法");
        boolean isGet = request.getMethod().toLowerCase().equals("get");
        if (isGet) {
            // 微信加密签名
            String signature = request.getParameter("signature");
            // 时间戳
            String timestamp = request.getParameter("timestamp");
            // 随机数
            String nonce = request.getParameter("nonce");
            // 随机字符串
            String echostr = request.getParameter("echostr");
            System.out.println(signature);
            System.out.println(timestamp);
            System.out.println(nonce);
            System.out.println(echostr);
            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
            if (signature != null && CheckoutUtil.checkSignature(signature, timestamp, nonce)) {
                System.out.println("校验成功");
                System.out.println("打印返回值："+echostr);
                try {
                    return echostr;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
