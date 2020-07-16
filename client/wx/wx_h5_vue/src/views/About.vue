<template>
  <div class="school-detail">
    <van-sticky :offset-top="0">
      <h5-header :leftStatus="true" :title="data.recruitTarget"></h5-header>
    </van-sticky>
    <div class="school-detail-title">
      <span>{{data.recruitTarget}}</span>
    </div>
    <div class="school-detail-content" v-html="resetHtml(data.planDescription)"></div>
  </div>
</template>

<script>
import { queryRecruitSchoolInfo } from "@/api/home/home";
import Header from "@/components/cheader";
import { Sticky } from "vant";
import { webImgDir } from "@/config/index"
export default {
  name: "About",
  components: {
    vanSticky: Sticky,
    h5Header: Header
  },
  data() {
    return {
      data: {}
    };
  },

  methods: {
    resetHtml(val) {
      if (val) {
        return val.replace(/\/admin/g, webImgDir);
      }
    },
    getSchoolInfo(id) {
      queryRecruitSchoolInfo(id).then(res => {
        console.log(res);
        this.data = res.data
      });
    }
  },
  created() {
    console.log(this.$route.query.apiId);
    let id = this.$route.query.apiId;
    this.getSchoolInfo(id);
  }
};
</script>

<style scoped lang="stylus">
.school-detail
  min-height calc( 100vh - 60px )
  background-color bgColor
  >.school-detail-title
    text-align: center;
    font-size: 20px;
    margin 30px 0
    display flex
    justify-content center
    >span
      display: inline-block;
      max-width: 80%;
      text-align: left;
  >.school-detail-content
    padding 20px
    line-height: 30px
    >>>img
        max-width 100%
</style>
