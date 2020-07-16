<template>
  <div class="page-container">
    <div class="crumbs">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item :to="{ path:'/enrollmentPlanList' }">招生计划管理</el-breadcrumb-item>
        <el-breadcrumb-item>{{title + '计划'}}</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="tabs">
      <el-tabs v-model="activeName" @tab-click="handleClick" type="card">
        <!-- 基本信息 -->
        <el-tab-pane label="基本信息" name="first">
          <el-form class="form-card" :rules="planRules" ref="planForm" :model="planForm" label-width="120px" size="mini"
            label-position="right">
            <el-row :gutter="20">
              <el-col :span="7">
                <el-form-item label="学期" prop="semester">
                  <el-select class="wid195" v-model="planForm.semester" placeholder="请选择学期" :disabled='isOrNotEdit'>
                    <el-option v-for="item in semesterQuery" :key="item.name" :label="item.name" :value="item.name">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="季度选择" prop="season">
                  <el-select style="width: 80%;" v-model="planForm.season" placeholder="请选择季度" :disabled='isOrNotEdit'>
                    <el-option v-for="item in seasonQuery" :key="item.seasonId" :label="item.season"
                      :value="item.seasonId"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col>
                <el-form-item label="标题" prop="title">
                  <el-input v-model="planForm.title" style="width: 60%;" placeholder="请输入标题" :disabled='isOrNotEdit'>
                  </el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="7">
                <el-form-item label="招生开始日期" prop="startTime">
                  <el-date-picker class="wid195" format="yyyy年MM月dd日" value-format="yyyy-MM-dd HH:mm:ss"
                    v-model="planForm.startTime" type="datetime" placeholder="选择开始日期时间" :disabled='isOrNotEdit'>
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="招生结束日期" prop="endTime">
                  <el-date-picker style="width: 112%;" format="yyyy年MM月dd日" value-format="yyyy-MM-dd HH:mm:ss"
                    v-model="planForm.endTime" type="datetime" placeholder="选择结束日期时间" :disabled='isOrNotEdit'>
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :span="7">
                <el-form-item label="招生负责人" prop="chargerId">
                  <el-select class="wid195" v-model="planForm.chargerId" placeholder="请选择(来源学校负责人)"
                    :disabled='isOrNotEdit'>
                    <!-- <el-option  label="请选择(来源学校负责人)" value="0"></el-option> -->
                    <el-option v-for="item in chargerQuery" :key="item.chargerId" :label="item.chargerName"
                      :value="item.chargerId"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="9">
                <el-form-item label="联系电话" prop="telephoneNumber">
                  <el-input v-model="planForm.telephoneNumber" maxlength="13" show-word-limit style="width: 93%;"
                    placeholder="请输入联系电话" :disabled='isOrNotEdit'></el-input>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-form-item label="说明" prop="comment">
                <quill-editor ref="quillEditor" v-model="planForm.comment" :options="editorOption"
                  style="min-height: 200px;width: 60%;" :disabled='isOrNotEdit'>
                </quill-editor>
              </el-form-item>
            </el-row>
            <el-form-item>
              <el-button align="center" style="width: 100px;" type="primary" @click="addOrEditPlanInfo()"
                v-if="!isOrNotEdit">保存</el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <!-- 学校简介 -->
        <el-tab-pane label="学校简介" name="second" :disabled="!addShow">
          <el-form class="form-card" :rules="schoolRules" ref="schoolForm" :model="schoolForm" label-width="120px"
            size="mini" label-position="right">
            <el-row :gutter="10">
              <el-col :span="8">
                <el-form-item label="学校名称" prop="recruitSchoolName">
                  <el-input v-model="schoolForm.recruitSchoolName" placeholder="请输入学校名称" :disabled='isOrNotEdit'>
                  </el-input>
                </el-form-item>
                <el-form-item label="校长" prop="principal">
                  <el-input v-model="schoolForm.principal" placeholder="请输入校长名称" :disabled='isOrNotEdit'></el-input>
                </el-form-item>
                <el-form-item label="联系电话" prop="telephoneNumber">
                  <el-input v-model="schoolForm.telephoneNumber" placeholder="请输入联系电话" maxlength="13" show-word-limit
                    :disabled='isOrNotEdit'>
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :span="7" class="img">
                <el-form-item prop="recruitSchoolLogo">
                  <el-upload v-if="!isSee" class="avatar-uploader" action="/upload" :show-file-list="false"
                    :http-request="uploadImg" :before-upload="beforeAvatarUpload" :disabled='isOrNotEdit'>
                    <img v-if="schoolForm.recruitSchoolLogo" :src="schoolForm.recruitSchoolLogo | imgUrlFilter"
                      class="avatar" />
                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                  </el-upload>
                  <img v-else :src="schoolForm.recruitSchoolLogo | imgUrlFilter" class="avatar" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-form-item label="学校地址" prop="address">
              <el-input v-model="schoolForm.address" class="wid50" placeholder="请输入学校地址" :disabled='isOrNotEdit'>
              </el-input>
            </el-form-item>
            <el-form-item label="学校简介" prop="description">
              <quill-editor ref="quillEditor" v-model="schoolForm.description" :options="editorSchoolOption"
                style="min-height: 200px;width: 50%;" :disabled='isOrNotEdit'>
              </quill-editor>
            </el-form-item>
            <el-form-item>
              <el-button align="center" style="width: 100px;" type="primary" @click="addOrEditPlanSchoolIntroduction()"
                v-if="!isOrNotEdit">保存
              </el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <!-- 招生简介 -->
        <el-tab-pane label="招生简介" name="third" :disabled="!addShow">
          <el-form class="form-card" ref="planRecruitFrom" :rules='recruitRules' :model="planRecruitFrom"
            label-width="120px" size="mini" label-position="right">
            <el-form-item label="招生对象" prop="recruitTarget">
              <el-input v-model="planRecruitFrom.recruitTarget" class="wid60" :disabled='isOrNotEdit'
                placeholder="请输入招生对象"></el-input>
            </el-form-item>
            <el-form-item label="注意事项" prop="attention">
              <el-input v-model="planRecruitFrom.attention" type="textarea" class="wid60"
                :autosize="{ minRows: 4, maxRows: 13}" placeholder="请输入注意事项" :disabled='isOrNotEdit'>
              </el-input>
            </el-form-item>
            <el-form-item label="报到与录取" prop="register">
              <el-input v-model="planRecruitFrom.register" class="wid60" :disabled='isOrNotEdit'
                placeholder="请输入报道与录取信息"></el-input>
            </el-form-item>
            <el-form-item label="招生计划" prop="planDescription">
              <quill-editor ref="quillEditor" v-model="planRecruitFrom.planDescription" :options="editorOption"
                style="min-height: 200px;width: 60%;" :disabled='isOrNotEdit'>
              </quill-editor>
            </el-form-item>
            <el-form-item>
              <el-button align="center" style="width: 100px;" type="primary" @click="addOrEditPlanRecruitInfo()"
                v-if="!isOrNotEdit">保存
              </el-button>
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <!-- 院系管理 -->
        <!-- 院系管理列表 -->
        <el-tab-pane label="院系管理" name="fourth" :disabled='!faculty' v-if='faculty'>
          <div>
            <div class="handle-box" align="right">
              <!-- <el-button type="primary" icon="el-icon-search" @click="">新建院系</el-button> -->
              <!-- <el-button type="primary" icon="el-icon-refresh" @click="">复制模板</el-button>
              <el-button type="primary" icon="el-icon-search" @click="">转为模板</el-button> -->
            </div>
            <div>
              <el-table :data="tableData" style="width: 100%">
                <el-table-column fixed width="55" label="序号" align="center">
                  <template slot-scope="scope">{{scope.$index + 1}}</template>
                </el-table-column>
                <el-table-column width="100" label="院系名称" prop="collegeName" align="center"></el-table-column>
                <el-table-column label="院系负责人" prop="collegeManager" align="center" width='110'></el-table-column>
                <el-table-column label="院系名称" prop="apiCollegeName" align="center"></el-table-column>
                <el-table-column label="专业数量" prop="majorCount" align="center"></el-table-column>
                <!-- <el-table-column label="招生人数" prop="recruitAmount" align="center"></el-table-column> -->
                <el-table-column label="创建人" prop="creatorName" align="center"></el-table-column>
                <el-table-column label="创建日期" prop="createTime" align="center" width='200'></el-table-column>
                <el-table-column fixed="right" label="操作" width="400" align="center">
                  <template slot-scope="scope">
                    <el-button type="primary" icon="el-icon-lx-attention" @click="seeShowFaculty(scope.row.collegeId)">
                      查看</el-button>
                    <el-button type="primary" icon="el-icon-setting" @click="editShowFaculty(scope.row.collegeId)">编辑
                    </el-button>
                    <el-button type="primary" icon="el-icon-setting"
                      @click="getFourth(scope.row.collegeId,scope.row.apiId)">专业管理
                    </el-button>
                    <!-- <el-button type="danger" icon="el-icon-delete" @click="">删除</el-button> -->
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </div>
        </el-tab-pane>
        <!-- 专业管理列表 -->
        <el-tab-pane label="院系管理" name="fourth" v-if='major'>
          <div>
            <div class="handle-box" align="right">
              <el-button type="primary" icon="el-icon-lx-add" @click="addOrEditMajor('add','addMajor')">新建专业
              </el-button>
              <!-- <el-button type="primary" icon="el-icon-refresh" @click="">复制模板</el-button>
              <el-button type="primary" icon="el-icon-search" @click="">转为模板</el-button> -->
            </div>
            <div>
              <el-table :data="majorList" style="width: 100%">
                <el-table-column fixed width="55" label="序号" align="center">
                  <template slot-scope="scope">{{scope.$index + 1}}</template>
                </el-table-column>
                <el-table-column width="100" label="专业名称" prop="apiMajorName" align="center"></el-table-column>
                <el-table-column label="招生目标" prop="cultivateAim" align="center" width='200'></el-table-column>
                <!-- <el-table-column label="招生计划" prop="professionalQuantity" align="center"></el-table-column> -->
                <el-table-column label="学费" prop="fee" align="center"></el-table-column>
                <el-table-column label="学制" prop="majorType" align="center"></el-table-column>
                <el-table-column label="创建人" prop="creatorName" align="center"></el-table-column>
                <el-table-column label="创建日期" prop="createTime" align="center" width='200'></el-table-column>
                <el-table-column fixed="right" label="操作" width="300" align="center">
                  <template slot-scope="scope">
                    <el-button type="primary" icon="el-icon-lx-attention" v-if='major'
                      @click="queryMajorInfoByMajorId(scope.row.majorId)">
                      查看</el-button>
                    <el-button type="primary" icon="el-icon-setting" v-if='major'
                      @click="addOrEditMajor('edit',scope.row.majorId)">
                      编辑</el-button>
                    <!-- <el-button type="primary" icon="el-icon-setting" @click="">专业管理</el-button> -->
                    <el-button type="danger" icon="el-icon-delete" v-if='major'
                      @click="deleteMajorDetailed(scope.row.majorId)">删除
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
              <div>
                <!-- <el-button type="primary" @click="returnFaculty()">
                  返回院系管理</el-button> -->
              </div>
            </div>
          </div>
        </el-tab-pane>
        <!-- 查看院系 -->
        <el-tab-pane label="院系管理" name="fourth" v-if='showFaculty'>
          {{facultyForm.comment}}
          <leftBorder title="招生对象"></leftBorder>
          <p style="margin-left: 20px;margin-top: 50px;">{{facultyForm.recruitTarget}}</p>
          <leftBorder title="培养目标"></leftBorder>
          <p style="margin-left: 20px;margin-top: 50px;">{{facultyForm.cultivateAim}}</p>
          <leftBorder title="主要课程"></leftBorder>
          <p style="margin-left: 20px;margin-top: 50px;">{{facultyForm.mainCourse}}</p>
          <leftBorder title="就业方向"></leftBorder>
          <p style="margin-left: 20px;margin-top: 50px;">{{facultyForm.careerForward}}</p>
          <el-button align="center" style="width: 100px;" type="primary" @click="returnFaculty">返回
          </el-button>
        </el-tab-pane>
        <!-- 查看专业 -->
        <el-tab-pane label="院系管理" name="fourth" v-if='showMajor'>
          <div style="margin-left: 10px;">
            {{majorForm.apiMajorName}}
            <div style="text-align: center;">
              <div>
                <p>招生人数：{{majorForm.recruitAmount}}</p>
                <p>学费：{{majorForm.fee}}</p>
              </div>
              <p>学制：{{majorForm.majorType}}</p>
            </div>
            <div style="margin-top: 20px;">
              <leftBorder title="培养目标"></leftBorder>
              <p style="margin-left: 20px;margin-top: 10px;">{{majorForm.cultivateAim}}</p>
              <leftBorder title="主要课程"></leftBorder>
              <p style="margin-left: 20px;margin-top: 10px;">{{majorForm.mainCourse}}</p>
              <leftBorder title="就业方向"></leftBorder>
              <p style="margin-left: 20px;margin-top: 10px;">{{majorForm.careerForward}}</p>
              <el-button align="center" style="width: 100px;margin-top: 20px;" type="primary" @click="returnMajor">返回
              </el-button>
            </div>
          </div>
        </el-tab-pane>
        <!-- 新建/编辑院系 -->
        <el-tab-pane label="院系管理" name="fourth" v-if='editFaculty'>
          <div>
            <span style="margin-left: 20px;">院系名称：{{facultyForm.apiCollegeName}}</span>
            <el-form class="form-card" ref="facultyForm" :rules='facultyRules' :model="facultyForm" label-width="120px"
              size="mini" label-position="right" style="margin-left: 10px;">
              <el-row :gutter="20">
                <el-col :span="7">
                  <el-form-item label="院系负责人" prop="collegeManager">
                    <el-input v-model="facultyForm.collegeManager" class="wid195" placeholder="请输入院系负责人"
                      :disabled='isOrNotEdit'>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="7">
                  <el-form-item label="院系名称" prop="collegeName">
                    <el-input v-model="facultyForm.collegeName" class="wid195" placeholder="请输入院系名称"
                      :disabled='isOrNotEdit'>
                    </el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :span="14">
                  <el-form-item label="培养目标" prop="cultivateAim">
                    <el-input v-model="facultyForm.cultivateAim" style="width: 104%;" placeholder="请输入培养目标"
                      :disabled='isOrNotEdit'>
                    </el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :span="14">
                  <el-form-item label="招生人数" prop="recruitAmount">
                    <el-input v-model="facultyForm.recruitAmount" style="width: 104%;" placeholder="请输入计划招生人数"
                      :disabled='isOrNotEdit'></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :span="14">
                  <el-form-item label="招生对象" prop="recruitTarget">
                    <el-input v-model="facultyForm.recruitTarget" style="width: 104%;" placeholder="请输入招生对象"
                      :disabled='isOrNotEdit'></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :span="14">
                  <el-form-item label="就业方向" prop="careerForward">
                    <el-input v-model="facultyForm.careerForward" style="width: 104%;" placeholder="请输入就业方向"
                      :disabled='isOrNotEdit'></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :span="14">
                  <el-form-item label="院系简介" prop="comment">
                    <quill-editor ref="quillEditor" v-model="facultyForm.comment" :options="editorSchoolOption"
                      style="min-height: 200px;width:104%;" v-if='!isOrNotEdit'>
                    </quill-editor>
                    <el-input type="textarea" style="min-height: 200px;width: 104%;" :rows="25"
                      v-model="facultyForm.comment" v-if='isOrNotEdit' disabled>
                    </el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-form-item>
                <el-button align="center" style="width: 100px;" type="primary" @click="returnFaculty"
                  v-if="!isOrNotEdit">取消
                </el-button>
                <el-button align="center" style="width: 100px;" type="primary" @click="editFacultyDetailed"
                  v-if="!isOrNotEdit">保存
                </el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-tab-pane>
        <!-- 新建/编辑专业 -->
        <el-tab-pane label="院系管理" name="fourth" v-if='editMajor'>
          <div>
            <span>专业x名称：{{majorForm.apiMajorName}}</span>
            <el-form class="form-card" ref="majorForm" :rules='majorRules' :model="majorForm" label-width="130px"
              size="mini" label-position="right" style="margin-left: 10px;">
              <el-row :gutter="20">
                <el-col :span="7">
                  <el-form-item label="专业名称" prop="apiMajorName">
                    <el-input v-model="majorForm.apiMajorName" class="wid195" placeholder="请输入专业名称"
                      :disabled='isOrNotEdit'>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="7">
                  <el-form-item label="学费（元/学期）" prop="fee">
                    <el-input v-model="majorForm.fee" style="width: 83%;" placeholder="请输入学费" :disabled='isOrNotEdit'>
                    </el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col>
                  <el-form-item label="学制" prop="majorType">
                    <el-input v-model="majorForm.majorType" class="wid50" placeholder="请输入学制" :disabled='isOrNotEdit'>
                    </el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :span="7">
                  <el-form-item label="所属院校">
                    <span>专科班</span>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="所属专业" prop="apiId">
                    <el-select style="width: 70%;" v-model="majorForm.apiId" placeholder="请选择专业"
                      :disabled='isOrNotEdit'>
                      <!-- <el-option  label="请选择(来源学校负责人)" value="0"></el-option> -->
                      <el-option v-for="item in majorQuery" :key="item.id" :label="item.name" :value="item.id">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :span="7">
                  <el-form-item label="专业目标">
                    <span>120</span>
                  </el-form-item>
                </el-col>
                <el-col :span="9">
                  <el-form-item label="计划招生人数" prop="recruitAmount">
                    <el-input v-model="majorForm.recruitAmount" style="width: 58%;" placeholder="请输入计划招生人数"
                      :disabled='isOrNotEdit'></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :span="7">
                  <el-form-item label="培养目标" prop="cultivateAim">
                    <el-input v-model="majorForm.cultivateAim" class="wid195" placeholder="请输入培养目标"
                      :disabled='isOrNotEdit'></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="7">
                  <el-form-item label="主要课程" prop="mainCourse">
                    <el-input v-model="majorForm.mainCourse" style="width: 86%;" placeholder="请输入主要课程"
                      :disabled='isOrNotEdit'></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :span="7">
                  <el-form-item label="就业方向" prop="careerForward">
                    <el-input v-model="majorForm.careerForward" class="wid195" placeholder="请输入就业方向"
                      :disabled='isOrNotEdit'></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-form-item label="专业简介" prop="comment">
                  <quill-editor ref="quillEditor" v-model="majorForm.comment" :options="editorSchoolOption"
                    style="min-height: 200px;width: 50%;" v-if='!isOrNotEdit'>
                  </quill-editor>
                  <el-input type="textarea" style="min-height: 200px;width: 580px;" :rows="25"
                    v-model="schoolForm.description" v-if='isOrNotEdit' disabled>
                  </el-input>
                </el-form-item>
              </el-row>
              <el-form-item>
                <el-button align="center" style="width: 100px;" type="primary" @click="returnMajor" v-if="!isOrNotEdit">
                  取消
                </el-button>
                <el-button align="center" style="width: 100px;" type="primary" @click="addOrEditMajorNews"
                  v-if="!isOrNotEdit">保存
                </el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>
