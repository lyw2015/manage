package com.ozf.laiyw.manage.service;

import com.github.pagehelper.PageInfo;
import com.ozf.laiyw.manage.model.LoginRecord;
import com.ozf.laiyw.manage.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    //统计每天的访客记录
    Map<String, Integer> countUserGuest();

    void login(User user);

    //统计今天新增用户数量
    int countTodayNewUser();

    User findByUserAccount(String userAccount);

    //获取角色
    List<String> findRolesByUserAccount(String userAccount);

    //获取权限
    List<String> findPermissionsByUserAccount(String userAccount);

    //保存访问记录
    int saveLoginRecord(String header, String clientIp);

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
