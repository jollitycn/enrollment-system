package com.educiot.recruit.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.educiot.recruit.data.entity.ConsultationRegister;
import com.educiot.recruit.data.entity.SourceSchool;
import com.educiot.recruit.data.entity.StudentInfo;
import com.educiot.recruit.data.entity.StudentPublicRelation;
import com.educiot.recruit.data.entity.query.AddConsultationRegisterQuery;
import com.educiot.recruit.data.entity.query.AddRegisterQuery;
import com.educiot.recruit.data.entity.query.AttendListQuery;
import com.educiot.recruit.data.entity.query.AttendQuery;
import com.educiot.recruit.data.entity.vo.*;
import com.educiot.recruit.data.mapper.ConsultationRegisterMapper;
import com.educiot.recruit.data.service.IConsultationRegisterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.data.service.ISourceSchoolService;
import com.educiot.common.constant.Constant;
import com.educiot.recruit.data.service.ITaskService;
import com.educiot.recruit.data.service.student.IProgressService;
import com.educiot.recruit.data.service.student.IStudentInfoService;
import com.educiot.recruit.data.service.student.IStudentPublicRelationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 招生咨询登记表 服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Service
public class ConsultationRegisterServiceImpl extends ServiceImpl<ConsultationRegisterMapper, ConsultationRegister> implements IConsultationRegisterService {

    @Resource
    private ISourceSchoolService sourceSchoolService;

    @Resource
    private IStudentPublicRelationService studentPublicRelationService;

    @Resource
    private IStudentInfoService studentInfoService;

    @Resource
    private ITaskService taskService;

    @Resource
    private IProgressService progressService;

    /**
     * 获取咨询人数日历
     * @param consultationId
     * @return List<ConsultationCalenderVO>
     */
    @Override
    public List<ConsultationCalenderVO> getConsultationCalender(Long consultationId) {

        return baseMapper.getConsultationCalender(consultationId);
    }


    /**
     * 获取咨询签到列表
     * @param page
     * @param attendListQuery
     * @return IPage<AttendListVO>
     */
    @Override
    public IPage<AttendListVO> getAttendList(Page<AttendListVO> page, AttendListQuery attendListQuery) {

        //获取基本信息列表
        List<ConsultationRegister> consultationRegisterList = baseMapper.getAttendList(page, attendListQuery.getConsultationId(), attendListQuery.getDate());

        //结果列表
        List<AttendListVO> resultList = new ArrayList<>();

        //填充特定字段
        for (ConsultationRegister consultationRegister : consultationRegisterList) {

            AttendListVO attendListVO = new AttendListVO();
            BeanUtils.copyProperties(consultationRegister, attendListVO);
            //判断是个人还是学校
            if (consultationRegister.getConsultationType() == Constant.SYS_ZERO) {
                //个人
                attendListVO.setAttenderId(consultationRegister.getStudentPublicRelationId());
                attendListVO.setAttenderName(consultationRegister.getStudentName());
            } else {
                //学校
                SourceSchool sourceSchool = sourceSchoolService.getById(consultationRegister.getSourceSchoolId());
                if (sourceSchool != null) {
                    //获取学校名称
                    String schoolName = sourceSchool.getSourceSchoolName();
                    //添加说明
                    String newComment = consultationRegister.getComment() + " 学校：" + schoolName + " 人数：" + consultationRegister.getAmount();
                    attendListVO.setComment(newComment);
                }
                attendListVO.setAttenderName(consultationRegister.getTeacher());
            }
            resultList.add(attendListVO);
        }
        IPage<AttendListVO> iPage = page;
        iPage.setRecords(resultList);
        return iPage;
    }

    /**
     * 签到
     * @param attendQuery
     * @param userId
     * @param userName
     * @return true 签到成功
     */
    @Override
    public boolean attend(AttendQuery attendQuery, Long userId, String userName) {

        boolean status = true;

        List<ConsultationRegister> consultationRegisterList = new ArrayList<>();
        for (Long consultationRegisterId : attendQuery.getConsultationRegisterIdList()) {

            ConsultationRegister consultationRegister = getById(consultationRegisterId);

            //判断是否已经签到
            if (consultationRegister.getIsAttend() == Constant.SYS_ONE) {
                return false;
            }
            consultationRegister.setIsAttend(Constant.SYS_ONE);

            consultationRegisterList.add(consultationRegister);

        }

        status = updateBatchById(consultationRegisterList);
        for (ConsultationRegister consultationRegister : consultationRegisterList) {

            //添加进度
            if (consultationRegister.getStudentPublicRelationId() != null) {
                progressService.addProgress(Constant.SYS_THREE, consultationRegister.getStudentPublicRelationId(), userId, userName);
            }
        }
        return status;
    }

    /**
     * 增加咨询登记
     * @param addConsultationRegisterQuery
     * @param userId
     * @param userName
     * @return true 增加成功
     */
    @Override
    public boolean addConsultationRegister(AddConsultationRegisterQuery addConsultationRegisterQuery, Long userId, String userName) {

        ConsultationRegister consultationRegister = new ConsultationRegister();
        BeanUtils.copyProperties(addConsultationRegisterQuery, consultationRegister);

        //填充创建信息
        consultationRegister.setCreatorId(userId);
        consultationRegister.setCreatorName(userName);
        consultationRegister.setCreateTime(LocalDateTime.now());

        return save(consultationRegister);
    }

