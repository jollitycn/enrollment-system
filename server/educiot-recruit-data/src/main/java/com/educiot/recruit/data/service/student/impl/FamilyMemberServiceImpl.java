package com.educiot.recruit.data.service.student.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.data.entity.FamilyMember;
import com.educiot.recruit.data.entity.query.AddFamilyMemberQuery;
import com.educiot.recruit.data.entity.vo.ChargeRelationVO;
import com.educiot.recruit.data.entity.vo.StudentChargerVO;
import com.educiot.recruit.data.entity.vo.StudentPublicRelationVO;
import com.educiot.recruit.data.mapper.FamilyMemberMapper;
import com.educiot.recruit.data.service.student.IFamilyMemberService;
import com.educiot.recruit.data.service.student.IStudentPublicRelationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 家庭成员表 服务实现类
 * </p>
 *
 * @author Mr.AI
 * @since 2020-04-13
 */
@Service
public class FamilyMemberServiceImpl extends ServiceImpl<FamilyMemberMapper, FamilyMember> implements IFamilyMemberService {

    @Resource
    private IStudentPublicRelationService studentPublicRelationService;

    @Override
    public List<FamilyMember> queryFamilyMemberInfoList(Long studentInfo) {
        if (null == studentInfo) {
            return null;
        }
        QueryWrapper<FamilyMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_info", studentInfo);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public Boolean saveOrUpdateFamilyMemberInfo(List<AddFamilyMemberQuery> addFamilyMemberQuery,Long studentInfoId) {
        if (null == addFamilyMemberQuery) {
            return false;
        }
        List<FamilyMember> familyMembers = addFamilyMemberQuery.stream().map(AddFamilyMemberQuery -> {
            FamilyMember familyMember = new FamilyMember();
            BeanUtils.copyProperties(AddFamilyMemberQuery, familyMember);
            if(null != studentInfoId){
                familyMember.setStudentInfo(studentInfoId);
            }
            return familyMember;
        }).collect(Collectors.toList());
        boolean b = saveOrUpdateBatch(familyMembers);
        return b;
    }

    @Override
    public StudentChargerVO queryTeacherCharge(Long studentInfo,Long schoolId) {
        StudentPublicRelationVO studentPublicRelation = studentPublicRelationService.getStudentPublicRelation(studentInfo, schoolId);
        if(null == studentPublicRelation){
            return null;
        }
        return baseMapper.queryTeacherCharge(studentInfo, schoolId, studentPublicRelation.getGroupId());
    }

    @Override
    public ChargeRelationVO queryChargeRelation(Long semesterId, Long sourceSchoolId) {
        log.debug("queryChargeRelation" + semesterId + "," + sourceSchoolId);
        return  baseMapper.queryChargeRelation(semesterId, sourceSchoolId);
//        ChargeRelationVO chargeRelationVOr =new ChargeRelationVO();
//        chargeRelationVOS.forEach(chargeRelationVO -> {
//            if(chargeRelationVO.getIsLeader()==1){
//                BeanUtils.copyProperties(chargeRelationVO,chargeRelationVOr);
//            }
//        });
//        return chargeRelationVOS.get(0);
    }
}
