<template>
  <div>
    <van-sticky :offset-top="0">
      <h5-header :leftStatus="true" title="诉求查看"></h5-header>
    </van-sticky>
    <div v-if="list.length!=0&&list!=null&&list!=[]">
      <appeal-list-item
        v-for="(item,index) in list "
        :show-detail="false"
        :key="index"
        :info="item"
        :status="true"
      />
    </div>
    <custom-empty v-else :type="1" height="calc(100vh - 200px)" />
  </div>
</template>

<script>
import Header from "@/components/cheader";
import CustomEmpty from "@/components/custom-empty";
import AppeallistItem from "@/views/admissions-process/appeal-manage/appeal-list-item";

import Vue from "vue";
import { Sticky } from "vant";
Vue.use(Sticky);

import { queryRequestInfo } from "../../../api/appeal/appeal-management.js";

export default {
  name: "student-appeal",
  components: {
    "h5-header": Header,
    "appeal-list-item": AppeallistItem,
    "custom-empty": CustomEmpty
  },
  data() {
    return {
      studentId: null,
      studentPublicRelationId: null,
      list: []
    };
  },
  methods: {
    queryRequestInfo() {
      let id = this.studentId;
      this.$message.loading();
      queryRequestInfo({
        studentInfo: id
      })
        .then(res => {
          this.$message.close();
          this.list = res.data;
        })
        .catch(err => {
          console.error(err);
        });
    }
  },
  created() {
    this.studentId = this.$route.params.id;
    this.studentPublicRelationId = this.$route.params.relationId;
    //获取学生的咨询信息
    this.queryRequestInfo();
  }
};
</script>

<style lang="stylus" scoped></style>