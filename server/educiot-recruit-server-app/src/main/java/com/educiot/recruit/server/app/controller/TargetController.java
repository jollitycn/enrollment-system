package com.educiot.recruit.server.app.controller;

import com.educiot.common.base.Result;
import com.educiot.recruit.data.entity.vo.TargetVO;
import com.educiot.recruit.data.service.ITargetService;
import com.educiot.recruit.server.app.common.BaseController;
import com.educiot.recruit.server.app.common.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 目标模块
 * </p>
 *
 * @author Mr.Ai
 * @since 2020-06-11
 */
@Api(tags = {"目标模块"}, value = "目标模块")
@RestController
@RequestMapping("/target")
@Log4j2
public class TargetController extends BaseController {

    @Autowired
    private ITargetService targetService;

    @GetMapping("/queryNodeNameList")
    @ApiOperation(value = "查询招生渠道的渠道名称列表", response = TargetVO.class)
    public Result queryNodeNameList() {
        log.info("查询招生渠道的渠道名称列表");
        //获取登录用户信息
        LoginUser loginUser = getLoginUser();
        List<String> strings = targetService.queryNodeNameList(loginUser.getCurrentSchoolId(), loginUser.getCurrentSemesterId());
        return Result.success(strings);
    }


}
