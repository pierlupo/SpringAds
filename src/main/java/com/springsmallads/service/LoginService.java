package com.springsmallads.service;

import com.springsmallads.entity.AppUser;

public interface LoginService {

    public boolean login(AppUser user);

    public boolean isLogged();

    public boolean isAdmin();

    public int getUserId();

}
