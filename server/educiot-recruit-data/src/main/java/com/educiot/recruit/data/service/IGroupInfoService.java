package com.educiot.recruit.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.common.base.BasePageQuery;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.LoginUser;
import com.educiot.recruit.data.entity.GroupInfo;
import com.educiot.recruit.data.entity.query.AddGroupInfoQuery;
import com.educiot.recruit.data.entity.query.UpdateGroupInfoQuery;
import com.educiot.recruit.data.entity.vo.ListGroupInfoPageVO;

/**
 * <p>
 * 招生组信息表 服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface IGroupInfoService extends IService<GroupInfo> {

    /**
     * 招生区域列表查询，查询的是当前登录的招生学校下的区域列表
     *
     * @param pageQuery       分页信息
     * @param recruitSchoolId 当前登录人的招生学校id
     * @return com.baomidou.mybatisplus.core.metadata.IPage&lt;com.educiot.recruit.data.entity.vo
     * .ListGroupInfoPageVO&gt;
     * @author Pan Juncai
     * @date 2020/4/13 15:00
     */
    IPage<ListGroupInfoPageVO> listGroupInfoPage(BasePageQuery pageQuery, Long recruitSchoolId);

    /**
     * 新增招生区域的信息
     *
     * @param groupInfo 区域信息
     * @return com.insi.educiot.recruit.common.base.Result
     * @author Pan Juncai
     * @date 2020/4/13 15:25
     */
    Result addGroupInfo(AddGroupInfoQuery groupInfo, LoginUser loginUser);

    /**
     * 复制招生区域信息
     * @param groupInfo
     * @param recruitSchoolId
     * @return
     */
    Result copyGroupInfo(UpdateGroupInfoQuery groupInfo, Long recruitSchoolId);

    /**
     * 修改招生区域的信息
     *
     * @param groupInfo       区域信息
     * @param recruitSchoolId 登录的招生学校id
     * @return com.insi.educiot.recruit.common.base.Result
     * @author Pan Juncai
     * @date 2020/4/13 15:38
     */
    Result updateGroupInfo(UpdateGroupInfoQuery groupInfo, Long recruitSchoolId);

    /**
     * 根据指定区域ID和招生学校的区域信息
     *
     * @param groupInfoId     区域ID
     * @param recruitSchoolId 招生学校ID
     * @return com.educiot.recruit.data.entity.vo.ListGroupInfoPageVO
     * @author Pan Juncai
     * @date 2020/4/15 15:04
     */
    ListGroupInfoPageVO getGroupInfo(Long groupInfoId, Long recruitSchoolId);
}
