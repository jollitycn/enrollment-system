package com.educiot.recruit.data.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.educiot.recruit.data.entity.RecruitSchool;
import com.educiot.recruit.data.entity.SysDictionary;
import com.educiot.recruit.data.entity.query.AddDictionaryQuery;
import com.educiot.recruit.data.entity.query.ListDictionaryQuery;
import com.educiot.recruit.data.entity.query.UpdateDictionaryQuery;
import com.educiot.recruit.data.entity.vo.SysDictVO;
import com.educiot.recruit.data.mapper.SysDictionaryMapper;
import com.educiot.recruit.data.service.IRecruitSchoolService;
import com.educiot.recruit.data.service.ISysDictionaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.educiot.common.base.CodeMsg;
import com.educiot.common.base.Result;
import com.educiot.common.constant.Constant;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import springfox.documentation.service.ApiInfo;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统数字字典 服务实现类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@Service
public class SysDictionaryServiceImpl extends ServiceImpl<SysDictionaryMapper, SysDictionary> implements ISysDictionaryService {

    @Resource
    private IRecruitSchoolService recruitSchoolService;

    /**
     * 获取数据字典列表
     *
     * @param currentSchoolId
     * @param listDictionaryQuery
     * @return List<SysDictionary>
     */
    @Override
    public List<SysDictionary> getDictList(Long currentSchoolId, ListDictionaryQuery listDictionaryQuery) {

        //获取招生学校ID
        QueryWrapper<RecruitSchool> recruitSchoolQueryWrapper = new QueryWrapper<>();
        recruitSchoolQueryWrapper.eq("recruit_school_id", currentSchoolId);
        RecruitSchool recruitSchool = recruitSchoolService.getOne(recruitSchoolQueryWrapper);

        //判断学校id是否存在
        if (recruitSchool != null) {
            QueryWrapper<SysDictionary> wrapper = new QueryWrapper<>();
            wrapper.eq("is_deleted", Constant.SYS_ZERO);

            //判断是否有parent_id
            if (null != listDictionaryQuery.getParentId()) {
                wrapper.eq("parent_id", listDictionaryQuery.getParentId());
            }
            wrapper.eq("recruit_school_id", recruitSchool.getRecruitSchoolId());
            List<SysDictionary> result = list(wrapper);
            return result;
        } else {
            return null;
        }

    }

    /**
     * 删除数据字典
     * @param dictionaryId
     * @return Result
     */
    @Override
    public Result<?> deleteDictionary(Long dictionaryId) {

        QueryWrapper<SysDictionary> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", dictionaryId);
        wrapper.eq("is_deleted", Constant.SYS_ZERO);
        int count = count(wrapper);

        //判断该数据字典是否有子数据
        if (count > 0) {
            return Result.error(CodeMsg.DIC_DELETE_ERROR);
        } else {

            SysDictionary sysDictionary = getById(dictionaryId);
            sysDictionary.setIsDeleted(Constant.SYS_ONE);
            boolean status = updateById(sysDictionary);
            if (status) {
                return Result.success();
            } else {
                return Result.error(CodeMsg.DATA_DELETE_ERROR);
            }
        }
    }

    /**
     * 新增数据字典
     * @param addDictionaryQuery
     * @return Result
     */
    @Override
    public Result<?> addDictionary(AddDictionaryQuery addDictionaryQuery, Long schoolId, Long apiId,Long userId,String userName) {

        QueryWrapper<SysDictionary> wrapper = new QueryWrapper<>();
        wrapper.eq("data_code", addDictionaryQuery.getDataCode());
        wrapper.eq("is_deleted", Constant.SYS_ZERO);
        wrapper.eq("recruit_school_id", schoolId);
        int count = count(wrapper);
        //判断数据字典编码是否有重复
        if (count > 0) {
            return Result.error(CodeMsg.DIC_CODE_REPEAT);
        }

        //获取招生学校ID
        QueryWrapper<RecruitSchool> recruitSchoolQueryWrapper = new QueryWrapper<>();
        recruitSchoolQueryWrapper.eq("api_id", apiId);
        RecruitSchool recruitSchool = recruitSchoolService.getOne(recruitSchoolQueryWrapper);

        //填充入库实体
        SysDictionary sysDictionary = new SysDictionary();
        BeanUtils.copyProperties(addDictionaryQuery, sysDictionary);
        sysDictionary.setRecruitSchoolId(recruitSchool.getRecruitSchoolId());
        sysDictionary.setModifyId(userId);
        sysDictionary.setModifyName(userName);
        sysDictionary.setModifyTime(LocalDateTime.now());
        boolean status = save(sysDictionary);
        if (status) {
            return Result.success();
        } else {
            return Result.error(CodeMsg.DATA_INSERT_ERROR);
        }
    }

