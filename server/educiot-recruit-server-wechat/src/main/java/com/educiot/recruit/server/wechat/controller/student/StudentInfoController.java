package com.educiot.recruit.server.wechat.controller.student;


import cn.hutool.core.bean.BeanUtil;
import com.educiot.common.base.CodeMsg;
import com.educiot.common.base.Result;
import com.educiot.common.constant.Constant;
import com.educiot.recruit.data.entity.LoginRelation;
import com.educiot.recruit.data.entity.StudentInfo;
import com.educiot.recruit.data.entity.query.AddBindStudentQuery;
import com.educiot.recruit.data.entity.query.AddStudentInfoQuery;
import com.educiot.recruit.data.entity.query.UpdateStudentInfoQuery;
import com.educiot.recruit.data.entity.vo.MiniProgramLoginVO;
import com.educiot.recruit.data.entity.vo.PlanVO;
import com.educiot.recruit.data.entity.vo.StudentIdAndNameVO;
import com.educiot.recruit.data.service.ILoginRelationService;
import com.educiot.recruit.data.service.ILoginService;
import com.educiot.recruit.data.service.IPlanService;
import com.educiot.recruit.data.service.student.IStudentInfoService;
import com.educiot.recruit.server.wechat.common.BaseController;
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
 * @author Mr.Ai
 * @since 2020-04-13
 */
@RestController
@RequestMapping("/student-info")
@Api(tags = {"学生基本信息"}, value = "StudentInfoController")
public class StudentInfoController extends BaseController {

    @Resource
    private IStudentInfoService studentInfoService;

    @Resource
    private ILoginService loginService;

    @Resource
    private ILoginRelationService loginRelationService;

    @Resource
    private IPlanService planService;

//    @GetMapping("/listStudentInfoPage")
//    @ApiOperation("分页获取所有学生基本信息列表")
//    public Result<IPage<StudentInfoVO>> listStudentInfoPage(@Valid ListStudentPageQuery param) {
//        IPage<StudentInfoVO> studentInfoVOIPage = studentInfoService.listStudentInfoPageForFamilyMember(param);
//        return Result.success(studentInfoVOIPage);
//    }

    @PostMapping("/addStudentInfo")
    @ApiOperation("添加学生基本信息")
    public Result addStudentInfo(@Valid @RequestBody AddStudentInfoQuery addStudentInfoQuery) throws Exception {
        addStudentInfoQuery.setCreatorId(getLoginVO().getCurrentStudent().getStudentInfo());
        addStudentInfoQuery.setCreatorName(getLoginVO().getCurrentStudent().getStudentName());
        addStudentInfoQuery.setRecruitSchoolId(getLoginVO().getCurrentRecruitSchool().getRecruitSchoolId());
        addStudentInfoQuery.setSemesterId(getLoginVO().getPlan().getSemesterId());
        addStudentInfoQuery.setSemester(getLoginVO().getPlan().getSemester());
        return studentInfoService.addStudentInfo(addStudentInfoQuery);
    }

    @PutMapping("/updateStudentInfo")
    @ApiOperation("编辑学生基本信息")
    public Result updateStudentInfo(@Valid @RequestBody UpdateStudentInfoQuery updateStudentInfoQuery) {
        Long studentInfo = getLoginVO().getCurrentStudent().getStudentInfo();
        updateStudentInfoQuery.setStudentInfo(studentInfo);
        return studentInfoService.updateStudentInfo(updateStudentInfoQuery);
    }

    @GetMapping("/queryStudentInfo")
    @ApiOperation("获取学生基本信息")
    public Result queryStudentInfo() {
        StudentInfo studentInfo = studentInfoService.queryStudentInfo(getLoginVO().getCurrentStudent().getStudentInfo());
        return Result.success(studentInfo);
    }

    @GetMapping("/queryStudentInfoStatu")
    @ApiOperation("查询学生基本信息填写完成状态")
    public Result queryStudentInfoStatu() {
        Boolean aBoolean = studentInfoService.queryStudentInfoStatu(getLoginVO().getCurrentStudent().getStudentInfo());
        return Result.success(aBoolean);
    }

