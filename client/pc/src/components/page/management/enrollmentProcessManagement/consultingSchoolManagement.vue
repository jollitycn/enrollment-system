<template>
  <div class="page-container">
    <div class="crumbs">
      <el-breadcrumb separator=">">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>招生过程管理</el-breadcrumb-item>
        <el-breadcrumb-item>咨询信息</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div style="margin-top: 20px;">
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="所属招生计划">
          <el-select v-model="semesterId" class="wid100" placeholder="请选择招生计划">
            <el-option v-for="item in semesterQuery" :key="item.semesterId" :label="item.title"
              :value="item.semesterId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-button @click="getgetConsultationId(semesterId)" type="primary">查询</el-button>
      </el-form>
    </div>
    <div class="canlendarPanel" v-loading="loading" element-loading-text="拼命加载中">
      <div class="header-btn">
        <h1>{{ `${year}年${month}月` }}</h1>
        <div>
          <el-button type="primary" @click="go2" v-show="!consultationId">新增咨询模板</el-button>
          <el-button type="primary" @click="go('edit')" v-show="consultationId">咨询模板设置</el-button>
          <!-- <el-button type="primary" @click="goCom" v-show="consultationId">咨询管理</el-button> -->
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
          <li style="cursor:pointer;" v-for="inx in weekDay" class="disabled" @click="changeDate('preMonth')">
            {{ preMonthSize() - weekDay + inx }}</li>
          <li
            @click="see(dataNow(index),(newJson[dataNow(index)] ? newJson[dataNow(index)].status : 0 ),newJson[dataNow(index)]? newJson[dataNow(index)].opendayId : undefined)"
            :class="{ currentDay: (index === day && adyear == year && admonth == month ),
              active:Boolean(newJson[dataNow(index)] ? newJson[dataNow(index)].status : 0) ,
              showCuro: true, disabled: isDisaled(index),
              showRegister:showSchool&&showSchool==0,
              showRegister:showSchool&&showSchool==0 } " v-for="index in monthList[month - 1]">
            <div><span>{{ index }}</span>
              <!-- <div v-show="(Boolean(newJson[dataNow(index)] ? newJson[dataNow(index)].status : 0)) || (isNum(index))"
                class="spot" /> -->
              <div v-show="(newJson[dataNow(index)])&&(newJson[dataNow(index)].status === 0)" class="spot" />
            </div>
            <span class="activeDay"
              v-show="Boolean(newJson[dataNow(index)] ? newJson[dataNow(index)].status : 0)">{{newJson[dataNow(index)] ?  newJson[dataNow(index)].status === 1? '接待日': '已取消': ''}}</span>
            <div class="all-numCount">
              <span class="numCount"
                v-show="(isNum(index))&&(newCount[dataNow(index)].personalCount!=0)">{{isNum(index)? newCount[dataNow(index)].personalCount: ''}}人</span>
              <span v-show="isNum(index)">&nbsp;</span>
              <span class="numCount"
                v-show="isNum(index)&&(newCount[dataNow(index)].schoolCount!=0)">{{isNum(index)? newCount[dataNow(index)].schoolCount: ''}}学校</span>
            </div>
          </li>
          <li style="cursor:pointer;" v-for="index in lastWeekDay" @click="changeDate('nextMonth')" class="disabled">
            {{ index }}</li>
        </ul>
      </div>
    </div>
  </div>
</template>


