package com.educiot.recruit.data.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.recruit.data.entity.AimStudent;
import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.data.entity.query.AddAimStudentQuery;
import com.educiot.recruit.data.entity.query.ListAimStudentPageQuery;
import com.educiot.recruit.data.entity.vo.AimStudentVO;

/**
 * <p>
 * 目标生表 服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
public interface IAimStudentService extends IService<AimStudent> {

    /**
     * 分页查询目标生
     * @param param 班级Id和分页参数
     * @return 分页后的目标生列表
     */
    IPage<AimStudent> listAimStudentPage(ListAimStudentPageQuery param);

    /**
     * 查询目标生数量
     * @param param 查询条件
     * @return 目标生数量
     */
    int listAimStudentCount(ListAimStudentPageQuery param);

    /**
     * 添加或编辑目标生
     * @param addAimStudentQuery 添加目标生封装实体参数
     * @return 添加或编辑成功装填
     */
    Boolean saveOrUpdateAimStudentInfo(AddAimStudentQuery addAimStudentQuery);

    /**
     * 获取目标生详情信息
     * @param aimStudentId 学生ID
     * @return 目标生信息
     */
    AimStudentVO queryAimStudentInfo(Long aimStudentId);

}
