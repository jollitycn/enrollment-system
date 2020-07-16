<template>
  <div>
    <!-- <van-sticky :offset-top="0"> -->
    <!-- <div class="cheader">
        <span class="cheader-left" @click="cancelAdd">取消</span>
        <span class="cheader-title">新增宣讲计划</span>
        <span class="cheader-right"></span>
    </div>-->
    <h5-header :leftStatus="true" title="新增宣讲计划"></h5-header>
    <!-- </van-sticky> -->
    <div class="info" @change="changeSaveBtn">
      <div>
        <van-form validate-first @submit="addPreach">
          <van-field
            v-model.trim="from.title"
            label-class="xx"
            label="主题"
            name="主题"
            maxlength="30"
            placeholder="请填写主题"
            :rules="rules.title"
          />
          <van-field
            v-model="from.preachTime"
            label-class="xx"
            label="宣讲开始时间"
            @click="changeVanDatetimePicker(false)"
            placeholder="请选择"
            :rules="rules.preachTime"
            disabled
          >
            <!-- <template #button>
              <van-button size="small">选择</van-button>
            </template>-->
            <!-- style="width:100%;" -->
            <template #right-icon>
              <img class="img-width" src="@/assets/images/right.png" name="arrow-left" />
            </template>
          </van-field>
          <van-field
            v-model="from.endTime"
            label-class="xx"
            label="宣讲结束时间"
            :rules="rules.endTime"
            @click="changeVanDatetimePicker(true)"
            placeholder="请选择"
            disabled
          >
            <!-- <template #button>
              <van-button size="small">选择</van-button>
            </template>-->
            <template #right-icon>
              <img class="img-width" src="@/assets/images/right.png" name="arrow-left" />
            </template>
            <van-icon slot="right-icon" name="arrow" />
          </van-field>
          <van-field
            v-model.trim="from.address"
            label-class="xx"
            label="宣讲地点"
            name="宣讲地点"
            maxlength="100"
            placeholder="请填写宣讲地点"
            :rules="rules.address"
          />
          <van-field
            v-model.trim="from.planAmount"
            label-class="xx"
            label="计划人数"
            name="计划人数"
            placeholder="请填写计划人数"
            :rules="rules.planAmount"
          />
          <van-field
            v-model="from.speakerName"
            label-class="xx"
            label="主讲人"
            placeholder="主讲人"
            disabled
            :rules="rules.speakerName"
          >
            <template slot="input">
              <div class="activeRight">
                <div class="activeRight-btn" @click="changeSpeakerNameOne">
                  <div class="activeRight-btn-one">
                    <div class="activeRight-btn-one-title">{{ from.speakerName }}</div>
                    <div>添加</div>
                  </div>
                </div>
              </div>
            </template>
          </van-field>
          <van-field
            v-model.trim="from.telephone"
            label-class="xx"
            label="联系手机"
            name="联系手机"
            placeholder="联系手机"
            maxlength="11"
            :rules="rules.telephone"
          />
          <van-field v-model="assistantIds" label="协助人" placeholder="协助人" disabled>
            <template slot="input">
              <div class="activeRight">
                <div class="activeRight-btn" @click="changeSpeakerName">
                  <div>添加</div>
                </div>
                <div class="activeRight-school">
                  <div
                    v-for="(item, index) in from.assistantIds"
                    :key="item.userId"
                    @click="deleteAssistantIdsItem(index)"
                  >
                    <div>
                      <div>
                        <span>{{ item.value }}</span>
                        <img :src="iconSrc" alt="删除" />
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </template>
          </van-field>
          <van-field v-model="value" label="面向学校" placeholder="面向学校" disabled>
            <template slot="input">
              <div class="activeRight">
                <div class="activeRight-btn" @click="changeSchool">
                  <div>添加</div>
                </div>
                <div class="activeRight-school">
                  <div
                    v-for="(item, index) in from.sourceSchoolIds"
                    :key="item.sourceSchoolId"
                    @click="deleteSchoolItem(index)"
                  >
                    <div>
                      <div>
                        <span>{{ item.sourceSchoolName }}</span>
                        <img :src="iconSrc" alt="删除" />
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </template>
          </van-field>
          <van-field
            v-model.trim="from.comment"
            rows="2"
            autosize
            label="备注"
            type="textarea"
            maxlength="50"
            placeholder="请输入备注"
            show-word-limit
          />
          <div>
            <!-- <van-button :color="saveBtnColor" size="large" native-type="save">保存</van-button> -->
            <button class="action-save-btn" type="submit">保存</button>
          </div>
        </van-form>
      </div>

      <!--显示面向学校列表-->
      <!-- @confirm="onConfirm" -->
      <van-popup v-model="showPicker" position="bottom">
        <van-picker
          :columns="sourceSchoolList"
          @cancel="showPicker = false"
          @confirm="changeSchoolList"
          value-key="sourceSchoolName"
          show-toolbar
        >
          <template slot="columns-top">
            <div>
              <span
                @click="deleteSchoolItem(index)"
                v-for="(item,index) in from.sourceSchoolIds"
                :key="item.sourceSchoolId"
                class="schooleClass"
              >{{ item.sourceSchoolName }}</span>
            </div>
          </template>
        </van-picker>
      </van-popup>
      <!--显示宣讲时间组件s-->
      <van-popup v-model="showVanDatetimePicker" position="bottom">
        <van-datetime-picker
          @confirm="DatetimePickerConfirm"
          @cancel="showVanDatetimePicker = false"
          v-model="currentDate"
          type="datetime"
          :min-date="minDate"
          :max-date="maxDate"
          :formatter="formatter"
        />
      </van-popup>
      <!--显示宣讲时间组件e-->
      <!--显示协助人列表多选s-->
      <!-- @confirm="DatetimeSpeakerName" -->
      <van-popup v-model="showSpeakerName" position="bottom">
        <van-picker
          :columns="speakerNameList"
          @cancel="showSpeakerName = false"
          @confirm="changeSpeakerNameList"
          value-key="value"
          show-toolbar
        >
          <template slot="columns-top">
            <div>
              <span
                @click="deleteAssistantIdsItem(index)"
                v-for="(item,index) in from.assistantIds"
                :key="item.userId"
                class="schooleClass"
              >{{ item.value }}</span>
            </div>
          </template>
        </van-picker>
      </van-popup>
      <!--显示协助人列表e-->
      <!--显示主讲人列表s-->
      <van-popup v-model="showSpeakerNameOne" position="bottom">
        <van-picker
          :columns="speakerNameList"
          @cancel="showSpeakerNameOne = false"
          @confirm="DatetimeSpeakerNameOne"
          value-key="value"
          show-toolbar
        ></van-picker>
      </van-popup>
      <!--显示主讲人列表e-->
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import { dateFormat } from "../../../utils/format";
import Header from "@/components/cheader";
import { Field, Picker, Popup, Form, DatetimePicker, Toast, Icon } from "vant";
import {
  addPreach,
  listAllSourceSchool,
  getStruct
} from "@/api/admissions-process/preaching-plan";
import rules from "@/assets/rules/rulesFrom";
Vue.use(Toast);
Vue.use(Icon);
export default {
  components: {
    vanField: Field,
    // vanButton: Button,
    vanPicker: Picker,
    vanPopup: Popup,
    vanForm: Form,
    vanDatetimePicker: DatetimePicker,
    h5Header: Header
  },
  name: "info",
  data() {
    return {
      schoolId: JSON.parse(localStorage.getItem("userInfo")).schoolid,
      iconSrc: require("@/assets/images/deleteIcon.png"),
      minDate: new Date(2020, 0, 1), //最小时间
      maxDate: new Date(2025, 10, 1), //最大时间啊
      currentDate: new Date(), //当前时间
      showVanDatetimePicker: false, //时间选择器弹窗
      isDatetimePicker: false, //宣讲开始时间还是结束时间 0为开始
      showSpeakerName: false, //协助人下拉列表是否显示
      showSpeakerNameOne: false, //主讲人是否显示
      speakerNameList: [], //主讲人与协助人信息列表
      assistantIds: "", //协助信息,
      sourceSchoolList: [], //面向学校列表
      changeSchoolItem: {}, //改变学校是的数据
      changeSpeakerNameItem: {}, //改变协助人的数据
      saveBtnColor: "#00CCFFFF", //保存按钮的背景颜色
      from: {
        address: "", //宣讲地点
        assistantIds: [], //协助人ID列表
        comment: "", //备注
        preachTime: "", //开始时间
        endTime: "", //结束时间
        planAmount: 1, //计划人数
        sourceSchoolIds: [], //面向学校列表
        speakerId: "", //主讲人ID
        speakerName: "", //主讲人姓名
        telephone: "", //联系方式
        title: "" //主题
      },
      rules: {
        title: [
          {
            pattern: /^[0-9a-zA-Z\u4e00-\u9fa5]{1,30}$/,
            message: "请输入1-30个字符的宣讲主题,不包含特殊字符"
          }
          // { required: true, message: "请输入宣讲主题" }
        ],
        preachTime: [{ required: true, message: "请选择宣讲开始时间" }],
        endTime: [{ required: true, message: "请选择宣讲结束时间" }],
        speakerName: [{ required: true, message: "请选择主讲人" }],
        address: [
          {
            pattern: /^[0-9a-zA-Z\u4e00-\u9fa5]{1,100}$/,
            message: "请输入1-100个字符的地址,不包含特殊字符"
          }
        ],
        planAmount: [
          {
            pattern: /^\+?[1-9]\d*$/,
            message: "人数为大于0的整数"
          }
        ],
        telephone: [
          {
            pattern: rules.phone.rules,
            message: rules.phone.message
          }
        ]
      },
      pattern: /^[a-zA-Z\u4e00-\u9fa5]{2,50}$/,
      value: "",
      value1: "",
      value2: "",
      value3: "",
      radio: "",
      showPicker: false,
      columns: ["杭州", "宁波", "温州", "嘉兴", "湖州"]
    };
  },
  methods: {
    //主讲人确定按钮
    DatetimeSpeakerNameOne(item) {
      this.from.speakerName = item.value;
      this.from.speakerId = item.userId;
      this.showSpeakerNameOne = false;
    },
    //新增主讲人
    changeSpeakerNameOne() {
      this.showSpeakerNameOne = true;
    },
    //获取组织结构
    getStruct() {
      let data = { schoolId: this.schoolId };
      getStruct(data)
        .then(res => {
          let list = res.data.data.child;
          let length = list.length;
          let arr = [];
          for (let i = 0; i < length; i++) {
            let childLength = list[i].child.length;
            // let title = list[i].name;
            let childData = list[i].child;
            for (let j = 0; j < childLength; j++) {
              arr.push({
                // value: "[" + title + "]" + childData[j].name,
                value: childData[j].name,
                userId: childData[j].userid
              });
            }
            this.speakerNameList = arr;
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    //获取面向学校
    getListAllSourceSchool() {
      listAllSourceSchool()
        .then(res => {
          this.sourceSchoolList = res.data;
        })
        .catch(err => {
          console.log(err);
        });
    },
    /**
     * 保存按钮颜色修改
     * @author LiuGuoHao
     */
    changeSaveBtn() {
      // if(this.from!=form){
      //   this.saveBtnColor = "#00CCFFFF"
      // }else{
      //   this.saveBtnColor = "#B5F0FFFF"
      // }
    },
    //时间格式装换
    _change(date) {
      console.log(date, "==>date");

      let seperator1 = "-";
      let month = date.getMonth() + 1;
      let strDate = date.getDate();
      if (month >= 1 && month <= 9) {
        month = "0" + month;
      }
      if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
      }
      return date.getFullYear() + seperator1 + month + seperator1 + strDate;
    },
    //改变学校添加
    changeSchoolList(picker, value) {
      console.log(picker, "==>schoolPicker");
      console.log(value, "==>schoolValue");
      this.changeSchoolItem = picker;
      this.clickSchoolList();
    },
    //点击学校列表触发
    clickSchoolList() {
      setTimeout(() => {
        let status = true;
        let index = 0;
        for (let i = 0; i < this.from.sourceSchoolIds.length; i++) {
          if (
            this.from.sourceSchoolIds[i].sourceSchoolId ===
            this.changeSchoolItem.sourceSchoolId
          ) {
            status = false;
            index = i;
            break;
          }
        }
        status && this.from.sourceSchoolIds.push(this.changeSchoolItem);
        !status && this.from.sourceSchoolIds.splice(index, 1);
        this.showPicker = false;
      }, 200);
    },
    //改变协助人添加
    changeSpeakerNameList(picker, value) {
      console.log(value, "==>value");
      console.log(picker, "==>picker");
      this.changeSpeakerNameItem = picker;
      this.clickSpeakerNameList();
    },
    //点击人员列表触发
    clickSpeakerNameList() {
      setTimeout(() => {
        let status = true;
        let index = 0;
        for (let i = 0; i < this.from.assistantIds.length; i++) {
          if (
            this.from.assistantIds[i].userId ===
            this.changeSpeakerNameItem.userId
          ) {
            status = false;
            index = i;
            break;
          }
        }
        status && this.from.assistantIds.push(this.changeSpeakerNameItem);
        !status && this.from.assistantIds.splice(index, 1);
        this.showSpeakerName = false;
      }, 200);
    },
    //打开主讲人列表
    changeSpeakerName() {
      this.showSpeakerName = !this.showSpeakerName;
    },
    //确定主讲人操作
    DatetimeSpeakerName(value) {
      if (value) {
        this.showSpeakerName = false;
      } else {
        this.$message.error("请先选择主讲人！");
      }
    },
    //选择时间
    changeVanDatetimePicker(type) {
      this.showVanDatetimePicker = true;
      this.isDatetimePicker = type;
    },
    //确认时间
    DatetimePickerConfirm(value) {
      if (this.isDatetimePicker) {
        // this.from.endTime = this._change(value);
        this.from.endTime = dateFormat(value, "YYYY-mm-dd HH:MM:SS");
      } else {
        // this.from.preachTime = this._change(value);
        this.from.preachTime = dateFormat(value, "YYYY-mm-dd HH:MM:SS");
      }
      this.showVanDatetimePicker = false;
    },
    validator(val) {
      return /1\d{10}/.test(val);
    },
    onFailed(errorInfo) {
      console.log("failed", errorInfo);
    },
    /**
     * 取消返回上一页
     * @author LiuGuoHao
     */
    cancelAdd() {
      this.$router.go(-1);
    },
    //选择学校
    changeSchool() {
      this.showPicker = true;
    },
    //删除指定面向学校
    deleteSchoolItem(index) {
      this.from.sourceSchoolIds.splice(index, 1);
    },
    //删除指定协助人
    deleteAssistantIdsItem(index) {
      this.from.assistantIds.splice(index, 1);
    },
    //面向学校弹出层确定
    onConfirm() {
      this.showPicker = false;
    },
    //新增宣讲计划
    addPreach() {
      let data = { ...this.from };
      let assistantIdsArr = this.from.assistantIds;
      console.log(assistantIdsArr);
      let assistantIds = assistantIdsArr.map(item => {
        return { id: item.userId, name: item.value };
      });
      let sourceSchoolIdsArr = this.from.sourceSchoolIds;
      let sourceSchoolIds = sourceSchoolIdsArr.map(item => {
        return item.sourceSchoolId;
      });
      data.assistantIds = assistantIds;
      data.sourceSchoolIds = sourceSchoolIds;
      // data.endTime = data.endTime + " 23:59:59";
      // data.preachTime = data.preachTime + " 00:00:00";
      // console.log(
      //   "assistantIds",
      //   assistantIds,
      //   "sourceSchoolIds",
      //   sourceSchoolIds,
      //   data
      // );
      this.$message.loading("正在保存...");
      addPreach(data)
        .then(() => {
          this.$message.close();
          this.$message.success("保存成功！");
          this.$router.go(-1);
        })
        .catch(err => {
          console.error("保存错误：", err);
        });
    },
    /**
     * 对列表的选项文字展示进行格式化处理
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
  },
  created() {
    //获取面向学校
    this.getListAllSourceSchool();
    //获取组织结构
    this.getStruct();
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

// .cheader {
// display: flex;
// justify-content: space-between;
// height: 60px;
// align-items: center;
// border-bottom: 0.5px solid #eee;
// padding: 0 15px;
// background-color: bgColor;
// padding-top: 54px;

// span {
// color: fontTitleColor;
// font-size: fontBigSize;
// font-weight: bold;
// }

// .cheader-left {
// color: btnBgColor;
// }

// .cheader-right {
// color: fontMiniFontColor;
// }
// }
.info {
  font-size: fontMedSize;
  padding: 20px;
  padding-left: 0px;
  padding-right: 0px;
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

  .schooleClass {
    border: 1px solid btnBgColor;
    padding: 7px 10px;
    display: inline-block;
    background-color: btnBgColor;
    color: bgColor;
    border-radius: 10px;
    margin-left: 10px;
    margin-top: 4px;
  }

  .activeRight {
    flex: 1;

    .activeRight-btn {
      color: btnBgColor;
      text-align: right;

      >.activeRight-btn-one {
        display: flex;
        justify-content: space-between;

        >.activeRight-btn-one-title {
          color: fontTextColor;
        }
      }
    }

    .activeRight-school {
      >div {
        >div {
          display: inline-block;
          border: 1px solid btnBgColor;
          padding: 4px 8px;
          border-radius: 10px;
          color: btnBgColor;
          margin-bottom: 5px;

          >div {
            display: flex;
            align-items: center;

            >img {
              width: 15px;
              height: 15px;
              padding-left: 5px;
            }
          }
        }
      }
    }
  }

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

  >>>.van-field__label {
    height: 0.6rem;
    line-height: 0.66rem;
    flex: 0.45;
  }

  >>>.van-cell {
    padding: 16px;
  }

  >>>.van-cell:not(:last-child)::after {
    left: 0;
  }

  >>>.van-cell__value {
    flex: 0.8;
  }

  >>>.van-cell--required::before {
    top: 0.3rem;
  }
}

.img-width {
  width: 26px;
  position: absolute;
  right: -4px;
  top: 1px;
}
</style>
