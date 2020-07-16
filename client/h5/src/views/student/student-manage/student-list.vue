<template>
  <div class="student-list-container">
    <van-sticky :offset-top="0">
      <h5-header :leftStatus="true" :isLeft="true" @clickHeadLeft="clickHeadLeft" title="学生管理"></h5-header>
    </van-sticky>
    <div class="student-list-content">
      <!-- <van-search
        v-model="query.searchKey"
        input-align="center"
        readonly
        maxlength="20"
        clearable
        placeholder="请输入学生姓名"
        @click="navToSearch"
      ></van-search>-->
      <div class="custom-search-box">
        <div class="custom-search-box-wrap" @click="navToSearch">
          <van-icon class="search-icon" name="search" />
          <span class="search-text">请输入学生姓名</span>
        </div>
      </div>
      <van-dropdown-menu active-color="#00CCFF">
        <van-dropdown-item
          v-model="searchQuery.sourceSchoolId"
          :options="option"
          @change="getStudentInfoPage"
        />
        <van-dropdown-item
          v-model="searchQuery.registerStatus"
          :options="statusOption"
          @change="getStudentInfoPage"
        />
      </van-dropdown-menu>
      <van-pull-refresh v-model="isLoading" @refresh="onRefresh">
        <van-list
          v-if="list.length!=0"
          class="student-list"
          v-model="loading"
          :finished="finished"
          finished-text="没有更多了"
          @load="onLoad"
          :immediate-check="false"
        >
          <div
            class="student-list-item"
            v-for="(item,index) in list"
            :key="index"
            @click="navToDetail(item.studentInfo)"
          >
            <div class="student-list-item-header">
              <div class="row-left">
                <img
                  v-if="item.imageUrl"
                  class="user-avatar"
                  :src="item.imageUrl | imgUrlFilter"
                  alt
                />
                <img
                  v-else
                  class="user-avatar"
                  :src="require(`../img/${item.gender?'woman':'man'}.png`)"
                  alt
                />
              </div>
              <div class="row-middle">
                <div class="content-cell">
                  <span class="user-name v-middle">{{spliceName(item.studentName)}}</span>
                  <span
                    :class="{'male':!item.gender,'female':!!item.gender}"
                    class="user-sex v-middle"
                  ></span>&emsp;&emsp;
                  <span class="user-phone v-middle gray">登记编号:{{item.studentNo}}</span>
                </div>
                <div
                  class="content-cell gray"
                >{{item.sourceSchoolName}}&emsp;|&emsp;{{item.className}}</div>
              </div>
              <div class="row-right">
                <span
                  class="stu-status"
                  :class="{'status-zero':item.registerStatus!=2,'status-one':item.registerStatus==2}"
                >{{registerStatus[item.registerStatus || 0]}}</span>
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
import Header from "@/components/cheader";
import CustomEmpty from "@/components/custom-empty";
import {
  listStudentInfoPage,
  listSourceSchoolPage
} from "../../../api/Student-info/Student-info.js";
import {
  Sticky,
  Search,
  DropdownMenu,
  DropdownItem,
  Col,
  Row,
  List,
  Icon,
  PullRefresh
} from "vant";
Vue.use(Sticky);
Vue.use(Search);
Vue.use(DropdownMenu);
Vue.use(DropdownItem);
Vue.use(Col);
Vue.use(Row);
Vue.use(List);
Vue.use(Icon);
Vue.use(PullRefresh);

