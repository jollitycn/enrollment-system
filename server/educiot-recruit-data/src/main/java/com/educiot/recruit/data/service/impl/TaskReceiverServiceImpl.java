package com.educiot.recruit.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.educiot.common.constant.Constant;
import com.educiot.recruit.data.entity.*;
import com.educiot.recruit.data.entity.vo.AdmissionVO;
import com.educiot.recruit.data.entity.vo.MessageListVO;
import com.educiot.recruit.data.mapper.TaskReceiverMapper;
import com.educiot.recruit.data.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.data.service.student.IAimMajorService;
import com.educiot.recruit.data.service.student.IProgressService;
import com.educiot.recruit.data.service.student.IStudentInfoService;
import com.educiot.recruit.data.service.student.IStudentPublicRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 任务接收人表 服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-05-07
 */
@Service
public class TaskReceiverServiceImpl extends ServiceImpl<TaskReceiverMapper, TaskReceiver> implements ITaskReceiverService {

    @Resource
    private INewsService newsService;

    @Resource
    private IStudentPublicRelationService studentPublicRelationService;

    @Resource
    private ITaskService taskService;

    @Resource
    private ISysDictionaryService sysDictionaryService;

    @Resource
    private IStudentInfoService studentInfoService;

    @Resource
    private IProgressService progressService;

    @Resource
    private IAimMajorService aimMajorService;

    @Resource
    private IMajorService majorService;

    @Resource
    private ICollegeService collegeService;

    /**
     * 获取消息列表
     * @param recruitSchoolId
     * @param studentPublicRelationId
     * @param planId
     * @param loginType
     * @return List<MessageListVO>
     */
    @Override
    public List<MessageListVO> getMessageList(Long recruitSchoolId, Long studentPublicRelationId, Long planId, Integer loginType) {

        //获取当前学生的信息列表
        List<MessageListVO> studentMessageList = baseMapper.getMessageList(studentPublicRelationId);

        //获取学校新闻列表
        List<MessageListVO> newsList = newsService.getMessageList(recruitSchoolId, planId, loginType);

        studentMessageList.addAll(newsList);

        return studentMessageList;
    }

    /**
     * 发送入学通知书
     * @param currentSchoolId
     * @param currentSemesterId
     * @param userId
     * @param userName
     * @return true 发送成功
     */
    @Override
    public boolean sendAdmission(Long currentSchoolId, Long currentSemesterId, Long userId, String userName) {

        //获取缴费学生列表
        QueryWrapper<StudentPublicRelation> studentPublicRelationQueryWrapper = new QueryWrapper<>();
        studentPublicRelationQueryWrapper.eq("recruit_school_id", currentSchoolId);
        studentPublicRelationQueryWrapper.in("register_status", Constant.SYS_ZERO, Constant.SYS_ONE) ;
        studentPublicRelationQueryWrapper.eq("status", Constant.SYS_ONE);
        studentPublicRelationQueryWrapper.eq("semester_id", currentSemesterId);
        List<StudentPublicRelation> studentList = studentPublicRelationService.list(studentPublicRelationQueryWrapper);

        if (studentList.size() != 0) {

            //初始化任务和进度
            List<Task> taskList = new ArrayList<>();
            List<Progress> progressList = new ArrayList<>();

            for (StudentPublicRelation studentPublicRelation : studentList) {
                //创建任务
                Task task = new Task();
                task.setTitle("入学通知书，请确认");
                task.setContent("admission");
                task.setCreateTime(LocalDateTime.now());
                task.setCreatorName(userName);
                task.setCreatorId(userId);
                task.setTaskType(Constant.SYS_FIVE);
                task.setSemesterId(currentSemesterId);
                task.setRelatedId(studentPublicRelation.getStudentPublicRelationId());
                task.setType(Constant.SYS_ONE);
                taskList.add(task);

                //创建进度
                Progress progress = new Progress();
                progress.setCreatorId(userId);
                progress.setCreatorName(userName);
                progress.setCreateTime(LocalDateTime.now());
                progress.setStudentPublicRelationId(studentPublicRelation.getStudentPublicRelationId());
                progress.setProgressType(Constant.SYS_TWO);
                progress.setProgressContent("入学通知已发送");
                progressList.add(progress);

            }
            boolean status = taskService.saveBatch(taskList) && progressService.saveBatch(progressList);

            //创建任务接收
            List<TaskReceiver> taskReceiverList = new ArrayList<>();
            for (Task task : taskList) {
                TaskReceiver taskReceiver = new TaskReceiver();
                taskReceiver.setTaskId(task.getTaskId());
                taskReceiver.setReceiverId(task.getRelatedId());
                taskReceiver.setSemesterId(currentSemesterId);
                taskReceiver.setCreateTime(LocalDateTime.now());
                taskReceiverList.add(taskReceiver);
            }
            return status && saveBatch(taskReceiverList);

        }


        return true;

    }

    @Override
    public AdmissionVO getAdmissionInfo(Long recruitSchoolId, Long taskReceiverId) {

        //获取入学通知的信息
        TaskReceiver taskReceiver = getById(taskReceiverId);

        if (taskReceiver != null) {
            Task task = taskService.getById(taskReceiver.getTaskId());
            if (task != null) {
                //获取学生信息
                StudentPublicRelation studentPublicRelation = studentPublicRelationService.getById(task.getRelatedId());
                if (studentPublicRelation != null) {
                    StudentInfo studentInfo = studentInfoService.getById(studentPublicRelation.getStudentInfo());
                    //根据数据字典获取入学通知内容
                    QueryWrapper<SysDictionary> wrapper = new QueryWrapper<>();
                    wrapper.eq("data_code", task.getContent());
                    wrapper.eq("recruit_school_id", recruitSchoolId);
                    SysDictionary sysDictionary = sysDictionaryService.getOne(wrapper);

                    //获取选择专业下信息
                    QueryWrapper<AimMajor> majorQueryWrapper = new QueryWrapper<>();
                    majorQueryWrapper.eq("student_public_relation_id", studentPublicRelation.getStudentPublicRelationId());
                    majorQueryWrapper.orderByAsc("level");
                    List<AimMajor> majorList = aimMajorService.list(majorQueryWrapper);

                    if (majorList.size() != 0) {
                        //获取最高志愿的专业
                        AimMajor aimMajor = majorList.get(0);
                        Major major = majorService.getById(aimMajor.getMajorId());

                        //获取院系
                        College college = collegeService.getById(major.getCollegeId());
                        //获取院系专业信息
                        AdmissionVO admissionVO = new AdmissionVO();
                        String content = MessageFormat.format(sysDictionary.getDataValue(), studentInfo.getStudentName(),
                                college.getCollegeName(), major.getMajorName());
                        admissionVO.setContent(content);
                        admissionVO.setConfirmStatus(taskReceiver.getReadStatus());
                        return admissionVO;
                    }

                }
            }
        }
        return null;
    }
}
