import request from '@/utils/request';

//系统地区管理
/**
 * 省份数据
 * @param {*} query 
 */
export const getProvinceList = (query) => {
    return request({
        url: '/sys-region/getProvinceList',
        method: 'get',
        params: query
    })
}

/**
 * 城市数据
 * @param {provinceId} query 
 */
export const getCityList = (provinceId) => {
    return request({
        url: `/sys-region/getCityList/${provinceId}`,
        method: 'get'
    })
}

/**
 * 区域数据
 * @param {cityId} query 
 */
export const getDistrictList = (cityId) => {
    return request({
        url: `/sys-region/getDistrictList/${cityId}`,
        method: 'get'
    })
}


