<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator=">">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>招生过程管理</el-breadcrumb-item>
        <el-breadcrumb-item>咨询管理</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="canlendarPanel">
      <div class="header-btn">

        <h1>{{ `${year}年${month}月` }}</h1>
        <div>
          <el-button type="primary" @click="go('add')">返回咨询基本信息</el-button>
        </div>
      </div>
      <div v-if="true" class="canlendar-header">
        <p class="pre">
          <el-button type="primary" @click="changeDate('preYear')">上一年</el-button>
          <el-button type="primary" @click="changeDate('preMonth')">上一个月</el-button>
        </p>
        <p class="next">
          <el-button type="primary" @click="changeDate('nextMonth')">下一个月</el-button>
          <el-button type="primary" @click="changeDate('nextYear')">下一年</el-button>
        </p>
      </div>
      <div class="canlendar-main">
        <ul class="main-header">
          <li v-for="(item, index) in week">{{ item }}</li>
        </ul>
        <ul class="main">
          <li style="cursor:pointer;" v-for="inx in weekDay" @click="changeDate('preMonth')" class="disabled">
            {{ preMonthSize() - weekDay + inx }}</li>
          <li
            @click="see(dataNow(index),Boolean(newJson[dataNow(index)] ? newJson[dataNow(index)].status : 0 ),newJson[dataNow(index)]? newJson[dataNow(index)].opendayId : undefined)"
            :class="{ currentDay:(index === day && adyear == year && admonth == month ),active:Boolean(newJson[dataNow(index)] ? newJson[dataNow(index)].status : 0) ,showCuro: true,disabled: isDisaled(index)} "
            v-for="index in monthList[month - 1]">
            <span>{{ index }}</span>
            <span v-show="Boolean(newJson[dataNow(index)] ? newJson[dataNow(index)].status : 0)">接待日</span>
            <span v-show="newJsonNumber[dataNow(index)]">{{newJsonNumber[dataNow(index)]}}人</span>
          </li>
          <li style="cursor:pointer;" v-for="index in lastWeekDay" @click="changeDate('nextMonth')" class="disabled">
            {{ index }}</li>
        </ul>
      </div>
    </div>
  </div>
</template>


