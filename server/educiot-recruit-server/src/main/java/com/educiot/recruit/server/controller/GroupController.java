package com.educiot.recruit.server.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.common.api.APIInfo;
import com.educiot.common.base.Result;
import com.educiot.recruit.data.entity.query.*;
import com.educiot.recruit.data.entity.vo.GroupDetailVO;
import com.educiot.recruit.data.entity.vo.GroupVO;
import com.educiot.recruit.data.entity.vo.SourceSchoolVO;
import com.educiot.recruit.data.entity.vo.SysDictVO;
import com.educiot.recruit.data.service.IGroupService;
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
import java.util.List;

/**
 * <p>
 * 招生组表 前端控制器
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@Api(tags = {"招生区域"}, value = "GroupController")
@RestController
@RequestMapping("/group")
public class GroupController extends BaseController {

    @Resource
    private IGroupService groupService;

    @GetMapping("/listGroupPage")
    @ApiOperation("分页获取分组信息列表")
    public Result<IPage<GroupVO>> listGroupPage(@Valid ListGroupPageQuery param) {
        IPage<GroupVO> page = groupService.listGroupPage(param);
        return Result.success(page);
    }

    @GetMapping("/listRecruitZone")
    @ApiOperation("获取招生学校下的招生区域（数据字典配置）")
    public Result<List<SysDictVO>> listRecruitZone() {
        List<SysDictVO> schoolTypes = groupService.listRecruitZone(getLoginUser().getCurrentSchoolId());
        return Result.success(schoolTypes);
    }

    @PostMapping("/addGroup")
    @ApiOperation("新增分组信息")
    public Result addGroup(@Valid @RequestBody AddGroupQuery groupInfo) {
        // 获取登录用户信息
        APIInfo user = getLoginUser().getApiInfo();
        return groupService.addGroup(groupInfo,user.getUserId(), user.getUserName(),getLoginUser().getCurrentSchoolId(), groupInfo.getSemesterId());
    }

    @PutMapping("/updateGroup")
    @ApiOperation("编辑分组信息")
    public Result updateGroup(@Valid @RequestBody UpdateGroupQuery groupInfo) {
        return groupService.updateGroup(groupInfo, getLoginUser().getCurrentSchoolId());
    }

    @GetMapping("/getGroupDetail/{groupId}")
    @ApiImplicitParam(name = "groupId", value = "分组ID", required = true)
    @ApiOperation("获取分组的详情")
    public Result<GroupDetailVO> getGroupDetail(@Valid @PathVariable("groupId")
                                                @NotNull(message = "分组ID不能为空")
                                                @Min(value = 1L, message = "分组ID最小为1")
                                                @Max(value = Long.MAX_VALUE, message = "分组ID超出最大限制")
                                                        Long groupId) {
        GroupDetailVO groupDetailVO = groupService.getGroupDetail(groupId);
        return Result.success(groupDetailVO);
    }

    @DeleteMapping("/deleteGroup/{groupId}")
    @ApiImplicitParam(name = "groupId", value = "招生组ID", required = true)
    @ApiOperation("删除分组")
    public Result deleteGroup(@Valid @PathVariable("groupId")
                              @NotNull(message = "招生组ID不能为空")
                              @Min(value = 1L, message = "招生组ID最小为1")
                              @Max(value = Long.MAX_VALUE, message = "招生组ID超出最大限制")
                                      Long groupId) {
        return groupService.deleteGroup(groupId);
    }

    @PostMapping("/addCharger")
    @ApiOperation("分组添加负责人")
    public Result addCharger(@Valid @RequestBody ChargerQuery chargerQuery) {
        return groupService.addCharger(chargerQuery.getSemesterId(), chargerQuery);
    }

    @DeleteMapping("/deleteCharger/{chargerId}")
    @ApiImplicitParam(name = "chargerId", value = "负责人ID", required = true)
    @ApiOperation("删除分组下的负责人")
    public Result deleteCharger(@Valid @PathVariable("chargerId") @NotNull(message = "负责人ID不能为空")
                                @Min(value = 1L, message = "负责人ID最小为1")
                                @Max(value = Long.MAX_VALUE, message = "负责人ID超出最大限制")
                                        Long chargerId) {
        return groupService.deleteCharger(chargerId);
    }

    @GetMapping("/listSourceSchool/{groupId}")
    @ApiImplicitParam(name = "groupId", value = "分组ID", required = true)
    @ApiOperation("获取生源学校")
    public Result<List<SourceSchoolVO>> listSourceSchool(@Valid @PathVariable("groupId")
                                                         @NotNull(message = "分组ID不能为空")
                                                         @Min(value = 1L, message = "分组ID最小为1")
                                                         @Max(value = Long.MAX_VALUE, message = "分组ID超出最大限制")
                                                                 Long groupId) {
        List<SourceSchoolVO> sourceSchools = groupService.listSourceSchool(groupId, getLoginUser().getCurrentSchoolId());
        return Result.success(sourceSchools);
    }

    @PostMapping("/addGroupSourceRelation")
    @ApiOperation("分组添加负责学校")
    public Result addGroupSourceRelation(@Valid @RequestBody GroupSourceRelationQuery groupSourceRelationQuery) {
        return groupService.addGroupSourceRelation(groupSourceRelationQuery);
    }

    @DeleteMapping("/deleteGroupSourceRelation")
    @ApiOperation("删除分组下的学校")
    public Result deleteGroupSourceRelation(@Valid GroupSourceRelationQuery groupSourceRelationQuery) {
        return groupService.deleteGroupSourceRelation(groupSourceRelationQuery);
    }
}
