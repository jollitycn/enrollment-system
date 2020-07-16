<template>
  <div class="student-list-container">
    <van-sticky :offset-top="0">
      <h5-header :leftStatus="true" title="目标生管理" rigText="新增" @clickHeadAction="navToAdd"></h5-header>
    </van-sticky>
    <div class="student-list-content">
      <div class="custom-search">
        <van-search
          v-model="query.name"
          input-align="left"
          maxlength="20"
          clearable
          placeholder="请输入学生姓名"
          @search="queryPage"
          @input="onDebounceInput"
        ></van-search>
      </div>
      <!-- <div class="custom-search-box">
          <div class="custom-search-box-wrap" >
            <van-icon class="search-icon" name="search" />
            <input class="search-text" placeholder="请输入学生姓名" maxlength="20"/>
          </div>
      </div>-->
      <van-pull-refresh v-model="isLoading" @refresh="onRefresh">
        <van-list
          class="student-list"
          v-model="loading"
          :finished="finished"
          finished-text="没有更多了"
          @load="onLoad"
          :immediate-check="false"
          v-if="list&&list.length"
        >
          <div
            class="student-list-item"
            v-for="(item,index) in list"
            :key="index"
            @click="navToEdit(item.aimStudentId)"
          >
            <div class="student-list-item-header">
              <!-- <div class="row-left">
              <img class="user-avatar" :src="item.imageUrl" alt />
              </div>-->
              <div class="row-middle">
                <div class="content-cell">
                  <span class="user-name">{{item.studentName}}</span>&emsp;&emsp;
                  <!-- <span class="user-sex">{{gender[item.gender]}}</span> -->
                </div>
                <div class="row-left content-cell">{{item.studentTelephone}}</div>
                <!-- <div class="content-cell">{{item.className}}</div> -->
              </div>
              <div class="row-right">
                <div
                  class="stu-level"
                  :class="{'hight':item.level==0,'middle':item.level==1,'low':item.level==2}"
                >跟进等级：{{levelEnum[item.level]}}</div>
                <!-- <div
                  class="stu-status"
                  :class="{'status-zero':item.status==0,'status-one':item.status==1}"
                >{{statusEnum[item.status]}}</div> -->
              </div>
            </div>
          </div>
        </van-list>
        <custom-empty v-else :type="1" height="calc(100vh - 200px)" />
      </van-pull-refresh>
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import CustomEmpty from "@/components/custom-empty";
import Header from "@/components/cheader";
import { queryStudentByPage } from "@/api/student/student-target-manage";
import {debounce} from "@/utils/index";
import { Sticky, Search, Col, Row, List, Icon, PullRefresh } from "vant";
Vue.use(Sticky);
Vue.use(Search);
Vue.use(Col);
Vue.use(Row);
Vue.use(List);
Vue.use(Icon);
Vue.use(PullRefresh);

