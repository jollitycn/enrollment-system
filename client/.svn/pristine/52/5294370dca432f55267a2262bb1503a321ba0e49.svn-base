<template>
  <div class="sign">
    <div>
      <div class="sign-title" @click="show = true">
        {{ currentRecruitSchool.recruitSchoolName }}
        <img :src="img" alt="" class="sign-title-img" />
      </div>
      <div style="padding: 16px;">
        <div class="sign-content">
          <div class="sign-content-title">报名资料</div>
          <van-grid :column-num="3"  class="sign-content-grid">
            <van-grid-item
              @click="go(item.router)"
              v-for="(item, index) in list"
              :key="index"
            >
              <van-image
                style="margin-top: 6px;"
                :src="item.imgSrc"
                class="imgSrcClass"
              />
              <span style="margin-bottom: 6px;">{{ item.title }}</span>
            </van-grid-item>
          </van-grid>
        </div>
      </div>
    </div>
    <h5Tabbar></h5Tabbar>
    <van-popup
      v-model="show"
      position="bottom"
    >
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
import { Grid, GridItem, Image, Popup, Picker } from "vant";
import { mapState, mapMutations } from "vuex";
import { switchRecruitSchool } from "@/api/home/home";
export default {
  name: "list",
  components: {
    h5Tabbar: Tabbar,
    vanGrid: Grid,
    vanGridItem: GridItem,
    vanPopup: Popup,
    vanPicker: Picker,
    vanImage: Image,
  },
  data() {
    return {
      show: false,
      showToast: null,
      img: require("../../../../assets/image/right.png"),
      list: [
        {
          title: "报名信息",
          img: "",
          router: "/signList/backups",
          imgSrc: require("../img/1.png")
        },
        {
          title: "填报志愿",
          img: "",
          router: "volunteer",
          imgSrc: require("../img/3.png")
        },
        {
          title: "学校宣传",
          img: "",
          router: "/signList/schoolList",
          imgSrc: require("../img/4.png")
        }
      ]
    };
  },
  computed: {
    ...mapState(["currentRecruitSchool", "SchoolList"])
  },
  beforeRouteLeave(to, from, next) {
    if(this.show) {
      this.show = false;
      next(false);
    }else {
      next();
    }
  },
  methods: {
    ...mapMutations(["setCurrentRecruitSchool", "setToken"]),
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
      if (!router) {
        return;
      }
      if (router === "volunteer") {
        this.$router.push({ name: router, params: { status: true } });
      } else {
        this.$router.push({ path: router });
      }
    }
  }
};
</script>

<style scoped lang="stylus">
.sign
    height: calc(100vh);
    background-color #f5f5f5
    .sign-content
        background-color bgColor
        border-radius 6px
        overflow hidden
        &-title
          height: 47px;
          line-height: 47px;
          font-size: 16px;
          margin-left: 16px;
          color: #333;
        &-grid
          width: 102%;
          position: relative;
          left: -1%;
        .imgSrcClass
          width: 28px;
          height: 28px;
          margin-bottom: 12px;
        >>>.van-hairline--top::after
              border-color: #ddd;
        >>>.van-grid-item__content::after
              border-color: #ddd;
              border-width: 0 1px 0 0
  .sign-title
      height: 60px;
      background-color: #fff;
      text-align: center;
      line-height: 60px;
      color fontTitleColor
      font-size: 17px;
      border-top 0.5px solid #f5f5f5
      >.sign-title-img
          width: 6px;
          transform: rotate(90deg);
          margin-left: 5px;
</style>
