package com.educiot.recruit.data.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.common.constant.Constant;
import com.educiot.recruit.common.util.JSONRedisUtil;
import com.educiot.recruit.data.entity.Class;
import com.educiot.recruit.data.entity.*;
import com.educiot.recruit.data.entity.query.*;
import com.educiot.recruit.data.entity.vo.*;
import com.educiot.recruit.data.mapper.LoginMapper;
import com.educiot.recruit.data.service.*;
import com.educiot.recruit.data.service.student.IFamilyMemberService;
import com.educiot.recruit.data.service.student.IStudentInfoService;
import com.educiot.recruit.data.service.student.IStudentPublicRelationService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 登录表 服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-28
 */
@Service
@Log4j2
public class LoginServiceImpl extends ServiceImpl<LoginMapper, Login> implements ILoginService {

    @Resource
    private ILoginRelationService loginRelationService;

    @Resource
    private IRecruitSchoolService recruitSchoolService;

    @Resource
    private IStudentInfoService studentInfoService;

    @Resource
    private IStudentPublicRelationService studentPublicRelationService;

    @Resource
    private ISourceSchoolService sourceSchoolService;

    @Resource
    private IClassService classService;

    @Resource
    private IPlanService planService;

    @Resource
    private IFamilyMemberService familyMemberService;

