<template>
  <div class="message" :style="{backgroundColor: list.length ? '#f2f2f2' : '#fff'}">
    <van-sticky :offset-top="0">
      <h5-header :leftStatus="true" title="学校宣传"></h5-header>
    </van-sticky>

    <div :style="{padding:list.length ? '20px' : '0px'}">
      <van-pull-refresh v-model="refreshing" @refresh="onRefresh" :style="{backgroundColor: list.length ? '' : '#fff'}">
        <van-list
          v-model="loading"
          :finished="finished"
          :finished-text="list.length ? '没有更多了' : ''"
          @load="onLoad"
          :immediate-check="false"
        >
          <div>
            <div v-for="(item, index) in list" :key="index">
              <div class="message-item">
                <div class="message-item-header">
                  <span class="message-item-header-title">{{
                    item.title
                  }}</span>
                </div>
                <div class="message-item-content">
                  {{ item.messageContent | escapeHTML }}
                </div>
                <div class="message-item-bottom" @click="seeNews(item.newsId)">
                  <span class="message-item-bottom-see">查看详情</span
                  ><van-icon class="message-item-bottom-right" name="arrow" />
                </div>
              </div>
            </div>
          </div>
        </van-list>
      </van-pull-refresh>
    </div>
    <h5FinishedShow v-show="isStatus"></h5FinishedShow>
  </div>
</template>

<script>
import { List, PullRefresh, Icon, Sticky } from "vant";
import Header from "@/components/cheader";
import { newsList } from "@/api/signList/schoolList/schoolList";
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
      list: [],
      show: false,
      loading: false,
      finished: false,
      refreshing: false,
      value1: 0,
      params: {
        pageNum: 1,
        pageSize: 10
      }
    };
  },
  filters: {
    escapeHTML(val) {
      return (val.replace(/<\/?.+?>/g,"")).replace(/&nbsp;/ig, "")
    }
  },
  methods: {
    //滚动触发,是否是滚动触发
    onLoad(status = true) {
      if (status) {
        this.params.pageNum += 1;
      }
      // 将 loading 设置为 true，表示处于加载状态
      this.loading = true;
      this.newsList();
    },
    //下拉刷新时
    onRefresh() {
      //不禁止下拉滚动
      this.finished = false;
      //开启下拉滚动效果
      this.refreshing = true;
      //将当前页码改为1
      this.params.pageNum = 1;
      this.onLoad(false);
    },
    //获取新闻列表
    newsList() {
      newsList(this.params).then(res => {
        //获取数据
        let data = res.data.records;
        //若是第一页，则空数组
        let list = res.data.current === 1 ? [] : this.list;
        //判断所有数据是否加载完成,如数据列表长度与当前页面数量大小不等，则所有数据加载完成。
        this.finished = data.length !== res.data.size;
        //合并
        this.list = [...list, ...data];
        this.isStatus = !this.list.length;
        //加载已完成
        this.loading = false;
        this.refreshing = false;
      });
    },
    //查看详情
    seeNews(id) {
      this.$router.push({
        path: "/signList/schoolDetailInfo",
        query:{
          id
        }
      });
    }
  },
  created() {
  },
  activated() {
    this.newsList();
  }
};
</script>

<style scoped lang="stylus">
.message
    font-size fontMedSize
    min-height: 100vh;
    .message-item
        padding-top: 8px;
        border-radius: 5px;
        margin-bottom: 20px;
        background-color: #fff
        .message-item-header
            margin-bottom: 7px;
            padding: 10px 16px;
            display: flex;
            justify-content: space-between;
            align-items: center
            .message-item-header-title
                color fontTitleColor
                font-size fontBigSize
                font-weight bold
                align-items: center;
                white-space: nowrap;
                text-overflow: ellipsis;
                overflow: hidden;
                word-break: break-all;
                height: 24px;
                line-height: 24px;
            .message-item-header-time
                color fontTextColor
                font-size fontMedSize
        .message-item-content
            padding-left: 16px
            padding-right: 10px
            color fontTextColor
            font-size fontMedSize
            margin-bottom: 15px
            white-space: nowrap
            text-overflow: ellipsis
            overflow: hidden
            word-break: break-all
        .message-item-bottom
            display: flex;
            padding: 16px 16px;
            justify-content: space-between;
            border-top: 1px solid #eee;
            align-items: center
            .message-item-bottom-see
                color fontTextColor
                font-size fontMedSize
</style>
