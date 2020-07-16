package com.educiot.recruit.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.recruit.data.entity.LoginRelation;
import com.educiot.recruit.data.entity.vo.StudentIdAndNameVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 登录信息关联表 服务类
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-28
 */
public interface ILoginRelationService extends IService<LoginRelation> {

    /**
     * 【小程序】获取当前登录用户绑定的学生
     *
     * @param login 登录用户login
     * @return java.util.List&lt;com.educiot.recruit.data.entity.vo.StudentIdAndNameVO&gt;
     * @author Pan Juncai
     * @date 2020/5/9 13:52
     */
    List<StudentIdAndNameVO> listFocusStudent(Long login);

    /**
     * 查询登录类型
     * @param studentInfo 登录人编号
     * @return 登录类型
     */
    Integer queryLoginType(@Param("studentInfo") Long studentInfo);
}