export default {
  name: "student-list",
  components: {
    "h5-header": Header,
    "custom-empty":CustomEmpty
  },
  data() {
    return {
      onDebounceInput:null,
      isLoading: false,
      query: {
        pageNum: 1,
        pageSize: 10,
        classId: "",
        name: ""
      },
      list: [],
      loading: false,
      finished: false,
      total: 10,
      statusEnum: ["已报名", "跟进中"],
      levelEnum: ["高", "中", "低"]
    };
  },
  methods: {
    navToAdd() {
      this.$router.push({ name: "student-target-add" });
    },
    /**
     * 上拉滚动分页加载目标生列表
     * 初始化默认加载一次，以后滚动到底后触发加载
     */
    onLoad() {
      let queryParam = { ...this.query };
      queryParam.pageNum++;
      this.loading = true;
      this.queryStudentByPage(queryParam, data => {
        this.loading = false;
        if (data.current >= data.pages) {
          this.finished = true;
        }
      });
    },
    /**
     * 下拉加载最新数据
     */
    onRefresh() {
      this.query.name = "";
      this.query.pageNum = 1;
      this.list = [];
      this.isLoading = true;
      this.queryStudentByPage({ ...this.query }, () => {
        this.isLoading = false;
      });
    },
    /**
     * 初始化分页查询
     */
    queryPage() {
      this.query.pageNum = 1;
      this.list = [];
      this.queryStudentByPage({ ...this.query });
    },
    /**
     * 分页获取目标生列表
     * @author lmh
     */
    queryStudentByPage(params, callback) {
      this.$message.loading("正在加载...");
      queryStudentByPage(params)
        .then(res => {
          this.$message.close();
          this.list = this.list.concat(res.data.records);
          callback && callback(res.data);
          console.log("查询数据:", res.data);
        })
        .catch(err => {
          console.error("分页获取目标生列表失败：", err);
        });
    },
    navToSearch() {
      // const name = this.name;
      this.$router.push({
        path: `/student-target-manage/student-target-search`
      });
    },
    navToEdit(id) {
      this.$router.push({
        path: `/student-target-manage/student-target-edit/${id}`
      });
    }
  },
  created() {
    // this.queryPage();
    this.queryStudentByPage({ ...this.query });
    //输入查询防抖处理
    this.onDebounceInput = debounce(this.queryPage,1000);
  },
  mounted() {}
};
</script>

<style scoped lang="stylus">
.student-list-container {
  .student-list-content {
    background-color: #fff;

    .select-active {
      .van-dropdown-menu__title {
        color: #00CCFF;
      }
    }

    .custom-search {
      padding: 0 16px;
      // border-top: 1px solid #DDD;
      // border-bottom: 1px solid #DDD;
    }

    // >>>.van-field__left-icon{
    // position: relative;
    // left: calc(50vw - 80px);
    // font-size: 14px;
    // }
    .custom-search-box {
      padding: 10px 0;

      &-wrap {
        margin: 0 16px;
        text-align: center;
        background-color: #F7F7F7;
        height: 35px;
        line-height: 35px;
        border-radius: 4px;

        .search-icon {
          font-size: 15px;
        }

        .search-text {
          font-size: 15px;
          padding-left: 8px;
          color: #999999;
        }
      }
    }

    .student-list {
      padding-bottom: 40px;

      &-item {
        // &:first-child {
        // border-top: 1px solid #DDD;
        // }
        border-bottom: 1px solid #DDD;
        // background: #fff;
        // border-radius: 8px;
        padding: 16px;
        // padding: 12px 16px;
        font-size: 15px;

        &-header {
          display: flex;

          // margin-top: 12px;
          .user-name {
            font-weight: bold;
            font-size: 17px;
          }

          .row-left {
            color: #999999;
            width: 90px;
          }

          .row-middle {
            margin: 0 12px;
            flex: 1;
            color: #666666;
          }

          .row-right {
            // width: 90px;
            display: flex;
            margin: 0 12px;
          }

          .content-cell {
            height: 23px;
            line-height: 23px;
          }

          .stu-level {
            text-align: center;
            font-size: 12px;
            width: 90px;
            border: 1px solid;
            height: 23px;
            line-height: 23px;
            border-radius: 4px;

            &.hight {
              color: #FF4C4C;
            }

            &.middle {
              color: #FF9638;
            }

            &.low {
              color: #00CCFF;
            }
          }

          .stu-status {
            font-size: 12px;
            margin-left: 16px;
            width: 36px;
            text-align: center;
            height: 23px;
            line-height: 23px;
            border-radius: 4px;

            &.status-zero {
              color: #999999;
              background-color: #F2F2F2;
            }

            &.status-one {
              color: #FE9902;
              background-color: #FFF4EB;
            }
          }
        }

        &-middle {
          display: flex;
          align-items: center;
          justify-content: flex-start;
          margin-top: 12px;
        }

        &-footer {
          display: flex;
          margin-top: 12px;
        }

        .replace-btn {
          color: #00CCFF;
          height: 23px;
          line-height: 23px;
          text-align: right;

          &>span, &>i {
            vertical-align: middle;
          }
        }
      }
    }
  }
}
</style>
