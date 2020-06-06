package com.example.sweater.controller;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repo.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")      // проверяет перед выполнением каждого метода наличие у пользователя прав (работает только вкупе с аннотацией на конфиге авторизации)
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "userList";
    }

    @GetMapping("{user}")
//    public String userEditForm(@RequestParam(required = true) String userId,
//    public String userEditForm(@PathVariable Long userId,         // можно смеппить переданный параметр, но спринг умнее и может сразу найти пользователя по id
    public String userEditForm(@PathVariable User user,
                               Model model){
        model.addAttribute("user", user);
        model.addAttribute("allRoles", Role.values());
        return "userEdit";
    }

    @PostMapping
    public String userSave(
            @RequestParam String newUsername,
            @RequestParam Map<String, String> form,     // потому что передаваться сюда будут только отмеченные параметры и каждый раз разное количество (?) + прочие параметры
            @RequestParam("userId") User user){

        user.setUsername(newUsername);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key))
                user.getRoles().add(Role.valueOf(key));
        }

        userRepository.save(user);      // нужен именно здесь. роли сохраняются вместе с пользователем

        return "redirect:/user";

    }

}
