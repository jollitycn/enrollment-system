//文件上传
import request from "../../utils/request";

/**
 * 上传文件
 * @param {} data 
 * @author cwz
 */
export const uploadAdd = data => {
    return request({
        url: `/upload/add`,
        method: "post",
        data,
        config: {
            headers: { 'Content-Type': 'multipart/form-data' }
        }
    });
};