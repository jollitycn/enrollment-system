<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <div>
        <el-col :span="24">
          <div class="analysis-content-title">
            <div class="login-title">总体招生进度</div>
            <div class="analysis-content-title-content">
              <div class="analysis-content-title-content-top">
                <div class="analysis-content-title-content-top-item">
                  <span class="analysis-content-title-content-top-item-top">实际人数</span>
                  <span class="analysis-content-title-content-top-item-number">{{
                enrollmentProgressData.numberCompletions
              }}</span>
                </div>
                <div class="analysis-content-title-content-top-item">
                  <span class="analysis-content-title-content-top-item-top">计划人数</span>
                  <span class="analysis-content-title-content-top-item-number">{{
                enrollmentProgressData.planAllNumber
              }}</span>
                </div>
              </div>
              <div class="analysis-content-title-content-bottom">
                <div class="analysis-content-title-content-bottom-bg">
                  <div class="analysis-content-title-content-bottom-bg-ac" :style="{
                  width:
                    parseInt(
                      (enrollmentProgressData.numberCompletions /
                        (enrollmentProgressData.planAllNumber || 1)) *
                        100
                    ) + '%'
                }">
                    {{ enrollmentProgressData.numberCompletions }}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </el-col>
      </div>
      <el-col :span="12">
        <div class="analysis-content-title">
          <div class="login-title">区域招生进度</div>
          <div class="analysis-content-sy-content" ref="wid">
            <div v-show="!enrollmentAnalysisShow || !facultyAdmissionsProgressShow"
              :style="{ width: '100%', height: '320px' ,lineHeight: '320px',textAlign: 'center',color: '#666'}"
              class="analysis-content-sy-content-show">
              暂无数据
            </div>
            <div class="sy" v-show="enrollmentAnalysisShow && facultyAdmissionsProgressShow" ref="sy"
              :style="{ width: '100%', height: '320px' }"></div>
          </div>
        </div>
      </el-col>
      <el-col :span="12">
        <div class="analysis-content-title">
          <div class="login-title">院系招生进度</div>
          <div class="analysis-content-sy-content">
            <div v-show="!enrollmentAnalysisShow || !facultyAdmissionsProgressShow"
              :style="{ width: '100%', height: '320px' ,lineHeight: '320px',textAlign: 'center',color: '#666'}"
              class="analysis-content-sy-content-show">
              暂无数据
            </div>
            <div class="sy" v-show="enrollmentAnalysisShow && facultyAdmissionsProgressShow" ref="oy"
              :style="{ width: '100%', height: '320px' }"></div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import Schart from 'vue-schart';
  import { enrollmentProgress, regionalAdmissionsProgress, facultyAdmissionsProgress } from "@/api/admin/Dashboard/Dashboard"
  import echarts from "echarts";
  export default {
    name: 'dashboard',
    data() {
      return {
        name: "Dashboard",
        enrollmentProgressData: {
          numberCompletions: 0,
          planAllNumber: 0
        },
        enrollmentAnalysisSyOption: {
          color:['#209ffa','#ffab19'],
          tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
              type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
          },
          legend: {
            data: ['计划人数', '实际人数',]
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          xAxis: [
            {
              type: 'category',
              data: [],
              "axisLabel": {
                interval: 0
              }
            },
          ],
          yAxis: [
            {
              type: 'value'
            }
          ],
          series: [
            {
              name: '计划人数',
              type: 'bar',
              data: [],
              barWidth: 30,//柱图宽度
            },
            {
              name: '实际人数',
              type: 'bar',
              data: [],
              barWidth: 30,//柱图宽度
              markLine: {
                lineStyle: {
                  type: 'dashed'
                },

              }
            },

          ]
        },
        enrollmentAnalysisShow: false,
        facultyAdmissionsProgressOption: {
          color:['#209ffa','#ffab19'],
          tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
              type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
          },
          legend: {
            data: ['计划人数', '实际人数',]
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          xAxis: [
            {
              type: 'category',
              data: [],
              "axisLabel": {
                interval: 0
              }
            }
          ],
          yAxis: [
            {
              type: 'value'
            }
          ],
          series: [
            {
              name: '计划人数',
              type: 'bar',
              data: [],
              barWidth: 30,//柱图宽度
            },
            {
              name: '实际人数',
              type: 'bar',
              data: [],
              barWidth: 30,//柱图宽度
              markLine: {
                lineStyle: {
                  type: 'dashed'
                },

              }
            },

          ]
        },
        facultyAdmissionsProgressShow: false,
      };
    },
    components: {
      Schart
    },
    methods: {
      //总体招生进度
      enrollmentProgress() {
        enrollmentProgress().then(res => {
          console.log(res);
          if (res.data) {
            this.enrollmentProgressData = res.data
          }
        })
      },

      //区域招生进度
      regionalAdmissionsProgress() {
        regionalAdmissionsProgress().then(res => {
          console.log(res, "区域招生进度");
          let w = this.$refs.wid.offsetWidth
          console.log(w)
          this.$refs.sy.style.width = w + 'px';
          let myChart = echarts.init(this.$refs.sy);
          let option = this.enrollmentAnalysisSyOption;
          let data = res.data;
          if (data.length) {
            let groupNameArr = []; //组别名称
            let targetAmountArr = []; // 计划人数
            let actualNumberArr = []; // 实际人数
            for (let i = 0; i < data.length; i++) {
              groupNameArr.push(data[i].groupName);
              targetAmountArr.push(data[i].targetAmount);
              actualNumberArr.push(data[i].actualNumber);
            }
            option.xAxis[0].data = groupNameArr;
            option.series[0].data = targetAmountArr;
            option.series[1].data = actualNumberArr;
            console.log(window.innerWidth, myChart.style);

            myChart.setOption(option);
            this.enrollmentAnalysisShow = true;
          } else {
            this.enrollmentAnalysisShow = false;
          }
        }).catch(() => {
          this.enrollmentAnalysisShow = false;
        })
      },
      //院系招生进度
      facultyAdmissionsProgress() {
        facultyAdmissionsProgress().then(res => {
          console.log(res, "院系招生进度");
          let w = this.$refs.wid.offsetWidth
          this.$refs.oy.style.width = w + 'px';
          let myChart = echarts.init(this.$refs.oy);
          let option = this.facultyAdmissionsProgressOption;
          let data = res.data || [];
          if (data.length) {
            let groupNameArr = []; //组别名称
            let targetAmountArr = []; // 计划人数
            let actualNumberArr = []; // 实际人数
            for (let i = 0; i < data.length; i++) {
              if (data[i].targetAmount!=null && data[i].actualNumber!=null) {
                groupNameArr.push(data[i].collegeName);
                targetAmountArr.push(data[i].targetAmount || 0);
                actualNumberArr.push(data[i].actualNumber || 0);
              }
            }
            if (groupNameArr.length) {
              option.xAxis[0].data = groupNameArr;
              option.series[0].data = targetAmountArr;
              option.series[1].data = actualNumberArr;
              myChart.setOption(option);
              this.facultyAdmissionsProgressShow = true;
            } else {
              this.facultyAdmissionsProgressShow = false;
            }
          } else {
            this.facultyAdmissionsProgressShow = false;
          }
        }).catch(() => {
          this.facultyAdmissionsProgressShow = false;
        })
      }
    },
    created() {

    },
    mounted() {
      this.enrollmentProgress();
      this.regionalAdmissionsProgress();
      this.facultyAdmissionsProgress()
    }
  };
