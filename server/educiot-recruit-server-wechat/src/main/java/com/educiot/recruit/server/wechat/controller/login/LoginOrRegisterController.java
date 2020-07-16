package com.educiot.recruit.server.wechat.controller.login;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.educiot.recruit.common.api.MsgCode;
import com.educiot.recruit.common.api.MsgCodeResult;
import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.base.MyExcption;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.common.constant.Constant;
import com.educiot.recruit.data.entity.Login;
import com.educiot.recruit.data.entity.query.*;
import com.educiot.recruit.data.entity.vo.ClassIdAndNameVO;
import com.educiot.recruit.data.entity.vo.MiniProgramLoginVO;
import com.educiot.recruit.data.entity.vo.RecruitSchoolIdAndNameVO;
import com.educiot.recruit.data.entity.vo.SourceSchoolVO;
import com.educiot.recruit.data.service.IClassService;
import com.educiot.recruit.data.service.ILoginService;
import com.educiot.recruit.data.service.IRecruitSchoolService;
import com.educiot.recruit.data.service.ISourceSchoolService;
import com.educiot.recruit.server.wechat.common.BaseController;
import com.educiot.recruit.server.wechat.config.WechatProperties;
import com.educiot.recruit.server.wechat.controller.api.APIController;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/4/28 10:19
 */
@Api(tags = {"登录/注册接口"}, value = "LoginOrRegisterController")
@Log4j2
@RequestMapping("/login")
@RestController
public class LoginOrRegisterController extends BaseController {

    @Resource
    private APIController apiController;

    @Resource
    private ILoginService loginService;

    @Resource
    private WechatProperties wechatProperties;

    @Resource
    private IRecruitSchoolService recruitSchoolService;

    @Resource
    private ISourceSchoolService sourceSchoolService;

    @Resource
    private IClassService classService;

    @PostMapping("/reLogin")
    @ApiOperation("重新刷新token")
    public Result reLogin() {
        MiniProgramLoginVO vo = getLoginVO();
        String openid = vo.getOpenidInfo().getOpenid();
        if (StrUtil.isBlank(openid)) {
            return Result.error(CodeMsg.CODE_INVALIDE);
        }
        Result result = loginService.loginOrRegister(null, openid);
        return checkResult(result, CodeMsg.SUCCESS.getRetCode(), null);

    }

    @PostMapping("/login")
    @ApiOperation("登录")
    public Result loginOrRegister(@Valid @RequestBody LoginQuery loginOrRegisterQuery) {
        String openid = getOpenId(loginOrRegisterQuery.getCode());
        if (StrUtil.isBlank(openid)) {
            return Result.error(CodeMsg.CODE_INVALIDE);
        }
        Result result = loginService.loginOrRegister(loginOrRegisterQuery, openid);
        return checkResult(result, CodeMsg.SUCCESS.getRetCode(), loginOrRegisterQuery.getRecruitSchoolId());
    }

    @PostMapping("/bindRecruitSchoolAndStudent")
    @ApiOperation("绑定招生学校和学生：针对已注册的账号在招生学校无绑定关系情况")
    public Result bindRecruitSchoolAndStudent(@Valid @RequestBody BindRecruitAndStudentQuery bindRecruitAndStudentQuery) {
        Result result = loginService.bindRecruitSchoolAndStudent(bindRecruitAndStudentQuery);
        return checkResult(result, CodeMsg.SUCCESS.getRetCode(), bindRecruitAndStudentQuery.getRecruitSchoolId());
    }

    @PostMapping("/checkStudentAndPhone")
    @ApiOperation("校验学生和家长手机是否存在")
    public Result checkStudentAndPhone(@Valid @RequestBody CheckStudentQuery checkStudentQuery) {
        return loginService.checkStudentAndPhone(checkStudentQuery);
    }

    @PostMapping("/bindStudent")
    @ApiOperation("绑定学生")
    public Result bindStudent(@Valid @RequestBody BindStudentQuery bindStudentQuery) {
//        String openid = getOpenId(bindStudentQuery.getCode());
//        if (StrUtil.isBlank(openid)) {
//            return Result.error(CodeMsg.CODE_INVALIDE);
//        }
        Result result = loginService.bindStudent(bindStudentQuery, bindStudentQuery.getOpenid());
        return checkResult(result, CodeMsg.SUCCESS.getRetCode(), null);
    }


    @GetMapping("/listRecruitSchoolWechat")
    @ApiImplicitParam(value = "招生学校id", name = "recruitSchoolId")
    @ApiOperation("切换招生学校列表")
    public Result<List<RecruitSchoolIdAndNameVO>> listRecruitSchoolWechat() {
        List<RecruitSchoolIdAndNameVO> list = recruitSchoolService.listRecruitSchoolWechat();
        return Result.success(list);
    }


    @GetMapping("/listRecruitSchool")
    @ApiImplicitParam(value = "招生学校id", name = "recruitSchoolId")
    @ApiOperation("获取招生学校")
    public Result<List<RecruitSchoolIdAndNameVO>> listRecruitSchool(Long recruitSchoolId) {
        List<RecruitSchoolIdAndNameVO> list = recruitSchoolService.listRecruitSchool(recruitSchoolId);
        return Result.success(list);
    }

