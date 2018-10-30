package com.ozf.laiyw.manage.service.impl;

import com.ozf.laiyw.manage.dao.mapper.UserMapper;
import com.ozf.laiyw.manage.model.User;
import com.ozf.laiyw.manage.service.UserService;
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
}
