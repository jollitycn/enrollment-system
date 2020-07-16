package com.educiot.recruit.server.app.controller.student;


import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.entity.RecruitWay;
import com.educiot.recruit.data.service.student.IRecruitWayService;
import com.educiot.recruit.server.app.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 学生招生渠道表 前端控制器
 * </p>
 *
 * @author Mr.AI
 * @since 2020-04-13
 */
@RestController
@RequestMapping("/recruit-way")
@Api(tags = {"学生招生渠道信息"}, value = "RecruitWayController")
public class RecruitWayController extends BaseController {

    @Resource
    private IRecruitWayService recruitWayService;

    @PostMapping("/saveOrUpdateRecruitWayInfo")
    @ApiOperation("添加或修改学生招生渠道信息")
    public Result saveOrUpdateRecruitWayInfo(@Valid @RequestBody RecruitWay recruitWay) {
        return recruitWayService.saveOrUpdateRecruitWayInfo(recruitWay);
    }

    @GetMapping("/queryRecruitWayInfo")
    @ApiOperation("根据学生公关ID查询学生招生渠道信息")
    public Result queryRecruitWayInfo(@Valid Long studentPublicRelationId) {
        return Result.success(recruitWayService.queryRecruitWayInfo(studentPublicRelationId));
    }

}
