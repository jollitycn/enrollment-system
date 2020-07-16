<template>
  <div>
    <van-sticky :offset-top="0">
      <h5-header :leftStatus="true" title="报名信息"></h5-header>
    </van-sticky>
    <div class="info">
      <div class="sign-title">
        {{ currentRecruitSchool.recruitSchoolName }}
      </div>
      <div>
        <van-form validate-first>
          <div class="info-t">
            <div class="info-title">招生老师</div>
            <div class="content">
              <div class="content-first">
                <span class="content-first-one">负责老师</span>
                <span class="content-first-two">{{ techer.name }}</span>
              </div>
              <div class="content-first">
                <span class="content-first-one">联系方式</span>
                <span class="content-first-two">{{ techer.telephone }}</span>
              </div>
            </div>
          </div>
          <div class="info-t newt">
            <!-- 通过 pattern 进行正则校验 -->
            <div class="info-title">填写信息</div>
            <div class="list-content">
              <div
                class="list-content-one"
                v-for="(item, index) in list"
                :key="index"
                @click="toRouter(index)"
              >
                <span class="list-content-one-title">{{ item.title }}</span>
                <span
                  :class="[
                    'list-content-one-center',
                    { isShowColor: !item.isOk }
                  ]"
                  >{{ item.isOk ? "已完成" : "去完成" }}</span
                >
                <img
                  class="list-content-one-img"
                  :src="item.isOk ? imgIcon : imgIconRight"
                  alt=""
                />
              </div>
            </div>
          </div>
        </van-form>
      </div>
      <div style="margin-top: 40px" v-show="isShow" class="btn">
        <van-button
          type="primary"
          color="#00ccff"
          block
          @click="queryRequiredFieldStatu"
          >保存</van-button
        >
      </div>
    </div>
  </div>
</template>

