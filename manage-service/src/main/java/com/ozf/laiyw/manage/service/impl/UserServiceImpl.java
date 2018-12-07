package com.ozf.laiyw.manage.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ozf.laiyw.manage.common.commons.Constants;
import com.ozf.laiyw.manage.common.utils.DateUtils;
import com.ozf.laiyw.manage.common.utils.StringUtils;
import com.ozf.laiyw.manage.dao.mapper.UserMapper;
import com.ozf.laiyw.manage.model.Message;
import com.ozf.laiyw.manage.model.User;
import com.ozf.laiyw.manage.model.vo.VerificationCode;
import com.ozf.laiyw.manage.redis.utils.RedisCacheUtils;
import com.ozf.laiyw.manage.service.UserService;
import com.ozf.laiyw.manage.service.socket.SpringWebSocketHandler;
import com.ozf.laiyw.manage.service.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * @Description:
 * @Auther: Laiyw
 * @Date: 2018/11/29 19:27
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SpringWebSocketHandler handler;
    @Autowired
    private RedisCacheUtils redisCacheUtils;
    @Value("${user.init.password}")
    private String initPassword;
    @Value("${redis.verification.code.queue.key}")
    private String verificationCodeKey;

    @Override
    public PageInfo queryUser(PageInfo pageInfo, User user) {
        Page page = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize());
        pageInfo.setList(userMapper.queryUser(user));
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }

    @Override
    public int saveUser(User user) {
        User exuser = getUserByAccount(user.getAccount());
        if (null != exuser) {
            return -1;
        }
        if (StringUtils.isNotEmpty(user.getMailbox())) {
            exuser = getUserByEmail(user.getMailbox());
            if (null != exuser) {
                return -2;
            }
        }
        String userId = StringUtils.randUUID();
        user.setId(userId);
        user.setPassword(ShiroUtils.getHashPassword(initPassword, user.getAccount()));
        user.setCreateTime(DateUtils.getDateTime());
        user.setUpdateTime(DateUtils.getDateTime());
        userMapper.saveUser(user);
        if (StringUtils.isNotEmpty(user.getRoleIds())) {
            userMapper.saveUserRole(userId, Arrays.asList(user.getRoleIds().split(",")));
        }
        return 1;
    }

    @Override
    public int updateUser(User user) {
        if (StringUtils.isNotEmpty(user.getMailbox())) {
            int count = userMapper.countUnCurrentUserEmail(user.getMailbox(), user.getId());
            if (count > 0) {
                return -2;
            }
        }
        user.setUpdateTime(DateUtils.getDateTime());
        userMapper.updateUser(user);
        userMapper.removeUserRoleByUserId(user.getId());
        if (StringUtils.isNotEmpty(user.getRoleIds())) {
            userMapper.saveUserRole(user.getId(), Arrays.asList(user.getRoleIds().split(",")));
        }
        return 1;
    }

    @Override
    public User getUserById(String id) {
        return userMapper.getUserById(id);
    }

    @Override
    public int updateUserStatus(String id, int status) {
        User user = getUserById(id);
        if ("admin".equals(user.getAccount())) {
            return -1;
        }
        if (status == 0) {
            userMapper.removeUserRoleByUserId(id);
        }
        return userMapper.updateUserStatus(id, status);
    }

    @Override
    public void resetUserPwd(String id) {
        User user = getUserById(id);
        userMapper.updateUserPwd(id, ShiroUtils.getHashPassword(initPassword, user.getAccount()));
    }

    @Override
    public User getUserByAccount(String account) {
        return userMapper.getUserByAccount(account);
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    @Override
    public int getVerificationCode(String email) {
        User user = getUserByEmail(email);
        if (null == user) {
            return -1;
        }
        redisCacheUtils.leftPush(verificationCodeKey, VerificationCode.buildMail(Constants.LOGIN_VERIFICATION_CODE_PREFIX, email, user.getAccount()));
        return 1;
    }

    @Override
    public int getVerificationCodeByType(String account, String type) {
        User user = getUserByAccount(account);
        if (null == user) {
            return 0;
        }
        if (type.equals(VerificationCode.SEND_TYPE_EMAIL)) {
            if (StringUtils.isEmpty(user.getMailbox())) {
                return -1;
            }
        } else if (type.equals(VerificationCode.SEND_TYPE_PHONE)) {
            if (StringUtils.isEmpty(user.getPhone())) {
                return -2;
            }
        } else {
            return -9;
        }
        redisCacheUtils.leftPush(verificationCodeKey, new VerificationCode(type, Constants.FORGETPWD_VERIFICATION_CODE_PREFIX, user.getMailbox(), null, user.getAccount()));
        return 1;
    }

    @Override
    public int forgetPwd(String account, String verificationCode, String password) {
        User user = getUserByAccount(account);
        if (null == user) {
            return 0;
        }
        int count = checkVerificationCode(Constants.FORGETPWD_VERIFICATION_CODE_PREFIX, user.getAccount(), verificationCode);
        if (count != 1) {
            return count;
        }
        userMapper.updateUserPwd(user.getId(), ShiroUtils.getHashPassword(password, user.getAccount()));
        return 1;
    }

    /**
     * 校验验证码
     *
     * @param account
     * @param verificationCode
     * @return -1：验证码失效  -2：验证码错误
     */
    @Override
    public int checkVerificationCode(String prefix, String account, String verificationCode) {
        Object object = redisCacheUtils.getCacheObject(prefix + account);
        if (null == object) {
            return -1;
        }
        if (!verificationCode.equals(String.valueOf(object))) {
            return -2;
        }
        redisCacheUtils.delete(prefix + account);
        return 1;
    }

    @Override
    public int updateUserPwd(String oldpassword, String newpassword) {
        User user = ShiroUtils.getCurrentUser();
        if (!user.getPassword().equals(ShiroUtils.getHashPassword(oldpassword, user.getAccount()))) {
            return -1;
        }
        return userMapper.updateUserPwd(ShiroUtils.getCurrentUser().getId(), ShiroUtils.getHashPassword(newpassword, user.getAccount()));
    }

    @Override
    public int updateUserInfo(User user) {
        User current = ShiroUtils.getCurrentUser();
        user.setId(current.getId());
        if (StringUtils.isNotEmpty(user.getMailbox())) {
            int count = userMapper.countUnCurrentUserEmail(user.getMailbox(), user.getId());
            if (count > 0) {
                return -2;
            }
        }
        int count = userMapper.updateUserInfo(user);
        if (count == 1) {
            current = getUserByAccount(current.getAccount());
            ShiroUtils.refreshUser(current);
            handler.sendMessageToUsers(new Message(user.getId(), current));
        }
        return count;
    }

    @Override
    public int countTodayNewUser() {
        return userMapper.countTodayNewUser();
    }

    @Override
    public User findUserByAccountOrMailbox(String userAccount) {
        return userMapper.findUserByAccountOrMailbox(userAccount);
    }
}
