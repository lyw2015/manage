package com.ozf.laiyw.manage.openapi.service.soap.impl;

import com.ozf.laiyw.manage.model.User;
import com.ozf.laiyw.manage.openapi.service.soap.SoapUserService;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

/**
 * serviceName：对外发布的服务名
 * targetNamespace：指定你想要的名称空间，通常使用使用包名反转
 * endpointInterface：服务接口全路径, 指定做SEI（Service EndPoint Interface）服务端点接口
 */
@WebService(serviceName = "SoapUserService",
        endpointInterface = "com.ozf.laiyw.manage.openapi.service.soap.SoapUserService",
        targetNamespace = "http://soap.service.openapi.manage.laiyw.ozf.com/")
@Component
public class SoapUserServiceImpl implements SoapUserService {

    private List<User> userList = new ArrayList<User>();

    public SoapUserServiceImpl() {
        User user = new User();
        user.setId("1");
        user.setUsername("张三");
        userList.add(user);

        user = new User();
        user.setId("2");
        user.setUsername("李四");
        userList.add(user);

        user = new User();
        user.setId("3");
        user.setUsername("王五");
        userList.add(user);
    }

    @Override
    public User getUser(String userId) {
        return userList.get(Integer.valueOf(userId) - 1);
    }

    @Override
    public List<User> listUser() {
        return userList;
    }
}
