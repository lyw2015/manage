package com.ozf.laiyw.manage.common.interceptor;

import com.ozf.laiyw.manage.common.utils.ByteUtils;
import com.ozf.laiyw.manage.common.utils.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        long endTime = System.currentTimeMillis();
        long executeTime = endTime - threadLocal.get();
        threadLocal.remove();
        Runtime runtime = Runtime.getRuntime();
        logger.info("计时结束：" + DateUtils.formatDate(endTime, DateUtils.HHMMSSSSS) + " 用时：" + DateUtils.formatDateAgo(executeTime) + " 总内存：" + ByteUtils.formatByteSize(runtime.totalMemory()) + " 已用内存：" + ByteUtils.formatByteSize(runtime.totalMemory() - runtime.freeMemory()));
    }
}
