package com.educiot.recruit.data.mapper;

import com.educiot.recruit.data.entity.FamilyMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.educiot.recruit.data.entity.vo.ChargeRelationVO;
import com.educiot.recruit.data.entity.vo.StudentChargerVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 家庭成员表 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
public interface FamilyMemberMapper extends BaseMapper<FamilyMember> {

    /**
     * 查询招生负责人信息
     * @param studentInfo
     * @return 招生负责人信息
     */
    StudentChargerVO queryTeacherCharge(@Param("studentInfo") Long studentInfo,@Param("schoolId") Long schoolId,@Param("groupId") Long groupId);

    /**
     * 添加学生初始化负责人
     * @param semesterId 学期ID
     * @param sourceSchoolId 生源学校ID
     * @return 负责人信息
     */
    ChargeRelationVO queryChargeRelation(@Param("semesterId") Long semesterId, @Param("sourceSchoolId") Long sourceSchoolId);

}
