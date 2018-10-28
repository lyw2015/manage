package com.ozf.laiyw.manage.web.controller;

import com.ozf.laiyw.manage.common.annotation.SystemLog;
import com.ozf.laiyw.manage.model.Log;
import com.ozf.laiyw.manage.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/10/27 17:23
 */
@SystemLog(description = "日志管理")
@RequestMapping("/log")
@Controller
public class LogController {

    @Autowired
    private LogService logService;

    @SystemLog(description = "查询日志")
    @RequestMapping("/queryLog")
    @ResponseBody
    public List<Log> queryLog() {
        return logService.queryLog();
    }
}
