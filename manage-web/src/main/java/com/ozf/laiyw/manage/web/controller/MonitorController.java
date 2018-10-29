package com.ozf.laiyw.manage.web.controller;

import com.ozf.laiyw.manage.common.annotation.SystemLog;
import com.ozf.laiyw.manage.dao.pagehelper.PageInfo;
import com.ozf.laiyw.manage.model.Log;
import com.ozf.laiyw.manage.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
