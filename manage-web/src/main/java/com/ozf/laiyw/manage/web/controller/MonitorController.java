package com.ozf.laiyw.manage.web.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.ozf.laiyw.manage.common.annotation.SystemLog;
import com.ozf.laiyw.manage.common.commons.WebResult;
import com.ozf.laiyw.manage.model.Log;
import com.ozf.laiyw.manage.model.LoginRecord;
import com.ozf.laiyw.manage.redis.utils.RedisCacheUtils;
import com.ozf.laiyw.manage.service.LogService;
import com.ozf.laiyw.manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

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
    @Autowired
    private RedisCacheUtils redisCacheUtils;

    @SystemLog(description = "查看缓存")
    @RequestMapping("/getCacheKeysByCond")
    @ResponseBody
    public PageInfo getCacheKeysByCond(PageInfo pageInfo, @RequestParam(required = false, defaultValue = "*") String key) {
        Set keys = redisCacheUtils.keys(new StringBuffer("*").append(key).append("*").toString());
        pageInfo.setList(new ArrayList(keys));
        pageInfo.setTotal(keys.size());
        return pageInfo;
    }

    @RequestMapping("/getValueByKey")
    @ResponseBody
    public String getValueByKey(String mapKey, String dataKey) {
        return (String) redisCacheUtils.getMapDataByKey(mapKey, dataKey);
    }

    @SystemLog(description = "删除Map中缓存Key")
    @RequestMapping("/removeMapKey")
    @ResponseBody
    public WebResult removeMapKey(String mapKey, String dataKey) {
        redisCacheUtils.deleteMapDataByKey(mapKey, dataKey);
        return WebResult.successResult();
    }

    @SystemLog(description = "删除缓存")
    @RequestMapping("/removeKey")
    @ResponseBody
    public WebResult removeKey(String key) {
        redisCacheUtils.delete(key);
        return WebResult.successResult();
    }

    @RequestMapping("/getCacheSonKeys")
    @ResponseBody
    public PageInfo getCacheSonKeys(PageInfo pageInfo, String key) {
        Object values = redisCacheUtils.getByKey(key);
        if (null == values) {
            pageInfo.setList(new ArrayList());
            return pageInfo;
        }
        if (values instanceof Map) {
            Set set = ((Map) values).keySet();
            pageInfo.setList(new ArrayList(set));
            pageInfo.setTotal(set.size());
            return pageInfo;
        }
        pageInfo.setList(Arrays.asList(JSON.toJSONString(values, true)));
        return pageInfo;
    }

    @RequestMapping("/countUserGuest")
    @ResponseBody
    public Map<String, Integer> countUserGuest() {
        return userService.countUserGuest();
    }

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
