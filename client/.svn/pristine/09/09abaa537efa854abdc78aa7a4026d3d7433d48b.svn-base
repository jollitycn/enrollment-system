<template>
  <div class="student-more-info">
    <div class="student-more-info-header">
      <van-sticky :offset-top="0">
        <h5-header :leftStatus="true" title="学生详情" rigText="编辑" @clickHeadAction="navToEdit"></h5-header>
      </van-sticky>
    </div>
    <div class="student-more-info-content">
      <!-- 头像 -->
      <div class="stu-info-header">
        <img v-if="form.imageUrl" class="user-avatar" :src="form.imageUrl | imgUrlFilter" alt />
        <img
          v-else
          class="user-avatar"
          :src="require(`../img/${form.gender?'woman':'man'}.png`)"
          alt
        />
      </div>
      <!-- 基本信息 -->
      <div class="stu-info">
        <div class="stu-info-title">基本信息</div>
        <div class="stu-info-content content-info-content-items">
          <div class="content-info-item">
            <div class="content-info-item-label">学生姓名</div>
            <div class="content-info-item-val">{{this.form.studentName}}</div>
          </div>
          <div class="content-info-item">
            <div class="content-info-item-label">所属学校</div>
            <div class="content-info-item-val">{{this.form.sourceSchoolName}}</div>
          </div>
          <div class="content-info-item">
            <div class="content-info-item-label">所在班级</div>
            <div class="content-info-item-val">{{this.form.className}}</div>
          </div>
          <div class="content-info-item">
            <div class="content-info-item-label">性别</div>
            <div class="content-info-item-val">{{gender[this.form.gender]}}</div>
          </div>
          <div class="content-info-item">
            <div class="content-info-item-label">民族</div>
            <div class="content-info-item-val">{{this.form.nation}}</div>
          </div>
          <div class="content-info-item">
            <div class="content-info-item-label">出生年月</div>
            <div class="content-info-item-val">{{this.form.doa}}</div>
          </div>
          <div class="content-info-item">
            <div class="content-info-item-label">身份证号</div>
            <div class="content-info-item-val">{{this.form.idNo}}</div>
          </div>
          <div class="content-info-item">
            <div class="content-info-item-label">身体状况</div>
            <div class="content-info-item-val">{{this.form.healthStatus}}</div>
          </div>
          <div class="content-info-item">
            <div class="content-info-item-label">户籍所在地</div>
            <div class="content-info-item-val">{{this.adress}}</div>
          </div>
        </div>
      </div>
      <!-- 联系方式 -->
      <div class="stu-info">
        <div class="stu-info-title">联系方式</div>
        <div class="stu-info-content content-info-content-items">
          <div class="content-info-item">
            <div class="content-info-item-label">家长手机</div>
            <div class="content-info-item-val">{{this.form.parentTelephone}}</div>
          </div>
          <div class="content-info-item">
            <div class="content-info-item-label">学生微信</div>
            <div class="content-info-item-val">{{this.form.wechat}}</div>
          </div>
          <div class="content-info-item">
            <div class="content-info-item-label">学生手机</div>
            <div class="content-info-item-val">{{this.form.studentTelephone}}</div>
          </div>
          <div class="content-info-item">
            <div class="content-info-item-label">家庭住址</div>
            <div class="content-info-item-val">{{this.familyadress}}</div>
          </div>
        </div>
      </div>
      <!-- 家庭成员 -->
      <div class="stu-info">
        <div class="stu-info-title">家庭成员</div>
        <div class="stu-info-content content-info-content-items">
          <div class="content-info-item">
            <div class="content-info-item-label">关系</div>
            <div class="content-info-item-val">父亲</div>
          </div>
          <div class="content-info-item">
            <div class="content-info-item-label">家长姓名</div>
            <div class="content-info-item-val">{{this.fatherform.name}}</div>
          </div>
          <div class="content-info-item">
            <div class="content-info-item-label">手机号</div>
            <div class="content-info-item-val">{{this.fatherform.telephone}}</div>
          </div>
          <div class="content-info-item">
            <div class="content-info-item-label">工作单位</div>
            <div class="content-info-item-val">{{this.fatherform.company}}</div>
          </div>
          <div class="content-info-item">
            <div class="content-info-item-label">职务</div>
            <div class="content-info-item-val">{{this.fatherform.position}}</div>
          </div>
          <div class="content-info-item">
            <div class="content-info-item-label">户籍所在地</div>
            <div class="content-info-item-val">{{this.fatherform.location}}</div>
          </div>
          <div class="stu-info-title">家庭成员</div>
          <div class="content-info-item">
            <div class="content-info-item-label">关系</div>
            <div class="content-info-item-val">母亲</div>
          </div>
          <div class="content-info-item">
            <div class="content-info-item-label">家长姓名</div>
            <div class="content-info-item-val">{{this.matherform.name}}</div>
          </div>
          <div class="content-info-item">
            <div class="content-info-item-label">手机号</div>
            <div class="content-info-item-val">{{this.matherform.telephone}}</div>
          </div>
          <div class="content-info-item">
            <div class="content-info-item-label">工作单位</div>
            <div class="content-info-item-val">{{this.matherform.company}}</div>
          </div>
          <div class="content-info-item">
            <div class="content-info-item-label">职务</div>
            <div class="content-info-item-val">{{this.matherform.position}}</div>
          </div>
          <div class="content-info-item">
            <div class="content-info-item-label">户籍所在地</div>
            <div class="content-info-item-val">{{this.matherform.location}}</div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import Header from "@/components/cheader";
