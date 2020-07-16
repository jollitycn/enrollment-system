module.exports = {
  publicPath: "./",
  assetsDir: "static",
  productionSourceMap: false,
  devServer: {
    host: "0.0.0.0",
    disableHostCheck: true,
    proxy: {
      "/": {
        // target: "http://100.168.2.207:8603", // 洪思帆
        // target: "http://100.168.2.71:8603", // 艾辉
        // target: "http://100.168.2.119:8603/", //刘豪
        // target: "http://100.168.2.199:8603", // 许超
        // target: "http://100.168.2.167:8603", // 潘俊才
        // target: "http://100.168.2.173:8603", //刘豪 地址
        target: "http://100.168.2.115:9003/wechat", //线上地址
        // target: "https://zhaosheng-test.szis.com.cn/wechat", //线上地址
        changeOrigin: true,
        pathRewrite: {
          "^/": "/"
        }
      }
    }
  },
  lintOnSave: false,
  css: {
    loaderOptions: {
      // 给 stylus-loader 传递选项
      stylus: {
        // @/ 是 src/ 的别名
        // 所以这里假设你有 `src/common/stylus/color.styl` 这个文件，需要在没有vue文件中import
        import: "~@/assets/css/variables.styl"
      }
    }
  }
};
