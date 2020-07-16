<template>
  <div class="analysis">
    <h5-header title="招生进度分析" :isLeft="true"></h5-header>
    <div>
      <van-dropdown-menu>
        <van-dropdown-item
          v-model="planQuery.semesterId"
          :options="planOption"
          @change="changeSemester"
        />
      </van-dropdown-menu>
    </div>
    <div class="analysis-content">
      <div class="analysis-content-title">
        <div class="login-title">
          <img class="login-title-img" src="@/assets/images/chart_title.png" />
          <div class="login-title-right">招生进度表</div>
        </div>
        <div class="analysis-content-title-content">
          <div class="analysis-content-title-content-top">
            <div class="analysis-content-title-content-top-item">
              <span class="analysis-content-title-content-top-item-top">实际人数</span>
              <span class="analysis-content-title-content-top-item-number">
                {{
                enrollmentProgressData.numberCompletions
                }}
              </span>
            </div>
            <div class="analysis-content-title-content-top-item">
              <span class="analysis-content-title-content-top-item-top">计划人数</span>
              <span class="analysis-content-title-content-top-item-number">
                {{
                enrollmentProgressData.planAllNumber
                }}
              </span>
            </div>
          </div>
          <div class="analysis-content-title-content-bottom">
            <div class="analysis-content-title-content-bottom-bg">
              <!-- 当数值小于7时默认背景值均为7% -->
              <div
                v-if="parseInt(
                      (enrollmentProgressData.numberCompletions /
                        (enrollmentProgressData.planAllNumber || 1)) *
                        100
                    )<=7"
                class="analysis-content-title-content-bottom-bg-ac textAli"
                :style="{
                  width: '7%'
                }"
              >
                <span
                  class="analysis-content-title-content-bottom-numberCompletions textAli"
                >{{ enrollmentProgressData.numberCompletions }}</span>
              </div>
              <!-- 当数值大于7时默认值回复正常 -->
              <div
                v-else
                class="analysis-content-title-content-bottom-bg-ac"
                :style="{
                  width:
                    parseInt(
                      (enrollmentProgressData.numberCompletions /
                        (enrollmentProgressData.planAllNumber || 1)) *
                        100
                    ) +'%'
                }"
              >
                <span
                  class="analysis-content-title-content-bottom-numberCompletions"
                >{{ enrollmentProgressData.numberCompletions+"" }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="analysis-content-title">
        <div class="login-title">
          <img class="login-title-img" src="@/assets/images/chart_title.png" />
          <div class="login-title-right">报名生源分析</div>
        </div>
        <div class="analysis-content-sy-content">
          <div
            v-show="!enrollmentAnalysisShow"
            :style="{ width: '100%', height: '320px' }"
            class="analysis-content-sy-content-show"
          >暂无数据</div>
          <div
            class="sy"
            v-show="enrollmentAnalysisShow"
            ref="sy"
            :style="{ width: '9rem', height: '320px' }"
          ></div>
        </div>
      </div>
      <div class="analysis-content-title">
        <div class="login-title">
          <img class="login-title-img" src="@/assets/images/chart_title.png" />
          <div class="login-title-right">报名阶段分析</div>
        </div>
        <div class="analysis-content-sy-content">
          <div
            v-show="!registrationStageShow"
            :style="{ width: '100%', height: '280px' }"
            class="analysis-content-sy-content-show"
          >暂无数据</div>
          <div
            v-show="registrationStageShow"
            class="jd"
            ref="jd"
            :style="{ width: '9rem', height: '280px' }"
          ></div>
        </div>
      </div>
      <div class="analysis-content-title">
        <div class="login-title">
          <img class="login-title-img" src="@/assets/images/chart_title.png" />
          <div class="login-title-right">各专业报名分析</div>
          <div class="login-title-analysis">报名/计划</div>
        </div>
        <div class="analysis-content-sy-content">
          <div
            v-show="!professionalRegistrationShow"
            :style="{ width: '100%', height: '320px' }"
            class="analysis-content-sy-content-show"
          >暂无数据</div>
          <div
            v-show="professionalRegistrationShow"
            class="analysis-content-title-content"
            v-for="(item,index) in professionalRegistrationOption"
            :key="index"
          >
            <div class="analysis-content-title-content-new">
              <div class="analysis-content-title-content-top-new">
                <div class="analysis-content-title-content-top-left">{{item.majorName}}</div>
                <div
                  class="analysis-content-title-content-top-right"
                >{{item.numberOfPeople}}/{{item.recruitAmount}}</div>
              </div>
              <div class="analysis-content-title-content-bottom" style="padding:0;margin-top:0">
                <div class="analysis-content-title-content-bottom-bg">
                  <!-- 当数值小于7时默认背景值均为7% -->
                  <div
                    v-if="parseInt(
                      (item.numberOfPeople /
                        (item.recruitAmount || 1)) *
                        100
                    )<=7"
                    class="analysis-content-title-content-bottom-bg-ac textAli"
                    :style="{
                  width: 7 + '%'
                }"
                  >
                    <span class="analysis-content-title-content-bottom-numberCompletions textAli">
                      <!-- {{ enrollmentProgressData.numberCompletions+"" }}人</span> -->
                      {{ item.numberOfPeople+"" }}
                    </span>
                  </div>
                  <!-- 当数值大于7时默认值回复正常 -->
                  <div
                    v-else
                    class="analysis-content-title-content-bottom-bg-ac"
                    :style="{
                  width:
                    parseInt(
                      (item.numberOfPeople /
                        (item.recruitAmount || 1)) *
                        100
                    ) +'%'
                }"
                  >
                    <span
                      class="analysis-content-title-content-bottom-numberCompletions"
                    >{{ item.numberOfPeople+"" }}</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <h5Tabbar></h5Tabbar>
  </div>
