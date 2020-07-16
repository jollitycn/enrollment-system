<template>
  <div class="inSchool">
    <van-sticky>
      <h5-header :leftStatus="true" title="到校咨询"></h5-header>
    </van-sticky>
    <div class="Calendar">
      <van-calendar
        color="#00CCFF"
        v-if="isShow"
        :show-title="false"
        :show-subtitle="false"
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
      <div
        v-show="!isShow"
        style="padding-top: 150px;text-align: center;color: #999;"
      >
        {{ schoolTotal }}
      </div>
    </div>
    <div class="information-content">
      <div class="information-content-title">{{ activeDate }}行程安排</div>
      <div v-show="!isOpend" class="information-content-isopend">
        <span>暂无安排</span>
      </div>
      <div class="information-content-bottom" v-show="isOpend">
        <div class="information-content-bottom-name">
          <span class="information-content-bottom-name-left">接待人</span>
          <span class="information-content-bottom-name-right">{{
            resInfo.receptionistName
          }}</span>
        </div>
        <div class="information-content-bottom-name">
          <span class="information-content-bottom-name-left">联系电话</span>
          <span class="information-content-bottom-name-right">{{
            resInfo.telephone
          }}</span>
        </div>
        <div class="information-content-bottom-name xc">
          <span class="information-content-bottom-name-left">行程安排</span>
          <span
            class="information-content-bottom-name-right html"
            v-html="resetHtml(resInfo.schedule)"
          ></span>
        </div>
        <div class="information-content-bottom-name">
          <span class="information-content-bottom-name-left">接待地址</span>
          <span
            class="information-content-bottom-name-right html"
            v-html="resetHtml(resInfo.address)"
          ></span>
        </div>
      </div>
    </div>
    <div class="btn-bottom">
      <van-button
        type="primary"
        :color="isMyActive ? '#ff0000' : '#00CCFF'"
        @click="clickHeadAction"
        block
        >{{ isMyActive ? "删除" : "新增" }}</van-button
      >
    </div>
  </div>
</template>

