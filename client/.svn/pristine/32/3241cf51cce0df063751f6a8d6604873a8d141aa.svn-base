//学生公关管理
import request from "../../utils/request";

/**
 * 获取学生公关信息
 * @param {number} studentInfoId 学生的公关ID
 * @author lgh
 */
export const queryStudentPublicRelation = studentInfoId => {
  return request({
      url: `/student-public-relation/queryStudentPublicRelation?studentInfoId=${studentInfoId}`,
      method: "get"
  });
};

/**
 * 新增学生的沟通信息
 * @param {array} data 学生的沟通信息
 * @author lgh
 */
export const addCommunicationRecord = data => {
  return request({
      url: `/communication-record/addCommunicationRecord`,
      method: "post",
      data
  });
};


/**
 * 添加或编辑学生公关信息
 * @param {array} data 学生公关信息
 * @author lgh
 */
export const saveOrUpdateStudentPublicRelation = data => {
  return request({
      url: `/student-public-relation/saveOrUpdateStudentPublicRelation`,
      method: "post",
      data
  });
};

/**
 * 查询公关信息中的招生组下拉列表
 * @author lgh
 */
export const queryPrincipal = (query) => {
  return request({
      url: `/student-public-relation/queryPrincipal`,
      method: "get",
      params:query
  });
};

/**
 * 获取分组下的负责人详情
 * @param {String}groupId 分组ID
 * @author lgh
 */
export const getGroupDetail = groupId => {
  return request({
      url: `/student-public-relation/getGroupDetail/${groupId}`,
      method: "get"
  });
};
