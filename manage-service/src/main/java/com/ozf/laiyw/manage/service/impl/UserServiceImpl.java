package com.ozf.laiyw.manage.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ozf.laiyw.manage.common.utils.DateUtils;
import com.ozf.laiyw.manage.common.utils.StringUtils;
import com.ozf.laiyw.manage.dao.mapper.UserMapper;
import com.ozf.laiyw.manage.model.LoginRecord;
import com.ozf.laiyw.manage.model.User;
import com.ozf.laiyw.manage.service.UserService;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int countTodayNewUser() {
        return userMapper.countTodayNewUser();
    }

    @Override
    public User findByUserAccount(String userAccount) {
        return userMapper.findByUserAccount(userAccount);
    }

    @Override
    public List<String> findRolesByUserAccount(String userAccount) {
        return new ArrayList<>();
    }

    @Override
    public List<String> findPermissionsByUserAccount(String userAccount) {
        return new ArrayList<>();
    }

    @Override
    public int saveLoginRecord(String header, String clientIp) {
        Session session = SecurityUtils.getSubject().getSession();
        UserAgent userAgent = UserAgent.parseUserAgentString(header);

        LoginRecord loginRecord = new LoginRecord();
        loginRecord.setId(StringUtils.randUUID());
        loginRecord.setOnline("true");
        loginRecord.setClientIp(clientIp);
        loginRecord.setOperatingSystemName(userAgent.getOperatingSystem().getName());
        loginRecord.setBrowser(userAgent.getBrowser().getName());
        loginRecord.setUserName(((User) SecurityUtils.getSubject().getPrincipal()).getUsername());

        loginRecord.setSessionId(session.getId().toString());
        loginRecord.setVisitTime(DateUtils.formatDateTime(session.getStartTimestamp()));//访问时间
        loginRecord.setLastTime(DateUtils.formatDateTime(session.getLastAccessTime()));//最后操作时间
        loginRecord.setLoginTime(DateUtils.formatDateTime(session.getStartTimestamp()));//登录时间
        return userMapper.saveLoginRecord(loginRecord);
    }

    @Override
    public int updateLoginRecord(LoginRecord loginRecord) {
        LoginRecord exist = userMapper.findLoginRecordBySessionId(loginRecord.getSessionId());
        if (null == exist)
            return 0;
        long uptime = DateUtils.parseDate(loginRecord.getLastTime(), DateUtils.YYYY_MM_DD_HH_MM_SS).getTime()
                - DateUtils.parseDate(exist.getLoginTime(), DateUtils.YYYY_MM_DD_HH_MM_SS).getTime();
        loginRecord.setOnlineTime(DateUtils.formatDateAgo(uptime));
        return userMapper.updateLoginRecord(loginRecord);
    }

    @Override
    public PageInfo onlineUser(PageInfo pageInfo, LoginRecord loginRecord) {
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        pageInfo.setList(userMapper.onlineUser(loginRecord));
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }

    @Override
    public int countOnline() {
        return userMapper.countOnline();
    }
}
