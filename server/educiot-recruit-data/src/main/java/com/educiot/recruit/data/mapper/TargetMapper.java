package com.educiot.recruit.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.educiot.recruit.data.entity.Target;
import com.educiot.recruit.data.entity.vo.TargetDetailVO;
import com.educiot.recruit.data.entity.vo.TargetVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 目标 Mapper 接口
 * </p>
 *
 * @author Xuchao
 * @since 2020-04-13
 */
public interface TargetMapper extends BaseMapper<Target> {

    /**
     * DAO查询，按创建时间倒序，时间格式化sql实现 格式为 YYYY-MM-DD HH:mm:ss
     * 分页通过mybatis拦截器自动实现
     * @param page 分页对象
     * @param schoolId 学校id
     * @return 返回目标列表分页数据
     */
    IPage<TargetVO> getTargetListPage(Page<TargetVO> page,
                                      @Param("schoolId") Long schoolId);


    /**
     * 根据目标id查询目标详情，包含渠道和专业树
     * @param targetId 目标id
     * @return
     */
    TargetDetailVO getDetailByTargetId(@Param("targetId")Long targetId);

    /**
     * 查询线上招生渠道
     * @param recruitSchoolId 招生学校ID
     * @param semesterId 学期ID
     * @return
     */
    String queryTargetDecompositionId(@Param("recruitSchoolId") Long recruitSchoolId,@Param("semesterId") Long semesterId);

    /**
     * 查询线上招生渠道名称
     * @param targetDecompositionId
     * @return
     */
    List<String> queryNodeNameList(@Param("targetDecompositionId") String targetDecompositionId);
}
