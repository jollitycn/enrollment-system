package com.educiot.recruit.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.common.constant.Constant;
import com.educiot.recruit.common.constant.LeaderFlagEnum;
import com.educiot.recruit.common.constant.StatusEnum;
import com.educiot.recruit.data.entity.*;
import com.educiot.recruit.data.entity.query.*;
import com.educiot.recruit.data.entity.vo.GroupDetailVO;
import com.educiot.recruit.data.entity.vo.GroupVO;
import com.educiot.recruit.data.entity.vo.SourceSchoolVO;
import com.educiot.recruit.data.entity.vo.SysDictVO;
import com.educiot.recruit.data.mapper.*;
import com.educiot.recruit.data.service.IGroupService;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 招生组表 服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements IGroupService {

    @Resource
    private ChargerMapper chargerMapper;

    @Resource
    private GroupSourceRelationMapper groupSourceRelationMapper;

    @Resource
    private SourceSchoolMapper sourceSchoolMapper;

    @Resource
    private SysDictionaryMapper sysDictionaryMapper;

    @Resource
    private AimStudentMapper aimStudentMapper;

    @Override
    public IPage<GroupVO> listGroupPage(ListGroupPageQuery pageQuery) {
        IPage<GroupVO> page = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        List<GroupVO> list = baseMapper.listGroupPage(page, pageQuery);

        // 统计负责人数量和生源学校数量
        list.forEach(groupVO -> {
            // 负责人
            QueryWrapper<Charger> chargerWrapper = new QueryWrapper<>();
            chargerWrapper.eq("group_id", groupVO.getGroupId());
            chargerWrapper.eq("status", Constant.SYS_ONE);
            groupVO.setCountManager(chargerMapper.selectCount(chargerWrapper));

            // 生源学校
            QueryWrapper<GroupSourceRelation> groupSourceWrapper = new QueryWrapper<>();
            groupSourceWrapper.eq("group_id", groupVO.getGroupId());
            groupVO.setCountSourceSchool(groupSourceRelationMapper.selectCount(groupSourceWrapper));
        });

        page.setRecords(list);
        return page;
    }

    @Override
    public List<SysDictVO> listRecruitZone(Long recruitSchoolId) {
        List<SysDictVO> schoolTypeList = Lists.newArrayList();
        // 查询父级标签
        QueryWrapper<SysDictionary> parentDicWrapper = new QueryWrapper<>();
        parentDicWrapper.eq("is_deleted", Constant.SYS_ZERO);
        parentDicWrapper.eq("recruit_school_id", recruitSchoolId);
        parentDicWrapper.eq("data_code", Constant.Dictionary.RECRUIT_ZONE);
        SysDictionary schoolTagDic = sysDictionaryMapper.selectOne(parentDicWrapper);
        if (null != schoolTagDic) {
            // 标签下的信息
            QueryWrapper<SysDictionary> tagWrapper = new QueryWrapper<>();
            tagWrapper.eq("is_deleted", Constant.SYS_ZERO);
            tagWrapper.eq("recruit_school_id", recruitSchoolId);
            tagWrapper.eq("parent_id", schoolTagDic.getDictionaryId());
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
    public Result addGroup(AddGroupQuery addGroupQuery, Long loginUserId, String loginUserName, Long recruitSchoolId, Long semesterId) {
        //判断不能添加重复的分组（名称）
        Integer integer = baseMapper.queryGroupCount(recruitSchoolId, semesterId, addGroupQuery.getName());
        if (integer > 0) {
            return Result.error(CodeMsg.GROUP_IS_EXIST);
        }
        LocalDateTime now = LocalDateTime.now();
        Group group = new Group();
        BeanUtils.copyProperties(addGroupQuery, group);
        group.setCreateTime(now);
        // 创建人信息从登陆信息中获取
        group.setCreatorId(loginUserId);
        group.setCreatorName(loginUserName);
        baseMapper.insert(group);
        return Result.success();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result updateGroup(UpdateGroupQuery editGroupQuery, Long currentSchoolId) {
        // 判断分组是否存在
        QueryWrapper<Group> groupWrapper = new QueryWrapper<>();
        groupWrapper.eq("group_id", editGroupQuery.getGroupId());
        groupWrapper.eq("status", Constant.SYS_ONE);
        Group selectGroup = baseMapper.selectOne(groupWrapper);
        if (null == selectGroup) {
            return Result.error(CodeMsg.GROUP_NOT_EXIST);
        }

        //判断不能添加重复的分组（名称）
        Integer integer = baseMapper.queryGroupCount(currentSchoolId, editGroupQuery.getSemesterId(), editGroupQuery.getName());
        if (integer > 0) {
            return Result.error(CodeMsg.GROUP_IS_EXIST);
        }

        Group group = new Group();
        BeanUtils.copyProperties(editGroupQuery, group);
        baseMapper.updateById(group);
        return Result.success();
    }

    @Override
    public Result deleteGroup(Long groupId) {
        // 判断分组是否存在
        Group group = baseMapper.selectById(groupId);
        if (null == group) {
            return Result.error(CodeMsg.GROUP_NOT_EXIST);
        }
        if (group.getStatus() == Constant.SYS_ZERO) {
            return Result.error(CodeMsg.GROUP_NOT_EXIST);
        }

        // 判断分组下是否还关联了负责人和生源学校
        // 负责人
        QueryWrapper<Charger> chargerWrapper = new QueryWrapper<>();
        chargerWrapper.eq("group_id", groupId);
        chargerWrapper.eq("status", Constant.SYS_ONE);
        Integer countCharger = chargerMapper.selectCount(chargerWrapper);
        if (countCharger > 0) {
            return Result.error(CodeMsg.CHARGER_HAS_RELATION_GROUP);
        }

        // 生源学校
        QueryWrapper<GroupSourceRelation> groupSourceWrapper = new QueryWrapper<>();
        groupSourceWrapper.eq("group_id", groupId);
        Integer countSchool = groupSourceRelationMapper.selectCount(groupSourceWrapper);
        if (countSchool > 0) {
            return Result.error(CodeMsg.SCHOOL_HAS_RELATION_GROUP);
        }

        group.setStatus(Constant.SYS_ZERO);
        baseMapper.updateById(group);
        return Result.success();
    }

    @Override
    public Result addCharger(Long semesterId, ChargerQuery chargerQuery) {
        // 查询是否已经关联
        QueryWrapper<Charger> chargerWrapper = new QueryWrapper<>();
//        chargerWrapper.eq("group_id", chargerQuery.getGroupId());
        chargerWrapper.eq("api_id", chargerQuery.getApiId());
        chargerWrapper.eq("status", StatusEnum.NORMAL.getStatus());
//        chargerWrapper.eq("group_id", chargerQuery.getGroupId());
//        int chargerCount = chargerMapper.selectCount(chargerWrapper);
        int chargerCount = chargerMapper.getChargerCount(chargerQuery.getApiId(), semesterId);
        if (chargerCount > 0) {
            return Result.error(CodeMsg.CHARGE_HAS_BIND_GROUP);
        }
//        ChargerExt count = chargerMapper.getByApiId(chargerQuery.getApiId(), semesterId, StatusEnum.NORMAL.getStatus());
//        if (count != null) {
//            return Result.error(CodeMsg.CHARGE_HAS_BIND_GROUP);
//        }

        if (LeaderFlagEnum.LEADER.getStatus().equals(chargerQuery.getIsLeader())) {
            QueryWrapper<Charger> chargerLeaderWrapper = new QueryWrapper<>();
            chargerLeaderWrapper.eq("group_id", chargerQuery.getGroupId());
            chargerLeaderWrapper.eq("status", StatusEnum.NORMAL.getStatus());
            chargerLeaderWrapper.eq("is_leader", Constant.SYS_ONE);
            Integer countLeader = chargerMapper.selectCount(chargerLeaderWrapper);
            if (countLeader > 0) {
                return Result.error(CodeMsg.GROUP_HAS_LEADER);
            }
        }

        Charger charger = new Charger();
        BeanUtils.copyProperties(chargerQuery, charger);
        charger.setStatus(StatusEnum.NORMAL.getStatus());
        chargerMapper.insert(charger);

        return Result.success();
    }

    @Override
    public Result deleteCharger(Long chargerId) {
        //判断如果数据有关联就不能删除
        QueryWrapper<AimStudent> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("creator_id",chargerId);
        Integer integer = aimStudentMapper.selectCount(queryWrapper);
        if(integer > 0){
            return Result.error(CodeMsg.DIC_DELETE_ERROR_SOURCE_SCHOOL);
        }
        Charger charger = new Charger();
        charger.setChargerId(chargerId);
        charger.setStatus(StatusEnum.IS_DELETE.getStatus());
        chargerMapper.updateById(charger);
        return Result.success();
    }

    /**
     * @param recruitSchoolId
     * @param semesterId
     * @Description: 根据学校id和学期id查询招生区域信息
     * @return:Group
     * @Author: XuChao
     * @Date: 2020/4/15
     */
    @Override
    public List<Group> getTargetUnderLineData(Long recruitSchoolId, Long semesterId) {
        return baseMapper.getTargetUnderLineData(recruitSchoolId, semesterId);
    }

    @Override
    public GroupDetailVO getGroupDetail(Long groupId) {
        GroupDetailVO groupDetailVO = new GroupDetailVO();
        Group group = baseMapper.selectById(groupId);
        if (null == group) {
            return groupDetailVO;
        }
        BeanUtils.copyProperties(group, groupDetailVO);

        // 查询负责人信息
        groupDetailVO.setChargerList(chargerMapper.listChargerByGroupId(groupId));

        // 查询负责学校（生源学校）信息
        QueryWrapper<GroupSourceRelation> sourceRelationWrapper = new QueryWrapper<>();
        sourceRelationWrapper.eq("group_id", groupId);
        List<GroupSourceRelation> sourceRelations = groupSourceRelationMapper.selectList(sourceRelationWrapper);
        List<Long> sourceSchoolIds =
                sourceRelations.stream().map(GroupSourceRelation::getSourceSchoolId).collect(Collectors.toList());
        if (!sourceSchoolIds.isEmpty()) {
            groupDetailVO.setSourceSchoolList(sourceSchoolMapper.listSourceSchoolByIds(sourceSchoolIds));
        }

        return groupDetailVO;
    }

    @Override
    public List<SourceSchoolVO> listSourceSchool(Long groupId, Long recruitSchoolId) {
        List<SourceSchoolVO> list = Lists.newArrayList();
        QueryWrapper<SourceSchool> sourceSchoolWrapper = new QueryWrapper<>();
        // 获取当前登录的招生学校id
        sourceSchoolWrapper.eq("recruit_school_id", recruitSchoolId);
        sourceSchoolWrapper.eq("status", StatusEnum.NORMAL.getStatus());
        List<SourceSchool> sourceSchools = sourceSchoolMapper.selectList(sourceSchoolWrapper);
        sourceSchools.forEach(sourceSchool -> {
            QueryWrapper<GroupSourceRelation> relationWrapper = new QueryWrapper<>();
            relationWrapper.eq("group_id", groupId);
            relationWrapper.eq("source_school_id", sourceSchool.getSourceSchoolId());
            Integer count = groupSourceRelationMapper.selectCount(relationWrapper);
            // 自身已经关联的剔除
            if (count == 0) {
                SourceSchoolVO sourceSchoolVO = new SourceSchoolVO();
                BeanUtils.copyProperties(sourceSchool, sourceSchoolVO);
                list.add(sourceSchoolVO);
            }
        });
        return list;
    }

    @Override
    public Result addGroupSourceRelation(GroupSourceRelationQuery groupSourceRelationQuery) {
        // 查询是否已经关联
        QueryWrapper<GroupSourceRelation> relationWrapper = new QueryWrapper<>();

        //一个生源学校只能有一个组
//        relationWrapper.eq("group_id", groupSourceRelationQuery.getGroupId());
        relationWrapper.eq("source_school_id", groupSourceRelationQuery.getSourceSchoolId());
        relationWrapper.eq("semester_id", groupSourceRelationQuery.getSemesterId());

        Integer count = groupSourceRelationMapper.selectCount(relationWrapper);
        if (count > 0) {
            return Result.error(CodeMsg.SOURCE_SCHOOL_REPEAT);
        }

        GroupSourceRelation groupSourceRelation = new GroupSourceRelation();
        BeanUtils.copyProperties(groupSourceRelationQuery, groupSourceRelation);
        groupSourceRelationMapper.insert(groupSourceRelation);
        return Result.success();
    }

    @Override
    public Result deleteGroupSourceRelation(GroupSourceRelationQuery groupSourceRelationQuery) {
        QueryWrapper<GroupSourceRelation> relationWrapper = new QueryWrapper<>();
        relationWrapper.eq("group_id", groupSourceRelationQuery.getGroupId());
        relationWrapper.eq("source_school_id", groupSourceRelationQuery.getSourceSchoolId());
        groupSourceRelationMapper.delete(relationWrapper);
        return Result.success();
    }


}
