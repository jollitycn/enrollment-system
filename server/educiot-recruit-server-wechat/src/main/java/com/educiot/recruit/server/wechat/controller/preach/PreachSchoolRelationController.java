package com.educiot.recruit.server.wechat.controller.preach;


import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.entity.vo.PlanVO;
import com.educiot.recruit.data.service.IPlanSchoolIntroductionService;
import com.educiot.recruit.data.service.IPlanService;
import com.educiot.recruit.server.wechat.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 宣讲生源学校关联表 前端控制器
 * </p>
 *
 * @author Jason Hong
 * @since 2020-04-10
 */
@RestController
@RequestMapping("/preach-school-relation")
@Api(tags = {"招生计划学校简介"}, value = "PreachSchoolRelationController")
public class PreachSchoolRelationController extends BaseController {

    @Resource
    private IPlanSchoolIntroductionService  planSchoolIntroductionService;

    @Resource
    private IPlanService planService;

    @GetMapping("/getByRecruitSchoolId/{recruitSchoolId}")
    @ApiOperation("根据招生学校id获取默认招生计划中的招生简介信息")
    public Result getByRecruitSchoolId(@PathVariable Long recruitSchoolId) {
        Result<PlanVO> defaultPlan = planService.queryDefaultPlanInfo(recruitSchoolId);
        if (defaultPlan.getData() == null) {
            return Result.error(CodeMsg.LOGIN_DEFAULT_PLAN_NOT_EXIST, recruitSchoolId != null ? "" : recruitSchoolId.toString());
        } else {
            return planSchoolIntroductionService.queryPlanSchoolIntroduction(defaultPlan.getData().getPlanId().toString());
        }
    }


    @GetMapping("/getByRecruitSchoolId")
    @ApiOperation("获取计划招生学校信息")
    public Result listRecruitSchoolOfLogin(@Valid Long studentInfo){
        List result = planSchoolIntroductionService.listRecruitSchoolByStudent(studentInfo);
        return Result.success(result);
    }
}
