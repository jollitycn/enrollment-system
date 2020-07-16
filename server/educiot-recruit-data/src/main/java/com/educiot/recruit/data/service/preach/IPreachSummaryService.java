package com.educiot.recruit.data.service.preach;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.LoginUser;
import com.educiot.recruit.data.entity.ext.PreachSummaryExt;
import com.educiot.recruit.data.entity.preach.PreachSummary;
import com.educiot.recruit.data.entity.query.preach.PreachSummaryAddQuery;
import com.educiot.recruit.data.entity.query.preach.PreachSummaryPageQuery;
import com.educiot.recruit.data.entity.query.preach.PreachSummaryQuery;
import com.educiot.recruit.data.entity.vo.preach.PreachSummaryFeedBackCountVO;

import java.util.List;
/**
 * <p>
 * 宣讲总结表  服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface IPreachSummaryService extends IService<PreachSummary> {

    IPage<PreachSummaryExt> listPage(PreachSummaryPageQuery query);

    List<PreachSummaryFeedBackCountVO> feedBackCount(PreachSummaryQuery query);

    Result<?> add(LoginUser loginUser, PreachSummaryAddQuery query);

    Result<?> add(Long userId, String userName, PreachSummaryAddQuery query);
}
