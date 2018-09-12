package com.ozf.laiyw.manage.openapi.service.soap;

import com.ozf.laiyw.manage.model.User;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;

@WebService
public interface SoapUserService {

    @WebMethod
    User getUser(@WebParam(name = "userId") String userId);

    @WebMethod
    List<User> listUser();
}
