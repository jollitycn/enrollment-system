<template>
  <div class="allStudent">
    <div>
      <div class="sign-title" @click="show = true">
        {{ currentRecruitSchool.recruitSchoolName }}
        <img :src="img" alt="" class="sign-title-img" />
      </div>
    </div>
    <div class="student">
      <div style="margin-bottom: 65px;" class="student-content">
        <div class="student-content">
          <div class="student-content-list head">
            <div class="student-content-list-left head">
              <img
                style="width: 100%;height: 100%;border-radius: 50%;"
                :src="userImage"
                alt=""
                :onerror="errorUserPhoto"
              />
            </div>
            <div class="student-content-list-right" style="font-size: 18px">
              {{ name }}
            </div>
          </div>
        </div>
        <div class="student-content">
          <div
            class="student-content-list"
            @click="go(item.router)"
            v-for="item in list"
            :key="item.title"
          >
            <div class="student-content-list-left">
              <img :src="item.imgSrc" alt="" />
            </div>
            <div class="student-content-list-right">{{ item.title }}</div>
          </div>
        </div>
        <div class="student-content">
          <div
            class="student-content-list"
            @click="go(item.router)"
            v-for="item in list2"
            :key="item.title"
          >
            <div class="student-content-list-left">
              <img :src="item.imgSrc" alt="" />
            </div>
            <div class="student-content-list-right">{{ item.title }}</div>
          </div>
        </div>
        <div class="student-content">
          <div
            class="student-content-list"
            @click="go(item.router)"
            v-for="item in list3"
            :key="item.title"
          >
            <div class="student-content-list-left">
              <img :src="item.imgSrc" alt="" />
            </div>
            <div class="student-content-list-right">{{ item.title }}</div>
          </div>
        </div>
      </div>
      <h5Tabbar></h5Tabbar>
    </div>
    <van-popup v-model="show" position="bottom">
      <van-picker
        show-toolbar
        :columns="SchoolList"
        value-key="recruitSchoolName"
        @confirm="SchoolListConfirm"
        @cancel="show = false"
      />
    </van-popup>
  </div>
</template>

