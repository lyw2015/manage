package com.ozf.laiyw.manage.web.controller;

import com.github.pagehelper.PageInfo;
import com.ozf.laiyw.manage.common.annotation.SystemLog;
import com.ozf.laiyw.manage.model.Log;
import com.ozf.laiyw.manage.model.LoginRecord;
import com.ozf.laiyw.manage.service.LogService;
import com.ozf.laiyw.manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/10/27 17:23
 */
@SystemLog(description = "监控管理")
@RequestMapping("/monitor")
@Controller
public class MonitorController extends BaseController {

    @Autowired
    private LogService logService;
    @Autowired
    private UserService userService;

    @SystemLog(description = "查看访客记录")
    @RequestMapping("/guestRecord")
    @ResponseBody
    public PageInfo guestRecord(PageInfo pageInfo, LoginRecord loginRecord) {
        return userService.guestRecord(pageInfo, loginRecord);
    }

    @SystemLog(description = "查看在线用户")
    @RequestMapping("/onlineUser")
    @ResponseBody
    public PageInfo onlineUser(PageInfo pageInfo, LoginRecord loginRecord) {
        return userService.onlineUser(pageInfo, loginRecord);
    }

    @SystemLog(description = "数据监控")
    @RequestMapping("/druid")
    public void druid() {
        redirect("/druid/index.html");
    }

    @SystemLog(description = "服务器监控")
    @RequestMapping("/server")
    public void server() {
        redirect("/server/index.html");
    }

    @SystemLog(description = "查询日志")
    @RequestMapping("/queryLog")
    @ResponseBody
    public PageInfo queryLog(PageInfo pageInfo, Log log) {
        return logService.queryLog(pageInfo, log);
    }

    @SystemLog(description = "查看日志详细信息")
    @RequestMapping("/detailLog")
    public ModelAndView detailLog(String id) {
        ModelAndView view = new ModelAndView("monitor/accessing-log-detail");
        view.addObject("log", logService.detailLog(id));
        return view;
    }
}
