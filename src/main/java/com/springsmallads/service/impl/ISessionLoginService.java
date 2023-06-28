package com.springsmallads.service.impl;

import com.springsmallads.entity.AppUser;
import com.springsmallads.service.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ISessionLoginService implements LoginService {

    @Autowired
    HttpSession httpSession;

    @Override
    public boolean login(AppUser user) {
        httpSession.setAttribute("isLogged", true);
        httpSession.setAttribute("fullName", user.getFirstName() + " "+user.getLastName());
        httpSession.setAttribute("isAdmin", user.isAdmin());
        httpSession.setAttribute("userId", user.getId());
        return true;
    }

    @Override
    public boolean isLogged() {
        return httpSession.getAttribute("isLogged") != null && (boolean)httpSession.getAttribute("isLogged") == true;
    }

    @Override
    public boolean isAdmin() {
        return httpSession.getAttribute("isLogged") != null && (boolean)httpSession.getAttribute("isAdmin") == true;
    }

    @Override
    public int getUserId() {
        return (int)httpSession.getAttribute("userId");
    }
}
