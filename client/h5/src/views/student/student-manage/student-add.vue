<template>
  <div class="student-add">
    <h5-header :leftStatus="true" title="意向生报名"></h5-header>
    <div class="student-add-content">
      <van-form @submit="onSubmit" :show-error="false" ref="form">
        <div style="position: relative;z-index: 9;background-color:#FFF">
          <!-- 基本信息 -->
          <div class="form-split-title">基本信息</div>
          <!-- 文件上传 -->
          <van-field name="form.imageUrl" label="照片">
            <template #input>
              <van-uploader
                :before-delete="deleteImg"
                :after-read="uploadImg"
                v-model="uploader"
                preview-image
                :max-count="1"
              />
            </template>
          </van-field>
          <!-- 所属学校 -->
          <van-field
            clickable
            label="所属学校"
            v-model="schoolName"
            label-class="xx"
            readonly
            placeholder="选择学校"
            @click="schoolVisible = true"
            :rules="[
            { required: true, message: '请选择学校', trigger: 'onChange' }
          ]"
          />
          <van-popup v-model="schoolVisible" position="bottom">
            <van-picker
              value-key="sourceSchoolName"
              show-toolbar
              :columns="schoolpicker"
              @cancel="schoolVisible = false"
              @confirm="onSchool"
            />
          </van-popup>
          <!-- 所在班级 -->
          <van-field
            clickable
            label-class="xx"
            readonly
            v-model="classes"
            @click="classVisible = true"
            label="所在班级"
            placeholder="选择班级"
            :rules="[{ required: true, message: '请选择班级' }]"
          />
          <van-popup v-model="classVisible" position="bottom">
            <van-picker
              show-toolbar
              value-key="className"
              :columns="classpicker"
              @cancel="classVisible = false"
              @confirm="onClass"
            />
          </van-popup>
          <van-field
            v-model="form.studentName"
            label="学生姓名"
            :rules="rules.title"
            maxlength="4"
            label-class="xx"
            placeholder="请输入姓名"
          />
          <!-- 性别 -->
          <van-field
            clickable
            readonly
            v-model="genderPicker[form.gender]"
            name="性别"
            label="性别"
            placeholder="选择性别"
            label-class="xx"
            :rules="[{ required: true, message: '请选择性别' }]"
            @click="genderVisible = true"
          />
          <van-popup v-model="genderVisible" position="bottom">
            <van-picker
              value-key="nation"
              show-toolbar
              :columns="genderPicker"
              @cancel="genderVisible = false"
              @confirm="onGender"
            />
          </van-popup>
          <!-- <van-field name="radio" required label="性别" :rules="[{ required: true, message: '请选择性别' }]">
          <template #input>
            <van-radio-group v-model="form.gender" direction="horizontal">
              <van-radio name="0" checked-color="#00CCFF">男</van-radio>
              <van-radio name="1" checked-color="#00CCFF">女</van-radio>
            </van-radio-group>
          </template>
          </van-field>-->
          <!-- 民族 -->
          <!-- <van-field v-model="form.nation" name="民族" label="民族" placeholder="请输入民族" /> -->
          <van-field
            clickable
            v-model="nation"
            label="民族"
            placeholder="选择民族"
            label-class="xx"
            readonly
            :rules="[{ required: true, message: '请选择民族' }]"
            @click="nationVisible = true"
          />
          <!-- 民族弹框 -->
          <van-popup v-model="nationVisible" position="bottom">
            <van-picker
              value-key="nation"
              show-toolbar
              :columns="natiompicker"
              @cancel="nationVisible = false"
              @confirm="onnation"
            />
          </van-popup>
          <!-- 出生年月 -->
          <van-field
            clickable
            v-model="yearMonth"
            label="出生时间"
            placeholder="选择年月日"
            :rules="[{ required: true, message: '请选择年月日' }]"
            @click="doaVisible = true"
            label-class="xx"
            readonly
          />
          <van-popup v-model="doaVisible" position="bottom">
            <van-datetime-picker
              show-toolbar
              v-model="currentDate"
              type="date"
              :formatter="formatter"
              :min-date="minDate"
              :max-date="maxDate"
              @cancel="doaVisible = false"
              @confirm="onYear"
            />
          </van-popup>
          <van-field
            v-model="form.idNo"
            label="身份证号"
            placeholder="请输入身份证号"
            :rules="rules.card"
            label-class="xx"
          />
          <!-- 健康状况 -->
          <van-field
            clickable
            readonly
            v-model="form.healthStatus"
            name="身体状况"
            label="身体状况"
            placeholder="选择身体状况"
            label-class="xx"
            @click="healthStatusVisible = true"
          />
          <van-field
            v-if="form.healthStatus == '其他'"
            placeholder="如果其他情况，请填写"
            input-align="right"
            maxlength="25"
            v-model="Hss"
          />
          <van-popup v-model="healthStatusVisible" position="bottom">
            <van-picker
              value-key="nation"
              show-toolbar
              :columns="healthStatusPicker"
              @cancel="healthStatusVisible = false"
              @confirm="onHealthStatus"
            />
          </van-popup>
          <!-- <van-field name="radio" label="身体状况">
          <template #input>
            <van-radio-group v-model="form.healthStatus" direction="horizontal">
              <van-radio name="健康" checked-color="#00CCFF">健康</van-radio>
              <van-radio name="2" checked-color="#00CCFF">其他</van-radio>
              <van-field
                v-if="form.healthStatus == 2"
                placeholder="如果其他情况，请填写"
                input-align="right"
                maxlength="25"
                v-model="Hss"
              />
            </van-radio-group>
          </template>
          </van-field>-->
          <!-- 户籍所在地 -->
          <!-- <van-field v-model="form.address" name="户籍所在地" label="户籍所在地" placeholder="请输入户籍所在地" /> -->
          <van-field
            clickable
            v-model="adresses"
            label="户籍所在地"
            placeholder="选择户籍所在地"
            :rules="[{ required: true, message: '请选择户籍所在地' }]"
            @click="adressVisible = true"
            label-class="xx"
            readonly
            :right-icon="adressVisible ? 'arrow-down' : 'arrow'"
          />
          <van-popup v-model="adressVisible" position="bottom">
            <van-area :area-list="areaList" @cancel="adressVisible = false" @confirm="onAdress" />
          </van-popup>
          <!-- 联系方式 -->
          <div class="form-split-title">联系方式</div>
          <van-field
            v-model="form.wechat"
            label="学生微信"
            placeholder="请输入学生微信"
            :rules="rules.wechat"
          />
          <van-field
            v-model="form.parentTelephone"
            name="家长手机"
            label="家长手机"
            placeholder="请输入家长手机"
            :rules="rules.telephone"
            maxlength="11"
            label-class="xx"
          />
          <van-field
            v-model="form.studentTelephone"
            name="学生手机"
            label="学生手机"
            placeholder="请输入学生手机"
            :rules="rules.isTelephone"
            maxlength="11"
          />
          <!-- 家庭住址 -->
          <van-field
            clickable
            label-class="xx"
            v-model="familyadresses"
            label="家庭住址"
            placeholder="选择家庭住址"
            @click="familyadressesVisible = true"
            readonly
            :rules="[{ required: true, message: '请选择家庭住址' }]"
            :right-icon="familyadressesVisible ? 'arrow-down' : 'arrow'"
          />
          <van-popup v-model="familyadressesVisible" position="bottom">
            <van-area
              :area-list="areaList"
              @cancel="familyadressesVisible = false"
              @confirm="onfamilyadresses"
            />
          </van-popup>
          <van-field
            v-model="form.address"
            input-align="right"
            placeholder="街道、小区、门牌号"
            maxlength="30"
          />
          <!-- 家庭信息 -->
          <div class="form-split-title">家庭信息</div>
          <!-- 关系 -->
          <van-field name="famRelation" label="关系" value="父亲" readonly>
            <!-- <template #input>
            <van-radio-group
              @change="isEquallyFam"
              v-model="familyform.relation"
              direction="horizontal"
            >
              <van-radio name="0" checked-color="#00CCFF">父亲</van-radio>
              <van-radio name="1" checked-color="#00CCFF">母亲</van-radio>
            </van-radio-group>
            </template>-->
          </van-field>
          <van-field
            v-model="familyform.name"
            name="家长姓名"
            label="家长姓名"
            placeholder="请填写家长姓名"
            maxlength="4"
          />
          <van-field
            v-model="familyform.telephone"
            name="手机号"
            label="手机号"
            placeholder="联系方式"
            :rules="rules.isTelephone"
            maxlength="11"
          />
          <van-field
            v-model="familyform.company"
            name="工作单位"
            label="工作单位"
            placeholder="工作单位"
            maxlength="30"
          />
          <van-field
            v-model="familyform.position"
            name="职务"
            label="职务"
            placeholder="职务"
            maxlength="10"
          />
          <van-field
            v-model="familyform.location"
            name="户籍所在地"
            label="户籍所在地"
            readonly
            placeholder="请选择户籍所在地"
            @click="adressVisible1 = true"
            maxlength="30"
          />
          <van-popup v-model="adressVisible1" position="bottom">
            <van-area :area-list="areaList" @cancel="adressVisible1 = false" @confirm="onAdress1" />
          </van-popup>
          <!-- <van-divider :style="{margin: '0 16px' }" /> -->
          <van-field name="famRelation" label="关系" value="母亲" readonly>
            <!-- <template #input>
            <van-radio-group
              @change="isEquallyFam"
              v-model="familyform1.relation"
              direction="horizontal"
            >
              <van-radio name="0" checked-color="#00CCFF">父亲</van-radio>
              <van-radio name="1" checked-color="#00CCFF">母亲</van-radio>
            </van-radio-group>
            </template>-->
          </van-field>
          <van-field v-model="familyform1.name" label="家长姓名" placeholder="请填写家长姓名" maxlength="4" />
          <van-field
            v-model="familyform1.telephone"
            label="手机号"
            placeholder="联系方式"
            :rules="rules.isTelephone"
            maxlength="11"
          />
          <van-field v-model="familyform1.company" label="工作单位" placeholder="工作单位" maxlength="30" />
          <van-field v-model="familyform1.position" label="职务" placeholder="职务" maxlength="10" />
          <van-field
            v-model="familyform1.location"
            label="户籍所在地"
            readonly
            placeholder="请选择户籍所在地"
            @click="adressVisible2 = true"
            maxlength="30"
          />
          <van-popup v-model="adressVisible2" position="bottom">
            <van-area :area-list="areaList" @cancel="adressVisible2 = false" @confirm="onAdress2" />
          </van-popup>
        </div>
        <div class="student-add-save">
          <button class="action-save-btn" native-type="submit">保存</button>
        </div>
      </van-form>
    </div>
  </div>
