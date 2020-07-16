<template>
  <div class="message">
    <van-sticky @scroll="scrollHandle">
      <div :class="{fixed:titleIsFixed}" class="new-list-header-wrapper">
        <div class="new-list-header">我的任务</div>
        <div class="new-list-header-subtitle">
          <div class="lineSty mr7">———</div>
          <span class="mr10">处理任务</span>
          <span>高效招生</span>
          <div class="lineSty ml7">———</div>
        </div>
      </div>
    </van-sticky>
    <div class="message-content">
      <van-pull-refresh v-model="refreshing" @refresh="onRefresh">
        <!-- 下拉提示 -->
        <template #pulling>
          <span class="custom-warn">释放即可刷新...</span>
        </template>

        <!-- 释放提示 -->
        <template #loosing>
          <span class="custom-warn">释放即可刷新...</span>
        </template>

        <!-- 加载提示 -->
        <template #loading>
          <span class="custom-warn">正在加载中...</span>
        </template>

        <!-- 刷新成功提示 -->
        <template #success>
          <span class="custom-warn">加载完成</span>
        </template>

        <van-list v-model="loading" :finished="finished" @load="onLoad" :immediate-check="false">
          <!-- 加载提示 -->
          <template #loading>
            <span class="custom-warn">加载中...</span>
          </template>

          <!-- 加载完成提示 -->
          <template #finished>
            <div class="finished-warn-box">
              <span class="custom-warn">{{list.length ? '没有更多了' : '暂无数据'}}</span>
            </div>
          </template>
          <div class="new-list-item" v-for="(item, index) in list" :key="index">
            <div class="new-list-header-time">{{ item.createTime }}</div>
            <div class="message-item">
              <div class="message-item-header">
                <span class="message-item-header-title">
                  {{
                  tanksList[item.taskType]
                  }}
                </span>
                <span v-show="!item.readStatus" class="message-item-header-right"></span>
              </div>
              <div class="message-item-content">{{ item.content | escapeHTML }}</div>
              <van-divider :style="{margin: '0' }" />
              <div class="message-item-footer" @click="jumpPage(item)">
                <span class="message-item-footer-see">查看详情</span>
                <!-- <van-icon class="message-item-footer-right" name="arrow" /> -->
                <img
                  style="width:15px;height:24px;line-height: 24px; margin-top:11px;margin-right:8px;"
                  src="@/assets/images/right.png"
                  name="arrow-left"
                />
              </div>
            </div>
          </div>
        </van-list>
      </van-pull-refresh>
    </div>
    <h5Tabbar></h5Tabbar>
  </div>
</template>

<script>
import Vue from "vue";
import { Sticky, List, PullRefresh, Icon, Divider } from "vant";
Vue.use(Sticky);
Vue.use(List);
Vue.use(PullRefresh);
Vue.use(Icon);
Vue.use(Divider);

