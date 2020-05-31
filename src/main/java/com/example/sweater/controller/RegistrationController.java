package com.example.sweater.controller;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("registration")
    public String registration(){
        return "registration";
    }

//    маппинг username и password сразу в user-а (как в уроке) не произошёл - поэтому реализовано с помощью проброса обычных текстовых полей
//
//    @PostMapping("/registration")
//    public String addUser(
//            @RequestParam User user,
//            Map<String, Object> model
//    ){
//
//        User userFromDB = userRepository.findByUsername(user.getUsername());
//        if (userFromDB != null){
//            model.put("message", String.format("User %s already exist!", user.getUsername()));
//            return "registration";
//        }
//
//        user.setActive(true);
//        user.setRoles(Collections.singleton(Role.USER));
//        userRepository.save(user);
//
//        return "redirect:/login";
//
//    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam @NotNull String username,
            @RequestParam @NotNull String password,
//            @RequestParam User user,
            Map<String, Object> model
    ){

        User userFromDB = userRepository.findByUsername(username);
        if (userFromDB != null){
            model.put("message", String.format("User %s already exist!", username));
            return "registration";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);

        return "redirect:/login";

    }

}