    /**
     * 获取咨询注册列表
     * @param page
     * @param attendListQuery
     * @return IPage<ConsultationRegisterVO>
     */
    @Override
    public IPage<ConsultationRegisterVO> getConsultationRegisterList(Page<ConsultationRegisterVO> page, AttendListQuery attendListQuery) {

        return baseMapper.getConsultationRegisterList(page, attendListQuery.getConsultationId(), attendListQuery.getDate(), attendListQuery.getConsultationType());
    }

    /**
     * 获取我的预约日期
     * @param studentPublicRelationId
     * @return List<String>
     */
    @Override
    public List<String> getMyRegister(Long studentPublicRelationId) {

        //获取咨询登记列表
        QueryWrapper<ConsultationRegister> wrapper = new QueryWrapper<>();
        wrapper.eq("student_public_relation_id", studentPublicRelationId);
        List<ConsultationRegister> list = list(wrapper);

        //返回日期
        List<String> resultList = new ArrayList<>();
        for (ConsultationRegister consultationRegister : list) {
            LocalDate consultationDate = consultationRegister.getConsultationDate();
            resultList.add(consultationDate.toString());
        }
        return resultList;
    }

    /**
     * 申请到校咨询
     * @param addRegisterQuery
     * @param studentPublicRelationId
     * @param currentSemesterId
     * @return true 添加成功
     */
    @Override
    public boolean addRegister(AddRegisterQuery addRegisterQuery, Long studentPublicRelationId, Long currentSemesterId) {

        //判断咨询不能重复
        QueryWrapper<ConsultationRegister> wrapper = new QueryWrapper<>();
        wrapper.eq("consultation_date", addRegisterQuery.getConsultationDate());
        wrapper.eq("student_public_relation_id", studentPublicRelationId);
        int count = count(wrapper);
        if (count > 0) {
            return false;
        }

        ConsultationRegister consultationRegister = new ConsultationRegister();
        BeanUtils.copyProperties(addRegisterQuery, consultationRegister);
        consultationRegister.setAttendTime(addRegisterQuery.getAttendTime());
        consultationRegister.setStudentPublicRelationId(studentPublicRelationId);
        consultationRegister.setConsultationType(Constant.SYS_ZERO);
        //获取生源学校信息
        StudentPublicRelation studentPublicRelation = studentPublicRelationService.getById(studentPublicRelationId);
        consultationRegister.setSourceSchoolId(studentPublicRelation.getSourceSchoolId());

        //获取班级信息
        consultationRegister.setClassId(studentPublicRelation.getClassId());

        //获取学生姓名
        StudentInfo studentInfo = studentInfoService.getById(studentPublicRelation.getStudentInfo());
        consultationRegister.setStudentName(studentInfo.getStudentName());

        //创建人信息
        consultationRegister.setCreateTime(LocalDateTime.now());
        consultationRegister.setCreatorId(studentPublicRelation.getCreatorId());
        consultationRegister.setCreatorName(studentPublicRelation.getCreatorName());

        //创建任务
        boolean status = save(consultationRegister);
        status = status && taskService.addTaskFromStudent(studentPublicRelationId, Constant.SYS_ONE, currentSemesterId,
                consultationRegister.getConsultationRegisterId());
        return status;
    }

    /**
     * 删除到校咨询
     * @param date
     * @param studentPublicRelationId
     * @return true 删除成功
     */
    @Override
    public boolean deleteRegister(String date, Long studentPublicRelationId) {

        QueryWrapper<ConsultationRegister> wrapper = new QueryWrapper<>();
        wrapper.eq("consultation_date", date);
        wrapper.eq("student_public_relation_id", studentPublicRelationId);
        return remove(wrapper);
    }

    /**
     * 根据学生查询咨询注册列表
     * @param studentPublicRelationId
     * @return List<StudentConsultationListVO>
     */
    @Override
    public List<StudentConsultationListVO> getConsultationListByStudent(Long studentPublicRelationId) {

        return baseMapper.getConsultationListByStudent(studentPublicRelationId);
    }

    @Override
    public Integer queryRegisterCount(LocalDate consultationDate, Long studentPublicRelationId) {
        QueryWrapper<ConsultationRegister> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("consultation_date",consultationDate);
        queryWrapper.eq("student_public_relation_id",studentPublicRelationId);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public Integer deleteConsultationRegister(LocalDate consultationDate, Long studentPublicRelationId) {
        QueryWrapper<ConsultationRegister> delWrapper=new QueryWrapper<>();
        delWrapper.eq("consultation_date",consultationDate);
        delWrapper.eq("student_public_relation_id",studentPublicRelationId);
        return baseMapper.delete(delWrapper);
    }

    @Override
    public List<ConsultationRegisterCountVO> getRegisterCount(Long consultationId) {

        return baseMapper.getRegisterCount(consultationId);
    }
}
