<template>
  <div class="to-school-counseling-manage">
    <div class="to-school-counseling-manage-header">
      <van-sticky :offset-top="0">
        <h5-header
          :leftStatus="true"
          title="到校咨询管理"
          :rigText="rightTexts[type]"
          @clickHeadAction="clickHeadAction"
        ></h5-header>
      </van-sticky>
    </div>
    <div class="to-school-counseling-manage-content">
      <van-calendar
        color="#00CCFF"
        v-if="isShow"
        :show-title="false"
        title="日历"
        @select="clickData"
        :default-date="nowDate"
        :poppable="false"
        :show-confirm="false"
        :min-date="minDate"
        :max-date="maxDate"
        :formatter="formatter"
        :style="{ height: '500px' }"
      />
      <van-tabs
        v-if="isShowTrip"
        v-model="active"
        color="#00CCFF"
        sticky
        line-width="88px"
        line-height="2px"
        title-active-color="#00CCFF"
        @click="tabsClick"
      >
        <van-tab title="行程安排">
          <div class="scheduling">
            <div class="scheduling-title">{{ activeDate1 }}行程安排</div>
            <div class="scheduling-info content-info-content-items" style="background: #FFFFFF;">
              <div class="content-info-item">
                <div class="content-info-item-label">接待人</div>
                <div class="content-info-item-val">{{ scheduList.receptionistName }}</div>
              </div>
              <div class="content-info-item">
                <div class="content-info-item-label">联系方式</div>
                <div class="content-info-item-val">{{ scheduList.telephone }}</div>
              </div>
              <div class="content-info-item">
                <div class="content-info-item-label">接待人数</div>
                <div class="content-info-item-val">{{ scheduList.actualAmount }}</div>
              </div>
            </div>
            <div style="margin-left: 18px;color: #999;margin-top: 5px;">接待地址</div>
            <div
              style="background: #FFFFFF;"
              class="scheduling-content"
              v-html="resetHtml(scheduList.address)"
            ></div>
            <div style="background: #FFFFFF;margin-left: 18px;color: #999;margin-top: 10px;">行程安排</div>
            <div
              class="scheduling-content"
              v-html="resetHtml(scheduList.schedule)"
            >{{ scheduList.schedule }}</div>
            <!-- <div class="scheduling-content">
              <div class="scheduling-content-item">
                <div class="scheduling-content-item-time">9:00~10:00</div>
                <div class="scheduling-content-item-desc">
                  <div class="scheduling-content-item-desc-name">集合团体介绍认识</div>
                  <div class="scheduling-content-item-desc-position">5楼会议室</div>
                </div>
              </div>
              <div class="scheduling-content-item">
                <div class="scheduling-content-item-time">11:00~12:00</div>
                <div class="scheduling-content-item-desc">
                  <div class="scheduling-content-item-desc-name">参观校园（全校）</div>
                  <div class="scheduling-content-item-desc-position">全校</div>
                </div>
              </div>
              <div class="scheduling-content-item">
                <div class="scheduling-content-item-time">11:00~12:00</div>
                <div class="scheduling-content-item-desc">
                  <div class="scheduling-content-item-desc-name">参观校园（全校）</div>
                  <div class="scheduling-content-item-desc-position">全校</div>
                </div>
              </div>
              <div class="scheduling-content-item">
                <div class="scheduling-content-item-time">12:00~13:00</div>
                <div class="scheduling-content-item-desc">
                  <div class="scheduling-content-item-desc-name">午餐</div>
                  <div class="scheduling-content-item-desc-position">学校食堂</div>
                </div>
              </div>
              <div class="scheduling-content-item">
                <div class="scheduling-content-item-time">13:00~14:00</div>
                <div class="scheduling-content-item-desc">
                  <div class="scheduling-content-item-desc-name">单独咨询</div>
                  <div class="scheduling-content-item-desc-position"></div>
                </div>
              </div>
            </div>-->
          </div>
        </van-tab>
        <van-tab title="个人咨询">
          <van-list
            class="person-info-list"
            v-model="studentloading"
            :finished="studentfinished"
            finished-text="没有更多了"
            @load="studentonLoad"
            :immediate-check="false"
          >
            <div class="person-info-list-item" v-for="(item, index) in studentList" :key="index">
              <div class="person-info-list-item-title">{{ item.studentName }}</div>
              <div class="person-info-list-item-content content-info-content-items">
                <div class="content-info-item">
                  <div class="content-info-item-label">联系方式</div>
                  <div class="content-info-item-val">{{ item.telephone }}</div>
                </div>
                <div class="content-info-item">
                  <div class="content-info-item-label">学校</div>
                  <div class="content-info-item-val">{{ item.sourceSchoolName }}</div>
                </div>
                <div class="content-info-item">
                  <div class="content-info-item-label">班级</div>
                  <div class="content-info-item-val">{{ item.className }}</div>
                </div>
                <div class="content-info-item">
                  <div class="content-info-item-label">到校时间</div>
                  <div class="content-info-item-val">{{ item.attendTime }}</div>
                </div>
              </div>
            </div>
          </van-list>
        </van-tab>
        <van-tab title="学校咨询">
          <van-list
            class="person-info-list"
            v-model="schoolloading"
            :finished="schoolfinished"
            finished-text="没有更多了"
            @load="SchoolonLoad"
            :immediate-check="false"
          >
            <div class="person-info-list-item" v-for="(item, index) in schoolList" :key="index">
              <div class="person-info-list-item-title">{{ item.teacher }}</div>
              <div class="person-info-list-item-content content-info-content-items">
                <div class="content-info-item">
                  <div class="content-info-item-label">联系方式</div>
                  <div class="content-info-item-val">{{ item.telephone }}</div>
                </div>
                <div class="content-info-item">
                  <div class="content-info-item-label">学校</div>
                  <div class="content-info-item-val">{{ item.sourceSchoolName }}</div>
                </div>
                <div class="content-info-item">
                  <div class="content-info-item-label">人数</div>
                  <div class="content-info-item-val">{{ item.amount }}</div>
                </div>
                <div class="content-info-item">
                  <div class="content-info-item-label">到校时间</div>
                  <div class="content-info-item-val">{{ item.attendTime }}</div>
                </div>
              </div>
            </div>
          </van-list>
        </van-tab>
      </van-tabs>
    </div>
    <!-- <div class="to-school-counseling-manage-footer">
      <div class="add-counseling-btn">
        <span class="add-counseling-btn-title">增加咨询</span>
        <van-icon class="add-counseling-btn-icon" color="#00CCFF" name="add-o" />
      </div>
    </div>-->
  </div>
