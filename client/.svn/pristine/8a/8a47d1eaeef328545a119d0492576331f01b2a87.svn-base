<template>
  <div class="school-class-detail">
    <div class="school-class-detail-header">
      <van-sticky :offset-top="0">
        <h5-header
          :leftStatus="true"
          :title="className"
          rigText="新增学生"
          @clickHeadAction="showAddStuPicker = true"
        ></h5-header>
      </van-sticky>
    </div>
    <div class="school-class-detail-content">
      <class-detail-info :info="classInfo" @click="navToEditClass" />
      <div class="class-student">
        <!-- <div class="class-student-navs">
          <div
            :class="{active:isActive}"
            @click="changeType(1)"
            class="class-student-navs-item"
          >目标生({{classInfo.aimStudentCount}})</div>
          <div
            :class="{active:!isActive}"
            @click="changeType(0)"
            class="class-student-navs-item"
          >意向生({{classInfo.studentCount}})</div>
        </div>-->
        <van-tabs
          v-model="isActive"
          color="#00CCFF"
          title-active-color="#00CCFF"
          title-inactive-color="#999999"
          line-width="30%"
          line-height="3px"
          @click="changeType(isActive)"
        >
          <van-tab :title="'意向生('+classInfo.studentCount+')'">
            <van-divider :style="{margin: '0' }" />
            <van-list
              class="class-student-list"
              v-model="loading"
              :finished="finished"
              finished-text="没有更多了"
              @load="onLoad"
              :immediate-check="false"
            >
              <class-student-info
                :info="item"
                class="class-student-list-item"
                v-for="(item,index) in list"
                :key="index"
                @click="navToStuentDetail(item.aimStudentId,item.studentInfo)"
              />
            </van-list>
          </van-tab>
          <van-tab :title="'目标生('+classInfo.aimStudentCount+')'">
            <van-divider :style="{margin: '0' }" />
            <van-list
              class="class-student-list"
              v-model="loading"
              :finished="finished"
              finished-text="没有更多了"
              @load="onLoad"
              :immediate-check="false"
            >
              <class-student-info
                :info="item"
                class="class-student-list-item"
                v-for="(item,index) in list"
                :key="index"
                @click="navToStuentDetail(item.aimStudentId,item.studentInfo)"
              />
            </van-list>
          </van-tab>
        </van-tabs>
      </div>

      <!-- 新增学生弹框 -->
      <van-popup v-model="showAddStuPicker" position="bottom" round>
        <div class="student-type-popup">
          <div class="popup-content">
            <div class="student-type" @click="navToAddStudent(1)">目标生</div>
            <div class="student-type" @click="navToAddStudent(2)">意向生</div>
          </div>
          <div class="popup-footer">
            <div class="cancel-btn" @click="showAddStuPicker=false">取消</div>
          </div>
        </div>
      </van-popup>
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import Header from "@/components/cheader";
import { Sticky, List, Popup, Tab, Tabs } from "vant";
import { Divider } from "vant";
Vue.use(Divider);
Vue.use(Sticky);
Vue.use(List);
Vue.use(Popup);
Vue.use(Tab);
Vue.use(Tabs);

import ClassDetailInfo from "./components/class-detail-info";
import ClassStudentInfo from "./components/class-student-info";

