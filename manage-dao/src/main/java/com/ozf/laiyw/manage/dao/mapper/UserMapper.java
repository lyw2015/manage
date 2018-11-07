package com.ozf.laiyw.manage.dao.mapper;

import com.ozf.laiyw.manage.model.LoginRecord;
import com.ozf.laiyw.manage.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    User getUserByEmail(String email);

    List<LoginRecord> getOnlineUserByAccount(String userAccount);

    List<LoginRecord> getUserLoginRecordsByDay(@Param("userAccount") String userAccount, @Param("day") String day);

    List<Map<String, String>> getUserLoginRecordDate(String userAccount);

    LoginRecord getLastLoginRecord(String userAccount);

    int updateUserPwd(@Param("id") String id, @Param("password") String password);

    int updateUserInfo(User user);

    int countTodayNewUser();

    User findByUserAccount(String userAccount);

    int saveLoginRecord(LoginRecord loginRecord);

    int updateLoginRecord(LoginRecord loginRecord);

    LoginRecord findLoginRecordBySessionId(String sessionId);

    List<LoginRecord> onlineUser(LoginRecord loginRecord);

    int countOnline();

    int countTodayTuest();

    List<LoginRecord> guestRecord(LoginRecord loginRecord);

    //统计两个时间点间的访问量
    List<Map<String, Integer>> countUserGuest(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
