package com.educiot.recruit.data.service.duty;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.recruit.common.base.MyExcption;
import com.educiot.recruit.data.entity.query.duty.DutyPageQuery;
import com.educiot.recruit.data.entity.vo.DutyVO;

/**
 * <p>
 * 招生宣讲计划 服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface IDutyService {

    IPage<DutyVO> listPage(Long semesterId, DutyPageQuery query) throws MyExcption;

    IPage<DutyVO> pageConflict(Long semesterId, DutyPageQuery query) throws MyExcption;
}
