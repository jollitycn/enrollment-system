<template>
  <div class="school-class-form">
    <h5-header :leftStatus="true" title="编辑学校"></h5-header>
    <div class="student-intended-content">
      <van-form
        label-width="120px"
        error-message-align="right"
        v-model="form"
        input-align="right"
        @submit="onSubmit"
      >
        <!-- <van-field name="radio" label-class="custom-field-title" label="学校类型">
          <template #input>
            <van-radio-group v-model="form.relationLevel" direction="horizontal">
              <van-radio :name="0" checked-color="#00CCFF">重点</van-radio>
              <van-radio :name="1" checked-color="#00CCFF">普通</van-radio>
              <van-radio :name="2" checked-color="#00CCFF">重新</van-radio>
            </van-radio-group>
          </template>
        </van-field>-->
        <div class="follow-type">
          <div class="follow-type-title">
            学校类型
            <span class="follow-type-title-tip">（可多选）</span>
          </div>
          <div class="follow-type-list">
            <span
              v-for="item in typeList"
              :key="item.dictionaryId"
              @click="changeType(item.dictionaryId)"
              :class="{ active: activeIndex2.indexOf(item.dictionaryId)>-1,'mult-checked':activeIndex2.indexOf(item.dictionaryId)>-1}"
            >{{ item.dataValue }}</span>
          </div>
        </div>
        <van-field
          v-model="form.recruitAmount"
          label-class="custom-field-title"
          label="中招人数"
          :maxlength="4"
          :rules="rules.planAmount"
          placeholder="填写中招人数"
        />
        <!-- <van-field name="radio" label-class="custom-field-title" label="跟进方式">
          <template #input>
            <van-checkbox-group v-model="recruitWays" direction="horizontal">
              <van-checkbox name="宣讲" checked-color="#00CCFF">宣讲</van-checkbox>
              <van-checkbox name="班主任指导" checked-color="#00CCFF">班主任指导</van-checkbox>
              <van-checkbox name="发宣传册" checked-color="#00CCFF">发宣传册</van-checkbox>
            </van-checkbox-group>
          </template>
        </van-field>-->
        <div class="follow-type">
          <div class="follow-type-title">
            跟进方式
            <span class="follow-type-title-tip">（可多选）</span>
          </div>
          <div class="follow-type-list">
            <span
              v-for="item in followList"
              :key="item.id"
              @click="changeFollow(item.value)"
              :class="{ active: recruitWays.indexOf(item.value)>-1,'mult-checked':recruitWays.indexOf(item.value)>-1}"
            >{{ item.value }}</span>
          </div>
        </div>
        <van-field
          v-model="form.gradeDirectorName"
          label-class="custom-field-title"
          label="年级主任"
          placeholder="请填写年级主任"
          :rules="rules.title"
          maxlength="7"
        />
        <van-field
          v-model="form.gradeDirectorTel"
          label-class="custom-field-title"
          label="年级主任电话"
          maxlength="11"
          :rules="rules.telephone"
          placeholder="请填写年级主任电话"
        />
        <van-field
          v-model="form.classAmount"
          label-class="custom-field-title"
          label="班级数量"
          maxlength="8"
          :rules="rules.planAmount"
          placeholder="请填写班级数量"
        />
        <van-field
          v-model="form.schoolCaseComment"
          label-class="custom-field-title"
          label="学校情况说明"
          class="custom-textarea"
          rows="6"
          type="textarea"
          placeholder="填写学校情况说明"
          maxlength="200"
        />
        <div class="school-class-form-save">
          <!-- <van-button round block type="info" native-type="submit">保 存</van-button> -->
          <button class="action-save-btn" type="submit">保存</button>
        </div>
      </van-form>
    </div>
  </div>
</template>

<script>
import Header from "@/components/cheader";
import Vue from "vue";
import { Picker } from "vant";
import { DatetimePicker } from "vant";
import { Area } from "vant";
import { Checkbox, CheckboxGroup } from "vant";
import { Form, Field, Button, Uploader, RadioGroup, Radio, Popup } from "vant";
import areaList from "@/assets/json/area";
Vue.use(Form);
Vue.use(Field);
Vue.use(Button);
Vue.use(Uploader);
Vue.use(RadioGroup);
Vue.use(Radio);
Vue.use(Popup);
Vue.use(Picker);
Vue.use(DatetimePicker);
Vue.use(Area);
Vue.use(Checkbox);
Vue.use(CheckboxGroup);