<script>
import { Form, Sticky, Button } from "vant";
import Header from "@/components/cheader";
import { listRecruitSchoolOfLogin } from "@/api/home/home";
import {
  queryTeacherCharge,
  queryRequiredFieldStatu
} from "@/api/student/student";
import { mapState, mapMutations } from "vuex";
import {
  studentStatus,
  schoolStatus,
  queryFamilyMemberStatus,
  queryContactDetailStatus
} from "@/api/signList/backups/backups";
export default {
  components: {
    vanForm: Form,
    vanSticky: Sticky,
    vanButton: Button,
    h5Header: Header
  },
  name: "info",
  computed: {
    ...mapState([
      "currentRecruitSchool",
      "studentList",
      "currentStudent",
      "SchoolList"
    ])
  },
  data() {
    return {
      list: [
        { title: "基本信息", isOk: false, router: "/signList/information" },
        { title: "联系方式", isOk: false, router: "/signList/contact" },
        { title: "在校信息", isOk: false, router: "/signList/schoolInfo" },
        { title: "家庭成员", isOk: false, router: "/signList/family" }
      ],
      imgIcon: require("@/assets/image/right_icon_color.png"),
      imgIconRight: require("@/assets/image/rigth_icon_orgen.png"),
      techer: {
        name: "无", //老师姓名
        telephone: "无" //老师手机号
      },
      isShow: false
    };
  },
  watch: {
    $route(to, from) {
      if (from.name === "information") {
        this.getStudentStatus();
      } else if (from.name === "contact") {
        this.getQueryContactDetailStatus();
      } else if (from.name === "schoolInfo") {
        this.getSchoolStatus();
      } else if (from.name === "family") {
        this.getQueryFamilyMemberStatus();
      } else if (from.name === "list" && to.name === "backups") {
        this.getList();
        this.getStudentStatus();
        this.getQueryContactDetailStatus();
        this.getSchoolStatus();
        this.getQueryFamilyMemberStatus();
        this.isShow = false;
      }
    }
  },
  methods: {
    ...mapMutations([
      "setCurrentStudent",
      "setToken",
      "setSchoolList",
      "setCurrentRecruitSchool"
    ]),
    //去往路由
    toRouter(index) {
      this.$router.push({
        path: this.list[index].router
      });
    },
    //验证必填信息是否填完
    queryRequiredFieldStatu() {
      queryRequiredFieldStatu()
        .then(res => {
          if (res.data) {
            this.$router.push({
              path: "/signList"
            });
          } else {
            this.$toast("请完善四个模块中必填字段");
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    //获取招生老师信息
    getList() {
      queryTeacherCharge().then(res => {
        console.log(res, "招生老师信息");
        if (res.data) {
          this.techer.name = res.data.name;
          this.techer.telephone = res.data.telephone;
        }
      });
    },
    //获取基本信息完成状态
    getStudentStatus() {
      studentStatus().then(res => {
        console.log(res);
        this.list[0].isOk = res.data;
      });
    },
    //获取联系方式完成状态
    getQueryContactDetailStatus() {
      queryContactDetailStatus().then(res => {
        console.log(res);
        this.list[1].isOk = res.data;
      });
    },
    //获取在校信息完成状态
    getSchoolStatus() {
      schoolStatus().then(res => {
        console.log(res);
        this.list[2].isOk = res.data;
      });
    },
    //获取家庭信息完成状态
    getQueryFamilyMemberStatus() {
      queryFamilyMemberStatus().then(res => {
        console.log(res);
        this.list[3].isOk = res.data;
      });
    },
    // 获取招生学校列表
    listRecruitSchoolOfLogin() {
      listRecruitSchoolOfLogin().then(res => {
        let list = res.data;
        if (list && list.length) {
          // 显示对应学生的学校列表
          this.setSchoolList(list);
          //判断切换之后的默认学校有没有
          let status = true;
          for (let i = 0; i < list.length; i++) {
            if (
              this.currentRecruitSchool.recruitSchoolId ===
              list[i].recruitSchoolId
            ) {
              this.setCurrentRecruitSchool(list[i]);
              status = false;
              break;
            }
          }
          if (status) {
            this.setCurrentRecruitSchool(list[0]);
          }
        }
      });
    }
  },
  created() {
    this.getList();
    this.getStudentStatus();
    this.getQueryContactDetailStatus();
    this.getSchoolStatus();
    this.getQueryFamilyMemberStatus();
    let data = this.$route.query;
    console.log(data);
    if (data.status) {
      this.isShow = true;
      this.listRecruitSchoolOfLogin();
    } else {
      this.isShow = false;
    }
  },
  activated() {}
};
</script>

<style scoped lang="stylus">
.info
  font-size fontMedSize
  background BgColorNew
  min-height 100vh
  .newt
    margin-top 16px
  .info-t
    padding 20px 20px 10px 20px;
    background-color bgColor
    .info-title
      color fontTitleColor
      font-size BigFontTitle
      font-weight bold
      margin-bottom: 10px;
    .content
      margin-top: 20px;
      .content-first
        margin-bottom: 10px;
        >span
          display inline-block
        .content-first-one
          color fontMiniFontColor
        .content-first-two
          color fontTitleColor
          margin-left marginLeft
  .list-content
    .list-content-one
      display flex
      font-size: 15px;
      color fontTextColor
      height: 45px;
      align-items center
      .list-content-one-title
        color fontTitleColor
        font-weight bold
      .list-content-one-center
        flex 1
        text-align right
        margin-right 10px
        color activeRightIconColor
      .isShowColor
        color #ff9a3f
      .list-content-one-img
        width: 5px;
        height: 8px;
  .sign-title
    height: 60px;
    background-color: #fff;
    text-align: center;
    line-height: 60px;
    color fontTitleColor
    font-size: 17px;
    border-bottom 0.5px solid #f5f5f5
    border-top 0.5px solid #f5f5f5
  .btn
    position: fixed;
    width: 100%;
    bottom: 0;
    height: 49px;
    >>>button
      height: 49px;
      line-height 49px
    >>>span
        font-size 18px

</style>
