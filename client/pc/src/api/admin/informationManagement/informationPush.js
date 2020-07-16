/**
 * 信息推送
 */
import request from '../../../utils/request';


/**
 * 分页获取新闻列表
 * @param query
 * @author cwz
 */
export const newslist = (query) => {
        return request({
            url: `/news/list`,
            method: 'get',
            params: query
        })
    }
    /**
     *添加或编辑新闻
     * @param data
     * @author cwz
     */
export const addOrEdit = (data) => {
        return request({
            url: `/news/addOrEdit`,
            method: 'post',
            data
        })
    }
    /**
     *删除新闻
     * @param {string} newsId 新闻id
     * @author cwz
     */
export const deleteNews = (newsId) => {
        return request({
            url: `/news/delete/${newsId}`,
            method: 'delete',
        })
    }
    /**
     *发布新闻
     * @param {string} newsId 新闻id
     * @author cwz
     */
export const releaseNews = (newsId) => {
        return request({
            url: `/news/release/${newsId}`,
            method: 'post',
        })
    }
    /**
     * 获取新闻详情
     * @param newsId
     * @author cwz
     */
export const detail = (newsId) => {
    return request({
        url: `/news/detail/${newsId}`,
        method: 'get',
    })
}