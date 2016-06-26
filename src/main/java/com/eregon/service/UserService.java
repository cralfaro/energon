package com.eregon.service;

import com.eregon.exception.UserException;
import com.eregon.model.User;

import java.util.List;

/**
 * Created by ruben on 26/06/16.
 */
public interface UserService {
    User createUser(User user);
    boolean saveUser(User user);
    boolean removeUser(String uuid);
    User getUser(String uuid) throws UserException;
    List<User> getUsers();
    boolean isExistingUser(String uuid);
    void removeAll();

}
