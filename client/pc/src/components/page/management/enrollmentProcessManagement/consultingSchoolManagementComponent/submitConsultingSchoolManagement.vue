<template>
    <div>
        <div class="crumbs">
            <el-breadcrumb separator=">">
                <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item>招生过程管理</el-breadcrumb-item>
                <el-breadcrumb-item :to="{ path : '/consultingSchoolManagement' }">到校咨询管理</el-breadcrumb-item>
                <el-breadcrumb-item>咨询总结</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <div class="newcontent">
            <div v-for="(item,index) in data" :key="index" class="newheader">
                <span class="header-first">{{item.title}}</span><span class="header-second">{{item.value}}</span>
            </div>
            <div class="newheader">
                <span class="header-first">行程安排</span>
                <div class="newheaderHtml" style=""><span style="display: inline-block;" class="header-second" v-html="htmlTest"></span></div>
            </div>
            <div style="margin-top: 50px;padding-bottom: 50px;">
            <el-form :model="ruleForm"  :rules="rules" ref="ruleForm" label-width="120px" class="demo-ruleForm">
                <el-form-item label="家长反馈汇总" >
                    <el-input type="textarea" :rows="7" v-model="ruleForm.consultationContent"></el-input>
                </el-form-item>
                <el-form-item label="总结" >
                    <el-input type="textarea" :rows="7" v-model="ruleForm.summary"></el-input>
                </el-form-item>
                <el-form-item v-show="!isShow">
                    <el-button type="primary" @click="addConsultationFeedback">立即总结</el-button>
                </el-form-item>
            </el-form>
            </div>
        </div>

    </div>

</template>

<script>
    import { getConsultationDayDetail, addConsultationFeedback }  from "@/api/management/enrollmentProcessManagement/consultingSchoolManagement"
    export default {
        name: "submitConsultingSchoolManagement",
        data() {
            return {
                data: [{
                    id: 5,
                    title: '日期',
                    name: 'opendayDate',
                    value: '2020-04-08'
                }, {
                    id: 0,
                    title: '接待日',
                    name: 'isOpenday',
                    value: '是'
                }, {
                    id: 1,
                    title: '接待人',
                    name: 'receptionistName',
                    value: '张老师'
                }, {
                    id: 2,
                    title: '实际人数',
                    name: 'actualAmount',
                    value: '38'
                },{
                    id: 4,
                    title: '联系方式',
                    name: 'telephone',
                    value: '15212345678'
                }],
                ruleForm: {
                    consultationContent: '',
                    summary:''
                },
                rules: {
                    desc: [{
                        required: true,
                        message: '请填写活动形式',
                        trigger: 'blur'
                    }]
                },
                htmlTest:'',
                isShow: true,
            }
        },
        methods: {
            //获取当前日期的详细信息
            getConsultationDayDetail() {
                getConsultationDayDetail(this.param1).then(res => {
                    this.list = res.data;
                    let data = this.data;
                    this.htmlTest = this.list.schedule;
                    this.ruleForm.consultationContent = this.list.consultationContent;
                    this.ruleForm.summary = this.list.summary
                    this.isShow = Boolean(this.list.isSummary)
                    for(let i =0; i< data.length; i++) {
                        for (let key in res.data) {
                            if(data[i].name == key) {
                                if(key=='isOpenday') {
                                    data[i].value = res.data[key] ? '是' : '否'
                                }else {
                                    data[i].value = res.data[key]
                                }
                                break;
                            }
                        }
                    }
                    console.log(res, '获取当前日期的详细信息');
                }).catch(err => {
                    console.log(err);
                })
            },

            //新增咨询总结
            addConsultationFeedback() {
                let data = {...this.ruleForm,...this.param}
                console.log(data);
                addConsultationFeedback(data).then(res => {
                    this.$message.success("总结成功")
                    this.isShow = true;
                    console.log(res);
                }).catch(err => {
                    console.log(err);
                })
            }

        },
        created() {
            let data = this.$route.query
            this.param1 = {
                date: data.date,
                consultationId: data.consultationId
            };
            this.param = {
                consultationDate: data.date,
                consultationId: data.consultationId
            };
            this.getConsultationDayDetail()
        }
    }
</script>

<style scoped>
    .newcontent {
        margin-top: 20px;
        background-color: #fff;
        padding-top: 20px;
        border-radius: 10px;
    }
    
    .newcontent>.newheader {
        color: #777;
    }
    
    .newcontent>.newheader>.header-first {
        width: 120px;
        display: inline-block;
        text-align: right;
        margin-right: 20px;
        margin-bottom: 14px;
    }
    .newcontent>.newheader> .newheaderHtml {
        padding-left: 125px;
        padding-bottom: 40px;
    }
    
    .newtable {
        margin-top: 20px;
    }
    
    .newpage {
        margin-top: 20px;
        text-align: right;
    }
    
    .newBtn {
        text-align: center;
        margin: 50px 0 20px;
    }
</style>