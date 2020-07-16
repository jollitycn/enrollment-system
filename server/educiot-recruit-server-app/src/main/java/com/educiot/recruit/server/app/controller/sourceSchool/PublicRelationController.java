package com.educiot.recruit.server.app.controller.sourceSchool;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.common.base.BasePageQuery;
import com.educiot.common.base.Result;
import com.educiot.recruit.data.entity.PublicRelation;
import com.educiot.recruit.data.entity.ext.GroupSourceRelationExt;
import com.educiot.recruit.data.entity.query.common.UpdateStatusQuery;
import com.educiot.recruit.data.entity.query.publicRelation.PublicRelationAddQuery;
import com.educiot.recruit.data.entity.query.publicRelation.PublicRelationPageQuery;
import com.educiot.recruit.data.entity.query.publicRelation.PublicRelationUpdateQuery;
import com.educiot.recruit.data.entity.vo.publicRelation.PublicRelationGroupCountVO;
import com.educiot.recruit.data.entity.vo.publicRelation.PublicRelationVO;
import com.educiot.recruit.data.service.IGroupSourceRelationService;
import com.educiot.recruit.data.service.IPublicRelationService;
import com.educiot.recruit.server.app.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 学校公关表 前端控制器PublicRelation
 *
 * @author Hongsifan
 * @since 2020-04-10
 */
@Api(tags = {PublicRelationController.CLASS_NAME_ZN}, value = PublicRelationController.CLASS_NAME_EN)
@RestController
@RequestMapping(PublicRelationController.REQUEST_MAPPING)
@Log4j2
public class PublicRelationController extends BaseController {
    public static final String CLASS_NAME_ZN = "学校公关";
    public static final String REQUEST_MAPPING = "public-relation";
    public static final String CLASS_NAME_EN = "PublicRelationController";
    @Resource
    private IPublicRelationService publicRelationService;

    @Resource
    private IGroupSourceRelationService groupSourceRelationService;

    /***
     *
     * @param query
     * @return
     */
    @PostMapping(METHOD_PAGE)
    @ApiOperation(value = METHOD_PAGE_NAME, response = PublicRelationVO.class)
    public Result<?> page(@RequestBody PublicRelationPageQuery query) {
        log.info(METHOD_PAGE_NAME);
        query.setGroupId(getLoginUser().getCharger().getGroupId());
        query.setSemesterId(getLoginUser().getCurrentSemesterId());
        IPage<PublicRelationVO> result = publicRelationService.listPage(getLoginUser().getApiInfo().getUserId(), query);
        return Result.success(result);
    }

    /**
     * @return
     */
    @PostMapping(METHOD_PAGE_GROUP_COUNT)
    @ApiOperation(value = METHOD_PAGE_GROUP_COUNT_NAME, response = PublicRelationGroupCountVO.class)
    public Result<?> pageGroupCount(@RequestBody BasePageQuery query) {
        log.info(METHOD_PAGE_GROUP_COUNT_NAME);
        IPage<PublicRelationGroupCountVO> result = publicRelationService.listPageGroupCountByUserId(getLoginUser().getCharger().getGroupId(), query, getLoginUser().getCurrentSemesterId(), getLoginUser().getCharger().getChargerId());
        return Result.success(result);
    }

    /**
     * @param query
     * @return
     */
    @PutMapping(METHOD_ADD)
    @ApiOperation(value = METHOD_ADD, response = Long.class)
    public Result<?> add(@RequestBody PublicRelationAddQuery query) {
        log.info(METHOD_ADD_NAME);
        query.setGroupId(getLoginUser().getCharger().getGroupId());
        GroupSourceRelationExt ext = groupSourceRelationService.getByGroupIdAndSourceSchoolId(getLoginUser().getCharger().getGroupId(), query.getSourceSchoolId());
        if (ext != null) {
            query.setGroupSourceRelationId(ext.getGroupSourceRelationId());
        }
        PublicRelation result = publicRelationService.add(getLoginUser().getApiInfo().getUserId(), getLoginUser().getApiInfo().getUserName(), query);
        return Result.success(result);
    }

    /**
     * @param query
     * @return
     */
    @PostMapping(METHOD_UPDATE)
    @ApiOperation(METHOD_UPDATE_NAME)
    public Result<?> update(@RequestBody PublicRelationUpdateQuery query) {
        log.info(METHOD_UPDATE_NAME);
        int result = publicRelationService.update(getLoginUser().getApiInfo().getUserId(), getLoginUser().getApiInfo().getUserName(), query);
        return Result.success(result);
    }


    /**
     * @param id
     * @return
     */
    @GetMapping(METHOD_DETAIL)
    @ApiOperation(value = METHOD_DETAIL_NAME, response = PublicRelationVO.class)
    public Result<?> detail(@PathVariable Long id) {
        log.info(METHOD_DETAIL_NAME);
        PublicRelationVO obj = publicRelationService.detail(id);
        return Result.success(obj);
    }

    /**
     * @param id
     * @return
     */
    @PutMapping(METHOD_DELETE)
    @ApiOperation(value = METHOD_DELETE_NAME)
    public Result<?> delete(@PathVariable Long id) {
        log.info(METHOD_DELETE_NAME);
//        UpdateStatusQuery query = new UpdateStatusQuery();
//        query.setId(id);
//        query.setStatus(2);
//        return updateStatus(query);
        boolean result = publicRelationService.removeById(id);
        return Result.success(result);
    }

    /**
     * @param query
     * @return
     */
    @PostMapping(METHOD_UPDATE_STATUS)
    @ApiOperation(value = METHOD_UPDATE_STATUS_NAME)
    private Result<?> updateStatus(@RequestBody UpdateStatusQuery query) {
        log.info(METHOD_UPDATE_STATUS_NAME);
        boolean result = publicRelationService.updateStatus(query);
        return Result.success(result);
    }
}
