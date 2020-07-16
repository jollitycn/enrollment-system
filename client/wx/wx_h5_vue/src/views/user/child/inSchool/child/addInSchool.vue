<template>
  <div>
    <van-sticky :offset-top="0">
      <h5-header :leftStatus="true" title="到校咨询"></h5-header>
    </van-sticky>
    <div class="info">
      <div>
        <van-form validate-first @submit="submit">
          <!-- 通过 pattern 进行正则校验 -->
          <van-field
            v-model="from.consultationDate"
            label="咨询日期"
            name="consultationDate"
            label-class="xx"
            placeholder="请选择"
            readonly
            class="activeClass"
          >
          </van-field>
          <van-field
            v-model="time"
            label="到校时间"
            name="time"
            label-class="xx"
            placeholder="请选择"
            readonly
            class="activeClass"
          >
            <template #button>
              <van-button
                size="small"
                @click="activeDate"
                native-type="button"
                class="activeBtn"
                >选择</van-button
              >
            </template>
          </van-field>
          <van-field
            v-model="from.telephone"
            label="手机号码"
            label-class="xx"
            name="telephone"
            placeholder="手机号码"
            type="tel"
            :rules="rules.phone"
          />
          <van-field name="isLunch" label-class="xx" label="是否用餐">
            <template #input>
              <van-radio-group v-model="from.isLunch" direction="horizontal">
                <van-radio :name="0">是</van-radio>
                <van-radio :name="1">否</van-radio>
              </van-radio-group>
            </template>
          </van-field>
          <van-field
            v-model="from.comment"
            rows="2"
            autosize
            name="comment"
            label="留言"
            type="textarea"
            maxlength="100"
            placeholder="请输入留言"
            show-word-limit
          />
          <div style="margin: 16px;">
            <van-button
              :loading="isLoading"
              :disabled="isLoading"
              loading-text="新增中..."
              round
              block
              color="#00CCFF"
              native-type="submit"
            >
              保存
            </van-button>
          </div>
        </van-form>
      </div>
    </div>
    <div>
      <van-popup v-model="showPicker" position="bottom">
        <van-datetime-picker
          type="time"
          min-hour="9"
          max-hour="18"
          @confirm="onConfirm"
          @cancel="showPicker = false"
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
  Form,
  Sticky,
  Popup,
  DatetimePicker
} from "vant";
import Header from "@/components/cheader";
import { phone } from "../../../../../assets/rules/rulesFrom";
import { addRegister } from "@/api/user/user";
export default {
  components: {
    vanField: Field,
    vanButton: Button,
    vanForm: Form,
    vanRadio: Radio,
    vanRadioGroup: RadioGroup,
    vanSticky: Sticky,
    vanPopup: Popup,
    vanDatetimePicker: DatetimePicker,
    h5Header: Header
  },
  name: "addInSchool",
  data() {
    return {
      isLoading: false,
      showPicker: false,
      time: "09:00",
      from: {
        consultationDate: "", //咨询日期
        attendTime: "", //到校时间
        isLunch: 0, //是否用餐
        telephone: "", //联系方式
        comment: "", //说明
        consultationId: "" //咨询计划id
      },
      rules: {
        phone: [{ pattern: phone.rules, message: phone.message }]
      }
    };
  },
  beforeRouteLeave(to, from, next) {
    if(this.showPicker) {
      this.showPicker = false;
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
    //到校时间选择
    activeDate() {
      console.log(1);
      this.showPicker = true;
    },
    //确定时间
    onConfirm(val) {
      this.time = val;
      this.showPicker = false;
    },
    //新增咨询计划
    addRegister() {
      addRegister(this.from)
        .then(() => {
          this.isLoading = false;
          this.$toast({
            message: "新增成功",
            onClose: () => {
              this.$router.go(-1);
            }
          });
        })
        .catch(() => {
          this.isLoading = false;
        });
    },
    //提交表达
    submit(data) {
      this.from.attendTime = data.consultationDate + " " + data.time + ":00";
      this.isLoading = true;
      this.addRegister();
      console.log(data, this.from);
    }
  },
  created() {
    this.from.consultationDate = this.$route.query.date;
    this.from.consultationId = this.$route.query.consultationId;
  }
};
</script>

<style scoped lang="stylus">
.info
  font-size fontMedSize
  padding 20px 0px
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
  .activeClass
    display flex
    align-items center
    .activeBtn
      border 0
      color activeBtnColor
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
