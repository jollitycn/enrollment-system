package com.educiot.recruit.server.wechat.controller.student;


import com.educiot.common.base.Result;
import com.educiot.recruit.data.entity.FamilyMember;
import com.educiot.recruit.data.entity.vo.FamilyMemberInfoVO;
import com.educiot.recruit.data.entity.vo.StudentChargerVO;
import com.educiot.recruit.data.service.student.IFamilyMemberService;
import com.educiot.recruit.server.wechat.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 家庭成员表 前端控制器
 * </p>
 *
 * @author Mr.Ai
 * @since 2020-04-13
 */
@RestController
@RequestMapping("/family-member")
@Api(tags = {"家庭成员信息"}, value = "FamilyMemberController")
public class FamilyMemberController extends BaseController {

    @Resource
    private IFamilyMemberService familyMemberService;

    @GetMapping("/listGroupInfoPage")
    @ApiOperation("根据学生基本信息ID查询家庭成员信息")
    public Result<List<FamilyMember>> queryFamilyMemberInfoList() {
        List<FamilyMember> familyMembers = familyMemberService.queryFamilyMemberInfoList(getLoginVO().getCurrentStudent().getStudentInfo());
        return Result.success(familyMembers);
    }

    @PostMapping("/saveOrUpdateFamilyMemberInfo")
    @ApiOperation("添加或修改学生家庭成员信息")
    public Result saveOrUpdateFamilyMemberInfo(@Valid @RequestBody FamilyMemberInfoVO familyMemberInfoVO) {
        Boolean aBoolean = familyMemberService.saveOrUpdateFamilyMemberInfo(familyMemberInfoVO.getAddFamilyMemberQueries(), getLoginVO().getCurrentStudent().getStudentInfo());
        return Result.success(aBoolean);
    }

    @GetMapping("/queryFamilyMemberStatu")
    @ApiOperation("查询学生家庭成员信息填写完成状态")
    public Result queryFamilyMemberStatu() {
        List<FamilyMember> familyMembers = familyMemberService.queryFamilyMemberInfoList(getLoginVO().getCurrentStudent().getStudentInfo());
        if (null != familyMembers && familyMembers.size() < 2) {
            return Result.success(false);
        }
        return Result.success(true);
    }

    @GetMapping("/queryTeacherCharge")
    @ApiOperation("查询负责老师信息")
    public Result queryTeacherCharge() {
        StudentChargerVO studentChargerVO = familyMemberService.queryTeacherCharge(getLoginVO().getCurrentStudent().getStudentInfo(), getLoginVO().getCurrentRecruitSchool().getRecruitSchoolId());
        return Result.success(studentChargerVO);
    }

}
