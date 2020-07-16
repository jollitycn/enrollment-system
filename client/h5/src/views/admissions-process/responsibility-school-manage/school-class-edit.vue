<template>
  <div class="school-class-edit">
    <van-sticky :offset-top="0">
      <h5-header :leftStatus="true" title="编辑班级"></h5-header>
    </van-sticky>
    <school-class-form :info="detail" :on-save="onSave" />
  </div>
</template>

<script>
import Vue from "vue";
import Header from "@/components/cheader";
import { Sticky } from "vant";

Vue.use(Sticky);
import SchoolClassForm from "./components/school-class-form";

import {
  getClassInfo,
  updateClass
} from "../../../api/sourceSchool/sourceSchool";
export default {
  name: "school-class-edit",
  components: {
    "h5-header": Header,
    "school-class-form": SchoolClassForm
  },
  data() {
    return {
      detail: {},
      form: {
        classId: null
      },
      classId: null
    };
  },
  created() {
    //获取班级信息
    this.queryDetail(this.$route.params.id);
  },
  methods: {
    /**
     * 获取班级信息
     * @param {string} id 班级ID
     * @author cwz
     */
    queryDetail(id) {
      getClassInfo(id).then(res => {
        this.detail = res.data;
        console.log("res", this.detail);
      });
      // this.detail = {};
    },
    // /**
    //  * 查询详情数据
    //  * @param {string} id 班级ID
    //  * @author lmh
    //  */
    // queryDetail(id){
    //   //TODO 调用接口查询数据
    //   this.detail = {};
    // },
    /**
     * 编辑班级信息保存
     */
    onSave(form) {
      console.log("表单数据：", form);
      this.form = form;
      this.form.classId = this.$route.params.id;
      let data = { ...this.form };
      //TODO 调用接口提交表单数据
      updateClass(data).then(() => {
        this.$message.success(`保存成功`);
        this.navToClassDetail();
      });
    },
    /**
     * 进入班级页面跳转
     */
    navToClassDetail() {
      let sid = this.$route.params.sid;
      let id = this.$route.params.id;
      this.$router.push({
        path: `/admissions-process/responsibility-school-manage/school-class-detail/${sid}/${id}`
      });
    }
  }
};
</script>

<style scoped lang="stylus">
.school-class-edit {
  background-color: rgba(255, 255, 255, 1);
}
</style>
