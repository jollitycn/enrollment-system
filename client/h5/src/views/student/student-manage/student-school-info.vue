<template>
  <div class="student-school-info">
    <van-sticky :offset-top="0">
      <h5-header :leftStatus="true" title="在校信息"></h5-header>
    </van-sticky>
    <div class="info">
      <div>
        <van-form validate-first @submit="submit">
          <div class="info-title">在校信息</div>
          <van-field
            v-model="from.sourceSchoolName"
            label="毕业学校"
            label-class="xx"
            placeholder="请选择毕业学校"
            disabled
            class="activeClass"
            @click="showSchool = true"
            :rules="[{ required: true, message: '请选择毕业学校' }]"
          >
            <van-icon slot="right-icon" name="arrow" />
          </van-field>
          <van-field
            v-model="from.className"
            label="所属班级"
            label-class="xx"
            placeholder="请选择所属班级"
            disabled
            class="activeClass"
            @click="showClass = true"
            :rules="[{ required: true, message: '请选择所属班级' }]"
          >
            <van-icon slot="right-icon" name="arrow" />
          </van-field>
          <van-field v-model="from.schoolNo" name="pattern" label="原学籍号" placeholder="请输入原学籍号" />
          <van-field
            v-model="from.adviser"
            readonly
            name="adviser"
            label="班主任"
            placeholder="请填写班主任"
          />
          <van-field
            v-model="chinese"
            name="chinese"
            label="语文成绩"
            placeholder="请填写语文成绩"
          />
          <van-field
            v-model="mathematics"
            name="mathematics"
            label="数学成绩"
            placeholder="请填写数学成绩"
          />
          <van-field
            v-model="english"
            name="pattern"
            label="英语成绩"
            placeholder="请填写英语成绩"
          />
          <van-field v-model="from.talent" name="talent" label="特长" placeholder="请填写特长" />
          <div class="student-add-save">
            <button class="action-save-btn" native-type="submit">提交</button>
          </div>
        </van-form>
      </div>

      <van-popup v-model="showSchool" position="bottom">
        <van-picker
          show-toolbar
          :columns="sourceSchoolList"
          value-key="sourceSchoolName"
          @cancel="showSchool = false"
          @confirm="onConfirmSchool"
        >
          <!-- <template slot="columns-top">
            <div>
              <van-search
                v-model="schoolParams.searchKey"
                show-action
                placeholder="请输入搜索关键词"
                action-text="重置"
                input-align="center"
                @search="onSearchSchool"
                @cancel="onCancelSchool"
              />
            </div>
          </template>-->
        </van-picker>
      </van-popup>

      <van-popup v-model="showClass" position="bottom">
        <van-picker
          show-toolbar
          :columns="sourceClassList"
          value-key="className"
          @cancel="showClass = false"
          @confirm="onConfirmClass"
        >
          <!-- <template slot="columns-top">
            <div>
              <van-search
                v-model="classParams.searchKey"
                show-action
                placeholder="请输入搜索关键词"
                action-text="重置"
                input-align="center"
                @search="onSearchClass"
                @cancel="onCancelClass"
              />
            </div>
          </template>-->
        </van-picker>
      </van-popup>
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import { Field, Button, Picker, Popup, Form, Sticky, Search } from "vant";
Vue.use(Field);
Vue.use(Button);
Vue.use(Picker);
Vue.use(Popup);
Vue.use(Form);
Vue.use(Sticky);
Vue.use(Search);
import {
  listSourceSchoolPage,
  listClassPage,
  queryInSchoolInfo,
  saveOrUpdateInSchoolInfo
} from "../../../api/Student-info/Student-info";

