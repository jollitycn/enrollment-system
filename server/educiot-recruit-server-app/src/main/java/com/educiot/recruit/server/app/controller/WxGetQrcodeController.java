package com.educiot.recruit.server.app.controller;


import com.educiot.common.base.Result;
import com.educiot.common.util.WeiXinUtil;
import com.educiot.recruit.server.app.common.BaseController;
import com.educiot.recruit.server.app.common.LoginUser;
import com.educiot.recruit.server.app.config.WxGetQrcodeConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(tags = {"生成小程序码"}, value = "生成小程序码")
@RestController
@RequestMapping("/getQrcode")
public class WxGetQrcodeController extends BaseController {

    @Value("${image.address}")
    private String uploadPath;

    @GetMapping("/generateQrCode")
    @ApiOperation(value = "生成小程序码")
    public Result getConsultationId(HttpServletRequest request) {
        String access_token = WeiXinUtil.getAccess_token_wechat();
        LoginUser loginUser = getLoginUser();
        String codeUrl = WxGetQrcodeConfig.getminiqrQr(access_token, uploadPath, request,loginUser);
        return Result.success(codeUrl);
    }


}
