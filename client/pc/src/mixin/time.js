import Vue from 'vue'
/**
 * 表单相关混入处理
 * @author lmh
 */
Vue.mixin({
    filters:{
        resetTime (time) {
            if(!time) return '时间不详';
            if(time.indexOf('T') !== -1 ){
                return time.replace('T',' ')
            }
            return time
        },
        resetTime2 (time) {
            if(!time) return '时间不详';
            if(time.indexOf('T') !== -1 ){
                return (time.replace('T',' ')).substring(0,time.indexOf('.'))
            }
            return time
        },
    }
})