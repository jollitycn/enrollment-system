package com.educiot.recruit.data.service.student;

import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.data.entity.RecruitWay;
import com.educiot.recruit.common.base.Result;

/**
 * <p>
 * 学生招生渠道表 服务类
 * </p>
 *
 * @author Mr.AI
 * @since 2020-04-13
 */
public interface IRecruitWayService extends IService<RecruitWay> {

    /**
     * 添加或修改学生招生渠道信息
     * @param recruitWay 添加或编辑学生招生渠道入参实体
     * @return 返回添加或编辑是否成功状态
     */
    Result saveOrUpdateRecruitWayInfo(RecruitWay recruitWay);

    /**
     * 根据学生公关ID查询学生招生渠道信息
     * @param studentInfoId 学生招生渠道Id
     * @return 返回招生渠道信息
     */
    RecruitWay queryRecruitWayInfo(Long studentInfoId);

}
