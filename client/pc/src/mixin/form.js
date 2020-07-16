import Vue from 'vue'
/**
 * 表单相关混入处理
 * @author lmh
 */
Vue.mixin({
    methods:{
        /**
         * 重置element表单数据
         * @param {*} formRef el-form标签的ref值
         */
        elFormReset(formRef){
            if(this.$refs[formRef]){
                this.$refs[formRef].resetFields();
            }
        },
        
    }
})