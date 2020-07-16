<template>
  <div class="student-search">
    <van-sticky :offset-top="0">
      <h5-header :leftStatus="true" title="学生管理"></h5-header>
    </van-sticky>
    <div class="student-search-content">
      <van-row type="flex" justify="center">
        <van-col :span="24">
          <van-search
            v-model="query.studentName"
            placeholder="请输入学生姓名"
            input-align="left"
            maxlength="20"
            clearable
            show-action
            @input="onDebounceInput"
            @search="querySearch"
            @cancel="$router.go(-1)"
          ></van-search>
        </van-col>
      </van-row>
      <div class="search-list">
        <van-cell :title="item.studentName" v-for="item in list" :key="item.studentInfo" is-link value="查看" @click="navToDetail(item.studentInfo)" />
      </div>
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import Header from "@/components/cheader";

import { listStudentInfoPage } from "../../../api/Student-info/Student-info.js";

import { Sticky, Search, Col, Row, Cell } from "vant";
Vue.use(Sticky);
Vue.use(Search);
Vue.use(Col);
Vue.use(Row);
Vue.use(Cell);

import {debounce} from "@/utils/index";


export default {
  name: "student-search",
  components: {
    h5Header: Header
  },
  data() {
    return {
      onDebounceInput:null,
      query: {
        pageNum: 1,
        pageSize: 20,
        studentName: ""
      },
      list:[]
    };
  },
  methods: {
    /**
     * 跳转到学生详情页面
     * @param {string } 学生ID
     * @author lmh
     */
    navToDetail(id) {
      this.$router.push({ path: `/student-manage/student-detail/${id}` });
    },
    /**
     * 关键字查询
     * @author lmh
     */
    querySearch(){
      let queryParam = {...this.query}
      this.listStudentInfoPage(queryParam);
    },
    /**
     * 分页查询学生列表
     */
    listStudentInfoPage(queryParam) {
      listStudentInfoPage(queryParam).then(res => {
        this.list = res.data.records;
      });
    }
  },
  created() {
    //输入查询防抖处理
    this.onDebounceInput = debounce(this.querySearch);
  }
};
</script>

<style scoped lang="stylus">
.student-search {
  &-content {
    // background-color: bgColor;
  }
}
</style>