</template>

<script>
import { dateFormat } from "@/utils/format";
import Header from "@/components/cheader";
import Vue from "vue";
import { Picker } from "vant";
import { DatetimePicker } from "vant";
import { Area } from "vant";
import { Divider } from "vant";
import { Form, Field, Button, Uploader, RadioGroup, Radio, Popup } from "vant";
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
Vue.use(Divider);

import {
  listSourceSchoolPage,
  listClassPage,
  addStudentInfoAPP
} from "../../../api/Student-info/Student-info";
import { uploadAdd } from "../../../api/Upload/upload";
import { sysnation } from "../../../api/Sys-nation/sys-nation";
import areaList from "@/assets/json/area";
import rulesFrom from "@/assets/rules/rulesFrom";

export default {
  name: "student-add",
  components: {
    h5Header: Header
  },
  data() {
    return {
      adressVisible1: false,
      adressVisible2: false,
      minDate: new Date(1980, 0, 1),
      maxDate: new Date(2050, 10, 1),
      schoolVisible: false,
      classVisible: false,
      doaVisible: false,
      adressVisible: false,
      provinceVisible: false,
      cityVisible: false,
      districtVisible: false,
      genderVisible: false,
      healthStatusVisible: false,
      nationVisible: false,
      familyadressesVisible: false,
      currentDate: new Date(),
      schoolpicker: "",
      natiompicker: "",
      healthStatusPicker: ["健康", "其他"],
      genderPicker: ["男", "女"],
      classpicker: [],
      schoolName: "",
      schoolId: "",
      uploader: [],
      adresses: "",
      familyadresses: "",
      classes: "",
      classId: "",
      nation: "",
      yearMonth: "",
      columns: "",
      adress: "",
      areaList: areaList,
      Hss: "",
      form: {
        imageUrl: "",
        recruitSchoolId: "",
        classId: "",
        nation: "",
        province: "",
        city: "",
        district: "",
        familyProvince: "",
        familyCity: "",
        familyDistrict: "",
        familyMembers: []
      },
      familyform: { relation: 0 },
      familyform1: { relation: 1 },
      query: {
        sourceSchoolId: "",
        pageNum: 1,
        pageSize: 100
      },
      rules: {
        title: [{ validator: this.isStudentName, message: "请输入1-10个字符" }],
        wechat: [{ validator: this.isWechat, message: "请输入正确微信号" }],
        isTelephone: [
          { validator: this.isTelephoneRule, message: "请输入正确手机号" }
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
            pattern: rulesFrom.phone.rules,
            message: rulesFrom.phone.message
          }
        ],
        card: [
          {
            pattern: rulesFrom.card.rules,
            message: rulesFrom.card.message
          }
        ],
        famRelation: [
          {
            validator: this.famRelation,
            message: "不可选择相同的家庭信息关系！"
          }
        ]
      }
    };
  },
  created() {
    //获取生源学校列表
    this.getSchoolPage();
    //获取民族
    this.getnationPage();
  },
  methods: {
    /**
     * 户籍地址弹框确定
     * @author cwz
     */
    onAdress1(adresses) {
      console.log(adresses);
      this.adressVisible1 = false;
      let name = adresses[1].name ? "/" + adresses[1].name : adresses[1].name;
      let name2 = adresses[2].name ? "/" + adresses[2].name : adresses[2].name;
      this.familyform.location = adresses[0].name + name + name2;
    },
    /**
     * 户籍地址弹框确定
     * @author cwz
     */
    onAdress2(adresses) {
      console.log(adresses);
      this.adressVisible2 = false;
      let name = adresses[1].name ? "/" + adresses[1].name : adresses[1].name;
      let name2 = adresses[2].name ? "/" + adresses[2].name : adresses[2].name;
      this.familyform1.location = adresses[0].name + name + name2;
    },
    //学校姓名校验
    isStudentName(val) {
      return val.length < 10 && val.length > 0;
    },
    //微信号验证
    isWechat(val) {
      if (!val) {
        return true;
      }
      let rules = /^[a-zA-Z]([-_a-zA-Z0-9]{5,20})$/;
      if (rules.test(val)) {
        return true;
      }
      return false;
    },
    //验证手机
    isTelephoneRule(val) {
      if (!val) {
        return true;
      }
      let rules = rulesFrom.phone.rules;
      if (rules.test(val)) {
        return true;
      }
      return false;
    },
    //验证家庭关系重复
    famRelation() {
      if (this.familyform1.relation == this.familyform.relation) {
        return false;
      }
      return true;
    },
    /**
     * 若选择其他家庭关系，两个关系不同时，则取消错误提示
     * @author lgh
     */
    isEquallyFam() {
      if (this.familyform1.relation != this.familyform.relation) {
        this.$refs.form.resetValidation("famRelation");
      }
    },
    /**
     * 保存
     * @param {obj} data  this.form
     * @author cwz
     */
    onSubmit() {
      if (this.form.healthStatus == "其他") {
        this.form.healthStatus = this.Hss;
      }
      this.form.familyMembers = [
        { ...this.familyform },
        { ...this.familyform1 }
      ];
      this.form.sourceSchoolId = this.schoolId;
      this.form.doa = this.yearMonth;
      const data = { ...this.form };
      addStudentInfoAPP(data).then(() => {
        this.$message.success(`保存成功`);
        //跳转到学生管理页面
        this.$router.push({ path: "/student-manage/student-list" });
      });
    },
    /**
     * 获取生源学校
     * @param
     * @author cwz
     */
    getSchoolPage() {
      this.$message.loading("正在加载学校数据...");
      listSourceSchoolPage()
        .then(res => {
          this.schoolpicker = res.data;
          this.$message.close();
          if (res.data.length) {
            this.schoolName = res.data[0].sourceSchoolName;
            // this.form.recruitSchoolId = schoolName.sourceSchoolId;
            this.schoolId = res.data[0].sourceSchoolId;
            this.getClassPage(true);
          } else {
            this.schoolName = "";
            // this.form.recruitSchoolId = schoolName.sourceSchoolId;
            this.schoolId = "";
            this.form.classId = "";
            this.form.className = "";
            this.classes = "";
          }
        })
        .catch(err => {
          console.error(err);
        });
    },
    /**
     * 获取班级
     * @param query {} sourceSchoolId学校id
     * @param {boolean}isClick 判断是否为点击所属学校后获取所在班级
     * @author cwz
     */
    getClassPage(isClick) {
      this.query.sourceSchoolId = this.schoolId;
      this.$message.loading("正在获取班级数据...");
      if (this.query.sourceSchoolId.length != 0) {
        listClassPage(this.query).then(res => {
          this.$message.close();
          this.classpicker = res.data.records;
          if (isClick) {
            //判断获取的班级列表数据是否存在
            if (this.classpicker[0] != undefined) {
              //切换学校的同时切换默认选择第一个班级
              this.form.classId = res.data.records[0].classId;
              this.form.className = res.data.records[0].className;
              this.classes = res.data.records[0].className;
              console.log(res);
            } else {
              this.form.classId = "";
              this.form.className = "";
              this.classes = "";
            }
          }
        });
      }
    },
    /**
     * 获取民族
     * @param
     * @author cwz
     */
    getnationPage() {
      sysnation()
        .then(res => {
          this.natiompicker = res.data;
        })
        .catch(err => {
          console.error(err);
        });
    },
    /**
     * 图片上传
     * @author cwz
     */
    uploadImg(option) {
      this.uploadAdd(option.file, imgUrl => {
        console.log("imgUrl", imgUrl);
        console.log("option.file", option.file);
        this.form.imageUrl = imgUrl;
      });
    },
    /**
     * 上传图片接口
     * @param file {object} 二进制文件数据
     * @param callback{}
     * @author cwz
     */
    uploadAdd(file, callback) {
      let formData = new FormData();
      let type = file.name.substring(
        file.name.indexOf(".") + 1,
        file.name.length
      );
      formData.append("file", file);
      formData.append("fileType", type);
      this.$message.loading("正在上传图片...");
      uploadAdd(formData)
        .then(res => {
          this.$message.close();
          this.$message.success("上传成功!");
          callback(res.data);
        })
        .catch(err => {
          console.error(err);
          this.$message.close();
          this.$message.error("上传失败!");
        });
    },
    /**
     * 删除图片后的操作
     * @author lgh
     */
    deleteImg() {
      this.form.imageUrl = "";
      this.uploader = [];
      console.log(this.form.imageUrl, 1111);
    },
    /**
     * 上传图片进行格式分析
     * @param file(String) 图片内容
     * @author cwz
     */
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg" || "image/png";
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG/PNG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },

    /**
     * 学校弹框确定
     * @author cwz
     */
    onSchool(schoolName) {
      console.log(schoolName, "==>schoolname");

      this.schoolName = schoolName.sourceSchoolName;
      // this.form.recruitSchoolId = schoolName.sourceSchoolId;
      this.schoolId = schoolName.sourceSchoolId;
      this.schoolVisible = false;
      this.getClassPage(true);
    },
    /**
     * 班级弹框确定
     * @author cwz
     */
    onClass(classes) {
      this.classes = classes.className;
      this.form.classId = classes.classId;
      this.classVisible = false;
    },
    /**
     * 性别弹框确定
     * @author cwz
     */
    onGender(gender, index) {
      this.form.gender = index;
      this.genderVisible = false;
    },
    /**
     * 身体状况弹框确定
     * @author cwz
     */
    onHealthStatus(healthStatus) {
      this.form.healthStatus = healthStatus;
      this.healthStatusVisible = false;
    },
    /**
     * 民族弹框确定
     * @author cwz
     */
    onnation(nation) {
      console.log(nation);
      this.nation = nation.nation;
      this.form.nation = nation.nation;
      this.nationVisible = false;
    },
    /**
     * 出生年月日弹框确定
     * @author cwz
     */
    onYear(date) {
      this.yearMonth = dateFormat(date, "YYYY-mm-dd");
      this.doaVisible = false;
    },
    /**
     * 户籍地址弹框确定
     * @author cwz
     */
    onAdress(adresses) {
      this.adresses = adresses[0].name + adresses[1].name + adresses[2].name;
      this.form.province = adresses[0].name;
      this.form.city = adresses[1].name;
      this.form.district = adresses[2].name;
      this.adressVisible = false;
    },
    /**
     * 家庭住址弹框确定
     * @author cwz
     */
    onfamilyadresses(familyadresses) {
      this.familyadresses =
        familyadresses[0].name +
        familyadresses[1].name +
        familyadresses[2].name;
      this.form.familyProvince = familyadresses[0].name;
      this.form.familyCity = familyadresses[1].name;
      this.form.familyDistrict = familyadresses[2].name;
      this.familyadressesVisible = false;
    },
    //出生年月
    formatter(type, val) {
      if (type === "year") {
        let year = `${val}年`;
        return year;
      } else if (type === "month") {
        let month = `${val}月`;
        return month;
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

>>>.van-cell:not(:last-child)::after {
  left: 0px;
}

.student-add {
  min-height: calc(290vh - 340px);

  >>>.van-radio__icon--checked+span {
    color: #00CCFF;
  }

  >>>.van-cell {
    line-height: 34px;

    .van-cell__title span {
      color: #333;
      font-weight: 600;
      font-size: 15px;
    }

    .van-cell__value {
      font-size: 15px;
    }
  }

  &-content {
    pading: 16px 0;
    background: #FFF;
  }

  &-save {
    margin-top: 50px;
    position: relative;
    z-index: 1;

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

  .form-split-title {
    height: 32px;
    line-height: 32px;
    font-size: 14px;
    padding: 0 16px;
    color: #999999;
    background-color: #F2F2F2;
  }
}
</style>
