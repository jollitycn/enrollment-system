<template>
  <div class="ma page-container">
    <el-row>
      <el-col :span="24">
        <el-col :span="17">
          <div class="newcontent newheader">
            <div v-for="(item,index) in data" :key="index" class="newheader">
              <span class="header-first">{{item.title}}</span><span class="header-second">{{item.value}}</span>
            </div>
          </div>
          <div class="newcontent">
            <div v-for="(item,index) in data2" :key="index" class="newheader">
              <span class="header-first">{{item.title}}</span><span class="header-second"
                :style="item.class">{{item.value}}</span>
            </div>
          </div>
        </el-col>
        <el-col :span="6" :pull="1">
          <div v-for="(item,index) in numberData" style="display: flex;">
            <el-rate :key="index" v-model="item.performance" disabled>
            </el-rate>
            <span style="margin-left: 20px">{{item.amount}}</span>
          </div>
        </el-col>
      </el-col>
    </el-row>
    <div class="leftBorder">
      <leftBorder title="反馈信息"></leftBorder>
    </div>
    <div class="newbuttom">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="creatorName" label="反馈人" width="180">
        </el-table-column>
        <el-table-column prop="studentTelephone" label="联系方式" width="180">
        </el-table-column>
        <el-table-column prop="createTime" label="反馈时间" width="180">
        </el-table-column>
        <el-table-column prop="feedbackContent" label="反馈内容">
        </el-table-column>
      </el-table>
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
        :current-page="params.pageNum" :page-sizes="[10, 20, 50, 100]" :page-size="params.pageSize"
        layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
  import { getDetail, preachSummary, feedBackCount } from '@/api/management/enrollmentProcessManagement/publicityPlanManagement'
  export default {
    name: "see",
    data() {
      return {
        numberData: [
          { performance: 1, amount: 20 },
          { performance: 2, amount: 20 },
          { performance: 3, amount: 20 },
          { performance: 4, amount: 20 },
          { performance: 5, amount: 20 },
        ],
        tableData: [],
        id: 0,
        data: [
          { id: 5, title: '宣讲时间', name: 'preachTime', value: '' },
          { id: 1, title: '主讲人', name: 'speakerName', value: '' },
          { id: 3, title: '计划人数', name: 'planAmount', value: '' },
          { id: 4, title: '联系方式', name: 'telephone', value: '' }
        ],
        data2: [
          { id: 5, title: '协助人', name: "assistants", value: '' },
          { id: 1, title: '主题', name: 'title', value: '' },
          { id: 3, title: '宣讲地点', name: 'address', value: '' },
          { id: 4, title: '面向学校', name: "sourceSchools", value: '' },
          { id: 2, title: '备注', name: 'comment', value: '', class: "width:80%" }
        ],
        params: {
          pageNum: 1,
          pageSize: 10,
          preachPlanId: ''
        },
        total: 0,
      }
    },
    methods: {

      //获取信息详情
      getDetail() {
        getDetail(this.id).then(res => {
          let data = res.data;
          for (let key in data) {
            for (let i = 0; i < this.data.length; i++) {
              if (key == this.data[i].name) {
                this.data[i].value = data[key];
                break;
              }
            }
            for (let i = 0; i < this.data2.length; i++) {
              if (key == this.data2[i].name) {
                // 将协助人以及面向学校的值提取出来
                let people = ""
                let sch = ""
                if (this.data2[i].name == "assistants" || this.data2[i].name == "sourceSchools") {
                  for (let index = 0; index < data[key].length; index++) {
                    //协助人赋值
                    console.log(key);
                    if (key == "assistants") {
                      let people = people + "," + data[key][index].value
                      this.data2[i].value = people.replace("undefined,", "")
                    }
                    //面向学校赋值
                    else if (key == "sourceSchools") {
                      let sch = sch + "," + data[key][index].value
                      this.data2[i].value = sch.replace("undefined,", "")
                    }
                  }
                } else {
                  this.data2[i].value = data[key];
                }
                break;
              }
            }
          }
        }).catch(err => {
          console.error(err);
        })
      },

      //获取反馈信息列表
      preachSummary() {
        preachSummary(this.params).then(res => {
          this.tableData = res.data.records;
          this.params.pageNum = res.data.current;
          this.params.pageSize = res.data.size
          this.total = res.data.total
        }).catch(err => {
          console.log(err);
        })
      },

      //页码变动
      handleSizeChange(val) {
        this.params.pageNum = 1;
        this.params.pageSize = val
        this.preachSummary()
        console.log(`每页 ${val} 条`);
      },

      //页码数量变动
      handleCurrentChange(val) {
        this.params.pageNum = val;
        this.preachSummary()
      },

      //获取打分数据
      feedBackCount() {
        let data = {
          preachPlanId: this.id
        }
        feedBackCount(data).then(res => {
          console.log(res);
          this.numberData = res.data
        }).catch(err => {
          console.log(err);
        })
      },
    },
    created() {
      this.id = this.$route.params.id;
      this.params.preachPlanId = this.id
      //获取详细信息
      this.getDetail();
      //获取反馈信息列表
      this.preachSummary()
      //获取打分数据
      this.feedBackCount()
      console.log(this.$route.params, 888);
    }
  }
</script>

<style scoped>
  .ma {
    border-radius: 10px;
    padding: 20px;
    background-color: #fff;
    color: #777;
  }

  .ma>>>.el-table th {
    background-color: rgb(2, 154, 230) !important;
    color: #fff;
    border-right-color: rgb(2, 154, 230);
  }

  .newcontent {
    background-color: #fff;
    padding-top: 20px;
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
    height: 25px;
    line-height:25px;
  }

  .newcontent>.newheader>.header-second {
    margin-top: 1px;
    height: 23px;
    line-height:23px;
  }

  .newheader {
    display: flex;
    flex-wrap: wrap;
  }

  .newheader>div {
    width: 40%;
  }

  .leftBorder {
    margin-top: 50px;
  }

  .newbuttom {
    margin-top: 20px;
  }

  .newbuttom>div {
    border: 1px solid #eee;
    padding: 10px;
    border-radius: 10px;
    margin-bottom: 20px;
    box-shadow: 5px 5px 10px #eee;
  }

  .newbuttom-top {
    display: flex;
    flex-wrap: wrap;
    margin-bottom: 15px;
    border-bottom: 1px solid #eee;
    padding-bottom: 10px;
  }

  .newbuttom-top>div {
    width: 33%;
  }

  .newbuttom-top>div>.newbuttom-top-left {
    margin-right: 20px;
  }
</style>