package com.educiot.recruit.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.common.base.BasePageQuery;
import com.educiot.recruit.data.LoginUser;
import com.educiot.recruit.data.entity.PublicRelation;
import com.educiot.recruit.data.entity.query.common.UpdateStatusQuery;
import com.educiot.recruit.data.entity.query.publicRelation.PublicRelationAddQuery;
import com.educiot.recruit.data.entity.query.publicRelation.PublicRelationPageQuery;
import com.educiot.recruit.data.entity.query.publicRelation.PublicRelationUpdateQuery;
import com.educiot.recruit.data.entity.vo.publicRelation.PublicRelationGroupCountVO;
import com.educiot.recruit.data.entity.vo.publicRelation.PublicRelationVO;

/**
 * <p>
 * 学校公关表 服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface IPublicRelationService extends IService<PublicRelation> {


    PublicRelation add(LoginUser loginUser, PublicRelationAddQuery query);

    PublicRelation add(Long userId, String userName, PublicRelationAddQuery query);

    PublicRelationVO detail(Long id);

    IPage<PublicRelationVO> listPage(long userId, PublicRelationPageQuery query);
//    boolean updateStatus(UpdateStatusQuery query);

    int update(LoginUser loginUser, PublicRelationUpdateQuery query);

    IPage<PublicRelationGroupCountVO> listPageGroupCountByUserId(Long groupId, BasePageQuery query,Long semesterId,Long chargerId);

    boolean updateStatus(UpdateStatusQuery query);

    int update(Long userId, String userName, PublicRelationUpdateQuery query);
}