import { getClassInfo } from "../../../api/sourceSchool/sourceSchool";
import {
  listAimStudentPage,
  listStudentInfoPage
} from "../../../api/Student-info/Student-info";
export default {
  name: "school-class-detail",
  components: {
    "h5-header": Header,
    "class-detail-info": ClassDetailInfo,
    "class-student-info": ClassStudentInfo
  },
  data() {
    return {
      stuAmount: 0,
      targetStuAmount: 0,
      active: ["意向生", "目标生"],
      isActive: 0,
      classId: null,
      schoolId: null,
      classInfo: {},
      className: "",
      // 列表
      list: [],
      stulist: [],
      type: 0,
      loading: false,
      finished: false,
      query: {
        pageNum: 1,
        pageSize: 10,
        classId: "",
        name: ""
      },
      total: 10,
      //新增学生弹框
      showAddStuPicker: false
    };
  },
  created() {
    //获取班级id
    this.classId = this.$route.params.id;
    //获取学校id
    this.schoolId = this.$route.params.sid;
    this.query.classId = this.classId;
    //获取班级信息
    this.getClassInfo();
    this.onLoad();
  },
  methods: {
    onLoad() {
      let query = { ...this.query };
      //意向生和目标生的列表切换
      if (this.isActive) {
        this.query.pageNum = 1;
        this.getStu(query);
      } else {
        this.query.pageNum = 1;
        this.getReasonStu(query);
      }
    },
    /**
     * 获取班级信息
     * @param {string} id 班级ID
     * @author cwz
     */
    getClassInfo() {
      let id = this.classId;
      this.$message.loading("正在加载...");
      getClassInfo(id)
        .then(res => {
          this.$message.close();
          this.classInfo = res.data;
          this.className = res.data.className;
        })
        .catch(err => {
          console.error("加载失败：", err);
          this.$message.close();
        });
    },
    /**
     * 分页获取生源学校班级下的学生(目标生)
     * @param {obj} query 班级ID、pageSize、pageNum
     * @author cwz
     */
    getStu(query) {
      this.query.pageNum++;
      this.$message.loading("正在加载...");
      listAimStudentPage(query)
        .then(res => {
          this.$message.close();
          this.query.pageSize = res.data.size;
          this.targetStuAmount = res.data.total;
          let data = res.data.current === 1 ? [] : this.list;
          for (let i = 0; i < res.data.records.length; i++) {
            res.data.records[i].status = null;
          }
          console.log(res.data.records, 11111);

          this.list = data.concat(res.data.records);
          // 加载状态结束
          this.loading = false;

          if (this.list.length >= this.targetStuAmount) {
            this.finished = true;
          }
        })
        .catch(err => {
          console.error("加载失败：", err);
          this.$message.close();
        });
    },
    /**
     * 分页获取生源学校班级下的学生(意向生)
     * @param {obj} query 班级ID、pageSize、pageNum
     * @author cwz
     */
    getReasonStu(query) {
      this.query.classId = this.classId;
      this.query.pageNum++;
      this.$message.loading("正在加载...");
      listStudentInfoPage(query)
        .then(res => {
          this.$message.close();
          this.query.pageSize = res.data.size;
          this.stuAmount = res.data.total;
          let data = res.data.current === 1 ? [] : this.list;
          this.list = data.concat(res.data.records);
          // 加载状态结束
          this.loading = false;
          // 数据全部加载完成
          if (this.list.length >= this.stuAmount) {
            this.finished = true;
          }
        })
        .catch(err => {
          console.error("加载失败：", err);
          this.$message.close();
        });
    },
    /**
     * 跳转编辑班级页面
     * @param {string} id 班级ID
     */
    navToEditClass(id) {
      this.showAddStuPicker = false;
      let sid = this.schoolId;
      this.$router.push({
        path: `/admissions-process/responsibility-school-manage/school-class-edit/${sid}/${id}`
      });
    },
    /**
     * 跳转到新增学生页面
     * @param {number} type 类型，1，目标生，2，意向生
     */
    navToAddStudent(type) {
      switch (type) {
        case 1:
          //目标生新增，带上学校id和班级id
          this.$router.push({
            name: "student-target-add",
            params: {
              sid: this.schoolId,
              cid: this.classId
            }
          });
          break;
        case 2:
          //意向生报名，带上学校id和班级id
          this.$router.push({
            name: "student-add",
            params: {
              sid: this.schoolId,
              cid: this.classId
            }
          });
          break;
      }
    },
    /**
     * 跳转编辑班级页面
     * @param {string} aimStudentId 查询目标生对应的学生Id
     * @param {string} aimStudentId 查询意向生对应的学生Id
     */
    navToStuentDetail(aimStudentId, studentInfo) {
      if (this.isActive) {
        this.$router.push({
          path: `/student-target-manage/student-target-edit/${aimStudentId}`
        });
      } else {
        this.$router.push({
          path: `/student-manage/student-detail/${studentInfo}`,
          query: {
            isToResSchool: true
          }
        });
      }
    },
    changeType(type) {
      this.query.pageNum = 1;
      this.list = [];
      this.stulist = [];
      if (type) {
        this.isActive = 1;
        this.type == 1;
        this.onLoad();
      } else {
        this.isActive = 0;
        this.type == 0;
        this.onLoad();
      }
    }
  }
};
</script>

<style scoped lang="stylus">
>>>.van-tab {
  flex: 0.35;
}

.school-class-detail {
  &-content {
    min-height: calc(100vh - 60px);

    .class-student {
      background-color: #F2F2F2;

      &-navs {
        display: flex;

        &-item {
          flex: 1;
          height: 37px;
          text-align: center;
          margin: 0 16px;
          margin-top: 16px;
          line-height: 37px;
          background: #FFFFFF;
          border-radius: 20px;
          color: #333333;
          font-size: 15px;
          font-weight: 500;
        }

        &-item.active {
          background: #00CCFF;
          color: #fff;
        }
      }

      &-list {
        &-item {
          padding-left: 16px;
          background: rgba(255, 255, 255, 1);
          border-radius: 6px;
        }
      }
    }
  }

  .student-type-popup {
    text-align: center;
    font-size: 18px;
    font-weight: 400;

    .popup-content {
      .student-type {
        padding: 16px;
        height: 25px;
        line-height: 25px;
        border-bottom: solid 1px #ddd;
      }
    }

    .popup-footer {
      .cancel-btn {
        border-top: solid 1px #F5F5F5;
        padding: 16px;
        height: 25px;
        line-height: 25px;
      }
    }
  }
}
</style>