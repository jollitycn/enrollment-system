//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    platform: '',
    // src: "https://zhaosheng-test.szis.com.cn/#/login",
    src: "http://educiot-recruit-miniapp-test.szis.com.cn:9003/#/login",
    // src: "http://100.168.1.2:8080/#/login",
    code: "",
    info: {
      id: ""
    }
  },
  onLoad: function (option) {
    // 扫描小程序码进入
    console.log(option)
    if (option.scene) {
      let info = decodeURIComponent(option.scene)
      this.setData({
        'info.id': info.id
      })
    }
  },
   /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    this.setData({
      'info.id': ''
    })
  },
  //登录
  clickCode(e) {
    let _this = this;
    wx.login({
      success(res) {
        if (res.code) {
          //发起网络请求
          let src = "";
          let math = (new Date()).getTime()
          src = `${_this.data.src}?code=${res.code}&recruitSchoolId=${_this.data.info.id}&math=${math}`
          _this.setData({
            code: res.code,
            src: src
          })
          console.log(res.code)
          // wx.navigateTo({
          //   url: '/pages/login/login',
          //   success: function (res) {
          //     // 通过eventChannel向被打开页面传送数据
          //     res.eventChannel.emit('src', {
          //       data: src
          //     })
          //   }
          // })
        } else {
          console.log('登录失败！' + res.errMsg)
        }
      },
      fail(err) {
        console.log(err)
      }
    })
  },
  //扫一扫
  sys() {
    let _this = this;
    wx.scanCode({
      success(res) {
        let info = decodeURIComponent(res.path)
        let id = info.substring(info.lastIndexOf("=") + 1 ,info.length)
        console.log(res,id)
        _this.setData({
          'info.id': id.toString()
        })
        _this.clickCode()
      }
    })
  }
})