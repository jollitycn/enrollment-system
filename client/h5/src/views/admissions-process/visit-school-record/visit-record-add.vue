<template>
  <div class="visit-school-add">
    <!-- <div class="add-title">
      <span class="add-title-left" @click="emitLeft">取消</span>
      <div class="add-title-mid">新增拜访记录</div>
    </div>-->
    <van-sticky :offset-top="0">
      <h5-header :leftStatus="true" :title="title"></h5-header>
    </van-sticky>
    <div class="add-form">
      <van-form
        validate-first
        input-align="right"
        error-message-align="right"
        @submit="addOrEditGroup"
      >
        <!-- 学校名称 -->
        <van-field
          readonly
          clickable
          name="picker"
          label-class="xx"
          :value="form.sourceSchoolName"
          label="学校名称"
          placeholder="请选择学校"
          @click="clickFrame('sourceSchoolName')"
          :rules="rules.schoolRules"
        >
          <van-icon slot="right-icon" name="arrow" />
        </van-field>
        <van-popup v-model="showPicker" position="bottom">
          <van-picker
            show-toolbar
            :columns="schoolList"
            @confirm="onConfirm"
            @cancel="showPicker = false"
          />
        </van-popup>
        <!-- 沟通人 -->
        <van-field
          v-model.trim="form.contacter"
          label-class="xx"
          name="沟通人"
          label="沟通人"
          maxlength="10"
          :rules="rules.contacter"
          placeholder="请输入沟通人姓名"
        />
        <!-- 联系方式 -->
        <van-field
          v-model.trim="form.telephone"
          label-class="xx"
          name="联系方式"
          label="联系方式"
          maxlength="11"
          :rules="rules.telephone"
          placeholder="请输入联系手机"
        />
        <!-- 开始时间 -->
        <van-field
          readonly
          clickable
          label-class="xx"
          name="picker"
          :value="form.startTime"
          label="开始时间"
          placeholder="请选择"
          :rules="rules.startTime"
          @click="clickFrame('startTime')"
        >
          <van-icon slot="right-icon" name="arrow" />
        </van-field>
        <van-popup v-model="showStartTime" position="bottom">
          <van-datetime-picker
            v-model="startTime"
            type="datetime"
            title="请选择开始时间"
            @confirm="onConfirm"
            @cancel="showStartTime = false"
            :formatter="formatter"
          />
        </van-popup>
        <!-- 结束时间 -->
        <van-field
          readonly
          clickable
          name="picker"
          :value="form.endTime"
          label-class="xx"
          label="结束时间"
          :rules="rules.endTime"
          placeholder="请选择"
          @click="clickFrame('endTime')"
        >
          <van-icon slot="right-icon" name="arrow" />
        </van-field>
        <van-popup v-model="showEndTime" position="bottom">
          <van-datetime-picker
            type="datetime"
            v-model="endTime"
            title="请选择结束时间"
            @confirm="onConfirm"
            @cancel="showEndTime = false"
            :formatter="formatter"
          />
        </van-popup>
        <!-- 拜访内容 -->
        <van-field
          class="add-form-record"
          v-model="form.content"
          rows="3"
          label="拜访内容"
          type="textarea"
          label-class="xx"
          :rules="rules.content"
          maxlength="80"
        />
        <div class="add-form-save">
          <button class="action-save-btn" type="submit">提交</button>
        </div>
      </van-form>
    </div>
  </div>
</template>

<script>
import rules from "@/assets/rules/rulesFrom";
import {
  updateGroupCount,
  addGroupCount,
  getGroupCount
} from "../../../api/visit-school-record/visit-school-record";
import { listAllSourceSchoolGroup } from "@/api/admissions-process/preaching-plan";
import Vue from "vue";
import { dateFormat } from "../../../utils/format.js";
import Header from "@/components/cheader";
import {
  Sticky,
  Form,
  Field,
  Popup,
  Picker,
  Button,
  Icon,
  DatetimePicker,
  Toast
} from "vant";

Vue.use(Sticky);
Vue.use(Icon);
Vue.use(Button);
Vue.use(Popup);
Vue.use(Form);
Vue.use(Field);
Vue.use(Picker);
Vue.use(DatetimePicker);
Vue.use(Toast);

