package com.educiot.recruit.server.controller.plan;


import com.educiot.recruit.common.api.MajorListResult;
import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.base.MyExcption;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.LoginUser;
import com.educiot.recruit.data.entity.Major;
import com.educiot.recruit.data.entity.query.AddMajorInfoQuery;
import com.educiot.recruit.data.entity.query.UpdateMajorInfoQuery;
import com.educiot.recruit.data.entity.vo.MajorAmountVO;
import com.educiot.recruit.data.entity.vo.MajorVO;
import com.educiot.recruit.data.entity.vo.PlanVO;
import com.educiot.recruit.data.service.IMajorService;
import com.educiot.recruit.data.service.IPlanService;
import com.educiot.recruit.server.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 招生专业表 前端控制器
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@RestController
@RequestMapping("/major")
@Api(tags = {"招生专业管理"}, value = "MajorController")
public class MajorController extends BaseController {

    @Resource
    private IMajorService majorService;

    @Resource
    private IPlanService planService;

    @PostMapping("/addMajorInfo")
    @ApiOperation("新建专业信息")
    public Result addMajorInfo(@Valid @RequestBody AddMajorInfoQuery addMajorInfoQuery){
        //在新建专业的时候，需要先绑定专业下拉框，1
        //目标人数需要跟随专业下拉框的选择而改变，2
        //专业新建是在院系的下级建立，3
        //目标那边需要提供一个根据apiID查询，目标人数的接口
        //获取登录用户信息
        LoginUser loginUser= getLoginUser();
        return majorService.addMajorInfo(addMajorInfoQuery,loginUser);
    }

    @GetMapping("/queryMajorInfoByCollegeId")
    @ApiOperation("获取院系下面的所有招生专业信息")
    public Result<List<Major>> queryMajorInfoByCollegeId(@Valid String collegeId){
        return majorService.queryMajorInfoByCollegeId(collegeId);
    }

    @GetMapping("/queryMajorInfoByMajorId")
    @ApiOperation("查看招生专业信息")
    public Result<MajorVO> queryMajorInfoByMajorId(@Valid @Param("majorId") String majorId,@Valid @Param("planId") Long planId){
        PlanVO planVOResult = planService.queryPlanInfo(planId);
        return majorService.queryMajorInfoByMajorId(majorId,getLoginUser().getCurrentSchoolId(),planVOResult.getSemesterId());
    }

    @PutMapping("/updateMajorInfo")
    @ApiOperation("编辑招生专业信息")
    public Result updateMajorInfo(@Valid @RequestBody UpdateMajorInfoQuery updateMajorInfoQuery){
        return majorService.updateMajorInfo(updateMajorInfoQuery);
    }


    @DeleteMapping("/deleteMajorInfo/{majorId}")
    @ApiImplicitParam(name = "majorId", value = "招生学校ID", required = true)
    @ApiOperation("删除专业信息")
    public Result deleteMajorInfo(@Valid @PathVariable("majorId")
                                  @NotNull(message = "专业信息ID不能为空")
                                  @Min(value = 1L, message = "招专业信息ID最小为1")
                                  @Max(value = Long.MAX_VALUE, message = "专业信息ID超出最大限制")
                                          Long majorId){
        return majorService.deleteMajorInfo(majorId);
    }


    @GetMapping("/queryMajorInfoByAPI")
    @ApiOperation("所属专业下拉选择，联动专业目标人数")
    public Result<List<MajorAmountVO>> queryMajorInfoByAPI(@Valid @Param("departId") String departId,@Valid @Param("planId")Long planId) throws MyExcption {
        if (null == departId) {
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        MajorListResult result = getAPIRequest().majorList(departId);
        LoginUser loginUser = getLoginUser();
        PlanVO planVOResult = planService.queryPlanInfo(planId);
        loginUser.setCurrentSemesterId(planVOResult.getSemesterId());
        List<MajorAmountVO> majors = majorService.queryMajorInfoByAPI(result, loginUser);
        return Result.success(majors);
    }

}
