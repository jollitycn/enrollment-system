<template>
  <div class="home">
    <van-sticky :offset-top="0">
      <div class="home-header">
        <div class="home-header-left" @click="show = true">
          {{ currentStudent.studentName }}
          <van-icon class="home-header-left-icon" name="exchange" />
        </div>
        <div class="home-header-center">招生学校列表</div>
        <div class="home-header-left"></div>
      </div>
    </van-sticky>
    <div class="home-content">
      <div class="home-content-list">
        <div
          class="home-content-list-one"
          v-for="(item, index) in SchoolList"
          :key="index"
          @click="go(item.recruitSchoolId)"
        >
          <div class="home-content-list-one-img">
            <img
              :src="imgReset(item.recruitSchoolLogo)"
              :alt="item.recruitSchoolName"
              style="width: 100%"
            />
          </div>
          <div class="home-content-list-one-text">
            <div class="home-content-list-one-text-title">
              {{ item.recruitSchoolName }}
            </div>
            <div class="home-content-list-one-text-address">
              学校地址：{{ item.address || "暂无地址" }}
            </div>
          </div>
          <div
            class="home-content-list-one-active"
            v-show="
              currentRecruitSchool.recruitSchoolId === item.recruitSchoolId
            "
          >
            当前选中
          </div>
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
        :columns="studentList"
        value-key="studentName"
        @confirm="studentListConfirm"
        @cancel="show = false"
      />
    </van-popup>
  </div>
</template>
<script>
import { Sticky, Icon, Popup, Picker } from "vant";
import {
  listRecruitSchoolOfLogin,
  listRecruitSchool,
  switchRecruitSchool
} from "@/api/home/home";
import { webImgDir } from "../../config";
import Tabbar from "@/components/ctabbar";
import { mapState, mapMutations } from "vuex";
export default {
  name: "Home",
  components: {
    vanSticky: Sticky,
    vanIcon: Icon,
    vanPopup: Popup,
    vanPicker: Picker,
    h5Tabbar: Tabbar
  },
  computed: {
    ...mapState([
      "studentList",
      "currentStudent",
      "SchoolList",
      "currentRecruitSchool"
    ])
  },
  data() {
    return {
      show: false
    };
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
    ...mapMutations([
      "setCurrentStudent",
      "setToken",
      "setSchoolList",
      "setCurrentRecruitSchool",
      "setActiveIndex"
    ]),
    // 选择学生确定操作
    studentListConfirm(value) {
      //更换默认学生
      this.setCurrentStudent(value);
      //获取当前学生的token
      this.listRecruitSchool(value.studentInfo);
      this.show = false;
    },
    // 切换学生之后换取token，并重新请求该学生对应下的招生学校列表
    listRecruitSchool(id) {
      listRecruitSchool(id).then(res => {
        this.setToken(res.data);
        this.listRecruitSchoolOfLogin();
      });
    },
    // 去往学校
    go(id) {
      this.$router.push({
        path: `/homeDetail/${id}`
      });
      this.setActiveIndex(0);
    },
    // 重新设置图片地址
    imgReset(img) {
      return webImgDir + "/static/" + img;
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
              this.switchRecruitSchool(list[i].recruitSchoolId);
              status = false;
              break;
            }
          }
          if (status) {
            this.setCurrentRecruitSchool(list[0]);
            this.switchRecruitSchool(list[0].recruitSchoolId);
          }
        }
      });
    },
    //切换学校换取token
    switchRecruitSchool(id) {
      switchRecruitSchool(id).then(res => {
        console.log(res, "切换学校后");
        this.setToken(res.data);
      });
    }
  },
  created() {
    // 获取当前学生下的招生学校列表
    this.listRecruitSchoolOfLogin();
  }
};
</script>
<style scoped lang="stylus">
.home
    height calc( 100vh - 50px)
    background-color #fff
    .home-header
        display flex
        justify-content: space-between;
        height 50px
        align-items center
        padding: 0 15px;
        border-bottom 0.5px solid #f5f5f5
        border-top 0.5px solid #f5f5f5
        .home-header-left
            min-width 100px
            color #333333
            font-size 15px
            display: flex;
            align-items: center;
            >.home-header-left-icon
                margin-left: 5px;
        .home-header-center
            font-size 18px
            font-weight bold
    .home-content
        .home-content-list
            .home-content-list-one
                display flex
                padding 15px
                border-bottom 0.5px solid #f5f5f5
                position: relative
            .home-content-list-one-img
                width 48px;
                height 48px;
                border-radius: 50%;
                margin-right: 10px;
                display: flex
                justify-content: center;
                align-items: center;
            .home-content-list-one-text
                flex 1
                display flex
                flex-direction column
                justify-content space-between
                .home-content-list-one-text-title
                    color #333
                    font-weight bold
                    font-size 17px
                    margin-bottom 5px
                .home-content-list-one-text-address
                    color #999
                    font-size 14px
                    line-height: 20px;
            .home-content-list-one-active
                position absolute
                top: 15px;
                right: 10px;
                color activeBtnColor
</style>
