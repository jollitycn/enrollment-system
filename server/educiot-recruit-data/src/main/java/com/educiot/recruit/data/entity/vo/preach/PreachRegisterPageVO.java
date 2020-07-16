package com.educiot.recruit.data.entity.vo.preach;

import com.educiot.recruit.common.base.BaseVO;
import com.educiot.recruit.common.constant.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "PreachRegisterPageVO")
@EqualsAndHashCode()
public class PreachRegisterPageVO extends BaseVO {


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.Sys.LOCALDATETIME_FORMATTER)
    private LocalDateTime createTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long preachRegisterId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long studentInfo;
    private String studentName;
    private String imageUrl;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long studentPublicRelationId;
    private String studentNo;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long recruitSchoolId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long sourceSchoolId;
    private String sourceSchoolName;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long classId;
    private String className;
    private String parentName;
    private String parentTelephone;

}
