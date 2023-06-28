package com.springsmallads.service;

import com.springsmallads.entity.AppUser;
import com.springsmallads.exception.*;
import com.springsmallads.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private LoginService loginService;

    public boolean signUp(String firstName, String lastName, String email, String password) throws UserExistsException {
        try {
            appUserRepository.findByEmail(email);
            throw new UserExistsException();
        }
        catch (Exception ex) {
            AppUser user = AppUser.builder().firstName(firstName).lastName(lastName).email(email).password(password).build();
            appUserRepository.save(user);
            return user.getId() > 0;
        }
    }

    public boolean signIn(String email, String password) throws UserExistsNotException {
        try {
            AppUser user = appUserRepository.findByEmailAndPassword(email, password);
            if(user.isActive()) {
                return loginService.login(user);
            }
            throw new UserNotActiveException();
        }catch (Exception ex) {
            throw new UserExistsNotException();
        }
    }

    public List<AppUser> getUsers() throws NotSignInException, NotAdminException {
        if(loginService.isLogged()) {
            if(loginService.isAdmin()) {
                return (List<AppUser>)appUserRepository.findAll();
            }
            throw new NotAdminException();
        }
        throw new NotSignInException();
    }

    public boolean deActivateUser(int userId) throws NotAdminException, NotSignInException, UserExistsNotException {
        if(loginService.isLogged()) {
            if(loginService.isAdmin()) {
                try {
                    AppUser user = appUserRepository.findById(userId).get();
                    user.setActive(false);
                    appUserRepository.save(user);
                    return true;
                }catch (Exception ex) {
                    throw new UserExistsNotException();
                }
            }
            throw new NotAdminException();
        }
        throw new NotSignInException();
    }

}
