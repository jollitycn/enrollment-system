<template>
  <div>
    <div class="info">
      <van-sticky :offset-top="0">
        <h5-header :leftStatus="true" title="基本信息"></h5-header>
      </van-sticky>
      <div class="info-from">
        <van-form validate-first @submit="submit">
          <!-- 通过 pattern 进行正则校验 -->
          <van-field
            v-model="from.imageUrl"
            label="照片"
            name="imageUrl"
            placeholder="学生照片"
          >
            <template #input>
              <van-uploader
                v-model="fileList"
                :before-read="beforeRead"
                multiple
                :after-read="uploadAdd"
                :max-count="1"
                :before-delete="beforeDelete"
              />
            </template>
          </van-field>
          <div class="info-new">
            <van-field
              v-model="from.studentName"
              label="学生姓名"
              name="studentName"
              placeholder="学生姓名"
              maxLength="4"
              label-class="xx"
              :rules="rules.studentName"
              input-align="right"
            />
            <van-field
              name="gender"
              v-model="gender"
              label-class="xx"
              readonly
              label="性别"
              input-align="right"
              :rules="rules.sex"
              @click="showSex = true"
              :right-icon="img"
              placeholder="请选择性别"
            >
            </van-field>
            <van-field
              v-model="from.nation"
              label="民族"
              name="nation"
              readonly
              label-class="xx"
              placeholder="请选择民族"
              @click="showNation = true"
              :rules="[{ required: true, message: '请选择民族' }]"
              :right-icon="img"
              input-align="right"
            />
            <van-field
              v-model="from.doa"
              label="出生年月"
              name="doa"
              label-class="xx"
              placeholder="请选择"
              readonly
              class="activeClass"
              :rules="rules.doa"
              @click="activeDate"
              :right-icon="img"
              input-align="right"
            >
            </van-field>
            <van-field
              v-model="from.idNo"
              label="身份证号"
              name="idNo"
              placeholder="请输入身份证号"
              :rules="rules.idNo"
              input-align="right"
              label-class="xx"
            />
            <van-field
              name="healthStatus"
              label="身体状况"
              readonly
              input-align="right"
              @click="healthStatusNew = true"
              :right-icon="img"
              v-model="from.healthStatus"
              placeholder="请选择身体状况"
            >
            </van-field>
            <van-field
              placeholder="如其它情况，请填写"
              maxlength="25"
              input-align="right"
              name="HealthStatusText"
              v-show="from.healthStatus !== '健康'"
              v-model="HealthStatusText"
              label=" "
            />
            <van-field
              v-model="location"
              name="location"
              label="户籍所在地"
              label-class="xx"
              placeholder="请选择户籍所在地"
              readonly
              class="activeClass"
              :rules="rules.location"
              @click="showArea = true"
              :right-icon="img"
              input-align="right"
            >
            </van-field>
          </div>
          <div class="information-btn">
            <van-button
              :disabled="isLoading"
              block
              color="#00CCFF"
              native-type="submit"
            >
              保存
            </van-button>
          </div>
        </van-form>
      </div>
      <!--      日期选择s-->
      <van-popup v-model="showDate" position="bottom">
        <van-datetime-picker
          @confirm="confirmDate"
          @cancel="showDate = false"
          type="date"
          :min-date="minDate"
          :max-date="maxDate"
        />
      </van-popup>
      <!--      日期选择e-->
      <!--      户籍地址选择s-->
      <van-popup v-model="showArea" position="bottom">
        <van-area
          :area-list="areaList"
          @confirm="onConfirmarea"
          @cancel="showArea = false"
        />
      </van-popup>
      <!--      户籍地址选择e-->
      <!--      民族选择-->
      <van-popup v-model="showNation" position="bottom">
        <van-picker
          value-key="nation"
          show-toolbar
          :columns="nationList"
          @confirm="NationConfirmarea"
          @cancel="showNation = false"
        />
      </van-popup>
      <van-popup v-model="showSex" position="bottom">
        <van-picker
          show-toolbar
          :default-index="from.gender"
          :columns="SexList"
          @confirm="showSexConfirmarea"
          @cancel="showSex = false"
        />
      </van-popup>
      <van-popup v-model="healthStatusNew" position="bottom">
        <van-picker
          show-toolbar
          :default-index="from.gender"
          :columns="jkList"
          @confirm="healthStatusConfirmarea"
          @cancel="healthStatusNew = false"
        />
      </van-popup>
    </div>
  </div>
</template>

