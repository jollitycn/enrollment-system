package com.educiot.recruit.server.wechat.controller.plan;


import com.educiot.common.base.Result;
import com.educiot.recruit.data.entity.ext.PlanSchoolIntroductionExt;
import com.educiot.recruit.data.service.IPlanSchoolIntroductionService;
import com.educiot.recruit.server.wechat.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

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

    @GetMapping("/getByRecruitSchoolId/{recruitSchoolId}")
    @ApiOperation(value="获取计划招生学校信息",response = PlanSchoolIntroductionExt.class)
    public Result getByRecruitSchoolId(@Valid @PathVariable String recruitSchoolId) {
        PlanSchoolIntroductionExt result = planSchoolIntroductionService.getByRecruitSchoolId(recruitSchoolId);
        return Result.success(result);
    }
}