<script>
  import { getDefaultPlanId, getConsultationDetail, getConsultationId, getConsultationDayList, getRegisterCount } from "@/api/management/enrollmentProcessManagement/consultingSchoolManagement"
  import { queryAllFinishedPlan } from '@/api/admin/enrollmentPlanList/enrollmentPlan.js'
  export default {
    name: 'consultingSchoolManagement',
    data() {
      return {
        semesterQuery: [],
        semesterId: '',
        showSchool: "",
        showStudent: "",
        allTimeArr: [],
        loading: false,
        form: {
          pageNum: 1,
          pageSize: 10
        },
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
        countDay: [],
        consultationId: '', //咨询ID
        newJson: {},
        newCount: {},
        startTimeDetail: [], // 咨询计划的开始时间
        endTimeDetail: [] // 咨询计划的结束时间
      }
    },
    methods: {
      /**
       * 重置页面接口获取的信息
       * @author lgh
       */
      resetInfo() {
        this.showSchool = ""
        this.showStudent = ""
        this.allTimeArr = []
        this.activeDay = []
        this.countDay = []
        this.consultationId = '' //咨询ID
        this.newJson = {}
        this.newCount = {}
        this.startTimeDetail = [] // 咨询计划的开始时间
        this.endTimeDetail = [] // 咨询计划的结束时间
      },
      /**
       * 获取所有已发布计划信息
       * @author lgh
       */
      queryAllFinishedPlan() {
        queryAllFinishedPlan().then(res => {
          this.getCurrentDate()
          this.initDate()
          // this.$set( this.formInline,'semesterId',this.semesterQuery[0].semesterId)
          this.semesterQuery = res.data
          this.semesterId = this.semesterQuery[0].semesterId
          this.getgetConsultationId(this.semesterId)
        })
      },
      /*用于判断是否为接待日期*/
      isNum(index) {
        if (this.newCount[this.dataNow(index)]) {
          console.log(this.newCount, 1111);

          //  this.newCount[this.dataNow(index)].personalCount
          return true
        } else {
          return false
        }
      },
      //判断是否有人数
      isDisaled(time) {
        //判断开始年月份是否相同
        let year = this.year;
        let month = this.month > 9 ? this.month : '0' + this.month;
        let day = time > 9 ? time : '0' + time;
        let date = year + '-' + month + '-' + day
        let d = (new Date(date)).getTime()
        let status = true
        // for (let i = 0; i < this.allTimeArr.length; i++) {
        //   // console.log(this.allTimeArr[i], d, this.allTimeArr[i][0] <= d && this.allTimeArr[i][1] >= d)
        //   if (this.allTimeArr[i][0] <= d && this.allTimeArr[i][1] >= d) {
        //     status = false
        //     break
        //   }
        // }
        if (this.startTimeDetail <= d && this.endTimeDetail >= d) {
          status = false
        }
        return status
      },
      //dataNow
      dataNow(day) {
        let d = day < 10 ? '0' + day : day
        let m = this.month < 10 ? '0' + this.month : this.month
        return this.year + '-' + m + '-' + d;
      },
      //创建信息
      go(type) {
        this.$router.push({
          path: `/consultingSchoolManagement/${type}`,
          query: {
            id: this.consultationId
          }
        })
      },
      //去往咨询管理
      goCom() {
        this.$router.push({
          path: `/addConsultingSchoolManagemenNow`
        })
      },

      //查看信息
      see(date, isOpenday, id) {
        let d = (new Date(date)).getTime()
        let status = false
        // for (let i = 0; i < this.allTimeArr.length; i++) {
        //   // console.log(this.allTimeArr[i], d, this.allTimeArr[i][0] <= d && this.allTimeArr[i][1] >= d)
        //   if (this.allTimeArr[i][0] <= d && this.allTimeArr[i][1] >= d) {
        //     status = true
        //     break
        //   }
        // }
        if (this.startTimeDetail <= d && this.endTimeDetail >= d) {
          status = true
        }
        if (!status) {
          return
        }
        let openid = id ? id : 0
        // this.$router.push({
        //   path: `/addConsultingSchoolManagementDay`,
        //   query: {
        //     date,
        //     isOpenday,
        //     consultationId: this.consultationId,
        //     openid
        //   }
        // })
        this.$router.push({
          path: `/seeConsultingSchoolManagement`,
          query: {
            date,
            isOpenday,
            consultationId: this.consultationId,
            openid
          }
        })
      },
      preMonthSize() {
        return this.month - 1 === 0 ? 31 : this.monthList[this.month - 2]
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
      //创建咨询计划
      go2() {
        this.$router.push({
          path: `/consultingSchoolManagement/add`
        })
      },
      //获取默认计划id
      getDefaultPlanId() {
        getDefaultPlanId().then(res => {
          if (res.data) {
            console.log("拥有默认计划");

          } else {
            this.$message({
              message: '不存在默认计划，请设置默认计划！',
              type: "warning",
              duration: 1000,
              onClose: () => {
                this.$router.push({
                  path: `/enrollmentPlanList`
                })
              }
            });
          }
        })
      },
      //获取咨询ID
      getgetConsultationId(semesterId) {
        this.resetInfo()
        this.loading = true
        getConsultationId(semesterId).then(res => {
          console.log(res, 55556);
          let _this = this;
          this.consultationId = res.data
          if (this.consultationId) {
            this.getConsultationDayList(res.data)
            this.getRegisterCount(res.data)
            this.getConsultationDetail()
          } else {
            this.$message({
              message: '暂无咨询计划，请添加。',
              type: "warning",
              duration: 1500,
              onClose: () => {
                // _this.$router.push({
                //     path: `/consultingSchoolManagement/add`
                // })
              }
            });
          }
          this.loading = false
        }).catch(err => {
          console.log(err);
          this.loading = false
        })
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
          console.log(this.activeDay, 888);
          console.log(this.newJson, 999);
        }).catch(err => {
          console.log(err);
        })
      },
      //获取咨询人数
      getRegisterCount(id) {
        getRegisterCount(id).then(res => {
          console.log(res, '获取咨询人数');
          let arr = [];
          let newCount = {}
          for (let i = 0; i < res.data.length; i++) {
            arr.push(res.data[i].consultationDate)
            newCount[res.data[i].consultationDate] = {
              personalCount: res.data[i].personalCount,
              schoolCount: res.data[i].schoolCount
            }
          }
          this.countDay = arr
          this.newCount = newCount
          console.log(newCount, 888);
        })
      },
      //获取咨询计划，主要获取计划时间
      getConsultationDetail() {
        getConsultationDetail(this.consultationId).then(res => {
          console.log(res, "获取咨询计划，主要获取计划时间");
          //   let arr = []
          //   for (let i = 0; i < res.data.length; i++) {
          //     arr.push([new Date(res.data[i].startTime).getTime(), new Date(res.data[i].endTime).getTime()])
          //   }
          //   this.allTimeArr = arr;
          // })
          this.startTimeDetail = new Date(res.data.startTime).getTime();
          this.endTimeDetail = new Date(res.data.endTime).getTime();
        })
      },
      /**
       * 获取数据
       * @author lgh
       */
      getData() {
        this.getDefaultPlanId()
        // this.getgetConsultationId()
        // this.getConsultationDetail()
        // this.getConsultationDayList(res.data)
      }
    },
    created() {
      this.getData()
      this.queryAllFinishedPlan()
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

  .header-btn {
    text-align: right;
    width: 98.5%;
    margin: 20px 0;
    position: relative;
  }

  .header-btn h1 {
    text-align: center;
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
    cursor: default;
  }

  .canlendar-main ul li.currentDay {
    background-color: #f2f8fe;
    color: #1989fa
  }

  .canlendar-main ul li.active {
    color: red;
  }

  .spot {
    display: inline-block;
    width: 6px;
    height: 6px;
    border-radius: 50%;
    background-color: red;
    margin-left: 5px;
    margin-bottom: 3px;
  }

  .activeDay {
    font-size: 12px;
  }

  .numCount {
    background-color: #2385FE;
    color: white;
    border-radius: 10px;
    padding: 3px;
    font-size: 12px;
  }
</style>