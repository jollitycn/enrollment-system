package com.educiot.recruit.data.service.duty.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.educiot.recruit.common.base.MyExcption;
import com.educiot.recruit.data.entity.query.duty.DutyPageQuery;
import com.educiot.recruit.data.entity.vo.DutyVO;
import com.educiot.recruit.data.mapper.duty.DutyMapper;
import com.educiot.recruit.data.service.duty.IDutyService;
import com.educiot.recruit.data.common.ValidateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 招生宣讲计划 服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
@Service
public class DutyServiceImpl implements IDutyService {

    @Resource
    private DutyMapper dutyMapper;


    @Override
    public IPage<DutyVO> listPage(Long semesterId, DutyPageQuery query) throws MyExcption {
        ValidateUtil.validateSemester(semesterId);
        Page<DutyVO> page = new Page<>(query.getPageNum(), query.getPageSize());

        page.setOptimizeCountSql(false);
        page.setSearchCount(false);
        List<DutyVO> list = dutyMapper.listPage(page, semesterId, query);
        page.setTotal(dutyMapper.listPageCount(semesterId, query));

        page.setRecords(list);
        return page;
    }

    @Override
    public IPage<DutyVO> pageConflict(Long semesterId, DutyPageQuery query) throws MyExcption {
        ValidateUtil.validateSemester(semesterId);
        Page<DutyVO> page = new Page<>(query.getPageNum(), query.getPageSize());
        page.setOptimizeCountSql(false);
        page.setSearchCount(false);
        List<DutyVO> list = dutyMapper.pageConflict(page, semesterId, query);
        page.setTotal(dutyMapper.pageConflictCount(semesterId, query));
        page.setRecords(list);
        return page;
    }
}
