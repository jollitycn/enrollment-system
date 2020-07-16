<template>
  <div>
    <h5-header
      :isLeft="true"
      :leftStatus="true"
      title="宣讲计划管理"
      rigText="新增"
      @clickHeadLeft="clickHeadLeft"
      @clickHeadAction="clickHeadAction"
    ></h5-header>
    <div class="preachingPlan">
      <van-tabs
        v-model="active"
        color="#00CCFF"
        title-active-color="#00CCFF"
        title-inactive-color="#999999"
        line-width="108px"
        line-height="4px"
        @click="onClick"
      >
        <van-tab title="所有宣讲计划" name="1">
          <van-list
            v-if="list.length!=0"
            v-model="loading"
            :finished="finished"
            finished-text="没有更多了"
            @load="onLoad"
          >
            <div class="preachingPlan-item">
              <div class="preachingPlan-item-list" v-for="(ite, n) in list" :key="n">
                <preachingPlanInfo status="all" :list="ite" :sourceSchoolList="sourceSchoolList"></preachingPlanInfo>
                <!-- 所有宣讲计划二维码签到按钮 -->
                <div class="preachingPlan-bottom" v-if="(ite.isRelation && ite.status==2)">
                  <div
                    v-for="(item, index) in allPreachListIcon"
                    :key="index"
                    @click="showPopup(item.QrType,ite.preachPlanId,ite.title)"
                  >
                    <img :src="item.src" :alt="item.src" />
                    <div class="preachingPlan-bottom-title">{{ item.title }}</div>
                  </div>
                </div>
                <!-- 所有宣讲计划的删除发布按钮 -->
                <!-- <div
                  class="preachingPlan-bottom mypreachingPlan-bottom"
                  v-else-if="ite.isRelation && ite.status==1"
                >
                  <div
                    :class="{ rightBot: index == 0 }"
                    v-for="(item, index) in myPreachListIcon"
                    :key="index"
                    v-show="index !== 2"
                  >
                    <div
                      :class="[item.colorName]"
                      @click="delOrRel(item.colorName,ite.preachPlanId)"
                    >{{ item.title }}</div>
                  </div>
                </div>-->
                <!-- 已完成状态下的按钮 -->
                <div class="preachingPlan-bottom mypreachingPlan-bottom" v-else-if="ite.isRelation">
                  <div
                    v-for="(item, index) in finishPreachListIcon"
                    :key="index"
                    @click="showPopup(item.QrType,ite.preachPlanId,ite.title)"
                  >
                    <img :src="item.src" :alt="item.src" />
                    <div class="preachingPlan-bottom-title">{{ item.title }}</div>
                  </div>
                </div>
              </div>
            </div>
          </van-list>
          <custom-empty v-else :type="1" height="calc(100vh - 200px)" />
        </van-tab>
        <van-tab title="我的宣讲计划" name="2">
          <van-list
            v-if="list.length!=0&&list!=null&&list!=[]"
            v-model="loading"
            :finished="finished"
            finished-text="没有更多了"
            @load="onLoad"
          >
            <div class="preachingPlan-item">
              <div class="preachingPlan-item-list" v-for="(ite, n) in list" :key="n">
                <preachingPlanInfo
                  status="myPreach"
                  :list="ite"
                  :sourceSchoolList="sourceSchoolList"
                ></preachingPlanInfo>
                <!-- 我的宣讲计划发布删除按钮 -->
                <div class="preachingPlan-bottom mypreachingPlan-bottom" v-if="ite.status==1">
                  <div
                    :class="{ rightBot: index == 0,topBot:true }"
                    v-for="(item, index) in myPreachListIcon"
                    :key="index"
                    v-show="index !== 2"
                  >
                    <div
                      :class="[item.colorName]"
                      @click="delOrRel(item.colorName,ite.preachPlanId)"
                    >{{ item.title }}</div>
                  </div>
                </div>
                <!-- 已完成状态下的按钮 -->
                <div class="preachingPlan-bottom mypreachingPlan-bottom" v-else-if="ite.status==3">
                  <div
                    v-for="(item, index) in finishPreachListIcon"
                    :key="index"
                    @click="showPopup(item.QrType,ite.preachPlanId,ite.title)"
                  >
                    <img :src="item.src" :alt="item.src" />
                    <div class="preachingPlan-bottom-title">{{ item.title }}</div>
                  </div>
                </div>
                <!-- 我的宣讲计划二维码签到按钮 -->
                <div class="preachingPlan-bottom" v-else>
                  <div
                    v-for="(item, index) in allPreachListIcon"
                    :key="index"
                    @click="showPopup(item.QrType,ite.preachPlanId,ite.title)"
                  >
                    <img :src="item.src" :alt="item.src" />
                    <div class="preachingPlan-bottom-title">{{ item.title }}</div>
                  </div>
                </div>
              </div>
            </div>
          </van-list>
          <custom-empty v-else :type="1" height="calc(100vh - 200px)" />
        </van-tab>
      </van-tabs>
    </div>
    <div>
      <van-popup v-model="popupShow" @click="showPopup('no')">
        <vueQr :text="qrConfig.value" :size="200"></vueQr>
      </van-popup>
    </div>
  </div>
