<template>
  <div class="school-detail">
    <van-sticky :offset-top="0">
      <h5-header :leftStatus="true" :title="data.title"></h5-header>
    </van-sticky>
    <div class="school-detail-title">
      <span>{{data.title}}</span>
    </div>
    <div class="school-detail-content" v-html="resetHtml(data.messageContent)"></div>
  </div>
</template>

<script>
import { newDetail } from "@/api/signList/schoolList/schoolList";
import Header from "@/components/cheader";
import { Sticky } from "vant";
import { webImgDir } from "@/config/index";
export default {
  name: "schoolDetailInfo",
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
    newDetail(id) {
      newDetail(id).then(res => {
        console.log(res);
        this.data = res.data;
      });
    }
  },
  activated() {
    this.newDetail(this.$route.query.id);
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
