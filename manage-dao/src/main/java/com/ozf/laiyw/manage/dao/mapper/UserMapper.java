package com.ozf.laiyw.manage.dao.mapper;

import com.ozf.laiyw.manage.model.User;

public interface UserMapper {

    User findByUserName(String userName);
}
