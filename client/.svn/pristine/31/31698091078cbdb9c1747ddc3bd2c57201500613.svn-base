<template>
  <div class="sign">
    <h5-header title="招生过程" :isLeft="true"></h5-header>
    <div>
      <van-dropdown-menu>
        <van-dropdown-item
          v-model="planQuery.semesterId"
          :options="planOption"
          @change="changeSemester"
        />
      </van-dropdown-menu>
    </div>
    <div class="sign-content">
      <div class="sign-content-header">招生过程</div>
      <van-grid :column-num="3" icon-size="30px">
        <!-- <van-grid-item
          v-for="(item, index) in list"
          :key="index"
          :icon="item.img"
          :text="item.title"
          @click="go(item.router)"
        />-->
        <van-grid-item :icon="list[0].img" :text="list[0].title" @click="go(list[0].router)" />
        <van-grid-item :icon="list[1].img" :text="list[1].title" @click="go(list[1].router)" />
        <van-grid-item
          :icon="list[2].img"
          :text="list[2].title"
          @click="go(list[2].router)"
          class="borBot-right"
        />
        <van-grid-item
          :icon="list[3].img"
          :text="list[3].title"
          @click="go(list[3].router)"
          class="borBot-bottom"
        />
        <van-grid-item
          :icon="list[4].img"
          :text="list[4].title"
          @click="go(list[4].router)"
          class="borBot-bottom"
        />
        <van-grid-item
          :icon="list[5].img"
          :text="list[5].title"
          @click="go(list[5].router)"
          class="borBot-bottom borBot-right"
        />
      </van-grid>
    </div>
    <h5Tabbar></h5Tabbar>
  </div>
</template>

<script>
import Header from "@/components/cheader";
import Tabbar from "@/components/ctabbar";
import { getConsultationId } from "../../api/admissions-process/to-school-counseling-manage";
import {
  queryAllPublishPlanAPP,
  getPlan,
  generateQrCode
} from "@/api/analysis/analysis";
import Vue from "vue";
import { Grid, GridItem, DropdownMenu, DropdownItem } from "vant";
Vue.use(DropdownMenu);
Vue.use(DropdownItem);
export default {
  name: "signList",
  components: {
    h5Header: Header,
    h5Tabbar: Tabbar,
    vanGrid: Grid,
    vanGridItem: GridItem
  },
  data() {
    return {
      planOption: [],
      planQuery: {
        semesterId: ""
      },
      list: [
        {
          title: "责任学校",
          img: require("./img/0.png"),
          router: "/admissions-process/responsibility-school-manage/school-list"
        },
        {
          title: "宣讲计划",
          img: require("./img/1.png"),
          router: "/admissions-process/preaching-plan/all/2"
        },
        {
          title: "到校咨询",
          img: require("./img/2.png"),
          router: "/admissions-process/to-school-counseling-manage"
        },
        {
          title: "学校拜访",
          img: require("./img/3.png"),
          router: "/admissions-process/visit-school-record"
        },
        {
          title: "诉求管理",
          img: require("./img/4.png"),
          router: "/admissions-process/appeal-manage"
        },
        {
          title: "我的招生",
          img: require("./img/9.png"),
          router: "applets"
        }
      ]
    };
  },
  created() {
    this.queryAllPublishPlanAPP();
  },
  methods: {
    go(path) {
      if (path == "/admissions-process/to-school-counseling-manage") {
        this.getConsultationId();
      } else if (path == "applets") {
        this.$router.push("/admissions-process/applets");
        // this.generateQrCode(1234456);
      } else {
        path && this.$router.push({ path });
      }
    },
    /**
     * 生成小程序码
     * @param {String}sceneStr 责任人的学校数据
     * @author LGH
     */
    generateQrCode(sceneStr) {
      generateQrCode(sceneStr).then(res => {
        console.log(res, "===>小程序码");
      });
    },
    /**
     * 获取咨询ID
     * @author LGH
     */
    getConsultationId() {
      getConsultationId().then(res => {
        if (res.data) {
          this.$router.push("/admissions-process/to-school-counseling-manage");
        } else {
          this.$message.error("当前没有咨询计划！");
        }
      });
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
.sign {
  min-height: calc(100vh - 1.33333rem);

  .sign-content {
    background-color: bgColor;
    margin: 16px;
    border-radius: 6px;
    overflow: hidden;
    box-shadow: 0px 4px 8px 0px rgba(7, 7, 7, 0.06);

    &-header {
      font-size: 15px;
      color: #333333;
      height: 47px;
      line-height: 47px;
      padding-left: 16px;
      box-sizing: border-box;
      // border-bottom: 1px solid #DEDEDE;
    }
  }

  >>>.van-grid-item__text {
    font-size: 14px;
    color: #212832;
    margin-top: 8px;
  }

  >>>[class*=van-hairline]::after {
    border-color: #DEDEDE;
  }

  >>>.van-icon__image {
    height: 25px;
    width: 25px;
  }

  >>>.van-grid-item__content {
    width: 114px;
    height: 114px;
    padding: 20px 20px;
  }

  .borBot-bottom>>>.van-grid-item__content::after {
    border-bottom-width: 0px;
  }

  .borBot-right>>>.van-grid-item__content::after {
    border-right-width: 0px;
  }
}
</style>
