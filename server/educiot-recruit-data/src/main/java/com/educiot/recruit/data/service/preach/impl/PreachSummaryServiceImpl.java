package com.educiot.recruit.data.service.preach.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.LoginUser;
import com.educiot.recruit.data.entity.ext.PreachSummaryExt;
import com.educiot.recruit.data.entity.preach.PreachPlan;
import com.educiot.recruit.data.entity.preach.PreachSummary;
import com.educiot.recruit.data.entity.query.common.UpdateStatusQuery;
import com.educiot.recruit.data.entity.query.preach.PreachSummaryAddQuery;
import com.educiot.recruit.data.entity.query.preach.PreachSummaryPageQuery;
import com.educiot.recruit.data.entity.query.preach.PreachSummaryQuery;
import com.educiot.recruit.data.entity.vo.preach.PreachSummaryFeedBackCountVO;
import com.educiot.recruit.data.mapper.preach.PreachSummaryMapper;
import com.educiot.recruit.data.service.ITaskService;
import com.educiot.recruit.data.service.preach.IPreachPlanService;
import com.educiot.recruit.data.service.preach.IPreachSummaryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 宣讲总结表  服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Service
public class PreachSummaryServiceImpl extends ServiceImpl<PreachSummaryMapper, PreachSummary> implements IPreachSummaryService {

    @Resource
    private ITaskService taskService;


    @Resource
    private IPreachPlanService preachPlanService;

    @Override
    public IPage<PreachSummaryExt> listPage(PreachSummaryPageQuery query) {
        IPage<PreachSummaryExt> page = new Page<>(query.getPageNum(), query.getPageSize());
        List<PreachSummaryExt> list = baseMapper.listPage(page, query);
        page.setRecords(list);
        return page;
    }

    @Override
    public List<PreachSummaryFeedBackCountVO> feedBackCount(PreachSummaryQuery query) {
        List<PreachSummaryFeedBackCountVO> list = baseMapper.feedBackCount(query);
        return list;
    }

    @Override
    public Result<?> add(LoginUser loginUser, PreachSummaryAddQuery query) {
      return add(loginUser.getApiInfo().getUserId(), loginUser.getApiInfo().getUserName(), query);
    }

    @Override
    public Result<?> add(Long userId, String userName, PreachSummaryAddQuery query) {
        LocalDateTime now = LocalDateTime.now();
        PreachSummary bean = new PreachSummary();
        BeanUtils.copyProperties(query, bean);
        bean.setCreateTime(now);
        bean.setCreatorId(userId);
        bean.setCreatorName(userName);
        baseMapper.insert(bean);
        UpdateStatusQuery statusQuery = new UpdateStatusQuery();
        statusQuery.setId(bean.getPreachPlanId());
        statusQuery.setStatus(PreachPlan.StatusEnum.finish.ordinal());
        preachPlanService.updateStatus(statusQuery);
        taskService.finishTask(bean.getPreachPlanId());
        return Result.success(bean);
    }
}

