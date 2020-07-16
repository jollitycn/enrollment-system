package com.educiot.recruit.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.educiot.recruit.data.entity.LoginRelation;
import com.educiot.recruit.data.entity.vo.StudentIdAndNameVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 登录信息关联表 Mapper 接口
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-28
 */
public interface LoginRelationMapper extends BaseMapper<LoginRelation> {

    List<LoginRelation> selectListByLoginAndMobile(Long login);

    List<StudentIdAndNameVO> listFocusStudent(Long login);

    /**
     * 查询登录类型
     * @param studentInfo 登录人编号
     * @return 登录类型
     */
    Integer queryLoginType(@Param("studentInfo") Long studentInfo);
}