    @Override
    public Result loginOrRegister(LoginQuery loginOrRegisterQuery, String openid) {
        log.info("loginOrRegister");
        // 查询是否注册过
        QueryWrapper<Login> loginWrapper = new QueryWrapper<>();
        loginWrapper.eq(Constant.Talble.LOGIN_OPENID, openid);
        Login login = baseMapper.selectOne(loginWrapper);
        if (null == login) {
            return Result.error(CodeMsg.USER_UN_REGISTER, openid);
        }
        if (null != loginOrRegisterQuery && null != loginOrRegisterQuery.getRecruitSchoolId()) {
            // 判断该招生学校是否和此openid有学生关联，
            // 有则不处理，无则需把该openid关注的学生全部绑定到该招生学校下
            // 1.查询关注学生
            List<StudentIdAndNameVO> studentList = loginRelationService.listFocusStudent(login.getLogin());
            if (studentList.size() == 0) {
                return Result.error(CodeMsg.UN_BIND_STUDENT);
            }
            // 2.查询关注学生绑定的公关信息中招生学校
            QueryWrapper<StudentPublicRelation> publicRelationWrapper = new QueryWrapper<>();
            List<Long> studentInfoIds =
                    studentList.stream().map(StudentIdAndNameVO::getStudentInfo).collect(Collectors.toList());
            publicRelationWrapper.in(Constant.Talble.LOGIN_RELATION_STUDENT_INFO, studentInfoIds);
            publicRelationWrapper.eq(Constant.Talble.RECRUIT_SCHOOL_ID, loginOrRegisterQuery.getRecruitSchoolId());
            int count = studentPublicRelationService.count(publicRelationWrapper);
            if (count == 0) {
                // 无关联，返回学生信息
                SelectStudentAndRecruitVO selectStudentAndRecruitVO = new SelectStudentAndRecruitVO();
                selectStudentAndRecruitVO.setStudentList(studentList);
                selectStudentAndRecruitVO.setRecruitSchoolId(loginOrRegisterQuery.getRecruitSchoolId());
                selectStudentAndRecruitVO.setOpenid(openid);
                return Result.error(CodeMsg.OPPENID_UN_BIND_RECRUIT_SCHOOL, selectStudentAndRecruitVO);
            }
        }
        return Result.success(login);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result bindRecruitSchoolAndStudent(BindRecruitAndStudentQuery bindRecruitAndStudentQuery) {
        log.info("bindRecruitSchoolAndStudent: param = {}", bindRecruitAndStudentQuery);

        // 通过openid查询login
        QueryWrapper<Login> loginWrapper = new QueryWrapper<>();
        loginWrapper.eq(Constant.Talble.LOGIN_OPENID, bindRecruitAndStudentQuery.getOpenid());
        Login login = baseMapper.selectOne(loginWrapper);
        if (null == login) {
            return Result.error(CodeMsg.USER_UN_REGISTER, bindRecruitAndStudentQuery.getOpenid());
        }

        // 建立公关信息
        LocalDateTime now = LocalDateTime.now();
        StudentPublicRelation studentPublicRelation = new StudentPublicRelation();
        BeanUtil.copyProperties(bindRecruitAndStudentQuery, studentPublicRelation);
        studentPublicRelation.setStatus(Constant.SYS_ONE);
        studentPublicRelation.setCreateTime(now);
        studentPublicRelation.setStudentInfo(bindRecruitAndStudentQuery.getStudentInfo());
        // 查询默认计划
        Result<PlanLoginVO> defaultPlan = planService.queryDefaultPlanLogin(studentPublicRelation.getRecruitSchoolId());

        if (null != defaultPlan.getData()) {
            PlanLoginVO planLoginVO = BeanUtil.copyProperties(defaultPlan.getData(), PlanLoginVO.class);
            studentPublicRelation.setSemesterId(planLoginVO.getSemesterId());
            //负责人,分组
            ChargeRelationVO chargeRelationVO = familyMemberService.queryChargeRelation(planLoginVO.getSemesterId(),
                    studentPublicRelation.getSourceSchoolId());
            if (null == chargeRelationVO) {
                return Result.error(CodeMsg.GROUP_NO_LEADER_CHARGE);
            }
            studentPublicRelation.setGroupId(chargeRelationVO.getGroupId());
            studentPublicRelation.setCreatorId(chargeRelationVO.getApiId());
            //添加学生，加学生登记号
            Integer integer = studentPublicRelationService.queryStudentNo(planLoginVO.getRecruitSchoolId(),
                    planLoginVO.getSemesterId());
            if (null == integer) {
                studentPublicRelation.setStudentNo(1);
            } else {
                studentPublicRelation.setStudentNo(integer);
            }

            studentPublicRelationService.save(studentPublicRelation);
        }

        return Result.success(login);
    }

    @Override
    public Result checkStudentAndPhone(CheckStudentQuery checkStudentQuery) {
        log.info("checkStudentAndPhone");
        QueryWrapper<StudentInfo> studentInfoWrapper = new QueryWrapper<>();
        studentInfoWrapper.eq(Constant.Talble.STUDENT_INFO_STUDENT_NAME, checkStudentQuery.getStudentName());
        studentInfoWrapper.eq(Constant.Talble.STUDENT_INFO_PARENT_TELEPHONE, checkStudentQuery.getParentTelephone());
        StudentInfo student = studentInfoService.getOne(studentInfoWrapper);
        // 需绑定已存在的学生和家长手机
        if (null == student) {
            return Result.error(CodeMsg.STUDENT_UN_EXIST);
        }
        return Result.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result<Login> bindStudent(BindStudentQuery bindStudentQuery, String openid) {
        log.info("bindStudent");
        // 查询这个学生的信息
        QueryWrapper<StudentInfo> studentInfoWrapper = new QueryWrapper<>();
        studentInfoWrapper.eq(Constant.Talble.STUDENT_INFO_PARENT_TELEPHONE, bindStudentQuery.getParentTelephone());
        studentInfoWrapper.eq(Constant.Talble.STUDENT_INFO_STUDENT_NAME, bindStudentQuery.getStudentName());
        StudentInfo student = studentInfoService.getOne(studentInfoWrapper);
        if (null == student) {
            return Result.error(CodeMsg.STUDENT_UN_EXIST);
        }

        // 先注册（入库）这个openid
        LocalDateTime now = LocalDateTime.now();
        Login login = new Login();
        login.setMobile(bindStudentQuery.getParentTelephone());
        login.setOpenid(openid);
        login.setLoginType(bindStudentQuery.getLoginType());
        login.setCreateTime(now);
        login.setLastTime(now);
        baseMapper.insert(login);

        // 绑定学生信息
        LoginRelation loginRelation = new LoginRelation();
        loginRelation.setLogin(login.getLogin());
        loginRelation.setStudentInfo(student.getStudentInfo());
        loginRelationService.save(loginRelation);
        return Result.success(login);
    }

    @Override
    public Result<MiniProgramLoginVO> getLogin(Login login, Long recruitSchoolId) {

        // 获取登录信息
//        QueryWrapper<LoginRelation> loginRelationWrapper = new QueryWrapper<>();
//        loginRelationWrapper.eq(Constant.Talble.LOGIN_RELATION_LOGIN, login.getLogin());
//        List<LoginRelation> loginRelations = loginRelationService.list(loginRelationWrapper);
        if (login == null || login.getLogin() == null) {
            return Result.error(CodeMsg.LOGIN_INFO_NOT_FOUND);
        }
        List<StudentIdAndNameVO> studentList = loginRelationService.listFocusStudent(login.getLogin());
        if (studentList.size() == 0) {
            return Result.error(CodeMsg.UN_BIND_STUDENT);
        }

        // 封装登录信息
        MiniProgramLoginVO loginVO = new MiniProgramLoginVO();
        //loginVO.setPageFlag(0);
        loginVO.setOpenidInfo(login);
        loginVO.setLoginType(login.getLoginType());
        loginVO.setStudentList(studentList);
        if (studentList.size() > 0) {
            StudentIdAndNameVO student = null;
            for (StudentIdAndNameVO s : studentList) {
                if (s.getLogin().equals(login.getLogin())) {
                    student = s;
                    break;
                }
            }
            loginVO.setCurrentStudent(student);
        }

        // 默认学校
        if (null == recruitSchoolId) {
            //没有的话查询当前学生的招生学校信息
            recruitSchoolId =
                    studentPublicRelationService.getRecruitSchoolIdByStudentInfo(loginVO.getCurrentStudent().getStudentInfo(), null);
        }
        RecruitSchoolIdAndNameVO recruitSchool = recruitSchoolService.getRecruitSchoolIdAndNameVOById(recruitSchoolId);
        loginVO.setCurrentRecruitSchool(BeanUtil.copyProperties(recruitSchool, RecruitSchoolIdAndNameVO.class));
        // 查询招生计划信息
        Result<PlanLoginVO> defaultPlan = planService.queryDefaultPlanLogin(recruitSchoolId);
        if (defaultPlan.getData() != null) {
            loginVO.setPlan(BeanUtil.copyProperties(defaultPlan.getData(), PlanLoginVO.class));
        }
        loginVO.setTimestamp(System.currentTimeMillis());
        // 生成token
        String token = DigestUtil.md5Hex(JSONObject.toJSONString(loginVO));
        loginVO.setToken(token);
        // 查询公关信息
        Long studentPublic =
                studentPublicRelationService.getIdByRecruitSchoolId(loginVO.getCurrentStudent().getStudentInfo(),
                        recruitSchoolId);
        loginVO.setStudentPublicRelationId(studentPublic);


        JSONRedisUtil.hSetWithTime(Constant.RedisKey.WECHAT_TOKEN_KEY, token, loginVO, Constant.Sys.TOKEN_EXPIRE_TIME);

        // 更新最后一次登录的时间
        login.setLastTime(LocalDateTime.now());
        baseMapper.updateById(login);

        return Result.success(loginVO);
    }

    @Override
    public Result bindStudent(AddBindStudentQuery addBindStudentQuery, MiniProgramLoginVO loginVO) {
        Long currentStudentInfo = loginVO.getCurrentStudent().getStudentInfo();
        StudentInfo loginStudent = studentInfoService.getById(currentStudentInfo);
        if (null == loginStudent) {
            return Result.error(CodeMsg.STUDENT_INFO_UN_EXIST);
        }
        // 查询这个学生的信息
//        QueryWrapper<StudentInfo> studentInfoWrapper = new QueryWrapper<>();
//        studentInfoWrapper.eq(Constant.Talble.STUDENT_INFO_STUDENT_NAME, addBindStudentQuery.getStudentName());
//        studentInfoWrapper.eq(Constant.Talble.STUDENT_INFO_PARENT_TELEPHONE, loginStudent.getParentTelephone());
//        StudentInfo student = studentInfoService.getOne(studentInfoWrapper);
        StudentQuery studentQuery = new StudentQuery();
        studentQuery.setClassId(addBindStudentQuery.getClassId());
        studentQuery.setRecruitSchoolId(addBindStudentQuery.getRecruitSchoolId());
        studentQuery.setSourceSchoolId(addBindStudentQuery.getSourceSchoolId());
        studentQuery.setStudentName(addBindStudentQuery.getStudentName());
        String str = studentInfoService.queryStudentCount(studentQuery);
        if (null == str) {
            return Result.error(CodeMsg.STUDENT_UN_EXIST);
        }

        // 绑定学生信息
        LoginRelation loginRelation = new LoginRelation();
        loginRelation.setLogin(loginVO.getOpenidInfo().getLogin());
        loginRelation.setStudentInfo(Long.parseLong(str));
        loginRelationService.save(loginRelation);

        StudentIdAndNameVO studentIdAndNameVO = new StudentIdAndNameVO();
        studentIdAndNameVO.setStudentInfo(Long.parseLong(str));
        studentIdAndNameVO.setStudentName(addBindStudentQuery.getStudentName());
        return Result.success(studentIdAndNameVO);
    }

    @Override
    public Result register(RegisterQuery registerQuery, String openid) {
        log.info("register");
//        if (!Constant.Sys.DEFAULT_VERIFY_CODE.equals(registerQuery.getVerifyCode())) {
//            return Result.error(CodeMsg.VERIFY_CODE_ERROR);
//        }
        // 判断家长手机号是否存在
        QueryWrapper<StudentInfo> studentInfoWrapper = new QueryWrapper<>();
        studentInfoWrapper.eq(Constant.Talble.STUDENT_INFO_PARENT_TELEPHONE, registerQuery.getParentTelephone());
        List<StudentInfo> studentInfos = studentInfoService.list(studentInfoWrapper);
        if (!studentInfos.isEmpty()) {
            // 已注册
            // 在判断名称学生是否一样
            for (StudentInfo studentInfo : studentInfos) {
                if (registerQuery.getStudentName().equals(studentInfo.getStudentName())) {
                    return Result.error(CodeMsg.BIND_EXIST_STUDENT);
                }
            }
            return Result.error(CodeMsg.WILL_CREATE_NEW_STUDENT);
        }

        // 验证生源学校id，班级id，学生姓名是否存在
        QueryWrapper<StudentPublicRelation> studentPublicRelationWrapper = new QueryWrapper<>();
        studentPublicRelationWrapper.eq(Constant.Talble.SOURCE_SCHOOL_ID, registerQuery.getSourceSchoolId());
        studentPublicRelationWrapper.eq(Constant.Talble.CLASS_ID, registerQuery.getClassId());
        List<StudentPublicRelation> studentPublicRelations =
                studentPublicRelationService.list(studentPublicRelationWrapper);
        if (!studentPublicRelations.isEmpty()) {
            // 学生基本信息主键
            List<Long> studentInfoIds = Lists.newArrayList();
            // 学生基本信息主键与公关信息map，为后续查找学校班级信息
            Map<Long, StudentPublicRelation> relationMap = Maps.newHashMap();
            studentPublicRelations.forEach(studentPublicRelation -> {
                studentInfoIds.add(studentPublicRelation.getStudentInfo());
                relationMap.put(studentPublicRelation.getStudentInfo(), studentPublicRelation);
            });

            // 查询学生信息
            List<StudentInfo> infos = studentInfoService.listByIds(studentInfoIds);
            if (!infos.isEmpty()) {
                for (StudentInfo studentInfo : infos) {
                    if (registerQuery.getStudentName().equals(studentInfo.getStudentName())) {
                        // 该学生已经存在，封装返回信息（页面展示的学生信息）
                        SchoolStudentClassVO schoolStudentClassVO = new SchoolStudentClassVO();
                        schoolStudentClassVO.setRecruitSchoolId(registerQuery.getRecruitSchoolId());
                        StudentPublicRelation studentPublicRelation = relationMap.get(studentInfo.getStudentInfo());
                        // 生源学校
                        SourceSchool sourceSchool =
                                sourceSchoolService.getById(studentPublicRelation.getSourceSchoolId());
                        schoolStudentClassVO.setSourceSchoolId(null == sourceSchool ? null :
                                sourceSchool.getSourceSchoolId());
                        schoolStudentClassVO.setSourceSchoolName(null == sourceSchool ? "" :
                                sourceSchool.getSourceSchoolName());
                        // 班级
                        Class clazz = classService.getById(studentPublicRelation.getClassId());
                        schoolStudentClassVO.setClassId(null == clazz ? null : clazz.getClassId());
                        schoolStudentClassVO.setClassName(null == clazz ? "" : clazz.getClassName());

                        schoolStudentClassVO.setStudentInfo(studentInfo.getStudentInfo());
                        // 隐藏手机号中间5位
                        schoolStudentClassVO.setStudentName(studentInfo.getStudentName());
                        schoolStudentClassVO.setParentTelephone(studentInfo.getParentTelephone()
                                .replaceAll("(\\d{3})\\d{5}(\\d{3})", "$1*****$2"));

                        return Result.error(CodeMsg.STUDENT_AND_PHONE_EXIST, schoolStudentClassVO);
                    }
                }
            }
        }

        // 新建学生和注册
        RegisterNewStudentQuery registerNewStudentQuery = new RegisterNewStudentQuery();
        BeanUtil.copyProperties(registerQuery, registerNewStudentQuery);
        return this.registerNewStudent(registerNewStudentQuery, openid);
    }

    @Override
    public Result registerBind(RegisterBindQuery registerBindQuery, String openid) {
        log.info("registerBind");
        // TODO 判断是否输入三次错误
        if (1 < Constant.SYS_THREE) {
            // 验证输入手机号是否一致
            StudentInfo studentInfo = studentInfoService.getById(registerBindQuery.getStudentInfo());
            if (null == studentInfo) {
                return Result.error(CodeMsg.STUDENT_INFO_UN_EXIST);
            }
            if (!studentInfo.getParentTelephone().equals(registerBindQuery.getParentTelephone())) {
                // TODO 次数+1
                return Result.error(CodeMsg.REGISTER_PHONE_CANNOT_MATCH);
            }

            // 手机号匹配，绑定
            return this.bindStudent(BeanUtil.copyProperties(registerBindQuery, BindStudentQuery.class), openid);
        }

        // 超过三次，开始注册
        RegisterNewStudentQuery registerNewStudentQuery = new RegisterNewStudentQuery();
        BeanUtil.copyProperties(registerBindQuery, registerNewStudentQuery);
        return this.registerNewStudent(registerNewStudentQuery, openid);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result<Login> registerNewStudent(RegisterNewStudentQuery registerNewStudentQuery, String openid) {
        log.info("registerNewStudent");
        // 首先新建学生基本信息
        LocalDateTime now = LocalDateTime.now();
        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentName(registerNewStudentQuery.getStudentName());
//        if(registerNewStudentQuery.getLoginType() == 0){
//            studentInfo.setStudentTelephone(registerNewStudentQuery.getParentTelephone());
//        }else{
//            studentInfo.setParentTelephone(registerNewStudentQuery.getParentTelephone());
//        }
        studentInfo.setParentTelephone(registerNewStudentQuery.getParentTelephone());
        studentInfo.setCreateTime(now);
        studentInfoService.save(studentInfo);

        // 建立公关信息
        StudentPublicRelation studentPublicRelation = new StudentPublicRelation();
        BeanUtil.copyProperties(registerNewStudentQuery, studentPublicRelation);
        studentPublicRelation.setStudentInfo(studentInfo.getStudentInfo());
        studentPublicRelation.setStatus(Constant.SYS_ONE);
        studentPublicRelation.setCreateTime(now);
        //添加学期ID
        Result<PlanLoginVO> defaultPlan = planService.queryDefaultPlanLogin(studentPublicRelation.getRecruitSchoolId());

        if (null != defaultPlan.getData()) {
            PlanLoginVO planLoginVO = BeanUtil.copyProperties(defaultPlan.getData(), PlanLoginVO.class);
            studentPublicRelation.setSemesterId(planLoginVO.getSemesterId());
            //负责人,分组
            ChargeRelationVO chargeRelationVO = familyMemberService.queryChargeRelation(planLoginVO.getSemesterId(),
                    studentPublicRelation.getSourceSchoolId());
            if (chargeRelationVO == null) {
                return Result.error(CodeMsg.GROUP_NO_LEADER_CHARGE);
            }
            studentPublicRelation.setGroupId(chargeRelationVO.getGroupId());
            studentPublicRelation.setCreatorId(chargeRelationVO.getApiId());
            //添加学生，加学生登记号
            Integer integer = studentPublicRelationService.queryStudentNo(planLoginVO.getRecruitSchoolId(),
                    planLoginVO.getSemesterId());
            if (null == integer) {
                studentPublicRelation.setStudentNo(1);
            } else {
                studentPublicRelation.setStudentNo(integer);
            }

            studentPublicRelationService.save(studentPublicRelation);
        }

        // 新建登录信息
        Login login = new Login();
        login.setOpenid(openid);
        login.setMobile(registerNewStudentQuery.getParentTelephone());
        login.setLoginType(registerNewStudentQuery.getLoginType());
        login.setCreateTime(now);
        baseMapper.insert(login);


        // 新建登录和学生关联表
        LoginRelation loginRelation = new LoginRelation();
        loginRelation.setLogin(login.getLogin());
        loginRelation.setStudentInfo(studentInfo.getStudentInfo());
        loginRelationService.save(loginRelation);
        return Result.success(login);
    }

    @Override
    public Result checkBindStudent(AddBindStudentQuery addBindStudentQuery, MiniProgramLoginVO loginVO) {
        log.info("checkBindStudent");
        Long studentInfo = loginVO.getCurrentStudent().getStudentInfo();
        StudentInfo loginStudent = studentInfoService.getById(studentInfo);
        if (null == loginStudent) {
            return Result.error(CodeMsg.STUDENT_INFO_UN_EXIST);
        }

        // 判断手机号和学生姓名是否存在
//        QueryWrapper<StudentInfo> studentWrapper = new QueryWrapper<>();
//        if(null != loginStudent.getParentTelephone()){
//            studentWrapper.eq(Constant.Talble.STUDENT_INFO_PARENT_TELEPHONE, loginStudent.getParentTelephone());
//        }
//        if(null != loginStudent.getStudentTelephone()){
//            studentWrapper.eq(Constant.Talble.STUDENT_INFO_STUDENT_TELEPHONE, loginStudent.getStudentTelephone());
//        }
//        studentWrapper.eq(Constant.Talble.STUDENT_INFO_STUDENT_NAME, addBindStudentQuery.getStudentName());
        StudentQuery studentQuery = new StudentQuery();
        studentQuery.setClassId(addBindStudentQuery.getClassId());
        studentQuery.setRecruitSchoolId(addBindStudentQuery.getRecruitSchoolId());
        studentQuery.setSourceSchoolId(addBindStudentQuery.getSourceSchoolId());
        studentQuery.setStudentName(addBindStudentQuery.getStudentName());
        String str = studentInfoService.queryStudentCount(studentQuery);
        if (null == str) {
            // 不存在该学生
            return Result.error(CodeMsg.WILL_CREATE_NEW_STUDENT);
        }

        // 判断该学生是否已经被登录用户绑定
        QueryWrapper<LoginRelation> loginRelationWrapper = new QueryWrapper<>();
        loginRelationWrapper.eq(Constant.Talble.LOGIN_RELATION_LOGIN, loginVO.getOpenidInfo().getLogin());
        loginRelationWrapper.eq(Constant.Talble.LOGIN_RELATION_STUDENT_INFO, str);
        LoginRelation loginRelation = loginRelationService.getOne(loginRelationWrapper);
        if (null == loginRelation) {
            // 未绑定
            return Result.error(CodeMsg.BIND_EXIST_STUDENT);
        }

        // 已绑定
        return Result.error(CodeMsg.STUDENT_HAS_BIND);
    }
}