export default {
  name: "student-list",
  components: {
    h5Header: Header,
    "custom-empty": CustomEmpty
  },
  data() {
    return {
      searchSchoolQuery: {
        pageNum: 1,
        pageSize: 100
      },
      searchQuery: {
        pageNum: 1,
        pageSize: 10,
        classId: "",
        sourceSchoolId: "",
        studentName: "",
        registerStatus: "" //报名状态（0：已交订金，1：已交全款，2：已退款，3：强意向生，4：已报名）
      },
      query: {
        searchKey: ""
      },
      option: [{ text: "全部学校", value: "" }],
      statusOption: [
        { text: "所有状态", value: "" },
        { text: "已交订金", value: 0 },
        { text: "已交全款", value: 1 },
        { text: "已退款", value: 2 },
        { text: "强意向生", value: 3 },
        { text: "已报名", value: 4 }
      ],
      registerStatus: ["已交订金", "已交全款", "已退款", "强意向生", "已报名"],
      gender: ["男", "女"],
      list: [],
      loading: false,
      finished: false,
      total: 10,
      isLoading: false
    };
  },
  methods: {
    //处理名称过长
    spliceName(val) {
      if (val.length > 3) {
        val = val.substring(0, 3) + "...";
      }
      return val;
    },
    onChange(picker, value, index) {
      this.$showToast(`当前值：${value}, 当前索引：${index}`);
    },
    onLoad() {
      this.loading = true;
      let queryParam = { ...this.searchQuery };
      this.listStudentInfoPage(queryParam, () => {
        // 加载状态结束
        this.loading = false;
        // 数据全部加载完成
        this.searchQuery.pageNum = this.searchQuery.pageNum + 1;
        if (this.list.length >= this.total) {
          this.finished = true;
        }
      });
    },
    /**
     * 分页获取生源学校信息列表
     * @author LiuGuoHao
     */
    listSourceSchoolPage() {
      listSourceSchoolPage(this.searchSchoolQuery).then(res => {
        this.option = this.option.concat(res.data);
        //使用正则表达式全局替换所需要的字段
        this.option = JSON.parse(
          JSON.stringify(this.option).replace(/sourceSchoolName/g, "text")
        );
        this.option = JSON.parse(
          JSON.stringify(this.option).replace(/sourceSchoolId/g, "value")
        );
      });
    },
    /**
     * 触发搜索条件后执行
     * @author LiuGuoHao
     */
    getStudentInfoPage() {
      this.searchQuery.pageNum = 1;
      this.list = [];
      this.finished = false;
      this.onLoad();
    },
    /**
     * 分页获取所有学生基本信息列表
     * @param queryParam{array} 传入的参数名称
     * @author LiuGuoHao
     */
    listStudentInfoPage(queryParam, callback) {
      this.$message.loading("正在加载...");
      listStudentInfoPage(queryParam).then(res => {
        this.$message.close();
        callback && callback(res);
        this.searchQuery.pageSize = res.data.size;
        this.total = res.data.total;
        let data = res.data.current === 1 ? [] : this.list;
        this.list = data.concat(res.data.records);
      });
    },
    /**
     * 返回生源管理页面
     * @author LGH
     */
    clickHeadLeft() {
      this.$router.push({
        path: `/student`
      });
    },
    navToSearch() {
      this.$router.push({ path: "/student-manage/student-search" });
    },
    navToDetail(id) {
      this.$router.push({ path: `/student-manage/student-detail/${id}` });
    },
    /**
     * 下拉加载最新数据
     */
    onRefresh() {
      this.searchQuery.pageNum = 1;
      this.list = [];
      this.isLoading = true;
      this.listStudentInfoPage({ ...this.searchQuery }, () => {
        this.isLoading = false;
      });
    }
  },
  created() {
    this.listSourceSchoolPage();
    this.getStudentInfoPage();
  }
};
</script>

<style scoped lang="stylus">
.van-pull-refresh {
  min-height: calc(70vh);
}

.student-list-container {
  min-height: calc(100vh);

  .student-list-content {
    background-color: #F5F5F5;

    .select-active {
      .van-dropdown-menu {
        position: relative;
        top: -2px;

        .van-dropdown-menu__title {
          color: #00CCFF;
        }
      }
    }

    .custom-search-box {
      // margin-bottom: 1px;
      padding: 10px 0;
      background-color: #fff;
      position: relative;
      z-index: 9;

      &-wrap {
        margin: 0 16px;
        text-align: center;
        background-color: #F7F7F7;
        height: 35px;
        line-height: 35px;
        border-radius: 4px;
        display: flex;
        justify-content: center;
        align-items: center;

        .search-icon {
          font-size: 15px;
        }

        .search-text {
          font-size: 15px;
          padding-left: 8px;
          color: #999999;
          width: 105px;
          height: 23px;
          line-height: 23px;
        }
      }
    }

    // 搜索框下的横线
    >>>.van-dropdown-menu__bar {
      box-shadow: 0px -1px 0px 0px rgba(0, 0, 0, 0.08);
      height: 37px;
    }

    .student-list {
      padding-bottom: 40px;

      &-item {
        // border-bottom: 1px solid #DDD;
        margin-bottom: 1px;
        background: #fff;
        // border-radius: 8px;
        padding: 16px;
        // padding: 12px 16px;
        font-size: 15px;

        &-header {
          display: flex;

          // margin-top: 12px;
          .user-sex {
            margin-left: 4px;
            margin-right: 12px;
            display: inline-block;
            width: 20px;
            height: 20px;
            padding: 0 4px;
            background: url('../img/male.png') center center no-repeat;

            &.male {
              background-image: url('../img/male.png');
            }

            &.female {
              background-image: url('../img/female.png');
            }
          }

          .v-middle {
            vertical-align: middle;
          }

          .gray {
            color: #999999;
          }

          .user-name {
            font-weight: bold;
            font-size: 17px;
            display: inline-block;
            vertical-align: top;
            // width: 1.8rem;
          }

          .row-left {
            color: #999999;
            width: 48px;

            .user-avatar {
              width: 48px;
              height: 48px;
              border-radius: 10px;
            }
          }

          .row-middle {
            margin-left: 12px;
            flex: 1;
            color: #666666;
          }

          .row-right {
            display: flex;
            justify-content: flex-end;
          }

          .content-cell {
            height: 23px;
            line-height: 23px;
          }

          .stu-status {
            font-size: 12px;
            margin-left: 16px;
            // width: 50px;
            text-align: center;
            height: 23px;
            line-height: 23px;
            border-radius: 4px;
            display: inline-block;
            padding: 0 8px;

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

>>>.cheader {
  border: none;
}
</style>
