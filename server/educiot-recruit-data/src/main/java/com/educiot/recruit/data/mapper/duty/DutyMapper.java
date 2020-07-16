package com.educiot.recruit.data.mapper.duty;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.recruit.data.entity.duty.Duty;
import com.educiot.recruit.data.entity.query.duty.DutyPageQuery;
import com.educiot.recruit.data.entity.vo.DutyVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 招生宣讲计划 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
public interface DutyMapper extends BaseMapper<Duty> {
    List<DutyVO> listPage(IPage<DutyVO> page, Long semesterId,@Param("param") DutyPageQuery param);
    long listPageCount( Long semesterId,@Param("param") DutyPageQuery param);
    long pageConflictCount(Long semesterId, @Param("param") DutyPageQuery param);
    List<DutyVO> pageConflict(IPage<DutyVO> page,  Long semesterId,@Param("param")  DutyPageQuery param);
}
