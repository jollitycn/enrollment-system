package com.educiot.recruit.server.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.recruit.data.entity.AimStudent;
import com.educiot.recruit.data.entity.query.ListAimStudentPageQuery;
import com.educiot.recruit.data.service.IAimStudentService;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.server.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 目标生表 前端控制器
 * </p>
 *
 * @author Mr.Ai
 * @since 2020-04-23
 */
@RestController
@RequestMapping("/aim-student")
@Api(tags = {"目标生信息"}, value = "AimStudentController")
public class AimStudentController extends BaseController {

    @Resource
    private IAimStudentService aimStudentService;

    @GetMapping("/listGroupInfoPage")
    @ApiOperation("分页获取分组信息列表")
    public Result<IPage<AimStudent>> listGroupInfoPage(@Valid ListAimStudentPageQuery param) {
        IPage<AimStudent> aimStudentIPage = aimStudentService.listAimStudentPage(param);
        return Result.success(aimStudentIPage);
    }

}
