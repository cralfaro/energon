package com.eregon.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by ruben on 26/06/16.
 */
public class User implements Serializable{

    @Getter @Setter
    private String uuid;

    @Getter @Setter
    private String name;

    public User(String uuid, String name){
        this.uuid = uuid;
        this.name = name;
    }

    public User(String name){
        this.name = name;
    }

    public User(){
    }

}
