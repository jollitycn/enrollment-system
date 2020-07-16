<template>
  <div class="list">
    <h5-header title="我的招生任务"></h5-header>
    <div>
      <van-tabs v-model="active" color="#00CCFF" title-active-color="#00CCFF" sticky>
        <van-tab title="我的待办" @click="list">
          <div class="pedding">
            <van-swipe-cell v-for="(item, index) in tasklist" :key="index">
              <div class="pedding-list">
                <div class="pedding-list-item">
                  <img class="pedding-list-item-img" src="../../assets/images/1.png" alt />
                  <div class="pedding-list-item-content" @click="jumpPageHandle(item)">
                    <div class="pedding-list-item-content-top">
                      <span class="pedding-list-item-content-top-title">{{item.content}}</span>
                    </div>
                    <div class="pedding-list-item-content-text">通知时间：{{item.createTime}}</div>
                    <div class="pedding-list-item-content-text">发布人员：{{item.creatorName}}</div>
                    <div class="pedding-list-item-content-text">上报方式：{{reportType[item.reportType]}}</div>
                  </div>
                </div>
              </div>
              <template #right>
                <div class="hua">
                  <div class="hua-success">
                    <img src="../../assets/images/success.png" @click="closeTask(item)" alt />
                  </div>
                  <!-- <div class="hua-error">
                    <img src="../../assets/images/error.png" @click="" alt />
                  </div> -->
                </div>
              </template>
            </van-swipe-cell>
          </div>
        </van-tab>
        <van-tab title="已完成" @click="list1">
          <div class="pedding">
            <van-swipe-cell v-for="(item, index) in tasklist1" :key="index">
              <div class="pedding-list">
                <div class="pedding-list-item">
                  <img class="pedding-list-item-img" src="../../assets/images/2.png" alt />
                  <div class="pedding-list-item-content" @click="jumpPageHandle(item)">
                    <div class="pedding-list-item-content-top">
                      <span class="pedding-list-item-content-top-title">{{item.content}}</span>
                    </div>
                    <div class="pedding-list-item-content-text">通知时间：{{item.createTime}}</div>
                    <div class="pedding-list-item-content-text">发布人员：{{item.creatorName}}</div>
                    <div class="pedding-list-item-content-text">上报方式：{{reportType[item.reportType]}}</div>
                  </div>
                </div>
              </div>
              <template #right>
                <div class="hua">
                  <div class="hua-success">
                    <img src="../../assets/images/success.png" alt />
                  </div>
                  <div class="hua-error">
                    <img src="../../assets/images/error.png" alt />
                  </div>
                </div>
              </template>
            </van-swipe-cell>
          </div>
        </van-tab>
      </van-tabs>
    </div>
    <h5Tabbar></h5Tabbar>
  </div>
</template>

<script>
import Header from "@/components/cheader";
import Tabbar from "@/components/ctabbar";
import Vue from "vue";
import { Tab, Tabs, SwipeCell, Dialog } from "vant";
Vue.use(Tabs);
Vue.use(Tab);
Vue.use(SwipeCell);
Vue.use(Dialog);

import {
  getTaskList,
  jumpPage,
  closeTask
} from "../../api/task/task";
export default {
  name: "list",
  components: {
    h5Header: Header,
    h5Tabbar: Tabbar
  },
  data() {
    return {
      reportType:['系统上报','用户填报'],
      active: 2,
      form: {},
      tasklist: [],
      tasklist1: []
    };
  },
  created() {
    //未完成列表
    this.list();
    //完成列表
    this.list1();
  },
  methods: {
    /**
     * 获取已完成任务列表
     * @param {object} data
     * status 状态（1、已完成）
     * @author cwz
     */
    list1() {
      let data = {
        pageNum: 1,
        pageSize: 10,
        status: 1
      };
      getTaskList(data).then(res => {
        this.tasklist1 = res.data.records;
      });
    },
    /**
     * 获取待完成任务列表
     * @param {object} data
     * status 状态（0、未完成）
     * @author cwz
     */
    list() {
      let data = {
        pageNum: 1,
        pageSize: 10,
        status: 0
      };
      getTaskList(data).then(res => {
        this.tasklist = res.data.records;
      });
    },
    /**
     * 关闭任务
     * @param  {Object} item 要处理的任务信息数据
     * @author cwz
     */
    closeTask(item) {
      let id = item.taskReceiverId;
      Dialog.confirm({
        title: "提示",
        message: "确认关闭任务吗？"
      })
        .then(() => {
          this.$message.loading("正在关闭中...");
          closeTask(id).then(() => {
            this.$message.close();
            this.$message.success(`关闭任务成功！`);
            this.list();
          });
        })
        .catch(err => {
          this.$message.close();
          console.error("关闭任务失败：", err);
        });
    },
    /**
     * 任务跳转对应页面
     * @param  {Object} item
     * item.taskReceiverId 任务接收信息ID
     *
     * item.taskType 跳转到相对应页面
     * 0  维护学校
     * 1  维护宣讲
     * 2  宣讲任务
     * 3  诉求
     * 4  咨询
     * @author cwz
     */
    jumpPageHandle(item) {
      console.log(1, item);
      let id = item.taskReceiverId;
      let name = "",
        params = {};
      switch (item.taskType) {
        case 0:
          name = "responsibility-school-list";
          break;
        case 1:
          name = "preaching-plan";
          params.tabIndex = 0;
          break;
        case 2:
          name = "preaching-plan";
          params.tabIndex = 1;
          break;
        case 3:
          name = "appeal-manage";
          break;
        case 4:
          name = "to-school-counseling-manage";
          break;
      }
      this.$router.push({
        name,
        params
      });
      this.jumpPage(id);
    },
    /**
     * 跳转路由标记已读
     * @param  {string} id 任务接收信息ID
     * @author cwz
     */
    jumpPage(id) {
      console.log("id:", id);
      jumpPage(id).then(res => {
        console.log("跳转链接结果：", res);
      });
    }
  }
};
</script>

<style scoped lang="stylus">
.list {
  font-size: fontMedSize;
  .pedding {
    margin-bottom: 1.33333rem;

    .pedding-list {
      .pedding-list-item {
        display: flex;
        padding: 20px;
        border-bottom: 1px solid #eee;
        background-color: bgColor;

        .pedding-list-item-img {
          width: 70px;
          height: 70px;
          margin-right: 15px;
        }

        .pedding-list-item-content {
          flex: 1;
          display: flex;
          flex-direction: column;
          justify-content: space-around;

          .pedding-list-item-content-text {
            overflow: hidden;
            text-overflow: ellipsis;
            display: box;
            display: -webkit-box;
            -webkit-line-clamp: 1;
            -webkit-box-orient: vertical;
            height: 20px;
            line-height: 20px;
            color: fontMiniFontColor;
          }

          .pedding-list-item-content-top {
            display: flex;
            justify-content: space-between;

            .pedding-list-item-content-top-title {
              color: fontTitleColor;
            }

            .pedding-list-item-content-top-time {
              color: fontMiniFontColor;
            }
          }
        }
      }
    }
  }

  .hua {
    display: flex;
    height: 100%;

    >div {
      width: 80px;
      display: flex;
      justify-content: center;
      align-items: center;
      position: relative;
      left: 2px;

      >img {
        width: 40px;
        height: 40px;
      }
    }

    .hua-success {
      background-color: btnBgColor;
    }

    .hua-error {
      background-color: red;
    }
  }
}
</style>
