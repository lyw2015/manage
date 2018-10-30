package com.ozf.laiyw.manage.service;

import com.ozf.laiyw.manage.model.User;

import java.util.List;

public interface UserService {

    User findByUserAccount(String userAccount);

    List<String> findRolesByUserAccount(String userAccount);

    List<String> findPermissionsByUserAccount(String userAccount);
}
