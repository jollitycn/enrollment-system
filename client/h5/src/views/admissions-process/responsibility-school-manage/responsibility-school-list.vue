<template>
  <div class="responsibility-school-manage">
    <div class="responsibility-school-manage-header">
      <van-sticky :offset-top="0">
        <!-- <h5-header
          :leftStatus="true"
          title="责任学校"
          rigText="新增学校"
          :isLeft="true"
          @clickHeadLeft="clickHeadLeft"
          @clickHeadAction="navToAddSchool"
        ></h5-header>-->
        <h5-header :leftStatus="true" :isLeft="true" @clickHeadLeft="clickHeadLeft" title="责任学校"></h5-header>
      </van-sticky>
    </div>
    <div class="responsibility-school-manage-content">
      <van-list
        v-if="list&&list.length"
        class="school-list"
        v-model="loading"
        :finished="finished"
        finished-text="没有更多了"
        @load="onLoad"
      >
        <school-list-item
          v-for="(item,index) in list"
          :key="item.sourceSchoolId"
          :info="item"
          :index="index+1"
          @click.native="navToSchoolDetail(item.sourceSchoolId)"
        />
      </van-list>
      <custom-empty v-else :type="1" height="calc(100vh - 200px)" />
    </div>
    <div class="responsibility-school-manage-footer"></div>
  </div>
</template>

<script>
import Header from "@/components/cheader";
import CustomEmpty from "@/components/custom-empty";
import Vue from "vue";
import SchoolListItem from "./components/school-list-item";

import { Sticky, Tab, Tabs, List, Calendar, Icon } from "vant";
Vue.use(Sticky);
Vue.use(Tabs);
Vue.use(Tab);
Vue.use(List);
Vue.use(Calendar);
Vue.use(Icon);

import { listCompleteRate } from "../../../api/sourceSchool/sourceSchool";
export default {
  name: "responsibility-school-manage",
  components: {
    "h5-header": Header,
    "school-list-item": SchoolListItem,
    "custom-empty": CustomEmpty
  },
  data() {
    return {
      // 列表
      list: [],
      loading: false,
      finished: false
    };
  },
  methods: {
    /**
     * 完成度统计
     * @author cwz
     */
    listCompleteRate() {
      this.$message.loading("正在加载中");
      listCompleteRate()
        .then(res => {
          this.$message.close();
          this.list = res.data;
          // 加载状态结束
          this.loading = false;
          // 数据全部加载完成
          if (this.list.length >= res.data.length) {
            this.finished = true;
          }
        })
        .catch(err => {
          this.$message.close();
          console.error(err);
        });
    },
    onLoad() {
      this.listCompleteRate();
      // // 异步更新数据
      // // setTimeout 仅做示例，真实场景中一般为 ajax 请求
      // setTimeout(() => {
      //   for (let i = 0; i < 2; i++) {
      //     this.list.push({id:this.list.length + 1});
      //   }
      //   // 加载状态结束
      //   this.loading = false;
      //   // 数据全部加载完成
      //   if (this.list.length >= 5) {
      //     this.finished = true;
      //   }
      // }, 1000);
    },
    /**
     * 返回招生过程管理
     */
    clickHeadLeft() {
      if (this.$route.params.returnRouter) {
        this.$router.push({
          path: this.$route.params.returnRouter
        });
      } else {
        this.$router.push({
          path: `/admissions-process`
        });
      }
    },
    /**
     * 新增学校页面跳转
     */
    navToAddSchool() {
      this.$router.push({
        path:
          "/admissions-process/responsibility-school-manage/responsibility-school-add"
      });
    },
    /**
     * 学校信息维护页面跳转
     */
    navToSchoolDetail(sourceSchoolId) {
      this.$router.push({
        path: `/admissions-process/responsibility-school-manage/responsibility-school-detail/${sourceSchoolId}`
      });
    }
  },
  created() {
    this.onLoad();
    // this.listCompleteRate();
  }
};
</script>

<style scoped lang="stylus">
.responsibility-school-manage {
  min-height: 100vh;

  // &-header{

  // }
  &-content {
    .school-list {
      background-color: #FFF;
    }
  }
}

>>>.van-list__finished-text {
  background-color: #F2F2F2;
}
</style>