<script>
  import { getConsultationDayDetail, getConsultationDetail, getConsultationId, getConsultationDayList, getConsultationCalender } from "@/api/management/enrollmentProcessManagement/consultingSchoolManagement"
  export default {
    name: 'addConsultingSchoolManagementNow',
    data() {
      return {
        form: {
          pageNum: 1,
          pageSize: 10
        },
        allTimeArr: [],
        year: '',
        month: '',
        adyear: '',
        admonth: '',
        day: '',
        week: ['日', '一', '二', '三', '四', '五', '六'],
        monthList: [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31],
        weekDay: 1,
        lastWeekDay: 1,
        activeDay: [],
        consultationId: '', //咨询ID
        newJson: {},
        newJsonNumber: {},
        startTimeDetail: "", // 咨询计划的开始时间
        endTimeDetail: "" // 咨询计划的结束时间
      }
    },
    methods: {
      isDisaled(time) {
        //判断开始年月份是否相同
        let year = this.year;
        let month = this.month > 9 ? this.month : '0' + this.month;
        let day = time > 9 ? time : '0' + time;
        let date = year + '-' + month + '-' + day
        let d = (new Date(date)).getTime()
        let status = true
        for (let i = 0; i < this.allTimeArr.length; i++) {
          console.log(this.allTimeArr[i], d, this.allTimeArr[i][0] <= d && this.allTimeArr[i][1] >= d)
          if (this.allTimeArr[i][0] <= d && this.allTimeArr[i][1] >= d) {
            status = false
            break
          }
        }
        return status
      },
      //dataNow
      dataNow(day) {
        let d = day < 10 ? '0' + day : day
        let m = this.month < 10 ? '0' + this.month : this.month
        return this.year + '-' + m + '-' + d;
      },
      //返回咨询信息
      go() {
        this.$router.push({
          path: `consultingSchoolManagement`
        })
      },
      //查看信息
      see(date, isOpenday, id) {
        let d = (new Date(date)).getTime()
        let status = false
        for (let i = 0; i < this.allTimeArr.length; i++) {
          console.log(this.allTimeArr[i], d, this.allTimeArr[i][0] <= d && this.allTimeArr[i][1] >= d)
          if (this.allTimeArr[i][0] <= d && this.allTimeArr[i][1] >= d) {
            status = true
            break
          }
        }
        if (!status) {
          return
        }
        let openid = id ? id : 0
        // if(isOpenday) {
        this.$router.push({
          path: `/seeConsultingSchoolManagement`,
          query: {
            date,
            consultationId: this.consultationId,
          }
        })
        // }
      },
      preMonthSize() {
        return this.month - 1 === 0 ? 31 : this.monthList[this.month - 2]
      },

      //获取接待日信息列表
      getConsultationDayList(id) {
        getConsultationDayList(id).then(res => {
          console.log(res, '获取接待日信息列表');
          let arr = [];
          let newJson = {}
          for (let i = 0; i < res.data.length; i++) {
            arr.push(res.data[i].opendayDate)
            newJson[res.data[i].opendayDate] = {
              opendayId: res.data[i].opendayId,
              status: res.data[i].isOpenday
            }
          }
          this.activeDay = arr
          this.newJson = newJson
          console.log(newJson, 888);
        }).catch(err => {
          console.log(err);
        })
      },
      //显示详情
      showTitle() {
        console.log(132)
      },
      // 获得今天的日期
      getCurrentDate() {
        const date = new Date()
        this.year = date.getFullYear()
        this.month = date.getMonth() + 1
        this.adyear = date.getFullYear()
        this.admonth = date.getMonth() + 1
        this.day = date.getDate()
      },
      // 根据年月日获得为星期几
      getWeekDay(year, month, day) {
        return new Date(`${year}/${month}/${day}`).getDay()
      },
      initDate() {
        if (
          (this.year % 4 === 0 && this.year % 100 !== 0) || this.year % 400 === 0
        ) {
          this.monthList[1] = 29
        } else {
          this.monthList[1] = 28
        }
        // 获得指定年月的1号是星期几
        const firstDay = this.getWeekDay(this.year, this.month, 1)
        // 在1号前面填充多少个上个月的日期
        this.weekDay = firstDay === 7 ? 0 : firstDay
        // 获得最后一天是星期几，往后填充多少个
        const remineDay = this.getWeekDay(
          this.year,
          this.month,
          this.monthList[this.month - 1]
        )
        this.lastWeekDay = remineDay === 7 ? 6 : 6 - remineDay
      },
      changeDate(type) {
        switch (type) {
          case 'preYear':
            this.year -= 1
            break
          case 'preMonth':
            // 当前月份为1月， 上一个月分为12月，年份减1
            if (this.month === 1) {
              this.month = 12
              this.year -= 1
            } else {
              this.month -= 1
            }
            break
          case 'nextYear':
            this.year += 1
            break
          case 'nextMonth':
            // 当前月份为12月， 下个月分为1月，年份加1
            if (this.month === 12) {
              this.month = 1
              this.year += 1
            } else {
              this.month += 1
            }
            break
          default:
            break
        }
        this.initDate()
      },

      //获取咨询ID
      getgetConsultationId() {
        getConsultationId().then(res => {
          console.log(res, 55556);
          this.consultationId = res.data
          this.getConsultationCalender(res.data)
          this.getConsultationDayList(res.data)
          this.getConsultationDetail(res.data)
        }).catch(err => {
          console.log(err);
        })
      },
      //获取接待日信息列表
      getConsultationCalender(id) {
        getConsultationCalender(id).then(res => {
          console.log(res, '获取咨询人数日历');
          let data = res.data;
          let json = {}
          for (let i = 0; i < data.length; i++) {
            json[data[i].date] = data[i].actualAmount
          }
          this.newJsonNumber = json;
        }).catch(err => {
          console.log(err);
        })
      },
      //获取咨询计划，主要获取计划时间
      getConsultationDetail(id) {
        getConsultationDetail(id).then(res => {
          console.log(res, "获取咨询计划，主要获取计划时间");
          let arr = []
          for (let i = 0; i < res.data.length; i++) {
            arr.push([new Date(res.data[i].startTime).getTime(), new Date(res.data[i].endTime).getTime()])
          }
          this.allTimeArr = arr;
          this.startTimeDetail = new Date(res.data.startTime).getTime();
          this.endTimeDetail = new Date(res.data.endTime).getTime();
          console.log(this.startTimeDetail, this.endTimeDetail)
        })
      },
    },
    created() {
      this.getCurrentDate()
      this.initDate()
      this.getgetConsultationId()
    }
  }
</script>
<style>
  .is-selected {
    color: red;
  }

  .canlendarPanel {
    width: 1079px;
    margin: 0 auto;
  }

  .canlendar-header {
    display: flex;
    margin-bottom: 20px;
    margin-top: 50px;
    justify-content: space-between;
  }

  .canlendar-header>p {
    margin-right: 20px;
  }

  .canlendar-header>p>span {
    display: inline-block;
    padding: 8px 10px;
    background-color: #409eff;
    color: white;
    font-size: 12px;
    margin-left: 10px;
    border-radius: 5px;
  }

  .header-btn {
    text-align: right;
    width: 98.5%;
    margin: 20px 0;
    position: relative;
  }

  .header-btn h1 {
    text-align: center;
  }

  .header-btn>div {
    position: absolute;
    right: 0;
    top: 20px;
  }

  .canlendar-main .header li {
    color: #606266;
    font-weight: 400;
  }

  .canlendar-main ul {
    list-style: none;
    display: flex;
    flex-wrap: wrap;
  }

  .canlendar-main ul li.active:hover {
    cursor: pointer;
    background-color: #f2f8fe;
  }

  .showCuro:hover {
    background-color: #f2f8fe !important;
    cursor: pointer;
  }

  .canlendar-main ul li {
    width: 14%;
    text-align: center;
    height: 100px;
    display: flex;
    justify-content: center;
    align-items: center;
    color: #606266;
    font-weight: 400;
    border-right: 1px solid #ebeef5;
    border-top: 1px solid #ebeef5;
    flex-direction: column;
    background-color: white;
  }

  .canlendar-main ul li.disabled {
    color: #c0c4cc;
  }

  .canlendar-main ul li.currentDay {
    background-color: #f2f8fe;
    color: #1989fa
  }

  .canlendar-main ul li.active {
    color: red;
  }
</style>