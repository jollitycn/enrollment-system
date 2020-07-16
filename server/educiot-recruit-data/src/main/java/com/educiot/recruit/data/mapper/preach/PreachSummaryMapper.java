package com.educiot.recruit.data.mapper.preach;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.recruit.data.entity.ext.PreachSummaryExt;
import com.educiot.recruit.data.entity.preach.PreachSummary;
import com.educiot.recruit.data.entity.query.preach.PreachSummaryPageQuery;
import com.educiot.recruit.data.entity.query.preach.PreachSummaryQuery;
import com.educiot.recruit.data.entity.vo.preach.PreachSummaryFeedBackCountVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 宣讲总结表  Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
public interface PreachSummaryMapper extends BaseMapper<PreachSummary> {

    List<PreachSummaryExt> listPage(IPage<PreachSummaryExt> page, @Param("param") PreachSummaryPageQuery param);

    List<PreachSummaryFeedBackCountVO> feedBackCount(@Param("param") PreachSummaryQuery param);

}
