<template>
  <div class="login">
    <div style="position: relative;z-index: 10;padding-top: 20px">
      <div class="login-title">
        当前招生学校为<span
          style="color: #00CCFF;font-size: 18px;font-weight: 700;margin-left: 10px;" >{{ recruitSchoolName }}</span>
      </div>
      <div v-show="isReg" class="login-data">
        <van-form>
          <van-field
            v-model="sourceSchoolName"
            label="所属学校"
            @click="sourceSchoolShowPicker = true"
            placeholder="请选择所属学校"
            disabled
            :right-icon="sourceSchoolShowPicker ? 'arrow-down' : 'arrow'"
          >
          </van-field>
          <van-field
            v-model="className"
            label="所属班级"
            @click="classShowPicker = true"
            placeholder="请选择所属班级"
            disabled
            :right-icon="classShowPicker ? 'arrow-down' : 'arrow'"
          >
          </van-field>
          <van-field
            v-model="studentName"
            label="选择学生"
            @click="showPicker = true"
            placeholder="请选择学生"
            disabled
            :right-icon="showPicker ? 'arrow-down' : 'arrow'"
          >
          </van-field>
        </van-form>
      </div>
    </div>
    <div class="info-button">
      <van-button
        :color="isLoading ? '#B5F0FF' : '#00CCFF'"
        block
        @click="go"
        :loading="isLoading"
        style="font-size: 16px;font-weight: bold"
        loading-text="进行中..."
        >下一步</van-button
      >
    </div>
    <van-popup v-model="sourceSchoolShowPicker" position="bottom">
      <van-picker
        show-toolbar
        value-key="sourceSchoolName"
        :columns="sourceSchoolIdList"
        @cancel="sourceSchoolShowPicker = false"
        @confirm="sourceSchoolonConfirm"
      />
    </van-popup>
    <van-popup v-model="classShowPicker" position="bottom">
      <van-picker
        show-toolbar
        value-key="className"
        :columns="classList"
        @cancel="classShowPicker = false"
        @confirm="classConfirm"
      />
    </van-popup>
    <van-popup v-model="showPicker" position="bottom">
      <van-picker
        show-toolbar
        value-key="studentName"
        :columns="studentList"
        @cancel="showPicker = false"
        @confirm="showPickerConfirm"
      />
    </van-popup>
  </div>
</template>

