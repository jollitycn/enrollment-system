package com.educiot.recruit.data.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.recruit.data.entity.GroupSourceRelation;
import com.educiot.recruit.data.entity.SourceSchool;
import com.educiot.recruit.data.entity.ext.GroupSourceRelationExt;
import com.educiot.recruit.data.entity.query.ListSourceSchoolPageQuery;
import com.educiot.recruit.data.entity.vo.ListSourceSchoolVO;
import com.educiot.recruit.data.mapper.GroupSourceRelationMapper;
import com.educiot.recruit.data.service.IGroupSourceRelationService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 招生组生源学校关联表 服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@Service
public class GroupSourceRelationServiceImpl extends ServiceImpl<GroupSourceRelationMapper, GroupSourceRelation> implements IGroupSourceRelationService {

    @Override
    public GroupSourceRelationExt getByGroupIdAndSourceSchoolId(Long groupId, Long sourceSchoolId) {
        // 判断分组是否存在
        QueryWrapper<GroupSourceRelation> wrapper = new QueryWrapper<>();
        wrapper.eq(GroupSourceRelation.Columns.GROUP_ID, groupId);
        wrapper.eq(GroupSourceRelation.Columns.SOURCE_SCHOOL_ID, sourceSchoolId);
        GroupSourceRelation bean = baseMapper.selectOne(wrapper);
        if (null == bean) {
            //  throw new MyExcption(CodeMsg.ID_NOT_EXIST);
        }
        GroupSourceRelationExt ext = new GroupSourceRelationExt();
        BeanUtil.copyProperties(bean, ext);
        return ext;
    }


    @Override
    public IPage<ListSourceSchoolVO> listSourceSchoolPage(ListSourceSchoolPageQuery pageQuery, Long schoolId, Long semesterId) {
        IPage<ListSourceSchoolVO> page = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());
        // 获取招生学校id
        List<ListSourceSchoolVO> list = baseMapper.listGroupInfoPage(page, pageQuery, schoolId, semesterId);
        page.setRecords(list);
        return page;
    }

    @Override
    public List<ListSourceSchoolVO> listSourceSchoolVOResult(Long groupId, Long currentSemesterId) {
        return baseMapper.listSourceSchoolVOResult(groupId,currentSemesterId);
    }
}
