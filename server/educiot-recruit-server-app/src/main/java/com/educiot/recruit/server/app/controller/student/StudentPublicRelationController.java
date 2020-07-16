package com.educiot.recruit.server.app.controller.student;


import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.entity.StudentPublicRelation;
import com.educiot.recruit.data.entity.query.UpdateStudentPublicRelationStatusQuery;
import com.educiot.recruit.data.entity.vo.GroupDetailVO;
import com.educiot.recruit.data.entity.vo.StudentPublicRelationRecordVO;
import com.educiot.recruit.data.entity.vo.StudentPublicRelationVO;
import com.educiot.recruit.data.service.IGroupService;
import com.educiot.recruit.data.service.student.IStudentPublicRelationService;
import com.educiot.recruit.server.app.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 意向生登录表 前端控制器
 * </p>
 *
 * @author Mr.AI
 * @since 2020-04-15
 */
@RestController
@RequestMapping("/student-public-relation")
@Api(tags = {"学生公关信息"}, value = "StudentPublicRelationController")
public class StudentPublicRelationController extends BaseController {

    @Resource
    private IStudentPublicRelationService studentPublicRelationService;

    @Resource
    private IGroupService groupService;

    @PostMapping("/saveOrUpdateStudentPublicRelation")
    @ApiOperation("添加或编辑学生公关信息")
    public Result saveOrUpdateStudentPublicRelation(@Valid @RequestBody StudentPublicRelation studentPublicRelation) {
        //添加学生公关信息时，需要返回公关信息ID,给沟通记录提供关联
        //添加学生基本信息时，初始化学生公关信息，添加公关信息只需要做编辑即可，查询可以获取公关ID
        return studentPublicRelationService.saveOrUpdateStudentPublicRelation(studentPublicRelation);
    }

    @GetMapping("/queryStudentPublicRelation")
    @ApiOperation("获取学生公关信息")
    public Result queryStudentPublicRelation(@Valid Long studentInfoId) {
        StudentPublicRelationRecordVO studentPublicRelationRecordVO = studentPublicRelationService.queryStudentPublicRelation(studentInfoId, getLoginUser().getCurrentSchoolId());
        return Result.success(studentPublicRelationRecordVO);
    }

    @PutMapping("/updateStudentPublicRelationById")
    @ApiOperation("修改公关信息中的报名状态")
    public Result updateStudentPublicRelationById(@Valid @RequestBody UpdateStudentPublicRelationStatusQuery studentPublicRelation) {
        return studentPublicRelationService.updateStudentPublicRelationById(studentPublicRelation);
    }

    @GetMapping("/queryPrincipal")
    @ApiOperation("查询公关信息中的招生组下拉列表")
    public Result queryPrincipal(@Valid @RequestParam("studentInfoId") Long studentInfoId) {
        List<StudentPublicRelationVO> studentPublicRelationVOS = studentPublicRelationService.queryPrincipal(studentInfoId, getLoginUser().getCurrentSchoolId());
        return Result.success(studentPublicRelationVOS);

    }

    @GetMapping("/getGroupDetail/{groupId}")
    @ApiImplicitParam(name = "groupId", value = "分组ID", required = true)
    @ApiOperation("获取分组下的负责人详情")
    public Result<GroupDetailVO> getGroupDetail(@Valid @PathVariable("groupId")
                                                @NotNull(message = "分组ID不能为空")
                                                @Min(value = 1L, message = "分组ID最小为1")
                                                @Max(value = Long.MAX_VALUE, message = "分组ID超出最大限制")
                                                        Long groupId) {
        GroupDetailVO groupDetailVO = groupService.getGroupDetail(groupId);
        return Result.success(groupDetailVO);
    }
}
