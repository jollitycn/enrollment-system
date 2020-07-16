package com.educiot.recruit.data.service.preach;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.data.LoginUser;
import com.educiot.recruit.data.entity.ext.PreachRegisterExt;
import com.educiot.recruit.data.entity.preach.PreachRegister;
import com.educiot.recruit.data.entity.query.common.UpdateStatusQuery;
import com.educiot.recruit.data.entity.query.preach.register.PreachRegisterAddQuery;
import com.educiot.recruit.data.entity.query.preach.register.PreachRegisterPageQuery;
import com.educiot.recruit.data.entity.query.preach.register.PreachRegisterUpdateQuery;
import com.educiot.recruit.data.entity.vo.preach.PreachRegisterPageVO;

import java.util.List;


/**
 * <p>
 * 宣讲计划报名表 服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface IPreachRegisterService extends IService<PreachRegister> {

    PreachRegister add(LoginUser loginUser, PreachRegisterAddQuery query);

    PreachRegister add(Long userId, String userName, PreachRegisterAddQuery query);

    PreachRegisterExt detail(Long id);

    IPage<PreachRegisterPageVO> listPage(long userId, PreachRegisterPageQuery query);

    IPage<PreachRegisterPageVO> queryPreachList(PreachRegisterPageQuery query);
//    boolean updateStatus(UpdateStatusQuery query);

    int update(LoginUser loginUser, PreachRegisterUpdateQuery query);
//
//    IPage<PreachRegisterGroupCountVO> listPageGroupCountByUserId(Long userId);
//
//    IPage<PreachRegisterGroupCountVO> listPageGroupCount(Long userId);

    boolean updateStatus(UpdateStatusQuery query);

    int update(Long userId, String userName, PreachRegisterUpdateQuery query);

    List<PreachRegister> checkSignIn(Long preachPlanId, Long studentPublicRelationId);

    List<PreachRegister> checkSignUp(Long preachPlanId, Long studentPublicRelationId);
}
