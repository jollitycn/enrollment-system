<template>
  <div class="school-class-add">
    <van-sticky :offset-top="0">
      <h5-header :leftStatus="true" title="新增班级"></h5-header>
    </van-sticky>
    <school-class-form :on-save="onSave" />
  </div>
</template>

<script>
import Vue from "vue";
import Header from "@/components/cheader";
import { Sticky } from "vant";

Vue.use(Sticky);
import SchoolClassForm from "./components/school-class-form";

import { addClass } from "../../../api/sourceSchool/sourceSchool";
export default {
  name: "school-class-add",
  components: {
    "h5-header": Header,
    "school-class-form": SchoolClassForm
  },
  data() {
    return {
      schoolId: null,
      form: {
        sourceSchoolId: null
      }
    };
  },
  created() {
    this.schoolId = this.$route.params.sid;
  },
  methods: {
    /**
     * 点击保存
     */
    onSave(form) {
      this.form = form;
      this.form.sourceSchoolId = this.schoolId;
      let data = { ...this.form };
      addClass(data).then(() => {
        this.$message.success(`保存成功`);
        this.navToSchoolDetail();
      });
    },
    /**
     * 学校信息维护页面跳转
     */
    navToSchoolDetail() {
      let id = this.schoolId;
      this.$router.push({
        path: `/admissions-process/responsibility-school-manage/responsibility-school-detail/${id}`
      });
    }
  }
};
</script>

<style scoped lang="stylus">
.school-class-add {
  background-color: rgba(255, 255, 255, 1);
}
</style>
