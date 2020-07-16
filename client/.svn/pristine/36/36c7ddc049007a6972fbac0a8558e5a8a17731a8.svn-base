<template>
  <div>
    <van-sticky :offset-top="0">
      <h5-header :leftStatus="true" title="报名管理"></h5-header>
    </van-sticky>
    <div class="info">
      <div>
        <van-form validate-first @failed="onFailed">
          <div class="info-title">招生老师</div>
          <div class="content">
            <div class="content-first">
              <span class="content-first-one">负责老师</span>
              <span class="content-first-two">王XX</span>
            </div>
            <div class="content-first">
              <span class="content-first-one">联系方式</span>
              <span class="content-first-two">15212345678</span>
            </div>
            <div class="content-first">
              <span class="content-first-one">微信号</span>
              <span class="content-first-two">123456789</span>
            </div>
            <div class="content-first">
              <span class="content-first-one">QQ号</span>
              <span class="content-first-two">123456789</span>
            </div>
          </div>
          <!-- 通过 pattern 进行正则校验 -->
          <div class="info-title">studentList2</div>
          <van-field
            v-model="value1"
            label="学生姓名"
            required
            name="pattern"
            maxLength="4"
            placeholder="正则校验"
            :rules="[{ pattern, message: '请输入正确内容' }]"
          />
          <van-field name="radio" required label="性别">
            <template #input>
              <van-radio-group v-model="radio" direction="horizontal">
                <van-radio name="1">男</van-radio>
                <van-radio name="2">女</van-radio>
              </van-radio-group>
            </template>
          </van-field>
          <van-field
            v-model="value"
            label="民族"
            @click="changeSchool"
            placeholder="请选择民族"
            readonly
          >
            <template #button>
              <van-button size="small">选择</van-button>
            </template>
          </van-field>
          <van-field
            v-model="value"
            label="出生年月"
            required
            @click="changeSchool"
            placeholder="请选择"
            disabled
          >
            <template #button>
              <van-button size="small">选择</van-button>
            </template>
          </van-field>
          <van-field
            v-model="value1"
            label="身份证号"
            name="pattern"
            placeholder="正则校验"
            :rules="[{ pattern, message: '请输入身份证号' }]"
          />
          <van-field
            v-model="value"
            label="户籍所在地"
            required
            @click="changeSchool"
            placeholder="请选择户籍所在地"
            disabled
          >
            <template #button>
              <van-button size="small">选择</van-button>
            </template>
          </van-field>
          <div class="info-title">联系方式</div>
          <van-field
            v-model="value1"
            label="学生微信"
            name="pattern"
            placeholder="正则校验"
            :rules="[{ pattern, message: '请输入微信' }]"
          />
          <van-field
            v-model="value1"
            label="家长手机"
            required
            name="pattern"
            placeholder="正则校验"
            :rules="[{ pattern, message: '请输入家长手机' }]"
          />
          <van-field
            v-model="value1"
            label="学生手机"
            name="pattern"
            placeholder="正则校验"
            :rules="[{ pattern, message: '请输入学生手机' }]"
          />
          <van-field
            v-model="value"
            label="家庭住址"
            required
            @click="changeSchool"
            placeholder="请选择家庭住址"
            disabled
          >
            <template #button>
              <van-button size="small">选择</van-button>
            </template>
          </van-field>
          <van-field
            v-model="value1"
            name="pattern"
            label="详细家庭地址"
            placeholder="填写详细地址"
            :rules="[{ pattern, message: '请输入学生手机' }]"
          />
          <div class="info-title">studentList2</div>
          <van-field
            v-model="value"
            label="毕业信息"
            required
            @click="changeSchool"
            placeholder="请选择毕业信息"
            disabled
          >
            <template #button>
              <van-button size="small">选择</van-button>
            </template>
          </van-field>
          <van-field
            v-model="value"
            label="所属班级"
            required
            @click="changeSchool"
            placeholder="请选择所属班级"
            disabled
          >
            <template #button>
              <van-button size="small">选择</van-button>
            </template>
          </van-field>
          <van-field
            v-model="value1"
            name="pattern"
            label="原学籍号"
            placeholder="请输入原学籍号"
            :rules="[{ pattern, message: '请输入原学籍号' }]"
          />
          <van-field
            v-model="value1"
            name="pattern"
            label="班主任"
            placeholder="请填写班主任"
            :rules="[{ pattern, message: '请填写班主任' }]"
          />
          <van-field
            v-model="value1"
            name="pattern"
            label="语文成绩"
            placeholder="请填写语文成绩"
            :rules="[{ pattern, message: '请填写语文成绩' }]"
          />
          <van-field
            v-model="value1"
            name="pattern"
            label="数学成绩"
            placeholder="请填写数学成绩"
            :rules="[{ pattern, message: '请填写数学成绩' }]"
          />
          <van-field
            v-model="value1"
            name="pattern"
            label="英语成绩"
            placeholder="请填写英语成绩"
            :rules="[{ pattern, message: '请填写英语成绩' }]"
          />
          <van-field
            v-model="value1"
            name="pattern"
            label="特长"
            placeholder="请填写特长"
            :rules="[{ pattern, message: '请填写特长' }]"
          />
          <div class="info-title">家庭成员</div>
          <van-field
            v-model="value"
            label="关系"
            required
            @click="changeSchool"
            placeholder="关系"
            disabled
          >
            <template #button>
              <van-button size="small">选择</van-button>
            </template>
          </van-field>
          <van-field
            v-model="value1"
            name="pattern"
            label="家长姓名"
            maxLength="4"
            placeholder="请填写家长姓名"
            :rules="[{ pattern, message: '请填写家长姓名' }]"
          />
          <van-field
            v-model="value1"
            name="pattern"
            label="工作单位"
            placeholder="请填写工作单位"
            :rules="[{ pattern, message: '请填写工作单位' }]"
          />
          <van-field
            v-model="value1"
            name="pattern"
            label="职务"
            placeholder="请填写职务"
            :rules="[{ pattern, message: '请填写职务' }]"
          />
          <van-field
            v-model="value1"
            name="pattern"
            label="联系方式"
            placeholder="请填写联系方式"
            :rules="[{ pattern, message: '请填写联系方式' }]"
          />
          <van-field
            v-model="value"
            label="关系"
            required
            @click="changeSchool"
            placeholder="关系"
            disabled
          >
            <template #button>
              <van-button size="small">选择</van-button>
            </template>
          </van-field>
          <van-field
            v-model="value1"
            name="pattern"
            label="家长姓名"
            maxLength="4"
            placeholder="请填写家长姓名"
            :rules="[{ pattern, message: '请填写家长姓名' }]"
          />
          <van-field
            v-model="value1"
            name="pattern"
            label="工作单位"
            placeholder="请填写工作单位"
            :rules="[{ pattern, message: '请填写工作单位' }]"
          />
          <van-field
            v-model="value1"
            name="pattern"
            label="职务"
            placeholder="请填写职务"
            :rules="[{ pattern, message: '请填写职务' }]"
          />
          <van-field
            v-model="value1"
            name="pattern"
            label="联系方式"
            placeholder="请填写联系方式"
            :rules="[{ pattern, message: '请填写联系方式' }]"
          />
          <!--                <van-field-->
          <!--                        v-model="value2"-->
          <!--                        name="validator"-->
          <!--                        placeholder="函数校验"-->
          <!--                        :rules="[{ validator, message: '请输入正确内容' }]"-->
          <!--                />-->
          <!-- 通过 validator 进行异步函数校验 -->
          <div style="margin: 16px;">
            <van-button round block type="info" native-type="submit">
              提交
            </van-button>
          </div>
        </van-form>
      </div>

      <van-popup v-model="showPicker" position="bottom">
        <van-picker
          show-toolbar
          :columns="columns"
          @cancel="showPicker = false"
          @confirm="onConfirm"
        />
      </van-popup>
    </div>
  </div>
</template>

<script>
import {
  Radio,
  RadioGroup,
  Field,
  Button,
  Picker,
  Popup,
  Form,
  Sticky
} from "vant";
import Header from "@/components/cheader";
export default {
  components: {
    vanField: Field,
    vanButton: Button,
    vanPicker: Picker,
    vanPopup: Popup,
    vanForm: Form,
    vanRadio: Radio,
    vanRadioGroup: RadioGroup,
    vanSticky: Sticky,
    h5Header: Header
  },
  name: "info",
  data() {
    return {
      value: "",
      value1: "",
      value2: "",
      value3: "",
      radio: "",
      pattern: /\d{6}/,
      showPicker: false,
      columns: ["杭州", "宁波", "温州", "嘉兴", "湖州"]
    };
  },
  methods: {
    onFailed(errorInfo) {
      console.log("failed", errorInfo);
    },
    //选择学校
    changeSchool() {
      console.log(1);
      this.showPicker = true;
    },
    onConfirm(value) {
      this.value = value;
      this.showPicker = false;
    }
  }
};
</script>

<style scoped lang="stylus">
.info
    font-size fontMedSize
    padding 20px
    background bgColor
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
</style>
