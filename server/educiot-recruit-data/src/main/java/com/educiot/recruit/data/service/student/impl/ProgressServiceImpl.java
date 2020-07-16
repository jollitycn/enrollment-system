package com.educiot.recruit.data.service.student.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.common.constant.Constant;
import com.educiot.recruit.data.entity.Progress;
import com.educiot.recruit.data.entity.RegisterStatusLog;
import com.educiot.recruit.data.entity.StudentPublicRelation;
import com.educiot.recruit.data.entity.query.ChangeStatusQuery;
import com.educiot.recruit.data.entity.vo.ProgressListVO;
import com.educiot.recruit.data.entity.vo.ProgressVO;
import com.educiot.recruit.data.mapper.ProgressMapper;
import com.educiot.recruit.data.service.IRegisterStatusLogService;
import com.educiot.recruit.data.service.student.IProgressService;
import com.educiot.recruit.data.service.student.IStudentPublicRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 学生报名进度表 服务实现类
 * </p>
 *
 * @author Mr.AI
 * @since 2020-04-13
 */
@Service
public class ProgressServiceImpl extends ServiceImpl<ProgressMapper, Progress> implements IProgressService {


    @Resource
    private ProgressMapper progressMapper;

    @Resource
    private IStudentPublicRelationService studentPublicRelationService;

    @Resource
    private IRegisterStatusLogService registerStatusLogService;

    @Override
    public ProgressVO queryProgressInfoByStudentId(Long studentInfoId) {
        if(null == studentInfoId){
            return null;
        }
        return progressMapper.queryProgressInfoByStudentId(studentInfoId);
    }

    /**
     * 获取学生的报名进度
     * @param studentPublicRelationId
     * @return List<ProgressListVO>
     */
    @Override
    public List<ProgressListVO> getProgress(Long studentPublicRelationId) {

        return baseMapper.getProgress(studentPublicRelationId);
    }

    /**
     * 状态更改
     * @param changeStatusQuery
     * @param userId
     * @param userName
     * @return true 修改成功
     */
    @Override
    public boolean changeStatus(ChangeStatusQuery changeStatusQuery, Long userId, String userName) {

        //改变学生的报名状态
        StudentPublicRelation studentPublicRelation = studentPublicRelationService.getById(changeStatusQuery.getStudentPublicRelationId());
        studentPublicRelation.setRegisterStatus(changeStatusQuery.getStatus());
        //如果是变为交定金或交全款状态，填充状态改变时间
        if (changeStatusQuery.getStatus() == 0 || changeStatusQuery.getStatus() == 1) {
            if (studentPublicRelation.getStatusChangeTime() == null) {
                studentPublicRelation.setStatusChangeTime(LocalDate.now());
            }
        }
        boolean status = studentPublicRelationService.updateById(studentPublicRelation);

        //添加状态改变日志
        RegisterStatusLog registerStatusLog = new RegisterStatusLog();
        registerStatusLog.setModifyId(userId);
        registerStatusLog.setModifyName(userName);
        registerStatusLog.setModifyTime(LocalDateTime.now());
        registerStatusLog.setStatusResult(changeStatusQuery.getStatus());
        registerStatusLog.setStudentPublicRelationId(changeStatusQuery.getStudentPublicRelationId());
        registerStatusLogService.save(registerStatusLog);

        //添加进度
        Progress progress = new Progress();
        progress.setCreatorId(userId);
        progress.setCreatorName(userName);
        progress.setCreateTime(LocalDateTime.now());
        progress.setStudentPublicRelationId(changeStatusQuery.getStudentPublicRelationId());

        switch(changeStatusQuery.getStatus()){
            case 0:
                progress.setProgressType(Constant.SYS_ONE);
                progress.setProgressContent("已交订金");
                status = status && save(progress);
                break;
            case 1:
                progress.setProgressType(Constant.SYS_ONE);
                progress.setProgressContent("已交全款");
                status = status && save(progress);
                break;
            case 2:
                progress.setProgressType(Constant.SYS_ONE);
                progress.setProgressContent("已退款");
                status = status && save(progress);
                break;
            case 4:
                progress.setProgressType(Constant.SYS_THREE);
                progress.setProgressContent("已报名");
                status = status && save(progress);
                break;
        }
        return status;
    }

    /**
     * 新增进度
     * @param type 进度类型（0：学生注册，1：宣讲报名，2：宣讲签到，3：到校咨询签到）
     * @param studentPublicRelationId 学生公关ID
     * @param createId 创建人ID
     * @param createName 创建人名称
     * @return true 添加成功
     */
    @Override
    public boolean addProgress(Integer type, Long studentPublicRelationId, Long createId, String createName) {

        //添加进度
        Progress progress = new Progress();
        progress.setCreatorId(createId);
        progress.setCreatorName(createName);
        progress.setCreateTime(LocalDateTime.now());
        progress.setStudentPublicRelationId(studentPublicRelationId);
        progress.setProgressType(Constant.SYS_ZERO);

        switch(type){
            case 0:
                progress.setProgressContent("学生注册");
                break;
            case 1:
                progress.setProgressContent("宣讲报名");
                break;
            case 2:
                progress.setProgressContent("宣讲签到");
                break;
            case 3:
                progress.setProgressContent("到校咨询签到");
                break;
        }
        return save(progress);
    }

    @Override
    public boolean addAttend(Long studentPublicRelationId) {

        //添加进度
        Progress progress = new Progress();
        progress.setCreatorId(studentPublicRelationId);
        progress.setCreateTime(LocalDateTime.now());
        progress.setStudentPublicRelationId(studentPublicRelationId);
        progress.setProgressType(Constant.SYS_TWO);
        progress.setProgressContent("入学通知已确认");
        return save(progress);
    }
}
