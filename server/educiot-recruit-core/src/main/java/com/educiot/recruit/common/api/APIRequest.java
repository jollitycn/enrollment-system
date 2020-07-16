package com.educiot.recruit.common.api;

import com.alibaba.fastjson.JSON;
import com.educiot.common.api.base.CodeMsg;
import com.educiot.common.api.base.MyExcption;
import com.educiot.common.util.HttpClientUtil;
import com.educiot.common.util.StringUtil;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jason Hong
 */
@Data
public class APIRequest {


    public static String apiUrl;
    private boolean useToken = false;


    //    public static  void setApiUrl(String apiUr){
//        APIRequest.apiUrl=apiUrl;
//    }
    private APIRequestSetting setting;


    /**
     *
     */
    public APIRequest() {
        setting = new APIRequestSetting();
    }

    /**
     * @param account
     * @param pwd
     */
    public APIRequest(String account, String pwd) {
        setting = new APIRequestSetting(account, pwd);
    }

    /**
     * 获得token
     *
     * @return String
     */
    public RequestResult login() throws MyExcption {
        final String LOGIN_URL = getApiUrl() + "/sys/user/login";
        Map<String, String> map = new HashMap<>();
        map.put("account", setting.getAccount());
        map.put("pwd", setting.getPwd());
        String str = HttpClientUtil.doPost(LOGIN_URL, map);
        RequestResult requestResult = JSON.parseObject(str, RequestResult.class);
        if (requestResult.getStatus().equals(APIResult.SUCCCESS)) {
            setting.setToken(requestResult.getData().getToken());
        } else {
            setting.setToken(null);
        }
        useToken = true;
        return requestResult;
    }


    /**
     * 获得token
     *
     * @return String
     */
    public String loadToken() {
        if (setting.getToken() == null && useToken) {
            RequestResult requestResult = null;
            try {
                requestResult = login();
                setting.setToken(requestResult.getData().getToken());
            } catch (MyExcption myExcption) {
                myExcption.printStackTrace();
            }
        }
        return setting.getToken();
    }

    /**
     * 获取当前登录学校信息和账号信息
     *
     * @return
     */
    public SchoolUserInfoResult schoolUserInfo() throws MyExcption {
        final String USER_INFO = getApiUrl() + "/sys/school/user/info";
        Map<String, String> map = new HashMap<>();
        map.put("FDToken", loadToken());
        String str = HttpClientUtil.doPost(USER_INFO, map);
        SchoolUserInfoResult result = JSON.parseObject(str, SchoolUserInfoResult.class);
        return (SchoolUserInfoResult) checkResult(result);
    }

    /**
     * 根据学校id获取当前组织结构
     *
     * @param schoolid
     * @return
     */
    public OrgStructResult orgStruct(String schoolid) throws MyExcption {
        final String STRUCT_URL = getApiUrl() + "/sys/school/org/struct";
        Map<String, String> map = new HashMap<>();
        map.put("FDToken", loadToken());
        map.put("schoolid", schoolid);
        String str = HttpClientUtil.doPost(STRUCT_URL, map);
        OrgStructResult result = JSON.parseObject(str, OrgStructResult.class);
        return (OrgStructResult) checkResult(result);
    }

    /**
     * 根据学校id获取院系列表信息
     *
     * @param schoolid
     * @return
     */
    public DepartInfoResult departInfo(String schoolid) throws MyExcption {
        final String DEPART_URL = getApiUrl() + "/sys/depart/info";
        Map<String, String> map = new HashMap<>();
        map.put("FDToken", loadToken());
        map.put("schoolid", schoolid);
        String str = HttpClientUtil.doPost(DEPART_URL, map);
        return JSON.parseObject(str, DepartInfoResult.class);
    }

