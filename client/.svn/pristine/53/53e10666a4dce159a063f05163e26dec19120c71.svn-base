//学生招生渠道信息管理
import request from "../../utils/request";

/**
 * 根据学生公关ID查询学生招生渠道信息
 * @param {number} studentInfoId 学生的公关ID
 * @author lgh
 */
export const queryRecruitWayInfo = studentPublicRelationId => {
  return request({
      url: `/recruit-way/queryRecruitWayInfo?studentPublicRelationId=${studentPublicRelationId}`,
      method: "get"
  });
};

/**
 * 添加或修改学生招生渠道信息
 * @param {number} data 学生招生渠道信息
 * @author lgh
 */
export const saveOrUpdateRecruitWayInfo = data => {
  return request({
      url: `/recruit-way/saveOrUpdateRecruitWayInfo`,
      method: "post",
      data
  });
};

/**
 * 查询招生渠道的渠道名称列表
 * @author lgh
 */
export const queryNodeNameList = () => {
  return request({
      url: `/target/queryNodeNameList`,
      method: "get",
  });
};