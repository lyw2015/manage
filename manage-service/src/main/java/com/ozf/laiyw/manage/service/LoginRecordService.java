package com.ozf.laiyw.manage.service;

import com.github.pagehelper.PageInfo;
import com.ozf.laiyw.manage.model.LoginRecord;
import eu.bitwalker.useragentutils.UserAgent;

import java.util.List;
import java.util.Map;

public interface LoginRecordService {

    //获取用户登录信息（是否登录）
    List<LoginRecord> getOnlineUserByAccount(String userAccount);

    List<LoginRecord> getUserLoginRecordsByDay(String day);

    List<Map<String, String>> getUserLoginRecordDate();

    LoginRecord getUserLastLoginRecord();

    //统计每天的访客记录
    Map<String, Integer> countUserGuest();

    //保存访问记录
    int saveLoginRecord(UserAgent userAgent, String clientIp);

    //修改访问记录
    int updateLoginRecord(LoginRecord loginRecord);

    //在线用户
    PageInfo onlineUser(PageInfo pageInfo, LoginRecord loginRecord);

    //统计当前在线用户数
    int countOnline();

    //统计今日访客
    int countTodayTuest();

    //访客统计
    PageInfo guestRecord(PageInfo pageInfo, LoginRecord loginRecord);
}
