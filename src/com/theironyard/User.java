package com.theironyard;

/**
 * Created by vajrayogini on 2/22/16.
 */
public class User {


    String name;
    String password;

    public User(String name) {
        this.name = name;
    }

    public User(String password, String name) {
        this.password = password;
        this.name = name;
    }
}

