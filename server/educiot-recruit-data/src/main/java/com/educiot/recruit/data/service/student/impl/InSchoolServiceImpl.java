package com.educiot.recruit.data.service.student.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.base.MyExcption;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.entity.InSchool;
import com.educiot.recruit.data.entity.Score;
import com.educiot.recruit.data.entity.StudentPublicRelation;
import com.educiot.recruit.data.entity.query.AddInSchoolQuery;
import com.educiot.recruit.data.entity.vo.InSchoolClassInfoVO;
import com.educiot.recruit.data.entity.vo.InSchoolVO;
import com.educiot.recruit.data.entity.vo.StudentPublicRelationRecordVO;
import com.educiot.recruit.data.mapper.InSchoolMapper;
import com.educiot.recruit.data.service.IScoreService;
import com.educiot.recruit.data.service.student.IInSchoolService;
import com.educiot.recruit.data.service.student.IStudentPublicRelationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 学生在校信息表 服务实现类
 * </p>
 *
 * @author Mr.AI
 * @since 2020-04-13
 */
@Service
public class InSchoolServiceImpl extends ServiceImpl<InSchoolMapper, InSchool> implements IInSchoolService {

    @Resource
    private IScoreService scoreService;

    @Resource
    private InSchoolMapper inSchoolMapper;

    @Resource
    private IStudentPublicRelationService studentPublicRelationService;

    @Override
    public Boolean saveOrUpdateInSchoolInfoForWeChat(AddInSchoolQuery addInSchoolQuery) throws MyExcption {
        if (null == addInSchoolQuery) {
            return false;
        }

        try {
            InSchool inSchool = new InSchool();
            if (null == addInSchoolQuery.getInSchoolId()) {
                LocalDateTime now = LocalDateTime.now();
                addInSchoolQuery.setCreateTime(now);
            }
            BeanUtils.copyProperties(addInSchoolQuery, inSchool);
            saveOrUpdate(inSchool);
            List<Score> scores = addInSchoolQuery.getScores();
            //新增或修改学生成绩
            if (scores.size() != 0) {
                scores.stream().map(Score -> {
                    Score.setInSchoolId(inSchool.getInSchoolId());
                    return Score;
                }).collect(Collectors.toList());
                scoreService.saveOrUpdateBatch(scores);
            }

            //修改学生在校信息的毕业学校和所属班级
            if(null != addInSchoolQuery.getStudentPublicRelationId()){
                StudentPublicRelation studentPublicRelation=new StudentPublicRelation();
                studentPublicRelation.setClassId(addInSchoolQuery.getClassId());
                studentPublicRelation.setSourceSchoolId(addInSchoolQuery.getSourceSchoolId());
                studentPublicRelation.setStudentPublicRelationId(addInSchoolQuery.getStudentPublicRelationId());
                studentPublicRelationService.updateById(studentPublicRelation);
            }
            //在添加或编辑学生在校信息的同时，也需要更新公关信息
            StudentPublicRelation studentPublicRelation = studentPublicRelationService.getById(addInSchoolQuery.getStudentPublicRelationId());
            if (null != studentPublicRelation) {
                studentPublicRelation.setClassId(addInSchoolQuery.getClassId());
                studentPublicRelation.setSourceSchoolId(addInSchoolQuery.getSourceSchoolId());
                studentPublicRelationService.updateById(studentPublicRelation);
            }
        }catch (Exception e){
            throw new MyExcption(CodeMsg.DATA_UPDATE_ERROR, e);
        }
       return true;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public Result saveOrUpdateInSchoolInfo(AddInSchoolQuery addInSchoolQuery)  throws MyExcption  {
        if (null == addInSchoolQuery) {
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        try {
            InSchool inSchool = new InSchool();
            if (null == addInSchoolQuery.getInSchoolId()) {
                LocalDateTime now = LocalDateTime.now();
                addInSchoolQuery.setCreateTime(now);
            }
            BeanUtils.copyProperties(addInSchoolQuery, inSchool);
            saveOrUpdate(inSchool);
            List<Score> scores = addInSchoolQuery.getScores();
            //新增或修改学生成绩
            if (scores.size() != 0) {
                scores.stream().map(Score -> {
                    Score.setInSchoolId(inSchool.getInSchoolId());
                    return Score;
                }).collect(Collectors.toList());
                scoreService.saveOrUpdateBatch(scores);
            }
            //修改学生公关信息中的生源学校和班级
            StudentPublicRelationRecordVO studentPublicRelationRecordVO = studentPublicRelationService.queryStudentPublicRelation(addInSchoolQuery.getStudentInfo(), addInSchoolQuery.getRecruitSchoolId());
            StudentPublicRelation studentPublicRelation=new StudentPublicRelation();
            studentPublicRelation.setSourceSchoolId(addInSchoolQuery.getSourceSchoolId());
            studentPublicRelation.setClassId(addInSchoolQuery.getClassId());
            studentPublicRelation.setStudentPublicRelationId(studentPublicRelationRecordVO.getStudentPublicRelationId());
            studentPublicRelationService.updateById(studentPublicRelation);
        }catch (Exception e){
            throw new MyExcption(CodeMsg.DATA_UPDATE_ERROR);
        }
        return Result.success();
    }

    @Override
    public InSchoolVO queryInSchoolInfo(Long studentInfoId, Long recruitSchoolId) {
        if (null == studentInfoId) {
            return null;
        }
        InSchoolVO inSchoolVO = inSchoolMapper.queryInSchoolInfo(studentInfoId, recruitSchoolId);
        return inSchoolVO;
    }

    @Override
    public Boolean queryInSchoolInfoStatu(Long studentInfoId, Long recruitSchoolId) {
        if (null == studentInfoId) {
            return null;
        }
        InSchoolVO inSchoolVO = inSchoolMapper.queryInSchoolInfo(studentInfoId, recruitSchoolId);
        return null != inSchoolVO && null != inSchoolVO.getClassName() &&
                null != inSchoolVO.getSourceSchoolName() &&
                null != inSchoolVO.getSchoolNo() &&
                null != inSchoolVO.getAdviser() &&
                null != inSchoolVO.getTalent() &&
                inSchoolVO.getScores().size() >= 3;
    }

    @Override
    public InSchoolClassInfoVO getSourceSchoolInfo(Long studentInfoId, Long recruitSchoolId) {
        if (null == studentInfoId) {
            return null;
        }
        InSchoolClassInfoVO inSchoolClassInfoVO = inSchoolMapper.getSourceSchoolInfo(studentInfoId, recruitSchoolId);
        return inSchoolClassInfoVO;
    }
}
