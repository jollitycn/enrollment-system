<template>
  <div class="class-student-info">
    <!-- <content-info
      :is-link="false"
      :title="info.studentName"
      :label="info.studentTelephone"
      value="跟进中"
      @click.native="clickHandle"
      :items="items"
      :labelWidth="labelWidth"
      :value-class="valueClass"
    />-->
    <div class="content-info-header">
      <van-cell @click="clickHandle" :label="info.studentTelephone">
        <template #title>
          <span class="custom-title">{{info.studentName}}</span>
          <span
            v-if="info.gender!=null"
            :class="{'male':!info.gender,'female':!!info.gender}"
            class="user-sex v-middle"
          ></span>
        </template>
        <template #right-icon>
          <!-- <div
            v-if="info.level!=null"
            class="custom-follow"
            :style="{color:level[info.level].color,borderColor:level[info.level].color}"
          >跟进等级:{{level[info.level].title}}</div>-->
          <div
            v-if="info.level!=null"
            class="stu-level"
            :class="{'hight':info.level==0,'middle':info.level==1,'low':info.level==2}"
          >跟进等级：{{level[info.level].title}}</div>
          <div
            v-if="info.status!=null"
            class="custom-right"
            :style="{color:status[info.status].color,background:status[info.status].bagCol}"
          >{{status[info.status].title}}</div>
        </template>
      </van-cell>
    </div>
    <div class="content-parents">
      <div class="content-parents-fat">
        <span class="font-color">{{items[0].label}}</span>
        <span>{{items[0].value}}</span>&nbsp;
        <span>{{items[1].value}}</span>
      </div>
      <div class="content-parents-mot">
        <span class="font-color">{{items[2].label}}</span>
        <span>{{items[2].value}}</span>&nbsp;
        <span>{{items[3].value}}</span>
      </div>
    </div>
    <div class="content-bottom">
      <div class="content-bottom-reason">
        <span class="content-bottom-name">{{items[5].label}}</span>
        <span class="content-bottom-value">{{items[5].value}}</span>
      </div>
      <div class="content-bottom-direction">
        <span class="content-bottom-name">{{items[6].label}}</span>
        <span class="content-bottom-value">{{items[6].value}}</span>
      </div>
    </div>
  </div>
</template>

<script>
// import ContentInfo from "./content-info";
import Vue from "vue";
import { Cell } from "vant";
Vue.use(Cell);
export default {
  name: "class-student-info",
  components: {
    // "content-info": ContentInfo
  },
  props: {
    info: {
      type: Object,
      default: () => {
        return {};
      }
    }
  },
  data() {
    return {
      valueClass: "uncomplate-status",
      valueClassList: ["uncomplate-status", "complate-status"],
      levelList: ["高", "中", "低"],
      labelWidth: "110px",
      items: [
        {
          label: "父亲",
          name: "fatherName",
          value: ""
        },
        {
          label: "联系电话",
          name: "fatherTelephone",
          value: ""
        },
        {
          label: "母亲",
          name: "motherName",
          value: ""
        },
        {
          label: "联系电话",
          name: "motherTelephone",
          value: ""
        },
        {
          label: "跟进等级",
          name: "level",
          value: ""
        },
        {
          label: "意向原因",
          name: "reason",
          value: ""
        },
        {
          label: "跟进方向",
          name: "direction",
          value: ""
        }
      ],
      level: {
        0: { title: "高", color: "#FF4C4CFF" },
        1: { title: "中", color: "#FF9638FF" },
        2: { title: "低", color: "#00CCFFFF" }
      },
      status: {
        0: { title: "已报名", color: "#999999FF", bagCol: "#F2F2F2FF" },
        1: { title: "跟进中", color: "#FE9902FF", bagCol: "#FFF4EBFF" }
      }
    };
  },
  methods: {
    clickHandle() {
      this.$emit("click");
    }
  },
  created() {
    this.items.forEach(item => {
      if (item.name == "level") {
        item.value = this.levelList[this.info[item.name]];
      } else {
        item.value = this.info[item.name];
      }
    });
    console.log(this.items, "====items");
    console.log(this.info, "111111111111");
  }
};
</script>

<style scoped lang="stylus">
>>>.van-cell {
  border-radius: 6px;
}

.class-student-info {
  .content-info {
    &-header {
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
          font-weight: 900;
          color: rgba(51, 51, 51, 1);
        }
      }

      .custom-follow {
        width: 88px;
        height: 22px;
        background: rgba(255, 255, 255, 1);
        border-radius: 4px;
        border: 1px solid rgba(255, 150, 56, 1);
        text-align: center;
      }

      .custom-title {
        font-weight: 600;
        font-size: 17px;
      }

      .stu-level {
        text-align: center;
        font-size: 12px;
        width: 90px;
        border: 1px solid;
        height: 23px;
        line-height: 23px;
        border-radius: 4px;

        &.hight {
          color: #FF4C4C;
        }

        &.middle {
          color: #FF9638;
        }

        &.low {
          color: #00CCFF;
        }
      }

      .custom-right {
        margin-left: 8px;
        width: 52px;
        height: 22px;
        text-align: center;
        background: rgba(255, 244, 235, 1);
        color: #FE9902FF;
      }
    }
  }

  .content-parents {
    width: 311px;
    height: 70px;
    margin-left: 16px;
    margin-top: 12px;
    background: rgba(248, 248, 248, 1);
    border-radius: 8px;

    .content-parents-fat {
      padding-left: 15px;
      padding-top: 12px;
    }

    .content-parents-mot {
      padding-left: 15px;
      padding-top: 12px;
    }

    .font-color {
      color: rgba(153, 153, 153, 1);
      padding-right: 16px;
    }
  }

  .content-bottom {
    margin-top: 12px;
    margin-left: 16px;
    padding-bottom: 8px;
    min-height: 60px;

    .content-bottom-reason {
    }

    .content-bottom-direction {
      margin-top: 10px;
    }

    .content-bottom-name {
      color: rgba(153, 153, 153, 1);
      margin-right: 16px;
    }
  }

  >>>.uncomplate-status {
    span {
      font-size: 15px;
      font-weight: 400;
      color: #FE9902;
    }
  }

  >>>.complate-status {
    span {
      font-size: 15px;
      font-weight: 400;
      color: #13CB77;
    }
  }

  .user-sex {
    display: inline-block;
    width: 20px;
    height: 20px;
    padding: 0 4px;
    background: url('./img/male.png') center center no-repeat;

    &.male {
      background-image: url('./img/male.png');
    }

    &.female {
      background-image: url('./img/female.png');
    }
  }

  .v-middle {
    vertical-align: middle;
  }
}
</style>