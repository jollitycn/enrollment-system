package com.educiot.recruit.server.app.controller.common;

import com.educiot.recruit.common.api.APIInfo;
import com.educiot.recruit.common.api.APIRequest;
import com.educiot.recruit.common.api.RequestResult;
import com.educiot.recruit.common.api.ResultLoginData;
import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.base.Result;
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
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/checkPlan")
@Api(value = "计划切换controller", tags = {"APP计划切换接口"})
public class CheckPlanController extends BaseController {


    @Value("${qrcode.url}")
    private String qrCodeUrl;

    @Resource
    private IPlanService planService;

    @Resource
    private IRecruitSchoolService recruitSchoolService;

    @Resource
    private IChargerService chargerService;

    @GetMapping("/getPlan/{planId}")
    @ApiOperation("APP切换计划")
    public Result getPlan(@PathVariable Long planId) throws Exception {
        LoginUser loginUser = getLoginUser();
        if (loginUser == null) {
            return Result.error(CodeMsg.TOKEN_NOT_EXIST);
        }

        APIRequest apiRequest = initAPI(loginUser.getAccount(), loginUser.getPwd());
        RequestResult obj = apiRequest.login();
        if (!obj.getStatus().equals(RequestResult.SUCCCESS)) {
            return Result.error(new CodeMsg(obj.getStatus(), obj.getMsg()),obj);
        } else {
            ResultLoginData data= (ResultLoginData) obj.getData();
            SetAPIRequest(data.getToken(), apiRequest);
            LoginUser user = new LoginUser();
            user.setToken(data.getToken());
            user.setAccount(loginUser.getAccount());
            user.setPwd(loginUser.getPwd());
            APIInfo apiInfo = new APIInfo();
            apiInfo.setUserName(data.getName());
            apiInfo.setUserId(Long.parseLong(data.getUserid()));
            apiInfo.setSchoolId(Long.parseLong(data.getSchoolid()));
            apiInfo.setSchoolName(data.getSchoolname());
            user.setApiInfo(apiInfo);
            user.setQrCodeUrl(qrCodeUrl);
            Result<RecruitSchool> result = recruitSchoolService.queryByApiId(apiInfo.getSchoolId());
            RecruitSchoolExt school = new RecruitSchoolExt();
            RecruitSchool recruitSchool = result.getData();
            if (recruitSchool != null) {
            } else {
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
            user.setCurrentSchoolId(school.getRecruitSchoolId());

            PlanVO defaultPlan = planService.queryPlanInfo(planId);
            if (defaultPlan == null) {
                return Result.error(CodeMsg.LOGIN_DEFAULT_PLAN_NOT_EXIST, school.getRecruitSchoolId());
            } else {
                if (defaultPlan != null) {
                    PlanExt plan = new PlanExt();
                    BeanUtils.copyProperties(defaultPlan,
                            plan);
                    user.setCurrentSemesterId(plan.getSemesterId());
                    user.setSemesterName(plan.getSemester());
                    user.setCurrentSchoolPlanId(plan.getPlanId());
                }
            }
            ChargerExt charger = chargerService.getByApiId(user.getApiInfo().getUserId(), defaultPlan.getSemesterId());
            if (charger == null) {
                return Result.error(CodeMsg.LOGIN_CHARGER_NOT_EXIST);
            } else {
                user.setCharger(charger);
//                //删掉旧的缓存信息
//                removeLoginUser(loginUser.getToken());
                //设置新的缓存信息
                setLoginUser(user);
                return Result.success(user);
            }
        }
    }
}
