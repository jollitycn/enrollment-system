package com.educiot.recruit.third.controller;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.common.api.base.Result;
import com.educiot.common.util.StringUtil;
import com.qq.weixin.mp.aes.SHA1;
import com.wx.utils.AuthUtil;
import com.wx.utils.Constants;
import com.wx.utils.JsSignUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Api(tags = {"微信扫一扫"}, value = "微信扫一扫")
@RestController
@RequestMapping("/scan")
public class ScanController {

    private Log log = LogFactory.getLog(this.getClass());

    @GetMapping("test")
    public Result test(String url, HttpServletRequest request) throws IOException {
        log.info("--------进入了方法-------");
        if (StringUtil.isEmpty(url)) {
            url = request.getRequestURL().toString();
        }

        Map<String, String> sign = JsSignUtil.sign(url);
//        ModelAndView mav = new ModelAndView("scan/scan");
//        mav.addObject("sign",sign);
        return Result.success(sign);
    }

    @GetMapping("/verifyUrl")
    @ApiOperation("验证微信配置信息")
    public Object verifyUrl(String signature, String token, String nonce, String timestamp, String echostr) throws Exception {
        log.info("token" + token + "," +
                        "nonce" + nonce + "," +
                        "timestamp" + timestamp + "," +
                        "signature" + signature + "," +
                        "echostr" + echostr);
        String signatureSha = SHA1.getSHA1(token, timestamp, nonce, echostr);
        if (!signatureSha.equals(signature)) {
            return true;
        } else {
            return false;
        }
    }
}

