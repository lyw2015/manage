package com.ozf.laiyw.manage.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ozf.laiyw.manage.common.commons.Constants;
import com.ozf.laiyw.manage.common.utils.DateUtils;
import com.ozf.laiyw.manage.common.utils.EmailUtils;
import com.ozf.laiyw.manage.common.utils.StringUtils;
import com.ozf.laiyw.manage.dao.mapper.UserMapper;
import com.ozf.laiyw.manage.model.Message;
import com.ozf.laiyw.manage.model.User;
import com.ozf.laiyw.manage.redis.utils.RedisCacheUtils;
import com.ozf.laiyw.manage.service.UserService;
import com.ozf.laiyw.manage.service.socket.SpringWebSocketHandler;
import com.ozf.laiyw.manage.service.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    @Value("${verificationCode.effective.time}")
    private Integer vcetime;
    @Value("${user.init.password}")
    private String initPassword;

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
    public String getVerificationCode(String email) {
        User user = getUserByEmail(email);
        if (null == user) {
            return "该邮箱尚未绑定账号";
        }
        String verificationCode = StringUtils.randomCode(6);
        boolean bl = EmailUtils.send(email, verificationCode, vcetime);
        if (!bl) {
            return Constants.ERROR_MESSAGE_NETWORK_ANOMALY;
        }
        redisCacheUtils.setCacheObject(Constants.VERIFICATION_CODE_PREFIX + email, verificationCode, vcetime, TimeUnit.MINUTES);
        return "邮件发送成功";
    }

    @Override
    public boolean checkVerificationCode(String email, String verificationCode) {
        String cacheCode = String.valueOf(redisCacheUtils.getCacheObject(Constants.VERIFICATION_CODE_PREFIX + email));
        if (verificationCode.equals(cacheCode)) {
            redisCacheUtils.delete(Constants.VERIFICATION_CODE_PREFIX + email);
            return true;
        }
        return false;
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

    @Override
    public List<String> findRolesByUserAccount(String userAccount) {
        return new ArrayList<>();
    }

    @Override
    public List<String> findPermissionsByUserAccount(String userAccount) {
        return new ArrayList<>();
    }
}
