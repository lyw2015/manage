package com.ozf.laiyw.manage.openapi.service.restful;

import com.ozf.laiyw.manage.model.User;
import com.ozf.laiyw.manage.openapi.service.soap.SoapUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/user")
@Produces({MediaType.APPLICATION_JSON})
@Component
public class RestUserService {

    @Autowired
    private SoapUserService soapUserService;

    @GET
    @Path("/getUser/{userId}")
    public User getUser(@PathParam("userId") String userId) {
        return soapUserService.getUser(userId);
    }

    @GET
    @Path("/listUser")
    public List<User> listUser() {
        return soapUserService.listUser();
    }
}
