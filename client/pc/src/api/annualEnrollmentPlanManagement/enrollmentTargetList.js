/**
 * 招生目标列表
 */

import request from '../../utils/request';


/**
 * 添加或编辑招生目标
 * @param {object} data 
 * data 包含参数如下（待*为必传参数）：
 * recruitSchoolId * 招生学校ID
 * targetId 招生目标id ，不传为新增，传为编辑
 * semesterId * 学期id
 * semester * 学期名称
 * title * 标题
 * targetAmount *目标数量
 * @author lmh
 */
export const addOrEdit = (data) => {
    return request({
        url: '/target/addOrEdit',
        method: 'post',
        params:data,
    })
}

/**
 * 新增学期
 * @param {object} data 
 * data 包含参数如下（待*为必传参数）：
 * schoolId * 学校id
 * semesterName * 学期信息
 * @author lmh
 */
export const addSemester = (data) => {
    return request({
        url: '/target/addSemester',
        method: 'post',
        data,
    })
}

/**
 * 获取招生目标详情
 * @param {string} targetId 招生目标ID
 * @author lmh
 */
export const getDetail = (targetId) => {
    return request({
        url: `/target/detail/${targetId}`,
        method: 'get',
    })
}


/**
 * 修改渠道/专业节点信息
 * @param {object} data 
 * data 包含参数如下（待*为必传参数）：
 * comment	说明
 * argetAmount * 目标数量
 * targetDecompositionId * 目标分解ID
 * @author lmh
 */
export const editChannelOrMajorNode = (data) => {
    return request({
        url: `/target/editChannelOrMajorNode`,
        method: 'put',
        data,
    })
}

/**
 * 分页获取招生目标列表
 * @param {object} params
 * params 包含参数如下（待*为必传参数）：
 * pageNum * 当前页
 * pageSize * 每页大小
 * schoolId * 学校id
 * @author lmh
 */
export const queryPage = (params) => {
    return request({
        url: `/target/list`,
        method: 'get',
        params,
    })
}

/**
 * 获取学校学期列表
 * @param {string} schoolId 
 * @author lmh
 */
export const getSemesterList = (schoolId) => {
    console.log("schoolId===",schoolId)
    return request({
        url: `/target/semesterList/${schoolId}`,
        method: 'get',
    })
}