</script>


<style scoped>
  .analysis-content-title {
    background-color: #ffffff;
    padding: 15px;
    border-radius: 6px;
    margin-bottom: 20px
  }

  .analysis-content-sy-content {
    position: relative;
    width: 100%;
  }

  analysis-content-sy-content .analysis-content-sy-content-show {
    z-index: 9;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .login-title {
    color: #999;
    font-size: 20px;
    font-weight: bold;
    margin-top: 5px;
    text-align: center;
    margin-bottom: 20px;
  }

  .analysis-content-title-content {
    margin-top: 40px
  }

  .analysis-content-title-content-top {
    display: flex;
    justify-content: space-around;
  }

  .analysis-content-title-content-top-item {
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  .analysis-content-title-content-top-item-top {
    color: #999;
    font-size: 20px;
  }

  .analysis-content-title-content-top-item-number {
    color: #666;
    font-size: 20px;
    font-weight: bold;
  }

  .analysis-content-title-content-bottom {
    margin-bottom: 20px;
    margin-top: 40px;
  }

  .analysis-content-title-content-bottom-bg {
    position: relative;
    width: 100%;
    height: 15px;
    background-color: rgba(242, 242, 242, 1);
    border-radius: 15px;
  }

  .analysis-content-title-content-bottom-bg-ac {
    position: absolute;
    width: 85%;
    background: linear-gradient(270deg, rgba(0, 204, 255, 1) 0%, rgba(1, 162, 255, 1) 100%);
    z-index: 9;
    height: 15px;
    border-radius: 15px;
    text-align: right;
    color: #c8c9cc;
    margin-left: 4px;
  }


  .el-row {
    margin-bottom: 20px;
  }

  .grid-content {
    display: flex;
    align-items: center;
    height: 100px;
  }

  .grid-cont-right {
    flex: 1;
    text-align: center;
    font-size: 14px;
    color: #999;
  }

  .grid-num {
    font-size: 30px;
    font-weight: bold;
  }

  .grid-con-icon {
    font-size: 50px;
    width: 100px;
    height: 100px;
    text-align: center;
    line-height: 100px;
    color: #fff;
  }

  .grid-con-1 .grid-con-icon {
    background: rgb(45, 140, 240);
  }

  .grid-con-1 .grid-num {
    color: rgb(45, 140, 240);
  }

  .grid-con-2 .grid-con-icon {
    background: rgb(100, 213, 114);
  }

  .grid-con-2 .grid-num {
    color: rgb(45, 140, 240);
  }

  .grid-con-3 .grid-con-icon {
    background: rgb(242, 94, 67);
  }

  .grid-con-3 .grid-num {
    color: rgb(242, 94, 67);
  }

  .user-info {
    display: flex;
    align-items: center;
    padding-bottom: 20px;
    border-bottom: 2px solid #ccc;
    margin-bottom: 20px;
  }

  .user-avator {
    width: 120px;
    height: 120px;
    border-radius: 50%;
  }

  .user-info-cont {
    padding-left: 50px;
    flex: 1;
    font-size: 14px;
    color: #999;
  }

  .user-info-cont div:first-child {
    font-size: 30px;
    color: #222;
  }

  .user-info-list {
    font-size: 14px;
    color: #999;
    line-height: 25px;
  }

  .user-info-list span {
    margin-left: 70px;
  }

  .mgb20 {
    margin-bottom: 20px;
  }

  .todo-item {
    font-size: 14px;
  }

  .todo-item-del {
    text-decoration: line-through;
    color: #999;
  }

  .schart {
    width: 100%;
    height: 300px;
  }

  .test {
    margin: 75px auto 0;
    width: 575px;
    background-color: white;
    padding: 45px;
  }

  .test>p {
    height: 33px;
    line-height: 33px;
  }

  .test>p:last-child {
    margin-top: 30px;
  }
</style>