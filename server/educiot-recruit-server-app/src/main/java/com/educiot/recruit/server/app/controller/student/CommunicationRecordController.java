package com.educiot.recruit.server.app.controller.student;


import com.educiot.recruit.common.base.Result;
import com.educiot.recruit.data.entity.CommunicationRecord;
import com.educiot.recruit.data.entity.query.AddCommunicationRecordQuery;
import com.educiot.recruit.data.service.student.ICommunicationRecordService;
import com.educiot.recruit.server.app.common.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 沟通记录 前端控制器
 * </p>
 *
 * @author Mr.AI
 * @since 2020-04-13
 */
@RestController
@RequestMapping("/communication-record")
@Api(tags = {"学生沟通记录信息"}, value = "CommunicationRecordController")
public class CommunicationRecordController extends BaseController {

    @Resource
    private ICommunicationRecordService communicationRecordService;

    @PostMapping("/addCommunicationRecord")
    @ApiOperation("添加学生沟通记录信息（可编辑）")
    public Result addCommunicationRecord(@Valid @RequestBody AddCommunicationRecordQuery addCommunicationRecordQuery) {
        return communicationRecordService.addCommunicationRecord(addCommunicationRecordQuery);
    }

    @GetMapping("/queryCommunicationRecord")
    @ApiOperation("按公关ID获取学生沟通记录信息")
    public Result queryCommunicationRecord(@Valid Long studentPublicRelationId) {
        List<CommunicationRecord> communicationRecords = communicationRecordService.queryCommunicationRecord(studentPublicRelationId);
        return Result.success(communicationRecords);
    }

    @DeleteMapping("/deleteCommunicationRecord/{communicationRecordId}")
    @ApiImplicitParam(name = "communicationRecordId", value = "学生沟通记录ID", required = true)
    @ApiOperation("删除学生沟通记录信息")
    public Result deleteCommunicationRecord(@Valid @PathVariable("communicationRecordId")
                                            @NotNull(message = "学生沟通记录ID不能为空")
                                            @Min(value = 1L, message = "学生沟通记录ID最小为1")
                                            @Max(value = Long.MAX_VALUE, message = "学生沟通记录ID超出最大限制")
                                                    Long communicationRecordId) {
        return communicationRecordService.deleteCommunicationRecord(communicationRecordId);
    }
}
