<template>
  <div class="appeal-manage">
    <van-sticky :offset-top="0">
      <h5-header :leftStatus="true" title="诉求管理"></h5-header>
    </van-sticky>
    <div class="appeal-manage-content">
      <van-tabs
        v-model="active"
        color="#00CCFF"
        sticky
        offset-top="90"
        title-active-color="#00CCFF"
        @click="onClick"
      >
        <van-tab title="待处理诉求">
          <template v-if="list&&list.length">
            <appeal-list-item
              class="appeal-manage-content-list"
              v-for="(item,index) in list "
              :key="index"
              :info="item"
            />
          </template>
          <custom-empty v-else :type="1" height="calc(100vh - 118px)" />
        </van-tab>
        <van-tab title="已处理诉求">
          <template v-if="list&&list.length">
            <appeal-list-item
              class="appeal-manage-content-list"
              v-for="(item,index) in list "
              :key="index"
              :info="item"
              :status="true"
            />
          </template>
          <custom-empty v-else :type="1" height="calc(100vh - 118px)" />
        </van-tab>
      </van-tabs>
    </div>
  </div>
</template>

<script>
import Header from "@/components/cheader";
import CustomEmpty from "@/components/custom-empty";
import AppeallistItem from "./appeal-list-item";
import { queryRequestInfo } from "../../../api/appeal/appeal-management.js";
import Vue from "vue";
import bus from "@/components/bus";
import { Sticky, Tab, Tabs, List, Toast } from "vant";
Vue.use(Sticky);
Vue.use(Tabs);
Vue.use(Tab);
Vue.use(List);
Vue.use(Toast);

export default {
  name: "appeal-manage",
  components: {
    "h5-header": Header,
    "appeal-list-item": AppeallistItem,
    "custom-empty": CustomEmpty
  },

  data() {
    return {
      active: 0,
      searchQuery: {
        status: 1, //诉求处理状态(0已处理，1未处理)
        pageNum: 1,
        pageSize: 10
      },
      // 列表
      list: [],
      loading: false,
      finished: false
    };
  },
  methods: {
    onLoad() {
      let queryParam = { ...this.searchQuery };
      this.queryRequestInfo(queryParam);
    },
    /**
     * 分页查询学生的已处理和未处理的诉求信息
     * @param searchQuery{array} 获取该数据所需要的页码，内含pageNum当前页，pageSize每页大小,处理状态status
     * @author LiuGuoHao
     */
    queryRequestInfo(searchQuery) {
      this.$message.loading("正在加载...");
      queryRequestInfo(searchQuery).then(res => {
        /**
         * 分页后加载操作
         */
        // console.log(res,"==>Res")
        // this.searchQuery.pageSize = res.data.size;
        // this.total = res.data.total;
        // let data = res.data.current === 1 ? [] : this.list;
        // this.list = data.concat(res.data.records);
        // // 加载状态结束
        // this.loading = false;
        // // 数据全部加载完成
        // // this.finished = this.res.data.records.length === this.searchQuery.pageNum;
        // this.searchQuery.pageNum = this.searchQuery.pageNum + 1;
        // console.log(this.list.length != this.total,"==>result");
        // if (this.list.length >= this.total) {
        //   this.finished = true;
        // }
        this.$message.close();
        this.list = res.data;
      });
    },
    /**
     * tabs的点击事件
     */
    onClick(name) {
      if (name == 1) {
        this.searchQuery.status = 0;
        this.list = [];
        this.queryRequestInfo(this.searchQuery);
      } else {
        this.searchQuery.status = 1;
        this.list = [];
        this.queryRequestInfo(this.searchQuery);
      }
    },
    getDataAfterReply() {
      bus.$emit("getData", this.queryRequestInfo(this.searchQuery));
    }
  },
  created() {
    this.queryRequestInfo(this.searchQuery);

    //组件之间通信，回复操作成功之后刷新数据处理
    bus.$on("reply-completed", () => {
      //完成回复后处理
      this.queryRequestInfo(this.searchQuery);
    });
  }
};
</script>

<style scoped lang="stylus">
.appeal-manage {
  &-content {
    background-color: rgba(245, 245, 245, 1);
    min-height: calc(100vh - 100px);

    &-list {
    }

    // min-height: calc(100vh - 60px);
    .appeal-list {
    }
  }

  >>>[class*=van-hairline]::after {
    border: 0;
  }
}
</style>
