<template>
  <div class="student-detail">
    <van-sticky :offset-top="0">
      <h5-header :leftStatus="true" :isLeft="true" @clickHeadLeft="clickHeadLeft" title="学生详情"></h5-header>
      <!-- <h5-header :leftStatus="true" title="学生详情"></h5-header> -->
    </van-sticky>
    <div class="student-info" @click="navTo(`/student-manage/student-more-info/${studentId}`)">
      <div class="student-info-header">
        <div class="row-left">
          <img v-if="info.imageUrl" class="user-avatar" :src="info.imageUrl | imgUrlFilter" alt />
          <img
            v-else
            class="user-avatar"
            :src="require(`../img/${info.gender?'woman':'man'}.png`)"
            alt
          />
        </div>
        <div class="row-middle">
          <div class="content-cell">
            <span class="user-name">{{info.studentName}}</span>&emsp;&emsp;
            <span
              :class="{'male':!info.gender,'female':!!info.gender}"
              class="user-sex"
            ></span>
          </div>
          <div class="content-cell">{{info.sourceSchoolName}}</div>
          <div class="content-cell">{{info.className}}</div>
          <div class="content-cell">{{info.studentTelephone}}</div>
        </div>
        <div class="row-right">
          <div class="stu-status">
            <!-- <van-icon slot="right-icon" name="arrow" /> -->
            <img
              style="width:15px;height:24px;line-height: 24px; margin-top:11px;margin-right:8px;"
              src="@/assets/images/right.png"
              name="arrow-left"
            />
          </div>
        </div>
      </div>
    </div>
    <van-divider :style="{margin: '0',transform: 'scaleY(0.5)' }" />
    <!-- 选择专业 -->
    <!-- <van-cell
      @click="navTo(item.router)"
      :title="item.title"
      is-link
      :value="item.status"
      value-class="cell-tip "
      title-class="cell-title"
    />-->
    <!-- 在校信息 -->
    <!-- 跟进信息 -->
    <!-- 招生渠道 -->
    <!-- 进度查看 -->
    <!-- 到校咨询 -->

    <!-- 诉求管理 -->

    <van-cell
      v-for="(item,index) in list"
      :key="index"
      @click="navTo(item.router)"
      :title="item.title"
      is-link
      :value="item.status"
      value-class="cell-tip "
      title-class="cell-title"
    >
      <template #right-icon>
        <!-- <van-icon
                  size="small"
                  name="arrow"
                  style="line-height: inherit;"
                  class="school-news-icon"
        />-->
        <img
          style="width:15px;height:24px;line-height: 24px; margin-top:4px;margin-right:7px;"
          src="@/assets/images/right.png"
          name="arrow-left"
        />
      </template>
    </van-cell>
  </div>
</template>

<script>
import Vue from "vue";
import Header from "@/components/cheader";
import { Sticky, Cell, Icon } from "vant";
import { Divider } from "vant";
Vue.use(Divider);
Vue.use(Sticky);
Vue.use(Cell);
Vue.use(Icon);

import {
  queryStudentInfo,
  queryStudentInfoStatus
} from "../../../api/Student-info/Student-info";

export default {
  name: "student-detail",
  components: {
    "h5-header": Header
  },
  data() {
    return {
      studentId: null,
      //学生公关id
      studentPublicRelationId: null,
      info: {},
      statusEnums: ["去填写", "已保存"],
      list: [
        {
          title: "选择专业",
          status: "去填写",
          router: "/student-manage/student-select-major/#sid#/#rid#"
        },
        {
          title: "在校信息",
          status: "去填写",
          router: "/student-manage/student-school-info/#sid#"
        },
        {
          title: "跟进信息",
          status: "去填写",
          router: "/student-manage/student-follow-info/#sid#/#rid#"
        },
        {
          title: "招生渠道",
          status: "去填写",
          router: "/student-manage/student-channels/#sid#/#rid#"
        },
        {
          title: "进度查看",
          status: "",
          router: "/student-manage/student-progress-detail/#sid#/#rid#"
        },
        {
          title: "到校咨询",
          status: "",
          router: "/student-manage/student-counseling/#sid#/#rid#"
        },
        {
          title: "诉求查看",
          status: "",
          router: "/student-manage/student-appeal/#sid#/#rid#"
        }
      ]
    };
  },
  methods: {
    /**
     * 导航跳转页面
     * @author lmh
     */
    navTo(path) {
      if (path) {
        path = path
          .replace("#sid#", this.studentId)
          .replace("#rid#", this.studentPublicRelationId);
        this.$router.push({ path });
      }
    },
    /**
     * 获取学生基本信息填写状态
     * @author lgh
     */
    queryStudentInfoStatus() {
      let query = { studentInfoId: this.studentId };
      this.$message.loading("正在加载学生数据...");
      queryStudentInfoStatus(query).then(res => {
        this.$message.close();
        this.list[0].status = this.statusEnums[Number(res.data.aimMajor)];
        this.list[1].status = this.statusEnums[Number(res.data.inSchool)];
        this.list[2].status = this.statusEnums[Number(res.data.publicRelation)];
        this.list[3].status = this.statusEnums[Number(res.data.recruitWay)];
      });
    },
    /**
     * 返回学生管理列表
     * @author LGH
     */
    clickHeadLeft() {
      if (this.$route.query.isToResSchool) {
        this.$router.go(-1);
      } else {
        this.$router.push({
          path: `/student-manage/student-list`
        });
      }
    },
    /**
     * 查询学生基本信息
     * @author lmh
     */
    queryStudentInfo() {
      let data = this.studentId;
      this.$message.loading("正在加载...");
      queryStudentInfo(data)
        .then(res => {
          this.$message.close();
          this.info = res.data;
          this.studentPublicRelationId = res.data.studentPublicRelationId;
        })
        .catch(err => {
          console.error(err);
        });
    }
  },
  created() {
    this.studentId = this.$route.params.id;

    //初始化跳转链接
    // for (const item of this.list) {
    // item.router = item.router.replace("#sid#", this.studentId);
    // }
    // console.log(1111, ",studentId：", this.studentId);
    //查询基本数据
    this.queryStudentInfo();
    this.queryStudentInfoStatus();
  }
};
</script>

<style lang="stylus" scoped>
.student-detail {
  background-color: #F2F2F2;

  .student-info {
    // border-bottom: 1px solid rgba(0, 0, 0, 0.08);
    background: #fff;
    // border-radius: 8px;
    // margin-top: 16px;
    padding: 12px 16px;
    font-size: 15px;

    &-header {
      display: flex;
      margin-top: 12px;

      .user-avatar {
        width: 92px;
        height: 92px;
      }

      .user-sex {
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

      .user-name {
        font-weight: bold;
        font-size: 17px;
      }

      .stu-status {
        height: 76px;
        line-height: 76px;
        text-align: right;
        color: #999999;
      }
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
      width: 10px;
      margin: 0 12px;
    }

    .content-cell {
      height: 23px;
      line-height: 23px;
    }
  }

  >>>.van-cell {
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
  }

  .cell-tip {
    span, &+.van-icon {
      font-size: 15px;
      font-weight: 400;
      color: #999999;
    }

    &.active {
      span, &+.van-icon {
        color: #FF9638;
      }
    }
  }

  .cell-title {
    span {
      font-size: 15px;
      font-weight: 400;
      color: #333333;
    }
  }
}
</style>