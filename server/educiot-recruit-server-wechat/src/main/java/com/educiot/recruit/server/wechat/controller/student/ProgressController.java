package com.educiot.recruit.server.wechat.controller.student;


import com.educiot.common.base.Result;
import com.educiot.recruit.data.entity.vo.ProgressListVO;
import com.educiot.recruit.data.entity.vo.ProgressVO;
import com.educiot.recruit.data.service.student.IProgressService;
import com.educiot.recruit.server.wechat.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/queryProgressInfo")
    @ApiOperation("根据学生ID查询学生报名进度信息")
    public Result queryProgressInfo() {
        ProgressVO progressVO = progressService.queryProgressInfoByStudentId(getLoginVO().getCurrentStudent().getStudentInfo());
        return Result.success(progressVO);
    }

    @GetMapping("/getProgress")
    @ApiOperation(value = "获取学生的报名进度", response = ProgressListVO.class)
    public Result<?> getProgress() {

        List<ProgressListVO> result = progressService.getProgress(getLoginVO().getStudentPublicRelationId());

        return Result.success(result);
    }
}
