package com.educiot.recruit.common.util;

import cn.hutool.core.util.StrUtil;
import com.educiot.recruit.common.constant.Constant;

/**
 * @program: server
 * @description: 公共工具类
 * @author: XuChao
 * @create: 2020-04-14 14:17
 **/
public class CommonUtil {

    /**
     * 判断传入id是否可用
     *
     * @param id 要判断的id
     * @return true为可用id
     */
    public static boolean checkId(Long id) {

        //id不为空且大于0则为可用id
        return null != id && id.compareTo(Constant.SYS_LONG_ZERO) > Constant.SYS_ZERO;
    }

    /**
     * 判断搜索字符在是否有效
     *
     * @param searchKey 要判断的搜索参数
     * @return 无效返回null
     */
    public static String checkSearchKey(String searchKey) {

        //searchKey如果不为空，则拼上%返回 用作模糊搜索
        if (!StrUtil.hasBlank(searchKey)) {
            searchKey = Constant.PERCENT + searchKey + Constant.PERCENT;

            return searchKey;
        }
        return null;
    }

}