<script>
import Tabbar from "@/components/ctabbar";
import { mapState, mapMutations } from "vuex";
import { imgDir } from "@/config/index";
import {
  queryStudentInfo,
  signInPreachPlanId,
  signUpPreachPlanId
} from "@/api/signList/backups/backups";
// import { getConfig } from "@/api/public/public";
import { switchRecruitSchool } from "@/api/home/home";
import { Popup, Picker, Dialog } from "vant";
const weixin = window.wx;
export default {
  name: "student",
  components: {
    h5Tabbar: Tabbar,
    vanPopup: Popup,
    vanPicker: Picker
  },
  data() {
    return {
      errorUserPhoto:
        'this.src="' + require("../../../../assets/image/defaultImg.jpg") + '"',
      img: require("../../../../assets/image/right.png"),
      show: false,
      showToast: false,
      showToast1: null,
      name: "",
      list: [
        {
          title: "进度查看",
          router: "/user/seeRate",
          imgSrc: require("../img/1.png")
        },
        {
          title: "我的诉求",
          router: "/user/myNeed",
          imgSrc: require("../img/2.png")
        }
      ],
      list2: [
        {
          title: "到校咨询",
          router: "/user/inSchool",
          imgSrc: require("../img/3.png")
        },
        { title: "在线咨询", router: "kefu", imgSrc: require("../img/4.png") },
        {
          title: "扫一扫",
          router: "code",
          imgSrc: require("../img/15.png")
        },
        {
          title: "宣讲评价",
          router: "/user/evaluate",
          imgSrc: require("../img/5.png")
        },
        {
          title: "消息中心",
          router: "/user/message",
          imgSrc: require("../img/6.png")
        }
      ],
      list3: [
        {
          title: "我的绑定",
          router: "/user/binding",
          imgSrc: require("../img/13.png")
        }
      ]
    };
  },
  computed: {
    ...mapState({
      userImage: state => imgDir + state.userImage,
      currentStudent: state => state.currentStudent,
      currentRecruitSchool: state => state.currentRecruitSchool,
      SchoolList: state => state.SchoolList
    })
  },
  beforeRouteLeave(to, from, next) {
    if (this.show) {
      this.show = false;
      next(false);
    } else {
      next();
    }
  },
  methods: {
    ...mapMutations(["setUserImage", "setCurrentRecruitSchool", "setToken"]),
    // 选择学校操作
    SchoolListConfirm(value) {
      console.log(value);
      this.switchRecruitSchool(value.recruitSchoolId, value);
      this.show = false;
      this.showToast = this.$toast.loading({
        duration: 0, // 持续展示 toast
        forbidClick: true,
        message: "切换学校中..."
      });
    },
    // 学生签到
    signInPreachPlanId(id) {
      signInPreachPlanId(id)
        .then(res => {
          console.log(res);
          this.showToast1.clear();
          setTimeout(() => {
            this.$toast({
              message: "签到成功",
              onClose: () => {
                this.$router.push({
                  path: "/user/evaluate"
                });
              }
            });
          }, 300);
        })
        .catch(err => {
          setTimeout(() => {
            this.showToast1.clear();
          }, 1500);
          console.log(err);
        });
    },
    // 学生报名
    signUpPreachPlanId(id) {
      signUpPreachPlanId(id)
        .then(res => {
          console.log(res);
          this.showToast1.clear();
          setTimeout(() => {
            this.$toast({
              message: "报名成功",
              onClose: () => {
                this.$router.push({
                  path: "/user/evaluate"
                });
              }
            });
          }, 300);
        })
        .catch(err => {
          setTimeout(() => {
            this.showToast1.clear();
          }, 1500);
          console.log(err);
        });
    },
    sys() {
      let wx = window.wx;
      let _this = this;
      wx.scanQRCode({
        needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
        scanType: ["qrCode", "barCode"], // 可以指定扫二维码还是一维码，默认二者都有
        success: function(res) {
          _this.showToast1 = _this.$toast.loading({
            duration: 0, // 持续展示 toast
            forbidClick: true,
            message: "识别中.."
          });
          if (res.errMsg === "scanQRCode:ok") {
            let result = ""; // 当needResult 为 1 时，扫码返回的结果
            try {
              result = JSON.parse(res.resultStr);
            } catch (e) {
              _this.showToast1.clear();
              _this.$toast("请扫描正确的二维码");
              return;
            }
            if (!result.schoolId) {
              _this.$toast("请扫描正确的二维码");
              setTimeout(() => {
                _this.showToast1.clear();
              }, 1500);
              return;
            }
            if (
              result.schoolId.toString() ===
              _this.currentRecruitSchool.recruitSchoolId.toString()
            ) {
              if (result.type === "SignIn") {
                // SignIn 签到
                _this.signInPreachPlanId(result.preachPlanId);
              } else if (result.type === "SignUp") {
                // SignUp 报名
                _this.signUpPreachPlanId(result.preachPlanId);
              } else {
                _this.showToast1.clear();
              }
            } else {
              _this.showToast1.clear();
              let status = false;
              let json = {};
              for (let i = 0; i < _this.SchoolList.length; i++) {
                if (
                  _this.SchoolList[i].recruitSchoolId.toString() ===
                  result.schoolId.toString()
                ) {
                  status = true;
                  json = _this.SchoolList[i];
                  break;
                }
              }
              if (status) {
                setTimeout(() => {
                  Dialog.confirm({
                    title: "确认操作",
                    message: "当前用户默认学校不是该学校，是否切换至该学校"
                  })
                    .then(() => {
                      _this.showToast = _this.$toast.loading({
                        duration: 0, // 持续展示 toast
                        forbidClick: true,
                        message: "切换学校中..."
                      });
                      _this.switchRecruitSchool(json.recruitSchoolId, json);
                    })
                    .catch(() => {
                      // on cancel
                    });
                }, 300);
              } else {
                _this.$toast("当前用户未注册该学校，请先注册再进行此操作");
              }
            }
          } else {
            _this.showToast1.clear();
          }
        },
        fail: function(err) {
          _this.showToast1.clear();
          console.log(err);
        }
      });
    },
    //发送切换学校
    switchRecruitSchool(id, value) {
      switchRecruitSchool(id)
        .then(res => {
          console.log(res, "发送切换学校");
          this.setToken(res.data);
          this.showToast.clear();
          this.setCurrentRecruitSchool(value);
        })
        .catch(() => {
          this.showToast.clear();
          this.$toast("切换失败");
        });
    },
    go(router) {
      if (router === "kefu") {
        this.navigate();
        return;
      }
      if (router === "code") {
        this.sys();
        return;
      }
      if (!router) {
        return;
      }
      this.$router.push({ path: router });
    },
    // 跳转至客服
    navigate() {
      weixin.miniProgram.navigateTo({
        url: "/pages/man/man"
      });
    },
    queryStudentInfo() {
      queryStudentInfo()
        .then(res => {
          console.log(res.data);
          if (res.data) {
            this.setUserImage(res.data.imageUrl);
            this.name = res.data.studentName;
          }
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  created() {
    this.queryStudentInfo();
  },
  mounted() {}
};
</script>

<style scoped lang="stylus">
.allStudent
  width: 10rem
  overflow: hidden
  background-color: #F5F5F5;
  .student
      height: calc(100vh - 1.33333rem)
      width: 11rem
      overflow-y: scroll
      .student-content
          margin-bottom 16px
          .student-content
              background-color bgColor
              .student-content-list.head
                  height 96px
              .student-content-list
                  display flex
                  align-items center
                  height 56px
                  .student-content-list-left.head
                      height 56px
                      width 56px
                      border-radius 1rem
                  .student-content-list-left
                      width 24px
                      height 40px
                      background-color #FFF
                      margin 0 16px;
                      border-radius: 8px;
                      display: flex;
                      justify-content: center;
                      align-items: center;
                      >img
                        width 24px
                        height 24px
                  .student-content-list-right
                      flex:1
                      color: fontTitleColor
                      height: 100%;
                      display: flex;
                      align-items: center;
                      border-bottom: 1px solid #f5f5f5
  .sign-title
    height: 60px;
    background-color: #fff;
    text-align: center;
    line-height: 60px;
    color fontTitleColor
    font-size: 17px;
    border-bottom: 0.5px solid #f5f5f5
    border-top 0.5px solid #f5f5f5
    >.sign-title-img
      width: 6px;
      transform: rotate(90deg);
      margin-left: 5px;
</style>
