package com.educiot.recruit.data.service.student.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.common.base.CodeMsg;
import com.educiot.common.base.Result;
import com.educiot.common.constant.Constant;
import com.educiot.recruit.data.entity.CommunicationRecord;
import com.educiot.recruit.data.entity.StudentPublicRelation;
import com.educiot.recruit.data.entity.query.UpdateStudentPublicRelationStatusQuery;
import com.educiot.recruit.data.entity.vo.StudentPublicRelationRecordVO;
import com.educiot.recruit.data.entity.vo.StudentPublicRelationVO;
import com.educiot.recruit.data.mapper.StudentPublicRelationMapper;
import com.educiot.recruit.data.service.student.ICommunicationRecordService;
import com.educiot.recruit.data.service.student.IStudentPublicRelationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 意向生登录表 服务实现类
 * </p>
 *
 * @author Mr.AI
 * @since 2020-04-15
 */
@Service
public class StudentPublicRelationServiceImpl extends ServiceImpl<StudentPublicRelationMapper, StudentPublicRelation> implements IStudentPublicRelationService {

    @Resource
    private ICommunicationRecordService communicationRecordService;

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public Result saveOrUpdateStudentPublicRelation(StudentPublicRelation studentPublicRelation) {
        if (null == studentPublicRelation) {
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        if(null == studentPublicRelation.getStudentPublicRelationId()){
            LocalDateTime now = LocalDateTime.now();
            studentPublicRelation.setCreateTime(now);
            studentPublicRelation.setStatus(Constant.SYS_ONE);
            //根据招生学校ID，查询登录号，最大值加1
            //添加学生，加学生登记号
//            Integer integer = this.queryStudentNo(studentPublicRelation.getRecruitSchoolId(), studentPublicRelation.getSemesterId());
//            if(null == integer){
//                studentPublicRelation.setStudentNo(1);
//            }else {
//                studentPublicRelation.setStudentNo(integer);
//            }
        }
        boolean b = saveOrUpdate(studentPublicRelation);
        return Result.success(b);
    }

    @Override
    public Integer queryStudentNo(Long recruitSchoolId,Long semesterId) {
        return baseMapper.queryStudentNo(recruitSchoolId,semesterId);
    }

    @Override
    public StudentPublicRelationRecordVO queryStudentPublicRelation(Long studentInfoId,Long recruitSchoolId) {
        if (null == studentInfoId) {
            return null;
        }
        StudentPublicRelationRecordVO studentPublicRelationRecordVO=baseMapper.queryStudentPublicRelation(studentInfoId,recruitSchoolId);
        //获取沟通记录
        List<CommunicationRecord> result = communicationRecordService.queryCommunicationRecord(studentPublicRelationRecordVO.getStudentPublicRelationId());
        studentPublicRelationRecordVO.setCommunicationRecords(result);
        return studentPublicRelationRecordVO;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public Result updateStudentPublicRelationById(UpdateStudentPublicRelationStatusQuery studentPublicRelation) {
        if (null == studentPublicRelation) {
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        StudentPublicRelation updateStudentPublicRelation = new StudentPublicRelation();
        BeanUtils.copyProperties(studentPublicRelation, updateStudentPublicRelation);
        boolean b = updateById(updateStudentPublicRelation);
        return Result.success(b);
    }

//    @Override
//    public StudentPublicRelation queryStudentPublicRelationById(Long studentInfoId) {
//        if (null == studentInfoId) {
//            return null;
//        }
//        QueryWrapper<StudentPublicRelation> studentPublicRelationQueryWrapper = new QueryWrapper<>();
//        studentPublicRelationQueryWrapper.eq("student_info", studentInfoId);
//        StudentPublicRelation studentPublicRelation = baseMapper.selectOne(studentPublicRelationQueryWrapper);
//        return studentPublicRelation;
//    }

    @Override
    public List<StudentPublicRelationVO> queryPrincipal(Long studentInfo, Long recruitSchoolId) {
        return baseMapper.queryPrincipal(studentInfo, recruitSchoolId);
    }

    @Override
    public Long getIdByRecruitSchoolId(Long studentInfo, Long recruitSchoolId) {
        return baseMapper.getIdByRecruitSchoolId(studentInfo, recruitSchoolId);
    }
    @Override
    public Long getRecruitSchoolIdByStudentInfo(Long studentInfo, Long recruitSchoolId) {
        return baseMapper.getRecruitSchoolIdByStudentInfo(studentInfo, recruitSchoolId);
    }

    @Override
    public StudentPublicRelationVO getStudentPublicRelation(Long studentInfo, Long recruitSchoolId) {
        return baseMapper.getStudentPublicRelation(studentInfo,recruitSchoolId);
    }
}
