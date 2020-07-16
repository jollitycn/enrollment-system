package com.educiot.recruit.data.common;

import com.educiot.recruit.data.entity.SysNation;
import com.educiot.recruit.data.entity.preach.PreachPlan;
import com.educiot.recruit.data.entity.vo.SysRegionVO;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

public class GlobalCache implements Serializable {


//    @Getter
//     LoginUser loginUser;

    //public void reloadLoginUser();
    @Getter
     List<SysRegionVO> sysRegions;

    @Getter
     List<SysNation> sysNations;

    @Getter
     List<PreachPlan>  preachPlan;



    //public void reloadLoginUser();

}
