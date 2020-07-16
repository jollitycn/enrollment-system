package com.educiot.recruit.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.common.base.BasePageQuery;
import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.LoginUser;
import com.educiot.recruit.data.entity.Charger;
import com.educiot.recruit.data.entity.Group;
import com.educiot.recruit.data.entity.GroupInfo;
import com.educiot.recruit.data.entity.GroupSourceRelation;
import com.educiot.recruit.data.entity.query.AddGroupInfoQuery;
import com.educiot.recruit.data.entity.query.UpdateGroupInfoQuery;
import com.educiot.recruit.data.entity.vo.ListGroupInfoPageVO;
import com.educiot.recruit.data.mapper.GroupInfoMapper;
import com.educiot.recruit.data.service.IChargerService;
import com.educiot.recruit.data.service.IGroupInfoService;
import com.educiot.recruit.data.service.IGroupService;
import com.educiot.recruit.data.service.IGroupSourceRelationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 招生组信息表 服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Service
public class GroupInfoServiceImpl extends ServiceImpl<GroupInfoMapper, GroupInfo> implements IGroupInfoService {

    @Resource
    private IGroupService groupService;
    
    @Resource
    private IGroupSourceRelationService groupSourceRelationService;

    @Resource
    private IChargerService chargerService;

    @Override
    public IPage<ListGroupInfoPageVO> listGroupInfoPage(BasePageQuery pageQuery, Long recruitSchoolId) {
        IPage<ListGroupInfoPageVO> page = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        List<ListGroupInfoPageVO> list = baseMapper.listGroupInfoPage(page, recruitSchoolId);
        page.setRecords(list);
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result addGroupInfo(AddGroupInfoQuery groupInfo, LoginUser loginUser) {
        // 校验学期是否已经存在
        QueryWrapper<GroupInfo> groupInfoWrapper = new QueryWrapper<>();
        groupInfoWrapper.eq("recruit_school_id", loginUser.getCurrentSchoolId());
        groupInfoWrapper.eq("semester", groupInfo.getSemester());
        Integer count = baseMapper.selectCount(groupInfoWrapper);
        if (count > 0) {
            return Result.error(CodeMsg.YEAR_OR_SEMESTER_EXIST_GROUP);
        }

        GroupInfo insert = new GroupInfo();
        BeanUtils.copyProperties(groupInfo, insert);

        // 获取登录账号信息
        insert.setCreatorId(loginUser.getApiInfo().getUserId());
        insert.setCreatorName(loginUser.getApiInfo().getUserName());
        insert.setCreateTime(LocalDateTime.now());
        insert.setRecruitSchoolId(loginUser.getCurrentSchoolId());

        baseMapper.insert(insert);


        return Result.success();
    }

    @Override
    public Result copyGroupInfo(UpdateGroupInfoQuery groupInfo, Long recruitSchoolId) {

        //判断学期是否重复
        QueryWrapper<GroupInfo> groupInfoQueryWrapper = new QueryWrapper<>();
        groupInfoQueryWrapper.eq("recruit_school_id", recruitSchoolId);
        groupInfoQueryWrapper.eq("semester_id", groupInfo.getSemesterId());
        int count = count(groupInfoQueryWrapper);
        if (count > 0) {
            return Result.error(CodeMsg.YEAR_OR_SEMESTER_EXIST_GROUP);
        }

        //保存区域信息
        GroupInfo groupInfo1 = baseMapper.selectById(groupInfo.getGroupInfoId());
        groupInfo1.setSemester(groupInfo.getSemester());
        groupInfo1.setSemesterId(groupInfo.getSemesterId());
        groupInfo1.setCreateTime(LocalDateTime.now());
        groupInfo1.setGroupInfoId(null);
        baseMapper.insert(groupInfo1);
        //保存小组信息
        QueryWrapper<Group> groupQueryWrapper=new QueryWrapper<>();
        groupQueryWrapper.eq("group_info_id",groupInfo.getGroupInfoId());
        List<Group> groups = groupService.getBaseMapper().selectList(groupQueryWrapper);
        groups.forEach(group->{
            //生成groupId
            Long uuid = IdWorker.getId();
            //保存小组和学校的关系
            List<GroupSourceRelation> groupList = findGroupList(group.getGroupId());
            groupList.forEach(groupSourceRelation -> {
                groupSourceRelation.setGroupSourceRelationId(null);
                groupSourceRelation.setGroupId(uuid);
                groupSourceRelation.setSemesterId(groupInfo.getSemesterId());
                groupSourceRelationService.save(groupSourceRelation);
            });
            //保存小组和负责人的关系
            List<Charger> chargerList = findChargerList(group.getGroupId());
            chargerList.forEach(charger -> {
                charger.setGroupId(uuid);
                charger.setChargerId(null);
                chargerService.save(charger);
            });
            group.setGroupInfoId(groupInfo1.getGroupInfoId());
            group.setGroupId(uuid);
            groupService.save(group);
        });
        return Result.success();
    }

    public List<GroupSourceRelation> findGroupList(Long groupId){
        QueryWrapper<GroupSourceRelation> groupQueryWrapper=new QueryWrapper<>();
        groupQueryWrapper.eq("group_id",groupId);
        List<GroupSourceRelation> groupSourceRelations = groupSourceRelationService.getBaseMapper().selectList(groupQueryWrapper);
        return groupSourceRelations;
    }

    public List<Charger> findChargerList(Long groupId){
        QueryWrapper<Charger> groupQueryWrapper=new QueryWrapper<>();
        groupQueryWrapper.eq("group_id",groupId);
        List<Charger> chargers = chargerService.getBaseMapper().selectList(groupQueryWrapper);
        return chargers;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result updateGroupInfo(UpdateGroupInfoQuery groupInfo, Long recruitSchoolId) {
        // 查询该区域是否存在
        GroupInfo select = baseMapper.selectById(groupInfo.getGroupInfoId());
        if (null == select) {
            return Result.error(CodeMsg.GROUP_INFO_NOT_EXIST);
        }

        // 判断学期是否唯一
        QueryWrapper<GroupInfo> groupInfoWrapper = new QueryWrapper<>();
        groupInfoWrapper.eq("semester", groupInfo.getSemester());
        groupInfoWrapper.notIn("group_info_id", groupInfo.getGroupInfoId());
        groupInfoWrapper.eq("recruit_school_id", recruitSchoolId);
        Integer count = baseMapper.selectCount(groupInfoWrapper);
        if (count > 0) {
            return Result.error(CodeMsg.YEAR_OR_SEMESTER_EXIST);
        }

        GroupInfo update = new GroupInfo();
        BeanUtils.copyProperties(groupInfo, update);
        baseMapper.updateById(update);
        return Result.success();
    }

    @Override
    public ListGroupInfoPageVO getGroupInfo(Long groupInfoId, Long recruitSchoolId) {
        ListGroupInfoPageVO groupInfoVO = new ListGroupInfoPageVO();
        GroupInfo groupInfo = baseMapper.selectById(groupInfoId);
        if (null != groupInfo && recruitSchoolId.equals(groupInfo.getRecruitSchoolId())) {
            BeanUtils.copyProperties(groupInfo, groupInfoVO);
        }
        return groupInfoVO;
    }
}
