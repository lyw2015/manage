package com.ozf.laiyw.manage.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ozf.laiyw.manage.common.utils.DateUtils;
import com.ozf.laiyw.manage.common.utils.StringUtils;
import com.ozf.laiyw.manage.dao.mapper.UserMapper;
import com.ozf.laiyw.manage.model.LoginRecord;
import com.ozf.laiyw.manage.model.Message;
import com.ozf.laiyw.manage.model.User;
import com.ozf.laiyw.manage.service.MessageService;
import com.ozf.laiyw.manage.service.UserService;
import com.ozf.laiyw.manage.service.socket.SpringWebSocketHandler;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MessageService messageService;
    @Autowired
    private SpringWebSocketHandler handler;

    @Override
    public void login(User user) {
        UsernamePasswordToken token = new UsernamePasswordToken(
                user.getAccount(),
                user.getPassword(),
                user.getRememberMe()
        );
        SecurityUtils.getSubject().login(token);
    }

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
        try {
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
            userMapper.saveLoginRecord(loginRecord);
            handler.sendMessageToUsers(new Message(messageService.getSocketMessage()));
            return 1;
        } catch (Exception e) {
            logger.error("save login record error", e);
            return 0;
        }
    }

    @Override
    public int updateLoginRecord(LoginRecord loginRecord) {
        LoginRecord exist = userMapper.findLoginRecordBySessionId(loginRecord.getSessionId());
        if (null == exist)
            return 0;
        long uptime = DateUtils.parseDate(loginRecord.getLastTime(), DateUtils.YYYY_MM_DD_HH_MM_SS).getTime()
                - DateUtils.parseDate(exist.getLoginTime(), DateUtils.YYYY_MM_DD_HH_MM_SS).getTime();
        loginRecord.setOnlineTime(DateUtils.formatDateAgo(uptime));
        userMapper.updateLoginRecord(loginRecord);
        if ("false".equals(loginRecord.getOnline())) {
            handler.sendMessageToUsers(new Message(messageService.getSocketMessage()));
        }
        return 1;
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

    @Override
    public int countTodayTuest() {
        return userMapper.countTodayTuest();
    }

    @Override
    public PageInfo guestRecord(PageInfo pageInfo, LoginRecord loginRecord) {
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        pageInfo.setList(userMapper.guestRecord(loginRecord));
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }
}
