package com.educiot.recruit.data.service;

import com.educiot.recruit.data.entity.TaskReceiver;
import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.data.entity.vo.AdmissionVO;
import com.educiot.recruit.data.entity.vo.MessageListVO;

import java.util.List;

/**
 * <p>
 * 任务接收人表 服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-05-07
 */
public interface ITaskReceiverService extends IService<TaskReceiver> {

    /**
     * 获取消息列表
     * @param recruitSchoolId
     * @param studentPublicRelationId
     * @param planId
     * @param loginType
     * @return List<MessageListVO>
     */
    List<MessageListVO> getMessageList(Long recruitSchoolId, Long studentPublicRelationId, Long planId, Integer loginType);

    /**
     * 发送入学通知书
     * @param currentSchoolId
     * @param currentSemesterId
     * @param userId
     * @param userName
     * @return true 发送成功
     */
    boolean sendAdmission(Long currentSchoolId, Long currentSemesterId, Long userId, String userName);

    AdmissionVO getAdmissionInfo(Long recruitSchoolId, Long taskReceiverId);
}
