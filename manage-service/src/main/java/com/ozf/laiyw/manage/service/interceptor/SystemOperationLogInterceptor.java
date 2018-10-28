package com.ozf.laiyw.manage.service.interceptor;

import com.alibaba.fastjson.JSON;
import com.ozf.laiyw.manage.common.annotation.SystemLog;
import com.ozf.laiyw.manage.common.utils.AddressUtils;
import com.ozf.laiyw.manage.common.utils.ByteUtils;
import com.ozf.laiyw.manage.common.utils.DateUtils;
import com.ozf.laiyw.manage.common.utils.StringUtils;
import com.ozf.laiyw.manage.model.Log;
import com.ozf.laiyw.manage.model.User;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Description: 系统操作日志
 * @Auther: Laiyw
 * @Date: 2018/9/30 10:54
 */
public class SystemOperationLogInterceptor implements HandlerInterceptor {

    private final Logger logger = Logger.getLogger(this.getClass());
    private final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        long beginTime = System.currentTimeMillis();
        threadLocal.set(beginTime);
        logger.info("计时开始：" + DateUtils.formatDate(beginTime, DateUtils.HHMMSSSSS) + " URI：" + httpServletRequest.getRequestURI());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if (null != modelAndView) {
            logger.info("ViewName：" + modelAndView.getViewName() + " URI：" + httpServletRequest.getRequestURI() + "--->" + o);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, Exception e) throws Exception {
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - threadLocal.get();
        threadLocal.remove();
        Runtime runtime = Runtime.getRuntime();

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (handlerMethod.hasMethodAnnotation(SystemLog.class)) {
                saveLog(handlerMethod, httpServletRequest, e, executeTime);
            }
        }
        logger.info("计时结束：" + DateUtils.formatDate(endTime, DateUtils.HHMMSSSSS) + " 用时：" + DateUtils.formatDateAgo(executeTime) + " 总内存：" + ByteUtils.formatByteSize(runtime.totalMemory()) + " 已用内存：" + ByteUtils.formatByteSize(runtime.totalMemory() - runtime.freeMemory()));
    }

    private void saveLog(HandlerMethod handlerMethod, HttpServletRequest httpServletRequest, Exception e, long executeTime) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String ip = AddressUtils.getIpAddress(httpServletRequest);
        String agent = httpServletRequest.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(agent);

        Log log = new Log();
        log.setId(StringUtils.randUUID());
        log.setClientIp(ip);
        log.setOperatingSystemName(userAgent.getOperatingSystem().getName());
        log.setBrowser(userAgent.getBrowser().getName());
        log.setAgent(agent);
        log.setRequestURI(httpServletRequest.getRequestURI());
        log.setRequestMethod(httpServletRequest.getMethod());
        log.setRequestParameter(getParameter(httpServletRequest));
        log.setOperationTime(DateUtils.getDateTime());
        if (null != user) {
            log.setOperationUsername(user.getUsername());
        }
        log.setOperationDescription(getLogDescription(handlerMethod));
        log.setResponseTime(DateUtils.formatDateAgo(executeTime));
        boolean isError = null != e;
        log.setIsError(String.valueOf(isError));
        if (isError) {
            log.setErrorMessage(ExceptionUtils.getStackTrace(e));
        }
        System.out.println(JSON.toJSONString(log, true));
    }

    private String getLogDescription(HandlerMethod handlerMethod) {
        StringBuffer description = new StringBuffer();
        SystemLog beanLog = handlerMethod.getMethod().getDeclaringClass().getAnnotation(SystemLog.class);
        if (null != beanLog) {
            description.append(beanLog.description()).append("/");
        }
        description.append(handlerMethod.getMethodAnnotation(SystemLog.class).description());
        return description.toString();
    }

    private String getParameter(HttpServletRequest httpServletRequest) {
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        if (null == parameterMap || parameterMap.isEmpty())
            return "";
        StringBuffer sb = new StringBuffer();
        String value = null;
        for (String name : parameterMap.keySet()) {
            value = String.join(",", parameterMap.get(name));
            if (httpServletRequest.getRequestURI().contains("/login") && "password".equals(name)) {
                value = "******";
            }
            sb.append(name).append("=").append(value).append("&");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }
}
