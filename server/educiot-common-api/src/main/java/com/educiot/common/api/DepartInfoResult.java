package com.educiot.common.api;

import java.util.List;

/**
 * @author Jason Hong
 */
public class DepartInfoResult extends APIResult {
    private List<DepartInfo> data;

    public DepartInfoResult() {
    }

    /**
     * @return
     */
    @Override
    public List<DepartInfo> getData() {
        return this.data;
    }

    public void setData(List<DepartInfo> data) {
        this.data = data;
    }

}
