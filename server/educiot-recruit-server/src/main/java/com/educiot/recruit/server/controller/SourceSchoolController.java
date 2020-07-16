package com.educiot.recruit.server.controller;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.common.api.APIInfo;
import com.educiot.common.base.CodeMsg;
import com.educiot.common.base.Result;
import com.educiot.recruit.data.entity.SourceSchool;
import com.educiot.recruit.data.entity.SourceSchoolType;
import com.educiot.recruit.data.entity.SysDictionary;
import com.educiot.recruit.data.entity.query.AddSourceSchoolQuery;
import com.educiot.recruit.data.entity.query.ListSourceSchoolPageQuery;
import com.educiot.recruit.data.entity.query.UpdateSourceSchoolQuery;
import com.educiot.recruit.data.entity.vo.ListSourceSchoolVO;
import com.educiot.recruit.data.entity.vo.SourceSchoolDetailVO;
import com.educiot.recruit.data.entity.vo.SourceSchoolVO;
import com.educiot.recruit.data.entity.vo.SysDictVO;
import com.educiot.recruit.data.service.ISourceSchoolService;
import com.educiot.recruit.data.service.ISourceSchoolTypeService;
import com.educiot.recruit.data.service.ISysDictionaryService;
import com.educiot.recruit.server.common.BaseController;
import com.educiot.recruit.server.common.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 生源学校 前端控制器
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@Api(tags = {"生源学校管理"}, value = "SourceSchoolController")
@RestController
@RequestMapping("/sourceSchool")
public class SourceSchoolController extends BaseController {

    private final static Logger logger = LoggerFactory.getLogger(SourceSchoolController.class);

    @Value("${source-school-template.download.url}")
    private String templateUrl;

    @Resource
    private ISourceSchoolService sourceSchoolService;

    @Resource
    private ISourceSchoolTypeService sourceSchoolTypeService;

    @Resource
    private ISysDictionaryService sysDictionaryService;

    @GetMapping("/listSourceSchoolPage")
    @ApiOperation("分页获取生源学校信息列表")
    public Result<IPage<ListSourceSchoolVO>> listSourceSchoolPage(@Valid ListSourceSchoolPageQuery param) {
        param.setSemesterId(getLoginUser().getCurrentSemesterId());
        IPage<ListSourceSchoolVO> page = sourceSchoolService.listSourceSchoolPage(param,
                getLoginUser().getCurrentSchoolId());
        return Result.success(page);
    }

    @GetMapping("/listSourceSchoolType")
    @ApiOperation("获取招生学校下的学校类型（数据字典配置）")
    public Result<List<SysDictVO>> listSourceSchoolType() {
        List<SysDictVO> schoolTypes =
                sourceSchoolService.listSourceSchoolType(getLoginUser().getCurrentSchoolId());
        return Result.success(schoolTypes);
    }