</template>

<script>
professionalRegistration;
import Header from "@/components/cheader";
import Tabbar from "@/components/ctabbar";
import echarts from "echarts";
import Vue from "vue";
import {
  enrollmentProgress,
  enrollmentAnalysis,
  registrationStage,
  professionalRegistration,
  queryAllPublishPlanAPP,
  getPlan
} from "@/api/analysis/analysis";
import { DropdownMenu, DropdownItem } from "vant";
Vue.use(DropdownMenu);
Vue.use(DropdownItem);
export default {
  name: "analysis",
  components: {
    h5Header: Header,
    h5Tabbar: Tabbar
  },
  data() {
    return {
      planOption: [],
      planQuery: {
        semesterId: "",
        groupId: ""
      },
      enrollmentProgressData: {
        numberCompletions: 0,
        planAllNumber: 0
      },
      enrollmentAnalysisSyOption: {
        tooltip: {
          trigger: "item",
          formatter: "{b} : <br/> {c} ({d}%)"
        },
        legend: {
          icon: "circle",
          left: "center",
          top: "bottom",
          data: [
            "宝安中心",
            "上寮学校",
            "中英公学",
            "宝安实验中学",
            "清华实验学校民办班",
            "清华实验学校办公室"
          ]
        },
        series: [
          {
            type: "pie",
            radius: "55%",
            center: ["50%", "42%"],
            data: [
              { value: 335, name: "宝安中心" },
              { value: 310, name: "上寮学校" },
              { value: 234, name: "中英公学" },
              {
                value: 135,
                name: "宝安实验中学"
              },
              {
                value: 548,
                name: "清华实验学校民办班"
              },
              {
                value: 135,
                name: "清华实验学校办公室"
              }
            ],
            label: {
              normal: {
                formatter: "{c}"
              }
            },
            itemStyle: {
              normal: {
                color: function(params) {
                  //设置柱状图的显示颜色
                  var colorList = [
                    "#2d52ee",
                    "#ffab19",
                    "#00ccff",
                    "#fe713a",
                    "#ffab19",
                    "#fe713a",
                    "#45d577",
                    "#6f92d8"
                  ];
                  return colorList[params.dataIndex];
                }
              }
            },
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)"
              }
            }
          }
        ]
      },
      enrollmentAnalysisShow: false,
      registrationStageOption: {
        xAxis: {
          type: "category",
          data: ["意向生", "登记生", "定位生", "全教生"],
          axisLabel: {
            textStyle: {
              //改变刻度字体样式
              color: "#999"
            }
          },
          axisTick: {
            show: false
          },
          axisLine: {
            lineStyle: {
              color: "#999"
            }
          }
        },
        yAxis: {
          type: "value",
          axisLabel: {
            textStyle: {
              //改变刻度字体样式
              color: "#999"
            }
          },
          axisLine: {
            show: false
          },
          axisTick: {
            show: false
          }
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            // 坐标轴指示器，坐标轴触发有效
            type: "shadow" // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        series: [
          {
            data: [120, 200, 150, 80],
            type: "bar",
            barWidth: 30,
            itemStyle: {
              normal: {
                //好，这里就是重头戏了，定义一个list，然后根据所以取得不同的值，这样就实现了，
                color: function() {
                  // build a color map as your need.
                  //设置柱状图的显示颜色
                  var colorList = {
                    type: "linear",
                    x: 0,
                    y: 0,
                    x2: 0,
                    y2: 1,
                    colorStops: [
                      {
                        offset: 0,
                        color: "#00CCFF" // 0% 处的颜色
                      },
                      {
                        offset: 1,
                        color: "#01A2FF" // 100% 处的颜色
                      }
                    ],
                    globalCoord: false // 缺省为 false
                  };
                  // return colorList[params.dataIndex];
                  return colorList;
                }
              }
            }
          }
        ]
      },
      registrationStageShow: false,
      professionalRegistrationOption: [],
      professionalRegistrationShow: false
    };
  },
  methods: {
    /**
     * 获取所有已发布计划信息
     * @author lgh
     */
    queryAllPublishPlanAPP() {
      queryAllPublishPlanAPP().then(res => {
        let planInfo = JSON.parse(localStorage.getItem("nowPlanInfo"));
        console.log(planInfo, res.data);

        if (planInfo) {
          this.planQuery.groupId = planInfo.groupId;
          this.planQuery.semesterId = planInfo.value;
        } else {
          this.planQuery.groupId = res.data[0].groupId;
          this.planQuery.semesterId = res.data[0].semesterId;
        }
        res.data = JSON.parse(
          JSON.stringify(res.data).replace(/title/g, "text")
        );
        res.data = JSON.parse(
          JSON.stringify(res.data).replace(/semesterId/g, "value")
        );
        this.planOption = res.data;
        this.getData();
      });
    },
    /**
     * 切换默认计划时，更改对应的groupId
     * @param {number}value 绑定的计划下拉框绑定的默认值
     * @author lgh
     */
    changeSemester(value) {
      console.log(value);
      var obj = this.planOption.find(function(x) {
        return x.value == value;
      });
      this.planQuery.groupId = obj.groupId;
      console.log(obj);
      this.getPlan(obj);
    },
    //招生进度表
    enrollmentProgress() {
      enrollmentProgress(this.planQuery).then(res => {
        console.log(res, "招生进度表");
        let data = res.data;
        this.enrollmentProgressData.numberCompletions = data.numberCompletions;
        this.enrollmentProgressData.planAllNumber = data.planAllNumber;
      });
    },
    //报名生源学校分析
    enrollmentAnalysis() {
      enrollmentAnalysis(this.planQuery).then(res => {
        console.log(res, "报名生源学校分析3213");
        let myChart = echarts.init(this.$refs.sy);
        let option = this.enrollmentAnalysisSyOption;
        let data = res.data;
        if (data.length) {
          let schoolNameArr = []; // 所有的学校
          let schoolArr = []; //所有的学校和数量
          for (let i = 0; i < data.length; i++) {
            let json = {
              value: data[i].numberOfPeople,
              name: data[i].sourceSchoolName
            };
            schoolNameArr.push(json.name);
            schoolArr.push(json);
          }
          console.log(schoolNameArr, schoolNameArr, "schoolNameArr");
          option.legend.data = schoolNameArr;
          option.series[0].data = schoolArr;
          myChart.setOption(option);
          this.enrollmentAnalysisShow = true;
        } else {
          this.enrollmentAnalysisShow = false;
        }
      });
    },
    //报名阶段分析
    registrationStage() {
      registrationStage(this.planQuery).then(res => {
        console.log(res, "报名阶段分析");
        let myChart = echarts.init(this.$refs.jd);
        let option = this.registrationStageOption;
        let data = res.data;
        let registrationStageName = [];
        let registrationStageNumber = [];
        if (data.length) {
          for (let i = 0; i < data.length; i++) {
            registrationStageName.push(data[i].stageName);
            registrationStageNumber.push(data[i].numberOfPeople);
          }
          option.xAxis.data = registrationStageName;
          option.series[0].data = registrationStageNumber;
          myChart.setOption(option);
          this.registrationStageShow = true;
        } else {
          this.registrationStageShow = false;
        }
      });
    },
    //各专业报名分析
    professionalRegistration() {
      professionalRegistration(this.planQuery).then(res => {
        console.log(res, "各专业报名分析");
        if (res.data.length) {
          this.professionalRegistrationShow = true;
          this.professionalRegistrationOption = res.data;
        } else {
          this.professionalRegistrationShow = false;
        }
      });
    },
    //APP切换计划
    getPlan(obj) {
      this.$message.loading("正在切换计划...");
      getPlan(obj.planId)
        .then(res => {
          localStorage.setItem("ACCESS_TOKEN", res.data.token);
          localStorage.setItem("nowPlanInfo", JSON.stringify(obj));
          this.$message.close();
          this.$message.success("切换成功！");
          this.getData();
        })
        .catch(err => {
          console.error(err);
          this.$message.error("切换计划失败！");
        });
    },
    getData() {
      this.enrollmentProgress();
      this.enrollmentAnalysis();
      this.registrationStage();
      this.professionalRegistration();
    }
  },
  /**
   * 获取各个报表的数据
   */
  mounted() {},
  created() {
    this.queryAllPublishPlanAPP();
  }
};
</script>

