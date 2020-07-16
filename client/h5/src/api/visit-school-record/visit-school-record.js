//文件上传
import request from "../../utils/request";


/**
 * 拜访学校列表分页查询次数
 * @author LGH
 */
export const pageGroupCount = data => {
  return request({
    url: `/public-relation/pageGroupCount`,
    method: "post",
    data
  });
};

/**
 * 拜访学校列表分页内容
 * @author LGH
 */
export const listGroupCount = data => {
  return request({
    url: `/public-relation/page`,
    method: "post",
    data
  });
};

/**
 * 新增拜访学校列表
 * @author LGH
 */
export const addGroupCount = data => {
  return request({
    url: `/public-relation/add`,
    method: "put",
    data
  });
};

/**
 * 删除拜访学校列表
 * @author LGH
 */
export const deleteGroupCount = id => {
  return request({
    url: `/public-relation/delete/${id}`,
    method: "put"
  });
};

export const getGroupCount = id => {
  return request({
    url: `/public-relation/detail/${id}`,
    method: "get"
  });
};
/**
 * 更新拜访学校列表
 * @author LGH
 */
export const updateGroupCount = data => {
  return request({
    url: `/public-relation/update`,
    method: "post",
    data
  });
};
