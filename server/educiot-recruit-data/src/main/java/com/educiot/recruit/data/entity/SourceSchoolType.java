package com.educiot.recruit.data.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 生源学校类型
 * </p>
 *
 * @author LiuHao
 * @since 2020-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("e_r_source_school_type")
@ApiModel(value="SourceSchoolType对象", description="生源学校类型")
public class SourceSchoolType implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "生源学校类型ID")
    @TableId(value = "source_school_type_id", type = IdType.ID_WORKER)
    private Long sourceSchoolTypeId;

    @ApiModelProperty(value = "生源学校ID")
    private Long sourceSchoolId;

    @ApiModelProperty(value = "数据字典ID")
    private Long typeId;


}