<style scoped lang="stylus">
.analysis {
  margin-bottom: 50px;

  .analysis-content {
    padding: 16px;

    .analysis-content-title {
      background-color: bgColor;
      padding: 15px;
      padding-top: 7px;
      border-radius: 6px;
      margin-bottom: 20px;

      .analysis-content-sy-content {
        position: relative;

        .analysis-content-sy-content-show {
          z-index: 9;
          display: flex;
          justify-content: center;
          align-items: center;
        }
      }

      .login-title {
        color: fontTitleColor;
        font-size: 17px;
        font-weight: bold;
        margin-top: 5px;
        display: flex;

        .login-title-img {
          margin-top: 3px;
          margin-bottom: 3px;
          margin-right: 12px;
          width: 6px;
          height: 18px;
        }

        .login-title-right {
          // width:102px;
          height: 25px;
          font-size: 17px;
          font-weight: 600;
          color: rgba(51, 51, 51, 1);
          line-height: 25px;
        }

        .login-title-analysis {
          margin-left: 105px;
          height: 25px;
          font-size: 14px;
          font-weight: 400;
          color: rgba(153, 153, 153, 1);
          line-height: 25px;
        }
      }

      .analysis-content-title-content {
        margin-top: 40px;

        .analysis-content-title-content-new {
          .analysis-content-title-content-top-new {
            display: flex;
            justify-content: space-between;
            margin-bottom: 8px;

            .analysis-content-title-content-top-left {
              height: 21px;
              font-size: 14px;
              font-weight: 600;
              color: rgba(51, 51, 51, 1);
              line-height: 21px;
            }

            .analysis-content-title-content-top-right {
              height: 21px;
              font-size: 14px;
              font-weight: 400;
              color: rgba(153, 153, 153, 1);
              line-height: 21px;
            }
          }

          .analysis-content-title-content-bottom {
            margin: 0;
            padding: 0;
          }
        }

        .analysis-content-title-content-top {
          display: flex;
          justify-content: space-around;

          .analysis-content-title-content-top-item {
            display: flex;
            flex-direction: column;
            align-items: center;
            height: 1rem;
            justify-content: space-between;

            .analysis-content-title-content-top-item-top {
              color: rgba(51, 51, 51, 1);
              font-size: 12px;
              // width: 54px;
              height: 18px;
              font-weight: 400;
              line-height: 18px;
            }

            .analysis-content-title-content-top-item-number {
              color: rgba(51, 51, 51, 1);
              font-weight: 600;
              height: 23px;
              line-height: 23px;
              font-size: 15px;
            }
          }
        }

        .analysis-content-title-content-bottom {
          margin-bottom: 20px;
          margin-top: 40px;

          .analysis-content-title-content-bottom-bg {
            position: relative;
            width: 100%;
            height: 22px;
            background-color: rgba(242, 242, 242, 1);
            border-radius: 11px;

            .analysis-content-title-content-bottom-bg-ac {
              position: absolute;
              // background: linear-gradient(270deg, rgba(0, 204, 255, 1) 0%, rgba(1, 162, 255, 1) 100%);
              background: linear-gradient(to right, rgba(1, 162, 255, 1) 0%, rgba(0, 204, 255, 1) 100%);
              // z-index: 9;
              height: 22px;
              border-radius: 11px;
              font-size: 14px;
              text-align: right;

              .analysis-content-title-content-bottom-numberCompletions {
                width: 39px;
                height: 21px;
                font-size: 14px;
                line-height: 21px;
                color: rgba(255, 255, 255, 1);
                margin-right: 12px;
              }
            }

            .textAli {
              text-align: center;
              margin-right: 0px !important;
            }
          }
        }
      }
    }
  }
}
</style>
