package com.educiot.recruit.data.service.student.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.data.entity.CommunicationRecord;
import com.educiot.recruit.data.entity.query.AddCommunicationRecordQuery;
import com.educiot.recruit.data.mapper.CommunicationRecordMapper;
import com.educiot.recruit.data.service.student.ICommunicationRecordService;
import com.educiot.common.base.CodeMsg;
import com.educiot.common.base.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 沟通记录 服务实现类
 * </p>
 *
 * @author Mr.AI
 * @since 2020-04-13
 */
@Service
public class CommunicationRecordServiceImpl extends ServiceImpl<CommunicationRecordMapper, CommunicationRecord> implements ICommunicationRecordService {

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public Result addCommunicationRecord(AddCommunicationRecordQuery addCommunicationRecordQuery) {
        if (null == addCommunicationRecordQuery) {
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        CommunicationRecord communicationRecord=new CommunicationRecord();
        if(null == addCommunicationRecordQuery.getCommunicationRecordId()){
            LocalDateTime now = LocalDateTime.now();
            communicationRecord.setCreateTime(now);
        }
        BeanUtils.copyProperties(addCommunicationRecordQuery,communicationRecord);
        boolean b = saveOrUpdate(communicationRecord);
        return Result.success(b);
    }

    @Override
    public List<CommunicationRecord> queryCommunicationRecord(Long studentPublicRelationId) {
        if(null == studentPublicRelationId){
            return null;
        }
        QueryWrapper<CommunicationRecord> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("student_public_relation_id",studentPublicRelationId);
        List<CommunicationRecord> communicationRecords = baseMapper.selectList(queryWrapper);
        return communicationRecords;
    }

    @Override
    public Result deleteCommunicationRecord(Long communicationRecordId) {
        if(null==communicationRecordId){
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        boolean b = removeById(communicationRecordId);
        if(b){
            return Result.success();
        }
        return Result.error(CodeMsg.DATA_DELETE_ERROR);
    }
}
