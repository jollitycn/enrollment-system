<template>
    <div class="page-container">
        <div class="crumbs">
            <el-breadcrumb separator=">">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item>招生过程管理</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path: '/publicityPlanManagement' }">宣讲计划管理</el-breadcrumb-item>
                <el-breadcrumb-item>{{title}}宣讲计划</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div>
            <pSee v-if="title === '查看'"></pSee>
            <pEditOrSee v-if="title !== '查看'"></pEditOrSee>
        </div>
    </div>
</template>

<script>
    import pSee from './child/see';
    import pEditOrSee from './child/editOrAdd'
    export default {
        name: "doPublicityPlanManagement",
        components:{
            pSee,
            pEditOrSee
        },
        data() {
            return {
                type: {
                    'see': '查看',
                    'edit': '修改',
                    'add' : '新增'
                },
                title: '查看'
            }
        },
        created() {
            let type = this.$route.params.type
            this.type[type] && (this.title = this.type[type])
        }
    }
</script>

<style scoped>

</style>