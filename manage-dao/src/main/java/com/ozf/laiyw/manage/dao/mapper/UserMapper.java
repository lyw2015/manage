package com.ozf.laiyw.manage.dao.mapper;

import com.ozf.laiyw.manage.model.LoginRecord;
import com.ozf.laiyw.manage.model.User;

import java.util.List;

public interface UserMapper {

    int countTodayNewUser();

    User findByUserAccount(String userAccount);

    int saveLoginRecord(LoginRecord loginRecord);

    int updateLoginRecord(LoginRecord loginRecord);

    LoginRecord findLoginRecordBySessionId(String sessionId);

    List<LoginRecord> onlineUser(LoginRecord loginRecord);

    int countOnline();
}