import { listSourceSchoolType } from "@/api/sourceSchool/sourceSchool";
import rules from "@/assets/rules/rulesFrom";
import { getSourceSchoolDetail } from "../../../api/sourceSchool/sourceSchool";
import { updateSourceSchool } from "../../../api/sourceSchool/sourceSchool";
export default {
  name: "responsibility-school-add",
  components: {
    h5Header: Header
  },
  data() {
    return {
      value: 50,
      result: [],
      schoolType: [],
      adresses: "",
      activeIndex2: [],
      adressVisible: false,
      schoolTypeVisible: false,
      areaList: areaList,
      typeList: [],
      followList: [
        { id: 0, value: "宣讲" },
        { id: 1, value: "班主任指导" },
        { id: 2, value: "发宣传册" }
      ],
      recruitWays: [],
      form: {
        province: "",
        city: "",
        district: "",
        recruitWay: ""
      },
      levelList: [
        { id: 0, title: "高" },
        { id: 1, title: "中" },
        { id: 2, title: "低" }
      ],
      rules: {
        title: [
          {
            pattern: rules.componetName.rules,
            message: rules.componetName.message
          }
        ],
        address: [
          {
            pattern: /^[0-9a-zA-Z\u4e00-\u9fa5]{2,50}$/,
            message: "请输入5-50个字符"
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
      }
    };
  },
  created() {
    this.schoolId = this.$route.params.id;
    //获取招生学校下的学校类型（数据字典配置）
    this.getSchoolType();
  },
  methods: {
    /**
     * 跟进类型切换
     * @param {int } index 类型列表的下标
     * @author lmh
     */
    changeType(index) {
      let idx = this.activeIndex2.indexOf(index);
      if (idx > -1) {
        this.activeIndex2.splice(idx, 1);
      } else {
        this.activeIndex2.push(index);
      }
    },
    /**
     * 跟进类型切换
     * @param {int } index 类型列表的下标
     * @author lmh
     */
    changeFollow(index) {
      let idx = this.recruitWays.indexOf(index);
      if (idx > -1) {
        this.recruitWays.splice(idx, 1);
      } else {
        this.recruitWays.push(index);
      }
    },
    /**
     * 点击保存
     */
    onSubmit() {
      const data = { ...this.form };
      data.recruitWay = this.recruitWays.join(",");
      data.typeIds = []
      this.activeIndex2.forEach((item, index) => {
        data.schoolTypeList[index] = this.getNameByObj(item, this.typeList);
        data.typeIds[index] = data.schoolTypeList[index].dictionaryId;
      });
      this.$message.loading("正在保存...");
      updateSourceSchool(data)
        .then(() => {
          this.$message.close();
          this.$message.success(`保存成功`);
          this.navToSchool(this.schoolId);
        })
        .catch(err => {
          console.error("保存失败：", err);
          this.$message.close();
        });
    },
    /**
     * 编辑学校信息页面跳转
     */
    navToSchool(id) {
      this.$router.push({
        path: `/admissions-process/responsibility-school-manage/responsibility-school-detail/${id}`
      });
    },
    /**
     * 获取生源学校详情
     * @param {string} id 生源学校id
     * @author cwz
     */
    getSchool() {
      let id = this.schoolId;
      this.$message.loading();
      getSourceSchoolDetail(id).then(res => {
        this.$message.close();
        this.form = res.data;
        this.form.schoolTypeList.forEach((item, index) => {
          this.activeIndex2[index] = item.dictionaryId;
        });
        console.log("11", this.form);
        if (res.data.recruitWay) {
          this.recruitWays = res.data.recruitWay.split(",");
        }
        this.adresses =
          this.form.province + this.form.city + this.form.district;
        console.log(this.recruitWays, "==>recruitWays");
      });
    },
    /**
     * 获取招生学校下的学校类型（数据字典配置）
     * @param
     * @author cwz
     */
    getSchoolType() {
      listSourceSchoolType()
        .then(res => {
          this.typeList = res.data;
          console.log(this.typeList, "==>typeList");
          //获取学校信息
          this.getSchool();
        })
        .catch(err => {
          console.error(err);
        });
    },
    /**
     * 户籍地址弹框确定
     * @author cwz
     */
    getAdress(adresses) {
      this.adresses = adresses[0].name + adresses[1].name + adresses[2].name;
      this.form.province = adresses[0].name;
      this.form.city = adresses[1].name;
      this.form.district = adresses[2].name;
      this.adressVisible = false;
    },
    /**
     * 根据ID获取数组
     * @param id{number} 获取的名称所需要的id
     * @param query{array} 需要获取的name对应的数组
     * @author LiuGuoHao
     */
    getNameByObj(id, query) {
      var obj = query.find(function(x) {
        return x.dictionaryId == id;
      });
      return obj;
    }
  }
};
</script>

<style scoped lang="stylus">
.school-class-form {
  margin-bottom: 49px;

  .van-cell {
    font-size: 15px;
    padding: 16px;
    line-height: 23px;

    &:after {
      position: absolute;
      box-sizing: border-box;
      content: ' ';
      pointer-events: none;
      right: 0;
      bottom: 0;
      left: 0;
      border-bottom: 0.02667rem solid #DDDDDD;
      -webkit-transform: scaleY(0.5);
      transform: scaleY(0.5);
    }
  }

  >>>.van-radio__icon--checked+span {
    color: #00CCFF;
  }

  >>>.custom-field-title {
    span {
      font-weight: 600;
      color: rgba(51, 51, 51, 1);
    }
  }

  >>>.van-checkbox__icon--checked+span {
    color: #00CCFF;
  }

  >>>.van-checkbox__label {
    margin-top: 6px;
  }

  .custom-textarea {
    display: block;

    >>>.van-field__value {
      text-align: left;
      margin-top: 16px;
    }

    >>>.van-field__control {
      text-align: left;
      padding: 0 16px;
      background-color: #F2F2F2;
    }
  }

  &-save {
    margin-top: 20px;
    bottom: 0;

    .action-save-btn {
      width: 100vw;
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

.follow-type {
  padding: 16px;

  &:after {
    position: absolute;
    box-sizing: border-box;
    content: ' ';
    pointer-events: none;
    right: 0;
    bottom: 0;
    left: 0;
    border-bottom: 0.02667rem solid #DDDDDD;
    -webkit-transform: scaleY(0.5);
    transform: scaleY(0.5);
  }

  &-title {
    color: #333333;
    font-size: 15px;
    font-weight: 600;
    margin-bottom: 16px;
  }

  &-list {
    >span {
      color: #999;
      background-color: #F2F2F2;
      // padding: 10px 40px;
      display: inline-block;
      border-radius: 4px;
      margin-left: 16px;
    }

    >span:first-child {
      margin-left: 0;
    }
  }
}

.follow-type {
  padding: 16px;
  background-color: #FFFFFF;
  position: relative;

  &:after {
    position: absolute;
    box-sizing: border-box;
    content: ' ';
    pointer-events: none;
    right: 0;
    bottom: 0;
    left: 0;
    border-bottom: 0.02667rem solid #DDDDDD;
    -webkit-transform: scaleY(0.5);
    transform: scaleY(0.5);
  }

  &-title {
    color: #333333;
    font-size: 15px;
    font-weight: 600;
    margin-bottom: 16px;

    &-tip {
      color: #999999;
      font-size: 12px;
    }
  }

  &-list {
    >span {
      box-sizing: border-box;
      width: 103px;
      height: 37px;
      line-height: 37px;
      text-align: center;
      color: #999;
      background-color: #F2F2F2;
      display: inline-block;
      border-radius: 4px;
      margin-left: 16px;
      margin-top: 12px;
      position: relative;

      &:nth-child(3n+1) {
        margin-left: 0;
      }

      &.mult-checked::after {
        content: '';
        position: absolute;
        right: -8px;
        top: -8px;
        width: 16px;
        height: 16px;
        background: url('../../student/img/checkbox-selected.png') center center no-repeat;
      }
    }

    .active, .choice {
      color: #FFFFFF;
      background-color: #00CCFF;
    }
  }
}
</style>