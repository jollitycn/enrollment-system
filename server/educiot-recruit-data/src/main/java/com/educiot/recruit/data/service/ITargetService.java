package com.educiot.recruit.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.common.api.APIRequest;
import com.educiot.recruit.data.entity.Target;
import com.educiot.recruit.data.entity.query.ListTargetPageQuery;
import com.educiot.recruit.data.entity.request.AddChannelOrMajorReq;
import com.educiot.recruit.data.entity.request.AddOrEditTargetReq;
import com.educiot.recruit.data.entity.vo.TargetDetailVO;
import com.educiot.recruit.data.entity.vo.TargetVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 目标 服务类
 * </p>
 *
 * @author Xuchao
 * @since 2020-04-13
 */
public interface ITargetService extends IService<Target> {
    TargetDetailVO beforeGetDetail(Long targetId, Long recruitSchoolId);

    /**
     * 分页获取招生目标列表
     *
     * @param param 分页参数封装
     * @return 返回目标列表分页数据
     */
    IPage<TargetVO> getTargetListPage(ListTargetPageQuery param, Long schoolId );

    /**
     * 添加或编辑招生目标
     *
     * @param addOrEditTargerReq 编辑招生目标参数封装
     * @return targetId
     * @throws Exception 初始化专业和渠道失败则会抛出此异常
     */
    Long addOrEditTarget(AddOrEditTargetReq addOrEditTargerReq,  Long recruitSchoolId,Long schoolId,Long userId,String userName, APIRequest apiRequest) throws Exception;

    /**
     * 获取招生目标详情
     *
     * @param targetId 招生目标主键id
     * @return
     */
    TargetDetailVO getDetail(Long targetId);


    /**
     * 修改渠道/专业节点信息
     * @param addChannelOrMajorReq 修改渠道/专业信息节点请求参数封装类
     * @return true 修改成功
     */
    Boolean editChannelOrMajorNode(AddChannelOrMajorReq addChannelOrMajorReq);


    /**
     * 根据学校id和学期id获取招生目标，存在即返回目标对象，不存在返回空
     * @param schoolId
     * @param semesterId
     * @return
     */
    Target checkTarget(Long schoolId, Long semesterId);

    /**
     * 查询招生渠道目标名称
     * @return
     */
    List<String> queryNodeNameList(Long schoolId, Long semesterId);

}