</template>

<script>
import {
  getConsultationId,
  getConsultationDayDetail,
  getConsultationRegisterList,
  getConsultationDayList,
  getConsultationCalender
} from "../../../api/admissions-process/to-school-counseling-manage";
import Header from "@/components/cheader";
import Vue from "vue";
import config from "@/config";
import { Sticky, Tab, Tabs, List, Calendar, Icon, Dialog } from "vant";
Vue.use(Sticky);
Vue.use(Tabs);
Vue.use(Tab);
Vue.use(List);
Vue.use(Calendar);
Vue.use(Icon);

import { dateFormat } from "@/utils/format";

export default {
  name: "to-school-counseling-manage",
  components: {
    "h5-header": Header
  },
  data() {
    return {
      active: 0,
      // 列表
      list: [],
      loading: false,
      finished: false,
      //新增、删除操作
      rightTexts: ["新增", "删除"],
      type: 0,
      isClick: false, //判断是否点击了日历的日期
      //日历
      isShow: true,
      opendDateList: [], //接待日信息列表
      myActiveDateList: [], //我的预约日期,
      minDate: new Date(2020, 3, 1),
      maxDate: new Date(2020, 11, 31),
      nowDate: new Date(),
      isOpend: false, //是否是接待日
      isMyActive: false, //是否是已预约
      dateFmt: "YYYY-mm-dd",
      consultationId: "",
      scheduList: {},
      activeDate: dateFormat(this.nowDate, this.dateFmt),
      isShowTrip: false, //是否展示tabs内容
      stuOrSchList: [], //个人&学校咨询信息内容
      tabsName: 0,
      total: "",
      searchQuery: {
        pageNum: 1,
        pageSize: 10,
        date: "2020-11-11",
        consultationId: this.consultationId,
        consultationType: 0
      }, //tabs内的信息搜索所需要的条件
      studentList: [], //个人咨询的列表
      activeDate1: this.setData(new Date()),
      studentloading: false,
      studentfinished: false,
      studentSearchQuery: {
        pageNum: 1,
        pageSize: 10,
        date: "2020-11-11",
        consultationId: this.consultationId,
        consultationType: 0
      },
      schoolList: [], //学校咨询列表
      schoolloading: false,
      schoolfinished: false,
      schoolSearchQuery: {
        pageNum: 1,
        pageSize: 10,
        date: "2020-11-11",
        consultationId: this.consultationId,
        consultationType: 1
      }
    };
  },
  methods: {
    resetHtml(val) {
      if (val) {
        return val.replace(/\/admin/g, config.webImgDir);
      }
    },
    //学生加载的
    studentonLoad() {},
    //学生加载的
    SchoolonLoad() {},
    /**
     * tabs的点击事件
     * @param name 事件点击的内容
     * @author LGH
     */
    tabsClick(name) {
      this.tabsName = name;
      // if (name === 0) {
      //   this.initialization(); //对searchQuery进行初始化
      //   this.getConsultationDayDetail(this.searchQuery);
      // } else {
      //   this.initialization(); //对searchQuery进行初始化
      //   this.getConsultationRegisterList(this.searchQuery);
      // }
    },

    /*--------------   日历  -----------------*/
    //点击日期
    clickData(day) {
      this.isClick = true;
      console.log(this.isClick, ">>>>>s");
      console.log(day, ">>>day");

      let data = this.setData(day);
      // let opendDateList = this.opendDateList;
      //后期删除--创建虚假数据，用于对接使用显示行程安排的数据内容
      let params = {
        date: data,
        consultationId: this.consultationId
      };
      this.activeDate1 = data;
      this.studentSearchQuery.date = data;
      this.schoolSearchQuery.date = data;
      let isNoOpen = true;
      for (let i = 0; i < this.opendDateList.length; i++) {
        if (
          this.setData(new Date(this.opendDateList[i].opendayDate)) == data &&
          this.opendDateList[i].isOpenday === 2
        ) {
          this.$message.error("当前日期没有接待行程！");
          isNoOpen = false;
          this.isShowTrip = false;
          break;
        } else {
          isNoOpen = true;
        }
      }
      if (isNoOpen) {
        this.getConsultationDayDetail(params);
        this.getConsultationRegisterList(this.studentSearchQuery);
        this.getConsultationRegisterList(this.schoolSearchQuery);
      } else {
        getConsultationDayDetail(params).then(res => {
          this.scheduList = res.data;
        });
      }
    },

    //获取今日时间，并格式化
    setData(data) {
      const year = data.getFullYear();
      const month =
        data.getMonth() + 1 > 9
          ? data.getMonth() + 1
          : "0" + (data.getMonth() + 1);
      const date = data.getDate() > 9 ? data.getDate() : "0" + data.getDate();
      let newData = year + "-" + month + "-" + date;
      return newData;
    },
    //格式化日历显示
    formatter(day) {
      const year = day.date.getFullYear();
      const month =
        day.date.getMonth() + 1 > 9
          ? day.date.getMonth() + 1
          : "0" + (day.date.getMonth() + 1);
      const date =
        day.date.getDate() > 9 ? day.date.getDate() : "0" + day.date.getDate();
      //获取接待日信息列表
      let data = this.opendDateList;
      let NowData = year + "-" + month + "-" + date;
      for (let i = 0; i < data.length; i++) {
        if (NowData === data[i].opendayDate && data[i].isOpenday === 1) {
          day.bottomInfo = "接待日";
          day.className = "dutyColor";
        }
        if (NowData === data[i].opendayDate && data[i].isOpenday === 2) {
          day.bottomInfo = "无接待";
          day.className = "dutyColor1";
        }
      }
      //获取今日日期
      console.log(day, 1111);

      let nowTime = this.setData(new Date());
      if (nowTime == NowData) {
        day.className = "dutyColor2";
      }

      // 设置我已预约日期
      let myActiveList = this.myActiveDateList;
      for (let i = 0; i < myActiveList.length; i++) {
        if (myActiveList[i].date === NowData) {
          day.className = "activeColor";
        }
      }
      return day;
    },
    //点击头部右侧按钮
    clickHeadAction() {
      if (this.isClick) {
        //若当前点击为无接待日时的操作
        if (!this.isShowTrip) {
          Dialog.confirm({
            title: "提示",
            message: "当前日期没接待行程，是否确定预约？"
          })
            .then(() => {
              console.log(this.scheduList);

              //增加咨询
              this.$router.push({
                path: "/admissions-process/counseling-add",
                query: {
                  opendayDate: this.scheduList.opendayDate,
                  receptionistId: this.scheduList.receptionistId,
                  opendayId: this.scheduList.opendayId,
                  consultationId: this.consultationId
                }
              });
            })
            .catch(() => {
              // on cancel
            });
        } else {
          //增加咨询
          this.$router.push({
            path: "/admissions-process/counseling-add",
            query: { ...this.scheduList, consultationId: this.consultationId }
          });
        }
      } else {
        this.$message.error("请先点击选择咨询日期！");
      }
    },
    /**
     * 获取咨询ID
     * @author LGH
     */
    getConsultationId() {
      getConsultationId().then(res => {
        if (res.data.consultationId) {
          this.consultationId = res.data.consultationId;
          this.studentSearchQuery.consultationId = res.data.consultationId;
          this.schoolSearchQuery.consultationId = res.data.consultationId;
          this.minDate = new Date(res.data.startTime);
          this.maxDate = new Date(res.data.endTime);
          //获取接待日信息列表,获取咨询人数日历
          const all = Promise.all([
            getConsultationDayList(this.consultationId),
            getConsultationCalender(this.consultationId)
          ]);
          all
            .then(res => {
              console.log(res, "获取接待日信息列表,获取咨询人数日历");
              this.opendDateList = res[0].data; //接待日信息列表
              this.myActiveDateList = res[1].data; //获取咨询日期
              // this.clickData(new Date());
            })
            .catch(err => {
              console.log(err);
            });
        } else {
          this.$toast("暂无咨询计划");
        }
      });
    },
    /**
     * 获取咨询日程详情
     * @param query{array} 获取咨询日程详情所需的信息
     * @author LGH
     */
    getConsultationDayDetail(query) {
      getConsultationDayDetail(query).then(res => {
        console.log(res, "获取咨询日程详情");
        this.isShowTrip = true;
        this.scheduList = res.data;
      });
    },
    /**
     * 获取咨询注册列表
     * @param query{array} 获取咨询注册列表所需的信息
     * @author LGH
     */
    getConsultationRegisterList(query) {
      getConsultationRegisterList(query).then(res => {
        let type = query.consultationType;
        let dataName = "";
        if (type === 0) {
          dataName = "student";
        } else {
          dataName = "school";
        }
        let data = dataName + "List";
        let loading = dataName + "loading";
        let finished = dataName + "finished";
        let arr = [];
        if (dataName == "school" && res.data.sourceSchoolName) {
          res.data.sourceSchoolName = "无学校信息";
        }
        let result = res.data;
        //判断是否是第一页
        if (result.current === 1) {
          arr = result.records;
        } else {
          arr = [...this[data], ...result.records];
        }
        //判断是否已全部加载
        this[finished] = result.records.length !== result.size;
        //加载已完成
        this[loading] = false;
        this[data] = arr;
        console.log(data, loading, finished);
      });
    }
  },
  created() {
    (this.nowDate = new Date(1880, 1, 1)), this.getConsultationId();
  }
};
</script>