    @PostMapping("/addSourceSchool")
    @ApiOperation("新增生源学校")
    public Result addSourceSchool(@Valid @RequestBody AddSourceSchoolQuery sourceSchoolInfo) {
        LoginUser loginUser = getLoginUser();
        return sourceSchoolService.addSourceSchool(sourceSchoolInfo, loginUser.getCurrentSchoolId(),
                loginUser.getApiInfo().getUserName(), loginUser.getApiInfo().getUserId());
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

    @DeleteMapping("/deleteSourceSchool/{sourceSchoolId}")
    @ApiImplicitParam(name = "sourceSchoolId", value = "生源学校ID", required = true)
    @ApiOperation("删除生源学校")
    public Result deleteSourceSchool(@Valid @PathVariable("sourceSchoolId")
                                     @NotNull(message = "生源学校ID不能为空")
                                     @Min(value = 1L, message = "生源学校ID最小为1")
                                     @Max(value = Long.MAX_VALUE, message = "生源学校ID超出最大限制")
                                             Long sourceSchoolId) {
        return sourceSchoolService.deleteSourceSchool(sourceSchoolId);
    }

    @PostMapping("/exportSchoolTemplate")
    @ApiOperation("下载学校模板")
    public Result exportSchoolTemplate() {
        return Result.success(templateUrl);
    }

    @PostMapping("/excelImport")
    @ApiOperation("批量导入")
    public Result uploadProductCover(@RequestParam("file") MultipartFile file) {
        try {
            if (file.getSize() == 0) {
                return Result.error(CodeMsg.FILE_CANNOT_EMPTY);
            }
            logger.info("正在上传文件 文件大小为：{}", file.getSize());
            ExcelReader reader = ExcelUtil.getReader(file.getInputStream());

            // 设置表头别名
            List<Object> header = reader.readRow(0);
            // 表名匹配
            String[] excelHead = {"生源学校名称", "省份", "市", "区（县）", "学校地址", "学校性质", "生源规模",
                    "学校资质", "上级主管部门", "说明","学校类型"};
            // 表头别名
            String[] excelHeadAlias = {"sourceSchoolName", "province", "city", "district", "address", "relationType",
                    "sourceScale", "qualification", "superiorDept", "comment","reserve"};
            for (int i = 0; i < excelHead.length; i++) {
                if (excelHead[i].equals(header.get(i))) {
                    reader.addHeaderAlias(excelHead[i], excelHeadAlias[i]);
                } else {
                    return Result.error(CodeMsg.FILE_FORMATTER_ERROR);
                }
            }
            // 读取转换为生源学校对象
            List<SourceSchool> sourceSchoolList = reader.readAll(SourceSchool.class);
            List<SourceSchoolType> sourceSchoolTypes=new ArrayList<>();
            LocalDateTime now = LocalDateTime.now();
            // 获取登录信息
            LoginUser loginUser = getLoginUser();
            APIInfo apiInfo = loginUser.getApiInfo();
            sourceSchoolList.forEach(sourceSchool -> {
//                //根据招生学校和数据字典的名称查询数据字典的编号
//                QueryWrapper<SysDictionary> queryWrapper=new QueryWrapper<>();
//                queryWrapper.eq("data_value",sourceSchool.getSchoolCaseComment());
//                queryWrapper.eq("recruit_school_id",getLoginUser().getCurrentSchoolId());
//                SysDictionary one = sysDictionaryService.getOne(queryWrapper);
//                if(one.getDictionaryId()!=null){
//                    sourceSchool.setRelationLevel(one.getDictionaryId().intValue());
//                }
                sourceSchool.setCreateTime(now);
                sourceSchool.setCreatorId(apiInfo.getUserId());
                sourceSchool.setCreatorName(apiInfo.getUserName());
                sourceSchool.setRecruitSchoolId(loginUser.getCurrentSchoolId());
            });
            logger.info("excel解析出的对象为：{}", sourceSchoolList.toString());
            if (sourceSchoolList.isEmpty()) {
                return Result.error(CodeMsg.NO_DATA);
            }
            sourceSchoolService.saveBatch(sourceSchoolList, sourceSchoolList.size());
            //保存生源学校类型关系
            sourceSchoolList.forEach(sourceSchool -> {
                //保存生源学校类型关系
                QueryWrapper<SysDictionary> queryWrapper=new QueryWrapper<>();
                queryWrapper.eq("data_value",sourceSchool.getReserve());
                queryWrapper.eq("recruit_school_id",getLoginUser().getCurrentSchoolId());
                SysDictionary one = sysDictionaryService.getOne(queryWrapper);
                SourceSchoolType sourceSchoolType = new SourceSchoolType();
                sourceSchoolType.setSourceSchoolId(sourceSchool.getSourceSchoolId());
                sourceSchoolType.setTypeId(one.getDictionaryId());
                sourceSchoolTypes.add(sourceSchoolType);
            });
            sourceSchoolTypeService.saveBatch(sourceSchoolTypes);
            return Result.success();
        } catch (Exception e) {
            logger.info(CodeMsg.SOURCE_SCHOOL_BATCH_IMPORT_ERROR.getMessage(), e);
            return Result.error(CodeMsg.SOURCE_SCHOOL_BATCH_IMPORT_ERROR);
        }
    }

    @GetMapping("/listAllSourceSchool")
    @ApiOperation("获取招生学校下的所有的生源学校")
    public Result<List<SourceSchoolVO>> listAllSourceSchool() {
        List<SourceSchoolVO> schoolList =
                sourceSchoolService.listAllSourceSchool(getLoginUser().getCurrentSchoolId());
        return Result.success(schoolList);
    }
}
