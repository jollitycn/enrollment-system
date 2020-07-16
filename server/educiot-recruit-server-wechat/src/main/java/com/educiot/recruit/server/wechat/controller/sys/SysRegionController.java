package com.educiot.recruit.server.wechat.controller.sys;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.educiot.common.base.Result;
import com.educiot.recruit.data.entity.SysRegion;
import com.educiot.recruit.data.entity.vo.SysRegionVO;
import com.educiot.recruit.data.service.ISysRegionService;
import com.educiot.recruit.server.wechat.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 系统地区表 前端控制器
 * </p>
 *
 * @author LiuHao
 * @since 2020-01-09
 */
@RestController
@RequestMapping("/sys-region")
@Api(tags = {"系统地区管理"})
public class SysRegionController extends BaseController {

    @Resource
    private ISysRegionService sysRegionService;

    @GetMapping("/getProvinceList")
    @ApiOperation(value = "获取省份列表", response = SysRegion.class)
    public Result<?> getProvinceList() {
        QueryWrapper<SysRegion> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", 100000);
        List<SysRegion> result = sysRegionService.list(wrapper);
        return Result.success(result);

    }

    @GetMapping("/getCityList/{provinceId}")
    @ApiOperation(value = "获取城市列表", response = SysRegion.class)
    public Result<?> getCityList(@PathVariable Long provinceId) {
        QueryWrapper<SysRegion> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", provinceId);
        List<SysRegion> result = sysRegionService.list(wrapper);
        return Result.success(result);
    }

    @GetMapping("/getDistrictList/{cityId}")
    @ApiOperation(value = "获取区县列表", response = SysRegion.class)
    public Result<?> getDistrictList(@PathVariable Long cityId) {
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
        List<SysRegionVO> result = sysRegionService.getAllList();
        return Result.success(result);
    }
}
