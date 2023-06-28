package com.springsmallads.controller;

import com.springsmallads.exception.UserExistsException;
import com.springsmallads.exception.UserExistsNotException;
import com.springsmallads.service.AppUserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private HttpServletResponse response;

    @ExceptionHandler(UserExistsNotException.class)
    public ModelAndView handleUserDoesNotExist(UserExistsNotException ex) {
        ModelAndView mv = new ModelAndView("signin");
        mv.addObject("message", ex.getMessage());
        return mv;
    }

    @ExceptionHandler(UserExistsException.class)
    public ModelAndView handleUserExists(UserExistsException ex) {
        ModelAndView mv = new ModelAndView("signup");
        mv.addObject("message", ex.getMessage());
        return mv;
    }

    @GetMapping("signin")
    public ModelAndView signIn(){
    ModelAndView mv = new ModelAndView("signin");
    return mv;
    }

    @PostMapping("signin")
    public String signUp(@RequestParam String email, @RequestParam String password) throws UserExistsNotException, IOException {
        if(appUserService.signIn(email, password)) {
            return "redirect:/";
        }
        return null;
    }

    @GetMapping("signup")
    public ModelAndView postSignIn() {
        ModelAndView mv = new ModelAndView("signup");
        return mv;
    }

    @PostMapping("signup")
    public String postSignUp(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String password) throws IOException, UserExistsException {
        if(appUserService.signUp(firstName, lastName, email, password)) {
            return "redirect:/user/signin";
        }
        return null;
    }

}
