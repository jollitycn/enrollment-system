import request from '../../utils/request';



//分页获取招生区域信息列表
export const listGroupInfoPage = (query) => {
        return request({
            url: '/groupInfo/listGroupInfoPage',
            method: 'get',
            params: query
        })
    }

    //新增分组信息
export const addGroup = (data) => {
        return request({
            url: '/group/addGroup',
            method: 'post',
            data
        })
    }

    //删除分组
export const deleteGroup = (id) => {
        return request({
            url: `/group/deleteGroup/${id}`,
            method: 'delete',
        })
    }

    //编辑分组信息
export const updateGroup = (data) => {
        return request({
            url: '/group/updateGroup',
            method: 'put',
            data
        })
    }

    //分页获取招生区域信息列表
export const listGroupPage = (query) => {
        return request({
            url: '/group/listGroupPage',
            method: 'get',
            params: query
        })
    }

    //新增招生区域信息
export const addGroupInfo = (data) => {
        return request({
            url: '/groupInfo/addGroupInfo',
            method: 'post',
            data
        })
    }
    export const copyGroupInfo = (data) => {
      return request({
          url: '/groupInfo/copyGroupInfo',
          method: 'put',
          data
      })
  }

    //获取指定招生区域信息
export const getGroupInfo = (id) => {
        return request({
            url: `/groupInfo/getGroupInfo/${id}`,
            method: 'get',
        })
    }

    //编辑招生区域信息
export const updateGroupInfo = (data) => {
        return request({
            url: '/groupInfo/updateGroupInfo',
            method: 'put',
            data
        })
    }

    //获取分组的详情
export const getGroupDetail = (id) => {
        return request({
            url: `/group/getGroupDetail/${id}`,
            method: 'get',
        })
    }

    //删除负责人
export const deleteCharger = (id) => {
        return request({
            url: `/group/deleteCharger/${id}`,
            method: 'delete',
        })
    }
    
    //删除分组下的学校
export const deleteGroupSourceRelation = (query) => {
        return request({
            url: `/group/deleteGroupSourceRelation`,
            method: 'delete',
            params: query
        })
    }
    //分组添加负责人
export const addCharger = (data) => {
        return request({
            url: '/group/addCharger',
            method: 'post',
            data
        })
    }
    //获取招生学校下的招生区域（数据字典配置）
export const listRecruitZone = () => {
        return request({
            url: `/group/listRecruitZone`,
            method: 'get',
        })
    }
    //分组添加负责学校
export const addGroupSourceRelation = (data) => {
        return request({
            url: '/group/addGroupSourceRelation',
            method: 'post',
            data
        })
    }
    //获取生源学校
export const listSourceSchool = (id) => {
    return request({
        url: `/group/listSourceSchool/${id}`,
        method: 'get',
    })
}