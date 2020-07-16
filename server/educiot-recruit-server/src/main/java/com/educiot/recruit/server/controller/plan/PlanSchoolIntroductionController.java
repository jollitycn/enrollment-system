package com.educiot.recruit.server.controller.plan;


import com.educiot.recruit.data.entity.query.AddPlanSchoolIntroductionQuery;
import com.educiot.recruit.data.entity.query.UpdatePlanSchoolIntroductionQuery;
import com.educiot.recruit.data.service.IPlanSchoolIntroductionService;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.server.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 招生计划学校简介 前端控制器
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@RestController
@RequestMapping("/plan-school-introduction")
@Api(tags = {"计划招生学校"}, value = "PlanSchoolIntroductionController")
public class PlanSchoolIntroductionController extends BaseController {

    @Resource
    private IPlanSchoolIntroductionService planSchoolIntroductionService;

    @PostMapping("/addPlanSchoolIntroduction")
    @ApiOperation("添加计划招生学校信息")
    public Result addPlanSchoolIntroduction(@Valid @RequestBody AddPlanSchoolIntroductionQuery addPlanSchoolIntroductionQuery) {
        return planSchoolIntroductionService.addPlanSchoolIntroduction(addPlanSchoolIntroductionQuery);
    }

    @GetMapping("/queryPlanSchoolIntroduction")
    @ApiOperation("获取计划招生学校信息")
    public Result queryPlanSchoolIntroduction(@Valid String planId){
        return planSchoolIntroductionService.queryPlanSchoolIntroduction(planId);
    }

    @PutMapping("/updatePlanSchoolIntroduction")
    @ApiOperation("编辑计划招生学校信息")
    public Result updatePlanSchoolIntroduction(@Valid @RequestBody UpdatePlanSchoolIntroductionQuery updatePlanSchoolIntroductionQuery){
        return planSchoolIntroductionService.updatePlanSchoolIntroduction(updatePlanSchoolIntroductionQuery);
    }

    @DeleteMapping("/deletePlanSchoolIntroduction/{planSchoolIntroductionId}")
    @ApiImplicitParam(name = "planSchoolIntroductionId", value = "招生学校ID", required = true)
    @ApiOperation("删除计划招生学校信息")
    public Result deletePlanSchoolIntroduction(@Valid @PathVariable("planSchoolIntroductionId")
                                               @NotNull(message = "招生学校ID不能为空")
                                               @Min(value = 1L, message = "招生学校ID最小为1")
                                               @Max(value = Long.MAX_VALUE, message = "招生学校ID超出最大限制")
                                                       Long planSchoolIntroductionId){
        return planSchoolIntroductionService.deletePlanSchoolIntroduction(planSchoolIntroductionId);
    }
}