import Header from "@/components/cheader";
export default {
  components: {
    "h5-header": Header
  },
  name: "student-school-info",
  data() {
    return {
      studentId: null,
      //学校信息查询条件
      schoolParams: {
        studentInfoId: "",
        pageNum: 1,
        pageSize: 100
      },
      sourceSchoolList: [], //学校列表
      showSchool: false, //是都展示学校列表

      classParams: {
        sourceSchoolId: "",
        pageNum: 1,
        pageSize: 100
      },
      sourceClassList: [], //班级列表
      showClass: false, //是都展示班级列表
      data: 123,
      from: {
        sourceSchoolId: "", //生源学校ID
        classId: "", //班级ID
        schoolNo: "", //原学籍号
        adviser: "", //班主任
        talent: "", //特长
        className: "", //班级名称
        sourceSchoolName: "", //生源学校名称
        scores: [] //成绩
      },
      chinese: "", //语文成绩
      mathematics: "", //数学成绩
      english: "", //英语成绩
      isAdd: true,
      showToast: null
    };
  },
  methods: {
    /**
     * 获取基本信息
     */
    queryInSchoolInfo() {
      this.$message.loading("正在加载...");
      queryInSchoolInfo(this.studentId)
        .then(res => {
          this.$message.close();
          console.log(res, 222333);
          //存在信息则代表修改
          if (res.data) {
            this.isAdd = false;
            this.from = res.data;
            if (res.data.sourceSchoolId) {
              this.classParams.sourceSchoolId = res.data.sourceSchoolId;
              this.listClassPage(true);
            }
            let data = res.data.scores;
            let jsonP = {
              语文: "chinese",
              数学: "mathematics",
              英语: "english"
            };
            for (let i = 0; i < data.length; i++) {
              if(data[i].score){
                this[jsonP[data[i].course]] = data[i].score;
              }else{
                this[jsonP[data[i].course]] = ""
              }
            }
          }
        })
        .catch(err => {
          console.error("加载失败：", err);
          this.$message.close();
        });
    },
    /**
     * 编辑保存
     */
    submit(errorInfo) {
      console.log("errorInfo:", errorInfo);
      this.showToast = this.$toast.loading({
        duration: 0, // 持续展示 toast
        forbidClick: true,
        message: "提交中"
      });
      // this.from.classId = 1;
      // this.from.scores = data;
      //如果不是添加
      // if (!this.isAdd) {
      let data = [
        {
          course: "语文",
          score: this.chinese
        },
        {
          course: "数学",
          score: this.mathematics
        },
        {
          course: "英语",
          score: this.english
        }
      ];
      // for (let i = 0; i < data.length; i++) {
      //   for (let j = 0; j < this.from.scores.length; j++) {
      //     if (data[i].course === this.from.scores[j].course) {
      //       this.from.scores[j].score = data[i].score;
      //       break;
      //     }
      //   }
      //   // }
      // }
      this.from.scores = data;
      console.log(this.from, 22222);
      this.saveOrUpdateInSchoolInfo();
    },

    /**
     * 选择学校
     */
    onConfirmSchool(item) {
      this.from.sourceSchoolId = item.sourceSchoolId;
      this.from.sourceSchoolName = item.sourceSchoolName;
      this.schoolParams.searchKey = "";
      this.showSchool = false;
      //获取对应学校ID
      this.classParams.sourceSchoolId = item.sourceSchoolId;
      // //获取对应学校班级信息
      this.listClassPage();
    },
    //搜索学校
    onSearchSchool(val) {
      console.log("搜索学校:", val);
      this.listSourceSchoolPage(this.studentId);
    },

    //重置学校
    onCancelSchool() {
      this.schoolParams.searchKey = "";
      this.listSourceSchoolPage(this.studentId);
    },
    /**
     * 获取学校列表
     */
    listSourceSchoolPage() {
      this.$message.loading("正在加载学校列表...");
      listSourceSchoolPage(this.schoolParams)
        .then(res => {
          this.sourceSchoolList = res.data;
          //获取学生基本信息
          this.queryInSchoolInfo();
          this.$message.close();
          console.log(res.data, 99);
          // 判断是否有学校
          if (res.data.length) {
            this.classParams.sourceSchoolId =
              res.data.records[0].sourceSchoolId;
            this.listClassPage();
          } else {
            this.classParams.sourceSchoolId = "";
          }
        })
        .catch(err => {
          console.log(err);
        });
    },

    /**
     * 选择班级
     */
    onConfirmClass(item) {
      this.from.classId = item.classId;
      this.from.className = item.className;
      this.classParams.searchKey = "";
      this.from.adviser = item.adviser;
      this.showClass = false;
    },
    //搜索学校
    onSearchClass(val) {
      this.listClassPage();
      console.log(val);
    },

    //重置班级
    onCancelClass() {
      this.classParams.searchKey = "";
      this.listClassPage();
    },
    /**
     * 获取对应学校下的班级信息
     * @param {boolean}isShow 用于判断是否为获取详情时的班级信息
     */
    listClassPage(isShow = false) {
      this.$message.loading("正在加载班级列表...");
      listClassPage(this.classParams)
        .then(res => {
          this.$message.close();
          this.sourceClassList = res.data.records;
          console.log(this.sourceClassList, 222);
          if (!isShow) {
            if (this.sourceClassList.length) {
              this.from.classId = this.sourceClassList[0].classId;
              this.from.className = this.sourceClassList[0].className;
              this.from.adviser = this.sourceClassList[0].adviser;
            } else {
              this.from.classId = "";
              this.from.className = "";
              this.from.adviser = "";
            }
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    /**
     * 新增或修改学生在校信息
     */
    saveOrUpdateInSchoolInfo() {
      this.from.studentInfo = this.studentId;
      saveOrUpdateInSchoolInfo(this.from)
        .then(res => {
          console.log(res);
          this.showToast.clear();
          this.$message.success("提交成功");
          let id = this.studentId;
          this.$router.push({
            path: `/student-manage/student-detail/${id}`
          });
        })
        .catch(err => {
          console.log(err);
        });
    }
  },
  created() {
    this.studentId = this.$route.params.id;
    //获取学校列表
    this.listSourceSchoolPage();
  }
};
</script>

<style scoped lang="stylus">
>>>.xx {
  >span {
    position: relative;

    &:after {
      position: absolute;
      right: -10px;
      color: #ee0a24;
      font-size: 0.37333rem;
      content: '*';
    }
  }
}

.info {
  font-size: fontMedSize;
  padding: 20px;
  background: bgColor;

  .info-title {
    color: fontTitleColor;
    font-size: fontBigSize;
    font-weight: bold;
    margin-bottom: 10px;
    margin-top: 20px;
  }

  .content {
    margin-bottom: 40px;
    margin-top: 20px;

    .content-first {
      margin-bottom: 10px;

      >span {
        display: inline-block;
      }

      .content-first-one {
        width: 30%;
        color: fontMiniFontColor;
      }

      .content-first-two {
        width: 40%;
        color: fontTitleColor;
      }
    }
  }
}

.activeClass {
  display: flex;
  align-items: center;

  .activeBtn {
    border: 0;
    color: activeBtnColor;
  }
}

.student-add-save {
  margin-top: 50px;

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

>>>.van-field__control:disabled {
  color: #323233;
}
</style>
