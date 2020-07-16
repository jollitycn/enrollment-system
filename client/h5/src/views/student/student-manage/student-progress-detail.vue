<template>
  <div>
    <h5-header :leftStatus="true" title="进度查看"></h5-header>
    <van-cell
      @click="clickHandle"
      title="当前状态"
      :is-link="true"
      :value="statusName"
      value-class="cell-tip"
      title-class="cell-title"
    />
    <div class="item">
      <van-steps direction="vertical" :active="active">
        <van-step v-for="(item,index) in list" :key="item">
          <h3 :class="index <= active ? 'isColor': 'notColor'">{{ item }}</h3>
          <p v-show="item2.progressType === index" :class="index <= active ? 'isColorMin': ''" v-for="item2 in data" :key="item2.progressContent">{{item2.createTime +' '+item2.progressContent}}</p>
        </van-step>
      </van-steps>
    </div>
<!--    之前进度样式s-->
<!--    <div class="item">-->
<!--      <van-steps active-color="#00CCFF" direction="vertical" :active="active">-->
<!--        <van-step v-for="(item,index) in list" :key="item" >-->
<!--          <h3 class="step-title" :class="{active:index == active}">{{ item }}</h3>-->
<!--          <p-->
<!--            class="step-sub-title"-->
<!--            v-show="item2.progressType === index && item2.progressType <=active"-->
<!--            v-for="(item2,index2) in data"-->
<!--            :key="`${index}+${index2}`"-->
<!--          >{{item2.createTime}}&emsp;{{item2.progressContent}}</p>-->
<!--        </van-step>-->
<!--      </van-steps>-->
<!--    </div>-->
    <!--    之前进度样式e-->
    <!-- 当前状态 -->
    <van-popup v-model="showPopup" position="bottom">
      <van-picker show-toolbar :columns="columns" @cancel="showPopup = false" @confirm="onConfirm" />
    </van-popup>
  </div>
</template>

<script>
import {
  getProgress,
  getRegisterStatus,
  changeStatus
} from "../../../api/Student-info/student-progress-detail";
import Vue from "vue";
import { Step, Steps, Cell, Popup, Picker } from "vant";
Vue.use(Step);
Vue.use(Steps);
Vue.use(Cell);
Vue.use(Popup);
Vue.use(Picker);

import Header from "@/components/cheader";
export default {
  name: "student-progress-detail",
  components: {
    "h5-header": Header
  },
  data() {
    return {
      studentId: null,
      relationId: null,
      statusName: "",
      changeStatusQuery: {
        status: "",
        studentPublicRelationId: ""
      },
      showPopup: false,
      columns: [
        { id: 0, text: "已交订金" },
        { id: 1, text: "已交全款" },
        { id: 2, text: "已退款" },
        { id: 3, text: "强意向生" },
        { id: 4, text: "已报名" }
      ],
      active: 0,
      list: ["报名登记", "交费定位", "接收通知", "入学报名"],
      data: []
    };
  },
  methods: {
    clickHandle() {
      if (this.statusName != "已报名") {
        this.showPopup = true;
      }
    },
    /**
     * 根据当前状态ID获取当前状态名称
     * @param groupId{String} 学生所属区域ID
     * @author LGH
     */
    getNameById(id) {
      var obj = this.columns.find(function(x) {
        return x.id == id;
      });
      return obj.text;
    },
    /**
     * 当前状态点击事件处理
     * @param value 点击的事件对应的数据
     * @author LGH
     */
    onConfirm(value) {
      this.changeStatusQuery.status = value.id;
      this.statusName = value.text;
      this.$message.loading("正在修改状态...");
      changeStatus(this.changeStatusQuery).then(() => {
        this.$message.close();
        this.$message.success("修改状态成功！");
        this.getProgress(this.changeStatusQuery.studentPublicRelationId);
        this.getRegisterStatus(this.changeStatusQuery.studentPublicRelationId);
        this.showPopup = false;
      });
    },
    /**
     * 根据学生公关ID查询学生招生渠道信息
     * @param {number} studentInfoId 学生的公关ID
     * @author lgh
     */
    getProgress(studentInfoId) {
      this.$message.loading("正在加载...");
      getProgress(studentInfoId)
        .then(res => {
          // 之前进度s
          // this.$message.close();
          // //获取progressType中的最大值
          // this.active = Math.max.apply(
          //   Math,
          //   res.data.map(item => {
          //     return item.progressType;
          //   })
          // );
          // this.data = res.data;
          // 之前进度e
          if(res.data.length) {
            this.data = res.data;
            this.active = res.data[res.data.length - 1].progressType
          }
        })
        .catch(err => {
          console.error("加载失败：", err);
        });
    },
    /**
     * 根据学生公关ID查询当前状态信息
     * @param {number} studentInfoId 学生的公关ID
     * @author lgh
     */
    getRegisterStatus(studentInfoId) {
      this.$message.loading("正在加载...");
      getRegisterStatus(studentInfoId)
        .then(res => {
          this.$message.close();
          this.changeStatusQuery.status = res.data;
          this.statusName = this.getNameById(res.data);
          switch (res.data) {
            case 0:
              this.columns = [
                { id: 1, text: "已交全款" },
                { id: 2, text: "已退款" },
                { id: 4, text: "已报名" }
              ];
              break;
            case 1:
              this.columns = [
                { id: 2, text: "已退款" },
                { id: 4, text: "已报名" }
              ];
              break;
            case 2:
              this.columns = [
                { id: 0, text: "已交订金" },
                { id: 1, text: "已交全款" }
              ];
              break;
            case 3:
              this.columns = [
                { id: 0, text: "已交订金" },
                { id: 1, text: "已交全款" }
              ];
              break;
            case 4:
              this.columns = [];
              break;
          }
          // let i = 0;
          // for (i in this.columns) {
          //   if (this.columns[i] == "") {
          //     this.columns[i] = this.columns[i].remove();
          //   }
          // }
        })
        .catch(err => {
          console.error("加载失败：", err);
        });
    }
  },
  created() {
    this.studentId = this.$route.params.id;
    this.changeStatusQuery.studentPublicRelationId = this.$route.params.relationId;
    this.getProgress(this.changeStatusQuery.studentPublicRelationId);
    this.getRegisterStatus(this.changeStatusQuery.studentPublicRelationId);
  }
};
</script>

<style scoped lang="stylus">
.cell-tip {
  span {
    font-size: 15px;
    font-weight: 400;
    color: rgba(153, 153, 153, 1);
  }
}

.cell-title {
  span {
    font-size: 17px;
    font-weight: 600;
    color: rgba(51, 51, 51, 1);
  }
}

  .item
    padding-left: 24px;
    background-color: #fff;
    padding-top: 50px;
    .isColor
      color btnBgColor
      position relative
      left 8px
    .notColor
      position relative
      left 8px
    .isColorMin
      color btnBgColor
      margin-top 15px
      margin-bottom 20px
    >>>.van-step__icon--active
      color: #00ccff;
    >>>.van-icon
      font-size 20px

  .step-sub-title
    margin-top: 15px;
    margin-bottom: 20px;
    color: #999999;
</style>
