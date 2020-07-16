package com.educiot.recruit.server.app.controller.preach;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.common.base.Result;
import com.educiot.recruit.data.entity.ext.PreachRegisterExt;
import com.educiot.recruit.data.entity.preach.PreachRegister;
import com.educiot.recruit.data.entity.query.common.UpdateStatusQuery;
import com.educiot.recruit.data.entity.query.preach.register.PreachRegisterAddQuery;
import com.educiot.recruit.data.entity.query.preach.register.PreachRegisterPageQuery;
import com.educiot.recruit.data.entity.query.preach.register.PreachRegisterUpdateQuery;
import com.educiot.recruit.data.entity.vo.preach.PreachRegisterPageVO;
import com.educiot.recruit.data.service.preach.IPreachRegisterService;
import com.educiot.recruit.server.app.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 宣讲计划报名表 前端控制器
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */

@Api(tags = {PreachRegisterController.CLASS_NAME_ZN}, value = PreachRegisterController.CLASS_NAME_EN)
@RestController
@RequestMapping(PreachRegisterController.REQUEST_MAPPING)
@Log4j2
public class PreachRegisterController extends BaseController {
    public static final String CLASS_NAME_ZN = "宣讲计划报名签到";
    public static final String REQUEST_MAPPING = "public-register";
    public static final String CLASS_NAME_EN = "PreachRegisterController";
    @Resource
    private IPreachRegisterService preachRegisterService;

    /***
     *
     * @param query
     * @return
     */
    @PostMapping(METHOD_PAGE)
    @ApiOperation(value = METHOD_PAGE_NAME, response = PreachRegisterPageVO.class)
    public Result<?> page(@RequestBody PreachRegisterPageQuery query) {
        log.info(METHOD_PAGE_NAME);
        IPage<PreachRegisterPageVO> result = preachRegisterService.listPage(getLoginUser().getApiInfo().getUserId(), query);
        return Result.success(result);
    }

    @PostMapping("/queryPreachList")
    @ApiOperation(value = "查询宣讲计划", response = PreachRegisterPageVO.class)
    public Result<?> queryPreachList(@RequestBody PreachRegisterPageQuery query) {
        IPage<PreachRegisterPageVO> result = preachRegisterService.queryPreachList(query);
        return Result.success(result);
    }

//
//    /**
//     *
//     * @return
//     */
//    @PostMapping(METHOD_PAGE_GROUP_COUNT)
//    @ApiOperation(value = METHOD_PAGE_GROUP_COUNT_NAME ,response = PreachRegisterGroupCountVO.class)
//    public Result<?> pageGroupCount() {
//        log.info(METHOD_PAGE_GROUP_COUNT_NAME);
//        IPage<PreachRegisterGroupCountVO> result = preachRegisterService.listPageGroupCount(getLoginUser().getApiInfo().getUserId());
//        return Result.success(result);
//    }

    /**
     * @param query
     * @return
     */
    @PutMapping(METHOD_ADD)
    @ApiOperation(METHOD_ADD_NAME)
    public Result<?> add(@RequestBody PreachRegisterAddQuery query) {
        log.info(METHOD_ADD_NAME);
        PreachRegister result = preachRegisterService.add(getLoginUser().getApiInfo().getUserId(), getLoginUser().getApiInfo().getUserName(), query);
        return Result.success(result);
    }

    /**
     * @param query
     * @return
     */
    @PostMapping(METHOD_UPDATE)
    @ApiOperation(METHOD_UPDATE_NAME)
    public Result<?> update(@RequestBody PreachRegisterUpdateQuery query) {
        log.info(METHOD_UPDATE_NAME);
        int result = preachRegisterService.update(getLoginUser().getApiInfo().getUserId(), getLoginUser().getApiInfo().getUserName(), query);
        return Result.success(result);
    }


    /**
     * @param id
     * @return
     */
    @GetMapping(METHOD_DETAIL)
    @ApiOperation(value = METHOD_DETAIL_NAME, response = PreachRegisterExt.class)
    public Result<?> detail(@PathVariable Long id) {
        log.info(METHOD_DETAIL_NAME);
        PreachRegisterExt obj = preachRegisterService.detail(id);
        return Result.success(obj);
    }

    /**
     * @param query
     * @return
     */
    @PostMapping(METHOD_UPDATE_STATUS)
    @ApiOperation(value = METHOD_UPDATE_STATUS_NAME)
    private Result<?> updateStatus(@RequestBody UpdateStatusQuery query) {
        log.info(METHOD_UPDATE_STATUS_NAME);
        boolean result = preachRegisterService.updateStatus(query);
        return Result.success(result);
    }
}

