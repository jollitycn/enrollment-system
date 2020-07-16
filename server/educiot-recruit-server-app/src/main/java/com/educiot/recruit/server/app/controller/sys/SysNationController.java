package com.educiot.recruit.server.app.controller.sys;


import com.educiot.common.base.Result;
import com.educiot.recruit.data.entity.SysNation;
import com.educiot.recruit.data.service.ISysNationService;
import com.educiot.recruit.server.app.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-27
 */
@RestController
@RequestMapping(SysNationController.REQUEST_MAPPING)
@Api(tags = {SysNationController.CLASS_NAME_ZN}, value = SysNationController.CLASS_NAME_EN)
@Log4j2
public class SysNationController extends BaseController {
    public static final String CLASS_NAME_ZN = "系统民族信息";
    public static final String REQUEST_MAPPING = "sys-nation";
    public static final String CLASS_NAME_EN = "SysNationController";

    @Resource
    private ISysNationService sysNationService;


    /***
     *获取民族接口
     * @return List
     */
    @PostMapping(METHOD_LIST)
    @ApiOperation(value = METHOD_LIST_NAME, response = SysNation.class)
    public Result<?> list() {
        log.info(METHOD_PAGE_NAME);
        List<SysNation> result = sysNationService.list();
        return Result.success(result);
    }

}
