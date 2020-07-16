package com.educiot.recruit.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.educiot.recruit.data.entity.Class;
import com.educiot.recruit.data.entity.query.ListClassBySourceQuery;
import com.educiot.recruit.data.entity.query.ListClassPageQuery;
import com.educiot.recruit.data.entity.query.RegisterQuery;
import com.educiot.recruit.data.entity.vo.ClassIdAndNameVO;
import com.educiot.recruit.data.entity.vo.ClassVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 班级表 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-13
 */
public interface ClassMapper extends BaseMapper<Class> {

    /**
     * 分页获取生源学校下的班级列表
     *
     * @param page      分页条件
     * @param pageQuery 查询条件
     * @return java.util.List&lt;com.educiot.recruit.data.entity.vo.ClassVO&gt;
     * @author Pan Juncai
     * @date 2020/4/23 10:47
     */
    List<ClassVO> listSchoolClassPage(IPage<ClassVO> page, @Param("param") ListClassPageQuery pageQuery, Long semesterId);

    /**
     * 获取指定生源学校下的所有班级
     *
     * @param
     * @return java.util.List&lt;com.educiot.recruit.data.entity.vo.ClassIdAndNameVO&gt;
     * @author  Pan Juncai
     * @date 2020/4/29 17:36
     */
    List<ClassIdAndNameVO> listClassBySource( @Param("param")  ListClassBySourceQuery param);
}
