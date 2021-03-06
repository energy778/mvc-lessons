package com.example.sweater.service;

import com.example.sweater.domain.Role;
import com.example.sweater.domain.User;
import com.example.sweater.repo.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service("userService")
public class UserService implements UserDetailsService {

    @Value("${app.mail.prefix}")
    private String mailPathPrefix;

    private final UserRepository userRepository;
    private final MailSender mailSender;

    public UserService(UserRepository userRepository, MailSender mailSender) {
        this.userRepository = userRepository;
        this.mailSender = mailSender;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public boolean addUser(User user){

        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null){
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);

        if (!StringUtils.isEmpty(user.getEmail()))
            sendMessage(user);

        return true;

    }

    public boolean activateUser(String code) {

        User user = userRepository.findByActivationCode(code);

        if (user == null)
            return false;

        user.setActivationCode(null);       // будет означать, что пользователь подтвердил свой почтовый ящик
        userRepository.save(user);

        return true;

    }

    public Iterable<User> getAll() {
        return userRepository.findAll();
    }

    public void saveUser(User user, String newUsername, Map<String, String> form) {

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

    }

    public void updateProfile(User user, String password, String email) {

        boolean isEmailChanged = !Objects.equals(user.getEmail(), email);

        if (isEmailChanged){
            user.setEmail(email);
            if (!StringUtils.isEmpty(email))
                user.setActivationCode(UUID.randomUUID().toString());
        }

        if (!StringUtils.isEmpty(password))
            user.setPassword(password);

        userRepository.save(user);

        if (isEmailChanged)
            sendMessage(user);

    }

    private void sendMessage(User user) {
        String message = String.format(
                "Привет, %s " +
                        "\nДобро пожаловать в Switter. " +
                        "\nДля подтверждения регистрации перейдите по следующей ссылке: %s/activate/%s",
                user.getUsername(), mailPathPrefix, user.getActivationCode()
        );
        mailSender.send(user.getEmail(), "Activation code", message);
    }

}
