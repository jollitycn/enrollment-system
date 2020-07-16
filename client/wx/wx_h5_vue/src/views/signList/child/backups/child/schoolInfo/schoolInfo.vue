<template>
  <div>
    <van-sticky :offset-top="0">
      <h5-header :leftStatus="true" title="在校信息"></h5-header>
    </van-sticky>
    <div class="info">
      <div class="info-con">
        <van-form validate-first>
          <div style="position: relative;z-index: 99;background-color: #fff">
            <van-field
              v-model="from.sourceSchoolName"
              label="所属学校"
              label-class="xx"
              placeholder="请选择毕业学校"
              readonly
              class="activeClass"
              @click="showSchool = true"
              input-align="right"
              :right-icon="img"
            >
            </van-field>
            <van-field
              v-model="from.className"
              label="所属班级"
              label-class="xx"
              placeholder="请选择所属班级"
              readonly
              class="activeClass"
              @click="showClass = true"
              input-align="right"
              :right-icon="img"
            >
            </van-field>
            <van-field
              v-model="from.schoolNo"
              name="pattern"
              label="原学籍号"
              placeholder="请输入原学籍号"
              maxlength="25"
              input-align="right"
            />
            <van-field
              v-model="from.adviser"
              name="adviser"
              label="班主任"
              readonly
              placeholder="请填写班主任"
              maxlength="3"
              input-align="right"
            />
            <div
              style="color: #323233;font-size:0.37333rem;margin-left: 15px;margin-top: 20px;margin-bottom: 10px"
            >
              最近成绩
            </div>
            <van-field
              v-model="chinese"
              style="color: #969799 "
              type="number"
              name="chinese"
              label="语文成绩"
              placeholder="请填写语文成绩"
              maxlength="3"
              input-align="right"
            />
            <van-field
              v-model="mathematics"
              style="color: #969799 "
              type="number"
              name="mathematics"
              label="数学成绩"
              placeholder="请填写数学成绩"
              maxlength="3"
              input-align="right"
            />
            <van-field
              v-model="english"
              style="color: #969799 "
              type="number"
              name="pattern"
              label="英语成绩"
              placeholder="请填写英语成绩"
              input-align="right"
            />
            <van-field
              v-model="from.talent"
              name="talent"
              label="特长"
              placeholder="请填写特长"
              maxlength="30"
              input-align="right"
              style="color: #969799 "
            />
          </div>
          <div class="information-btn">
            <van-button block color="#00CCFF" type="info" @click="submit">
              保存
            </van-button>
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
          <template slot="columns-top">
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
          </template>
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
          <!--          <template slot="columns-top">-->
          <!--            <div>-->
          <!--              <van-search-->
          <!--                v-model="classParams.searchKey"-->
          <!--                show-action-->
          <!--                placeholder="请输入搜索关键词"-->
          <!--                action-text="重置"-->
          <!--                input-align="center"-->
          <!--                @search="onSearchClass"-->
          <!--                @cancel="onCancelClass"-->
          <!--              />-->
          <!--            </div>-->
          <!--          </template>-->
        </van-picker>
      </van-popup>
    </div>
  </div>
</template>

