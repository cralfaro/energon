package com.eregon.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by ruben on 26/06/16.
 */
public class Message implements Serializable{

    @Getter @Setter
    private String sender;

    @Getter @Setter
    private String receiver;

    @Getter @Setter
    private String senderName;

    @Getter @Setter
    private String receiverName;

    @Getter @Setter
    private String message;

    public Message(String sender, String receiver, String message){
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    public Message(){
    }

}
