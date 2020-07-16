package com.educiot.recruit.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.educiot.recruit.data.entity.SysDictionary;

/**
 * <p>
 * 系统数字字典 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface SysDictionaryMapper extends BaseMapper<SysDictionary> {
    void insertBaseInfo(Long recruitSchoolId);
}
