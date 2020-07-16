<template>
  <div class="student-select-major">
    <div class="student-select-major-header">
      <van-sticky :offset-top="0">
        <h5-header
          :leftStatus="true"
          title="选择专业"
          rigText="保存"
          @clickHeadAction="save"
        ></h5-header>
      </van-sticky>
    </div>
    <div class="student-select-major-content">
      <van-checkbox-group v-model="result" :max="3">
        <div class="major-group" v-for="item in list" :key="item.id">
          <div class="major-title">{{ item.collegeName }}</div>
          <div class="major-list">
            <div v-for="subItem in item.majors" :key="subItem.majorId">
              <van-cell-group>
                <van-cell
                  clickable
                  :title="subItem.majorName"
                  @click="toggle(subItem, item)"
                >
                  <template #right-icon>
                    <van-checkbox
                      :name="subItem.majorId"
                      :ref="`checkboxes${subItem.majorId}`"
                      checked-color="#00CCFF"
                    />
                  </template>
                </van-cell>
              </van-cell-group>
              <div
                class="major-list-detail"
                v-show="oldArr.indexOf(subItem.majorId) !== -1"
              >
                <div>
                  <span class="major-list-detail-left">学费</span
                  ><span class="major-list-detail-right"
                    >{{ subItem.fee }}元/学期</span
                  >
                </div>
                <div>
                  <span class="major-list-detail-left">学制</span
                  ><span class="major-list-detail-right"
                    >{{ subItem.majorType }}年</span
                  >
                </div>
                <div>
                  <span class="major-list-detail-left">志愿选择</span
                  ><span class="major-list-detail-right"
                    >{{ showInfo(subItem.majorId)
                    }}<van-icon
                      name="arrow"
                      @click="changeShow(subItem.majorId)"
                  /></span>
                </div>
                <div>
                  <span class="major-list-detail-left">是否接受调配</span
                  ><span class="major-list-detail-right"
                    ><van-switch
                      v-model="subItem.isAdjust"
                      :active-value="1"
                      :inactive-value="0"
                      active-color="#00CCFF"
                      size="21px"
                      @change="changeActive(subItem.isAdjust, subItem.majorId)"
                  /></span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </van-checkbox-group>
    </div>
    <van-popup v-model="show" position="bottom" :style="{ height: '40%' }">
      <van-picker
        show-toolbar
        :columns="PickerList"
        @confirm="PickeronConfirm"
        @cancel="PickeronCancel"
      />
    </van-popup>
  </div>
</template>

