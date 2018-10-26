package com.ozf.laiyw.manage.service;

import com.ozf.laiyw.manage.model.User;

import java.util.List;

public interface UserService {

    User findByUserName(String userName);

    List<String> findRolesByUserName(String userName);

    List<String> findPermissionsByUserName(String userName);
}
