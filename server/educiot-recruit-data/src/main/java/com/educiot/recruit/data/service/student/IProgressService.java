package com.educiot.recruit.data.service.student;

import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.data.entity.Progress;
import com.educiot.recruit.data.entity.query.ChangeStatusQuery;
import com.educiot.recruit.data.entity.vo.ProgressListVO;
import com.educiot.recruit.data.entity.vo.ProgressVO;

import java.util.List;

/**
 * <p>
 * 学生报名进度表 服务类
 * </p>
 *
 * @author Mr.AI
 * @since 2020-04-13
 */
public interface IProgressService extends IService<Progress> {

    /**
     * 查询学生报名进度信息
     * @param studentInfoId 学生信息ID
     * @return 学生报名进度信息
     */
    ProgressVO queryProgressInfoByStudentId(Long studentInfoId);

    /**
     * 获取学生的报名进度
     * @param studentPublicRelationId
     * @return List<ProgressListVO>
     */
    List<ProgressListVO> getProgress(Long studentPublicRelationId);

    /**
     * 状态更改
     * @param changeStatusQuery
     * @param userId
     * @param userName
     * @return true 修改成功
     */
    boolean changeStatus(ChangeStatusQuery changeStatusQuery, Long userId, String userName);

    /**
     * 新增进度
     * @param type 进度类型（0：学生注册，1：宣讲报名，2：宣讲签到，3：到校咨询签到）
     * @param studentPublicRelationId 学生公关ID
     * @param createId 创建人ID
     * @param createName 创建人名称
     * @return true 添加成功
     */
    boolean addProgress(Integer type, Long studentPublicRelationId, Long createId, String createName);

    boolean addAttend(Long studentPublicRelationId);
}
