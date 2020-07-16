<template>
  <div class="home-list" :style="{backgroundColor: activeIndex1 === 2 ? '#f5f5f5':'#ffffff'}">
    <van-sticky>
      <h5-header :leftStatus="true" title="学校详情"></h5-header>
      <div class="home-list-title">
        <div @click=" activeIndex1 = index " :class="index === activeIndex1 ? 'activeColor': ''" v-for="(item,index) in list" :key="index">{{item}}</div>
      </div>
    </van-sticky>
    <div>
      <schoolDetail v-show="activeIndex1 === 0" />
      <recruitStudents v-show="activeIndex1 === 1" />
      <departmentIntroduction v-show="activeIndex1 === 2" />
    </div>
  </div>
</template>

<script>
import Header from "@/components/cheader";
import schoolDetail from "./schoolDetail";
import recruitStudents from "./recruitStudents";
import departmentIntroduction from "./departmentIntroduction";
import { Sticky } from "vant";
import { mapState } from "vuex"
export default {
  name: "homeList",
  components: {
    h5Header: Header,
    schoolDetail: schoolDetail,
    recruitStudents: recruitStudents,
    departmentIntroduction,
    vanSticky: Sticky
  },
  computed: {
    ...mapState(["activeIndex"])
  },
  data() {
    return {
      activeIndex1: 0,
      list: ["学校简介", "招生简介", "院系简介"]
    };
  },
  created() {
    this.activeIndex1 = this.activeIndex;
  }
};
</script>

<style scoped lang="stylus">
.home-list
  .home-list-title
    display flex
    height: 44px;
    align-items: center;
    background-color: #fff;
    >div
      flex 1
      text-align center
      height: 40px;
      border-bottom 4px solid #fff
      line-height 40px
    >.activeColor
        color activeBottomColor
        border-bottom 4px solid activeBottomColor
</style>
