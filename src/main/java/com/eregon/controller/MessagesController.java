package com.eregon.controller;

import com.eregon.exception.MessageException;
import com.eregon.exception.UserException;
import com.eregon.model.Message;
import com.eregon.model.MessageResponse;
import com.eregon.model.User;
import com.eregon.service.MessageService;
import com.eregon.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by ruben on 26/06/16.
 */
@Controller
public class MessagesController {

    final static Logger logger = Logger.getLogger(MessagesController.class);

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;


    @RequestMapping(value = "/messages", method = RequestMethod.POST)
    @ResponseBody
    public void storeMessage(@RequestBody Message message) {
        try {
            messageService.postMessage(message);
        }catch(Exception ex){
            logger.error("An exception has occurred trying to store a message", ex);
        }
    }

    @RequestMapping(value = "/messages/users/{uuid}", method = RequestMethod.GET)
    @ResponseBody
    public List<MessageResponse> storeMessage(@PathVariable("uuid")String uuid) {
        try {
            User user = userService.getUser(uuid);
            return messageService.retrieveMessages(user);
        }catch (UserException ex){
            logger.error("An exception has occurred trying to retrieve messages for user: " + uuid, ex);
        }
        return null;
    }
}
