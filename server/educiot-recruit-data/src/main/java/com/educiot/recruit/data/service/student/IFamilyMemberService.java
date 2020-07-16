package com.educiot.recruit.data.service.student;

import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.data.entity.FamilyMember;
import com.educiot.recruit.data.entity.query.AddFamilyMemberQuery;
import com.educiot.recruit.data.entity.vo.ChargeRelationVO;
import com.educiot.recruit.data.entity.vo.StudentChargerVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 家庭成员表 服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
public interface IFamilyMemberService extends IService<FamilyMember> {


    /**
     * 根据学生基本信息ID查询家庭成员信息
     * @param studentInfo 学生基本信息ID
     * @return 家庭成员信息列表
     */
    List<FamilyMember> queryFamilyMemberInfoList(Long studentInfo);

    /**
     * 添加或编辑学生家庭成员信息
     * @param addFamilyMemberQuery 家庭成员入参实体
     * @return 添加或编辑成功装填
     */
    Boolean saveOrUpdateFamilyMemberInfo(List<AddFamilyMemberQuery> addFamilyMemberQuery,Long studentInfoId);

    /**
     * 查询招生负责人信息
     * @param
     * @return 招生负责人信息
     */
    StudentChargerVO queryTeacherCharge(Long studentInfo,Long school);


    /**
     * 添加学生初始化负责人
     * @param semesterId 学期ID
     * @param sourceSchoolId 生源学校ID
     * @return 负责人信息
     */
    ChargeRelationVO queryChargeRelation(Long semesterId,Long sourceSchoolId);

}
