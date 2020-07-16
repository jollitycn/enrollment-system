package com.educiot.recruit.data.entity.query;

import com.educiot.recruit.common.base.BasePageQuery;
import com.educiot.recruit.common.base.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Pan Juncai
 * @version 1.0
 * @date 2020/4/22 10:32
 */
@Data
@ApiModel(value = "获取班级")
@EqualsAndHashCode(callSuper = true)
public class ListClassBySourceQuery extends BaseQuery {

    @Valid
    @NotNull(message = "生源学校id不能为空")
    @Min(value = 1L, message = "生源学校id不合法")
    @Max(value = Long.MAX_VALUE, message = "生源学校id不合法")
    @ApiModelProperty(value = "生源学校id", name = "sourceSchoolId", required = true)
    private Long sourceSchoolId;

    @Valid
    @NotNull(message = "招生学校id不能为空")
    @Min(value = 1L, message = "招生学校id不合法")
    @Max(value = Long.MAX_VALUE, message = "招生学校id不合法")
    @ApiModelProperty(value = "招生学校id", name = "recruitSchoolId", required = true)
    private Long recruitSchoolId;

}
