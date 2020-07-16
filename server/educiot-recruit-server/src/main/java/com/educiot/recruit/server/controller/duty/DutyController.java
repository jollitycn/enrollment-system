package com.educiot.recruit.server.controller.duty;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.recruit.common.base.MyExcption;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.common.constant.Constant;
import com.educiot.recruit.data.entity.Plan;
import com.educiot.recruit.data.entity.query.duty.DutyPageQuery;
import com.educiot.recruit.data.entity.vo.DutyVO;
import com.educiot.recruit.data.service.IPlanService;
import com.educiot.recruit.data.service.duty.IDutyService;
import com.educiot.recruit.server.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 值班计划管理
 * </p>
 *
 * @author Hongsifan
 * @since 2020-04-14
 */
@Api(tags = {"值班计划"}, value = "DutyController")
@RestController
@RequestMapping("/duty")
public class DutyController extends BaseController {

    @Resource
    private IDutyService dutyService;

    @Resource
    private IPlanService planService;

    @PostMapping("/page")
    @ApiOperation(value = "获取值班计划列表")
    public Result<?> page(@RequestBody DutyPageQuery query) {

        Long semesterId = getLoginUser().getCurrentSemesterId();

        if (query.getPlanId() != null) {
            Plan plan = planService.getById(query.getPlanId());
            semesterId = plan.getSemesterId();
        }
        IPage<DutyVO> result = null;
        try {
            result = dutyService.listPage(semesterId, query);
        } catch (MyExcption myExcption) {
            myExcption.printStackTrace();
        }
        return Result.success(result);
    }

    @PostMapping("/pageConflict")
    @ApiOperation(value = "异常列表")
    public Result<?> pageConflict(@RequestBody DutyPageQuery query) {

        Long semesterId = getLoginUser().getCurrentSemesterId();

        if (query.getPlanId() != null) {
            Plan plan = planService.getById(query.getPlanId());
            semesterId = plan.getSemesterId();
        }

        IPage<DutyVO> result = null;
        try {
            result = dutyService.pageConflict(semesterId, query);
        } catch (MyExcption myExcption) {
            myExcption.printStackTrace();
        }
        return Result.success(result);
    }

    @GetMapping("/getPlanList")
    @ApiOperation(value = "获取计划列表")
    public Result<?> getPlanList() {

        QueryWrapper<Plan> wrapper = new QueryWrapper<>();
        wrapper.in("status", Constant.SYS_TWO, Constant.SYS_THREE);
        wrapper.eq("recruit_school_id", getLoginUser().getCurrentSchoolId());
        wrapper.orderByDesc("semester_start");
        List<Plan> result = planService.list(wrapper);

        return Result.success(result);
    }

}