<script>
  import 'quill/dist/quill.core.css';
  import 'quill/dist/quill.snow.css';
  import 'quill/dist/quill.bubble.css';
  import config from '../../../../config/index.js'
  import { quillEditor } from 'vue-quill-editor';
  import { termlist } from '../../../../api/admin/API-Controller/APIController.js';
  import { uploadAdd, majorlist } from '../../../../api/admin/API-Controller/APIController.js';
  import {
    addPlanInfo,
    addPlanSchoolIntroduction,
    addPlanRecruitInfo,
    queryCollegeInfo,
    queryPlanInfo,
    updatePlanInfo,
    queryRecruitSchoolInfo,
    queryPlanSchoolIntroduction,
    updatePlanSchoolIntroduction,
    updatePlanRecruitInfo,
    addCollegeInfo,
    queryCollegeInfoById,
    updateCollegeInfo,
    queryMajorInfoByCollegeId,
    queryMajorInfoByMajorId,
    updateMajorInfo,
    deleteMajorInfo,
    addMajorInfo,
    getMajorList
  } from '@/api/admin/enrollmentPlanList/addenrollmentPlan.js';
  export default {
    components: {
      quillEditor
    },
    data() {
      // 工具栏配置
      const toolbarOptions = [
        ['bold', 'italic', 'underline', 'strike'], // toggled buttons
        ['blockquote', 'code-block'],

        [{ header: 1 }, { header: 2 }], // custom button values
        [{ list: 'ordered' }, { list: 'bullet' }],
        [{ script: 'sub' }, { script: 'super' }], // superscript/subscript
        [{ indent: '-1' }, { indent: '+1' }], // outdent/indent
        [{ direction: 'rtl' }], // text direction

        [{ size: ['small', false, 'large', 'huge'] }], // custom dropdown
        [{ header: [1, 2, 3, 4, 5, 6, false] }],

        [{ color: [] }, { background: [] }], // dropdown with defaults from theme
        [{ font: [] }],
        [{ align: [] }],
        ['clean'], // remove formatting button
        ['link', 'image', 'video']
      ];
      return {
        isSee: '',
        apiId: '',
        majorId: '',
        majorTitle: '',
        faculty: true, //查看显示院系列表展示
        major: false, //查看显示专业列表展示
        showFaculty: false, //查看院系展示
        showMajor: false, //查看专业展示
        editFaculty: false, //编辑/新增院系展示
        editMajor: false, //编辑/新增专业展示
        addShow: true, //展示学校与招生简介，增加保存时使用
        schoolId: '',
        title: '',
        planId: this.$route.params.planId, //查询/编辑所需要的ID，其中add表示新增页面
        operation: this.$route.params.operation, //操作判断，0为新增，1为查看，2为编辑
        isOrNotEdit: false,
        collegeId: "",
        imageUrl: '', //图片url路径
        activeName: 'first',
        //查看/新建院系|专业表单内容
        facultyForm: {
          //新建/编辑院系的数据
          careerForward: '',
          collegeId: '',
          collegeManager: '',
          collegeName: '',
          comment: '',
          cultivateAim: '',
          mainCourse: '',
          recruitAmount: '',
          recruitTarget: '',
        },
        facultyRules: {
          collegeManager: [
            { required: true, message: '请输入院系负责人', trigger: 'blur' },
          ],
          collegeName: [
            { required: true, message: '请输入院系名称', trigger: 'blur' },
          ],
          cultivateAim: [
            { required: true, message: '请输入培养目标', trigger: 'blur' },
          ],
          recruitAmount: [
            { required: true, message: '请输入招生人数', trigger: 'blur' },
          ],
          recruitTarget: [
            { required: true, message: '请输入招生对象', trigger: 'blur' },
          ],
          careerForward: [
            { required: true, message: '请输入就业方向', trigger: 'blur' },
          ],
          comment: [
            { required: true, message: '请输入院系简介', trigger: 'blur' },
          ],
        },
        majorForm: {
          //新建/编辑专业的数据
          chargerId: '',
          chargerName: '',
          comment: '',
          creatorId: '',
          creatorName: '',
          endTime: '',
          recruitSchoolId: '',
          season: '',
          semester: '',
          semesterId: '',
          startTime: '',
          telephoneNumber: '',
          title: '',
        },
        majorRules: {
          apiMajorName: [
            { required: true, message: '请输入专业名称', trigger: 'blur' },
          ],
          fee: [
            { required: true, message: '请输入学费', trigger: 'blur' },
          ],
          majorType: [
            { required: true, message: '请输入学制', trigger: 'blur' },
          ],
          apiId: [
            { required: true, message: '请选择专业', trigger: 'change' },
          ],
          recruitAmount: [
            { required: true, message: '请输入计划招生人数', trigger: 'blur' },
          ],
          cultivateAim: [
            { required: true, message: '请输入培养目标', trigger: 'blur' },
          ],
          mainCourse: [
            { required: true, message: '请输入主要课程', trigger: 'blur' },
          ],
          careerForward: [
            { required: true, message: '请输入就业方向', trigger: 'blur' },
          ],
          comment: [
            { required: true, message: '请输入专业简介', trigger: 'blur' },
          ],
        },
        seasonQuery: [
          {
            seasonId: 0,
            season: '春季'
          }, {
            seasonId: 1,
            season: '夏季'
          }, {
            seasonId: 2,
            season: '秋季'
          }, {
            seasonId: 3,
            season: '冬季'
          },
        ],
        majorQuery: [],
        //学期信息 TODO 模拟学期数据
        semesterQuery: [],
        //招生负责人信息 TODO 模拟负责人数据
        chargerQuery: [
          {
            chargerId: 1,
            chargerName: '张三'
          },
          {
            chargerId: 2,
            chargerName: '李四'
          }
        ],
        //基本信息内容
        planForm: {
          planId: '',
          chargerId: '',
          chargerName: '',
          comment: '',
          creatorId: "1",//TODO 数据由登录取得
          creatorName: "张三",//TODO 数据由登录取得
          endTime: '',
          recruitSchoolId: '1',//TODO 数据由登录取得
          season: '',
          semester: '',
          semesterId: '',
          startTime: '',
          telephoneNumber: '',
          title: ''
        },
        planRules: {
          semester: [
            { required: true, message: '请选择学期', trigger: 'change' },
          ],
          season: [
            { required: true, message: '请选择季节', trigger: 'change' },
          ],
          startTime: [
            { required: true, message: '请选择开始时间', trigger: 'blur' },
          ],
          endTime: [
            { required: true, message: '请选择结束时间', trigger: 'blur' },
          ],
          chargerId: [
            { required: true, message: '请选择负责人', trigger: 'blur' },
          ],
          telephoneNumber: [
            { required: true, message: '请输入联系电话', trigger: 'blur' },
            {
              pattern: /^((\d{3,4})|(\d{3,4}-|\s))?\d{7,11}$/,
              message: '请输入正确的手机号或者联系电话，如：0755-87654321',
              trigger: 'blur'
            }
          ],
          title: [
            { required: true, message: '请输入标题', trigger: 'blur' },
          ],
          // comment:[
          //   { required: true, message: '请输入基本信息说明', trigger: 'blur' },
          // ],
        },
        //学校简介内容
        schoolForm: {
          recruitSchoolName: '',
          recruitSchoolLogo: '',
          principal: '',
          telephoneNumber: '',
          address: '',
          description: '',
          apiId: '',
          planId: ''
        },
        schoolRules: {
          recruitSchoolName: [
            { required: true, message: '请输入学校名称', trigger: 'blur' },
          ],
          recruitSchoolLogo: [
            { required: true, message: '请上传学校Logo图片', trigger: 'change' },
          ],
          principal: [
            { required: true, message: '请输入校长名称', trigger: 'blur' },
          ],
          telephoneNumber: [
            { required: true, message: '请输入联系电话', trigger: 'blur' },
            {
              pattern: /^((\d{3,4})|(\d{3,4}-|\s))?\d{7,11}$/,
              message: '请输入正确的手机号或者联系电话',
              trigger: 'blur'
            },
          ],
          address: [
            { required: true, message: '请输入学校地址', trigger: 'blur' },
          ],
          description: [
            { required: true, message: '请输入学校简介', trigger: 'blur' },
          ],
          // comment:[
          //   { required: true, message: '请输入基本信息说明', trigger: 'blur' },
          // ],
        }
        ,
        //招生简介内容
        planRecruitFrom: {
          planId: '',
          recruitTarget: '',
          attention: '',
          register: '',
          planDescription: ''
        },
        recruitRules: {
          recruitTarget: [
            { required: true, message: '请输入招生对象', trigger: 'blur' },
          ],
          attention: [
            { required: true, message: '请输入注意事项', trigger: 'blur' },
          ],
          register: [
            { required: true, message: '请输入报到与录取', trigger: 'blur' },
          ],
          planDescription: [
            { required: true, message: '请输入招生计划说明', trigger: 'blur' },
          ]
        },
        tableData: [],//院系管理列表数据
        majorList: [],//专业管理页面
        editorOption: {
          placeholder: '请输入计划说明',
          modules: {
            toolbar: {
              container: toolbarOptions, // 工具栏
            }
          }
        }, editorSchoolOption: {
          placeholder: '请输入学校简介',
          modules: {
            toolbar: {
              container: toolbarOptions, // 工具栏
            }
          }
        },
      };
    },
    created() {
      this.schoolId = JSON.parse(localStorage.getItem("userInfo")).schoolid
      this.isEdit();
      this.getData();
      this.termlist();
      // this.addCollegeInfo(this.planId)//测试接口
    },
    methods: {
      /**
       * 展示编辑招生院校
       * @param collegeId{String} 根据院系ID查找具体数据
       * @author LiuGuoHao
       */
      editShowFaculty(collegeId) {
        this.editFaculty = true;
        this.faculty = false;
        queryCollegeInfoById(collegeId).then(res => {
          this.facultyForm = res.data
          console.log(this.facultyForm, '==>facultyres')
        })
      },
      //进入专业新增页面
      addOrEditMajor(majorTitle, majorId) {
        console.log(majorId, '==>majorId')
        this.faculty = false; //查看显示院系列表展示
        this.major = false; //查看显示专业列表展示
        this.showFaculty = false; //查看院系展示
        this.showMajor = false; //查看专业展示
        this.editFaculty = false; //编辑/新增院系展示
        this.editMajor = true; //编辑/新增专业展示
        this.majorTitle = majorTitle;

        let api = { departId: this.apiId }
        console.log(this.apiId, '==>apiId')
        majorlist(api).then(res => {
          console.log(res, '==>majorList')
          this.majorQuery = res.data
        })
        if (majorTitle == 'add') {
          this.majorForm = {}
        } else if (majorTitle == 'edit') {
          queryMajorInfoByMajorId(majorId).then(res => {
            this.majorId = majorId
            this.majorForm = res.data
          })
        }
      },
      /**
       * 图片上传
       */
      uploadImg(option) {
        this.uploadAdd(option.file, imgUrl => {
          console.log(option.file, '==>file')
          console.log(imgUrl, '==>imgUrl')
          this.schoolForm.recruitSchoolLogo = imgUrl;
        });
      },
      /**
       * 上传图片接口
       * @param file {object} 二进制文件数据
       * @param callback{} 
       * @author LiuGuoHao
       */
      uploadAdd(file, callback) {
        let formData = new FormData();
        let type = file.name.substring(file.name.indexOf('.') + 1, file.name.length);
        formData.append('file', file);
        formData.append('fileType', type);
        uploadAdd(formData)
          .then(res => {
            callback(res.data);
          })
          .catch(err => {
            console.error(err);
          });
      },
      //删除图片
      deleteImage() {
        this.$confirm('是否要删除图片？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '删除成功！'
          });
          this.$refs.imageInput.value = '';
          this.schoolForm.recruitSchoolLogo = '';
        });
      },
      /**
       * 富文本编辑插入图片
       * @param option {object} 二进制文件数据
       * @author LiuGuoHao
       */
      uploadImgForEditor(option) {
        this.uploadAdd(option.file, imgUrl => {
          //插入富文本中
          let quill = this.$refs['quillEditor'].quill;
          let length = quill.getSelection().index;
          quill.insertEmbed(length, 'image', imgUrl);
          //光标移动一格
          quill.setSelection(length + 1);
        });
      },
      //判断是否为新增专业页面
      addOrEditMajorNews() {
        this.$refs.majorForm.validate(valid => {
          if (valid) {
            if (this.majorTitle == 'add') {
              this.addMajorInfo()
              // this.$message.success("新增专业院系成功！")
            } else if (this.majorTitle == 'edit') {
              this.updateMajorInfo(this.majorId)
              // this.$message.success("编辑专业院系成功！")
            }
            this.faculty = false; //查看显示院系列表展示
            this.major = true; //查看显示专业列表展示
            this.showFaculty = false; //查看院系展示
            this.showMajor = false; //查看专业展示
            this.editFaculty = false; //编辑/新增院系展示
            this.editMajor = false; //编辑/新增专业展示
          }
        })
      },
      //查看专业明细
      queryMajorInfoByMajorId(majorId) {
        this.faculty = false; //查看显示院系列表展示
        this.major = false; //查看显示专业列表展示
        this.showFaculty = false; //查看院系展示
        this.showMajor = true; //查看专业展示
        this.editFaculty = false; //编辑/新增院系展示
        this.editMajor = false; //编辑/新增专业展示
        queryMajorInfoByMajorId(majorId).then(res => {
          this.majorForm = res.data
        })
      },
      //编辑专业
      updateMajorInfo(majorId) {
        this.$refs.majorForm.validate(valid => {
          if (valid) {
            this.majorForm.majorId = majorId
            console.log(this.majorForm.majorId, '==>')
            updateMajorInfo(this.majorForm).then(res => {
              this.$message.success("编辑院系专业成功！")
            })
          }
        })
      },
      //删除专业
      deleteMajorDetailed(majorId) {
        this.$confirm('是否要删除该院系专业？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '删除院系专业成功！'
          });
          deleteMajorInfo(majorId).then(res => {
            // this.$message.success("删除院系专业成功！")
            this.getFourth(this.collegeId);
          })
        });
      },
      //新建专业
      addMajorInfo() {
        this.$refs.majorForm.validate(valid => {
          if (valid) {
            this.majorForm.collegeId = this.collegeId
            addMajorInfo(this.majorForm).then(res => {
              this.returnMajor()
              this.$message.success("新增院系专业成功！")
            })
          }
        })
      },
      //新建院系
      addFaculty() {
        this.facultyForm = {}
        updateCollegeInfo().then(res => {

        })
      },

      /**
       * 获取院系下面的所有招生专业信息
       * @param collegeId{String} 根据院系ID查找具体数据
       * @param apiId{String} 获取apiId数据
       * @author LiuGuoHao
       */
      getFourth(collegeId, apiId) {
        this.faculty = false; //查看显示院系列表展示
        this.major = true; //查看显示专业列表展示
        this.showFaculty = false; //查看院系展示
        this.showMajor = false; //查看专业展示
        this.editFaculty = false; //编辑/新增院系展示
        this.editMajor = false; //编辑/新增专业展示
        this.collegeId = collegeId
        if (apiId) {
          this.apiId = apiId
        }
        queryMajorInfoByCollegeId(collegeId).then(res => {
          this.majorList = res.data
          console.log(this.majorList, '==>majorList')
        })
      },
      /**
       * 查看招生院校
       * @param collegeId{String} 根据院系ID查找具体数据
       * @author LiuGuoHao
       */
      seeShowFaculty(collegeId) {
        this.faculty = false; //查看显示院系列表展示
        this.major = false; //查看显示专业列表展示
        this.showFaculty = true; //查看院系展示
        this.showMajor = false; //查看专业展示
        this.editFaculty = false; //编辑/新增院系展示
        this.editMajor = false; //编辑/新增专业展示
        queryCollegeInfoById(collegeId).then(res => {
          this.facultyForm = res.data
          console.log(this.facultyForm, '==>facultyres')
        })
      },
      //编辑院系管理操作
      editFacultyDetailed() {
        this.$refs.facultyForm.validate(valid => {
          if (valid) {
            updateCollegeInfo(this.facultyForm).then(res => {
              // console.log(res,'==>updateFacultyFormRes')
              this.$message.success("编辑院系成功！")
              this.returnFaculty()
              this.queryCollegeInfo(this.planId);
            })
          }
        })
      },
      //返回院系管理页面
      returnFaculty() {
        this.faculty = true;
        this.major = false; //查看显示专业列表展示
        this.showFaculty = false; //查看院系展示
        this.showMajor = false; //查看专业展示
        this.editFaculty = false; //编辑/新增院系展示
        this.editMajor = false; //编辑/新增专业展示
      },
      //返回专业管理页面
      returnMajor() {
        this.faculty = false;
        this.major = true; //查看显示专业列表展示
        this.showFaculty = false; //查看院系展示
        this.showMajor = false; //查看专业展示
        this.editFaculty = false; //编辑/新增院系展示
        this.editMajor = false; //编辑/新增专业展示
        this.getFourth(this.collegeId)
      },
      /**
       * 获取招生学校数据
       * @author LiuGuoHao
       */
      getData() {
        if (this.operation != 0) {
          //获取学期信息
          queryPlanInfo(this.planId).then(res => { this.planForm = res.data })
        } else {
          this.addShow = false
          this.faculty = false
        }
      },
      //根据学校ID获取学期信息
      termlist() {
        termlist(this.schoolId).then(res => {
          this.semesterQuery = res.data.data
        })
      },
      /**
       * 图片上传成功后执行
       * @param res(array) 接口对接返回的内容
       *  @param file(文件名)
       * @author LiuGuoHao
       */
      handleAvatarSuccess(res, file) {
        this.imageUrl = URL.createObjectURL(file.raw);
      },
      /**
       * 上传图片进行格式分析
       * @param file(String) 图片内容
       * @author LiuGuoHao
       */
      beforeAvatarUpload(file) {
        const isJPG = (file.type === 'image/jpeg' || file.type === 'image/png');
        // console.log(file.type,'==>fileType')
        const isLt2M = file.size / 1024 / 1024 < 2;
        if (!isJPG) {
          this.$message.error('上传头像只能是 JPG或PNG 格式!');
        }
        else if (!isLt2M) {
          this.$message.error('上传头像大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
      },
      /**
       * 添加基本信息
       * @author LiuGuoHao
       */
      addOrEditPlanInfo() {
        // this.planForm.planId = this.planId
        this.$refs.planForm.validate(valid => {
          if (valid) {
            if (this.operation == 0) {
              var semester = this.planForm.semester;
              var obj = this.semesterQuery.find(function (x) {
                return x.name == semester;
              });
              console.log(obj, "==>obj");
              this.planForm.semesterId = obj.id;
              addPlanInfo(this.planForm).then(res => {
                this.planId = res.data
                //TODO 提出单独方法
                queryPlanInfo(this.planId).then(res => {
                  console.log(res, '==>res')
                  this.planForm = res.data
                })
                // console.log(this.planId, '==>addNews')
                this.addShow = true
                this.faculty = true
                this.addCollegeInfo(this.planId) //新增院系
                this.$message.success("新增信息成功！")
              })
            } else if (this.operation == 2) {
              console.log(this.planForm, "===>planForm")
              this.planForm.planId = this.planForm.planId.toString()
              updatePlanInfo(this.planForm).then(res => {
                this.$message.success("编辑信息成功！")
              })
            }
          }
        });
      },
      /**
       * 添加招生院系信息
       * @param planId{number} 添加招生院系下的planId
       * ?>@author LiuGuoHao
       */
      addCollegeInfo(planId) {
        addCollegeInfo(planId).then(ref => {
          // this.queryCollegeInfo(this.planId);  //查询院系列表
          console.log(ref, "==>ref")
        })
      },
      /**
       * 添加/编辑学校简介
       * @author LiuGuoHao
       */
      addOrEditPlanSchoolIntroduction() {
        this.$refs.schoolForm.validate(valid => {
          if (valid) {
            this.schoolForm.planId = this.planId
            console.log(this.schoolForm, "==>schoolForm")
            if (!this.planForm.planSchoolIntroductionId) {
              addPlanSchoolIntroduction(this.schoolForm).then(res => {
                this.$message.success("新增学校简介成功！")
              })
            } else {
              this.schoolForm.planSchoolIntroductionId = this.planForm.planSchoolIntroductionId
              updatePlanSchoolIntroduction(this.schoolForm).then(res => {
                this.$message.success("编辑学校简介成功！")
              })
            }
          }
        })
      },
      /**
       * 添加招生简介信息
       * @author LiuGuoHao
       */
      addOrEditPlanRecruitInfo() {
        this.$refs.planRecruitFrom.validate(valid => {
          if (valid) {
            this.planRecruitFrom.planId = this.planId
            if (!this.planForm.planRecruitInfoId) {
              addPlanRecruitInfo(this.planRecruitFrom).then(res => {
                this.$message.success("新增招生简介成功！")
              })
            } else {
              this.planRecruitFrom.planRecruitInfoId = this.planForm.planRecruitInfoId
              updatePlanRecruitInfo(this.planRecruitFrom).then(res => {
                this.$message.success("编辑招生简介成功！")
                console.log(this.schoolForm, "===>schoolForm")
              })
            }
          }
        })
      },
      /**
       * 查看招生院系信息
       * @param planId{String} 获取招生院系需要的计划Id
       * @author LiuGuoHao
       */
      queryCollegeInfo(planId) {
        queryCollegeInfo(planId).then(res => {
          this.tableData = res.data
          console.log(res, '==>res')
        })
      },
      /**
       * 显示开启基本信息，学校简介招生简介的状态
       * @author LiuGuoHao
       */
      isEdit() {
        if (this.operation == 1) {
          this.title = '查看'
          this.isOrNotEdit = true
        } else if (this.operation == 0) {
          this.title = '创建'
          this.isOrNotEdit = false
        } else if (this.operation == 2) {
          this.title = '编辑'
          this.isOrNotEdit = false
        }
      },
      handleClick(tab, event) {
        console.log(tab, '==>tab');
        // //获取计划招生学校信息
        // queryPlanSchoolIntroduction(this.planId).then(res => { this.schoolForm = res.data; })
        // //获取招生简介信息
        // queryRecruitSchoolInfo(this.planId).then(res => { this.planRecruitFrom = res.data; })
        // //获取招生院系信息
        // this.queryCollegeInfo(this.planId);
        // if (this.operation == 2) {
        switch (tab.name) {
          case 'first':
            if (this.planId != 'add') {
              queryPlanInfo(this.planId).then(res => { this.planForm = res.data })
            }
            break;
          case 'second':
            if (this.planForm.planSchoolIntroductionId) {
              queryPlanSchoolIntroduction(this.planId).then(res => { this.schoolForm = res.data; })
            }
            break;
          case 'third':
            if (this.planForm.planRecruitInfoId) {
              queryRecruitSchoolInfo(this.planId).then(res => { this.planRecruitFrom = res.data; })
            }
            break;
          case 'fourth':
            this.queryCollegeInfo(this.planId);
            this.returnFaculty()
            break;
        }
        // }
      }
    }
  };
</script>
<style scoped>
  .form-card {
    margin-top: 10px;
  }

  .wid195 {
    width: 110%;
  }

  .wid50 {
    width: 50%;
  }

  .wid60 {
    width: 60%;
  }

  .wid200 {
    width: 200px;
  }

  .handle-box {
    margin-bottom: 20px;
  }

  .red {
    color: #ff0000;
  }

  .img>>>.el-upload--text {
    margin-left: -80px;
    width: 70%;
    height: 130px;
  }

  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar {
    width: 100%;
    height: 100%;
    display: block;
  }
</style>