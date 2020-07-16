package com.educiot.recruit.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.common.base.BasePageQuery;
import com.educiot.recruit.data.LoginUser;
import com.educiot.recruit.data.entity.PublicRelation;
import com.educiot.recruit.data.entity.query.common.UpdateStatusQuery;
import com.educiot.recruit.data.entity.query.publicRelation.PublicRelationAddQuery;
import com.educiot.recruit.data.entity.query.publicRelation.PublicRelationPageQuery;
import com.educiot.recruit.data.entity.query.publicRelation.PublicRelationUpdateQuery;
import com.educiot.recruit.data.entity.vo.publicRelation.PublicRelationGroupCountVO;
import com.educiot.recruit.data.entity.vo.publicRelation.PublicRelationVO;
import com.educiot.recruit.data.mapper.PublicRelationMapper;
import com.educiot.recruit.data.service.IPublicRelationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 * 学校公关表 服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Service
public class PublicRelationServiceImpl extends ServiceImpl<PublicRelationMapper, PublicRelation> implements IPublicRelationService {

    @Override
    public IPage<PublicRelationVO> listPage(long userId, PublicRelationPageQuery query) {
        IPage<PublicRelationVO> page = new Page<>(query.getPageNum(), query.getPageSize());
        IPage<PublicRelationVO> list = baseMapper.listPage(page, userId, query);
        return list;
    }

    @Override
    public IPage<PublicRelationGroupCountVO> listPageGroupCountByUserId(Long groupId, BasePageQuery query,Long semesterId,Long chargerId) {
        IPage<PublicRelationGroupCountVO> page = new Page<>(query.getPageNum(), query.getPageSize());
        IPage<PublicRelationGroupCountVO> list = baseMapper.listPageGroupCountByUserId(page, groupId,semesterId);
        return list;
    }

    @Override
    public boolean updateStatus(UpdateStatusQuery query) {
        return false;
    }

    @Override
    public int update(Long userId, String userName, PublicRelationUpdateQuery query) {
        // 判断分组是否存在
        QueryWrapper<PublicRelation> wrapper = new QueryWrapper<>();
        wrapper.eq(PublicRelation.Columns.PUBLIC_RELATION, query.getPublicRelation());
        //wrapper.eq(PublicRelation.Columns.STATUS, Constant.SYS_ONE);
        PublicRelation selectGroup = baseMapper.selectOne(wrapper);
//        if (null == selectGroup) {
//            return Result.error(CodeMsg.ID_NOT_EXIST);
//        }
        PublicRelation bean = new PublicRelation();
        BeanUtils.copyProperties(query, bean);
        bean.setCreatorId(userId);
        bean.setCreatorName(userName);
        int result = baseMapper.updateById(bean);
//        removeAndAddRelations(query);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PublicRelation add(LoginUser loginUser, PublicRelationAddQuery query) {
        LocalDateTime now = LocalDateTime.now();
        PublicRelation bean = new PublicRelation();
        BeanUtils.copyProperties(query, bean);
        bean.setCreateTime(now);
        bean.setCreatorId(loginUser.getApiInfo().getUserId());
        bean.setCreatorName(loginUser.getApiInfo().getUserName());
        //bean.setStatus(1);
        // bean.setSemesterId(loginUser.getCurrentSemesterId());
        int result = baseMapper.insert(bean);
        return bean;
    }

    @Override
    public int update(LoginUser loginUser, PublicRelationUpdateQuery query) {
        return update(loginUser.getApiInfo().getUserId(), loginUser.getApiInfo().getUserName(), query);
    }

    @Override
    public PublicRelation add(Long userId, String userName, PublicRelationAddQuery query) {
        LocalDateTime now = LocalDateTime.now();
        PublicRelation bean = new PublicRelation();
        BeanUtils.copyProperties(query, bean);
        bean.setCreateTime(now);
        bean.setCreatorId(userId);
        bean.setCreatorName(userName);
        baseMapper.insert(bean);
        PublicRelationUpdateQuery updateQuery = new PublicRelationUpdateQuery();
        BeanUtils.copyProperties(query, updateQuery);
//        removeAndAddRelations(updateQuery);
        return bean;
    }

    @Override
    public PublicRelationVO detail(Long id) {
        PublicRelationVO vo = new PublicRelationVO();
        PublicRelation bean = baseMapper.selectById(id);
        BeanUtils.copyProperties(bean, vo);
        return vo;
    }
}