</template>

<script>
import {
  deleteData,
  publish
} from "../../../api/admissions-process/preaching-plan";
import { Tab, Tabs, Popup } from "vant";
import preachingPlanInfo from "./components/preaching-plan-info";
import { listSourceSchoolPage } from "@/api/sourceSchool/sourceSchool";
import CustomEmpty from "@/components/custom-empty";
import Header from "@/components/cheader";
import Qr from "vue-qr";
import Vue from "vue";
import { List, Toast, Dialog } from "vant";
Vue.use(List);
Vue.use(Toast);

import {
  pagePreachPlanByUserId,
  pageAllPreachPlanByUserId
} from "@/api/admissions-process/preaching-plan.js";
export default {
  name: "preachingPlan",
  components: {
    h5Header: Header,
    vanTab: Tab,
    vanTabs: Tabs,
    vanPopup: Popup,
    preachingPlanInfo: preachingPlanInfo,
    vueQr: Qr,
    "custom-empty": CustomEmpty
  },
  data() {
    return {
      popupShow: false, //弹出层显示状态
      qrConfig: {
        value: {}
      },
      active: this.$route.params.active,
      allPreachListIcon: [
        { title: "报名二维码", src: require("../img/5.png"), QrType: "SignUp" },
        { title: "签到二维码", src: require("../img/6.png"), QrType: "SignIn" },
        { title: "签到学员", src: require("../img/7.png"), QrType: "SignStu" },
        { title: "宣讲总结", src: require("../img/8.png"), QrType: "SignSum" }
      ],
      finishPreachListIcon: [
        { title: "签到学员", src: require("../img/7.png"), QrType: "SignStu" },
        { title: "宣讲总结", src: require("../img/8.png"), QrType: "SeeSum" }
      ],
      myPreachListIcon: [
        { title: "删除", colorName: "deleteColor" },
        { title: "发布", colorName: "readColor" },
        { title: "已发布", colorName: "readTrueColor" }
      ],
      searchQuery: {
        pageNum: 1,
        pageSize: 10
      },
      list: [],
      sourceSchoolList: [],
      loading: false,
      finished: false,
      status: this.$route.params.status,
      returnRouter: ""
    };
  },
  watch: {
    "$route.params.status"() {
      this.listSourceSchoolPage();
    },
    "$route.params.returnRouter"() {
      this.returnRouter = "/list";
    }
  },
  created() {
    this.returnRouter = this.$route.params.returnRouter;
    this.listSourceSchoolPage();
  },
  methods: {
    //新增宣讲计划
    clickHeadAction() {
      this.$router.push({ path: "/admissions-process/preaching-plan-add" });
    },

    //二维码展示
    /**
     * @param type{String} 标识点击的是哪个按钮
     * @param id{number} 生成二维码报名签到事件
     * @param title{String} 宣讲计划的主题
     */
    showPopup(type, id, title) {
      console.log(title, 111);

      if (type == "SignUp" || type == "SignIn") {
        this.popupShow = !this.popupShow;
        // this.qrConfig.value = id;
        this.qrConfig.value = JSON.stringify({
          preachPlanId: id,
          type: type,
          schoolId: JSON.parse(localStorage.getItem("currentSchoolId"))
        });
        console.log(id, "===>ID");
      } else if (type == "SignSum") {
        this.$router.push({
          path: `/admissions-process/preaching-plan-summary/${id}`
        });
      } else if (type == "SignStu") {
        this.$router.push({
          path: `/admissions-process/preaching-plan-signed-students/${title}/${id}`
        });
      } else if (type == "SeeSum") {
        this.$router.push({
          path: `/admissions-process/preaching-plan-summary/${id}/see`
        });
      }
    },
    onClick(name) {
      if (name == 2) {
        this.status = "all";
        let path = `/admissions-process/preaching-plan/${this.status}/2`;
        let returnRouter = this.returnRouter;
        if (!(this.$route.path == path)) {
          this.initializeNews();
          this.$router.push({
            path: `/admissions-process/preaching-plan/${this.status}/2`,
            params: returnRouter
          });
        }
        // this.pageAllPreachPlanByUserId(this.searchQuery);
      } else {
        this.status = "myPreach";
        let path = `/admissions-process/preaching-plan/${this.status}/1`;
        let returnRouter = this.$route.params.returnRouter;
        if (!(this.$route.path == path)) {
          this.initializeNews();
          this.$router.push({
            path: `/admissions-process/preaching-plan/${this.status}/1`,
            params: returnRouter
          });
        }
        // this.pagePreachPlanByUserId(this.searchQuery);
      }
    },
    /**
     * load加载内容操作
     * @author LiuGuoHao
     */
    onLoad() {
      let queryParam = { ...this.searchQuery };
      if (this.status == "all") {
        this.pageAllPreachPlanByUserId(queryParam);
      } else if (this.status == "myPreach") {
        this.pagePreachPlanByUserId(queryParam);
      }
    },
    /**
     * 初始化搜索条件以及加载状态
     * @author LiuGuoHao
     */
    initializeNews() {
      this.list = [];
      this.loading = false;
      this.finished = false;
      this.searchQuery = {
        pageNum: 1,
        pageSize: 10
      };
    },
    /**
     * 删除或者发布的点击操作时间
     * @param btnNews{String} 点击按钮对应的信息，deleteColor为删除，readColor为发布
     * @param preachPlanId{String} 删除宣讲计划对应的宣讲计划ID
     * @author LiuGuoHao
     */
    delOrRel(btnNews, preachPlanId) {
      if (btnNews == "deleteColor") {
        Dialog.confirm({
          title: "提示",
          message: "是否确认要删除该宣讲计划？"
        }).then(() => {
          this.$message("正在删除...");
          deleteData(preachPlanId)
            .then(() => {
              this.$message.close();
              this.$message.success("删除成功");
              this.initializeNews();
              this.onLoad();
            })
            .catch(err => {
              console.error("删除失败：", err);
            });
        });
      } else {
        Dialog.confirm({
          title: "提示",
          message: "是否确认要发布该宣讲计划？"
        })
          .then(() => {
            this.$message("正在发布...");
            publish(preachPlanId).then(() => {
              this.$message.close();
              this.$message.success("发布成功");
              this.initializeNews();
              this.onLoad();
            });
          })
          .catch(err => {
            console.error("发布失败：", err);
          });
      }
    },
    /**
     * 分页获取生源学校信息列表
     * @author LiuGuoHao
     */
    listSourceSchoolPage() {
      let schoolQuery = {
        pageNum: 1,
        pageSize: 10000
      };
      this.$message.loading("正在加载...");
      listSourceSchoolPage(schoolQuery)
        .then(res => {
          this.onLoad();
          this.sourceSchoolList = res.data.records;
          this.$message.close();
        })
        .catch(err => {
          console.error("加载出错：", err);
        });
    },
    /**
     * 获取所有演讲计划列表
     * @param searchQuery{array} 获取该数据所需要的页码，内含pageNum当前页，pageSize每页大小
     * @author LiuGuoHao
     */
    pageAllPreachPlanByUserId(searchQuery) {
      this.$message.loading("加载中...");
      pageAllPreachPlanByUserId(searchQuery)
        .then(res => {
          this.searchQuery.pageSize = res.data.size;
          this.total = res.data.total;
          let data = res.data.current === 1 ? [] : this.list;

          this.list = data.concat(res.data.records);
          this.searchQuery.pageNum = this.searchQuery.pageNum + 1;
          // this.finished =
          //   this.res.data.records.length === this.searchQuery.pageNum;

          // 加载状态结束
          this.loading = false;
          // 数据全部加载完成
          if (this.list.length >= this.total) {
            this.finished = true;
          }
          this.$message.close();
        })
        .catch(err => {
          console.error("加载错误：", err);
          this.$message.close();
        });
    },
    /**
     * 根据当前主讲人获取所有演讲计划列表
     * @param searchQuery{array} 获取该数据所需要的页码，内含pageNum当前页，pageSize每页大小
     * @author LiuGuoHao
     */
    pagePreachPlanByUserId(searchQuery) {
      this.$message.loading("加载中...");
      pagePreachPlanByUserId(searchQuery)
        .then(res => {
          this.searchQuery.pageSize = res.data.size;
          this.total = res.data.total;
          let data = res.data.current === 1 ? [] : this.list;
          // let data = this.list;
          this.list = data.concat(res.data.records);
          this.searchQuery.pageNum = this.searchQuery.pageNum + 1;
          // 加载状态结束
          this.loading = false;
          // 数据全部加载完成
          if (this.list.length >= this.total) {
            this.finished = true;
          }
          this.$message.close();
        })
        .catch(err => {
          console.error("加载错误：", err);
        });
    },
    /**
     * 返回招生过程管理
     */
    clickHeadLeft() {
      if (this.returnRouter) {
        this.$router.push({
          path: this.returnRouter
        });
      } else {
        this.$router.push({
          path: `/admissions-process`
        });
      }
    }
  }
};
</script>

