
export default  {
    //系统所有接口的根路径
    rootPath: process.env.NODE_ENV === 'development'?'/':'/admin/',
    //发送短信间隔时间,单位秒
    sendMsgInterval:120,

    //上传图片的图片目录
    imgDir: process.env.NODE_ENV === 'development'?'/static/':'/admin/static/',
}
