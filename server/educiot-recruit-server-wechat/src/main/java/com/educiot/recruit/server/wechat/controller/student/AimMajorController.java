package com.educiot.recruit.server.wechat.controller.student;


import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.entity.AimMajor;
import com.educiot.recruit.data.entity.vo.AimMajorInfoVO;
import com.educiot.recruit.data.entity.vo.CollegeAndMajorVO;
import com.educiot.recruit.data.entity.vo.MajorVO;
import com.educiot.recruit.data.service.ICollegeService;
import com.educiot.recruit.data.service.IMajorService;
import com.educiot.recruit.data.service.student.IAimMajorService;
import com.educiot.recruit.server.wechat.common.BaseController;
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
 * @author Mr.Ai
 * @since 2020-04-13
 */
@RestController
@RequestMapping("/aim-major")
@Api(tags = {"专业选择"}, value = "AimMajorController")
public class AimMajorController extends BaseController {

    @Resource
    private IAimMajorService aimMajorService;

    @Resource
    private ICollegeService collegeService;

    @Resource
    private IMajorService majorService;

    @PostMapping("/saveOrUpdateAimMajorInfo")
    @ApiOperation("添加或修改选择专业信息")
    public Result saveOrUpdateAimMajorInfo(@Valid @RequestBody AimMajorInfoVO aimMajorInfoVO) {
        aimMajorInfoVO.setStudentPublicRelationId(getLoginVO().getStudentPublicRelationId());
        return Result.success(aimMajorService.saveOrUpdateAimMajorInfo(aimMajorInfoVO));
    }

    @GetMapping("/queryAimMajorInfo")
    @ApiOperation("根据学生ID查询学生选择的专业信息")
    public Result queryAimMajorInfo() {

        List<AimMajor> aimMajor = aimMajorService.queryAimMajorInfo(getLoginVO().getCurrentStudent().getStudentInfo(), getLoginVO().getCurrentRecruitSchool().getRecruitSchoolId());
        return Result.success(aimMajor);
    }

    @GetMapping("/queryCollegeAndMajor")
    @ApiOperation("获取招生院系信息以及所有的专业信息")
    public Result<List<CollegeAndMajorVO>> queryCollegeAndMajor() {
        return collegeService.queryCollegeAndMajor(getLoginVO().getPlan().getPlanId(), getLoginVO().getCurrentRecruitSchool().getRecruitSchoolId(), getLoginVO().getPlan().getSemesterId());
    }

    @GetMapping("/queryMajorInfoByMajorId")
    @ApiOperation("查看招生专业信息")
    public Result<MajorVO> queryMajorInfoByMajorId(@Valid String majorId) {
        return majorService.queryMajorInfoByMajorId(majorId, getLoginVO().getCurrentRecruitSchool().getRecruitSchoolId(), getLoginVO().getPlan().getSemesterId());
    }

}
