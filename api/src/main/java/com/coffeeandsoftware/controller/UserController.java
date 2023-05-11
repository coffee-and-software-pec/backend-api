package com.coffeeandsoftware.controller;

import com.coffeeandsoftware.model.User;

public class UserController {
    public void saveUser(String name, String email) {
        User usr = new User();
        usr.setName(name);
        usr.setEmail(email);
    }
}