<style scoped lang="stylus">
>>>[class*=van-hairline]::after {
  border: 0px;
}

.preachingPlan {
  min-height: calc(100vh - 94px);

  >>>.van-tab__text--ellipsis {
    font-size: 15px;
    height: 23px;
    font-weight: Regular;
  }

  .preachingPlan-item {
    .preachingPlan-item-list {
      margin-bottom: 20px;

      .mypreachingPlan-bottom {
        padding-top: 10px;
        // border-top: 1px solid #eee;
      }

      .preachingPlan-bottom {
        background-color: bgColor;
        display: flex;
        color: fontTitleColor;

        .preachingPlan-bottom-title {
          font-size: 12px;
          color: rgba(51, 51, 51, 1);
          font-weight: 400;
        }

        .rightBot {
          border-right: 1px solid #eee;
        }

        .topBot {
          border-top: 1px solid #eee;
        }

        >div {
          flex: 1;
          text-align: center;

          >img {
            width: 0.7rem;
            height: 0.7rem;
          }

          >div {
            margin-top: 10px;
            margin-bottom: 20px;
          }
        }

        .deleteColor {
          padding-top: 10px;
          color: deleteColor;
        }

        .readColor {
          padding-top: 10px;
          color: readColor;
        }

        .readTrueColor {
          color: readTrueColor;
        }
      }
    }
  }
}
</style>
