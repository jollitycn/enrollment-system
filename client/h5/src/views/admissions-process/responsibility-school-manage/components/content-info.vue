<template>
  <div class="content-info">
    <div class="content-info-header" v-if="showTitle">
      <van-cell
        @click="clickHandle"
        :title="title"
        :is-link="isLink"
        :label="label"
        :value-class="valueClass"
        :title-class="titleClass"
      >
        <template #default>
          <span class="test">{{value}}</span>
        </template>
      </van-cell>
    </div>
    <div class="content-info-content">
      <div class="content-info-content-items">
        <div class="content-info-item" v-for="(item,index) in items" :key="index">
          <!-- <div class="content-info-item-label" :style="{'width':labelWidth}">{{item.label}}</div> -->
          <div class="content-info-item-label">{{item.label}}</div>
          <div class="content-info-item-val">{{item.value}}</div>
        </div>
      </div>
    </div>
    <!-- 删除编辑按钮 -->
    <div
      class="preachingPlan-bottom mypreachingPlan-bottom"
      v-if="items[0].name == 'level'&&showTitle"
    >
      <div class="rightBot" v-for="(item, index) in listIcon" :key="index">
        <div :class="[item.colorName]" @click="delOrEdit(item)">{{ item.title }}</div>
      </div>
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import { deleteClass } from "../../../../api/sourceSchool/sourceSchool";
import { Cell, Dialog } from "vant";
Vue.use(Cell);

export default {
  name: "content-info",
  props: {
    items: {
      required: true,
      type: Array,
      default: () => {
        return [];
      }
    },
    classId: {
      type: String,
      default: ""
    },
    schoolId: {
      type: String,
      default: ""
    },
    labelWidth: {
      type: String,
      default: ""
    },
    showTitle: {
      type: Boolean,
      default: true
    },
    title: {
      type: String,
      default: "标题"
    },
    value: [String, Number],
    label: {
      type: String,
      default: ""
    },
    isLink: {
      type: Boolean,
      default: true
    },
    valueClass: {
      type: String,
      default: "content-info-header-tip"
    },
    titleClass: {
      type: String,
      default: "content-info-header-title"
    }
  },
  data() {
    return {
      listIcon: [
        { title: "删除", colorName: "deleteColor" },
        { title: "编辑", colorName: "readColor" }
      ]
    };
  },
  created() {
    console.log(this.items, "==>items");
  },
  methods: {
    clickHandle() {
      this.$emit("click");
    },
    /**
     * 删除或者编辑的点击操作时间
     * @param item{String} 点击按钮对应的信息
     * @author LiuGuoHao
     */
    delOrEdit(item) {
      console.log(item, "==>item");
      if (item.colorName == "deleteColor") {
        this.deleteClass();
      } else {
        this.goEdit();
      }
    },
    /**
     * 删除接口引入
     * @author lgh
     */
    deleteClass() {
      Dialog.confirm({
        title: "提示",
        message: "是否确认要删除该班级？"
      }).then(() => {
        this.$message.loading("正在删除...");
        deleteClass(this.classId)
          .then(() => {
            this.$router.go(0);
            this.$message.close();
            this.$message.success("删除成功！");
          })
          .catch(err => {
            console.error("删除失败：", err);
          });
      });
    },
    /**
     * 编辑班级路由跳转
     * @author lgh
     */
    goEdit() {
      this.$router.push({
        path: `/admissions-process/responsibility-school-manage/school-class-edit/${this.schoolId}/${this.classId}`
      });
    }
  }
};
</script>

<style scoped lang="stylus">
>>>.van-cell__title {
  flex: 1;
}

>>>.van-cell__value {
  flex: 0.25;
}

.content-info {
  &-header {
    margin-bottom: 1pt;

    .test {
      width: 200px;
    }

    &-tip {
      span {
        font-size: 15px;
        font-weight: 400;
        color: rgba(153, 153, 153, 1);
      }
    }

    &-title {
      span {
        font-size: 17px;
        font-weight: 600;
        color: rgba(51, 51, 51, 1);
      }
    }
  }

  &-content {
    margin-bottom: 8px;

    &-items {
      font-size: 15px;
      font-weight: 400;
      line-height: 28px;
      padding: 4px 16px;
      background: rgba(255, 255, 255, 1);

      .content-info-item {
        display: flex;

        &-label {
          width: 110px;
          color: #999999;
        }

        &-val {
          margin-left: 12px;
          flex: 1;
          width: 150px;
          word-break: break-all;
          word-wrap: break-word;
        }
      }
    }
  }
}

.mypreachingPlan-bottom {
  // padding-top: 10px;
  border-top: 1px solid #eee;
}

.preachingPlan-bottom {
  background-color: bgColor;
  display: flex;
  color: fontTitleColor;

  .rightBot {
    border-right: 1px solid #eee;
    padding-top:10px
  }

  >div {
    flex: 1;
    text-align: center;

    >img {
      width: 0.7rem;
      height: 0.7rem;
    }

    >div {
      margin-top: 10px;
      margin-bottom: 20px;
    }
  }

  .deleteColor {
    flex: 1;
    color: deleteColor;
  }

  .readColor {
    color: readColor;
  }
}
</style>