    /**
     * 更新数据字典
     * @param updateDictionaryQuery
     * @return
     */
    @Override
    public Result<?> updateDictionary(UpdateDictionaryQuery updateDictionaryQuery, Long currentSchoolId,Long apiId,Long userId,String userName) {

        // 校验修改的编码不能重复
        QueryWrapper<SysDictionary> wrapper = new QueryWrapper<>();
        wrapper.notIn("dictionary_id", updateDictionaryQuery.getDictionaryId());
        wrapper.eq("is_deleted", Constant.SYS_ZERO);
        wrapper.eq("data_code", updateDictionaryQuery.getDataCode());
        wrapper.eq("recruit_school_id", currentSchoolId);
        if (count(wrapper) > 0) {
            return Result.error(CodeMsg.DIC_CODE_REPEAT);
        }

        //获取招生学校ID
        QueryWrapper<RecruitSchool> recruitSchoolQueryWrapper = new QueryWrapper<>();
        recruitSchoolQueryWrapper.eq("api_id", apiId);
        RecruitSchool recruitSchool = recruitSchoolService.getOne(recruitSchoolQueryWrapper);

        //填充入库实体
        SysDictionary sysDictionary = getById(updateDictionaryQuery.getDictionaryId());
        BeanUtils.copyProperties(updateDictionaryQuery, sysDictionary);
        sysDictionary.setRecruitSchoolId(recruitSchool.getRecruitSchoolId());
        sysDictionary.setModifyId(userId);
        sysDictionary.setModifyName(userName);
        sysDictionary.setModifyTime(LocalDateTime.now());
        boolean status = updateById(sysDictionary);
        if (status) {
            return Result.success();
        } else {
            return Result.error(CodeMsg.DATA_UPDATE_ERROR);
        }
    }

    @Override
    public List<SysDictVO> getAllDict(Long recruitSchoolId) {

        //获取一级菜单信息
        QueryWrapper<SysDictionary> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", 0);
        wrapper.eq("recruit_school_id", recruitSchoolId);
        wrapper.eq("is_deleted", Constant.SYS_ZERO);
        List<SysDictionary> sysDictionaries = list(wrapper);

        List<SysDictVO> result = new ArrayList<>();

        for (SysDictionary sysDictionary : sysDictionaries) {
            SysDictVO sysDictVO = new SysDictVO();
            BeanUtils.copyProperties(sysDictionary, sysDictVO);
            result.add(sysDictVO);
        }

        result.forEach(levelOneMenu -> levelOneMenu.setSubs(listChildDict(levelOneMenu.getDictionaryId())));
        return result;
    }

    @Override
    public void insertBaseInfo(Long schoolId) {

        baseMapper.insertBaseInfo(schoolId);
    }

    @Override
    public List<SysDictionary> getDictListByCode(Long recruitSchoolId , String dataCode) {

        //获取code对应的字典id
        QueryWrapper<SysDictionary> dictionaryQueryWrapper = new QueryWrapper<>();
        dictionaryQueryWrapper.eq("data_code", dataCode);
        dictionaryQueryWrapper.eq("recruit_school_id",recruitSchoolId);
        SysDictionary sysDictionary = getOne(dictionaryQueryWrapper);

        QueryWrapper<SysDictionary> wrapper = new QueryWrapper<>();
        wrapper.eq("recruit_school_id", recruitSchoolId);
        wrapper.eq("is_deleted", Constant.SYS_ZERO);
        wrapper.eq("parent_id", sysDictionary.getDictionaryId());
        return list(wrapper);
    }

    private List<SysDictVO> listChildDict(Long parentId) {

        QueryWrapper<SysDictionary> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", parentId);
        wrapper.eq("is_deleted", Constant.SYS_ZERO);
        List<SysDictionary> sysDictionaries = list(wrapper);

        List<SysDictVO> child = new ArrayList<>();
        for (SysDictionary sysDictionary : sysDictionaries) {
            SysDictVO sysDictVO = new SysDictVO();
            BeanUtils.copyProperties(sysDictionary, sysDictVO);
            child.add(sysDictVO);
        }

        child.forEach(menu -> menu.setSubs(listChildDict(menu.getDictionaryId())));
        return child;
    }
}
