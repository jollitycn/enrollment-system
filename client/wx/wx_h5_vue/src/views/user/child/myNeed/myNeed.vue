<template>
  <div class="message">
    <van-sticky :offset-top="0">
      <h5-header
        :leftStatus="true"
        title="我的诉求"
      ></h5-header>
    </van-sticky>
    <div :style="{marginBottom: '65px' ,backgroundColor: list.length ? '#fff' : '#fff', padding:list.length ? '20px' : '0px'}">
      <van-pull-refresh v-model="refreshing" @refresh="onRefresh" :style="{backgroundColor: list.length ? '' : '#fff'}">
        <van-list
          v-model="loading"
          :finished="finished"
          :finished-text="list.length ? '没有更多了' : ''"
          @load="onLoad"
        >
          <div>
            <div v-for="(item, index) in list" :key="index">
              <div class="myNeed-item">
                <div class="myNeed-item-content">
                  <div>
                    <div class="myNeed-item-content-wen">问:</div>
                    <div class="myNeed-item-content-content">
                      {{ item.requestContent }}
                    </div>
                  </div>
                  <div>
                    <div class="myNeed-item-content-da">答:</div>
                    <div
                      class="myNeed-item-content-content"
                      :style="{ color: item.replyContent ? '' : '#999' }"
                    >
                      {{ item.replyContent || "老师正在回答的路上.." }}
                    </div>
                  </div>
                </div>
                <div class="myNeed-item-bottom">
                  <div>{{ item.replyName }}</div>
                  <div>{{ item.replyTime }}</div>
                </div>
              </div>
            </div>
          </div>
        </van-list>
      </van-pull-refresh>
    </div>
    <div class="btn-bottom">
      <van-button style="    height: 49px;
    font-size: 18px;" type="primary" color="#00CCFF" @click="clickHeadAction" block>新增诉求</van-button>
    </div>
    <h5FinishedShow v-show="isStatus"></h5FinishedShow>
  </div>
</template>

<script>
import { List, PullRefresh, Sticky, Button } from "vant";
import { queryRequestInfo } from "@/api/user/user";
import Header from "@/components/cheader";
import FinishedShow from "@/components/finishedShow/finishedShow";
export default {
  name: "myNeed",
  components: {
    vanList: List,
    vanPullRefresh: PullRefresh,
    vanSticky: Sticky,
    vanButton: Button,
    h5Header: Header,
    h5FinishedShow: FinishedShow
  },
  data() {
    return {
      isStatus: false,
      list: [],
      params: {},
      value: "深圳市奋达职业技术学校",
      show: false,
      loading: false,
      finished: false,
      refreshing: false
    };
  },
  methods: {
    //新增诉求
    clickHeadAction() {
      this.$router.push({
        path: "/user/addMyNeed"
      });
    },
    onLoad() {
      this.getQueryRequestInfo();
    },
    onRefresh() {
      // 清空列表数据
      this.finished = false;
      // 重新加载数据
      // 将 loading 设置为 true，表示处于加载状态
      this.loading = true;
      this.onLoad();
    },
    //获取诉求列表
    getQueryRequestInfo() {
      queryRequestInfo().then(res => {
        let data = [];
        //如果下拉刷新
        if (this.refreshing) {
          this.refreshing = false;
        } else  {
          data = this.list
        }
        this.list = [...data, ...res.data];
        this.isStatus = !this.list.length
        this.loading = false;
        //如果是最后一页，结束滚动加载,现在没做分页，直接禁止滚动加载
        this.finished = true;
        // if (this.list.length >= 40) {
        //   this.finished = true;
        // }
      });
    }
  },
  created() {}
};
</script>

<style scoped lang="stylus">
.message
    font-size fontMedSize
    .myNeed-item
        border-bottom: 1px solid #eee
        padding-top: 15px
        padding-bottom 25px
        margin-bottom 10px
        >.myNeed-item-content
            >div
                display flex
                color fontTitleColor
                margin-bottom: 15px;
                .myNeed-item-content-content
                    margin-left 15px
                    word-break: break-all;
                .myNeed-item-content-wen
                    color #FF4C4C
                .myNeed-item-content-da
                    color #3567E6
        .myNeed-item-bottom
            display flex
            justify-content space-between
            color fontMiniFontColor
    .btn-bottom
      position: fixed;
      bottom: 0rem;
      width: 100%;
      text-align: center;
      height: 49px;
      z-index: 999;
      background-color: #fff;
      >>>.van-button__text
            position relative
            top 1px
</style>
