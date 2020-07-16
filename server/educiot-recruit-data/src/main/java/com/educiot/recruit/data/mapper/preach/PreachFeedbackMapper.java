package com.educiot.recruit.data.mapper.preach;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.recruit.data.entity.preach.PreachFeedback;
import com.educiot.recruit.data.entity.query.preach.PreachFeedbackPageQuery;
import com.educiot.recruit.data.entity.query.preach.PreachFeedbackQuery;
import com.educiot.recruit.data.entity.vo.preach.PreachFeedbackCountVO;
import com.educiot.recruit.data.entity.vo.preach.PreachFeedbackVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 宣讲反馈表 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
public interface PreachFeedbackMapper extends BaseMapper<PreachFeedback> {

    List<PreachFeedbackVO> listPage(IPage<PreachFeedbackVO> page, @Param("param")  PreachFeedbackPageQuery param);

    List<PreachFeedbackCountVO> feedBackCount(@Param("param")  PreachFeedbackQuery param);
}
