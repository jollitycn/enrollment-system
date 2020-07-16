package com.educiot.recruit.data.service.student.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.base.MyExcption;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.common.constant.Constant;
import com.educiot.recruit.common.util.JSONRedisUtil;
import com.educiot.recruit.data.entity.*;
import com.educiot.recruit.data.entity.query.*;
import com.educiot.recruit.data.entity.vo.*;
import com.educiot.recruit.data.mapper.StudentInfoMapper;
import com.educiot.recruit.data.service.IChargerService;
import com.educiot.recruit.data.service.IPlanService;
import com.educiot.recruit.data.service.IRecruitSchoolService;
import com.educiot.recruit.data.service.student.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * 学生基本信息表 服务实现类
 * </p>
 *
 * @author Mr.AI
 * @since 2020-04-13
 */
@Service
public class StudentInfoServiceImpl extends ServiceImpl<StudentInfoMapper, StudentInfo> implements IStudentInfoService {

    @Resource
    private IStudentPublicRelationService studentPublicRelationService;

    @Resource
    private IFamilyMemberService familyMemberService;

    @Resource
    private IPlanService planService;

    @Resource
    private IRecruitSchoolService recruitSchoolService;

    @Resource
    private IProgressService progressService;

    @Resource
    private IAimMajorService aimMajorService;

    @Resource
    private IInSchoolService iInSchoolService;

    @Resource
    private IRecruitWayService recruitWayService;

    @Resource
    private IChargerService chargerService;

    @Override
    public StudentInfoStatusVO queryStudentInfoStatus(Long studentInfoId, Long schoolId) {
        StudentInfoStatusVO studentInfoStatusVO = new StudentInfoStatusVO();
        List<AimMajor> aimMajors = aimMajorService.queryAimMajorInfo(studentInfoId, schoolId);
        studentInfoStatusVO.setAimMajor(aimMajors.size() > 0);
        InSchoolVO inSchoolVO = iInSchoolService.queryInSchoolInfo(studentInfoId, schoolId);
        studentInfoStatusVO.setInSchool(null != inSchoolVO);
        StudentPublicRelationRecordVO studentPublicRelationRecordVO = studentPublicRelationService.queryStudentPublicRelation(studentInfoId, schoolId);
        if (null != studentPublicRelationRecordVO) {
            studentInfoStatusVO.setPublicRelation(true);
            RecruitWay recruitWay = recruitWayService.queryRecruitWayInfo(studentPublicRelationRecordVO.getStudentPublicRelationId());
            studentInfoStatusVO.setRecruitWay(null != recruitWay);
        } else {
            studentInfoStatusVO.setPublicRelation(false);
        }

        return studentInfoStatusVO;
    }

    @Override
    public String queryStudentCount(StudentQuery studentQuery) {
        return baseMapper.queryStudentCount(studentQuery);
    }

