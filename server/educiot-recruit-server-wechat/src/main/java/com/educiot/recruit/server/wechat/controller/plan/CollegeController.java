package com.educiot.recruit.server.wechat.controller.plan;


import com.educiot.recruit.common.api.DepartInfoResult;
import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.base.MyExcption;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.entity.query.AddCollegeQuery;
import com.educiot.recruit.data.entity.query.UpdateCollegeQuery;
import com.educiot.recruit.data.entity.vo.CollegeAndMajorVO;
import com.educiot.recruit.data.entity.vo.CollegeVO;
import com.educiot.recruit.data.entity.vo.PlanVO;
import com.educiot.recruit.data.service.ICollegeService;
import com.educiot.recruit.data.service.IPlanService;
import com.educiot.recruit.server.wechat.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 招生院系表 前端控制器
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@RestController
@RequestMapping("/college")
@Api(tags = {"招生院系管理"}, value = "CollegeController")
public class CollegeController extends BaseController {

    @Resource
    private ICollegeService collegeService;

    @Resource
    private IPlanService planService;
    @GetMapping("/queryCollegeInfo/{recruitSchoolId}")
    @ApiOperation(value = "获取招生院系列表信息", response = CollegeVO.class)
    public Result queryCollegeInfo(@Valid @PathVariable Long recruitSchoolId) {
        Result<PlanVO> defaultPlan = planService.queryDefaultPlanInfoWeChat(recruitSchoolId);
        if (defaultPlan.getData() == null) {
            return Result.error(CodeMsg.LOGIN_DEFAULT_PLAN_NOT_EXIST, recruitSchoolId != null ? "" : recruitSchoolId.toString());
        } else {
            return collegeService.queryCollegeInfo(defaultPlan.getData().getPlanId().toString());
        }
    }

    @GetMapping("/queryCollegeInfoById")
    @ApiOperation(value = "获取招生院系信息", response = CollegeVO.class)
    public Result queryCollegeInfoById(@Valid String collegeId){
        return collegeService.queryCollegeInfoById(collegeId);
    }
}
