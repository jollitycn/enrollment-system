//学生基本信息管理
import request from "../../utils/request";


/**
 * 添加学生基本信息
 * @param {} data 
 * @author cwz
 */
export const addStudentInfo = data => {
  return request({
    url: `/student-info/addStudentInfo`,
    method: "post",
    data
  });
};
/**
 * 分页获取生源学校信息列表
 * @param {} query 
 * @author cwz
 */
export const listSourceSchoolPage = () => {
  return request({
    url: `/student-info/listSourceSchool`,
    method: "get"
  });
};
/**
 * 分页获取生源学校下的班级信息列表
 * @param {} query 
 * @author cwz
 */
export const listClassPage = query => {
  return request({
    url: `/student-info/listClassPage`,
    method: "get",
    params: query
  });
};
/**
 * 分页获取所有学生基本信息列表（意向生）
 * @param {} query 
 * @author cwz
 */
export const listStudentInfoPage = query => {
  return request({
    url: `/student-info/listStudentInfoPage`,
    method: "get",
    params: query
  });
};
/**
 * 分页获取分组信息列表(目标生)
 * @param {} query 
 * @author cwz
 */
export const listAimStudentPage = query => {
  return request({
    url: `/aim-student/listAimStudentPage`,
    method: "get",
    params: query
  });
};

/**
 * 获取学生基本信息
 * @param {} data 
 * @author cwz
 */
export const queryStudentInfo = data => {
  return request({
    url: `/student-info/queryStudentInfo?studentInfoId=${data}`,
    method: "get"
  });
};
/**
 * 添加学生基本信息和家庭信息
 * @param {} data 
 * @author cwz
 */
export const addStudentInfoAPP = data => {
  return request({
    url: `/student-info/addStudentInfoAPP`,
    method: "post",
    data
  });
};
/**
 * 添加或修改学生家庭成员信息
 * @param {} data 
 * @author cwz
 */
export const saveOrUpdateFamilyMemberInfo = data => {
  return request({
    url: `/family-member/saveOrUpdateFamilyMemberInfo`,
    method: "post",
    data
  });
};
/**
 * 编辑学生基本信息
 * @param {} data 
 * @author cwz
 */
export const updateStudentInfo = data => {
  return request({
    url: `/student-info/updateStudentInfo`,
    method: "put",
    data
  });
};
/**
 * 添加或修改学生在校信息
 * @param {} data 
 * @author cwz
 */
export const saveOrUpdateInSchoolInfo = data => {
  return request({
    url: `/in-school/saveOrUpdateInSchoolInfo`,
    method: "post",
    data
  });
};
/**
 * 根据学生基本信息ID查询学生在校信息(包含生源学校、班级信息、成绩信息)
 * @param {} data 
 * @author cwz
 */
export const queryInSchoolInfo = studentInfoId => {
  return request({
    url: `/in-school/queryInSchoolInfo?studentInfoId=${studentInfoId}`,
    method: "get",
  });
};
/**
 * 根据学生基本信息ID查询家庭成员信息
 * @param {} data 
 * @author cwz
 */
export const familyinfo = data => {
  return request({
    url: `/family-member/listGroupInfoPage?studentInfo=${data}`,
    method: "get"
  });
};

/**
 * 获取学生基本信息填写状态
 * @param {Array}studentInfoId 
 * @author lgh
 */
export const queryStudentInfoStatus = studentInfoId => {
  return request({
    url: `/student-info/queryStudentInfoStatus`,
    method: "get",
    params: studentInfoId
  });
};