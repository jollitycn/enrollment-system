package com.educiot.recruit.data.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.educiot.common.base.Result;
import com.educiot.recruit.data.entity.Login;
import com.educiot.recruit.data.entity.query.*;
import com.educiot.recruit.data.entity.vo.MiniProgramLoginVO;

/**
 * <p>
 * 登录表 服务类
 * </p>
 *
 * @author Pan Juncai
 * @since 2020-04-28
 */
public interface ILoginService extends IService<Login> {

    /**
     * 小程序登录/注册
     *
     * @param loginOrRegisterQuery 微信信息和二维码信息
     * @param openid               微信openID
     * @return com.insi.educiot.recruit.common.base.Result
     * @author Pan Juncai
     * @date 2020/4/28 14:56
     */
    Result loginOrRegister(LoginQuery loginOrRegisterQuery, String openid);

    /**
     * 针对该openid下与招生学校下无绑定关系的情况，将进行招生学校和学生的绑定
     *
     * @param bindRecruitAndStudentQuery 招生学校和学生信息
     * @return com.educiot.common.api.base.Result
     * @author  Pan Juncai
     * @date 2020/6/30 17:42
     */
    Result bindRecruitSchoolAndStudent(BindRecruitAndStudentQuery bindRecruitAndStudentQuery);

    /**
     * 校验学生信息和家长手机号是否存在
     *
     * @param checkStudentQuery 学生信息
     * @return com.insi.educiot.recruit.common.base.Result
     * @author Pan Juncai
     * @date 2020/4/29 14:08
     */
    Result checkStudentAndPhone(CheckStudentQuery checkStudentQuery);

    /**
     * 小程序绑定学生信息
     *
     * @param bindStudentQuery 绑定学生信息
     * @param openid           微信openID
     * @return com.insi.educiot.recruit.common.base.Result
     * @author Pan Juncai
     * @date 2020/4/28 14:56
     */
    Result<Login> bindStudent(BindStudentQuery bindStudentQuery, String openid);

    Result<MiniProgramLoginVO> getLogin(Login login, Long recruitSchoolId);

    /**
     * 小程序登录之后的添加学生里面的绑定学生信息
     *
     * @param addBindStudentQuery 学生信息
     * @param loginVO             登录信息
     * @return com.insi.educiot.recruit.common.base.Result
     * @author Pan Juncai
     * @date 2020/4/28 14:56
     */
    Result bindStudent(AddBindStudentQuery addBindStudentQuery, MiniProgramLoginVO loginVO);

    /**
     * 小程序注册
     *
     * @param registerQuery 微信信息和二维码信息
     * @param openid        微信openID
     * @return com.insi.educiot.recruit.common.base.Result
     * @author Pan Juncai
     * @date 2020/4/29 9:37
     */
    Result register(RegisterQuery registerQuery, String openid);

    /**
     * 注册时去绑定学生
     *
     * @param registerBindQuery 学生信息
     * @param openid            微信openid
     * @return com.educiot.common.api.base.Result
     * @author Pan Juncai
     * @date 2020/4/30 15:14
     */
    Result<Login> registerBind(RegisterBindQuery registerBindQuery, String openid);

    /**
     * 注册新学生和用户
     *
     * @param registerNewStudentQuery 注册信息
     * @param openid                  微信openid
     * @return com.educiot.common.api.base.Result
     * @author Pan Juncai
     * @date 2020/4/30 16:52
     */
    Result<Login> registerNewStudent(RegisterNewStudentQuery registerNewStudentQuery, String openid);

    /**
     * 小程序登陆之后的添加学生判断
     *
     * @param addBindStudentQuery 学生信息
     * @param loginVO             当前登录信息
     * @return com.educiot.common.api.base.Result
     * @author Pan Juncai
     * @date 2020/5/9 10:03
     */
    Result checkBindStudent(AddBindStudentQuery addBindStudentQuery, MiniProgramLoginVO loginVO);
}