    /**
     * 根据学校id获取学期列表信息
     *
     * @param schoolid
     * @return
     */
    public TermInfoResult termInfo(String schoolid) throws MyExcption {
        final String TERM_URL = getApiUrl() + "/sys/term/info";
        Map<String, String> map = new HashMap<>();
        map.put("FDToken", loadToken());
        map.put("schoolid", schoolid);
        String str = HttpClientUtil.doPost(TERM_URL, map);
        TermInfoResult result = JSON.parseObject(str, TermInfoResult.class);
        return (TermInfoResult) checkResult(result);
    }

    /**
     * 新增专业同步到平台
     *
     * @return
     */
    public AddMajorResult addMajor(String major, String departid) throws MyExcption {
        final String ADD_MAJOR_URL = getApiUrl() + "/sys/add/major";
        Map<String, String> map = new HashMap<>();
        map.put("FDToken", loadToken());
        map.put("major", major);
        map.put("departid", departid);
        String s = HttpClientUtil.doPost(ADD_MAJOR_URL, map);
        AddMajorResult result = JSON.parseObject(s, AddMajorResult.class);
        return (AddMajorResult) checkResult(result);
    }

    private APIResult checkResult(APIResult result) throws MyExcption {
        if (result.isSuccess()) {
            return result;
        } else {
            if (result.getStatus() == CodeMsg.API_NOT_LOGIN.getRetCode()) {
                throw new MyExcption(CodeMsg.TOKEN_NOT_EXIST, result.getMsg());
            } else {
                throw new MyExcption(result.getStatus(), result.getMsg());
            }
        }
    }


    /**
     * 根据院系id获取专业列表
     *
     * @param departid
     * @return
     */
    public MajorListResult majorList(String departid) throws MyExcption {
        final String MAJOR_URL = getApiUrl() + "/sys/major/list";
        Map<String, String> map = new HashMap<>();
        map.put("FDToken", loadToken());
        map.put("departid", departid);
        String str = HttpClientUtil.doPost(MAJOR_URL, map);
        MajorListResult result = JSON.parseObject(str, MajorListResult.class);
        return (MajorListResult) checkResult(result);
    }

    /**
     * 新增学期返回学期id和名称
     *
     * @return
     */
    public AddTermResult addTerm(String name) throws MyExcption {
        final String ADD_TERM_URL = getApiUrl() + "/sys/term/add";
        Map<String, String> map = new HashMap<>();
        map.put("FDToken", loadToken());
        map.put("name", name);
        String str = HttpClientUtil.doPost(ADD_TERM_URL, map);
        AddTermResult result = JSON.parseObject(str, AddTermResult.class);
        return (AddTermResult) checkResult(result);
    }

    /**
     * 根据员工id或者模糊名称，获取负责人信息
     *
     * @param userid
     * @param name
     * @return
     */
    public APIResult getSuperior(String userid, String name) throws MyExcption {
        final String SUPERIOR_URL = getApiUrl() + "/sys/get/superior/info";
        Map<String, String> map = new HashMap<>();
        map.put("FDToken", loadToken());
        map.put("userid", userid);
        map.put("name", name);
        String str = HttpClientUtil.doPost(SUPERIOR_URL, map);
        APIResult result = JSON.parseObject(str, APIResult.class);
        return checkResult(result);
    }

    /**
     * @param phone
     * @return
     * @throws MyExcption
     */
    public MsgCodeResult getMsgCode(String phone) throws MyExcption {
        final String SUPERIOR_URL = getApiUrl() + "/sys/get/msg/code";
        Map<String, String> map = new HashMap<>();
        //   map.put("FDToken", getToken());
        map.put("phone", phone);
        String str = HttpClientUtil.doPost(SUPERIOR_URL, map);
        return JSON.parseObject(str, MsgCodeResult.class);
    }

    private String getApiUrl() throws MyExcption {
        if (StringUtil.isEmpty(APIRequest.apiUrl)) {
            throw new MyExcption(CodeMsg.API_NOT_CONFIG);
        }
        return APIRequest.apiUrl;
    }


}
