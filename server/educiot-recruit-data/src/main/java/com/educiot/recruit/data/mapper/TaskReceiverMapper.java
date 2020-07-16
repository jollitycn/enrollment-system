package com.educiot.recruit.data.mapper;

import com.educiot.recruit.data.entity.TaskReceiver;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.educiot.recruit.data.entity.vo.MessageListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 任务接收人表 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-05-07
 */
public interface TaskReceiverMapper extends BaseMapper<TaskReceiver> {

    /**
     * 获取当前学生的信息列表
     * @param studentPublicRelationId
     * @return List<MessageListVO>
     */
    List<MessageListVO> getMessageList(@Param("studentPublicRelationId") Long studentPublicRelationId);
}
