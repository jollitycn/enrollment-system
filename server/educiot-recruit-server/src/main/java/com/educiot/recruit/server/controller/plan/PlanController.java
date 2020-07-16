package com.educiot.recruit.server.controller.plan;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.common.base.MyExcption;
import com.educiot.common.base.Result;
import com.educiot.recruit.data.entity.Plan;
import com.educiot.recruit.data.entity.query.*;
import com.educiot.recruit.data.entity.vo.ChargerVO;
import com.educiot.recruit.data.entity.vo.PlanVO;
import com.educiot.recruit.data.service.IPlanService;
import com.educiot.recruit.server.common.BaseController;
import com.educiot.recruit.server.common.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 招生计划表  前端控制器
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@RestController
@RequestMapping("/plan")
@Api(tags = {"招生计划"}, value = "PlanController")
public class PlanController extends BaseController {

    @Resource
    private IPlanService planService;

    @GetMapping("/listGroupInfoPage")
    @ApiOperation("分页获取招生计划信息列表")
    public Result<IPage<Plan>> listGroupInfoPage(@Valid ListPlanPageQuery param) {
        param.setRecruitSchoolId(getLoginUser().getCurrentSchoolId());
        IPage<Plan> page = planService.listPlanPage(param);
        return Result.success(page);
    }

    @PostMapping("/addPlanInfo")
    @ApiOperation("添加计划基本信息")
    public Result addPlanInfo(@Valid @RequestBody AddPlanQuery addPlanQuery) throws MyExcption {
        LoginUser loginUser= getLoginUser();
        return planService.addPlanInfo(addPlanQuery, loginUser.getCurrentSchoolId(),loginUser.getApiInfo().getUserId(),loginUser.getApiInfo().getUserName(),loginUser.getApiInfo().getSchoolId(), getAPIRequest());
    }

    @GetMapping("/queryPlanInfo")
    @ApiOperation("获取招生计划基本信息")
    public Result queryPlanInfo(@Valid Long planId){
        PlanVO planVO = planService.queryPlanInfo(planId);
        return Result.success(planVO);
    }


    @GetMapping("/queryDefaultPlanInfo")
    @ApiOperation("获取默认招生计划基本信息")
    public Result queryDefaultPlanInfo(){
        return planService.queryDefaultPlanInfo(getLoginUser().getCurrentSchoolId());
    }

    @GetMapping("/queryAllPublishPlan")
    @ApiOperation("获取所有已发布计划信息")
    public Result queryAllPublishPlan(){
        List<Plan> planVOS = planService.queryAllPublishPlan(getLoginUser().getCurrentSchoolId());
        return Result.success(planVOS);
    }

    @GetMapping("/queryAllFinishedPlan")
    @ApiOperation("获取所有已发布和已完成的计划信息")
    public Result queryAllFinishedPlan(){
        List<Plan> planVOS = planService.queryAllFinishedPlan(getLoginUser().getCurrentSchoolId());
        return Result.success(planVOS);
    }

    @PostMapping("/addCopyPlanInfo")
    @ApiOperation("复制计划信息")
    public Result addCopyPlanInfo(@Valid @RequestBody AddCopyPlanQuery addCopyPlanQuery){
        LoginUser loginUser= getLoginUser();
        return planService.addCopyPlanInfo(addCopyPlanQuery,loginUser.getCurrentSchoolId(),loginUser.getApiInfo().getUserId(),loginUser.getApiInfo().getUserName());
    }

    @PutMapping("/updatePlanInfo")
    @ApiOperation("编辑计划基本信息")
    public Result updatePlanInfo(@Valid @RequestBody UpdatePlanInfoQuery updatePlanInfoQuery){
        LoginUser loginUser= getLoginUser();
        return planService.updatePlanInfo(updatePlanInfoQuery,loginUser.getCurrentSchoolId(),loginUser.getApiInfo().getUserId());
    }

    @PutMapping("/updatePlanStatus")
    @ApiOperation("编辑计划的发布状态,及逻辑删除")
    public Result updatePlanStatus(@Valid @RequestBody UpdatePlanQuery planQuery){
        LoginUser loginUser= getLoginUser();
        return planService.updateStatus(planQuery,loginUser.getApiInfo().getUserId(),loginUser.getApiInfo().getUserName());
    }

    @PutMapping("/updatePlanStatusEnd")
    @ApiOperation("编辑计划的完成状态")
    public Result updatePlanStatusEnd(@Valid @RequestBody UpdatePlanQuery planQuery){
        LoginUser loginUser= getLoginUser();
        Boolean aBoolean = planService.updateStatusEnd(planQuery, loginUser.getCurrentSchoolId(),loginUser.getCurrentSemesterId(),loginUser.getApiInfo().getUserId(),loginUser.getApiInfo().getUserName());
        return Result.success(aBoolean);
    }

    @PutMapping("/updateIsDefaulted")
    @ApiOperation("编辑计划的默认状态")
    public Result updateIsDefaulted(@Valid UpdatePlanIsDefaultecQuery planQuery){
        LoginUser loginUser= getLoginUser();
        return planService.updateIsDefaulted(planQuery,loginUser.getCurrentSchoolId());
    }

    @PutMapping("/queryChargerBySemesterId")
    @ApiOperation("查询基本信息的负责人列表")
    public Result<List<ChargerVO>> queryChargerBySemesterId(@Valid @RequestParam("semesterId") Long semesterId){
        List<ChargerVO> chargerVOS = planService.queryChargerBySemesterId(semesterId, getLoginUser().getCurrentSchoolId());
        return Result.success(chargerVOS);
    }

}
