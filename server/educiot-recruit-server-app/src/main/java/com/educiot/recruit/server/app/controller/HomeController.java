package com.educiot.recruit.server.app.controller;


import com.educiot.recruit.common.api.APIInfo;
import com.educiot.recruit.common.api.APIRequest;
import com.educiot.recruit.common.api.RequestResult;
import com.educiot.recruit.common.api.ResultLoginData;
import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.base.MyExcption;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.common.util.PasswordUtil;
import com.educiot.recruit.data.entity.RecruitSchool;
import com.educiot.recruit.data.entity.ext.ChargerExt;
import com.educiot.recruit.data.entity.ext.PlanExt;
import com.educiot.recruit.data.entity.ext.RecruitSchoolExt;
import com.educiot.recruit.data.entity.query.AddRecruitSchoolQuery;
import com.educiot.recruit.data.entity.vo.PlanVO;
import com.educiot.recruit.data.service.IChargerService;
import com.educiot.recruit.data.service.IPlanService;
import com.educiot.recruit.data.service.IRecruitSchoolService;
import com.educiot.recruit.server.app.common.BaseController;
import com.educiot.recruit.server.app.common.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;


/**
 * <p>
 * 选择专业表 前端控制器
 * </p>
 *
 * @author Hongsifan
 * @since 2020-04-13
 */


@Api(tags = {"首页登录接口"}, value = "HomeController")
@RestController
@RequestMapping("/home")
@Configuration
public class HomeController extends BaseController {

    @Value("${qrcode.url}")
    private String qrCodeUrl;

    @Resource
    private IPlanService planService;

    @Resource
    private IRecruitSchoolService recruitSchoolService;

    @Resource
    private IChargerService chargerService;

//    @PostMapping("/login")
//    @ApiOperation("获取当前登录学校信息和账号信息")
//    public Result login() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
//        RequestResult obj = apiRequest.login();
//        //BeanUtil.copyProperties(obj, apiInfo);
//        LoginUser user = new LoginUser();
//        user.setToken(obj.getData().getToken());
////        if(defaultPlan==null){
////            return Result.error(CodeMsg.PLAN_DEFAULT_NOT_FOUND );
////        }
//        APIInfo apiInfo = new APIInfo();
//        apiInfo.setUserName(obj.getData().getName());
//        apiInfo.setUserId(Long.parseLong(obj.getData().getUserid()));
//        apiInfo.setSchoolId(Long.parseLong(obj.getData().getSchoolid()));
//        apiInfo.setSchoolName(obj.getData().getSchoolname());
////        apiInfo.
//        user.setApiInfo(apiInfo);
//        Result<RecruitSchool> result = recruitSchoolService.queryByApiId(apiInfo.getSchoolId());
//        RecruitSchoolExt school = new RecruitSchoolExt();
//        RecruitSchool recruitSchool = result.getData();
//        if (recruitSchool != null) {
//            //user.setCurrentSchoolId(recruitSchool.getRecruitSchoolId());
//        } else {
//            //  vo.
//            AddRecruitSchoolQuery query = new AddRecruitSchoolQuery();
//            query.setApiId(apiInfo.getSchoolId());
//            query.setRecruitSchoolName(apiInfo.getSchoolName());
//            Result addResult = recruitSchoolService.addRecruiutSchool(query);
//            if (addResult.getRetCode() == 0) {
//                Result<RecruitSchool> queryResult = recruitSchoolService.queryByApiId(apiInfo.getSchoolId());
//                recruitSchool = queryResult.getData();
//                // Long recruitSchoolId = recruitSchool.getRecruitSchoolId();
//                //user.setCurrentSchoolId(recruitSchoolId);
//
//                //   sysDictionaryService.insertBaseInfo(queryResult.getData().getRecruitSchoolId());
//            }
//            //addResult.getData()
//        }
//        BeanUtils.copyProperties(recruitSchool, school);
//        user.setRecruitSchool(school);
//        user.setCurrentSchoolId(school.getRecruitSchoolId());
//
//        Result<PlanVO> defaultPlan = planService.queryDefaultPlanInfo(school.getRecruitSchoolId());
//        if (defaultPlan != null) {
//            PlanExt plan = new PlanExt();
//            BeanUtils.copyProperties(defaultPlan.getData(),
//                    plan);
//            user.setPlan(plan);
//            user.setCurrentSemesterId(plan.getSemesterId());
//            user.setSemesterName(plan.getSemester());
//            user.setCurrentSchoolPlanId(plan.getPlanId());
//        }
//        ChargerExt charger = chargerService.getByApiId(user.getApiInfo().getUserId());
//        user.setCharger(charger);
//        setLoginUser(user);
//        return Result.success(user);
//    }

