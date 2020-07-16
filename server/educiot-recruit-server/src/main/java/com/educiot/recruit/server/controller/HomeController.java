package com.educiot.recruit.server.controller;


import cn.hutool.core.bean.BeanUtil;
import com.educiot.recruit.common.api.*;
import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.LoginUser;
import com.educiot.recruit.data.entity.RecruitSchool;
import com.educiot.recruit.data.entity.ext.PlanExt;
import com.educiot.recruit.data.entity.ext.RecruitSchoolExt;
import com.educiot.recruit.data.entity.query.AddRecruitSchoolQuery;
import com.educiot.recruit.data.entity.vo.PlanVO;
import com.educiot.recruit.data.service.IPlanService;
import com.educiot.recruit.data.service.IRecruitSchoolService;
import com.educiot.recruit.data.service.ISysDictionaryService;
import com.educiot.recruit.server.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


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

    @Resource
    private IPlanService planService;

    @Resource
    private IRecruitSchoolService recruitSchoolService;

    @Resource
    private ISysDictionaryService sysDictionaryService;

    @PostMapping("/reLogin")
    @ApiOperation("刷新token")
    public Result reLogin() throws Exception {
        LoginUser loginUser = getLoginUser();
        if(loginUser==null){
            return Result.error(CodeMsg.TOKEN_NOT_EXIST);
        }else {
            return login(loginUser.getAccount(), loginUser.getPwd());
        }
    }

    @PostMapping("/login")
    @ApiOperation("获取当前登录学校信息和账号信息")
    public Result login(String account, String pwd) throws Exception {
        // initAPI(account,pwd);
        APIRequest apiRequest = initAPI(account, pwd);
        RequestResult obj = apiRequest.login();
        if (!obj.getStatus().equals(APIResult.SUCCCESS)) {
            return Result.error(obj.getStatus(), obj.getMsg());
        } else {
            ResultLoginData data=  (ResultLoginData)obj.getData();
            setAPIRequest(data.getToken(), apiRequest);
            LoginUser user = new LoginUser();
            user.setAccount(account);
            user.setPwd(pwd);
            user.setToken(data.getToken());
            APIInfo apiInfo = new APIInfo();
            apiInfo.setUserName(data.getName());
            apiInfo.setUserId(Long.parseLong(data.getUserid()));
            apiInfo.setSchoolId(Long.parseLong(data.getSchoolid()));
            apiInfo.setSchoolName(data.getSchoolname());
            user.setApiInfo(apiInfo);
            Result<RecruitSchool> result = recruitSchoolService.queryByApiIdForlogin(apiInfo.getSchoolId());
            RecruitSchoolExt school = new RecruitSchoolExt();
            RecruitSchool recruitSchool = result.getData();
            if (recruitSchool != null) {
                //user.setCurrentSchoolId(recruitSchool.getRecruitSchoolId());
            } else {
                AddRecruitSchoolQuery query = new AddRecruitSchoolQuery();
                query.setApiId(apiInfo.getSchoolId());
                query.setRecruitSchoolName(apiInfo.getSchoolName());
                Result<RecruitSchool> addResult = recruitSchoolService.addRecruiutSchool(query);

                if (addResult.getRetCode() == 0) {
                    //Result<RecruitSchool> queryResult = recruitSchoolService.queryByApiIdForlogin(apiInfo.getSchoolId());
                    recruitSchool = addResult.getData();
                    sysDictionaryService.insertBaseInfo(recruitSchool.getRecruitSchoolId());
                }
            }
            BeanUtil.copyProperties(recruitSchool, school);
            //user.setRecruitSchool(school);
            user.setCurrentSchoolId(school.getRecruitSchoolId());

            Result<PlanVO> defaultPlan = planService.queryDefaultPlanInfo(school.getRecruitSchoolId());
            if (defaultPlan.getData() == null) {
              //  return Result.error(CodeMsg.LOGIN_DEFAULT_PLAN_NOT_EXIST, school.getRecruitSchoolId());
            } else {
                PlanExt plan = new PlanExt();
                BeanUtil.copyProperties(defaultPlan.getData(), plan);
                // user.setPlan(plan);
                user.setCurrentSemesterId(plan.getSemesterId());
                user.setSemesterName(plan.getSemester());
                user.setCurrentSchoolPlanId(plan.getPlanId());
            }

            setLoginUser(user);
            return Result.success(user);
        }
    }
}
