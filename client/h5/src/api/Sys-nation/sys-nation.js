//系统民族信息
import request from "../../utils/request";

/**
 * 系统民族信息
 * @param {} data 
 * @author cwz
 */
export const sysnation = () => {
    return request({
        url: `/sys-nation/list`,
        method: "post"
    });
};