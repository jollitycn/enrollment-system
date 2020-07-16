package com.educiot.recruit.data.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.data.entity.Class;
import com.educiot.recruit.data.entity.*;
import com.educiot.recruit.data.entity.query.AddSourceSchoolQuery;
import com.educiot.recruit.data.entity.query.ListSourceSchoolPageQuery;
import com.educiot.recruit.data.entity.query.UpdateSourceSchoolQuery;
import com.educiot.recruit.data.entity.vo.*;
import com.educiot.recruit.data.mapper.*;
import com.educiot.recruit.data.service.IPlanService;
import com.educiot.recruit.data.service.ISourceSchoolService;
import com.google.common.collect.Lists;
import com.educiot.common.base.CodeMsg;
import com.educiot.common.base.Result;
import com.educiot.common.constant.Constant;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 生源学校 服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@Service
public class SourceSchoolServiceImpl extends ServiceImpl<SourceSchoolMapper, SourceSchool> implements ISourceSchoolService {

    @Resource
    private SourceSchoolTypeMapper sourceSchoolTypeMapper;

    @Resource
    private SysDictionaryMapper sysDictionaryMapper;

    @Resource
    private GroupSourceRelationMapper groupSourceRelationMapper;

    @Resource
    private ClassMapper classMapper;

    @Resource
    private AimStudentMapper aimStudentMapper;

    @Resource
    private StudentPublicRelationMapper studentPublicRelationMapper;

    @Resource
    private IPlanService planService;

    @Override
    public IPage<ListSourceSchoolVO> listSourceSchoolPage(ListSourceSchoolPageQuery pageQuery, Long recruitSchoolId) {
        IPage<ListSourceSchoolVO> page = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        // 获取招生学校id
        List<ListSourceSchoolVO> list = baseMapper.listGroupInfoPage(page, pageQuery, recruitSchoolId);
        page.setRecords(list);
        return page;
    }

    @Override
    public IPage<ListSourceSchoolVO> listSourceSchoolWeChat(ListSourceSchoolPageQuery pageQuery, Long recruitSchoolId) {
        IPage<ListSourceSchoolVO> page = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        // 获取招生学校id
        List<ListSourceSchoolVO> list = baseMapper.listGroupInfoWeChat(page, pageQuery, recruitSchoolId);
        page.setRecords(list);
        return page;
    }

