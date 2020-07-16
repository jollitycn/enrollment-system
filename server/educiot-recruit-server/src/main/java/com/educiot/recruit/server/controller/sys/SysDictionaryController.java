package com.educiot.recruit.server.controller.sys;


import com.educiot.common.base.Result;

import com.educiot.recruit.data.entity.SysDictionary;
import com.educiot.recruit.data.entity.query.AddDictionaryQuery;
import com.educiot.recruit.data.entity.query.ListDictionaryQuery;
import com.educiot.recruit.data.entity.query.UpdateDictionaryQuery;
import com.educiot.recruit.data.entity.vo.SysDictVO;
import com.educiot.recruit.data.service.ISysDictionaryService;
import com.educiot.recruit.server.common.BaseController;
import com.educiot.recruit.server.common.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * <p>
 * 系统数字字典 前端控制器
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@RestController
@RequestMapping("/sys-dictionary")
@Api(tags = {"系统数字字典"}, value = "SysDictionaryController")
public class SysDictionaryController extends BaseController {

    @Resource
    private ISysDictionaryService sysDictionaryService;

    @PutMapping("/add")
    @ApiOperation("新增数据字典")
    public Result<?> add(@RequestBody AddDictionaryQuery addDictionaryQuery) {

        LoginUser loginUser = getLoginUser();
        return sysDictionaryService.addDictionary(addDictionaryQuery, loginUser.getCurrentSchoolId(),loginUser.getApiInfo().getSchoolId(),loginUser.getApiInfo().getUserId(),loginUser.getApiInfo().getUserName());
    }

    @DeleteMapping("/delete/{dictionaryId}")
    @ApiOperation("删除数据字典")
    public Result<?> delete(@PathVariable Long dictionaryId) {

        return sysDictionaryService.deleteDictionary(dictionaryId);
    }

    @PostMapping("/update")
    @ApiOperation("更新数据字典")
    public Result<?> update(@RequestBody UpdateDictionaryQuery updateDictionaryQuery) {

        LoginUser loginUser = getLoginUser();
        return sysDictionaryService.updateDictionary(updateDictionaryQuery, loginUser.getCurrentSchoolId(),loginUser.getApiInfo().getSchoolId(),loginUser.getApiInfo().getUserId(),loginUser.getApiInfo().getUserName());
    }

    @GetMapping("/detail/{dictionaryId}")
    @ApiOperation(value = "获取字典详情", response = SysDictionary.class)
    public Result<?> detail(@PathVariable Long dictionaryId) {

        SysDictionary sysDictionary = sysDictionaryService.getById(dictionaryId);

        return Result.success(sysDictionary);
    }

    @GetMapping("/list")
    @ApiOperation(value = "获取数据字典列表", response = SysDictionary.class)
    public Result<?> list(ListDictionaryQuery listDictionaryQuery) {

        LoginUser loginUser = getLoginUser();
        List<SysDictionary> result = sysDictionaryService.getDictList(loginUser.getCurrentSchoolId(), listDictionaryQuery);
        return Result.success(result);
    }

    @GetMapping("/listAllData")
    @ApiOperation(value = "获取全部数据字典", response = SysDictVO.class)
    public Result<?> list() {

        LoginUser loginUser = getLoginUser();
        List<SysDictVO> list = sysDictionaryService.getAllDict(loginUser.getCurrentSchoolId());
        return Result.success(list);
    }

    @GetMapping("/getDictListByCode/{dataCode}")
    @ApiOperation(value = "根据date_code获取数据字典", response = SysDictionary.class)
    public Result<?> getDictListByCode(@PathVariable String dataCode) {

        LoginUser loginUser = getLoginUser();
        List<SysDictionary> result = sysDictionaryService.getDictListByCode(loginUser.getCurrentSchoolId(), dataCode);
        return Result.success(result);
    }
}
