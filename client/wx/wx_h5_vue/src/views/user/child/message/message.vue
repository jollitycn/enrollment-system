<template>
  <div class="message"  :style="{ backgroundColor: list.length ? '#f5f5f5' : '#fff' }">
    <van-sticky :offset-top="0">
      <h5-header :leftStatus="true" title="消息中心"></h5-header>
    </van-sticky>
    <div
      :style="{
        padding: list.length ? '20px' : '0px'
      }"
    >
      <van-pull-refresh
        v-model="refreshing"
        @refresh="onRefresh"
        :style="{ backgroundColor: list.length ? '' : '#fff' }"
      >
        <van-list>
          <div>
            <div v-for="(item, index) in list" :key="index">
              <div class="message-item">
                <div class="message-item-header">
                  <span class="message-item-header-title">
                    <img :src="item.messageType ? imgSrc : imgSrcBm" alt="" />
                    {{item.messageType ? '系统消息' : '学校新闻'}}</span
                  >
                  <span class="message-item-header-time">{{
                    item.createTime
                  }}</span>
                </div>
                <div class="message-item-content">{{ item.title }}</div>
                <div class="message-item-bottom"  @click="see(item.messageType, item.messageType ? item.taskReceiverId : item.newsId)">
                  <span
                    class="message-item-bottom-see"
                    >查看详情</span
                  ><van-icon class="message-item-bottom-right" name="arrow" />
                </div>
              </div>
            </div>
          </div>
        </van-list>
      </van-pull-refresh>
    </div>
    <div v-show="list.length" class="message-total">没有更多了</div>
    <h5FinishedShow v-show="isStatus"></h5FinishedShow>
  </div>
</template>

<script>
import { List, PullRefresh, Icon, Sticky } from "vant";
import Header from "@/components/cheader";
import { getMessageList } from "@/api/user/user";
import FinishedShow from "@/components/finishedShow/finishedShow";
export default {
  name: "message",
  components: {
    vanList: List,
    vanPullRefresh: PullRefresh,
    vanIcon: Icon,
    vanSticky: Sticky,
    h5Header: Header,
    h5FinishedShow: FinishedShow
  },
  data() {
    return {
      isStatus: false,
      imgSrc: require("../img/7.png"),
      imgSrcBm: require("../img/10.png"),
      list: [],
      refreshing: false
    };
  },
  methods: {
    getMessageList() {
      getMessageList().then(res => {
        console.log(res);
        this.list = res.data;
        this.isStatus = !this.list.length;
        this.refreshing = false;
      });
    },
    onRefresh() {
      this.getMessageList();
    },
    //查看详情
    see(type, id) {
      console.log(type, id);
      if (type === 0) {
        this.$router.push({
          path: "/signList/schoolDetailInfo",
          query: {
            id
          }
        });
      } else if (type === 1) {
        this.$router.push({
          path: "/user/messageInfo",
          query: {
            id
          }
        });
      }
    }
  },
  created() {
    this.getMessageList();
  }
};
</script>

<style scoped lang="stylus">
.message
    font-size fontMedSize
    min-height: 100vh;
    .message-item
        border: 1px solid #eee;
        border-radius: 5px;
        margin-bottom: 25px;
        background-color: #fff
        .message-item-header
            padding: 16px 16px 8px 12px;
            display: flex;
            justify-content: space-between;
            align-items: center
            .message-item-header-title
                color fontTitleColor
                font-size fontBigSize
                font-weight bold
                display: flex;
                align-items: center;
                >img
                    width 20px
                    height 20px
                    margin-right: 10px;
            .message-item-header-time
                color fontTextColor
                font-size fontMedSize
        .message-item-content
            padding-left 12px;
            color fontTextColor
            font-size fontMedSize
            margin-bottom: 18px;
            margin-top: 14px;
        .message-item-bottom
            display: flex;
            padding: 10px;
            justify-content: space-between;
            border-top: 1px solid #eee;
            padding-top: 13px;
            align-items: center
            .message-item-bottom-see
                color fontTextColor
                font-size fontMedSize
    .message-total
        text-align center
        color: #999;
</style>
