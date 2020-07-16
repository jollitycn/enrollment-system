package com.educiot.recruit.server.app.controller.student;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.common.base.Result;
import com.educiot.recruit.data.entity.AimStudent;
import com.educiot.recruit.data.entity.query.AddAimStudentQuery;
import com.educiot.recruit.data.entity.query.ListAimStudentPageQuery;
import com.educiot.recruit.data.entity.vo.ProgressListVO;
import com.educiot.recruit.data.service.IAimStudentService;
import com.educiot.recruit.server.app.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/listAimStudentPage")
    @ApiOperation("分页获取目标生信息列表")
    public Result<IPage<AimStudent>> listAimStudentPage(@Valid ListAimStudentPageQuery param) {
        param.setCreatorId(getLoginUser().getCharger().getChargerId());
        IPage<AimStudent> aimStudentIPage = aimStudentService.listAimStudentPage(param);
        return Result.success(aimStudentIPage);
    }

    @PostMapping("/saveOrUpdateAimStudentInfo")
    @ApiOperation("添加或编辑目标生")
    public Result saveOrUpdateAimStudentInfo(@Valid @RequestBody AddAimStudentQuery addAimStudentQuery) {
        addAimStudentQuery.setCreatorId(getLoginUser().getCharger().getChargerId());
        addAimStudentQuery.setCreatorName(getLoginUser().getCharger().getName());
        Boolean aBoolean = aimStudentService.saveOrUpdateAimStudentInfo(addAimStudentQuery);
        return Result.success(aBoolean);
    }

    @GetMapping("/queryAimStudentInfo/{aimStudentId}")
    @ApiOperation(value = "获取目标生详情信息", response = ProgressListVO.class)
    public Result queryAimStudentInfo(@PathVariable Long aimStudentId) {
        return Result.success(aimStudentService.queryAimStudentInfo(aimStudentId));
    }


}
