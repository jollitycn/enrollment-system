package com.educiot.recruit.server.wechat.controller.plan;


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
import com.educiot.recruit.server.wechat.common.BaseController;
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


    @GetMapping("/queryMajorInfoByCollegeId")
    @ApiOperation(value= "获取院系下面的所有招生专业信息",response = Major.class)
    public Result<List<Major>> queryMajorInfoByCollegeId(@Valid String collegeId){
        return majorService.queryMajorInfoByCollegeId(collegeId);
    }

    @GetMapping("/queryMajorInfoByMajorId")
    @ApiOperation(value= "查看招生专业信息",response = MajorVO.class)
    public Result<MajorVO> queryMajorInfoByMajorId(@Valid @Param("majorId") String majorId,@Valid @Param("recruitSchoolId") Long recruitSchoolId){
        PlanVO planVOResult = planService.queryDefaultPlanInfoBySchool(recruitSchoolId);
        return majorService.queryMajorInfoByMajorId(majorId,planVOResult.getRecruitSchoolId(),planVOResult.getSemesterId());
    }

}
