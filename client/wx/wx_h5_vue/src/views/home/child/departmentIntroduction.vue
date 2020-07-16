<template>
  <div class="dep">
    <div class="department">
      <div class="department-list" v-for="(item, index) in list" :key="index">
        <div class="department-list-header" @click="go(item.collegeId)">
          <div class="department-list-header-left">{{ item.collegeName }}</div>
          <div class="department-list-header-right">
            院系详情 <img :src="img" alt="" />
          </div>
        </div>
        <div class="department-list-center">
          <div class="department-list-center-left">专业数量</div>
          <div class="department-list-center-right">
            {{ item.majorCount || 0 }}
          </div>
        </div>
        <div class="department-list-center">
          <div class="department-list-center-left">招生人数</div>
          <div class="department-list-center-right">
            {{ item.recruitAmount || 0 }}人
          </div>
        </div>
        <div class="department-list-center">
          <div class="department-list-center-left">招生对象</div>
          <div class="department-list-center-right">
            {{ item.recruitTarget || "暂无招生对象" }}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { queryCollegeInfo } from "@/api/user/user";
export default {
  name: "departmentIntroduction",
  data() {
    return {
      list: [],
      id: "",
      img: require("../../../assets/image/right.png")
    };
  },
  methods: {
    // 获取学校院系列表
    queryCollegeInfo() {
      queryCollegeInfo(this.id)
        .then(res => {
          console.log(res);
          this.list = res.data;
        })
        .catch(err => {
          console.log(err);
        });
    },

    //前往院系详情
    go(id) {
      this.$router.push({
        path: `/departmentDetail/${id}`
      });
    }
  },
  created() {
    this.id = this.$route.params.id;
    this.queryCollegeInfo();
  }
};
</script>

<style scoped lang="stylus">
.dep
  .department
    .department-list
      margin: 16px;
      padding: 16px;
      border-radius: 4px;
      background-color #fff
      .department-list-header
        display flex
        justify-content space-between
        align-items center
        .department-list-header-left
          font-size 18px
          font-weight bold
        .department-list-header-right
          font-size 15px
          color #999
          display: flex;
          align-items: center;
          >img
            width: 5px;
            margin-left 10px
      .department-list-center
        font-size 15px
        display: flex;
        margin-top: 16px;
      .department-list-center-left
          color #999
          width: 80px;
        .department-list-center-right
          color #333
          flex 1
</style>
