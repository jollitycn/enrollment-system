<template>
  <div class="binging">
    <h5-header :leftStatus="true" title="我的绑定"></h5-header>
    <div class="binging-from">
      <van-form>
        <van-field
          v-model="recruitSchoolName"
          label="招生学校"
          @click="showPicker = true"
          placeholder="请选择招生学校"
          readonly
          :right-icon="img"
          label-class="xx"
        >
        </van-field>
        <van-field
          v-model="sourceSchoolName"
          label="所属学校"
          @click="sourceSchoolShowPicker = true"
          placeholder="请选择所属学校"
          readonly
          label-class="xx"
          :right-icon="img"
        >
        </van-field>
        <van-field
          v-model="className"
          label="所属班级"
          @click="classShowPicker = true"
          placeholder="请选择所属班级"
          readonly
          label-class="xx"
          :right-icon="img"
        >
        </van-field>
        <van-field
          v-model="from.studentName"
          :rules="rules.studentName"
          label-class="xx"
          label="学生姓名"
          placeholder="填写学生姓名"
          maxlength="4"
        />
      </van-form>
      <div class="btn">
        <van-button
          type="primary"
          color="#00CCFF"
          block
          @click="checkBindStudent"
          >绑定</van-button
        >
      </div>
    </div>
    <van-popup v-model="showPicker" position="bottom">
      <van-picker
        show-toolbar
        value-key="recruitSchoolName"
        :columns="recruitSchoolList"
        @cancel="showPicker = false"
        @confirm="onConfirm"
      />
    </van-popup>
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
  </div>
</template>

