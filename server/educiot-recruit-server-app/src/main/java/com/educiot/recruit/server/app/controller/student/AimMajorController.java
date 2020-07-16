package com.educiot.recruit.server.app.controller.student;


import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.entity.AimMajor;
import com.educiot.recruit.data.entity.vo.AimMajorInfoVO;
import com.educiot.recruit.data.entity.vo.CollegeAndMajorVO;
import com.educiot.recruit.data.service.ICollegeService;
import com.educiot.recruit.data.service.student.IAimMajorService;
import com.educiot.recruit.server.app.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 选择专业表 前端控制器
 * </p>
 *
 * @author Mr.AI
 * @since 2020-04-13
 */
@RestController
@RequestMapping("/aim-major")
@Api(tags = {"选择专业信息"}, value = "AimMajorController")
public class AimMajorController extends BaseController {

    @Resource
    private IAimMajorService aimMajorService;

    @Resource
    private ICollegeService collegeService;

    @PostMapping("/saveOrUpdateAimMajorInfo")
    @ApiOperation("添加或修改选择专业信息")
    public Result saveOrUpdateAimMajorInfo(@Valid @RequestBody AimMajorInfoVO aimMajorInfoVO) {
        return Result.success(aimMajorService.saveOrUpdateAimMajorInfo(aimMajorInfoVO));
    }

    @GetMapping("/queryAimMajorInfo")
    @ApiOperation("根据学生ID查询学生选择的专业信息")
    public Result queryAimMajorInfo(@Valid @RequestParam("studentInfoId") Long studentInfoId) {
        List<AimMajor> aimMajors = aimMajorService.queryAimMajorInfo(studentInfoId, getLoginUser().getCurrentSchoolId());
        return Result.success(aimMajors);
    }

    @GetMapping("/queryCollegeAndMajor")
    @ApiOperation("获取招生院系信息以及所有的专业信息")
    public Result<List<CollegeAndMajorVO>> queryCollegeAndMajor() {
        return collegeService.queryCollegeAndMajor(getLoginUser().getCurrentSchoolPlanId(), getLoginUser().getCurrentSchoolId(), getLoginUser().getCurrentSemesterId());
    }

}
