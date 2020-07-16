<template>
  <div class="student-channels">
    <h5-header :leftStatus="true" title="招生渠道"></h5-header>

    <van-form validate-first input-align="right">
      <!-- 渠道选择 -->
      <div class="follow-type">
        <div class="follow-type-title">渠道选择</div>
        <div class="follow-type-list">
          <span
            :v-model="form.level"
            v-for="(item, index) in typeList"
            :key="index"
            @click="changeType(index)"
            :class="{ active: item.title == form.onlineType }"
          >{{ item.title }}</span>
        </div>
      </div>
      <!-- 推荐人信息 -->
      <van-field
        label-class="custom-field-title"
        v-model="form.recommendName"
        label="推荐人信息"
        placeholder="请填写推荐人"
      />
      <!-- 联系方式 -->
      <van-field
        label-class="custom-field-title"
        v-model="form.telephone"
        label="联系方式"
        placeholder="请填写联系方式"
      />
      <div class="student-channels-save">
        <button class="action-save-btn" @click="save">保存</button>
      </div>
    </van-form>
  </div>
</template>

<script>
import {
  queryRecruitWayInfo,
  saveOrUpdateRecruitWayInfo,
  queryNodeNameList
} from "../../../api/Student-info/student-channels";
import Header from "@/components/cheader";
import Vue from "vue";
import { Form, Field } from "vant";

Vue.use(Form);
Vue.use(Field);

export default {
  name: "student-channels",
  components: {
    "h5-header": Header
  },
  data() {
    return {
      activeIndex: 0,
      studentInfoId: "",
      typeList: [
        // { id: 0, title: "线下渠道" },
        // { id: 1, title: "线上渠道" },
        // { id: 2, title: "网站招生" },
        // { id: 3, title: "广告招生" },
        // { id: 4, title: "朋友介绍" }
      ],
      form: {
        recommendName: "",
        recruitType: 0,
        recruitWayId: 0,
        studentPublicRelationId: 0,
        telephone: "",
        level: 0,
        onlineType: 0
      }
    };
  },
  watch: {
    info() {
      this.form = this.info;
      this.typeList[0].id = this.form.level;
      console.log("this.typeList[0].id", this.typeList[0].id);
    }
  },
  created() {
    this.studentInfoId = this.$route.params.id;
    this.studentPublicRelationId = this.$route.params.relationId;
    console.log(this.studentInfoId, "==>");
    this.queryRecruitWayInfo(this.studentPublicRelationId);
    this.queryNodeNameList();
  },
  methods: {
    /**
     * 跟进类型切换
     * @param {int } index 类型列表的下标
     * @author lmh
     */
    changeType(index) {
      this.form.recruitType = index;
      this.form.level = this.typeList[this.activeIndex].id;
      this.form.onlineType = this.typeList[this.form.recruitType].title;
    },
    save() {
      this.form.studentPublicRelationId = this.studentPublicRelationId;
      if (this.form.onlineType) {
        this.form.recruitType = 0;
      } else {
        this.form.recruitType = 1;
      }
      console.log(this.form.onlineType, 222);

      this.$message.loading("正在保存...");
      saveOrUpdateRecruitWayInfo(this.form)
        .then(() => {
          this.$message.close();
          this.$message.success("保存成功");
          this.$router.go(-1);
        })
        .catch(err => {
          console.error("保存失败：", err);
        });
    },
    /**
     * 根据学生公关ID查询学生招生渠道信息
     * @param {number} studentInfoId 学生的公关ID
     * @author lgh
     */
    queryRecruitWayInfo(studentInfoId) {
      this.$message.loading("正在加载...");
      queryRecruitWayInfo(studentInfoId)
        .then(res => {
          this.$message.close();
          if (res.data) {
            this.form = res.data;
          }
          console.log(this.form, "===>form");
        })
        .catch(err => {
          console.error("加载失败：", err);
        });
    },
    //  查询招生渠道的渠道名称列表
    queryNodeNameList() {
      queryNodeNameList().then(res => {
        for (let i = 0; i < res.data.length; i++) {
          let type = { id: i, title: res.data[i] };
          this.typeList.push(type);
        }
      });
    }
  }
};
</script>

<style scoped lang="stylus">
.student-channels {
  background-color: #fff;

  >>>.custom-field-title {
    span {
      font-weight: 600;
      color: rgba(51, 51, 51, 1);
    }
  }

  &-save {
    margin-top: 100px;

    .action-save-btn {
      width: 100%;
      height: 49px;
      color: #fff;
      background: rgba(0, 204, 255, 1);
      // 消除默认样式
      margin: 0px;
      padding: 0px;
      border: 0px;
    }
  }
}

.follow-type {
  padding: 16px;
  border-bottom: solid 1px #DDDDDD;

  &-title {
    color: #333333;
    font-size: 15px;
    font-weight: 600;
    margin-bottom: 16px;
  }

  &-list {
    >span {
      width: 60px;
      // height: 21px;
      line-height: 21px;
      color: #999;
      background-color: #F2F2F2;
      padding: 10px 40px;
      display: inline-block;
      border-radius: 4px;
      margin-left: 16px;
      margin-top: 8px;

      &:nth-child(2n+1) {
        margin-left: 22px;
      }
    }

    .active {
      color: #FFFFFF;
      background-color: #00CCFF;
    }
  }
}
</style>