<script>
import Vue from "vue";
import Header from "@/components/cheader";
import {
  Sticky,
  Cell,
  CellGroup,
  CheckboxGroup,
  Checkbox,
  Switch,
  Popup,
  Icon,
  Picker
} from "vant";
import {
  queryCollegeAndMajor,
  queryAimMajorInfo,
  saveOrUpdateAimMajorInfo
} from "../../../api/Student-info/student-select-major";
Vue.use(Sticky);
Vue.use(CellGroup);
Vue.use(Cell);
Vue.use(CheckboxGroup);
Vue.use(Checkbox);
Vue.use(Switch);
Vue.use(Popup);
Vue.use(Icon);
Vue.use(Picker);
export default {
  name: "student-select-major",
  components: {
    "h5-header": Header
  },
  data() {
    return {
      show: false,
      activeId: "",
      levelArr: [],
      isAdjustArr: [],
      oldArr: [],
      studentInfoId: null,
      studentSelect: [],
      PickerList: [
        { text: "第一志愿", level: 1, disabled: false },
        { text: "第二志愿", level: 2, disabled: false },
        { text: "第三志愿", level: 3, disabled: false }
      ],
      list: [], //所有的院系专业数据
      result: [], //选中的majorID
      form: [] //保存所需要的选中的专业信息列表数据
    };
  },
  created() {
    this.studentInfoId = this.$route.params.id;
    this.queryCollegeAndMajor();

  },
  methods: {
    //是否接受调试
    changeActive(val, id) {
      let arr = [...this.isAdjustArr];
      console.log(val, id, arr);
      let status = true;
      for (let i = 0; i < arr.length; i++) {
        if (arr[i].majorId === id) {
          arr[i].isAdjust = val;
          status = false;
        }
      }
      if (status) {
        arr.push({
          majorId: id,
          isAdjust: val
        });
      }
      this.isAdjustArr = arr;
      console.log("是否接受调配:", this.isAdjustArr);
    },
    //显示专业
    showInfo(id) {
      let arr = this.levelArr.filter(item => {
        return item.majorId === id;
      });
      if (arr.length) {
        return arr[0].text;
      } else {
        return "";
      }
    },
    /**
     * 确定专业
     */
    PickeronConfirm(value, index) {
      console.log(value, index);
      // this.PickerList[index].disabled = true;
      let arr = [...this.levelArr];
      let status = true;
      for (let i = 0; i < arr.length; i++) {
        if (arr[i].majorId === this.activeId) {
          arr[i].level = value.level;
          arr[i].text = value.text;
        }
      }
      if (status) {
        arr.push({
          majorId: this.activeId,
          level: value.level,
          text: value.text
        });
      }
      this.levelArr = arr;
      this.show = false;
    },
    /**
     * 确定专业
     */
    PickeronCancel(value, index) {
      console.log(value, index);
    },
    /**
     * 选择专业
     */
    changeShow(id) {
      this.show = true;
      this.activeId = id;
      console.log(id);
    },
    /**
     * 专业框的点击事件
     * @param {string} major 选择的专业数据信息
     * @author LGH
     */
    toggle(major) {
      let refName = `checkboxes${major.majorId}`;
      //将选中的复选框的属性取反
      this.$refs[refName][0].toggle();
      setTimeout(() => {
        this.oldArr = this.result;
      }, 200);
    },
    /**
     * 保存操作
     * @author lgh
     */
    save() {
      this.form = [];
      console.log(this.levelArr);
      console.log(this.isAdjustArr);

      this.getSeleByMajorId();
      let form = {
        aimMajors: this.form,
        studentPublicRelationId: this.$route.params.relationId
      };
      let data = [...form.aimMajors];
      for (let i = 0; i < data.length; i++) {
        let levelArrstatus = true;
        let isAdjustArrstatus = true;
        for (let j = 0; j < this.levelArr.length; j++) {
          if (this.levelArr[j].majorId === data[i].majorId) {
            data[i].level = this.levelArr[j].level;
            levelArrstatus = false;
          }
        }
        for (let j = 0; j < this.isAdjustArr.length; j++) {
          if (this.isAdjustArr[j].majorId === data[i].majorId) {
            data[i].isAdjust = this.isAdjustArr[j].isAdjust;
            isAdjustArrstatus = false;
          }
        }
        if (levelArrstatus) {
          this.$toast("请选择志愿等级");
          return;
        }
        if (isAdjustArrstatus) {
          data[i].isAdjust = 0;
        }
      }
      let arrN = [];
      for (let i = 0; i < data.length; i++) {
        if (arrN.indexOf(data[i].level) === -1) {
          arrN.push(data[i].level);
        }
      }
      if (arrN.length !== data.length) {
        this.$toast("志愿等级不能相同");
        return;
      }
      let newFrom = {
        aimMajors: data,
        studentPublicRelationId: this.$route.params.relationId
      };
      this.$message.loading("正在保存...");
      saveOrUpdateAimMajorInfo(newFrom).then(() => {
        this.$message.close();
        this.$message.success("保存成功");
        this.$router.go(-1);
      });
    },
    /**
     * 根据选择的majorId获取保存所需要的专业信息列表数据
     * @author lgh
     */
    getSeleByMajorId() {
      //遍历选中的majorId
      for (let i = 0; i < this.result.length; i++) {
        let majorId = this.result[i];
        //根据选择的majorId进行筛选专业信息列表数据
        for (let j = 0; j < this.list.length; j++) {
          var obj = this.list[j].majors.find(function(x) {
            return x.majorId == majorId;
          });
          //将筛选出来的数据存入到form里面
          if (obj) {
            let form = {
              // aimMajorId: obj.apiId,
              majorId: obj.majorId,
              studentPublicRelationId: this.$route.params.relationId
            };
            this.form.push(form);
          }
        }
      }
    },
    /**
     * 获取招生院系信息以及所有的专业信息
     * @author lgh
     */
    queryCollegeAndMajor() {
      this.$message.loading("正在加载...");
      queryCollegeAndMajor().then(res => {
        this.$message.close();
        this.list = res.data;
        this.queryAimMajorInfo(this.studentInfoId);
      });
    },
    /**
     * 根据学生信息ID查询学生选择的专业信息
     * @param {number}studentInfoId 学生信息ID
     * @author lgh
     */
    queryAimMajorInfo(studentInfoId) {
      this.$message.loading("正在加载...");
      queryAimMajorInfo(studentInfoId).then(res => {
        this.$message.close();
        this.studentSelect = res.data;
        //遍历传入学生选择的专业信息ID
        for (var i = 0; i < this.studentSelect.length; i++) {
          this.result.push(this.studentSelect[i].majorId);
          this.levelArr.push({
            majorId: this.studentSelect[i].majorId,
            level:this.studentSelect[i].level,
            text: this.PickerList[this.studentSelect[i].level - 1].text
          });
          this.isAdjustArr.push({
            majorId: this.studentSelect[i].majorId,
            isAdjust: this.studentSelect[i].isAdjust
          });
          this.oldArr.push(this.studentSelect[i].majorId)
        }
        let newList = JSON.stringify(this.list);
        newList = JSON.parse(newList)
        for (let i = 0 ; i< newList.length; i++) {
          for (let j = 0; j < newList[i].majors.length; j++ ) {
            let status = true
            for (let z= 0; z < this.isAdjustArr.length; z++) {
              if(this.isAdjustArr[z].majorId === newList[i].majors[j].majorId && this.isAdjustArr[z].isAdjust === 1) {
                newList[i].majors[j].isAdjust = 1;
                status = false
                break
              }
              if(status){
                newList[i].majors[j].isAdjust = 0;
              }
            }
          }
        }
        this.list = newList
        console.log(this.oldArr,this.isAdjustArr,this.list);
      });
    }
  }
};
</script>

<style lang="stylus" scoped>
.student-select-major {
  &-content {
    .major-group {
      .major-title {
        height: 32px;
        line-height: 32px;
        font-size: 14px;
        margin: 0 16px;
        color: #999999;
        background-color: #F2F2F2;
      }
      .major-list {
        >div{
          margin-bottom 10px
        }
        .major-list-detail {
          background-color #fff;
          padding 0 15px;
          color: #333;
          >div{
            display flex;
            height: 35px;
            align-items: center;
            >.major-list-detail-left{
                flex 1
            }
            >.major-list-detail-right {

            }
          }
        }
      }
    }
  }
}
</style>