import Tabbar from "@/components/ctabbar";
import { getTaskList, jumpPage } from "../../api/task/task";
export default {
  name: "newList",
  components: {
    h5Tabbar: Tabbar
  },
  data() {
    return {
      titleIsFixed: false,
      list: [],
      tanksList: {
        0: "维护学校",
        1: "维护宣讲",
        2: "宣讲任务",
        3: "诉求",
        4: "咨询"
      },
      show: false,
      loading: false,
      finished: false,
      refreshing: false,
      value1: 0,
      params: {
        pageNum: 1,
        pageSize: 10,
        status: 0
      }
    };
  },
  filters: {
    escapeHTML(val) {
      return val;
    }
  },
  methods: {
    /**
     * 头部菜单滚动后处理
     * @param val { scrollTop: 距离顶部位置, isFixed: 是否吸顶 }
     */
    scrollHandle(val) {
      // console.error("滚动情况：", val);
      this.titleIsFixed = val.isFixed;
    },
    //滚动触发,是否是滚动触发
    onLoad(status = true) {
      if (status) {
        this.params.pageNum += 1;
      }
      // 将 loading 设置为 true，表示处于加载状态
      this.loading = true;
      this.getTaskList();
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
    jumpPage(item) {
      console.log("item:", item);
      let status = item.readStatus;
      let id = item.taskReceiverId;
      let name = "",
        params = {};
      switch (item.taskType) {
        case 0:
          name = "responsibility-school-list";
          params.returnRouter = "/list";
          break;
        case 1:
          params.active = "1";
          params.status = "myPreach";
          name = "preaching-plan";
          params.tabIndex = 0;
          params.returnRouter = "/list";
          break;
        case 2:
          params.active = "2";
          params.status = "all";
          name = "preaching-plan";
          params.tabIndex = 1;
          params.returnRouter = "/list";
          break;
        case 3:
          name = "appeal-manage";
          params.returnRouter = "/list";
          break;
        case 4:
          name = "to-school-counseling-manage";
          params.returnRouter = "/list";
          break;
      }
      if (!status) {
        jumpPage(id).then(res => {
          console.log("跳转链接结果：", res);
          this.$router.push({
            name,
            params
          });
        });
      } else {
        this.$router.push({
          name,
          params
        });
      }
    },
    //获取新闻列表
    getTaskList() {
      getTaskList(this.params).then(res => {
        //获取数据
        let data = res.data.records;
        //若是第一页，则空数组
        let list = res.data.current === 1 ? [] : this.list;
        //判断所有数据是否加载完成,如数据列表长度与当前页面数量大小不等，则所有数据加载完成。
        this.finished = data.length !== res.data.size;
        //合并
        this.list = [...list, ...data];
        //加载已完成
        this.loading = false;
        this.refreshing = false;
      });
    }
  },
  created() {
    this.getTaskList();
  }
};
</script>

<style scoped lang="stylus">
>>>.van-pull-refresh {
  min-height: 73vh;
}

.message {
  font-size: fontMedSize;
  min-height: 100vh;
  background: url('~@/assets/images/my_bg.png') center center no-repeat;
  background-size: 100% 100%;
  overflow: hidden;
  overflow-y: auto;
  box-sizing: border-box;
  padding-top: 68px;

  .message-content {
    padding-bottom: 50px;
    box-sizing: border-box;

    .custom-warn {
      color: #fff;
      font-size: 12px;
    }

    .finished-warn-box {
      height: 100%;
      margin: 0 16px;
      // border-top: 1px solid #DDD;
    }
  }

  .new-list-header-wrapper {
    &.fixed {
      background-color: #2D5BCA;

      .new-list-header {
        padding-top: 44px;
        font-size: 30px;
        height: 42px;
        line-height: 42px;
      }

      .new-list-header-subtitle {
        display: none;
      }
    }

    .new-list-header {
      color: #fff;
      font-size: 30px;
      text-align: center;
      height: 42px;
      line-height: 42px;
      // margin-bottom: 10px;
    }

    .new-list-header-subtitle {
      color: #fff;
      font-family: PingFangSC-Regular, PingFang SC;
      font-size: 12px;
      text-align: center;
      font-weight: 400;
      height: 18px;
      line-height: 18px;
    }

    .lineSty {
      width: 40px;
      height: 20px;
      color: rgba(255, 255, 255, 0.6);
      // opacity: 0.5;
      transform: scaleY(0.5);
      position: absolute;
      top: 110px;
    }

    .mr10 {
      margin-right: 10px;
    }

    .mr7 {
      right: 246px;
    }

    .ml7 {
      left: 244px;
    }
  }

  .new-list-item {
    margin: 16px;

    .new-list-header-time {
      text-align: center;
      color: #fff;
      padding: 8px 0;
      font-size: 12px;
      line-height: 18px;
    }

    .message-item {
      border-radius: 6px;
      background-color: #fff;
      box-shadow: 0px 4px 8px 0px rgba(7, 7, 7, 0.06);
      position: relative;

      .message-item-header {
        padding: 0 16px;
        padding-top: 12px;
        display: flex;
        justify-content: space-between;
        align-items: center;

        .message-item-header-title {
          color: #333333;
          font-size: 16px;
          line-height: 24px;
          font-weight: bold;
          align-items: center;
          white-space: nowrap;
          text-overflow: ellipsis;
          // overflow: hidden;
          word-break: break-all;
        }

        .message-item-header-right {
          width: 10px;
          height: 10px;
          background-color: #FB4F4C;
          border-radius: 5px;
          box-shadow: 0px 4px 8px 0px rgba(7, 7, 7, 0.06);
          position: absolute;
          top: 7px;
          right: 7px;
        }

        .message-item-header-time {
          color: fontTextColor;
          font-size: fontMedSize;
        }
      }

      .message-item-content {
        color: fontTextColor;
        font-size: 14px;
        line-height: 21px;
        padding: 0 16px;
        padding-top: 8px;
        padding-bottom: 12px;
        white-space: nowrap;
        text-overflow: ellipsis;
        overflow: hidden;
        word-break: break-all;
      }

      .message-item-footer {
        display: flex;
        padding: 12px 16px;
        padding-top: 5px;
        padding-bottom: 5px;
        padding-right: 8px;
        justify-content: space-between;
        // border-top: 1px solid #DDD;
        align-items: center;

        .message-item-footer-see {
          color: #333;
          font-size: 14px;
          font-weight: 400;
          // font-family: PingFangSC-Regular, PingFang SC;
          height: 21px;
          line-height: 21px;
        }
      }
    }
  }
}
</style>