    @PostMapping("/reLogin")
    @ApiOperation("刷新token")
    public Result reLogin() throws Exception {
         LoginUser  loginUser = getLoginUser();
        if(loginUser==null){
            return Result.error(CodeMsg.TOKEN_NOT_EXIST);
        }else {
            return login(loginUser.getAccount(), loginUser.getPwd());
        }
    }

    @PostMapping("/login")
    @ApiOperation("获取当前登录学校信息和账号信息")
    public Result login(String account, String pwd) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, MyExcption {
        APIRequest apiRequest = initAPI(account, pwd);
        RequestResult obj = apiRequest.login();
        if (!obj.getStatus().equals(RequestResult.SUCCCESS)) {
           // throw new MyExcption(CodeMsg.API_NOT_LOGIN, obj.getMsg());
            return Result.error(new CodeMsg(obj.getStatus(), obj.getMsg()),obj);
        } else {
            ResultLoginData data= (ResultLoginData) obj.getData();
            SetAPIRequest(data.getToken(), apiRequest);
            //BeanUtil.copyProperties(obj, apiInfo);
            LoginUser user = new LoginUser();
            user.setToken(data.getToken());
            user.setAccount(account);
            user.setPwd(pwd);
//        if(defaultPlan==null){
//            return Result.error(CodeMsg.PLAN_DEFAULT_NOT_FOUND );
//        }
            APIInfo apiInfo = new APIInfo();
            apiInfo.setUserName(data.getName());
            apiInfo.setUserId(Long.parseLong(data.getUserid()));
            apiInfo.setSchoolId(Long.parseLong(data.getSchoolid()));
            apiInfo.setSchoolName(data.getSchoolname());
//        apiInfo.
            user.setApiInfo(apiInfo);
            user.setQrCodeUrl(qrCodeUrl);
            Result<RecruitSchool> result = recruitSchoolService.queryByApiId(apiInfo.getSchoolId());
            RecruitSchoolExt school = new RecruitSchoolExt();
            RecruitSchool recruitSchool = result.getData();
            if (recruitSchool != null) {
                //user.setCurrentSchoolId(recruitSchool.getRecruitSchoolId());
            } else {
                //  vo.
                AddRecruitSchoolQuery query = new AddRecruitSchoolQuery();
                query.setApiId(apiInfo.getSchoolId());
                query.setRecruitSchoolName(apiInfo.getSchoolName());
                Result addResult = recruitSchoolService.addRecruiutSchool(query);
                if (addResult.getRetCode() == 0) {
                    Result<RecruitSchool> queryResult = recruitSchoolService.queryByApiId(apiInfo.getSchoolId());
                    recruitSchool = queryResult.getData();
                }
            }
            BeanUtils.copyProperties(recruitSchool, school);
            // user.setRecruitSchool(school);
            user.setCurrentSchoolId(school.getRecruitSchoolId());

            Result<PlanVO> defaultPlan = planService.queryDefaultPlanInfo(school.getRecruitSchoolId());
            if (defaultPlan.getData() == null) {
                return Result.error(CodeMsg.LOGIN_DEFAULT_PLAN_NOT_EXIST, school.getRecruitSchoolId());
            } else {
                if (defaultPlan != null) {
                    PlanExt plan = new PlanExt();
                    BeanUtils.copyProperties(defaultPlan.getData(),
                            plan);
                    //user.setPlan(plan);
                    user.setCurrentSemesterId(plan.getSemesterId());
                    user.setSemesterName(plan.getSemester());
                    user.setCurrentSchoolPlanId(plan.getPlanId());
                }
            }
            ChargerExt charger = chargerService.getByApiId(user.getApiInfo().getUserId(), user.getCurrentSemesterId());
            if (charger == null) {
                return Result.error(CodeMsg.LOGIN_CHARGER_NOT_EXIST);
            } else {
                user.setCharger(charger);
                setLoginUser(user);
                return Result.success(user);
            }
        }
    }
}
