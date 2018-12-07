package com.ozf.laiyw.manage.service;

import com.github.pagehelper.PageInfo;
import com.ozf.laiyw.manage.model.User;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/29 19:26
 */
public interface UserService {

    PageInfo queryUser(PageInfo pageInfo, User user);

    int saveUser(User user);

    int updateUser(User user);

    User getUserById(String id);

    int updateUserStatus(String id, int status);

    void resetUserPwd(String id);

    User getUserByAccount(String account);

    User getUserByEmail(String email);

    int getVerificationCode(String email);

    int getVerificationCodeByType(String account, String type);

    int forgetPwd(String account, String verificationCode, String password);

    int checkVerificationCode(String prefix, String account, String verificationCode);

    //修改用户密码
    int updateUserPwd(String oldpassword, String newpassword);

    //修改用户信息
    int updateUserInfo(User user);

    //统计今天新增用户数量
    int countTodayNewUser();

    User findUserByAccountOrMailbox(String userAccount);
}
