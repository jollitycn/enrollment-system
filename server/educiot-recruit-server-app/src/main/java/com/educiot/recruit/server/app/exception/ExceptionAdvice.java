package com.educiot.recruit.server.app.exception;


import com.educiot.common.base.CodeMsg;
import com.educiot.common.base.MyExcption;
import com.educiot.common.base.Result;
import com.educiot.common.util.HttpClientUtil;
import com.educiot.recruit.data.entity.ExceptionLog;
import com.educiot.recruit.data.service.IExceptionLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;


/**
 * @program: server
 * @description: 统一异常处理
 * @author: XuChao
 * @create: 2020-04-23 17:03
 **/
@ControllerAdvice
@ResponseBody
@Log4j2
public class ExceptionAdvice {

    private static final String REQUESTBODY = "requestBodyMessage";

    @Value("${sys.version}")
    private String verSion;

    @Autowired
    private IExceptionLogService exceptionLogService;

    /**
     * 捕获系统异常，返回固定状态码
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler({Exception.class})
    public Result handleException(HttpServletRequest req, Exception e) throws Exception {
log.error(e);
        //构建系统异常信息
        ExceptionLog exceptionLog = this.createExceptionLog(req);

        if (e.getMessage() != null) {
            exceptionLog.setExcMessage(e.getMessage());
        } else {
            if (e.getStackTrace().length > 0) {
                StackTraceElement element = e.getStackTrace()[0];
                exceptionLog.setExcMessage(element.toString());
            }
        }
        //保存异常信息

        String excName = e.toString();
        if (excName.length() > 100) {
            excName = excName.substring(0, 90);
        }

        exceptionLog.setExcName(excName);
        exceptionLogService.save(exceptionLog);
        return Result.error(CodeMsg.SERVER_EXCEPTION, exceptionLog.getExcMessage());
    }

    /**
     * 捕获自定义异常，返回统一状态码
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler({MyExcption.class})
    public Result handleMyException(HttpServletRequest req, MyExcption e) throws Exception {
        log.error(e);
        //构建系统异常信息
        ExceptionLog exceptionLog = this.createExceptionLog(req);

        //保存异常信息
        if (e.getMessage() != null) {
            exceptionLog.setExcMessage(e.getMessage());
        } else {
            if (e.getStackTrace().length > 0) {
                StackTraceElement element = e.getStackTrace()[0];
                exceptionLog.setExcMessage(element.toString());
            }
        }

        exceptionLog.setExcName(e.toString());
        exceptionLogService.save(exceptionLog);
        Result result = Result.error(new CodeMsg(e.getCode(), e.getMessage()), exceptionLog.getExcMessage());
        return result;
    }

    /**
     * 由于body在接口读取后无法获取，这里把body提前取出放到参数中，在上面处理异常时使用
     */
    @ModelAttribute
    public void getBobyInfo(HttpServletRequest request) {
        //获取requestBody
        try {
            if(request instanceof StandardMultipartHttpServletRequest){
                return;
            }

            if (request instanceof HttpServletRequest) {
                ContentCachingRequestWrapper requestWapper = (ContentCachingRequestWrapper) request;
                byte[] bodys=requestWapper.getBody();
                if(null!=bodys&&bodys.length>0){
                    String body = IOUtils.toString(bodys, request.getCharacterEncoding());
                    request.setAttribute(REQUESTBODY, body);
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    /**
     * 根据HttpServletRequest对象构建系统异常日志
     * @param req
     * @return
     */
    private ExceptionLog createExceptionLog(HttpServletRequest req) throws Exception{

        //获取请求uri
        String uri = req.getRequestURI();

        //记录异常信息
        ExceptionLog exceptionLog = new ExceptionLog();
        exceptionLog.setOperCreateTime(LocalDateTime.now());
        Object body = req.getAttribute(REQUESTBODY);
        //判断参数是否是json
        if (body != null) {
            exceptionLog.setExcRequParam(body.toString());
            exceptionLog.setExcRequParamType("JSON");
        }
        exceptionLog.setOperUri(uri);

        //判断参数是否是from表单提交
        Map<String, String[]> parameterMap = req.getParameterMap();
        if (!parameterMap.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            exceptionLog.setExcRequParam(objectMapper.writeValueAsString(parameterMap));
            exceptionLog.setExcRequParamType("FROM");
        }

        //返回异常信息
        exceptionLog.setOperVer(verSion);
        exceptionLog.setOperIp(HttpClientUtil.getIpAddr(req));

        return exceptionLog;
    }

}
