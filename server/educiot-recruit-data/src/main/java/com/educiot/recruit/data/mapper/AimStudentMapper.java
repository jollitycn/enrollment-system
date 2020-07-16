package com.educiot.recruit.data.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.recruit.data.entity.AimStudent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.educiot.recruit.data.entity.Plan;
import com.educiot.recruit.data.entity.query.ListAimStudentPageQuery;
import com.educiot.recruit.data.entity.query.ListPlanPageQuery;
import com.educiot.recruit.data.entity.vo.AimStudentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 目标生表 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
public interface AimStudentMapper extends BaseMapper<AimStudent> {

    /**
     * 分页查询目标生信息
     * @param page 分页信息
     * @param param 查询条件
     * @return 分页目标生列表信息
     */
    IPage<AimStudent> listAimStudentPage(IPage<AimStudent> page, @Param("param") ListAimStudentPageQuery param);

    /**
     * 查询目标生数量
     * @param param 查询条件
     * @return 目标生数量
     */
    int listAimStudentCount(@Param("param") ListAimStudentPageQuery param);
    /**
     * 获取目标生信息
     * @param aimStudentId 目标生ID
     * @return 目标生信息
     */
    AimStudentVO queryAimStudentInfo(@Param("aimStudentId") Long aimStudentId);

}
