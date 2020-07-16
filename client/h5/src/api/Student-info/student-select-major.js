//选择专业信息
import request from "../../utils/request";

/**
 * 获取招生院系信息以及所有的专业信息
 * @author lgh
 */
export const queryCollegeAndMajor = () => {
  return request({
      url: `/aim-major/queryCollegeAndMajor`,
      method: "get"
  });
};


/**
 * 根据学生信息ID查询学生选择的专业信息
 * @param {number}studentInfoId 学生信息ID
 * @author lgh
 */
export const queryAimMajorInfo = studentInfoId => {
  return request({
      url: `/aim-major/queryAimMajorInfo?studentInfoId=${studentInfoId}`,
      method: "get"
  });
}

/**
 * 添加或修改选择专业信息
 * @param {Array}data 选中的专业信息列表数据
 * @author lgh
 */
export const saveOrUpdateAimMajorInfo = data => {
  return request({
      url: `/aim-major/saveOrUpdateAimMajorInfo`,
      method: "post",
      data
  });
}