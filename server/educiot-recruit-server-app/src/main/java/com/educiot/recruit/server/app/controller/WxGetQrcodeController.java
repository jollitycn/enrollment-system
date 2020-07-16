package com.educiot.recruit.server.app.controller;


import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.educiot.recruit.common.api.HttpClientUtil;
import com.educiot.recruit.common.api.WeiXinUtils;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.entity.vo.ConsultationIdVO;
import com.educiot.recruit.server.app.common.BaseController;
import com.educiot.recruit.server.app.common.LoginUser;
import com.educiot.recruit.server.app.config.WxGetQrcodeConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Api(tags = {"生成小程序码"}, value = "生成小程序码")
@RestController
@RequestMapping("/getQrcode")
public class WxGetQrcodeController extends BaseController {

    @Value("${image.address}")
    private String uploadPath;

    @GetMapping("/generateQrCode")
    @ApiOperation(value = "生成小程序码")
    public Result getConsultationId(HttpServletRequest request) {
        String access_token = WeiXinUtils.getAccess_token_wechat();
        LoginUser loginUser = getLoginUser();
        String codeUrl = WxGetQrcodeConfig.getminiqrQr(access_token, uploadPath, request,loginUser);
        return Result.success(codeUrl);
    }


}
