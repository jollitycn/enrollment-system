import request from '../../../utils/request';

// 获取全部的字典列表
export const getAllDictionaryList = (query) => {
    return request({
        url: '/sys-dictionary/listAllData',
        method: 'get',
        params: query
    })
};

// 获取信息类型列表
export const getMessageTypeList = () => {
  return request({
      url: '/news/getMessageTypeList',
      method: 'get'
  })
};

// 获取指定节点下所有的数据字典列表
export const sysDictionaryList = (query) => {
    return request({
        url: '/sys-dictionary/list',
        method: 'get',
        params: query
    })
};
//更新数据字典
export const sysDictionaryUpdate = (data) => {
    return request({
        url: '/sys-dictionary/update',
        method: 'post',
        data
    })
}
//获取字典详情
export const sysDictionaryDetail = (id) => {
    return request({
        url: `/sys-dictionary/detail/${id}`,
        method: 'get',
    })
}

//删除字典
export const sysDictionaryDelete = (id) => {
    return request({
        url: `/sys-dictionary/delete/${id}`,
        method: 'delete',
    })
}

//新增字典
export const sysDictionaryAdd = (data) => {
    return request({
        url: `/sys-dictionary/add`,
        method: 'put',
        data
    })
}