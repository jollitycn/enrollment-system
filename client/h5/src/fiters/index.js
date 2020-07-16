import Vue from 'vue'
import config from '@/config'
/**
 * 图片地址过滤器
 * 用来拼接设置访问的服务器地址
 * 
 * 在图片显示的地方加上
 * eg:   <img :src="src | imgUrlFilter">
 * @author lmh
 */
Vue.filter('imgUrlFilter',val=>{
    //val
    return `${config.rootPath}${config.imgDir}${val}`
})