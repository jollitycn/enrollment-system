package com.educiot.recruit.data.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.educiot.recruit.data.entity.Task;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.educiot.recruit.data.entity.vo.TaskListVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 招生任务表 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
public interface TaskMapper extends BaseMapper<Task> {

    /**
     * 获取任务列表
     * @param page
     * @param userId
     * @param semesterId
     * @param schoolId
     * @param status
     * @return IPage<TaskListVO>
     */
    IPage<TaskListVO> getTaskList(Page<TaskListVO> page,
                                  @Param("userId") Long userId,
                                  @Param("semesterId") Long semesterId,
                                  @Param("schoolId") Long schoolId,
                                  @Param("status") Integer status);
}
