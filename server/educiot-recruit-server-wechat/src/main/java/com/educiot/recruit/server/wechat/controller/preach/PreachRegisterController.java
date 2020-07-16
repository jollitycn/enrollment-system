package com.educiot.recruit.server.wechat.controller.preach;


import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.entity.ext.PreachRegisterExt;
import com.educiot.recruit.data.entity.preach.PreachRegister;
import com.educiot.recruit.data.service.preach.IPreachRegisterService;
import com.educiot.recruit.data.service.student.IProgressService;
import com.educiot.recruit.server.wechat.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 宣讲计划报名表 前端控制器
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */

@Api(tags = {PreachRegisterController.CLASS_NAME_ZN}, value = PreachRegisterController.CLASS_NAME_EN)
@RestController
@RequestMapping(PreachRegisterController.REQUEST_MAPPING)
@Log4j2
public class PreachRegisterController extends BaseController {
    public static final String CLASS_NAME_ZN = "宣讲计划报名签到";
    public static final String REQUEST_MAPPING = "public-register";
    public static final String CLASS_NAME_EN = "PreachRegisterController";
    @Resource
    private IPreachRegisterService preachRegisterService;

    @Resource
    private IProgressService progressService;

    /**
     * @param id
     * @return
     */
    @GetMapping(METHOD_DETAIL)
    @ApiOperation(value = METHOD_DETAIL_NAME, response = PreachRegisterExt.class)
    public Result<?> detail(@PathVariable Long id) {
        log.info(METHOD_DETAIL_NAME);
        PreachRegisterExt obj = preachRegisterService.detail(id);
        return Result.success(obj);
    }

    @PutMapping("signIn/{preachPlanId}")
    @ApiOperation(value = "签到")
    public Result<?> signIn(@PathVariable Long preachPlanId) {
        log.info("签到");
        List<PreachRegister> orgBean   = preachRegisterService.checkSignIn(preachPlanId,getLoginVO().getStudentPublicRelationId());
        if(orgBean.size()>0){
            return Result.error(CodeMsg.PREACH_REGISTER_SIGNIN);
        }
        PreachRegister bean = new PreachRegister();
        bean.setCreateTime(LocalDateTime.now())
                .setStudentPublicRelationId(getLoginVO().getStudentPublicRelationId())
                .setPreachPlanId(preachPlanId)
                .setCreatorId(getLoginVO().getCurrentStudent().getStudentInfo()).setStatus(1);
        preachRegisterService.save(bean);
        progressService.addProgress(2, getLoginVO().getStudentPublicRelationId(), getLoginVO().getCurrentStudent().getStudentInfo(), getLoginVO().getCurrentStudent().getStudentName());

        //签到的时候，如果已经报名，那就不能再报名了，如果没报名，就直接报名
        List<PreachRegister> orgBeanName   = preachRegisterService.checkSignUp(preachPlanId,getLoginVO().getStudentPublicRelationId());
        if(orgBeanName.size()==0){
            PreachRegister preachRegister = new PreachRegister();
            preachRegister.setCreateTime(LocalDateTime.now())
                    .setStudentPublicRelationId(getLoginVO().getStudentPublicRelationId())
                    .setPreachPlanId(preachPlanId)
                    .setCreatorId(getLoginVO().getCurrentStudent().getStudentInfo()).setStatus(0);
            preachRegisterService.save(preachRegister);
            progressService.addProgress(1, getLoginVO().getStudentPublicRelationId(), getLoginVO().getCurrentStudent().getStudentInfo(), getLoginVO().getCurrentStudent().getStudentName());
        }
        return Result.success(bean);
    }

    @PutMapping("signUp/{preachPlanId}")
    @ApiOperation(value = "报名")
    public Result<?> signUp(@PathVariable Long preachPlanId) {
        log.info("报名");
        List<PreachRegister> orgBean   = preachRegisterService.checkSignUp(preachPlanId,getLoginVO().getStudentPublicRelationId());
        if(orgBean.size()>0){
            return Result.error(CodeMsg.PREACH_REGISTER_SIGNUP);
        }
        PreachRegister bean = new PreachRegister();
        bean.setCreateTime(LocalDateTime.now())
                .setStudentPublicRelationId(getLoginVO().getStudentPublicRelationId())
                .setPreachPlanId(preachPlanId)
                .setCreatorId(getLoginVO().getCurrentStudent().getStudentInfo()).setStatus(0);
        preachRegisterService.save(bean);
        progressService.addProgress(1, getLoginVO().getStudentPublicRelationId(), getLoginVO().getCurrentStudent().getStudentInfo(), getLoginVO().getCurrentStudent().getStudentName());
        return Result.success(bean);
    }

}

