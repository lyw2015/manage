package com.ozf.laiyw.manage.web.controller;

import com.ozf.laiyw.manage.model.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/10/26 18:41
 */
public class BaseController {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }

    //重定向到指定URL
    protected void redirect(String url) {
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    protected User getCurrentUser() {
        return (User) SecurityUtils.getSubject().getPrincipal();
    }
}