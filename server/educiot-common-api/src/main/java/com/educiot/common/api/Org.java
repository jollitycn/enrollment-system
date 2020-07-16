package com.educiot.common.api;

import com.educiot.common.base.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Jason Hong
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Org extends BaseVO {
    private String name;
    private String id;
    private List<Person> child;
}