    @Override
    public List<SysDictVO> listSourceSchoolType(Long recruitSchoolId) {
        List<SysDictVO> schoolTypeList = Lists.newArrayList();
        // 查询父级标签
        QueryWrapper<SysDictionary> parentDicWrapper = new QueryWrapper<>();
        parentDicWrapper.eq("data_code", Constant.Dictionary.SCHOOL_TAG);
        parentDicWrapper.eq("recruit_school_id", recruitSchoolId);
        parentDicWrapper.eq("is_deleted", Constant.SYS_ZERO);
        SysDictionary schoolTagDic = sysDictionaryMapper.selectOne(parentDicWrapper);
        if (null != schoolTagDic) {
            // 标签下的信息
            QueryWrapper<SysDictionary> tagWrapper = new QueryWrapper<>();
            tagWrapper.eq("parent_id", schoolTagDic.getDictionaryId());
            tagWrapper.eq("recruit_school_id", recruitSchoolId);
            tagWrapper.eq("is_deleted", Constant.SYS_ZERO);
            List<SysDictionary> schoolTypes = sysDictionaryMapper.selectList(tagWrapper);
            schoolTypes.forEach(sysDictionary -> {
                SysDictVO sysDictVO = new SysDictVO();
                BeanUtils.copyProperties(sysDictionary, sysDictVO);
                schoolTypeList.add(sysDictVO);
            });
        }
        return schoolTypeList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result addSourceSchool(AddSourceSchoolQuery sourceSchoolQuery, Long recruitSchoolId, String userName,
                                  Long userId) {
        // 生源学校信息入库
        SourceSchool sourceSchool = new SourceSchool();
        BeanUtils.copyProperties(sourceSchoolQuery, sourceSchool);
        // 获取登录用户的招生学校id
        sourceSchool.setRecruitSchoolId(recruitSchoolId);
        sourceSchool.setCreatorId(userId);
        sourceSchool.setCreatorName(userName);
        sourceSchool.setCreateTime(LocalDateTime.now());
        baseMapper.insert(sourceSchool);

        // 生源学校的学校类型关系建立（可多选）
        sourceSchoolQuery.getTypeIds().forEach(typeId -> {
            SourceSchoolType sourceSchoolType = new SourceSchoolType();
            sourceSchoolType.setSourceSchoolId(sourceSchool.getSourceSchoolId());
            sourceSchoolType.setTypeId(typeId);
            sourceSchoolTypeMapper.insert(sourceSchoolType);
        });

        return Result.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result updateSourceSchool(UpdateSourceSchoolQuery sourceSchoolQuery) {
        SourceSchool select = baseMapper.selectById(sourceSchoolQuery.getSourceSchoolId());
        if (null == select) {
            return Result.error(CodeMsg.SOURCE_SCHOOL_NOT_EXIST);
        }
        if (select.getStatus() == Constant.SYS_ZERO) {
            return Result.error(CodeMsg.SOURCE_SCHOOL_NOT_EXIST);
        }
        SourceSchool sourceSchool = new SourceSchool();
        BeanUtils.copyProperties(sourceSchoolQuery, sourceSchool);
        // 修改学校信息
        baseMapper.updateById(sourceSchool);

        // 生源学校的学校类型关系建立：先删除关系，再重新建立关系
        if (null != sourceSchoolQuery.getTypeIds() && !sourceSchoolQuery.getTypeIds().isEmpty()) {
            // 删除关联
            QueryWrapper<SourceSchoolType> typeWrapper = new QueryWrapper<>();
            typeWrapper.eq("source_school_id", sourceSchoolQuery.getSourceSchoolId());
            sourceSchoolTypeMapper.delete(typeWrapper);
            sourceSchoolQuery.getTypeIds().forEach(typeId -> {
                SourceSchoolType sourceSchoolType = new SourceSchoolType();
                sourceSchoolType.setSourceSchoolId(sourceSchool.getSourceSchoolId());
                sourceSchoolType.setTypeId(typeId);
                sourceSchoolTypeMapper.insert(sourceSchoolType);
            });
        }

        return Result.success();
    }

    @Override
    public SourceSchoolDetailVO getSourceSchoolDetail(Long sourceSchoolId) {
        SourceSchoolDetailVO sourceSchoolDetailVO = new SourceSchoolDetailVO();
        SourceSchool sourceSchool = baseMapper.selectById(sourceSchoolId);
        if (null == sourceSchool) {
            return sourceSchoolDetailVO;
        }
        BeanUtils.copyProperties(sourceSchool, sourceSchoolDetailVO);

        // 查询生源学校类型
        QueryWrapper<SourceSchoolType> typeWrapper = new QueryWrapper<>();
        typeWrapper.eq("source_school_id", sourceSchoolId);
        List<SourceSchoolType> schoolTypes = sourceSchoolTypeMapper.selectList(typeWrapper);
        List<Long> typeIds = schoolTypes.stream().map(SourceSchoolType::getTypeId).collect(Collectors.toList());
        List<SysDictVO> schoolTypeList = Lists.newArrayList();
        if (!typeIds.isEmpty()) {
            QueryWrapper<SysDictionary> dictionaryWrapper = new QueryWrapper<>();
            dictionaryWrapper.in("dictionary_id", typeIds);
            List<SysDictionary> dictionaryList = sysDictionaryMapper.selectList(dictionaryWrapper);
            dictionaryList.forEach(sysDictionary -> {
                SysDictVO sysDictVO = new SysDictVO();
                BeanUtils.copyProperties(sysDictionary, sysDictVO);
                schoolTypeList.add(sysDictVO);
            });
        }
        sourceSchoolDetailVO.setSchoolTypeList(schoolTypeList);

        return sourceSchoolDetailVO;
    }

    @Override
    public Result deleteSourceSchool(Long sourceSchoolId) {
        // 判断生源学校是否存在
        SourceSchool sourceSchool = baseMapper.selectById(sourceSchoolId);
        if (null == sourceSchool) {
            return Result.error(CodeMsg.SOURCE_SCHOOL_NOT_EXIST);
        }
        if (sourceSchool.getStatus() == Constant.SYS_ZERO) {
            return Result.error(CodeMsg.SOURCE_SCHOOL_NOT_EXIST);
        }
        // 查询学校是否有关联分组
        QueryWrapper<GroupSourceRelation> groupSourceRelationWrapper = new QueryWrapper<>();
        groupSourceRelationWrapper.eq("source_school_id", sourceSchoolId);
        Integer count = groupSourceRelationMapper.selectCount(groupSourceRelationWrapper);
        if (count > 0) {
            return Result.error(CodeMsg.SCHOOL_HAS_RELATION_GROUP);
        }
        sourceSchool.setStatus(Constant.SYS_ZERO);
        baseMapper.updateById(sourceSchool);
        return Result.success();
    }

    @Override
    public List<SourceSchoolVO> listAllSourceSchool(Long recruitSchoolId) {
        //根据招生学校查询默认计划的学期
        PlanVO planVO = planService.queryDefaultPlanInfoBySchool(recruitSchoolId);
        if(null == planVO){
            return null;
        }
        // 当前登录招生学校id
        return baseMapper.listSourceSchoolByRecruitSchoolId(recruitSchoolId,planVO.getSemesterId());
    }

    @Override
    public List<SourceSchoolVO> listSourceSchoolByRecruitSchoolIdAndGroupId(Long recruitSchoolId, Long groupId,Long chargerId,Long semesterId) {
        return baseMapper.listSourceSchoolByRecruitSchoolIdAndGroupId(recruitSchoolId,groupId,chargerId,semesterId);
    }

    @Override
    public List<CompleteRateVO> listCompleteRate(Long groupId,Long currentSemesterId) {
        List<CompleteRateVO> list = Lists.newArrayList();
        List<SourceSchool> sourceSchoolList = baseMapper.listCompleteRate(groupId,currentSemesterId);
        sourceSchoolList.forEach(sourceSchool -> {
            //   完成度：
            //1、维护了学校的信息，完成度+20%
            //2、增加学校班级，完成度+20%
            //3、增加班主任信息，完成度+20%
            //4、增加每个班级的预计人数，完成度+20%
            //5、增加预报名学生人信息，完成度+20%
            CompleteRateVO completeRateVO = new CompleteRateVO();
            completeRateVO.setSourceSchoolId(sourceSchool.getSourceSchoolId());
            completeRateVO.setSourceSchoolName(sourceSchool.getSourceSchoolName());
            completeRateVO.setCompleteRate(0.00F);

            // 学校信息
            if (null != sourceSchool.getRelationLevel() ||
                null != sourceSchool.getRecruitAmount() || null != sourceSchool.getRecruitWay() ||
                null != sourceSchool.getGradeDirectorName() || null != sourceSchool.getGradeDirectorTel() ||
                null != sourceSchool.getClassAmount() ||  null != sourceSchool.getSchoolCaseComment()) {
                completeRateVO.setCompleteRate(completeRateVO.getCompleteRate() + 0.20F);
            }
            // 班主任信息
            if (StrUtil.isNotBlank(sourceSchool.getGradeDirectorName())) {
                completeRateVO.setCompleteRate(completeRateVO.getCompleteRate() + 0.20F);
            }
            // 班级信息
            QueryWrapper<Class> classWrapper = new QueryWrapper<>();
            classWrapper.eq("source_school_id", sourceSchool.getSourceSchoolId());
            classWrapper.eq("semester_id", currentSemesterId);
            List<Class> classList = classMapper.selectList(classWrapper);
            if (!classList.isEmpty()) {
                completeRateVO.setCompleteRate(completeRateVO.getCompleteRate() + 0.20F);
                boolean flag = true;
                for (Class c : classList) {
                    if (null == c.getAmount() || c.getAmount() <= 0) {
                        flag = false;
                        break;
                    }
                }
                // 班级人数
                if (flag) {
                    completeRateVO.setCompleteRate(completeRateVO.getCompleteRate() + 0.20F);
                }
                // 预报名学生
                List<Long> classIds = classList.stream().map(Class::getClassId).collect(Collectors.toList());
                QueryWrapper<AimStudent> aimStudentWrapper = new QueryWrapper<>();
                aimStudentWrapper.in("class_id", classIds);
                Integer aimCount = aimStudentMapper.selectCount(aimStudentWrapper);
                if (aimCount > 0) {
                    completeRateVO.setCompleteRate(completeRateVO.getCompleteRate() + 0.20F);
                } else {
                    QueryWrapper<StudentPublicRelation> publicWrapper = new QueryWrapper<>();
                    publicWrapper.in("class_id", classIds);
                    Integer publicCount = studentPublicRelationMapper.selectCount(publicWrapper);
                    if (publicCount > 0) {
                        completeRateVO.setCompleteRate(completeRateVO.getCompleteRate() + 0.20F);
                    }
                }
            }
            list.add(completeRateVO);
        });
        return list;
    }
}
