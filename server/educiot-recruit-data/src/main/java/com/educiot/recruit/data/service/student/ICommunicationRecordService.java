package com.educiot.recruit.data.service.student;

import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.data.entity.CommunicationRecord;
import com.educiot.recruit.data.entity.query.AddCommunicationRecordQuery;
import com.educiot.common.base.Result;

import java.util.List;

/**
 * <p>
 * 沟通记录 服务类
 * </p>
 *
 * @author Mr.AI
 * @since 2020-04-13
 */
public interface ICommunicationRecordService extends IService<CommunicationRecord> {

    /**
     * 添加学生沟通记录信息
     * @param addCommunicationRecordQuery 添加沟通记录入参封装类
     * @return 返回添加是否成功状态
     */
    Result addCommunicationRecord(AddCommunicationRecordQuery addCommunicationRecordQuery);

    /**
     * 获取学生沟通记录信息
     *
     * @param communicationRecordId 沟通记录ID
     * @return 沟通记录信息
     */
    List<CommunicationRecord> queryCommunicationRecord(Long communicationRecordId);

    /**
     * 删除学生沟通记录信息
     *
     * @param communicationRecordId 沟通记录Id
     * @return 返回删除是否成功状态
     */
    Result deleteCommunicationRecord(Long communicationRecordId);
}
