<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator=">">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>招生过程管理</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path : '/consultingSchoolManagement' }">到校咨询管理</el-breadcrumb-item>
        <el-breadcrumb-item>查看到校咨询信息</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="newcontent">
      <div v-for="(item,index) in data" :key="index" class="newheader" v-show=" isO !=2 || index ==0 ">
        <span class="header-first">{{item.title}}</span><span class="header-second">{{item.value}}</span>
      </div>
      <div>
        <el-button class="edit-btn" type="primary" @click="editConsulting()">编辑</el-button>
      </div>
      <div class="newheader">
        <span class="header-first">行程安排</span>
        <div v-show=" isO !=2 " class="newheaderHtml" style=""><span style="display: inline-block;" class="header-second"
            v-html="htmlTest"></span>
        </div>
        <span v-show=" isO ==2" class="header-second"> 行程已取消 </span>
      </div>
    </div>
    <div style="text-align: right;margin-top: 60px;">
      <el-button type="success" @click="addAttend" :disabled="!from.consultationRegisterIdList.length">确认签到</el-button>
    </div>
    <div class="newtable">
      <el-table :data="tableData" stripe @selection-change="handleSelectionChange" style="width: 100%">
        <el-table-column type="selection" :selectable='checkboxT' disabled='true' width="55">
        </el-table-column>
        <el-table-column prop="attenderName" label="咨询人" width="180">
        </el-table-column>
        <el-table-column prop="attendTime" label="到校时间">
        </el-table-column>
        <el-table-column prop="telephone" label="联系方式">
        </el-table-column>
        <el-table-column label="是否用餐">
          <template slot-scope="scope">
            <span class="message-title">{{scope.row.isLunch ?  '否':'是'}}</span>
          </template>
        </el-table-column>
        <el-table-column label="是否签到">
          <template slot-scope="scope">
            <span class="message-title">{{scope.row.isAttend ? '是': '否'}}</span>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div class="newpage">
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="params.pageNum" :page-sizes="[10, 20, 50, 100]" :page-size="params.pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>
    <div class="newBtn">
      <el-button type="info" @click="clo">关闭</el-button>
      <el-button type="success" @click="sub">总结</el-button>

    </div>
  </div>

</template>

<script>
  import { getConsultationDayDetail, getAttendList, addAttend } from "@/api/management/enrollmentProcessManagement/consultingSchoolManagement"
  export default {
    name: "seeConsultingSchoolManagement",
    data() {
      return {
        isO: '',
        params: {
          pageNum: 1,
          pageSize: 10,
          date: '',
          consultationId: ''
        },
        total: 0,
        tableData: [],
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
        }, {
          id: 4,
          title: '联系方式',
          name: 'telephone',
          value: '15212345678'
        }],
        currentPage4: 4,
        htmlTest: '<p>312312</p>',
        from: {
          consultationRegisterIdList: []
        }
      }
    },
    methods: {
      checkboxT(row, index) {
        console.log(row, 889);
        if (row.isAttend == 1) {
          return 0
        } else {
          return 1
        }
      },
      //获取当前日期的详细信息
      getConsultationDayDetail() {
        getConsultationDayDetail(this.param).then(res => {
          this.list = res.data;
          let data = this.data;
          this.htmlTest = this.list.schedule
          for (let i = 0; i < data.length; i++) {
            for (let key in res.data) {
              if (data[i].name == key) {
                if (key == 'isOpenday') {
                  data[i].value = res.data[key] ? '是' : '否'
                } else {
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
      //编辑页面跳转
      editConsulting() {
        let date = this.$route.query.date
        let isOpenday = this.$route.query.isOpenday
        let consultationId = this.$route.query.consultationId
        let openid = this.$route.query.openid
        this.$router.push({
          path: `/addConsultingSchoolManagementDay`,
          query: {
            date,
            isOpenday,
            consultationId: consultationId,
            openid
          }
        })
      },
      //获取咨询签到列表
      getAttendList() {
        getAttendList(this.params).then(res => {
          this.tableData = res.data.records;
          this.params.pageNum = res.data.current;
          this.params.pageSize = res.data.size
          this.total = res.data.total
        }).catch(err => {
          console.log(err);
        })
      },

      //确认签到
      addAttend() {
        addAttend(this.from).then(res => {
          this.$message.success("签到成功")
          this.getAttendList();
          this.getConsultationDayDetail()
          console.log(res);
        }).catch(err => {
          this.$message.error("签到失败")
          console.log(err);
        })
      },
      //去总结页面
      sub() {
        this.$router.push({
          path: `/submitConsultingSchoolManagement`,
          query: this.param
        })
      },
      //关闭
      clo() {
        this.$router.push({
          path: `/consultingSchoolManagement`,
        })
      },
      //页码变动
      handleSizeChange(val) {
        this.params.pageNum = 1;
        this.params.pageSize = val
        this.getAttendList()
        console.log(`每页 ${val} 条`);
      },

      //页码数量变动
      handleCurrentChange(val) {
        this.params.pageNum = val;
        this.getAttendList()
      },

      //选择
      handleSelectionChange(e) {
        console.log(e);
        let arr = [];
        for (let i = 0; i < e.length; i++) {
          arr.push(e[i].consultationRegisterId)
        }
        this.from.consultationRegisterIdList = arr
      }
    },
    created() {
      let data = this.$route.query
      this.param = {
        date: data.date,
        consultationId: data.consultationId
      };
      this.params.date = data.date
      this.params.consultationId = data.consultationId
      this.isO = data.isOpenday
      this.getConsultationDayDetail()
      this.getAttendList()
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

  .newcontent>.newheader>.newheaderHtml {
    padding-left: 140px;
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

  .edit-btn {
    margin-left: 64px;
    margin-bottom: 40px;
  }
</style>