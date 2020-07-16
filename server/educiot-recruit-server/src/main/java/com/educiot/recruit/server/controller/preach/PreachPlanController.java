package com.educiot.recruit.server.controller.preach;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.recruit.common.base.MyExcption;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.entity.query.common.UpdateStatusQuery;
import com.educiot.recruit.data.entity.query.preach.PreachPlanAddQuery;
import com.educiot.recruit.data.entity.query.preach.PreachPlanPageQuery;
import com.educiot.recruit.data.entity.query.preach.PreachPlanUpdateQuery;
import com.educiot.recruit.data.entity.vo.preach.PreachPlanVO;
import com.educiot.recruit.data.service.preach.IPreachPlanService;
import com.educiot.recruit.server.common.BaseController;
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

    @PutMapping("/add")
    @ApiOperation("新增宣讲计划")
    public Result<?> add(@RequestBody PreachPlanAddQuery query) throws MyExcption {
        return preachPlanService.add(getLoginUser(), getLoginUser().getCurrentSemesterId(), getLoginUser().getSemesterName(), query);
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除宣讲计划")
    public Result<?> delete(@PathVariable Long id) {
        return preachPlanService.delete(getLoginUser(), id);
    }

    @PostMapping("/update")
    @ApiOperation("更新宣讲计划")
    public Result<?> update(@RequestBody PreachPlanUpdateQuery query) throws MyExcption {
        return preachPlanService.update(getLoginUser(), query);
    }

    @GetMapping("/detail/{id}")
    @ApiOperation(value = "获取宣讲计划详情", response = PreachPlanVO.class)
    public Result<?> detail(@PathVariable Long id) {
        PreachPlanVO obj = preachPlanService.detail(id);
        return Result.success(obj);
    }

    @PostMapping("/page")
    @ApiOperation(value = "获取演讲计划列表")
    public Result<?> page(@RequestBody PreachPlanPageQuery query) {
        IPage<PreachPlanVO> result = null;
        try {
            result = preachPlanService.listPage(query, query.getSemesterId());
        } catch (MyExcption myExcption) {
            myExcption.printStackTrace();
        }
        return Result.success(result);
    }

    @PutMapping("/publish/{id}")
    @ApiOperation(value = "发布")
    public Result<?> publish(@PathVariable Long id) {
        UpdateStatusQuery query = new UpdateStatusQuery();
        query.setId(id);
        query.setStatus(2);
        boolean result = preachPlanService.publish(query);
        //
        return Result.success(result);
    }

    @PostMapping("/updateStatus")
    @ApiOperation(value = "修改状态")
    private Result<?> updateStatus(@RequestBody UpdateStatusQuery query) {
        boolean result = preachPlanService.updateStatus(query);
        return Result.success(result);
    }
}
