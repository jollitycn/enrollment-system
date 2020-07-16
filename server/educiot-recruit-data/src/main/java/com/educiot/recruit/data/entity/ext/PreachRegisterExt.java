package com.educiot.recruit.data.entity.ext;

import com.educiot.recruit.data.entity.preach.PreachRegister;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel(value = "学生签到信息")
@EqualsAndHashCode()
public class PreachRegisterExt extends PreachRegister {
}
