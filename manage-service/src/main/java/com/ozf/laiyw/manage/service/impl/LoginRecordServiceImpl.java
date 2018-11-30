package com.ozf.laiyw.manage.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ozf.laiyw.manage.common.commons.Constants;
import com.ozf.laiyw.manage.common.utils.DateUtils;
import com.ozf.laiyw.manage.common.utils.StringUtils;
import com.ozf.laiyw.manage.dao.mapper.LoginRecordMapper;
import com.ozf.laiyw.manage.model.LoginRecord;
import com.ozf.laiyw.manage.model.Message;
import com.ozf.laiyw.manage.model.User;
import com.ozf.laiyw.manage.redis.utils.RedisCacheUtils;
import com.ozf.laiyw.manage.service.LoginRecordService;
import com.ozf.laiyw.manage.service.MessageService;
import com.ozf.laiyw.manage.service.socket.SpringWebSocketHandler;
import com.ozf.laiyw.manage.service.utils.ShiroUtils;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.log4j.Logger;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service
public class LoginRecordServiceImpl implements LoginRecordService {

    private final Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private LoginRecordMapper loginRecordMapper;
    @Autowired
    private MessageService messageService;
    @Autowired
    private SpringWebSocketHandler handler;
    @Autowired
    private RedisCacheUtils redisCacheUtils;
    @Value("${session.shareSessionMapCache}")
    private String shareSessionMapCache;

    @Override
    public List<LoginRecord> getOnlineUserByAccount(String userAccount) {
        return loginRecordMapper.getOnlineUserByAccount(userAccount);
    }

    @Override
    public List<LoginRecord> getUserLoginRecordsByDay(String day) {
        return loginRecordMapper.getUserLoginRecordsByDay(ShiroUtils.getCurrentUser().getAccount(), day);
    }

    @Override
    public List<Map<String, String>> getUserLoginRecordDate() {
        return loginRecordMapper.getUserLoginRecordDate(ShiroUtils.getCurrentUser().getAccount());
    }

    @Override
    public LoginRecord getUserLastLoginRecord() {
        return loginRecordMapper.getLastLoginRecord(ShiroUtils.getCurrentUser().getAccount());
    }


    @Override
    public Map<String, Integer> countUserGuest() {
        Date before = DateUtils.getBeforeDayDate(6);
        Date current = new Date();
        List<Map<String, Integer>> list = loginRecordMapper.countUserGuest(DateUtils.formatDateTime(before), DateUtils.formatDateTime(current));
        Map<String, Integer> resultMap = new HashMap<>();
        for (Map map : list) {
            resultMap.put(map.get("lrtime").toString(), Integer.valueOf(map.get("lrcount").toString()));
        }
        String md = null;
        while (before.compareTo(current) < 1) {
            md = DateUtils.formatDate(before, "MM-dd");
            if (!resultMap.containsKey(md)) {
                resultMap.put(md, 0);
            }
            before = new Date(before.getTime() + 1000 * 60 * 60 * 24);
        }
        return sortMap(resultMap);
    }

    private Map<String, Integer> sortMap(Map<String, Integer> map) {
        Map<String, Integer> sortMap = new TreeMap<>(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    class MapKeyComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }


    @Override
    public int saveLoginRecord(UserAgent userAgent, String clientIp) {
        try {
            Session session = ShiroUtils.getSubject().getSession();
            //当前浏览器是否已登录  如果当前为在线状态 再进行登录则退出之前的登录
            LoginRecord exist = loginRecordMapper.findLoginRecordByCond(clientIp, userAgent.getOperatingSystem().getName(), userAgent.getBrowser().getName());
            if (null != exist) {
                LoginRecord exit = new LoginRecord();
                exit.setSessionId(exist.getSessionId());
                exit.setOnline(Boolean.FALSE.toString());
                exit.setLogoutTime(DateUtils.formatDateTime(new Date()));
                updateLoginRecord(exit);
                if (!session.getId().toString().equals(exist.getSessionId())) {
                    redisCacheUtils.deleteMapDataByKey(shareSessionMapCache, exist.getSessionId());
                }
            }
            User user = ShiroUtils.getCurrentUser();

            LoginRecord loginRecord = new LoginRecord();
            loginRecord.setId(StringUtils.randUUID());
            loginRecord.setOnline(Boolean.TRUE.toString());
            loginRecord.setClientIp(clientIp);
            loginRecord.setDeviceType(userAgent.getOperatingSystem().getDeviceType().getName());
            loginRecord.setGroupName(userAgent.getOperatingSystem().getGroup().getName());
            loginRecord.setOperatingSystemName(userAgent.getOperatingSystem().getName());
            loginRecord.setBrowser(userAgent.getBrowser().getName());
            loginRecord.setUserAccount(user.getAccount());
            loginRecord.setUserName(user.getUsername());

            loginRecord.setSessionId(session.getId().toString());
            loginRecord.setVisitTime(DateUtils.getDateTime());//访问时间
            loginRecord.setLastTime(DateUtils.formatDateTime(session.getLastAccessTime()));//最后操作时间
            loginRecord.setLoginTime(DateUtils.getDateTime());//登录时间
            loginRecordMapper.saveLoginRecord(loginRecord);
            handler.sendMessageToUsers(new Message(messageService.getSocketMessage()));
            return 1;
        } catch (Exception e) {
            logger.error("save login record error", e);
            return 0;
        }
    }

    @Override
    public int updateLoginRecord(LoginRecord loginRecord) {
        LoginRecord exist = loginRecordMapper.findLoginRecordBySessionId(loginRecord.getSessionId());
        if (null == exist)
            return 0;
        String lastTime = loginRecord.getLastTime();
        if (Boolean.FALSE.toString().equals(loginRecord.getOnline())) {
            lastTime = loginRecord.getLogoutTime();
        }
        long uptime = DateUtils.parseDate(lastTime, DateUtils.YYYY_MM_DD_HH_MM_SS).getTime()
                - DateUtils.parseDate(exist.getLoginTime(), DateUtils.YYYY_MM_DD_HH_MM_SS).getTime();
        loginRecord.setOnlineTime(DateUtils.formatDateAgo(uptime));
        int count = loginRecordMapper.updateLoginRecord(loginRecord);
        if (Boolean.FALSE.toString().equals(loginRecord.getOnline()) && count == 1) {
            handler.sendMessageToUsers(new Message(messageService.getSocketMessage()));
        }
        return 1;
    }

    @Override
    public PageInfo onlineUser(PageInfo pageInfo, LoginRecord loginRecord) {
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        pageInfo.setList(loginRecordMapper.onlineUser(loginRecord));
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }

    @Override
    public int countOnline() {
        return loginRecordMapper.countOnline();
    }

    @Override
    public int countTodayTuest() {
        return loginRecordMapper.countTodayTuest();
    }

    @Override
    public PageInfo guestRecord(PageInfo pageInfo, LoginRecord loginRecord) {
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        pageInfo.setList(loginRecordMapper.guestRecord(loginRecord));
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }
}