    @Override
    public IPage<StudentInfoVO> listStudentInfoPageByClassId(ListStudentPageByClassIdQuery param,Long semesterId) {
        IPage<StudentInfoVO> page = new Page<>(param.getPageNum(), param.getPageSize());
        List<StudentInfoVO> list = baseMapper.listStudentInfoPageByClassId(page, param,semesterId);
        page.setRecords(list);
        return page;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public Result addStudentInfo(AddStudentInfoQuery addStudentInfoQuery) throws Exception {
        if (null == addStudentInfoQuery) {
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        try {
            // TODO 缓存信息保存好，需要放开注释
            LocalDateTime now = LocalDateTime.now();
            StudentInfo studentInfo = new StudentInfo();
            BeanUtils.copyProperties(addStudentInfoQuery, studentInfo);
            studentInfo.setCreateTime(now);
            save(studentInfo);
            //初始化学生公关基本信息
            ChargeRelationVO chargeRelationVO = familyMemberService.queryChargeRelation(addStudentInfoQuery.getSemesterId(), addStudentInfoQuery.getSourceSchoolId());
            StudentPublicRelation studentPublicRelation = new StudentPublicRelation();
            studentPublicRelation.setStudentInfo(studentInfo.getStudentInfo());
            studentPublicRelation.setRecruitSchoolId(addStudentInfoQuery.getRecruitSchoolId());
            studentPublicRelation.setStatus(Constant.SYS_ONE);
            studentPublicRelation.setRegisterStatus(Constant.SYS_THREE);
            studentPublicRelation.setSourceSchoolId(addStudentInfoQuery.getSourceSchoolId());
            studentPublicRelation.setClassId(addStudentInfoQuery.getClassId());
            studentPublicRelation.setSemesterId(addStudentInfoQuery.getSemesterId());
            studentPublicRelation.setSemester(addStudentInfoQuery.getSemester());
            //初始化进度查看的状态更改
            studentPublicRelation.setRegisterStatus(Constant.SYS_THREE);

            //初始化负责人
            if (null == chargeRelationVO) {
                return Result.error(CodeMsg.GROUP_NO_LEADER_CHARGE_FOR_SOURCE_SCHOOL);
            }
            studentPublicRelation.setCreatorId(chargeRelationVO.getApiId());
            studentPublicRelation.setCreatorName(chargeRelationVO.getName());
            //添加分组ID
            //Charger charger = chargerService.getById(chargeRelationVO.getChargerId());
            studentPublicRelation.setGroupId(chargeRelationVO.getGroupId());
            //添加学生，加学生登记号
            Integer integer = studentPublicRelationService.queryStudentNo(addStudentInfoQuery.getRecruitSchoolId(), addStudentInfoQuery.getSemesterId());
            if (null == integer) {
                studentPublicRelation.setStudentNo(1);
            } else {
                studentPublicRelation.setStudentNo(integer);
            }
            boolean b = studentPublicRelationService.save(studentPublicRelation);
            //初始化招生渠道
            RecruitWay recruitWay = new RecruitWay();
            recruitWay.setStudentPublicRelationId(studentPublicRelation.getStudentPublicRelationId());
            recruitWay.setRecruitType(Constant.SYS_ZERO);
            return Result.success(studentInfo);
        }catch (Exception e){
            throw new Exception();
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public Result addStudentInfoAPP(AddStudentInfoQuery addStudentInfoQuery) throws Exception {
        if (null == addStudentInfoQuery) {
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }

        try {
            LocalDateTime now = LocalDateTime.now();
            StudentInfo studentInfo = new StudentInfo();
            BeanUtils.copyProperties(addStudentInfoQuery, studentInfo);
            studentInfo.setCreateTime(now);
            save(studentInfo);

            //添加学生家庭信息
            if (addStudentInfoQuery.getFamilyMembers().size() > 0) {
                List<FamilyMember> familyMembers = addStudentInfoQuery.getFamilyMembers().stream().map(AddFamilyMemberQuery -> {
                    FamilyMember familyMember = new FamilyMember();
                    BeanUtils.copyProperties(AddFamilyMemberQuery, familyMember);
                    familyMember.setStudentInfo(studentInfo.getStudentInfo());
                    return familyMember;
                }).collect(Collectors.toList());
                familyMemberService.saveBatch(familyMembers);
            }

            //初始化学生公关基本信息
            ChargeRelationVO chargeRelationVO = familyMemberService.queryChargeRelation(addStudentInfoQuery.getSemesterId(), addStudentInfoQuery.getSourceSchoolId());
            StudentPublicRelation studentPublicRelation = new StudentPublicRelation();
            studentPublicRelation.setStudentInfo(studentInfo.getStudentInfo());
            studentPublicRelation.setRecruitSchoolId(addStudentInfoQuery.getRecruitSchoolId());
            studentPublicRelation.setStatus(Constant.SYS_ONE);
            studentPublicRelation.setRegisterStatus(Constant.SYS_THREE);
            studentPublicRelation.setSourceSchoolId(addStudentInfoQuery.getSourceSchoolId());
            studentPublicRelation.setClassId(addStudentInfoQuery.getClassId());
            studentPublicRelation.setSemesterId(addStudentInfoQuery.getSemesterId());
            studentPublicRelation.setSemester(addStudentInfoQuery.getSemester());
            //初始化负责人
            if (null == chargeRelationVO) {
                return Result.error(CodeMsg.GROUP_NO_LEADER_CHARGE_FOR_SOURCE_SCHOOL);
            }
            studentPublicRelation.setCreatorId(chargeRelationVO.getApiId());
            studentPublicRelation.setCreatorName(chargeRelationVO.getName());
            studentPublicRelation.setGroupId(chargeRelationVO.getGroupId());

            //添加学生，加学生登记号
            Integer i = studentPublicRelationService.queryStudentNo(addStudentInfoQuery.getRecruitSchoolId(),addStudentInfoQuery.getSemesterId());
            if (null == i) {
                studentPublicRelation.setStudentNo(1);
            } else {
                studentPublicRelation.setStudentNo(i);
            }
            //添加进度
            progressService.addProgress(0, studentPublicRelation.getStudentPublicRelationId(), addStudentInfoQuery.getCreatorId(), addStudentInfoQuery.getCreatorName());
            studentPublicRelationService.save(studentPublicRelation);
            return Result.success();
        } catch (Exception e) {
            throw new Exception();
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result switchStudent(Long studentInfo, MiniProgramLoginVO loginVO) {
        StudentInfo student = baseMapper.selectById(studentInfo);
        if (null == student) {
            return Result.error(CodeMsg.STUDENT_INFO_UN_EXIST);
        }

        // 保存切换的学生
        StudentIdAndNameVO studentIdAndNameVO = new StudentIdAndNameVO();
        BeanUtil.copyProperties(student, studentIdAndNameVO);
        loginVO.setCurrentStudent(studentIdAndNameVO);

        // 查询公关信息
        setStudentPublicRelation(loginVO);

        // 生成新的token
        String oldToken = loginVO.getToken();
        String token = DigestUtil.md5Hex(JSONObject.toJSONString(loginVO));
        loginVO.setToken(token);


        JSONRedisUtil.hSetWithTime(Constant.RedisKey.WECHAT_TOKEN_KEY, token, loginVO, Constant.Sys.TOKEN_EXPIRE_TIME);  // 删除旧的token
        //RedisUtil.delHSet(Constant.RedisKey.WECHAT_TOKEN_KEY, oldToken);

        return Result.success(token);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result switchRecruitSchool(Long recruitSchoolId, MiniProgramLoginVO loginVO) {
        RecruitSchoolIdAndNameVO recruitSchool = recruitSchoolService.getRecruitSchoolIdAndNameVOById(recruitSchoolId);
        if (null == recruitSchool) {
            return Result.error(CodeMsg.STUDENT_INFO_UN_EXIST);
        }

        loginVO.setCurrentRecruitSchool(recruitSchool);
        setStudentPublicRelation(loginVO);

        // 查询招生计划信息
        Result<PlanLoginVO> defaultPlan = planService.queryDefaultPlanLogin(loginVO.getCurrentRecruitSchool().getRecruitSchoolId());
        if (defaultPlan.getData() != null) {
            loginVO.setPlan(BeanUtil.copyProperties(defaultPlan.getData(), PlanLoginVO.class));
        }

        // 生成新的token
        String token = DigestUtil.md5Hex(JSONObject.toJSONString(loginVO));
        String oldToken = loginVO.getToken();
        loginVO.setToken(token);

        // 将token存入redis
        JSONRedisUtil.hSetWithTime(Constant.RedisKey.WECHAT_TOKEN_KEY, token, loginVO, Constant.Sys.TOKEN_EXPIRE_TIME);

        // 删除旧的token
        //  RedisUtil.delHSet(Constant.RedisKey.WECHAT_TOKEN_KEY, oldToken);

        return Result.success(token);
    }

    private void setStudentPublicRelation(MiniProgramLoginVO loginVO) {
        // 查询公关信息
        if (loginVO.getCurrentRecruitSchool() != null) {
            Long studentPublic = studentPublicRelationService.getIdByRecruitSchoolId(loginVO.getCurrentStudent().getStudentInfo(), loginVO.getCurrentRecruitSchool().getRecruitSchoolId());
            loginVO.setStudentPublicRelationId(studentPublic);
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public Result updateStudentInfo(UpdateStudentInfoQuery updateStudentInfoQuery) {
        if (null == updateStudentInfoQuery) {
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        StudentInfo studentInfo = new StudentInfo();
        BeanUtils.copyProperties(updateStudentInfoQuery, studentInfo);
        boolean b = updateById(studentInfo);
        return Result.success(b);
    }

    @Override
    public Result updateStudentInfoAPP(UpdateStudentInfoQuery updateStudentInfoQuery) throws Exception {
        if (null == updateStudentInfoQuery) {
            return Result.error(CodeMsg.PARAMETER_ISNULL);
        }
        try {
            StudentInfo studentInfo = new StudentInfo();
            BeanUtils.copyProperties(updateStudentInfoQuery, studentInfo);
            updateById(studentInfo);
            //编辑家庭信息
            if (null == updateStudentInfoQuery) {
                return Result.error(CodeMsg.PARAMETER_ISNULL);
            }
            List<FamilyMember> familyMembers = updateStudentInfoQuery.getAddFamilyMemberQueries().stream().map(AddFamilyMemberQuery -> {
                FamilyMember familyMember = new FamilyMember();
                BeanUtils.copyProperties(AddFamilyMemberQuery, familyMember);
                return familyMember;
            }).collect(Collectors.toList());
            familyMemberService.saveOrUpdateBatch(familyMembers);

            //修改学生公关信息中的生源学校和班级
            StudentPublicRelationRecordVO studentPublicRelationRecordVO = studentPublicRelationService.queryStudentPublicRelation(updateStudentInfoQuery.getStudentInfo(), updateStudentInfoQuery.getRecruitSchoolId());
            StudentPublicRelation studentPublicRelation=new StudentPublicRelation();
            studentPublicRelation.setSourceSchoolId(updateStudentInfoQuery.getSourceSchoolId());
            studentPublicRelation.setClassId(updateStudentInfoQuery.getClassId());
            studentPublicRelation.setStudentPublicRelationId(studentPublicRelationRecordVO.getStudentPublicRelationId());
            studentPublicRelationService.updateById(studentPublicRelation);
        } catch (Exception e) {
            throw new Exception();
        }
        return Result.success();
    }

    @Override
    public StudentInfo queryStudentInfo(Long studentInfoId) {
        if (null == studentInfoId) {
            return null;
        }
        StudentInfo studentInfo = getById(studentInfoId);
        if(null == studentInfo.getHealthStatus() || "".equals(studentInfo.getHealthStatus())){
            studentInfo.setHealthStatus("健康");
        }
        return studentInfo;
    }

    @Override
    public StudentInfoVO queryStudentInfoAPP(Long studentInfoId, Long schoolId) {
        if (null == studentInfoId) {
            return null;
        }
        StudentInfoVO studentInfo = baseMapper.queryByStudentInfoId(studentInfoId, schoolId);
        if(null == studentInfo.getHealthStatus() || "".equals(studentInfo.getHealthStatus())){
            studentInfo.setHealthStatus("健康");
        }
        List<FamilyMember> familyMembers = familyMemberService.queryFamilyMemberInfoList(studentInfoId);
        StudentInfoVO studentInfoVO = new StudentInfoVO();
        BeanUtils.copyProperties(studentInfo, studentInfoVO);
        studentInfoVO.setFamilyMembers(familyMembers);
        return studentInfoVO;
    }

    @Override
    public IPage<StudentInfoVO> listStudentInfoPage(ListStudentPageQuery param) {
        IPage<StudentInfoVO> page = new Page<>(param.getPageNum(), param.getPageSize());
        List<StudentInfoVO> list = baseMapper.listStudentPage(page, param);
        page.setRecords(list);
        return page;
    }

    @Override
    public int listStudentCount(ListStudentPageQuery param) {
        return baseMapper.listStudentCount(param);
    }

    @Override
    public IPage<StudentInfoVO> listStudentInfoPageForFamilyMember(ListStudentPageQuery param, Long chargerId) {
        IPage<StudentInfoVO> page = new Page<>(param.getPageNum(), param.getPageSize());
        Charger charger = chargerService.getById(chargerId);
        param.setGroupId(charger.getGroupId());
        List<StudentInfoVO> list = baseMapper.listStudentPage(page, param);
        if (null != param && null != param.getClassId() && list.size() > 0) {
            list.forEach(studentInfoVO -> {
                List<FamilyMember> familyMembers = familyMemberService.queryFamilyMemberInfoList(studentInfoVO.getStudentInfo());
                //将学生的家长信息set到学生基本信息中一起返回
                if (familyMembers.size() > 0) {
                    familyMembers.forEach(familyMember -> {
                        if (null != familyMember && null != familyMember.getRelation()) {
                            if (familyMember.getRelation() == 0) {
                                studentInfoVO.setFatherName(familyMember.getName());
                                studentInfoVO.setFatherTelephone(familyMember.getTelephone());
                            } else {
                                studentInfoVO.setMotherName(familyMember.getName());
                                studentInfoVO.setMotherTelephone(familyMember.getTelephone());
                            }
                        }
                    });
                }
            });
        }
        page.setRecords(list);
        return page;
    }


    @Override
    public Boolean queryStudentInfoStatu(Long studentInfoId) {
        if (null == studentInfoId) {
            return null;
        }
        StudentInfo studentInfo = getById(studentInfoId);
        return null != studentInfo && null != studentInfo.getStudentName() &&
                null != studentInfo.getGender() &&
                null != studentInfo.getNation() &&
                null != studentInfo.getDoa() &&
                null != studentInfo.getIdNo() &&
                null != studentInfo.getHealthStatus() &&
                null != studentInfo.getProvince() &&
                null != studentInfo.getCity() &&
                null != studentInfo.getDistrict();
    }

    @Override
    public Boolean queryContactDetailStatu(Long studentInfoId) {
        if (null == studentInfoId) {
            return null;
        }
        StudentInfo studentInfo = getById(studentInfoId);
        return null != studentInfo && null != studentInfo.getWechat() &&
                null != studentInfo.getParentTelephone() &&
                null != studentInfo.getStudentTelephone() &&
                null != studentInfo.getFamilyCity() &&
                null != studentInfo.getFamilyDistrict() &&
                null != studentInfo.getFamilyProvince() &&
                null != studentInfo.getAddress();
    }

    @Override
    public Boolean queryRequiredFieldStatu(Long studentInfoId) {
        if (null == studentInfoId) {
            return null;
        }
        StudentInfo studentInfo = getById(studentInfoId);
        QueryWrapper<StudentPublicRelation> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("student_info",studentInfoId);
        StudentPublicRelation studentP = studentPublicRelationService.getOne(queryWrapper);
        return null != studentInfo && null != studentInfo.getStudentName() &&
                null != studentInfo.getDoa() && null != studentInfo.getProvince() &&
                null != studentInfo.getCity() && null != studentInfo.getDistrict() &&
                null != studentInfo.getParentTelephone() && null != studentInfo.getFamilyCity() &&
                null != studentInfo.getFamilyDistrict() && null != studentInfo.getFamilyProvince() &&
                null != studentInfo.getAddress() && null != studentP.getSourceSchoolId() &&
                null != studentP.getClassId();
    }

    @Override
    public List<ListSourceSchoolVO> listSourceSchool(Long chargerId) {
        return baseMapper.listSourceSchool(chargerId);
    }

    @Override
    public Plan queryDefaultPlanWeChat(Long recruitSchoolId) {
        if (null == recruitSchoolId) {
            return null;
        }
        QueryWrapper<Plan> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("recruit_school_id", recruitSchoolId);
        queryWrapper.eq("is_defaulted", Constant.SYS_ONE);
        queryWrapper.eq("status", Constant.SYS_TWO);
        Plan plan = planService.getOne(queryWrapper);
        return plan;
    }

}
