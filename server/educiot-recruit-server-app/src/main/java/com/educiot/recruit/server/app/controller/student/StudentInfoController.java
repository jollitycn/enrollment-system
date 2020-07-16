package com.educiot.recruit.server.app.controller.student;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.common.base.MyExcption;
import com.educiot.common.base.Result;
import com.educiot.recruit.data.entity.query.*;
import com.educiot.recruit.data.entity.vo.ClassVO;
import com.educiot.recruit.data.entity.vo.ListSourceSchoolVO;
import com.educiot.recruit.data.entity.vo.StudentInfoStatusVO;
import com.educiot.recruit.data.entity.vo.StudentInfoVO;
import com.educiot.recruit.data.service.IClassService;
import com.educiot.recruit.data.service.student.IStudentInfoService;
import com.educiot.recruit.server.app.common.BaseController;
import com.educiot.recruit.server.app.common.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 学生基本信息表 前端控制器
 * </p>
 *
 * @author Mr.AI
 * @since 2020-04-13
 */
@RestController
@RequestMapping("/student-info")
@Api(tags = {"学生基本信息管理"}, value = "StudentInfoController")
public class StudentInfoController extends BaseController {

    @Resource
    private IStudentInfoService studentInfoService;

    @Resource
    private IClassService classService;

    @GetMapping("/queryStudentInfoStatus")
    @ApiOperation("获取学生基本信息填写状态")
    public Result<StudentInfoStatusVO> queryStudentInfoStatus(@Valid @RequestParam("studentInfoId") Long studentInfoId) {
        StudentInfoStatusVO studentInfoStatusVO = studentInfoService.queryStudentInfoStatus(studentInfoId, getLoginUser().getCurrentSchoolId());
        return Result.success(studentInfoStatusVO);
    }

    @GetMapping("/listClassPage")
    @ApiOperation("分页获取生源学校下的班级信息列表")
    public Result<IPage<ClassVO>> listClassPage(@Valid ListClassPageQuery pageQuery) throws MyExcption {
        IPage<ClassVO> classVOIPage = classService.listSchoolClassPage(pageQuery, getLoginUser().getCurrentSemesterId());
        return Result.success(classVOIPage);
    }

    @GetMapping("/listSourceSchool")
    @ApiOperation("获取负责人下的生源学校信息列表")
    public Result listSourceSchool() {
        Long chargerId = getLoginUser().getCharger().getChargerId();
        List<ListSourceSchoolVO> listSourceSchoolVOS = studentInfoService.listSourceSchool(chargerId);
        return Result.success(listSourceSchoolVOS);
    }

    @GetMapping("/listStudentInfoPage")
    @ApiOperation("分页获取所有学生基本信息列表")
    public Result<IPage<StudentInfoVO>> listStudentInfoPage(@Valid ListStudentPageQuery param) {
        param.setSemesterId(getLoginUser().getCurrentSemesterId());
        IPage<StudentInfoVO> studentInfoVOIPage = studentInfoService.listStudentInfoPageForFamilyMember(param, getLoginUser().getCharger().getChargerId());
        return Result.success(studentInfoVOIPage);
    }

    @GetMapping("/listStudentInfoPageByClassId")
    @ApiOperation("分页获取所有班级下学生基本信息列表")
    public Result<IPage<StudentInfoVO>> listStudentInfoPageByClassId(@Valid ListStudentPageByClassIdQuery param) {

        IPage<StudentInfoVO> studentInfoVOIPage = studentInfoService.listStudentInfoPageByClassId(param,getLoginUser().getCurrentSemesterId());
        return Result.success(studentInfoVOIPage);
    }

    @PostMapping("/addStudentInfo")
    @ApiOperation("添加学生基本信息")
    public Result addStudentInfo(@Valid @RequestBody AddStudentInfoQuery addStudentInfoQuery) throws Exception {
        LoginUser loginUser = getLoginUser();
        addStudentInfoQuery.setCreatorId(loginUser.getApiInfo().getUserId());
        addStudentInfoQuery.setCreatorName(loginUser.getApiInfo().getUserName());
        addStudentInfoQuery.setRecruitSchoolId(loginUser.getCurrentSchoolId());
        addStudentInfoQuery.setSemesterId(loginUser.getCurrentSemesterId());
        addStudentInfoQuery.setSemester(loginUser.getSemesterName());
        return studentInfoService.addStudentInfo(addStudentInfoQuery);
    }

    @PostMapping("/addStudentInfoAPP")
    @ApiOperation("添加学生基本信息和家庭信息")
    public Result addStudentInfoAPP(@Valid @RequestBody AddStudentInfoQuery addStudentInfoQuery) throws Exception {
        LoginUser loginUser = getLoginUser();
        addStudentInfoQuery.setCreatorId(loginUser.getApiInfo().getUserId());
        addStudentInfoQuery.setCreatorName(loginUser.getApiInfo().getUserName());
        addStudentInfoQuery.setRecruitSchoolId(loginUser.getCurrentSchoolId());
        addStudentInfoQuery.setSemesterId(loginUser.getCurrentSemesterId());
        addStudentInfoQuery.setSemester(loginUser.getSemesterName());
        return studentInfoService.addStudentInfoAPP(addStudentInfoQuery);

    }

    @PutMapping("/updateStudentInfo")
    @ApiOperation("编辑学生基本信息")
    public Result updateStudentInfo(@Valid @RequestBody UpdateStudentInfoQuery updateStudentInfoQuery) throws Exception {
        updateStudentInfoQuery.setRecruitSchoolId(getLoginUser().getCurrentSchoolId());
        return studentInfoService.updateStudentInfoAPP(updateStudentInfoQuery);
    }

    @GetMapping("/queryStudentInfo")
    @ApiOperation("获取学生基本信息")
    public Result queryStudentInfo(@Valid @RequestParam("studentInfoId") Long studentInfoId) {
        StudentInfoVO studentInfoVO = studentInfoService.queryStudentInfoAPP(studentInfoId, getLoginUser().getCurrentSchoolId());
        return Result.success(studentInfoVO);
    }

}
