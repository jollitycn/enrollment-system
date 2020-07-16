package com.educiot.recruit.server.app.controller.group;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.entity.ext.ChargerExt;
import com.educiot.recruit.data.entity.query.ListSourceSchoolPageQuery;
import com.educiot.recruit.data.entity.vo.ListSourceSchoolVO;
import com.educiot.recruit.data.service.IGroupSourceRelationService;
import com.educiot.recruit.server.app.common.BaseController;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 招生组生源学校关联表 前端控制器
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@RestController
@RequestMapping("/group-source-relation")
public class GroupSourceRelationController extends BaseController {
    @Resource
    private IGroupSourceRelationService groupSourceRelationService;
@GetMapping("/listSourceSchoolPage")
    @ApiOperation("分页获取生源学校信息列表")
    public Result<IPage<ListSourceSchoolVO>> listSourceSchoolPage(@Valid ListSourceSchoolPageQuery param) {
        IPage<ListSourceSchoolVO> page = groupSourceRelationService.listSourceSchoolPage(param,
                getLoginUser().getCurrentSchoolId(),getLoginUser().getCurrentSemesterId());
        return Result.success(page);
    }

    @GetMapping("/listSourceSchoolVOResult")
    @ApiOperation("返回责任人所属的学校")
    public Result<List<ListSourceSchoolVO>> listSourceSchoolVOResult(){
        ChargerExt charger = getLoginUser().getCharger();
        Long currentSemesterId = getLoginUser().getCurrentSemesterId();
        Long groupId = null == charger ? null : charger.getGroupId();
        List<ListSourceSchoolVO> listSourceSchoolVOS = groupSourceRelationService.listSourceSchoolVOResult(groupId, currentSemesterId);
        return Result.success(listSourceSchoolVOS);
    }

}
