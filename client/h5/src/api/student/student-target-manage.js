//目标生管理管理api
import request from "../../utils/request";

/**
 * 分页查询目标列表
 * @param {*} params 
 * {int} params.pageNum 当前页
 * {int} params.pageSize 每页大小
 * {string} params.classId  班级Id
 * {string} params.name 学生姓名关键字
 */
export const queryStudentByPage = params => {
    return request({
        url: `/aim-student/listAimStudentPage`,
        method: "get",
        params
    });
};

/**
 * 保存添加或编辑的目标生
 * aimStudentId 为空则为新增
 * aimStudentId 非空则为编辑
 * @param {*} data 
 * {
  "aimStudentId": 0,
  "classId": 0,
  "createTime": "2020-05-09T07:05:21.595Z",
  "creatorId": 0,
  "creatorName": "string",
  "direction": "string",
  "fatherName": "string",
  "fatherTelephone": "string",
  "level": 0,
  "motherName": "string",
  "motherTelephone": "string",
  "reason": "string",
  "status": 0,
  "studentName": "string",
  "studentTelephone": "string"
}
 */
export const saveStudent = data => {
    return request({
        url: `/aim-student/saveOrUpdateAimStudentInfo`,
        method: "post",
        data
    });
};

/**
 * 获取目标生详情数据
 * @param {*} id 
 */
export const detail = id => {
    return request({
        url: `/aim-student/queryAimStudentInfo/${id}`,
        method: "get",
    });
};