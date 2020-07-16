<template>
  <div class="departmentall">
    <van-sticky :offset-top="0">
      <h5-header :leftStatus="true" title="专业详情"></h5-header>
    </van-sticky>
    <div>
      <div class="department">
        <div class="department-list">
          <div class="department-list-title">{{ form.majorName }}</div>
          <div class="department-list-center">
            <div class="department-list-center-left">招生人数</div>
            <div class="department-list-center-right">
              {{ form.recruitAmount }}
            </div>
          </div>
          <div class="department-list-center">
            <div class="department-list-center-left">学费</div>
            <div class="department-list-center-right">
              {{ form.fee }}
            </div>
          </div>
          <div class="department-list-center">
            <div class="department-list-center-left">培养目标</div>
            <div class="department-list-center-right">
              {{ form.cultivateAim }}
            </div>
          </div>
          <div class="department-list-center">
            <div class="department-list-center-left">主要课程</div>
            <div class="department-list-center-right">
              {{ form.mainCourse }}
            </div>
          </div>
          <div class="department-list-center">
            <div class="department-list-center-left">就业方向</div>
            <div class="department-list-center-right">
              {{ form.careerForward }}
            </div>
          </div>
          <div class="schoolDetail-list-content">
            <div class="schoolDetail-list-content-left">专业简介</div>
            <div
              class="schoolDetail-list-content-right"
              v-html="resetHtml(form.comment)"
            ></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Sticky } from "vant";
import Header from "@/components/cheader";
import {
  queryMajorInfoByMajorId
} from "@/api/user/user";
import { webImgDir } from "../../../config";
export default {
  name: "objectDetail",
  components: {
    h5Header: Header,
    vanSticky: Sticky
  },
  data() {
    return {
      form: {},
      id: "",
      schoolID: ""
    };
  },
  methods: {
    resetHtml(val) {
      if (val) {
        return val.replace(/\/admin/g, webImgDir);
      }
    },
    //获取具体的院系详细信息
    queryMajorInfoByMajorId() {
      let data ={
        majorId: this.id
      };
      console.log(data);
      queryMajorInfoByMajorId(data)
        .then(res => {
          console.log(res);
          this.form = res.data;
        })
        .catch(err => {
          console.log(err);
        });
    },

  },
  created() {
    this.id = this.$route.params.id;
    this.schoolID = this.$route.params.schoolID;
    this.queryMajorInfoByMajorId();
  }
};
</script>

<style scoped lang="stylus">
.department
    .department-list
        margin: 16px;
        border-radius: 4px;
        background-color #fff
        .department-list-title
            font-size: 18px;
            font-weight: bold;
            color: #333;
            margin-bottom: 24px;
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
                >img
                    width: 5px;
                    margin-left 10px
        .department-list-center
            font-size 15px
            margin-bottom: 20px;
        .department-list-center-left
            color #333
            font-weight bold
            display block
        .department-list-center-right
            color #999
            margin-top 8px
            display: inline-block;
        .ht
            >>>img
                max-width 100%
        .schoolDetail-list-content
          margin-top: 10px;
        .schoolDetail-list-content-left
          color #333
          font-weight bold
        .schoolDetail-list-content-right
          color #999
          margin-top 10px
          >>>img
            max-width 100%
          >>>p
            line-height: 22px;
            margin-bottom: 15px;
</style>
