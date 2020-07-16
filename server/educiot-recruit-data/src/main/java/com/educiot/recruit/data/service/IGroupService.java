package com.educiot.recruit.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.entity.Group;
import com.educiot.recruit.data.entity.query.*;
import com.educiot.recruit.data.entity.vo.GroupDetailVO;
import com.educiot.recruit.data.entity.vo.GroupVO;
import com.educiot.recruit.data.entity.vo.SourceSchoolVO;
import com.educiot.recruit.data.entity.vo.SysDictVO;

import java.util.List;

/**
 * <p>
 * 招生组表 服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface IGroupService extends IService<Group> {

    /**
     * 分页查询招生组列表信息
     *
     * @param pageQuery 分页参数和查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage&lt;com.educiot.recruit.data.entity.vo.GroupVO&gt;
     * @author Pan Juncai
     * @date 2020/4/10 10:29
     */
    IPage<GroupVO> listGroupPage(ListGroupPageQuery pageQuery);

    /**
     * 获取字典里配置的招生区域
     *
     * @param recruitSchoolId 招生学校id
     * @return java.util.List&lt;com.educiot.recruit.data.entity.vo.SysDictVO&gt;
     * @author Pan Juncai
     * @date 2020/4/16 13:54
     */
    List<SysDictVO> listRecruitZone(Long recruitSchoolId);

    /**
     * 添加分组信息
     *
     * @param addGroupQuery 分组信息
     * @param loginUserId   登录用户id
     * @param loginUserName 登录用户名称
     * @return com.insi.educiot.recruit.common.base.Result
     * @author Pan Juncai
     * @date 2020/4/10 10:47
     */
    Result addGroup(AddGroupQuery addGroupQuery, Long loginUserId, String loginUserName,Long recruitSchoolId, Long semesterId);

    /**
     * 修改分组信息
     *
     * @param editGroupQuery 要修改的分组信息
     * @param currentSchoolId
     * @return com.insi.educiot.recruit.common.base.Result
     * @author Pan Juncai
     * @date 2020/4/10 10:56
     */
    Result updateGroup(UpdateGroupQuery editGroupQuery, Long currentSchoolId);

    /**
     * 根据分组id删除分组（修改状态）
     *
     * @param groupId 要删除的分组ID
     * @return com.insi.educiot.recruit.common.base.Result
     * @author Pan Juncai
     * @date 2020/4/10 11:24
     */
    Result deleteGroup(Long groupId);

    /**
     * 给指定分组添加负责人
     *
     * @param chargerQuery 负责人信息
     * @return com.insi.educiot.recruit.common.base.Result
     * @author Pan Juncai
     * @date 2020/4/16 9:51
     */
    Result addCharger(Long semesterId, ChargerQuery chargerQuery);

    /**
     * 删除分组的负责人（修改状态）
     *
     * @param chargerId 负责人id
     * @return com.insi.educiot.recruit.common.base.Result
     * @author Pan Juncai
     * @date 2020/4/16 10:18
     */
    Result deleteCharger(Long chargerId);


    /**
     * @param recruitSchoolId
     * @param semesterId
     * @Description: 根据学校id和学期id查询招生区域信息
     * @return:Group
     * @Author: XuChao
     * @Date: 2020/4/15
     */
    List<Group> getTargetUnderLineData(Long recruitSchoolId, Long semesterId);

    /**
     * 根据分组id获取分组详情信息
     *
     * @param groupId 分组ID
     * @return com.educiot.recruit.data.entity.vo.GroupDetailVO
     * @author Pan Juncai
     * @date 2020/4/15 16:43
     */
    GroupDetailVO getGroupDetail(Long groupId);

    /**
     * 获取当前招生学校创建的生源学校，再剔除已经制定分组groupId关联的学校
     *
     * @param groupId         分组ID
     * @param recruitSchoolId 招生学校id
     * @return java.util.List&lt;com.educiot.recruit.data.entity.vo.SourceSchoolVO&gt;
     * @author Pan Juncai
     * @date 2020/4/15 17:50
     */
    List<SourceSchoolVO> listSourceSchool(Long groupId, Long recruitSchoolId);

    /**
     * 分组新增负责学校
     *
     * @param groupSourceRelationQuery 关联关系
     * @return com.insi.educiot.recruit.common.base.Result
     * @author Pan Juncai
     * @date 2020/4/15 18:17
     */
    Result addGroupSourceRelation(GroupSourceRelationQuery groupSourceRelationQuery);

    /**
     * 分组删除负责学校
     *
     * @param groupSourceRelationQuery 关联关系
     * @return com.insi.educiot.recruit.common.base.Result
     * @author Pan Juncai
     * @date 2020/4/15 18:17
     */
    Result deleteGroupSourceRelation(GroupSourceRelationQuery groupSourceRelationQuery);
}