<script>
import {
  Field,
  Button,
  Popup,
  Form,
  Sticky,
  DatetimePicker,
  Area,
  Uploader,
  Picker
} from "vant";
import Header from "@/components/cheader";
import { card } from "../../../../../../assets/rules/rulesFrom";
import areaList from "../../../../../../assets/json/area";
import { nationList, uploadAdd } from "@/api/public/public";
import { imgDir } from "@/config/index";
import {
  addStudentInfo,
  queryStudentInfo,
  updateStudentInfo
} from "@/api/signList/backups/backups";
import { mapMutations } from "vuex";
export default {
  components: {
    vanField: Field,
    vanButton: Button,
    vanPopup: Popup,
    vanForm: Form,
    vanSticky: Sticky,
    vanDatetimePicker: DatetimePicker,
    vanArea: Area,
    vanUploader: Uploader,
    vanPicker: Picker,
    h5Header: Header
  },
  name: "info",
  data() {
    return {
      gender: "",
      SexList: ["男", "女"],
      jkList: ["健康", "其他"],
      healthStatusNew: false,
      img: require("../../../../../../assets/image/right.png"),
      showSex: false,
      isLoading: false,
      isAdd: true,
      fileList: [],
      showDate: false, //时间选择器
      showArea: false,
      areaList: areaList, // 数据格式见 Area 组件文档
      minDate: new Date(1990, 0, 1),
      maxDate: new Date(2025, 10, 1),
      currentDate: new Date(),
      location: "",
      nationList: [], //民族列表
      showNation: false,
      HealthStatusText: "",
      isShowHealthStatus: false,
      from: {
        studentName: "", //学生姓名
        gender: 0, // 性别 0为男 1为女
        nation: "汉族", //民族
        doa: this.setDate(new Date()), //出生年月日
        idNo: "", //身份证号
        province: "",
        city: "",
        district: "",
        imageUrl: "" // 学生照片
      },
      rules: {
        studentName: [
          { validator: this.isStudentName, message: "请输入1-10个字符" }
        ],
        idNo: [{ validator: this.isIdNo, message: card.message }],
        location: [{ validator: this.isLocation, message: "请选择地址" }],
        doa: [{ validator: this.isDoa, message: "请选择出生年月" }],
        sex: [{ validator: this.isSex, message: "请选择性别" }]
      },
      showToast: null
    };
  },
  beforeRouteLeave(to, from, next) {
    if(this.showSex || this.showNation || this.showDate || this.healthStatusNew || this.showArea ) {
      this.showSex = false;
      this.showNation = false;
      this.showDate = false;
      this.healthStatusNew = false;
      this.showArea = false;
      next(false);
    }else {
      next();
    }
  },
  methods: {
    ...mapMutations(["setUserImage"]),
    // 删除头像
    beforeDelete() {
      this.from.imageUrl = "";
      return true
    },
    //性别选择
    showSexConfirmarea(value, index) {
      console.log(value, index);
      this.gender = value;
      this.from.gender = index;
      this.showSex = false;
    },
    //健康选择
    healthStatusConfirmarea(value) {
      console.log(value);
      this.from.healthStatus = value;
      this.healthStatusNew = false;
    },
    uploadAdd(item) {
      let file = item.file;
      item.status = "uploading";
      item.message = "上传中...";
      console.log(file);
      let formData = new FormData();
      let type = file.name.substring(
        file.name.indexOf(".") + 1,
        file.name.length
      );
      formData.append("file", file);
      formData.append("fileType", type);
      uploadAdd(formData)
        .then(res => {
          console.log(res);
          // callback(res.data);
          item.status = "done";
          item.message = "上传成功";
          this.fileList[0] = { url: imgDir + res.data };
          this.from.imageUrl = res.data;
        })
        .catch(err => {
          console.error(err);
          item.status = "failed";
          item.message = "上传失败";
        });
    },
    //上传限制
    beforeRead(file) {
      let name = file.name.substr(
        file.name.lastIndexOf(".") + 1,
        file.name.length
      );
      console.log(name);
      let imgArr = ["png", "jpg", "jpeg", "bmp"];
      if (imgArr.indexOf(name) === -1) {
        this.$toast("请选择png、jpg、jpeg、bmp格式图片");
        return false;
      }
      if (file.size > 1024 * 1024 * 5) {
        this.$toast("请选择小于5MB的照片");
        return false;
      }
      return true;
    },
    //确定民族
    NationConfirmarea(item) {
      console.log(item);
      this.from.nation = item.nation;
      this.showNation = false;
    },
    //点击显示日期选择器
    activeDate() {
      this.showDate = true;
    },
    //获取所有民族
    nationAllList() {
      nationList().then(res => {
        console.log(res.data, "民族");
        this.nationList = res.data;
      });
    },
    //格式化时间
    setDate(time) {
      const data = new Date(time);
      const year = data.getFullYear();
      const month =
        data.getMonth() + 1 > 9
          ? data.getMonth() + 1
          : "0" + (data.getMonth() + 1);
      const day = data.getDate() > 9 ? data.getDate() : "0" + data.getDate();
      return year + "-" + month + "-" + day;
    },
    //确定选择日期
    confirmDate(val) {
      this.from.doa = this.setDate(val);
      this.showDate = false;
    },
    //确定户籍地址
    onConfirmarea(values) {
      this.location = values.map(item => item.name).join("/");
      this.showArea = false;
    },
    //学校姓名校验
    isStudentName(val) {
      return val.length < 11 && val.length > 0;
    },
    //地址校验
    isLocation(val) {
      return val.length > 4;
    },
    //身份证校验
    isIdNo(val) {
      if (card.rules.test(val)) {
        return true;
      }
      return false;
    },
    isDoa(val) {
      if (val) {
        return val.length > 4;
      }
      return false;
    },
    isSex(val) {
      return val === "男" || val === "女";
    },
    //提交表达
    submit(data) {
      if (this.isLoading) {
        return;
      }
      this.isLoading = true;
      this.showToast = this.$toast.loading({
        duration: 0, // 持续展示 toast
        forbidClick: true,
        message: "提交中"
      });
      //表单验证通过，二次拦截
      let from = data;
      let allLocation = {
        province: from.location.substring(0, from.location.indexOf("/")),
        city: from.location.substring(
          from.location.indexOf("/") + 1,
          from.location.lastIndexOf("/")
        ),
        district: from.location.substring(
          from.location.lastIndexOf("/") + 1,
          from.location.length
        )
      };
      let newData = { ...this.from, ...from, ...allLocation };
      if (newData.healthStatus === "其他") {
        newData.healthStatus = newData.HealthStatusText;
      }
      newData.imageUrl = this.from.imageUrl;
      delete newData.location;
      delete newData.HealthStatusText;
      console.log(newData, 987);
      newData.gender = newData.gender === "男" ? 0 : 1;
      if (this.isAdd) {
        console.log("新增");
        this.addStudentInfo(newData);
      } else {
        console.log(newData, 1231);
        this.updateStudentInfo(newData);
      }
    },
    //新增学生基本信息
    addStudentInfo(data) {
      addStudentInfo(data)
        .then(res => {
          console.log(res);
          this.showToast.clear();
          this.isLoading = false;
          this.$toast({
            message: "新增成功",
            onClose: () => {
              this.$router.go(-1);
            }
          });
          this.setUserImage(data.imageUrl);
        })
        .catch(err => {
          console.log(err);
          this.showToast.clear();
          this.$toast("新增失败");
          this.isLoading = false;
        });
    },
    //获取学生基本信息
    queryStudentInfo() {
      queryStudentInfo()
        .then(res => {
          console.log(res.data);
          if (res.data) {
            this.isAdd = false;
            this.from = res.data;
            let province = res.data.province ? res.data.province + "/" : "";
            let city = res.data.city ? res.data.city + "/" : "";
            let district = res.data.district ? res.data.district : "";
            this.location = province + city + district;
            if (!res.data.imageUrl) {
              this.fileList = [];
            } else {
              this.fileList = [{ url: imgDir + res.data.imageUrl }];
            }
            if (this.from.healthStatus !== "健康") {
              this.HealthStatusText = this.from.healthStatus;
              this.from.healthStatus = "其他";
            }
            this.gender = this.SexList[this.from.gender];
            this.setUserImage(res.data.imageUrl);
          } else {
            this.isAdd = true;
          }
        })
        .catch(err => {
          console.log(err);
        });
    },
    //编辑学生基本信息
    updateStudentInfo(data) {
      updateStudentInfo(data)
        .then(res => {
          console.log(res, "编辑成功");
          this.showToast.clear();
          this.isLoading = false;
          this.$toast({
            message: "编辑成功",
            onClose: () => {
              this.$router.go(-1);
            }
          });
          this.setUserImage(data.imageUrl);
        })
        .catch(err => {
          console.log(err);
          this.showToast.clear();
          this.isLoading = false;
          this.$toast("编辑失败");
        });
    }
  },
  created() {
    //获取所有民族
    this.nationAllList();
  },
  activated() {
    //获取学生基本信息
    this.queryStudentInfo();
  }
};
</script>

<style scoped lang="stylus">
.info
  font-size fontMedSize
  margin 20px 0px
  background bgColor
  min-height calc(100vh - 85px)
  padding-bottom: 30px;
  .activeClass
    display flex
    align-items center
    .activeBtn
      border 0
      color activeBtnColor
  .info-new
    position relative
    z-index 9
  .information-btn
      position: fixed;
      bottom: 0;
      width: 100%;
      height: 49px;
      z-index: 3;
      >>>button
          height: 49px;
          >span
            font-size 18px
            line-height: 50px;
  >>>.van-icon-arrow
        font-size: 10px;
        transform: scale(1);
  >>>.van-icon-arrow-down
        font-size: 10px;
        transform: scale(1)
  >>>.van-cell
        display flex
        min-height 55px
        align-items: center;
  >>>.van-cell__title
        >span
          font-size 15px
          color #333
          font-weight bold
  .info-new
    >>>img
        width: 6px;
  .info-from
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
