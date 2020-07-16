package com.educiot.recruit.data.mapper.preach;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.recruit.data.entity.preach.PreachRegister;
import com.educiot.recruit.data.entity.query.preach.register.PreachRegisterPageQuery;
import com.educiot.recruit.data.entity.vo.preach.PreachRegisterPageVO;

import java.util.List;

/**
 * <p>
 * 宣讲计划报名表 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
public interface PreachRegisterMapper extends BaseMapper<PreachRegister> {

    IPage<PreachRegisterPageVO> listPage(IPage<PreachRegisterPageVO> page, long userId, PreachRegisterPageQuery query);

    List<PreachRegister> checkSignIn(Long preachPlanId, Long studentPublicRelationId);

    List<PreachRegister> checkSignUp(Long preachPlanId, Long studentPublicRelationId);

    IPage<PreachRegisterPageVO> queryPreachList(IPage<PreachRegisterPageVO> page, PreachRegisterPageQuery query);
}
