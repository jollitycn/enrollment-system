package com.educiot.recruit.server.wechat.controller;

import com.educiot.recruit.common.base.CodeMsg;
import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.common.constant.Constant;
import com.educiot.recruit.data.entity.TaskReceiver;
import com.educiot.recruit.data.entity.vo.AdmissionVO;
import com.educiot.recruit.data.entity.vo.MessageListVO;
import com.educiot.recruit.data.entity.vo.MiniProgramLoginVO;
import com.educiot.recruit.data.service.ITaskReceiverService;
import com.educiot.recruit.data.service.student.IProgressService;
import com.educiot.recruit.server.wechat.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liuhao
 * @program educiot-recruit
 * @description：消息中心模块
 * @date Create in 2020/5/11
 */
@Api(tags = {"消息中心模块"}, value = "消息中心模块")
@RestController
@RequestMapping("/message")
public class MessageController extends BaseController {

    @Resource
    private ITaskReceiverService taskReceiverService;

    @Resource
    private IProgressService progressService;

    @GetMapping("/getMessageList")
    @ApiOperation(value = "获取消息列表", response = MessageListVO.class)
    public Result<?> getMessageList() {

        MiniProgramLoginVO miniProgramLoginVO = getLoginVO();

        if (miniProgramLoginVO != null && miniProgramLoginVO.getCurrentRecruitSchool() != null && miniProgramLoginVO.getStudentPublicRelationId() != null
                && miniProgramLoginVO.getPlan() != null) {
            List<MessageListVO> result = taskReceiverService.getMessageList(miniProgramLoginVO.getCurrentRecruitSchool().getRecruitSchoolId(),
                    miniProgramLoginVO.getStudentPublicRelationId(), miniProgramLoginVO.getPlan().getPlanId(), miniProgramLoginVO.getLoginType());
            return Result.success(result);
        } else {
            return Result.error(CodeMsg.TOKEN_NOT_EXIST);
        }

    }

    @GetMapping("/getAdmissionInfo/{taskReceiverId}")
    @ApiOperation(value = "获取入选通知信息", response = AdmissionVO.class)
    public Result<?> getAdmissionInfo(@PathVariable Long taskReceiverId) {

        MiniProgramLoginVO miniProgramLoginVO = getLoginVO();

        if (miniProgramLoginVO != null && miniProgramLoginVO.getCurrentRecruitSchool() != null) {
            AdmissionVO admissionVO =
                    taskReceiverService.getAdmissionInfo(miniProgramLoginVO.getCurrentRecruitSchool().getRecruitSchoolId(), taskReceiverId);
            return Result.success(admissionVO);
        }
        return Result.success();
    }

    @PostMapping("/confirm/{taskReceiverId}")
    @ApiOperation(value = "确认通知")
    public Result<?> confirm(@PathVariable Long taskReceiverId) {

        TaskReceiver taskReceiver = taskReceiverService.getById(taskReceiverId);

        if (taskReceiver != null) {
            taskReceiver.setReadStatus(Constant.SYS_ONE);
            taskReceiverService.updateById(taskReceiver);
            //添加进度
            boolean status = progressService.addAttend(taskReceiver.getReceiverId());
            return Result.success();
        }
        return Result.success();
    }

}