<script>
import { Field, Picker, Popup, Form, Dialog, Button } from "vant";
import { mapMutations } from "vuex";
import {
  listRecruitSchool,
  listSourceSchoolByRecruit,
  listClassBySource,
  bindRecruitSchoolAndStudent
} from "@/api/login/login";
export default {
  name: "loginActive",
  components: {
    vanField: Field,
    vanPicker: Picker,
    vanPopup: Popup,
    vanForm: Form,
    vanButton: Button,
    [Dialog.Component.name]: Dialog.Component
  },
  data() {
    return {
      isLoading: false,
      showPicker: false,
      recruitSchoolList: [], //招生学校列表
      recruitSchoolName: "", //选中的招生学校名称
      sourceSchoolIdList: [], //生源学校列表
      sourceSchoolName: "", //选中的生源学校名次
      sourceSchoolShowPicker: false,
      classList: [], //班级列表
      className: "", //班级名称
      studentName: "", //学生姓名
      studentList: [], //学生列表
      classShowPicker: false,
      isReg: true,
      data: {
        recruitSchoolId: "",
        openid: "",
        sourceSchoolId: "",
        classId: "",
        studentInfo: ""
      },
      from: {
        classId: 1, // 班级ID
        preachPlanId: "", // 招生宣讲计划ID
        recruitSchoolId: 0, // 招生学校ID
        sourceSchoolId: 0 // 生源学校ID
      }
    };
  },
  beforeRouteLeave(to, from, next) {
    if(this.sourceSchoolShowPicker || this.classShowPicker) {
      this.sourceSchoolShowPicker = false;
      this.classShowPicker = false;
      next(false);
    }else {
      next();
    }
  },
  methods: {
    ...mapMutations(["setUser"]),
    go() {
      if (!this.data.sourceSchoolId) {
        this.$toast("请选择所属学校");
        return;
      }
      if (!this.data.classId) {
        this.$toast("请选择所属班级");
        return;
      }
      if (!this.data.studentInfo) {
        this.$toast("请选择人物");
        return;
      }
      console.log(this.data);
      // if (this.isLoading) {
      //   return;
      // }
      // this.isLoading = true;
      bindRecruitSchoolAndStudent(this.data)
        .then(res => {
          console.log(res);
          this.setUser(res.data);
          this.WXConfig.wxShowMenu();
          setTimeout(() => {
            this.$router.push({
              path: "/"
            });
          }, 200);
        })
        .catch(err => {
          console.log(err);
        });
    },
    //所属学校确认选择
    sourceSchoolonConfirm(item) {
      console.log(item);
      this.sourceSchoolName = item.sourceSchoolName;
      this.data.sourceSchoolId = item.sourceSchoolId;
      this.listClassBySource(item.sourceSchoolId, this.data.recruitSchoolId);
      this.sourceSchoolShowPicker = false;
    },
    //所属班级确认
    classConfirm(item) {
      this.className = item.className;
      this.data.classId = item.classId;
      this.classShowPicker = false;
    },
    //所属学生确认
    showPickerConfirm(item) {
      this.studentName = item.studentName;
      this.data.studentInfo = item.studentInfo;
      this.showPicker = false;
    },
    //获取招生学校列表
    listRecruitSchool() {
      listRecruitSchool().then(res => {
        console.log(res);
        if (res.data && res.data.length) {
          this.recruitSchoolList = res.data;
          for (let i = 0; i < this.recruitSchoolList.length; i++) {
            if (
              this.recruitSchoolList[i].recruitSchoolId ===
              this.data.recruitSchoolId
            ) {
              this.recruitSchoolName = res.data[i].recruitSchoolName;
              this.data.recruitSchoolId = res.data[i].recruitSchoolId;
              this.listSourceSchoolByRecruit(res.data[i].recruitSchoolId);
            }
          }
        }
      });
    },
    //根据所属学校获取对应的生源学校
    listSourceSchoolByRecruit(id) {
      listSourceSchoolByRecruit(id).then(res => {
        console.log(res, "生源学校");
        if (res.data && res.data.length) {
          this.sourceSchoolIdList = res.data;
          this.sourceSchoolName = res.data[0].sourceSchoolName;
          this.data.sourceSchoolId = res.data[0].sourceSchoolId;
          this.listClassBySource(
            res.data[0].sourceSchoolId,
            this.data.recruitSchoolId
          );
        } else {
          this.sourceSchoolName = "";
          this.data.sourceSchoolId = "";
          this.sourceSchoolIdList = [];
          this.classList = [];
          this.className = "";
          this.data.classId = "";
        }
      });
    },
    //根据学校获取对应的班级信息
    listClassBySource(sourceSchoolId, recruitSchoolId) {
      listClassBySource({ sourceSchoolId, recruitSchoolId }).then(res => {
        console.log(res, "班级信息");
        if (res.data && res.data.length) {
          this.className = res.data[0].className;
          this.data.classId = res.data[0].classId;
          this.classList = res.data;
        } else {
          this.className = "";
          this.data.classId = "";
          this.classList = [];
        }
      });
    }
  },
  created() {
    let query = JSON.parse(this.$route.query.data);
    console.log(query);
    this.studentList = query.studentList;
    this.data.recruitSchoolId = query.recruitSchoolId;
    this.data.openid = query.openid;
    this.listRecruitSchool();
  }
};
</script>

<style scoped lang="stylus">
.login
    font-size fontMedSize
    background-color bgColor
    height 100vh
    .login-header
        display flex
        justify-content: space-around
        margin-bottom: 25pt
        >div
            color fontNotActiveColor
            padding: 10px 30px;
            border-radius: 20px;
            flex 1
            border 1px solid notActiveBtnColor
            text-align center
            background-color notActiveBtnColor
            position relative
        .isleft
            left 15px
        .isright
            right 15px
        .isReg
            background-color btnBgColor
            color bgColor
            z-index 9
    .login-title
        color fontTitleColor
        font-size 16px
        font-weight bold
        margin-left: 16pt;
        position: relative;
        top: 16px;
        height: 20px;
        line-height: 20px;
    .login-data
        border-bottom 1px solid #eee
        margin-top: 32pt;
        >>>.van-cell
            padding-left: 16pt
            padding-right: 16pt
            font-size 15px
            display: flex;
            align-items: center;
            height: inputLineHeight
            span
                font-weight bold
        >>>.van-button--default
            color activeBtnColor !important
            font-size 15px
            padding: 0;
            text-align: right;
            span
                font-weight normal
        >>>.van-field__control:disabled
            color: #333333;
        >>>.van-icon-arrow
            font-size: 10px;
            transform: scale(1);
        >>>.van-icon-arrow-down
            font-size: 10px;
            transform: scale(1);
    .login-img
        display flex;
        justify-content space-around
        text-align center
        .login-img-title
            position relative
            .login-img-name
                margin-top 10px
                color fontTitleColor
            .login-icon-n
                position: absolute;
                right: -5px;
                bottom: 30px;
    .info
        margin-top 68px
        margin-bottom 30px

        .info-btn
            color bgColor
    .info-button
        position: absolute;
        width: 100%;
        bottom: 0;
        height: 44px;
        z-index: 8;
        >>>span
            font-weight bold
            position relative
            top 1px
</style>
