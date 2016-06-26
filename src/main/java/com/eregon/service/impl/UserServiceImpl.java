package com.eregon.service.impl;

import com.eregon.exception.UserException;
import com.eregon.model.User;
import com.eregon.service.MessageService;
import com.eregon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ruben on 26/06/16.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final String USER_NOT_FOUND = "The user is not stored";

    Map<String, User> usersStorage = new HashMap<>();

    @Autowired
    MessageService messageService;

    @Override
    public User createUser(User newUser) {
        UUID id = UUID.randomUUID();
        while(isExistingUser(id.toString())){
            id = UUID.randomUUID();
        }
        User user = new User(id.toString(), newUser.getName());
        storeUser(user);
        return user;
    }

    @Override
    public boolean saveUser(User newUser) {
        while(isExistingUser(newUser.getUuid())){
            newUser.setUuid(UUID.randomUUID().toString());
        }
        storeUser(newUser);
        return true;
    }

    private void storeUser(User user) {
        usersStorage.put(user.getUuid(), user);
    }

    @Override
    public boolean removeUser(String uuid) {
        if(!isExistingUser(uuid)){
            return false;
        }
        usersStorage.remove(uuid);
        messageService.removeAllMessages(uuid);
        return true;
    }

    @Override
    public User getUser(String uuid) throws UserException{
        if(!isExistingUser(uuid)){
            throw new UserException(USER_NOT_FOUND);
        }
        return usersStorage.get(uuid);
    }

    @Override
    public List<User> getUsers() {
        return usersStorage.keySet().stream().map(usersStorage::get).collect(Collectors.toList());
    }

    public boolean isExistingUser(String uuid){
        return usersStorage.containsKey(uuid);
    }

    @Override
    public void removeAll() {
        usersStorage = new HashMap<>();
    }
}
