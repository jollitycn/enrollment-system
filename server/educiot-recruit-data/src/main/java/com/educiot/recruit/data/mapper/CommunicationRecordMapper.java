package com.educiot.recruit.data.mapper;

import com.educiot.recruit.data.entity.CommunicationRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.educiot.common.base.Result;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 沟通记录 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
public interface CommunicationRecordMapper extends BaseMapper<CommunicationRecord> {

    /**
     * 按公关Id获取沟通记录信息
     * @param studentPublicRelationId
     * @return
     */
    Result queryCommunicationRecord(@Param("studentPublicRelationId") String studentPublicRelationId);
}
