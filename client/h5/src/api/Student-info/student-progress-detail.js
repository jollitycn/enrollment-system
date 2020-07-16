//学生报名进度信息
import request from "../../utils/request";

/**
 * 根据学生公关ID查询学生招生渠道信息
 * @param {number} studentInfoId 学生的公关ID
 * @author lgh
 */
export const getProgress = studentPublicRelationId => {
  return request({
      url: `/progress/getProgress/${studentPublicRelationId}`,
      method: "get"
  });
};

/**
 * 根据学生公关ID查询获取注册状态
 * @param {number} studentInfoId 学生的公关ID
 * @author lgh
 */
export const getRegisterStatus = studentPublicRelationId => {
  return request({
      url: `/progress/getRegisterStatus/${studentPublicRelationId}`,
      method: "get"
  });
};

/**
 * 注册状态更改
 * @param {number} studentInfoId 学生的公关ID
 * @author lgh
 */
export const changeStatus = data => {
  return request({
      url: `/progress/changeStatus`,
      method: "post",
      data
  });
};