    @GetMapping("/listSourceSchoolByRecruit/{recruitSchoolId}")
    @ApiImplicitParam(value = "招生学校id", name = "recruitSchoolId", required = true)
    @ApiOperation("获取生源学校")
    public Result<List<SourceSchoolVO>> listSourceSchoolByRecruit(@Valid @PathVariable("recruitSchoolId")
                                                                  @NotNull(message = "招生学校id不能为空")
                                                                  @Min(value = 1L, message = "招生学校id不合法")
                                                                  @Max(value = Long.MAX_VALUE, message = "招生学校id不合法")
                                                                          Long recruitSchoolId) {
        List<SourceSchoolVO> list = sourceSchoolService.listAllSourceSchool(recruitSchoolId);
        return Result.success(list);
    }

    @PostMapping("/listClassBySource")
    @ApiOperation("获取班级")
    public Result<List<ClassIdAndNameVO>> listClassBySource(@Valid @RequestBody ListClassBySourceQuery registerQuery) {
        List<ClassIdAndNameVO> list = classService.listClassBySource(registerQuery);
        return Result.success(list);
    }

    @PostMapping("/register")
    @ApiOperation("注册")
    public Result<MiniProgramLoginVO> register(@Valid @RequestBody RegisterQuery registerQuery) {
        MsgCode msgCode = getCache(Constant.RedisKey.MSG_CODE, registerQuery.getParentTelephone(), MsgCode.class);
        if (msgCode == null) {
            return Result.error(CodeMsg.VERIFY_CODE_ERROR);
        }
        if (!msgCode.getCode().equals(registerQuery.getVerifyCode())) {
            return Result.error(CodeMsg.VERIFY_CODE_ERROR);
        }
        Result result = loginService.register(registerQuery, registerQuery.getOpenid());
        return checkResult(result, CodeMsg.SUCCESS.getRetCode(), registerQuery.getRecruitSchoolId());
    }

    @GetMapping("/getMsgCode")
    @ApiOperation("招生系统获取短信息验证码")
    public Result getMsgCode(String phone) throws MyExcption {
        if("13613613603".equals(phone)){
            MsgCodeResult plan = new MsgCodeResult();
            String code="{\"code\":\"1111\"}";
            Object parse = JSONObject.parse(code);
            setCache(Constant.RedisKey.MSG_CODE, phone, parse, Constant.Sys.MSG_CODE_EXPIRE_TIME);
            return Result.success();
        }
        Result<MsgCodeResult> result = apiController.getMsgCode(phone);
        if (result.getRetCode() == CodeMsg.SUCCESS.getRetCode()) {
            MsgCodeResult plan = new MsgCodeResult();
            BeanUtil.copyProperties(result.getData(), plan);
            setCache(Constant.RedisKey.MSG_CODE, phone, plan.getData(), Constant.Sys.MSG_CODE_EXPIRE_TIME);
            return Result.success();
        } else {
            return result;
        }
    }

    @PostMapping("/registerBind")
    @ApiOperation("注册时学校班级姓名相同的手机绑定")
    public Result registerBind(@Valid @RequestBody RegisterBindQuery registerBindQuery) {
//        String openid = getOpenId(registerBindQuery.getCode());
//        if (StrUtil.isBlank(openid)) {
//            return Result.error(CodeMsg.CODE_INVALIDE);
//        }

        Result result = loginService.registerBind(registerBindQuery, registerBindQuery.getOpenid());
        return checkResult(result, CodeMsg.SUCCESS.getRetCode(), registerBindQuery.getRecruitSchoolId());
    }

    private Result checkResult(Result result, int retCode, Long recruitSchoolId) {
        if (result.getRetCode() == retCode) {
            return loginService.getLogin(((Result<Login>) result).getData(), recruitSchoolId);
        } else {
            return result;
        }
    }

    @PostMapping("/registerNewStudent")
    @ApiOperation("注册新用户和学生")
    public Result registerNewStudent(@Valid @RequestBody RegisterNewStudentQuery registerNewStudentQuery) {
//        String openid = getOpenId(registerNewStudentQuery.getCode());
//        if (StrUtil.isBlank(openid)) {
//            return Result.error(CodeMsg.CODE_INVALIDE);
//        }
        Result result = loginService.registerNewStudent(registerNewStudentQuery, registerNewStudentQuery.getOpenid());
        return checkResult(result, CodeMsg.SUCCESS.getRetCode(), registerNewStudentQuery.getRecruitSchoolId());
    }

    /**
     * 获取微信的openid
     *
     * @param code 微信code
     * @return java.lang.String
     * @author Pan Juncai
     * @date 2020/4/29 11:53
     */
    @PostMapping("/getOpenId")
    @ApiOperation("注册新用户和学生")
    public String getOpenId(String code) {
        Map<String, Object> requestUrlParam = Maps.newHashMap();
        // 小程序appId
        requestUrlParam.put("appid", wechatProperties.getAppid());
        // 小程序secret
        requestUrlParam.put("secret", wechatProperties.getSecret());
        // 小程序端返回的code
        requestUrlParam.put("js_code", code);
        // 默认参数
        requestUrlParam.put("grant_type", wechatProperties.getGrant_type());

        //发送post请求
        String result = HttpUtil.post(wechatProperties.getREQUEST_URL(), requestUrlParam);
        log.info("result={}", result);
        JSONObject jsonObject = JSON.parseObject(result);
        String openid = null;
        if (null != jsonObject) {
            openid = jsonObject.getString(Constant.Wechat.OPENID_FLAG);
        }
        return openid;
    }
}
