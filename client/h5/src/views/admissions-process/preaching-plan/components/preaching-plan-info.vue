<template>
  <div>
    <div class="evaluate">
      <div class="evaluate-title">{{list.title}}</div>
      <div class="evaluate-header">
        <div class="evaluate-header-list">
          <span class="evaluate-header-list-left">宣讲时间：</span>
          <span class="evaluate-header-list-right">{{list.preachTime}}</span>
        </div>
        <div class="evaluate-header-list">
          <span class="evaluate-header-list-left">宣讲地点：</span>
          <span class="evaluate-header-list-right">{{list.address}}</span>
        </div>
        <div class="evaluate-header-list">
          <span class="evaluate-header-list-left">主讲人：</span>
          <span class="evaluate-header-list-right">{{list.speakerName}}</span>
        </div>
        <div class="evaluate-header-list">
          <span class="evaluate-header-list-left">联系手机：</span>
          <span class="evaluate-header-list-right">{{list.telephone}}</span>
        </div>
        <div class="evaluate-header-list">
          <span class="evaluate-header-list-left">计划人数：</span>
          <span class="evaluate-header-list-right">{{list.planAmount}}</span>
        </div>
      </div>
      <div class="evaluate-school">
        <div class="evaluate-school-title">面向学校</div>
        <div class="evaluate-school-name">{{sourceSchool}}</div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "preachingPlanInfo",
  components: {},
  data() {
    return {
      radio: "1",
      message: "",
      sourceSchool: ""
    };
  },
  props: {
    status: { type: String, default: "" },
    list: { type: Object, default: Object },
    sourceSchoolList: { type: Array, default: Array }
  },
  created() {
    this.nullSchool();
  },
  methods: {
    /**
     * 根据面向学校ID获取面向学校名称
     * @author LiuGuoHao
     */
    nullSchool() {
      if (
        this.list.sourceSchoolIds == null ||
        this.list.sourceSchoolIds.length === 0
      ) {
        this.sourceSchool = "无面向学校";
      } else {
        for (let i = 0; i <= this.list.sourceSchoolIds.length; i++) {
          if (this.list.sourceSchoolIds[i] != null) {
            //根据sourceSchoolId获取对应的名称
            let obj = this.getSchoolNameById(this.list.sourceSchoolIds[i]);
            //在sourceSchool后添加查询出来的名称
            this.sourceSchool = this.sourceSchool + "，" + obj;
          }
        }
        //去除因为for循环操作而在最前面错加的“，”
        if (this.sourceSchool.substr(0, 1) == "，") {
          this.sourceSchool = this.sourceSchool.substr(1);
        }
      }
    },
    /**
     * 根据面向学校ID获取学校名称
     * @param sourceSchoolId{String} 面向学校ID
     * @author LiuGuoHao
     */
    getSchoolNameById(sourceSchoolId) {
      var obj = this.sourceSchoolList.find(function(x) {
        return x.sourceSchoolId == sourceSchoolId;
      });
      return obj.sourceSchoolName;
    }
  }
};
</script>

<style scoped lang="stylus">
.evaluate {
  padding: 20px;
  padding-bottom: 10px;
  border-top: 1px solid #eee;
  background-color: bgColor;

  .evaluate-title {
    font-size: 21px;
    color: fontTitleColor;
    margin-bottom: 32px;
    font-weight: 900;
  }

  .evaluate-header {
    .evaluate-header-list {
      display: flex;
      margin-bottom: 10px;

      .evaluate-header-list-left {
        font-weight: 400;
        font-size: 15px;
        color: rgba(153, 153, 153, 1);
        min-width: 100px;
      }

      .evaluate-header-list-right {
        font-weight: 400;
        font-size: 15px;
        color: rgba(51, 51, 51, 1);
      }
    }
  }

  .evaluate-school {
    padding-bottom: 8px;
    border-radius: 6px;
    // margin-bottom: 20px;
    background-color: #F8F8F8;

    .evaluate-school-title {
      font-size: 15px;
      color: fontTitleColor;
      margin-top: 20px;
      font-weight: 900;
      padding-left: 10px;
      padding-top: 10px;
    }

    .evaluate-school-name {
      font-size: 15px;
      color: rgba(153, 153, 153, 1);
      margin-left: 10px;
      margin-top: 10px;
    }
  }
}
</style>