<script>
import Header from "@/components/cheader";
import { Field, Picker, Popup, Form, Button, Dialog } from "vant";
import {
  listRecruitSchool,
  listSourceSchoolByRecruit,
  listClassBySource
} from "@/api/login/login";
import { checkBindStudent, addNewStudent, bindStudent } from "@/api/user/user";
import { mapState, mapMutations } from "vuex";
export default {
  name: "binding",
  components: {
    h5Header: Header,
    vanField: Field,
    vanPicker: Picker,
    vanPopup: Popup,
    vanForm: Form,
    vanButton: Button
  },
  data() {
    return {
      img: require("../../../../assets/image/right.png"),
      showPicker: false,
      pattern: /\d{10}/,
      recruitSchoolList: [], //招生学校列表
      recruitSchoolName: "", //选中的招生学校名称
      sourceSchoolIdList: [], //生源学校列表
      sourceSchoolName: "", //选中的生源学校名次
      sourceSchoolShowPicker: false,
      classList: [], //班级列表
      className: "", //班级名称
      classShowPicker: false,
      from: {
        classId: 1, // 班级ID
        recruitSchoolId: 0, // 招生学校ID
        sourceSchoolId: 0, // 生源学校ID
        studentName: "" // 学生姓名
      },
      rules: {
        studentName: [
          { validator: this.isStudentName, message: "请输入1-4个字符" }
        ]
      }
    };
  },
  computed: {
    ...mapState(["studentList"])
  },
  beforeRouteLeave(to, from, next) {
    if(this.showPicker || this.sourceSchoolShowPicker || this.classShowPicker) {
      this.showPicker = false;
      this.sourceSchoolShowPicker = false;
      this.classShowPicker = false;
      next(false);
    }else {
      next();
    }
  },
  methods: {
    //学校姓名校验
    isStudentName(val) {
      return val.length < 11 && val.length > 0;
    },
    ...mapMutations(["setStudentList"]),
    //获取招生学校列表
    listRecruitSchool() {
      listRecruitSchool().then(res => {
        console.log(res);
        if (res.data && res.data.length) {
          this.recruitSchoolList = res.data;
          this.recruitSchoolName = res.data[0].recruitSchoolName;
          this.from.recruitSchoolId = res.data[0].recruitSchoolId;
          this.listSourceSchoolByRecruit(res.data[0].recruitSchoolId);
        } else {
          this.recruitSchoolList = [];
          this.recruitSchoolName = "";
          this.from.recruitSchoolId = "";
        }
      });
    },
    //根据招生学校获取对应的生源学校
    listSourceSchoolByRecruit(id) {
      listSourceSchoolByRecruit(id).then(res => {
        console.log(res, "生源学校");
        if (res.data && res.data.length) {
          this.sourceSchoolIdList = res.data;
          this.sourceSchoolName = res.data[0].sourceSchoolName;
          this.from.sourceSchoolId = res.data[0].sourceSchoolId;
          this.listClassBySource(
            res.data[0].sourceSchoolId,
            this.from.recruitSchoolId
          );
        } else {
          this.sourceSchoolName = "";
          this.from.sourceSchoolId = "";
          this.sourceSchoolIdList = [];
          this.classList = [];
          this.className = "";
          this.from.classId = "";
        }
      });
    },
    //根据学校获取对应的班级信息
    listClassBySource(sourceSchoolId, recruitSchoolId) {
      listClassBySource({ sourceSchoolId, recruitSchoolId }).then(res => {
        console.log(res, "班级信息");
        if (res.data && res.data.length) {
          this.classList = res.data;
          this.className = res.data[0].className;
          this.from.classId = res.data[0].classId;
        } else {
          this.classList = [];
          this.className = "";
          this.from.classId = "";
        }
      });
    },
    //招生学校确认选择
    onConfirm(item) {
      console.log(item);
      this.from.recruitSchoolId = item.recruitSchoolId;
      this.recruitSchoolName = item.recruitSchoolName;
      this.listSourceSchoolByRecruit(item.recruitSchoolId);
      this.showPicker = false;
    },
    //所属学校确认选择
    sourceSchoolonConfirm(item) {
      console.log(item);
      this.sourceSchoolName = item.sourceSchoolName;
      this.from.sourceSchoolId = item.sourceSchoolId;
      this.listClassBySource(item.sourceSchoolId, this.from.recruitSchoolId);
      this.sourceSchoolShowPicker = false;
    },
    //所属班级确认
    classConfirm(item) {
      this.className = item.className;
      this.from.classId = item.classId;
      this.classShowPicker = false;
    },
    //绑定前校验操作
    checkBindStudent() {
      if (!this.recruitSchoolName) {
        this.$toast("请选择招生学校");
        return;
      }
      if (!this.sourceSchoolName) {
        this.$toast("请选择所属学校");
        return;
      }
      if (!this.className) {
        this.$toast("请选择所属班级");
        return;
      }
      if (!this.from.studentName) {
        this.$toast("请输入学生姓名");
        return;
      }
      checkBindStudent(this.from)
        .then(res => {
          console.log(res);
        })
        .catch(err => {
          console.log(err.data.retCode);
          this.isCreatNewInfo(err.data.message, err.data.retCode);
        });
    },
    //将会新建一个学生帐号，是否继续
    isCreatNewInfo(message, code) {
      Dialog.confirm({
        title: "温馨提示",
        message: message
      })
        .then(() => {
          if (code === 600007) {
            this.addNewStudent();
          }
          if (code === 600011) {
            this.bindStudent();
          }
        })
        .catch(() => {
          // on cancel
        });
    },
    //新增操作
    addNewStudent() {
      addNewStudent(this.from).then(res => {
        console.log(res);
        let status = this.studentList;
        let statusList = [...status, res.data];
        this.setStudentList(statusList);
        this.$toast({
          message: "绑定成功",
          onClose: () => {
            this.$router.push({
              path: "/user"
            });
          }
        });
      });
    },
    //绑定操作
    bindStudent() {
      bindStudent(this.from).then(res => {
        console.log(res);
        let status = this.studentList;
        let statusList = [...status, res.data];
        this.setStudentList(statusList);
        this.$toast({
          message: "绑定成功",
          onClose: () => {
            this.$router.push({
              path: "/user"
            });
          }
        });
      });
    }
  },
  created() {
    this.listRecruitSchool();
  }
};
</script>

<style scoped lang="stylus">
.binging
  .btn
    background-color bgColor
    position: fixed;
    bottom: 0;
    width: 100%;
    >>>span
        font-size 16px
  >>>.van-icon-arrow
    font-size: 10px;
    transform: scale(1);
  >>>.van-icon-arrow-down
    font-size: 10px;
    transform: scale(1);
  .binging-from
    >>>.van-cell
          display: flex;
          align-items: center;
          height: 55px;
    >>>img
          width: 6px;
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
</style>