    @GetMapping("/queryContactDetailStatu")
    @ApiOperation("查询学生联系方式填写完成状态")
    public Result queryContactDetailStatu() {
        Boolean aBoolean = studentInfoService.queryContactDetailStatu(getLoginVO().getCurrentStudent().getStudentInfo());
        return Result.success(aBoolean);
    }


    @GetMapping("/queryRequiredFieldStatu")
    @ApiOperation("查询学生所有必填项的填写完成状态")
    public Result queryRequiredFieldStatu() {
        Boolean aBoolean = studentInfoService.queryRequiredFieldStatu(getLoginVO().getCurrentStudent().getStudentInfo());
        return Result.success(aBoolean);
    }

    @GetMapping("/queryDefaultPlanWeChat/{recruitSchoolId}")
    @ApiOperation("查询当前招生学校和当前学期默认招生计划")
    public Result queryDefaultPlanWeChat(@Valid @PathVariable("recruitSchoolId") Long recruitSchoolId) {
        return Result.success(studentInfoService.queryDefaultPlanWeChat(recruitSchoolId));
    }

    @GetMapping("/listFocusStudent")
    @ApiOperation("查询当前登录微信的绑定学生列表")
    public Result<List<StudentIdAndNameVO>> listFocusStudent() {
        List<StudentIdAndNameVO> list = loginRelationService.listFocusStudent(getLoginVO().getOpenidInfo().getLogin());
        return Result.success(list);
    }

    @PostMapping("/checkBindStudent")
    @ApiOperation("检验学生")
    public Result checkBindStudent(@Valid @RequestBody AddBindStudentQuery addBindStudentQuery) {
        return loginService.checkBindStudent(addBindStudentQuery, getLoginVO());
    }

    @PostMapping("/addNewStudent")
    @ApiOperation("添加新的学生")
    public Result addNewStudent(@Valid @RequestBody AddBindStudentQuery addBindStudentQuery) throws Exception {
        AddStudentInfoQuery addStudentInfoQuery = new AddStudentInfoQuery();
        BeanUtil.copyProperties(addBindStudentQuery, addStudentInfoQuery);

        MiniProgramLoginVO loginVO = getLoginVO();
        Long studentInfo = loginVO.getCurrentStudent().getStudentInfo();
        StudentInfo loginStudent = studentInfoService.getById(studentInfo);
        if (null == loginStudent) {
            return Result.error(CodeMsg.STUDENT_INFO_UN_EXIST);
        }

        if (null == loginVO.getPlan()) {
            return Result.error(CodeMsg.PLAN_DEFAULT_NOT_FOUND);
        }
        //根据切换后的学校查询学期
        PlanVO planVO = planService.queryDefaultPlanInfoBySchool(addBindStudentQuery.getRecruitSchoolId());
        addStudentInfoQuery.setSemesterId(planVO.getSemesterId());
        addStudentInfoQuery.setSemester(planVO.getSemester());
        addStudentInfoQuery.setParentTelephone(loginStudent.getParentTelephone());

        Result result = studentInfoService.addStudentInfo(addStudentInfoQuery);
        if (result.getRetCode() == Constant.SYS_ZERO && null != result.getData()) {
            // 关联关系
            StudentInfo student = (StudentInfo) result.getData();
            // 绑定学生信息
            LoginRelation loginRelation = new LoginRelation();
            loginRelation.setLogin(getLoginVO().getOpenidInfo().getLogin());
            loginRelation.setStudentInfo(student.getStudentInfo());
            loginRelationService.save(loginRelation);

            return Result.success(BeanUtil.copyProperties(student, StudentIdAndNameVO.class));
        }

        return result;
    }

    @PostMapping("/bindStudent")
    @ApiOperation("绑定该学生")
    public Result bindStudent(@Valid @RequestBody AddBindStudentQuery addBindStudentQuery) {
        return loginService.bindStudent(addBindStudentQuery, getLoginVO());
    }
}
