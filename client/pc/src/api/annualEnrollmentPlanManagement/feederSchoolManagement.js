/**
 * 生源学校管理
 */


import request from '../../utils/request';

//分页获取生源学校信息列表
export const listSourceSchoolPage = (query) => {
        return request({
            url: '/sourceSchool/listSourceSchoolPage',
            method: 'get',
            params: query
        })
    }
    //新增生源学校
export const addSourceSchool = (data) => {
        return request({
            url: '/sourceSchool/addSourceSchool',
            method: 'post',
            data
        })
    }
    //删除生源学校
export const deleteSourceSchool = (id) => {
        return request({
            url: `/sourceSchool/deleteSourceSchool/${id}`,
            method: 'delete',
        })
    }
    //编辑生源学校信息
export const updateSourceSchool = (data) => {
    return request({
        url: '/sourceSchool/updateSourceSchool',
        method: 'put',
        data
    })
}
/**
 * 生源学校类型列表
 * @param {object} params 请求参数，可不传
 * 
 * @author lmh
 */
export const listSourceSchoolType = (params) => {
    return request({
        url: '/sourceSchool/listSourceSchoolType',
        method: 'get',
        params,
    })
}

/**
 * 获取某个生源学校详情
 * @param {string} id 生源学校id
 * @author lmh
 */
export const getSourceSchoolDetail = (id) => {
    return request({
        url: `/sourceSchool/getSourceSchoolDetail/${id}`,
        method: 'get',
    })
}

/**
 * 导入excel
 * @param {object} data formdata 数据
 * 
 * @author lmh
 */
export const excelImport = (data) => {
    return request({
        url:'/sourceSchool/excelImport',
        method: 'post',
        data,
        config : {
            headers:{'Content-Type': 'multipart/form-data'}
        }
    })
}

/**
 * 获取模板地址
 * 
 * @author lmh
 */
export const getExportSchoolTemplateUrl = (data) => {
    return request({
        url: `/sourceSchool/exportSchoolTemplate`,
        method: 'post',
        data
    })
}



