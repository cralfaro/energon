package com.eregon.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by ruben on 26/06/16.
 */
public class MessageResponse implements Serializable{

    @Getter @Setter
    private String sender;

    @Getter @Setter
    private String receiver;

    @Getter @Setter
    private String message;

}