<style scoped lang="stylus">
>>>.van-tab {
  flex: 0.26;
}

.to-school-counseling-manage {
  &-content {
    background-color: #fff;

    >>>.active-color {
      color: #fff;
      background-color: red;
    }

    >>>.dutyColor {
      color: red;
    }

    >>>.dutyColor1 {
      color: #999;
    }

    >>>.dutyColor2 {
      color: rgba(0, 204, 255, 1);
    }

    >>>.activeColor {
      color: red;

      &:after {
        position: absolute;
        content: '';
        right: -0.25rem;
        top: 0.2rem;
        border-bottom: 0.32rem solid;
        border-left: 0rem solid transparent;
        border-right: 0.32rem solid transparent;
        border-top: 0rem solid;
        width: 0px;
        height: 0px;
        border-top: 5px solid transparent;
        border-bottom: 5px solid transparent;
        border-left: 8px solid;
      }

      &:before {
        position: absolute;
        content: '';
        right: 0.26rem;
        top: 0.21rem;
        border-top: 0.4rem solid;
        width: 0.05rem;
      }
    }

    // 计划安排
    .scheduling {
      &-title {
        font-size: 17px;
        font-weight: 600;
        color: rgba(51, 51, 51, 1);
        line-height: 26px;
        padding: 0 16px;
        padding-top: 12px;
      }

      &-content {
        margin: 5px 25px 16px;
        background-color: #F8F8F8;

        >>>img {
          max-width: 100%;
        }

        &-item {
          display: flex;
          padding-top: 16px;
          padding-left: 16px;

          &-time {
            width: 80px;
            font-size: 12px;
            color: #999999;
            height: 21px;
            line-height: 21px;
          }

          &-desc {
            position: relative;
            flex: 1;
            font-size: 14px;
            line-height: 21px;
            padding-left: 12px;

            &:before {
              content: '';
              position: absolute;
              top: 7.5px;
              left: 0;
              width: 6px;
              height: 6px;
              background: #00CCFF;
              border-radius: 3px;
            }

            &-name {
              color: #333333;
              font-weight: 600;
            }

            &-position {
              color: #999999;
              font-weight: 400;
            }
          }
        }
      }
    }

    // 咨询人员列表
    .person-info-list {
      background-color: #fff;

      &-item {
        margin: 16px;
        background: rgba(248, 248, 248, 1);
        border-radius: 4px;
        font-size: 14px;
        font-weight: 400;
        line-height: 21px;

        &-title {
          padding-left: 16px;
          font-weight: 600;
          color: #333333;
        }
      }
    }
  }

  &-footer {
    position: sticky;
    bottom: 0;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 49px;
  }
}

// 底部新增咨询按钮
.add-counseling-btn {
  color: #00CCFF;
  height: 49px;
  line-height: 49px;

  &-title, &-icon {
    vertical-align: middle;
  }

  &-icon {
    padding-left: 8px;
  }
}

.content-info-content-items {
  font-size: 15px;
  font-weight: 400;
  line-height: 28px;
  padding: 4px 16px;
  background: rgba(248, 248, 248, 1);

  .content-info-item {
    display: flex;

    &-label {
      width: 60px;
      color: #999999;
    }

    &-val {
      text-align: right;
      margin-left: 12px;
      flex: 1;

      >>>img {
        max-width: 100%;
      }
    }
  }
}
</style>
