<template>
  <div class="preaching-plan-signed-students">
    <van-sticky :offset-top="0">
      <h5-header :leftStatus="true" title="签到列表"></h5-header>
    </van-sticky>
    <div class="preaching-plan-signed-students-content">
      <!-- 签到统计 -->
      <div class="signed-stat">
        <div class="signed-stat-title">{{title}}</div>
        <div class="signed-stat-content">已签到：{{total}}人</div>
      </div>

      <!-- 学生列表 -->
      <van-list
        class="student-list"
        v-model="loading"
        :finished="finished"
        finished-text="没有更多了"
        @load="onLoad"
      >
        <div class="student-list-item" v-for="(item,index) in list" :key="index">
          <div class="student-list-item-header">
            <div class="row-left">
              <img v-if="item.imageUrl" class="user-avatar" :src="item.imageUrl | imgUrlFilter" alt />
              <img
                  v-else
                  class="user-avatar"
                  :src="require(`@/views/student/img/${item.gender?'woman':'man'}.png`)"
                  alt
                />
            </div>
            <div class="row-middle">
              <div class="content-cell">
                <span class="user-name">{{item.studentName}}</span>&emsp;&emsp;
                <!-- <span class="user-sex">{{gender[item.gender]}}</span> -->
              </div>
              <div class="content-cell">{{item.sourceSchoolName}}</div>
              <div class="content-cell">{{item.className}}</div>
            </div>
            <div class="row-right van-ellipsis">
              <div class="stu-status">登记编号&emsp;{{item.studentNo}}</div>
            </div>
          </div>
          <div class="student-list-item-middle">
            <div class="row-left">
              <div class="content-cell">家长</div>
              <div class="content-cell">联系方式</div>
            </div>
            <div class="row-middle">
              <!-- 家长存在时展示家长姓名，若不存在则展示XX家长 -->
              <div v-if="item.parentName" class="content-cell">{{item.parentName}}</div>
              <div v-else class="content-cell">{{item.studentName+'家长'}} </div>
              <div class="content-cell">{{item.parentTelephone || '无联系方式'}}</div>
            </div>
          </div>
        </div>
      </van-list>
    </div>
  </div>
</template>

<script>
import { queryPreachList } from "../../../api/admissions-process/preaching-plan";
import Vue from "vue";
import Header from "@/components/cheader";
// import { queryPreachList } from "../../../api/admissions-process/preaching-plan";
// import {
//   listStudentInfoPage,
//   listSourceSchoolPage
// } from "../../../api/Student-info/Student-info.js";
import {
  Sticky,
  Search,
  DropdownMenu,
  DropdownItem,
  Col,
  Row,
  List,
  Icon
} from "vant";
Vue.use(Sticky);
Vue.use(Search);
Vue.use(DropdownMenu);
Vue.use(DropdownItem);
Vue.use(Col);
Vue.use(Row);
Vue.use(List);
Vue.use(Icon);

export default {
  name: "preaching-plan-signed-students",
  components: {
    "h5-header": Header
  },
  data() {
    return {
      title:this.$route.params.title,
      searchSchoolQuery: {
        pageNum: 1,
        pageSize: 100
      },
      searchQuery: {
        pageNum: 1,
        pageSize: 10
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
        { text: "强意向生", value: 3 }
      ],
      registerStatus: ["已交订金", "已交全款", "已退款", "强意向生"],
      gender: ["男", "女"],
      list: [],
      loading: false,
      finished: false,
      total: 0
    };
  },
  methods: {
    onLoad() {
      let queryParam = { ...this.searchQuery };
      this.queryPreachList(queryParam);
    },
    /**
     * 分页获取生源学校信息列表
     * @author LiuGuoHao
     */
    // listSourceSchoolPage() {
    //   listSourceSchoolPage(this.searchSchoolQuery).then(res => {
    //     this.option = this.option.concat(res.data);
    //     //使用正则表达式全局替换所需要的字段
    //     this.option = JSON.parse(
    //       JSON.stringify(this.option).replace(/sourceSchoolName/g, "text")
    //     );
    //     this.option = JSON.parse(
    //       JSON.stringify(this.option).replace(/sourceSchoolId/g, "value")
    //     );
    //   });
    // },
    /**
     * 分页获取所有学生基本信息列表
     * @param queryParam{array} 传入的参数名称
     * @author LiuGuoHao
     */
    queryPreachList(queryParam) {
      queryParam.preachPlanId = this.$route.params.id
      queryPreachList(queryParam).then(res => {
        this.searchQuery.pageSize = res.data.size;
        this.total = res.data.total;
        // this.title = res.data.title;
        let data = res.data.current === 1 ? [] : this.list;
        this.list = data.concat(res.data.records);
        // 加载状态结束
        this.loading = false;
        // 数据全部加载完成
        // this.finished = this.res.data.records.length === this.searchQuery.pageNum;
        this.searchQuery.pageNum = this.searchQuery.pageNum + 1;
        if (this.list.length >= this.total) {
          this.finished = true;
        }
      });
    },
    navToSearch() {
      this.$router.push({ path: "/student/student-search" });
    }
  },
  created() {
    // this.listSourceSchoolPage();
    // this.onLoad();
    // this.listStudentInfoPage();
    // this.$showToast('测试！！！')
  }
};
</script>

<style scoped lang="stylus">
.preaching-plan-signed-students {
  min-height 100vh
  &-content {
    background-color: #F2F2F2;

    .signed-stat {
      padding: 16px;
      background: #fff;

      &-title {
        font-size: 21px;
        font-weight: 600;
        color: #333333;
        line-height: 32px;
      }

      &-content {
        margin-top: 10px;
        font-size: 15px;
        font-weight: 400;
        color: #999999;
        line-height: 23px;
      }
    }

    .student-list {
      padding-bottom: 40px;

      &-item {
        background: #fff;
        border-radius: 8px;
        margin: 16px;
        padding: 12px 16px;
        font-size: 15px;

        &-header {
          display: flex;
          margin-top: 12px;

          .user-avatar {
            width: 50px;
            height: 50px;
            border-radius: 38px;
          }

          .user-name {
            font-weight: bold;
            font-size: 17px;
          }
        }

        &-middle {
          display: flex;
          align-items: center;
          justify-content: flex-start;
          margin-top: 12px;

          .row-left {
            color: #999999;
          }
        }

        &-footer {
          display: flex;
          margin-top: 12px;
        }

        .row-left {
          width: 60px;
          margin: 0 8px;
        }

        .row-middle {
          margin: 0 8px;
          flex: 1;
          color: #666666;
        }

        .row-right {
          width: 90px;
          margin: 0 10px;
        }

        .content-cell {
          // height: 23px;
          line-height: 23px;
        }
      }
    }
  }
}
</style>
