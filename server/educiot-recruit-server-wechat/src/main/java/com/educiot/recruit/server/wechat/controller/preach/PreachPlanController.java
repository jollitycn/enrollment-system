package com.educiot.recruit.server.wechat.controller.preach;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.common.base.BasePageQuery;
import com.educiot.common.base.Result;
import com.educiot.recruit.data.entity.preach.PreachPlan;
import com.educiot.recruit.data.entity.vo.preach.PreachPlanVO;
import com.educiot.recruit.data.service.preach.IPreachPlanService;
import com.educiot.recruit.server.wechat.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 招生宣讲计划管理
 * </p>
 *
 * @author Hongsifan
 * @since 2020-04-14
 */
@Api(tags = {"宣讲计划管理"}, value = "PreachPlanController")
@RestController
@RequestMapping("/preach-plan")
public class PreachPlanController extends BaseController {

    @Resource
    private IPreachPlanService preachPlanService;

    @GetMapping("/detail/{id}")
    @ApiOperation(value = "获取宣讲计划详情", response = PreachPlan.class)
    public Result<?> detail(@PathVariable Long id) {
        PreachPlan obj = preachPlanService.getById(id);
        return Result.success(obj);
    }

    @PostMapping("/listPageByStudentPublicRelationId")
    @ApiOperation(value = "获取签到宣讲计划列表", response = PreachPlanVO.class)
    public Result<?> listPageByStudentPublicRelationId(@RequestBody BasePageQuery query) {
        IPage<PreachPlanVO> result = preachPlanService.listPageByStudentPublicRelationId(getLoginVO().getStudentPublicRelationId(), query);
        return Result.success(result);
    }

}