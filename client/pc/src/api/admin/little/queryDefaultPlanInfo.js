import request from '@/utils/request';


/**
 * 获取默认招生计划基本信息
 */
export const queryDefaultPlanInfo = () => {
    return request({
        url: '/plan/queryDefaultPlanInfo',
        method: 'get'
    })
}