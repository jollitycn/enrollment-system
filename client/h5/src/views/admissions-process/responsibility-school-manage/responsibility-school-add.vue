<template>
  <div class="student-intended">
    <h5-header :leftStatus="true" title="新增学校"></h5-header>
    <div class="student-intended-content">
      <van-form @submit="onSubmit">
        <van-form v-model="form">
          <div class="form-split-title"></div>
          <van-field
            clickable
            v-model="adresses"
            label="学校所在地"
            placeholder="选择学校所在地"
            @click="adressVisible = true"
          />
          <van-popup v-model="adressVisible" position="bottom">
            <van-area
              :area-list="areaList"
              :columns-placeholder="['请选择', '请选择', '请选择']"
              @cancel="adressVisible = false"
              @confirm="getAdress"
            />
          </van-popup>
          <van-field name="radio" label="学校性质">
            <template #input>
              <van-checkbox-group v-model="form.typeIds" direction="horizontal">
                <van-checkbox
                shape="square"
                v-for="item in Typelist"
                :key="item.dictionaryId"
                :name="item.dictionaryId"
                >{{item.dataDesc}}
                </van-checkbox>
              </van-checkbox-group>
            </template>
          </van-field>
          <van-field
            v-model="form.sourceSchoolName"
            name="学校名称"
            label="学校名称"
            placeholder="请输入学校名称"
          />
          <van-field v-model="form.sourceScale" name="生源规模" label="生源规模" placeholder="填写生源规模" />
          <van-field name="radio" label="学校性质">
            <template #input>
              <van-radio-group v-model="form.relationType" direction="horizontal">
                <van-radio name="0">公立</van-radio>
                <van-radio name="1">私立</van-radio>
              </van-radio-group>
            </template>
          </van-field>
          <van-field v-model="form.qualification" name="学校资质" label="学校资质" placeholder="请填写学校资质" />
          <van-field v-model="form.address" name="学校地址" label="学校地址" placeholder="请填写学校地址" />
          <van-field
            v-model="form.superiorDept"
            name="上级主管部门"
            label="上级主管部门"
            placeholder="请填写上级主管部门"
          />
          <van-field 
          v-model="form.comment"
          autosize 
          name="说明" 
          label="说明" 
          placeholder="填写说明"
          />
        </van-form>
        <div style="margin: 16px;">
          <van-button round block type="info" native-type="onSubmit">保 存</van-button>
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

import {
  listSourceSchoolType,
  addSourceSchool
} from "@/api/sourceSchool/sourceSchool";
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
      adressVisible: false,
      schoolTypeVisible: false,
      areaList: areaList,
      Typelist: [],
      form: {
        province: "",
        city: "",
        district: "",
        typeIds: []
      }
    };
  },
  created() {
    //获取招生学校下的学校类型（数据字典配置）
    this.getSchoolType();
  },
  methods: {
    /**
     * 点击保存
     */
    onSubmit() {
      const data = { ...this.form };
      addSourceSchool(data).then(() => {
        this.$message.success(`保存成功`);
      });
      console.log("this.form", this.form);
    },
    /**
     * 获取招生学校下的学校类型（数据字典配置）
     * @param
     * @author cwz
     */
    getSchoolType() {
      listSourceSchoolType()
        .then(res => {
          this.Typelist = res.data;
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
  }
};
</script>

<style scoped lang="stylus">
.student-intended {
  min-height: calc(100vh - 1.33333rem);

  &-content {
    margin: 20px;
  }

  .form-split-title {
    height: 10px;
    line-height: 32px;
    font-size: 14px;
    margin: 0 16px;
    color: #999999;
    background-color: #F2F2F2;
  }
}
</style>