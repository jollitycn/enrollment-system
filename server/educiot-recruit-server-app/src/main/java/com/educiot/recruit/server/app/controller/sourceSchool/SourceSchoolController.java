package com.educiot.recruit.server.app.controller.sourceSchool;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.common.api.APIInfo;
import com.educiot.common.base.MyExcption;
import com.educiot.common.base.Result;
import com.educiot.recruit.data.entity.ext.ChargerExt;
import com.educiot.recruit.data.entity.query.*;
import com.educiot.recruit.data.entity.vo.*;
import com.educiot.recruit.data.service.IClassService;
import com.educiot.recruit.data.service.ISourceSchoolService;
import com.educiot.recruit.server.app.common.BaseController;
import com.educiot.recruit.server.app.common.LoginUser;
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
 * 责任学校管理
 *
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/4/20 17:57
 */
@Api(tags = {"责任学校管理"}, value = "SourceSchoolController")
@RequestMapping("/sourceSchool")
@RestController
public class SourceSchoolController extends BaseController {

    @Resource
    private ISourceSchoolService sourceSchoolService;

    @Resource
    private IClassService classService;

    @GetMapping("/listCompleteRate")
    @ApiOperation(value = "获取完成度统计")
    public Result<List<CompleteRateVO>> listCompleteRate() {
        // 获取登录用户的招生组id
        ChargerExt charger = getLoginUser().getCharger();
        Long currentSemesterId = getLoginUser().getCurrentSemesterId();
        Long groupId = null == charger ? null : charger.getGroupId();
        List<CompleteRateVO> list = sourceSchoolService.listCompleteRate(groupId,currentSemesterId);
        return Result.success(list);

    }

    @GetMapping("/listSourceSchoolType")
    @ApiOperation("获取招生学校下的学校类型（数据字典配置）")
    public Result<List<SysDictVO>> listSourceSchoolType() {
        // 获取招生学校id
        List<SysDictVO> schoolTypes =
                sourceSchoolService.listSourceSchoolType(getLoginUser().getCurrentSchoolId());
        return Result.success(schoolTypes);
    }

    @PostMapping("/addSourceSchool")
    @ApiOperation("新增生源学校")
    public Result addSourceSchool(@Valid @RequestBody AddSourceSchoolQuery sourceSchoolInfo) {
        // 获取登录用户信息
        LoginUser loginUser = getLoginUser();
        APIInfo apiInfo = loginUser.getApiInfo();
        return sourceSchoolService.addSourceSchool(sourceSchoolInfo, loginUser.getCurrentSchoolId(),
                apiInfo.getUserName(), apiInfo.getUserId());
    }

    @PutMapping("/updateSourceSchool")
    @ApiOperation("编辑生源学校信息")
    public Result updateSourceSchool(@Valid @RequestBody UpdateSourceSchoolQuery sourceSchoolInfo) {
        return sourceSchoolService.updateSourceSchool(sourceSchoolInfo);
    }

    @GetMapping("/getSourceSchoolDetail/{sourceSchoolId}")
    @ApiImplicitParam(name = "sourceSchoolId", value = "生源学校ID", required = true)
    @ApiOperation("获取生源学校详情")
    public Result<SourceSchoolDetailVO> getSourceSchoolDetail(@Valid @PathVariable("sourceSchoolId")
                                                              @NotNull(message = "生源学校ID不能为空")
                                                              @Min(value = 1L, message = "生源学校ID最小为1")
                                                              @Max(value = Long.MAX_VALUE, message = "生源学校ID超出最大限制")
                                                                      Long sourceSchoolId) {
        SourceSchoolDetailVO sourceSchoolDetail = sourceSchoolService.getSourceSchoolDetail(sourceSchoolId);
        return Result.success(sourceSchoolDetail);
    }

    @GetMapping("/listSchoolClassPage")
    @ApiOperation("分页获取生源学校下的班级")
    public Result<IPage<ClassVO>> listSchoolClassPage(@Valid ListClassPageQuery pageQuery) throws MyExcption {
//        pageQuery.setSemesterId(getLoginUser().getCurrentSemesterId());
        IPage<ClassVO> page = classService.listSchoolClassPage(pageQuery, getLoginUser().getCurrentSemesterId());
        return Result.success(page);
    }

    @PostMapping("/addClass")
    @ApiOperation("新增班级")
    public Result addClass(@Valid @RequestBody AddClassQuery classQuery) throws MyExcption {
        APIInfo apiInfo = getLoginUser().getApiInfo();
        return classService.addClass(classQuery, apiInfo.getUserId(), apiInfo.getUserName(), getLoginUser().getCurrentSemesterId());
    }

    @GetMapping("/getClassInfo/{classId}")
    @ApiImplicitParam(name = "classId", value = "班级ID", required = true)
    @ApiOperation("获取班级信息")
    public Result<ClassVO> getClassInfo(@Valid @PathVariable("classId")
                                        @NotNull(message = "班级ID不能为空")
                                        @Min(value = 1L, message = "班级ID最小为1")
                                        @Max(value = Long.MAX_VALUE, message = "班级ID超出最大限制")
                                                Long classId) throws MyExcption {
        return Result.success(classService.getClassInfo(classId, getLoginUser().getCurrentSemesterId()));
    }

    @PutMapping("/updateClass")
    @ApiOperation("编辑班级信息")
    public Result updateClass(@Valid @RequestBody UpdateClassQuery updateClassQuery) {
        return classService.updateClass(updateClassQuery);
    }

    @PutMapping("/deleteClass/{classId}")
    @ApiOperation("删除班级信息")
    public Result deleteClass(@Valid @PathVariable("classId")
                              @NotNull(message = "班级ID不能为空")
                              @Min(value = 1L, message = "班级ID最小为1")
                              @Max(value = Long.MAX_VALUE, message = "班级ID超出最大限制")
                                      Long classId) {
        return classService.deleteClass(classId);
    }

    @GetMapping("/listAllSourceSchool")
    @ApiOperation("获取招生学校下的所有的生源学校")
    public Result<List<SourceSchoolVO>> listAllSourceSchool() {
        List<SourceSchoolVO> schoolList =
                sourceSchoolService.listAllSourceSchool(getLoginUser().getCurrentSchoolId());
        return Result.success(schoolList);
    }


    @GetMapping("/listAllSourceSchoolGroup")
    @ApiOperation("获取招生学校以及当前分组下的所有的生源学校")
    public Result<List<SourceSchoolVO>> listAllSourceSchoolGroup() {
        List<SourceSchoolVO> schoolList =
                sourceSchoolService.listSourceSchoolByRecruitSchoolIdAndGroupId(getLoginUser().getCurrentSchoolId(), getLoginUser().getCharger().getGroupId(), getLoginUser().getCharger().getChargerId(), getLoginUser().getCurrentSemesterId());
        return Result.success(schoolList);
    }
}
