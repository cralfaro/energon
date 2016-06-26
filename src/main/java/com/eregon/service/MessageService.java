package com.eregon.service;

import com.eregon.exception.MessageException;
import com.eregon.exception.UserException;
import com.eregon.model.Message;
import com.eregon.model.MessageResponse;
import com.eregon.model.User;

import java.util.List;

/**
 * Created by ruben on 26/06/16.
 */
public interface MessageService {
    boolean postMessage(Message message) throws MessageException, UserException;
    List<MessageResponse> retrieveMessages(User user);
    void removeAll();
    void removeAllMessages(String uuid);
}
