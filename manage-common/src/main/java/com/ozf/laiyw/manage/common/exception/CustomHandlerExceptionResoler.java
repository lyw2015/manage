package com.ozf.laiyw.manage.common.exception;

import com.alibaba.fastjson.JSON;
import com.ozf.laiyw.manage.common.commons.WebResult;
import com.ozf.laiyw.manage.common.utils.MBUtils;
import com.ozf.laiyw.manage.common.utils.WebUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CustomHandlerExceptionResoler implements HandlerExceptionResolver {

    private final Logger logger = Logger.getLogger(this.getClass());

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        logger.error(e);
        //如果请求是ajax 返回json格式
        if (WebUtils.isAjax(request)) {
            try {
                response.setContentType("application/json;charset=UTF-8");
                PrintWriter writer = response.getWriter();
                if (e instanceof CustomException) {
                    writer.write(JSON.toJSONString(WebResult.errorResult(e.getMessage())));
                } else {
                    writer.write(JSON.toJSONString(WebResult.errorNetworkAnomalyResult()));
                }
                writer.flush();
                writer.close();
            } catch (IOException e1) {
                e1.printStackTrace();
                logger.error(e1);
            }
        } else {
            // 如果不是ajax，JSP格式返回
            WebResult webResult = null;
            if (e instanceof CustomException) {
                webResult = WebResult.errorResult(e.getMessage());
            } else {
                webResult = WebResult.errorNetworkAnomalyResult();
            }
            return new ModelAndView("/error.jsp", MBUtils.convertObjToMap(webResult));
        }
        return null;
    }
}
