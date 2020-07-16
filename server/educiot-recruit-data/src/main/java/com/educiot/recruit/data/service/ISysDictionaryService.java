package com.educiot.recruit.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.LoginUser;
import com.educiot.recruit.data.entity.SysDictionary;
import com.educiot.recruit.data.entity.query.AddDictionaryQuery;
import com.educiot.recruit.data.entity.query.ListDictionaryQuery;
import com.educiot.recruit.data.entity.query.UpdateDictionaryQuery;
import com.educiot.recruit.data.entity.vo.SysDictVO;

import java.util.List;

/**
 * <p>
 * 系统数字字典 服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
public interface ISysDictionaryService extends IService<SysDictionary> {

    /**
     * 获取数据字典列表
     *
     * @param currentSchoolId
     * @param listDictionaryQuery
     * @return List<SysDictionary>
     */
    List<SysDictionary> getDictList(Long currentSchoolId, ListDictionaryQuery listDictionaryQuery);

    /**
     * 删除数据字典
     * @param dictionaryId
     * @return Result
     */
    Result<?> deleteDictionary(Long dictionaryId);

    /**
     * 新增数据字典
     * @param addDictionaryQuery
     * @param loginUser
     * @return Result
     */
    Result<?> addDictionary(AddDictionaryQuery addDictionaryQuery, LoginUser loginUser);

    /**
     * 更新数据字典
     * @param updateDictionaryQuery
     * @param loginUser
     * @return
     */
    Result<?> updateDictionary(UpdateDictionaryQuery updateDictionaryQuery, LoginUser loginUser);

    List<SysDictVO> getAllDict(LoginUser loginUser);

    void insertBaseInfo(Long recruitSchoolId);

    List<SysDictionary> getDictListByCode(LoginUser loginUser, String dataCode);
}
