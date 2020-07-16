package com.educiot.recruit.server.wechat.controller.plan;


import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.entity.vo.PlanRecruitInfoVO;
import com.educiot.recruit.data.entity.vo.PlanVO;
import com.educiot.recruit.data.service.IPlanRecruitInfoService;
import com.educiot.recruit.data.service.IPlanService;
import com.educiot.recruit.server.wechat.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 招生简介表 前端控制器
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@RestController
@RequestMapping("/plan-recruit-info")
@Api(tags = {"招生简介"}, value = "PlanRecruitInfoController")
public class PlanRecruitInfoController extends BaseController {

    @Resource
    private IPlanRecruitInfoService planRecruitInfoService;

    @Resource
    private IPlanService planService;

    @GetMapping("/queryPlanRecruitInfoByRecruitSchoolId/{recruitSchoolId}")
    @ApiOperation(value="根据招生学校id获取默认招生计划中的招生简介信息",response =PlanRecruitInfoVO.class )
    public Result queryPlanRecruitInfoByRecruitSchoolId(@PathVariable Long recruitSchoolId) {
        Result<PlanVO> defaultPlan = planService.queryDefaultPlanInfoWeChat(recruitSchoolId);
        if (defaultPlan.getData() == null) {
            return Result.error(CodeMsg.LOGIN_DEFAULT_PLAN_NOT_EXIST, recruitSchoolId != null ? "" : recruitSchoolId.toString());
        } else {
            return planRecruitInfoService.queryPlanRecruitInfo(defaultPlan.getData().getPlanId());
        }
    }

}