package com.eregon.controller;

import com.eregon.comparator.UserComparator;
import com.eregon.exception.UserException;
import com.eregon.model.User;
import com.eregon.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

@RestController
public class UserController {

    final static Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    @ResponseBody
    public User createUser(@RequestBody User user, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_CREATED);
        return userService.createUser(user);
    }

    @RequestMapping(value = "/users/{uuid}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteUser(@PathVariable("uuid")String uuid, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        userService.removeUser(uuid);
    }

    @RequestMapping(value = "/users/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public User getUser(@PathVariable("uuid")String uuid, HttpServletResponse response) {
        try{
            User user = userService.getUser(uuid);
            response.setStatus(HttpServletResponse.SC_OK);
            return user;
        }catch(UserException ex){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            logger.error("An error has occurred trying to retrieve the user with id: " + uuid, ex);
        }
        return null;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    @ResponseBody
    public List<User> getUsers(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        List<User> users = userService.getUsers();
        Collections.sort(users, new UserComparator());
        return users;
    }

}
