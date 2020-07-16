package com.educiot.recruit.data.service.student.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.data.entity.RecruitWay;
import com.educiot.recruit.data.mapper.RecruitWayMapper;
import com.educiot.recruit.data.service.student.IRecruitWayService;
import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.base.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 * 学生招生渠道表 服务实现类
 * </p>
 *
 * @author Mr.AI
 * @since 2020-04-13
 */
@Service
public class RecruitWayServiceImpl extends ServiceImpl<RecruitWayMapper, RecruitWay> implements IRecruitWayService {

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public Result saveOrUpdateRecruitWayInfo(RecruitWay recruitWay) {
        if(null==recruitWay){
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        if(null == recruitWay.getRecruitWayId()){
            LocalDateTime now = LocalDateTime.now();
            recruitWay.setCreateTime(now);
        }
        boolean b = saveOrUpdate(recruitWay);
        return Result.success(b);
    }

    @Override
    public RecruitWay queryRecruitWayInfo(Long studentPublicRelationId) {
        if(null==studentPublicRelationId){
            return null;
        }
        QueryWrapper<RecruitWay> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("student_public_relation_id",studentPublicRelationId);
        RecruitWay recruitWay = baseMapper.selectOne(queryWrapper);
        return recruitWay;
    }
}
