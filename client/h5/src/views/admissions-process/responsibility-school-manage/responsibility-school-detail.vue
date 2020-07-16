<template>
  <div class="responsibility-school-detail">
    <div class="responsibility-school-detail-header">
      <van-sticky :offset-top="0">
        <h5-header
          :leftStatus="true"
          title="学校信息维护"
          rigText="新增班级"
          :isLeft="true"
          @clickHeadLeft="clickHeadLeft"
          @clickHeadAction="navToAddClass"
        ></h5-header>
      </van-sticky>
    </div>
    <div class="responsibility-school-detail-content">
      <!-- 学校信息 -->
      <school-info :info="schoolInfo" @click="navToEditSchool" />
      <div class="school-classes">
        <div class="school-classes-title">班级</div>
        <van-list
          v-if="list.length!=0"
          class="class-student-list"
          v-model="loading"
          :finished="finished"
          finished-text="没有更多了"
          @load="onLoad"
        >
          <!-- 班级信息 -->
          <class-info
            :info="item"
            v-for="item in list"
            :key="item.id"
            :classId="item.classId"
            :schoolId="schoolId"
            @click="navToClassDetail(item.classId)"
          />
        </van-list>
        <custom-empty v-else :type="1" height="calc(100vh - 350px)" />
      </div>
    </div>
  </div>
</template>

<script>
import Header from "@/components/cheader";
import SchoolInfo from "./components/school-info";
import ClassInfo from "./components/class-info";
import Vue from "vue";
import { Form, CellGroup } from "vant";
import CustomEmpty from "@/components/custom-empty";
import { Sticky, List, Icon, Cell } from "vant";
Vue.use(Sticky);
Vue.use(List);
Vue.use(Icon);
Vue.use(Cell);
Vue.use(Form);
Vue.use(CellGroup);

import {
  getSourceSchoolDetail,
  listSchoolClassPage
} from "../../../api/sourceSchool/sourceSchool";
export default {
  name: "responsibility-school-detail",
  components: {
    "h5-header": Header,
    "school-info": SchoolInfo,
    "class-info": ClassInfo,
    "custom-empty": CustomEmpty
  },
  data() {
    return {
      isFinish: false, //用作渲染，数据加载完成时才做渲染处理
      schoolId: null,
      // 列表
      list: [],
      loading: false,
      finished: false,
      schoolInfo: {},
      query: {
        pageNum: 1,
        pageSize: 10,
        sourceSchoolId: ""
      },
      total: 10
    };
  },
  methods: {
    onLoad() {
      this.query.sourceSchoolId = this.schoolId;
      let query = { ...this.query };
      this.getClass(query);
    },
    /**
     * 获取生源学校详情
     * @param {string} id 生源学校id
     * @author cwz
     */
    getSchool() {
      let id = this.schoolId;
      this.$message.loading("正在加载...");
      getSourceSchoolDetail(id)
        .then(res => {
          this.$message.close();
          this.isFinish = true;
          this.schoolInfo = res.data;
          this.onLoad()
        })
        .catch(err => {
          console.error("加载失败：", err);
          this.$message.close();
        });
    },
    /**
     * 分页获取生源学校下的班级
     * @param {obj} query 生源学校id、pageSize、pageNum
     * @author cwz
     */
    getClass(query) {
      listSchoolClassPage(query).then(res => {
        this.query.pageSize = res.data.size;
        this.total = res.data.total;
        let data = res.data.current === 1 ? [] : this.list;
        this.list = data.concat(res.data.records);
        // 加载状态结束
        this.loading = false;
        // 数据全部加载完成
        this.query.pageNum = this.query.pageNum + 1;
        if (this.list.length >= this.total) {
          this.finished = true;
        }
      });
    },
    /**
     * 编辑学校信息页面跳转
     */
    navToEditSchool() {
      let id = this.schoolId;
      this.$router.push({
        path: `/admissions-process/responsibility-school-manage/responsibility-school-edit/${id}`
      });
    },
    /**
     * 返回责任学校列表跳转
     */
    clickHeadLeft() {
      this.$router.push({
        path: `/admissions-process/responsibility-school-manage/school-list`
      });
    },
    /**
     * 新增班级页面跳转
     */
    navToAddClass() {
      let sid = this.schoolId;
      this.$router.push({
        path: `/admissions-process/responsibility-school-manage/school-class-add/${sid}`
      });
    },
    /**
     * 进入班级页面跳转
     */
    navToClassDetail(id) {
      let sid = this.schoolId;
      this.$router.push({
        path: `/admissions-process/responsibility-school-manage/school-class-detail/${sid}/${id}`
      });
    }
  },
  created() {
    //获取学校ID
    this.schoolId = this.$route.params.id;
    //获取学校详情
    this.getSchool();
    //获取学校下班级信息
    // this.getClass();
  }
};
</script>

<style lang="stylus" scoped>
.responsibility-school-detail {
  min-height 100vh
  // &-header{
  // }
  &-content {
    height 100px
    .school-classes {
      background: rgba(242, 242, 242, 1);
      &-title {
        margin-left: 16px;
        font-size: 16px;
        font-weight: 400;
        color: rgba(153, 153, 153, 1);
        height: 32px;
        line-height: 32px;
      }
    }
  }
}
</style>