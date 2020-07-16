package com.educiot.recruit.server.app.controller.student;


import com.educiot.common.base.CodeMsg;
import com.educiot.common.base.Result;
import com.educiot.recruit.data.entity.StudentPublicRelation;
import com.educiot.recruit.data.entity.query.ChangeStatusQuery;
import com.educiot.recruit.data.entity.vo.CollegeVO;
import com.educiot.recruit.data.entity.vo.ProgressListVO;
import com.educiot.recruit.data.service.student.IProgressService;
import com.educiot.recruit.data.service.student.IStudentPublicRelationService;
import com.educiot.recruit.server.app.common.BaseController;
import com.educiot.recruit.server.app.common.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 学生报名进度表 前端控制器
 * </p>
 *
 * @author Mr.AI
 * @since 2020-04-13
 */
@RestController
@RequestMapping("/progress")
@Api(tags = {"学生报名进度信息"}, value = "ProgressController")
public class ProgressController extends BaseController {

    @Resource
    private IProgressService progressService;

    @Resource
    private IStudentPublicRelationService studentPublicRelationService;


    @GetMapping("/getProgress/{studentPublicRelationId}")
    @ApiOperation(value = "获取学生的报名进度", response = CollegeVO.class)
    public Result<?> getProgress(@PathVariable Long studentPublicRelationId) {

        List<ProgressListVO> result = progressService.getProgress(studentPublicRelationId);

        return Result.success(result);
    }

    @PostMapping("/changeStatus")
    @ApiOperation(value = "状态更改")
    public Result<?> changeStatus(@RequestBody ChangeStatusQuery changeStatusQuery) {

        LoginUser loginUser = getLoginUser();

        boolean status = progressService.changeStatus(changeStatusQuery, loginUser.getApiInfo().getUserId(), loginUser.getApiInfo().getUserName());

        if (status) {
            return Result.success();
        } else {
            return Result.error(CodeMsg.DATA_UPDATE_ERROR);
        }
    }

    @GetMapping("/getRegisterStatus/{studentPublicRelationId}")
    @ApiOperation("获取注册状态")
    public Result<?> getRegisterStatus(@PathVariable Long studentPublicRelationId) {

        StudentPublicRelation studentPublicRelation = studentPublicRelationService.getById(studentPublicRelationId);
        if (null != studentPublicRelation) {
            Integer status = studentPublicRelation.getRegisterStatus();
            return Result.success(status);
        } else {
            return Result.success(null);
        }

    }

}
