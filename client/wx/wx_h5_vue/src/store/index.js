import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);
let token = "";
try {
  if (localStorage.getItem("token")) {
    token = localStorage.getItem("token");
  }
} catch (e) {
  token = "";
}
let user = "";
try {
  if (localStorage.getItem("user")) {
    user = JSON.parse(localStorage.getItem("user"));
  }
} catch (e) {
  user = "";
}
let studentList = [];
try {
  if (localStorage.getItem("studentList")) {
    studentList = JSON.parse(localStorage.getItem("studentList"));
  }
} catch (e) {
  studentList = [];
}

let SchoolList = [];
try {
  if (localStorage.getItem("SchoolList")) {
    SchoolList = JSON.parse(localStorage.getItem("SchoolList"));
  }
} catch (e) {
  SchoolList = [];
}

let currentStudent = {};
try {
  if (localStorage.getItem("currentStudent")) {
    currentStudent = JSON.parse(localStorage.getItem("currentStudent"));
  }
} catch (e) {
  currentStudent = {};
}

let currentRecruitSchool = {};
try {
  if (localStorage.getItem("currentRecruitSchool")) {
    currentRecruitSchool = JSON.parse(
      localStorage.getItem("currentRecruitSchool")
    );
  }
} catch (e) {
  currentRecruitSchool = {};
}
let userImage = "";
try {
  if (localStorage.getItem("userImage")) {
    userImage = localStorage.getItem("userImage");
  }
} catch (e) {
  userImage = "";
}

export default new Vuex.Store({
  state: {
    activeIndex: 0,
    user: user,
    token: token,
    studentList: studentList, //已绑定的学生列表
    SchoolList: SchoolList, //对应学生的学校列表
    currentStudent: currentStudent, //当前选择的学生
    currentRecruitSchool: currentRecruitSchool, //当前选中的学校
    userImage: userImage //学生图片
  },
  mutations: {
    setActiveIndex(state,data) {
      state.activeIndex = data
    },
    setUser(state, data) {
      console.log(state, data);
      state.user = data;
      state.token = data.token;
      state.studentList = data.studentList; //当前的学生列表
      state.currentStudent = data.currentStudent; //当前选中的学生
      state.currentRecruitSchool = data.currentRecruitSchool; //当前选中的学校
      localStorage.setItem("user", JSON.stringify(data));
      localStorage.setItem("token", data.token);
      localStorage.setItem("studentList", JSON.stringify(data.studentList));
      localStorage.setItem(
        "currentStudent",
        JSON.stringify(data.currentStudent)
      );
      localStorage.setItem(
        "currentRecruitSchool",
        JSON.stringify(data.currentRecruitSchool)
      );
      localStorage.setItem(
          "tokenTime",(new Date().getTime())
      );
    },
    setToken(state, data) {
      state.token = data;
      localStorage.setItem("token", data);
    },
    //所绑定的学生列表
    setStudentList(state, data) {
      state.studentList = data;
      localStorage.setItem("studentList", JSON.stringify(data));
    },
    //对应学生的对应招生学校列表
    setSchoolList(state, data) {
      state.SchoolList = data;
      localStorage.setItem("SchoolList", JSON.stringify(data));
    },
    //当前选择的学生
    setCurrentStudent(state, data) {
      state.currentStudent = data;
      localStorage.setItem("currentStudent", JSON.stringify(data));
    },
    //当前选择的学校
    setCurrentRecruitSchool(state, data) {
      console.log(data, "132456")
      state.currentRecruitSchool = data;
      localStorage.setItem("currentRecruitSchool", JSON.stringify(data));
    },
    //修改学生图片
    setUserImage(state, imgUrl) {
      state.userImage = imgUrl;
      localStorage.setItem("userImage", imgUrl);
    }
  },
  actions: {},
  modules: {}
});
