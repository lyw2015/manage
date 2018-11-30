package com.ozf.laiyw.manage.dao.mapper;

import com.ozf.laiyw.manage.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/29 18:34
 */
public interface UserMapper {

    List<User> queryUser(User user);

    void removeUserRoleByUserId(String userId);

    void saveUserRole(@Param("userId") String userId, @Param("roleIds") List<String> roleIds);

    int updateUserStatus(@Param("id") String id, @Param("status") int status);

    int saveUser(User user);

    int updateUser(User user);

    User getUserById(String id);

    User getUserByAccount(String account);

    User getUserByEmail(String email);

    int countUnCurrentUserEmail(@Param("email") String email, @Param("id") String id);

    int updateUserPwd(@Param("id") String id, @Param("password") String password);

    int updateUserInfo(User user);

    User findUserByAccountOrMailbox(String userAccount);

    int countTodayNewUser();
}