export default {
  name: "visit-record-add",
  components: {
    "h5-header": Header
  },
  data() {
    return {
      title: "",
      startTime: new Date(),
      endTime: new Date(),
      form: {
        contacter: "",
        content: "",
        // endTime:'',
        endTime: dateFormat(new Date(), "YYYY-mm-dd HH:MM:SS"),
        groupId: 0,
        groupSourceRelationId: 0,
        publicRelationId: 0,
        sourceSchoolId: 0,
        // startTime:'',
        startTime: dateFormat(new Date(), "YYYY-mm-dd HH:MM:SS"),
        telephone: ""
      },
      rules: {
        schoolRules: [{ required: true, message: "请选择学校" }],
        contacter: [{ required: true, message: "请输入沟通人" }],
        telephone: [
          {
            pattern: rules.phone.rules,
            message: rules.phone.message
          }
        ],
        startTime: [{ required: true, message: "请选择开始时间" }],
        endTime: [{ required: true, message: "请选择结束时间" }],
        content: [{ required: true, message: "请输入拜访内容" }]
      },
      schoolList: [],
      choseName: "", //在弹框时用于判断点击的是哪一个框
      showPicker: false,
      showStartTime: false,
      showEndTime: false,
      publicRelation: ""
    };
  },
  created() {
    console.log(this.$route.query.sourceSchoolId);
    this.listAllSourceSchoolGroup();
  },
  methods: {
    /**
     * 获取招生学校下的所有的生源学校
     * @author LGH
     */
    listAllSourceSchoolGroup() {
      listAllSourceSchoolGroup().then(res => {
        this.schoolList = res.data;
        //使用正则表达式全局替换所需要的字段
        this.schoolList = JSON.parse(
          JSON.stringify(this.schoolList).replace(/sourceSchoolName/g, "text")
        );
        this.publicRelation = this.$route.params.publicRelation;
        this.addOrEdit(this.publicRelation);
      });
    },
    /**
     * 新增还是编辑判断
     * @author LGH
     */
    addOrEdit(publicRelation) {
      if (publicRelation) {
        this.title = "修改拜访记录";
        this.getGroupCount(publicRelation);
      } else {
        if (this.$route.query.sourceSchoolId) {
          this.form.sourceSchoolId = this.$route.query.sourceSchoolId;
          this.form["sourceSchoolName"] = this.getSchoolNameById(
            this.form.sourceSchoolId
          );
        } else {
          this.title = "新增拜访记录";
          this.form["sourceSchoolName"] = this.schoolList[0].text;
          this.form.sourceSchoolId = this.schoolList[0].sourceSchoolId;
        }
      }
    },
    /**
     * 获取公关学校详情
     * @param id{int} 公关学校ID
     * @author LGH
     */
    getGroupCount(id) {
      getGroupCount(id).then(res => {
        this.$message.loading("加载中...");
        this.form = res.data;
        this.startTime = new Date(this.form.startTime);
        this.endTime = new Date(this.form.endTime);
        this.form.sourceSchoolName = this.getSchoolNameById(
          this.form.sourceSchoolId
        );
        this.$message.close();
      });
    },
    /**
     * 各个选择器赋值方法封装
     * @param value{String} 各个选择器选中的数据
     * @author LGH
     */
    onConfirm(value) {
      if (this.choseName == "startTime" || this.choseName == "endTime") {
        value = dateFormat(value, "YYYY-mm-dd HH:MM:SS");
      }
      if (this.choseName == "sourceSchoolName") {
        this.form[this.choseName] = value.text;
        this.form.sourceSchoolId = value.sourceSchoolId;
      } else {
        this.form[this.choseName] = value;
      }
      switch (this.choseName) {
        case "sourceSchoolName":
          this.showPicker = false;
          break;
        case "startTime":
          this.showStartTime = false;
          break;
        case "endTime":
          this.showEndTime = false;
          break;
      }
    },
    /**
     * 点击选择时弹出框的过程中的操作
     * @param name{String} 用于判断目前点击的是哪一个弹框
     */
    clickFrame(name) {
      this.choseName = name;
      switch (name) {
        case "sourceSchoolName":
          this.showPicker = true;
          break;
        case "startTime":
          this.showStartTime = true;
          break;
        case "endTime":
          this.showEndTime = true;
          break;
      }
    },
    /**
     * 根据面向学校ID获取学校名称
     * @param sourceSchoolId{String} 面向学校ID
     * @author LiuGuoHao
     */
    getSchoolNameById(sourceSchoolId) {
      var obj = this.schoolList.find(function(a) {
        return a.sourceSchoolId == sourceSchoolId;
      });
      return obj.text;
    },
    /**
     * 点击取消返回拜访记录查看页面
     * @author LiuGuoHao
     */
    emitLeft() {
      this.$router.go(-1);
    },
    /**
     * 保存按钮操作
     * @author LGH
     */
    addOrEditGroup() {
      if (this.$route.params.publicRelation) {
        this.editGroup();
      } else {
        this.addGroup();
      }
    },
    /**
     * 新增操作
     * @author LGH
     */
    addGroup() {
      addGroupCount(this.form).then(() => {
        this.$message.success("新增成功");
        this.$router.go(-1);
      });
    },
    /**
     * 修改操作
     * @author LGH
     */
    editGroup() {
      this.form.publicRelation = this.$route.params.publicRelation;
      updateGroupCount(this.form).then(() => {
        this.$message.success("修改成功");
        this.$router.go(-1);
      });
    },
    /**
     * 对列表的选项文字展示进行格式化处理
     * @author lgh
     */
    formatter(type, val) {
      if (type === "year") {
        return `${val}年`;
      } else if (type === "month") {
        return `${val}月`;
      } else if (type === "day") {
        return `${val}日`;
      } else if (type === "hour") {
        return `${val}时`;
      } else if (type === "minute") {
        return `${val}分`;
      }
      return val;
    }
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

.van-cell:not(:last-child)::after {
  left: 0;
}

.visit-school-add {
  background-color: rgba(255, 255, 255, 1);
}

.add-title {
  display: flex;
  height: 60px;
  align-items: center;
  border-bottom: 0.5px solid #eee;
  padding: 0 10px;
  background-color: bgColor;

  .add-title-left {
    width: 125px;
    font-size: 18px;
  }

  span {
    color: fontTitleColor;
    font-size: fontBigSize;
    color: rgba(51, 51, 51, 1);
  }

  .add-title-mid {
    color: rgba(51, 51, 51, 1);
    font-size: 18px;
    font-weight: 600;
  }
}

.add-form {
  .van-cell {
    line-height: 32px;
  }

  .add-form-recodiv {
    background: #fff;
  }

  .add-form-recordContent {
    padding: 8px 16px;
    resize: none;
    margin: 0px 16px;
    background-color: rgba(242, 242, 242, 1);
    height: 100px;
    border: 0px solid;
  }

  .add-form-record {
    display: block;

    >>>.van-field__value {
      text-align: left;
    }

    >>>.van-field__control {
      text-align: left;
      padding: 0 16px;
      background-color: #F2F2F2;
    }
  }

  .add-form-save {
    margin-top: 150px;

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
}
</style>