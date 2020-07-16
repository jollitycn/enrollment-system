<template>
  <div class="student">
    <h5-header title="生源管理" :isLeft="true"></h5-header>
    <div>
      <van-dropdown-menu>
        <van-dropdown-item
          v-model="planQuery.semesterId"
          :options="planOption"
          @change="changeSemester"
        />
      </van-dropdown-menu>
    </div>
    <div class="student-content">
      <div class="student-content">
        <div
          class="student-content-list"
          v-for="item in list"
          :key="item.title"
          @click="navTo(item.router)"
        >
          <div class="student-content-list-left">
            <img width="22px" height="22px" :src="item.img" alt />
          </div>
          <div class="student-content-list-right">{{ item.title }}</div>
          <div class="row-right">
            <div class="stu-status">
              <!-- <van-icon size="small" slot="right-icon" name="arrow" /> -->
              <img
                style="width:15px;height:24px;line-height: 24px; margin-top:15px;margin-right:8px;"
                src="@/assets/images/right.png"
                name="arrow-left"
              />
            </div>
          </div>
        </div>
      </div>
    </div>
    <h5Tabbar></h5Tabbar>
  </div>
</template>

<script>
import Vue from "vue";
import Header from "@/components/cheader";
import Tabbar from "@/components/ctabbar";
import { queryAllPublishPlanAPP, getPlan } from "@/api/analysis/analysis";
import { Icon, DropdownMenu, DropdownItem } from "vant";
Vue.use(DropdownMenu);
Vue.use(DropdownItem);
Vue.use(Icon);
export default {
  name: "student",
  components: {
    h5Header: Header,
    h5Tabbar: Tabbar
  },
  data() {
    return {
      planOption: [],
      planQuery: {
        semesterId: ""
      },
      list: [
        {
          title: "学生管理",
          img: require("./img/student-manage.png"),
          router: "/student-manage/student-list"
        },
        {
          title: "意向生报名",
          img: require("./img/student-sign.png"),
          router: "/student-manage/student-add"
        },
        {
          title: "目标生管理",
          img: require("./img/student-target-manage.png"),
          router: "/student-target-manage/student-target-list"
        }
      ]
    };
  },
  created() {
    this.queryAllPublishPlanAPP();
  },
  methods: {
    /**
     * 导航跳转页面
     * @author lmh
     */
    navTo(path) {
      path && this.$router.push({ path });
    },
    /**
     * 获取所有已发布计划信息
     * @author lgh
     */
    queryAllPublishPlanAPP() {
      queryAllPublishPlanAPP().then(res => {
        let planInfo = JSON.parse(localStorage.getItem("nowPlanInfo"));
        if (planInfo) {
          this.planQuery.groupId = planInfo.groupId;
          this.planQuery.semesterId = planInfo.value;
        } else {
          this.planQuery.groupId = res.data[0].groupId;
          this.planQuery.semesterId = res.data[0].semesterId;
        }
        res.data = JSON.parse(
          JSON.stringify(res.data).replace(/title/g, "text")
        );
        res.data = JSON.parse(
          JSON.stringify(res.data).replace(/semesterId/g, "value")
        );
        this.planOption = res.data;
      });
    },
    /**
     * 切换默认计划时，刷新全局的默认计划
     * @param {number}value 绑定的计划下拉框绑定的默认值
     * @author lgh
     */
    changeSemester(value) {
      var obj = this.planOption.find(function(x) {
        return x.value == value;
      });
      this.getPlan(obj);
    },
    //APP切换计划
    getPlan(obj) {
      this.$message.loading("正在切换计划...");
      getPlan(obj.planId)
        .then(res => {
          localStorage.setItem("ACCESS_TOKEN", res.data.token);
          localStorage.setItem("nowPlanInfo", JSON.stringify(obj));
          this.$message.close();
          this.$message.success("切换成功！");
        })
        .catch(err => {
          console.error(err);
          this.$message.error("切换计划失败！");
        });
    }
  }
};
</script>

<style scoped lang="stylus">
.student {
  min-height: calc(100vh - 1.33333rem);

  .student-content {
    background-color: #F2F2F2;

    .student-content {
      .student-content-list {
        background-color: bgColor;
        display: flex;
        align-items: center;
        height: 55px;
        width: 100%;
        position: relative;

        &:after {
          position: absolute;
          box-sizing: border-box;
          content: ' ';
          pointer-events: none;
          right: 0;
          bottom: 0;
          left: 0;
          border-bottom: 0.02667rem solid #ebedf0;
          -webkit-transform: scaleY(0.5);
          transform: scaleY(0.5);
        }

        &:first-child {
          // border-top: 1px solid #DEDEDE;
        }

        .row-right {
          width: 10px;
          margin: 0 12px;

          .stu-status {
            height: 55px;
            line-height: 55px;
            text-align: right;
            color: #999999;
          }
        }

        .student-content-list-left {
          margin: 0px 20px;
          border-radius: 8px;
          display: flex;
          align-items: center;
        }

        .student-content-list-right {
          flex: 1;
          color: #212832;
          height: 100%;
          display: flex;
          align-items: center;
        }
      }
    }
  }
}
</style>