import { Sticky } from "vant";
Vue.use(Sticky);

import {
  queryStudentInfo,
  familyinfo
} from "../../../api/Student-info/Student-info";
export default {
  name: "student-more-info",
  components: {
    "h5-header": Header
  },
  data() {
    return {
      gender: ["男", "女"],
      studentId: null,
      adress: "",
      familyadress: "",
      form: {},
      familyform: {},
      fatherform: {},
      matherform: {}
    };
  },
  created() {
    this.studentId = this.$route.params.id;
    this.queryStudentInfo();
    // this.familyinfo();
  },
  methods: {
    /**
     * 查询学生基本信息
     * @author lmh
     */
    queryStudentInfo() {
      let data = this.studentId;
      this.$message.loading();
      queryStudentInfo(data).then(res => {
        this.$message.close();
        this.form = res.data;
        this.adress = (res.data.province || "") + (res.data.city || "");
        this.familyadress =
          (res.data.familyProvince || "") + (res.data.familyCity || "");
        console.log(11122, res);
        //家庭称院信息获取
        this.familyform = res.data.familyMembers;
        if (this.familyform[0].relation == 0) {
          this.fatherform = res.data.familyMembers[0];
          this.matherform = res.data.familyMembers[1];
        } else {
          this.fatherform = res.data.familyMembers[1];
          this.matherform = res.data.familyMembers[0];
        }
      });
    },
    /**
     * 查询学生基本信息
     * @author lmh
     */
    familyinfo() {
      let data = this.studentId;
      familyinfo(data).then(res => {
        this.familyform = res.data;
        if (this.familyform[0].relation == 0) {
          this.fatherform = res.data[0];
          this.matherform = res.data[1];
        } else {
          this.fatherform = res.data[1];
          this.matherform = res.data[0];
        }
        console.log(12, res);
      });
    },

    navToEdit() {
      let id = this.studentId;
      this.$router.push({
        path: `/student-manage/student-more-info-edit/${id}`
      });
    }
  }
};
</script>

<style lang="stylus" scoped>
.student-more-info {
  &-content {
    .stu-info-header {
      margin: 16px;
      text-align: center;
    }

    .stu-info {
      margin: 16px;
      font-size: 14px;
      font-weight: 400;
      line-height: 21px;

      &-title {
        font-weight: 600;
        color: #333333;
      }
    }
  }
}

.user-avatar {
  width: 76px;
  height: 76px;
  border-radius: 38px;
}

.content-info-content-items {
  font-size: 15px;
  font-weight: 400;
  line-height: 28px;
  padding: 4px 0;

  .content-info-item {
    display: flex;

    &-label {
      width: 75px;
      color: #999999;
    }

    &-val {
      margin-left: 12px;
      flex: 1;
    }
  }
}
</style>