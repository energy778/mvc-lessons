package com.example.sweater.controller;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.getAll());
        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
//    public String userEditForm(@RequestParam(required = true) String userId,
//    public String userEditForm(@PathVariable Long userId,         // можно смеппить переданный параметр, но спринг умнее и может сразу найти пользователя по id
    public String userEditForm(@PathVariable User user,
                               Model model){
        model.addAttribute("user", user);
        model.addAttribute("allRoles", Role.values());
        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(
            @RequestParam String newUsername,
            @RequestParam Map<String, String> form,     // потому что передаваться сюда будут только отмеченные параметры и каждый раз разное количество (?) + прочие параметры
            @RequestParam("userId") User user){

        userService.saveUser(user, newUsername, form);
        return "redirect:/user";

    }

    @GetMapping("/profile")
    public String getProfile(Model model,
                             @AuthenticationPrincipal User user) {

        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());
        return "profile";

    }

    @PostMapping("/profile")
    public String updateProfile(Model model,
                                @AuthenticationPrincipal User user,
                                @RequestParam String password,
                                @RequestParam String email) {

        userService.updateProfile(user, password, email);

        return "redirect:/user/profile";

    }

}
