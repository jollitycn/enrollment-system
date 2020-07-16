<template>
  <div class="student-target-search">
    <van-sticky :offset-top="0">
      <h5-header :leftStatus="true" title="学生管理"></h5-header>
    </van-sticky>
    <div class="student-target-search-content">
      <van-row type="flex" justify="center">
        <van-col :span="24">
          <van-search v-model="query.searchKey" placeholder="请输入搜索关键词"></van-search>
        </van-col>
      </van-row>
      <div class="search-list">
        <van-cell title="XXX学生" is-link value="查看" @click="navToEdit(1)" />
        <van-cell title="XXX学生" is-link value="查看" @click="navToEdit(1)" />
        <van-cell title="XXX学生" is-link value="查看" @click="navToEdit(1)" />
        <van-cell title="XXX学生" is-link value="查看" @click="navToEdit(1)" />
      </div>
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import Header from "@/components/cheader";

import { Sticky, Search, Col, Row, Cell } from "vant";
Vue.use(Sticky);
Vue.use(Search);
Vue.use(Col);
Vue.use(Row);
Vue.use(Cell);

export default {
  name: "student-target-search",
  components: {
    h5Header: Header
  },
  data() {
    return {
      query: {
        searchKey: ""
      }
    };
  },
  methods: {
    navToEdit(id) {
      this.$router.push({
        path: `/student-target-manage/student-target-edit/${id}`
      });
    }
  },
  created() {}
};
</script>

<style scoped lang="stylus">
.student-target-search {
  &-content {
  }
}
</style>
