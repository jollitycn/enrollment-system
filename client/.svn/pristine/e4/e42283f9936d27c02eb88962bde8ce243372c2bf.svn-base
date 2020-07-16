import request from '../../../utils/request';

//总体招生进度
export const enrollmentProgress = () => {
    return request({
        url: '/report/enrollmentProgress',
        method: 'get',
    })
}

//区域招生进度
export const regionalAdmissionsProgress = () => {
    return request({
        url: '/report/regionalAdmissionsProgress',
        method: 'get',
    })
}
//院系招生进度
export const facultyAdmissionsProgress = () => {
    return request({
        url: '/report/facultyAdmissionsProgress',
        method: 'get',
    })
}
//招生表报
export const admissionsReport = data => {
    return request({
        url: '/report/admissionsReport',
        method: 'post',
        data
    })
}
//区域招生进度表
export const regionalEnrollmentProgressReport = query => {
    return request({
        url: '/report/regionalEnrollmentProgressReport',
        method: 'get',
        params: query
    })
}
//生源报表
export const studentSourceReport = query => {
    return request({
        url: '/report/studentSourceReport',
        method: 'get',
        params: query
    })
}
//渠道报表
export const channelReport = query => {
    return request({
        url: '/report/channelReport',
        method: 'get',
        params: query
    })
}
// 刷新token
export const reLogin = () => {
    return request({
        url: `/home/reLogin`,
        method: "post"
    });
};