
package com.educiot.recruit.server.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.common.base.BasePageQuery;
import com.educiot.common.base.Result;
import com.educiot.recruit.data.entity.query.AddGroupInfoQuery;
import com.educiot.recruit.data.entity.query.UpdateGroupInfoQuery;
import com.educiot.recruit.data.entity.vo.ListGroupInfoPageVO;
import com.educiot.recruit.data.service.IGroupInfoService;
import com.educiot.recruit.server.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 招生组信息表 前端控制器
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@Api(tags = {"招生区域"}, value = "GroupInfoController")
@RestController
@RequestMapping("/groupInfo")
public class GroupInfoController extends BaseController {

    @Resource
    private IGroupInfoService groupInfoService;

    @GetMapping("/listGroupInfoPage")
    @ApiOperation("分页获取招生区域信息列表")
    public Result<IPage<ListGroupInfoPageVO>> listGroupInfoPage(@Valid BasePageQuery pageQuery) {
        // 获取当前登录用户的招生学校id
        Long recruitSchoolId = getLoginUser().getCurrentSchoolId();
        IPage<ListGroupInfoPageVO> page = groupInfoService.listGroupInfoPage(pageQuery, recruitSchoolId);
        return Result.success(page);
    }

    @PostMapping("/addGroupInfo")
    @ApiOperation("新增招生区域信息")
    public Result addGroupInfo(@Valid @RequestBody AddGroupInfoQuery groupInfo) {
        return groupInfoService.addGroupInfo(groupInfo, getLoginUser().getCurrentSchoolId(),getLoginUser().getApiInfo().getUserId(),getLoginUser().getApiInfo().getUserName());
    }

    @PutMapping("/updateGroupInfo")
    @ApiOperation("编辑招生区域信息")
    public Result updateGroupInfo(@Valid @RequestBody UpdateGroupInfoQuery groupInfo) {
        return groupInfoService.updateGroupInfo(groupInfo, getLoginUser().getCurrentSchoolId());
    }

    @PutMapping("/copyGroupInfo")
    @ApiOperation("复制招生区域信息")
    public Result copyGroupInfo(@Valid @RequestBody UpdateGroupInfoQuery groupInfo) {
        // 获取当前登录用户的招生学校id
        Long recruitSchoolId = getLoginUser().getCurrentSchoolId();
        return groupInfoService.copyGroupInfo(groupInfo, recruitSchoolId);
    }


    @GetMapping("/getGroupInfo/{groupInfoId}")
    @ApiImplicitParam(name = "groupInfoId", value = "招生区域信息ID", required = true)
    @ApiOperation("获取指定招生区域信息")
    public Result<ListGroupInfoPageVO> getGroupInfo(@Valid @PathVariable("groupInfoId")
                                                    @NotNull(message = "招生区域信息ID不能为空")
                                                    @Min(value = 1L, message = "招生区域信息ID不合法")
                                                    @Max(value = Long.MAX_VALUE, message = "招生组信息ID不合法") Long groupInfoId) {
        // 获取当前登录用户的招生学校id
        Long recruitSchoolId = getLoginUser().getCurrentSchoolId();
        ListGroupInfoPageVO groupInfo = groupInfoService.getGroupInfo(groupInfoId, recruitSchoolId);
        return Result.success(groupInfo);
    }
}
