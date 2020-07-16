package com.educiot.recruit.server.wechat.controller;


import com.educiot.common.base.Result;
import com.educiot.recruit.data.entity.vo.RecruitSchoolVO;
import com.educiot.recruit.data.service.IRecruitSchoolService;
import com.educiot.recruit.data.service.student.IStudentInfoService;
import com.educiot.recruit.server.wechat.common.BaseController;
import com.educiot.recruit.server.wechat.controller.preach.PreachSchoolRelationController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * <p>
 * 首页 前端控制器
 * </p>
 *
 * @author panjuncai
 * @since 2020-05-08
 */


@Api(tags = {"首页接口"}, value = "HomeController")
@RestController
@RequestMapping("/home")
public class HomeController extends BaseController {
    @Resource
    PreachSchoolRelationController preachSchoolRelationController;
    @Resource
    private IStudentInfoService studentInfoService;

    @Resource
    private IRecruitSchoolService recruitSchoolService;

    @GetMapping("/switchStudent/{studentInfo}")
    @ApiImplicitParam(value = "学生ID", name = "studentInfo", required = true)
    @ApiOperation("切换学生")
    public Result switchStudent(@Valid @PathVariable("studentInfo")
                                @NotNull(message = "学生ID不能为空")
                                @Min(value = 1L, message = "学生ID不合法")
                                @Max(value = Long.MAX_VALUE, message = "学生ID不合法")
                                        Long studentInfo) {
        return studentInfoService.switchStudent(studentInfo, getLoginVO());
    }

    @GetMapping("/listRecruitSchoolOfLogin")
    @ApiOperation("获取学生的招生学校列表")
    public Result<List<RecruitSchoolVO>> listRecruitSchoolOfLogin() {
       return  preachSchoolRelationController.listRecruitSchoolOfLogin(getLoginVO().getCurrentStudent().getStudentInfo());
    }

    @GetMapping("/switchRecruitSchool/{recruitSchoolId}")
    @ApiImplicitParam(value = "招生学校id", name = "recruitSchoolId", required = true)
    @ApiOperation("切换招生学校")
    public Result switchRecruitSchool(@Valid @PathVariable("recruitSchoolId")
                                      @NotNull(message = "招生学校id不能为空")
                                      @Min(value = 1L, message = "招生学校id不合法")
                                      @Max(value = Long.MAX_VALUE, message = "招生学校id不合法")
                                              Long recruitSchoolId) {
        return studentInfoService.switchRecruitSchool(recruitSchoolId, getLoginVO());
    }
}