<script>
import { Field, Button, Picker, Popup, Form, Sticky, Search } from "vant";
import {
  listSourceSchoolPage,
  listClassPage,
  getSourceSchoolInfo
} from "@/api/student/student";
import {
  saveOrUpdateInSchoolInfo,
  queryInSchoolInfo
} from "@/api/signList/backups/backups";
import Header from "@/components/cheader";
export default {
  components: {
    vanField: Field,
    vanButton: Button,
    vanPicker: Picker,
    vanPopup: Popup,
    vanForm: Form,
    vanSticky: Sticky,
    vanSearch: Search,
    h5Header: Header
  },
  name: "schoolInfo",
  data() {
    return {
      img: require("../../../../../../assets/image/right.png"),
      //学校信息查询条件
      schoolParams: {
        searchKey: "",
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
      sourceClassList: [], //学校列表
      showClass: false, //是都展示学校列表

      from: {
        sourceSchoolId: "", //生源学校ID
        classId: "", //班级ID
        schoolNo: "", //原学籍号
        adviser: "", //班主任
        talent: "", //特长
        className: "", //班级名称
        sourceSchoolName: "" //生源学校名称
      },
      chinese: "", //语文成绩
      mathematics: "", //数学成绩
      english: "", //英语成绩
      isAdd: true,
      showToast: null,
      isFirstCome: true, //是否第一次进入
      isHasInfo: true //是否有资料
    };
  },
  beforeRouteLeave(to, from, next) {
    if(this.showSchool || this.showClass) {
      this.showSchool = false;
      this.showClass = false;
      next(false);
    }else {
      next();
    }
  },
  methods: {
    submit() {
      if (!this.from.classId) {
        this.$toast("请选择班级");
        return;
      }
      this.showToast = this.$toast.loading({
        duration: 0, // 持续展示 toast
        forbidClick: true,
        message: "提交中"
      });

      let data = [
        {
          course: "语文",
          score: this.chinese || 0
        },
        {
          course: "数学",
          score: this.mathematics || 0
        },
        {
          course: "英语",
          score: this.english || 0
        }
      ];
      //如果不是添加
      if (this.from.scores) {
        for (let i = 0; i < data.length; i++) {
          for (let j = 0; j < this.from.scores.length; j++) {
            if (data[i].course === this.from.scores[j].course) {
              this.from.scores[j].score = data[i].score;
              break;
            }
          }
        }
      } else {
        this.from.scores = data;
      }
      console.log(this.from, 88);
      this.saveOrUpdateInSchoolInfo();
    },

    //选择学校
    onConfirmSchool(item) {
      this.from.sourceSchoolId = item.sourceSchoolId;
      this.from.sourceSchoolName = item.sourceSchoolName;
      this.schoolParams.searchKey = "";
      this.showSchool = false;
      //获取对应学校ID
      this.classParams.sourceSchoolId = item.sourceSchoolId;
      //获取对应学校班级信息
      this.listClassPage();
    },
    //搜索学校
    onSearchSchool() {
      this.listSourceSchoolPage();
    },

    //重置学校
    onCancelSchool() {
      this.schoolParams.searchKey = "";
      this.listSourceSchoolPage();
    },
    //获取学校信息列表
    listSourceSchoolPage(status = true) {
      listSourceSchoolPage(this.schoolParams)
        .then(res => {
          if (res.data.records.length) {
            this.sourceSchoolList = res.data.records;
            //获取对应学校班级信息
            //获取对应学校ID,如果是切换，
            if (status) {
              this.from.sourceSchoolId = res.data.records[0].sourceSchoolId;
              this.from.sourceSchoolName = res.data.records[0].sourceSchoolName;
              this.classParams.sourceSchoolId =
                res.data.records[0].sourceSchoolId;
              this.listClassPage(status);
            } else {
              this.listClassPage(status);
            }
          } else {
            this.sourceSchoolList = [];
            this.from.sourceSchoolId = "";
            this.from.sourceSchoolName = "";
            this.from.classId = "";
            this.from.className = "";
            this.from.adviser = "";
          }
        })
        .catch(err => {
          console.log(err);
        });
    },

    //选择班级
    onConfirmClass(item) {
      console.log(item);
      this.from.classId = item.classId;
      this.from.className = item.className;
      this.from.adviser = item.adviser;
      this.showClass = false;
    },
    //搜索班级
    onSearchClass() {
      this.listClassPage();
    },

    //重置班级
    onCancelClass() {
      this.listClassPage();
    },
    //获取对应学校下的班级信息
    listClassPage(status = true) {
      listClassPage(this.classParams)
        .then(res => {
          if (res.data.records.length) {
            this.sourceClassList = res.data.records;
            if (status) {
              this.from.className = this.sourceClassList[0].className;
              this.from.classId = this.sourceClassList[0].classId;
              this.from.adviser = this.sourceClassList[0].adviser;
            }
          } else {
            this.from.className = "";
            this.from.classId = "";
            this.from.adviser = "";
            this.sourceClassList = [];
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    //新增或修改学生在校信息
    saveOrUpdateInSchoolInfo() {
      saveOrUpdateInSchoolInfo(this.from)
        .then(res => {
          console.log(res);
          this.showToast.clear();
          this.$toast({
            message: "保存成功",
            onClose: () => {
              this.$router.go(-1);
            }
          });
        })
        .catch(err => {
          console.log(err);
        });
    },
    //获取学生信息
    getSourceSchoolInfo() {
      getSourceSchoolInfo()
        .then(res => {
          console.log(res);
          let data = res.data;
          this.from.sourceSchoolId = data.sourceSchoolId; //生源学校ID
          this.from.classId = data.classId; //班级ID
          this.from.adviser = data.adviser; //班主任
          this.from.className = data.className; //班级名称
          this.from.sourceSchoolName = data.sourceSchoolName; //生源学校名称
          this.classParams.sourceSchoolId = data.sourceSchoolId;
          //获取学校列表
          this.listSourceSchoolPage(false);
        })
        .catch(err => {
          console.log(err);
        });
    },
    //查询基本信息
    queryInSchoolInfo() {
      queryInSchoolInfo().then(res => {
        //存在信息则代表修改
        if (res.data) {
          this.isAdd = false;
          this.from = res.data;
          let data = res.data.scores;
          let jsonP = {
            语文: "chinese",
            数学: "mathematics",
            英语: "english"
          };
          for (let i = 0; i < data.length; i++) {
            this[jsonP[data[i].course]] =
              data[i].score == 0 ? "" : data[i].score;
          }
          this.getSourceSchoolInfo(false);
          console.log(res.data);
        } else {
          this.isAdd = true;
          this.getSourceSchoolInfo(false);
        }
      });
    }
  },
  created() {},
  activated() {
    //获取学生基本信息
    this.queryInSchoolInfo();
    this.schoolParams.searchKey = "";
  }
};
</script>

<style scoped lang="stylus">
.info
  font-size fontMedSize
  padding-top 20px
  background bgColor
  min-height calc(100vh - 64px)
  .info-title
    color fontTitleColor
    font-size fontBigSize
    font-weight bold
    margin-bottom: 10px;
    margin-top: 20px;
  .content
    margin-bottom: 40px;
    margin-top: 20px;
    .content-first
      margin-bottom: 10px;
      >span
        display inline-block
      .content-first-one
        width 30%
        color fontMiniFontColor
      .content-first-two
        width 40%
        color fontTitleColor
  .information-btn
    position: fixed;
    bottom: 0;
    width: 100%;
    z-index: 5;
    height 49px
    >>>button
        height 49px
        >span
          font-size 18px
  .info-con
    >>>.xx
      >span
        position relative
        &:after
          position: absolute;
          right: -10px;
          color: #ee0a24;
          font-size: 0.37333rem;
          content: '*';
    >>>.van-cell:not(:last-child)::after
      left 0px
    >>>.van-cell
      display flex
      min-height 55px
      align-items: center;
    >>>img
      width: 6px;
.activeClass
  display flex
  align-items center
  .activeBtn
    border 0
    color activeBtnColor
</style>
