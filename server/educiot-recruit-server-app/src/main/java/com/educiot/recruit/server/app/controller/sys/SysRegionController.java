package com.educiot.recruit.server.app.controller.sys;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.common.constant.Constant;
import com.educiot.recruit.common.util.JSONRedisUtil;
import com.educiot.recruit.data.entity.SysRegion;
import com.educiot.recruit.data.entity.vo.SysRegionVO;
import com.educiot.recruit.data.service.ISysRegionService;
import com.educiot.recruit.server.app.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 系统地区通用接口
 *
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/4/20 14:15
 */
@RestController
@RequestMapping("/sysRegion")
@Api(tags = {"系统地区管理"}, value = "SysRegionController")
public class SysRegionController extends BaseController {

    @Resource
    private ISysRegionService sysRegionService;

    @GetMapping("/listProvince")
    @ApiOperation(value = "获取省份列表")
    public Result<List<SysRegion>> listProvince() {
        QueryWrapper<SysRegion> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", 100000);
        List<SysRegion> result = sysRegionService.list(wrapper);
        return Result.success(result);
    }

    @GetMapping("/listCity/{provinceId}")
    @ApiOperation(value = "获取城市列表")
    public Result<List<SysRegion>> listCity(@PathVariable Long provinceId) {
        QueryWrapper<SysRegion> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", provinceId);
        List<SysRegion> result = sysRegionService.list(wrapper);
        return Result.success(result);
    }

    @GetMapping("/listDistrict/{cityId}")
    @ApiOperation(value = "获取区县列表")
    public Result<List<SysRegion>> listDistrict(@PathVariable Long cityId) {
        QueryWrapper<SysRegion> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", cityId);
        List<SysRegion> result = sysRegionService.list(wrapper);
        return Result.success(result);
    }

    @GetMapping("/getAllList")
    @ApiOperation(value = "获取全部地区列表", response = SysRegionVO.class)
    public Result<?> getAllList() {
//        List<SysRegionVO> result = sysRegionService.getAllList();
//        return Result.success(result);

        List<SysRegionVO> result = JSONRedisUtil.parseArray(Constant.RedisKey.SYS_REGIONS, SysRegionVO.class);
        return Result.success(result);
    }
}
