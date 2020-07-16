package com.educiot.recruit.data.service.preach.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.common.constant.Constant;
import com.educiot.recruit.data.LoginUser;
import com.educiot.recruit.data.entity.ext.PreachRegisterExt;
import com.educiot.recruit.data.entity.preach.PreachRegister;
import com.educiot.recruit.data.entity.query.common.UpdateStatusQuery;
import com.educiot.recruit.data.entity.query.preach.register.PreachRegisterAddQuery;
import com.educiot.recruit.data.entity.query.preach.register.PreachRegisterPageQuery;
import com.educiot.recruit.data.entity.query.preach.register.PreachRegisterUpdateQuery;
import com.educiot.recruit.data.entity.vo.preach.PreachRegisterPageVO;
import com.educiot.recruit.data.mapper.preach.PreachRegisterMapper;
import com.educiot.recruit.data.service.preach.IPreachRegisterService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 宣讲计划报名表 服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Service
public class PreachRegisterServiceImpl extends ServiceImpl<PreachRegisterMapper, PreachRegister> implements IPreachRegisterService {

    @Override
    public IPage<PreachRegisterPageVO> listPage(long userId, PreachRegisterPageQuery query) {
        IPage<PreachRegisterPageVO> page = new Page<>(query.getPageNum(), query.getPageSize());
        page = baseMapper.listPage(page, userId, query);
        return page;
    }

    @Override
    public IPage<PreachRegisterPageVO> queryPreachList(PreachRegisterPageQuery query) {
        IPage<PreachRegisterPageVO> page = new Page<>(query.getPageNum(), query.getPageSize());
        page = baseMapper.queryPreachList(page, query);
        return page;
    }

    //    @Override
//    public IPage<PreachRegisterGroupCountVO> listPageGroupCountByUserId(Long userId) {
//        return baseMapper.listPageGroupCountByUserId(userId);
//    }
//
//    @Override
//    public IPage<PreachRegisterGroupCountVO> listPageGroupCount(Long userId) {
//        return null;
//    }

    @Override
    public boolean updateStatus(UpdateStatusQuery query) {
        return false;
    }

    @Override
    public int update(Long userId, String userName, PreachRegisterUpdateQuery query) {
        // 判断分组是否存在
        QueryWrapper<PreachRegister> wrapper = new QueryWrapper<>();
        wrapper.eq(PreachRegister.Columns.PREACH_REGISTER_ID, query.getPreachRegisterId());
        wrapper.eq(PreachRegister.Columns.STATUS, Constant.SYS_ONE);
        PreachRegister selectGroup = baseMapper.selectOne(wrapper);
//        if (null == selectGroup) {
//            return Result.error(CodeMsg.ID_NOT_EXIST);
//        }
        PreachRegister bean = new PreachRegister();
        BeanUtils.copyProperties(query, bean);
//        bean.setCreatorId(userId);
//        bean.setCreatorName(userName);
        int result = baseMapper.updateById(bean);
//        removeAndAddRelations(query);
        return result;
    }

    @Override
    public List<PreachRegister> checkSignIn(Long preachPlanId, Long studentPublicRelationId) {
        return baseMapper.checkSignIn(preachPlanId,studentPublicRelationId);
    }

    @Override
    public   List<PreachRegister> checkSignUp(Long preachPlanId, Long studentPublicRelationId) {

        return baseMapper.checkSignUp(preachPlanId,studentPublicRelationId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PreachRegister add(LoginUser loginUser, PreachRegisterAddQuery query) {
        LocalDateTime now = LocalDateTime.now();
        PreachRegister bean = new PreachRegister();
        BeanUtils.copyProperties(query, bean);
//        bean.setCreateTime(now);
//        bean.setCreatorId(loginUser.getApiInfo().getUserId());
//        bean.setCreatorName(loginUser.getApiInfo().getUserName());
        //bean.setStatus(1);
        // bean.setSemesterId(loginUser.getCurrentSemesterId());
        int result = baseMapper.insert(bean);
        return bean;
    }

    @Override
    public int update(LoginUser loginUser, PreachRegisterUpdateQuery query) {
        return update(loginUser.getApiInfo().getUserId(), loginUser.getApiInfo().getUserName(), query);
    }

    @Override
    public PreachRegister add(Long userId, String userName, PreachRegisterAddQuery query) {
        LocalDateTime now = LocalDateTime.now();
        PreachRegister bean = new PreachRegister();
        BeanUtils.copyProperties(query, bean);
//        bean.setCreateTime(now);
//        bean.setCreatorId(userId);
//        bean.setCreatorName(userName);
        baseMapper.insert(bean);
        PreachRegisterUpdateQuery updateQuery = new PreachRegisterUpdateQuery();
        BeanUtils.copyProperties(query, updateQuery);
//        removeAndAddRelations(updateQuery);
        return bean;
    }

    @Override
    public PreachRegisterExt detail(Long id) {
        PreachRegisterExt vo = new PreachRegisterExt();
        PreachRegister bean = baseMapper.selectById(id);
        BeanUtils.copyProperties(bean, vo);
        return vo;
    }
}
