package com.educiot.recruit.server.controller.plan;


import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.entity.query.AddPlanRecruitInfoQuery;
import com.educiot.recruit.data.entity.query.UpdatePlanRecruitInfoQuery;
import com.educiot.recruit.data.entity.vo.PlanRecruitInfoVO;
import com.educiot.recruit.data.service.IPlanRecruitInfoService;
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

    @PostMapping("/addPlanRecruitInfo")
    @ApiOperation("添加招生简介信息")
    public Result addPlanRecruitInfo(@Valid @RequestBody AddPlanRecruitInfoQuery addPlanRecruitInfoQuery) {
        return planRecruitInfoService.addPlanRecruitInfo(addPlanRecruitInfoQuery);
    }

    @GetMapping("/queryRecruitSchoolInfo")
    @ApiOperation("获取招生简介信息")
    public Result<PlanRecruitInfoVO> queryPlanRecruitInfo(@Valid Long planId) {
        return planRecruitInfoService.queryPlanRecruitInfo(planId);
    }

    @PutMapping("/updatePlanRecruitInfo")
    @ApiOperation("编辑招生简介信息")
    public Result updatePlanRecruitInfo(@Valid @RequestBody UpdatePlanRecruitInfoQuery updateRecruitSchoolQuery){
        return planRecruitInfoService.updatePlanRecruitInfo(updateRecruitSchoolQuery);
    }

    @DeleteMapping("/deletePlanRecruitInfo/{planRecruitInfoId}")
    @ApiImplicitParam(name = "planRecruitInfoId", value = "招生学校ID", required = true)
    @ApiOperation("删除招生简介信息")
    public Result deletePlanRecruitInfo(@Valid @PathVariable("planRecruitInfoId")
                                        @NotNull(message = "招生简介ID不能为空")
                                        @Min(value = 1L, message = "招生简介ID最小为1")
                                        @Max(value = Long.MAX_VALUE, message = "招生简介ID超出最大限制")
                                                Long planRecruitInfoId){
        return planRecruitInfoService.deletePlanRecruitInfo(planRecruitInfoId);
    }

}