<script>
import { Calendar, Sticky, Dialog, Button } from "vant";
import Header from "@/components/cheader";
import {
  getConsultationId,
  getConsultationDayList,
  getMyRegister,
  getConsultationDayDetail,
  deleteRegister,
  getConsultationCalender
} from "@/api/user/user";
import { webImgDir } from "@/config/index";
export default {
  name: "inSchool",
  components: {
    vanCalendar: Calendar,
    vanSticky: Sticky,
    vanButton: Button,
    h5Header: Header
  },
  data() {
    return {
      schoolTotal: "正在加载咨询数据...",
      isShow: false,
      opendDateList: [], //接待日信息列表
      myActiveDateList: [], //我的预约日期,
      minDate: new Date(2020, 3, 1),
      maxDate: new Date(2020, 11, 31),
      nowDate: new Date(),
      resInfo: {}, //咨询详情信息
      isOpend: false, //是否是接待日
      isMyActive: false, //是否是已预约
      activeDate: this.setData(new Date()),
      consultationId: "", //咨询计划id,
      getConsultationCalenderArr : []
    };
  },
  methods: {
    resetHtml(val) {
      if (val) {
        return val.replace(/\/admin/g, webImgDir);
      }
    },
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
      let nowTime = this.setData(new Date());
      if (nowTime == NowData) {
        day.text = "今天";
        // day.className = "dutyColor";
      }

      // 设置我已预约日期
      let myActiveList = this.myActiveDateList;
      for (let i = 0; i < myActiveList.length; i++) {
        if (myActiveList[i] === NowData) {
          day.topInfo = "已预约";
          day.className = " dutyColor";
        }
      }
      let myConsultationCalenderArr = this.getConsultationCalenderArr;
      for (let i = 0; i < myConsultationCalenderArr.length; i++) {
        if (myConsultationCalenderArr[i] === NowData) {
          day.className = "hasCou";
        }
      }
      return day;
    },
    // 获取有预约的日期列表
    getConsultationCalender(id) {
      getConsultationCalender(id).then(res => {
        // this.getConsultationCalenderArr = res.data;
        let data = res.data;
        let arr = [];
        for (let i = 0; i < data.length; i++) {
          arr.push(data[i].date)
        }
        this.getConsultationCalenderArr = arr;
        console.log(res);
      })
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
    //获取咨询ID
    getConsultationId() {
      getConsultationId().then(res => {
        if (res.data) {
          this.getAllList(res.data.consultationId);
          this.getConsultationCalender(res.data.consultationId);
          this.consultationId = res.data.consultationId;
          this.minDate = new Date(res.data.startTime + " " + "00:00:00");
          this.maxDate = new Date(res.data.endTime + " " + "00:00:00");
        } else {
          this.schoolTotal = "暂无咨询计划,请联系招生人员";
        }
      });
    },
    //获取接待日信息列表与我的预约日期
    getAllList(id) {
      let all = Promise.all([getConsultationDayList(id), getMyRegister()]);
      all.then(res => {
        console.log(res);
        this.opendDateList = res[0].data; //接待日信息列表
        this.myActiveDateList = res[1].data; //获取咨询日期
        console.log(this.opendDateList, 889, this.myActiveDateList);
        let nowTime1 = this.setData(new Date());
        //判断当前日期
        let opendDateList = this.opendDateList;
        let isOpend = true;
        //判断是否是有接待计划
        let nowTime = new Date(nowTime1 + " " + "00:00:00").getTime();
        if (
          !(
            this.minDate.getTime() <= nowTime &&
            nowTime <= this.maxDate.getTime()
          )
        ) {
          isOpend = false;
        }
        if (isOpend) {
          for (let i = 0; i < opendDateList.length; i++) {
            if (
              nowTime1 === opendDateList[i].opendayDate &&
              opendDateList[i].isOpenday === 2
            ) {
              isOpend = false;
              break;
            }
          }
        }
        if (isOpend) {
          let params = {
            date: nowTime1,
            consultationId: this.consultationId
          };
          this.getConsultationDayDetail(params);
        }
        this.isOpend = isOpend; //是否是接待日
        this.isShow = true;
      });
    },
    //获取咨询日详情
    getConsultationDayDetail(params) {
      getConsultationDayDetail(params).then(res => {
        console.log(res, "咨询详情");
        this.resInfo = res.data;
      });
    },
    // 删除咨询日期
    deleteRegister() {
      deleteRegister(this.activeDate).then(res => {
        console.log(res);
        this.isMyActive = false;
        this.myActiveDateList = this.myActiveDateList.filter(item => {
          return item !== this.activeDate;
        });
      });
    },
    //点击日期
    clickData(day) {
      let data = this.setData(day);
      let opendDateList = this.opendDateList;
      let isOpend = true;
      //判断是否是有接待计划
      for (let i = 0; i < opendDateList.length; i++) {
        if (
          data === opendDateList[i].opendayDate &&
          opendDateList[i].isOpenday === 2
        ) {
          isOpend = false;
          break;
        }
      }
      if (isOpend) {
        let params = {
          date: data,
          consultationId: this.consultationId
        };
        this.getConsultationDayDetail(params);
      }
      this.isOpend = isOpend; //是否是接待日
      //判断是否已预约
      let myActiveDateList = this.myActiveDateList;
      let isMyActive = false;
      for (let i = 0; i < myActiveDateList.length; i++) {
        if (myActiveDateList[i] === data) {
          isMyActive = true;
        }
      }
      this.isMyActive = isMyActive;
      this.activeDate = data;
      console.log(data);
    },
    //点击新增到校咨询
    clickHeadAction() {
      if (this.isMyActive) {
        Dialog.confirm({
          title: "删除操作",
          message: `确认删除${this.activeDate}此预约日期`
        })
          .then(() => {
            // on confirm
            this.deleteRegister();
          })
          .catch(() => {
            // on cancel
          });
      } else {
        let nowTime = new Date(this.activeDate + " " + "00:00:00").getTime();
        console.log(
          this.minDate.getTime() < nowTime,
          nowTime < this.maxDate.getTime()
        );
        if (
          !(
            this.minDate.getTime() <= nowTime &&
            nowTime <= this.maxDate.getTime()
          )
        ) {
          this.$toast("请选择日期");
          return;
        }
        if (this.consultationId) {
          let data = this.opendDateList;
          let status = true;
          for (let i = 0; i < data.length; i++) {
            if (
              this.activeDate === data[i].opendayDate &&
              data[i].isOpenday === 2
            ) {
              status = false;
              Dialog.confirm({
                title: "确认操作",
                message: "当前日期没接待行程，是否确定预约"
              })
                .then(() => {
                  this.$router.push({
                    path: "/user/addInSchool",
                    query: {
                      date: this.activeDate,
                      consultationId: this.consultationId
                    }
                  });
                })
                .catch(() => {
                  // on cancel
                });
            }
          }
          if (!status) {
            return;
          }
          this.$router.push({
            path: "/user/addInSchool",
            query: {
              date: this.activeDate,
              consultationId: this.consultationId
            }
          });
        } else {
          this.$toast("暂无咨询计划,不可添加到校咨询");
        }
      }
    }
  },
  created() {
    this.getConsultationId();
  }
};
</script>

<style scoped lang="stylus">
.inSchool
    .Calendar
        min-height 500px
        >>>.activeColor
                color bgColor
                background-color red
        >>>.hasCou:after
              content : ""
              margin: 3px;
              display: table;
              width: 6px;
              height: 6px;
              background: red;
              border-radius: 50%;
              position: absolute;
              right: 5px;
              top: 26px;
        >>>.dutyColor
                color red
        >>>.dutyColor1
            color #999
    .information-content
        margin-bottom 60px
        >.information-content-title
            height 50px
            line-height 50px
            color: fontTextColor
            padding-left: 20px
            background-color #eee
        >.information-content-isopend
            text-align: center
            background-color: #fff
            padding-top: 20px
        >.information-content-bottom
            background-color bgColor
            padding: 20px
            >.information-content-bottom-name
                font-size fontBigSize
                margin-bottom: 20px;
                >.information-content-bottom-name-left
                    color fontTextColor
                    width 80px
                    display inline-block
                >.information-content-bottom-name-right
                    color fontTitleColor;
                    display inline-block
                    >>>p
                        margin-bottom 15px
                    >>>img
                        max-width 100%
                >.information-content-bottom-name-right.html
                    margin-top: 10px;
            >.information-content-bottom-ap
                min-height: 100px;
                border-top: 1px solid #eee;
                padding-top: 20px;
                >>>p
                    margin-bottom 15px
                >>>img
                    max-width 100%
            .xc
              border-top: 0.5px solid #eee;
              padding-top: 20px
    .btn-bottom
      position: fixed;
      bottom: 0;
      width: 100%;
      text-align: center;
</style>
