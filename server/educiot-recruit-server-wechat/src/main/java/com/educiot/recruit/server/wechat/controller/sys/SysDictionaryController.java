package com.educiot.recruit.server.wechat.controller.sys;


import com.educiot.recruit.data.service.ISysDictionaryService;
import com.educiot.recruit.server.wechat.common.BaseController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


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
//
//    @PutMapping("/add")
//    @ApiOperation("新增数据字典")
//    public Result<?> add(@RequestBody AddDictionaryQuery addDictionaryQuery) {
//
//        LoginUser loginUser = getLoginUser();
//        return sysDictionaryService.addDictionary(addDictionaryQuery, loginUser);
//    }
//
//    @DeleteMapping("/delete/{dictionaryId}")
//    @ApiOperation("删除数据字典")
//    public Result<?> delete(@PathVariable Long dictionaryId) {
//
//        return sysDictionaryService.deleteDictionary(dictionaryId);
//    }
//
//    @PostMapping("/update")
//    @ApiOperation("更新数据字典")
//    public Result<?> update(@RequestBody UpdateDictionaryQuery updateDictionaryQuery) {
//
//        LoginUser loginUser = getLoginUser();
//        return sysDictionaryService.updateDictionary(updateDictionaryQuery, loginUser);
//    }
//
//    @GetMapping("/detail/{dictionaryId}")
//    @ApiOperation(value = "获取字典详情", response = SysDictionary.class)
//    public Result<?> detail(@PathVariable Long dictionaryId) {
//
//        SysDictionary sysDictionary = sysDictionaryService.getById(dictionaryId);
//
//        return Result.success(sysDictionary);
//    }
//
//    @GetMapping("/list")
//    @ApiOperation(value = "获取数据字典列表", response = SysDictionary.class)
//    public Result<?> list(ListDictionaryQuery listDictionaryQuery) {
//
//        LoginUser loginUser = getLoginUser();
//        List<SysDictionary> result = sysDictionaryService.getDictList(loginUser.getCurrentSchoolId(), listDictionaryQuery);
//        return Result.success(result);
//    }
//
//    @GetMapping("/listAllData")
//    @ApiOperation(value = "获取全部数据字典", response = SysDictVO.class)
//    public Result<?> list() {
//
//        LoginUser loginUser = getLoginUser();
//        List<SysDictVO> list = sysDictionaryService.getAllDict(loginUser);
//        return Result.success(list);
//    }

//    @GetMapping("/getDictListByCode/{dataCode}")
//    @ApiOperation(value = "根据date_code获取数据字典", response = SysDictionary.class)
//    public Result<?> getDictListByCode(@PathVariable String dataCode) {
//
//        LoginUser loginUser = CacheUtil.getLoginUser();
//        List<SysDictionary> result = sysDictionaryService.getDictListByCode(loginUser, dataCode);
//    }
}
