package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {

    private final MessageRepository messageRepository;

    public MainController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/main")           // если не указывать, то по умолчанию '/'
    public String main(@RequestParam(required = false, defaultValue = "") String filter,
                       Model model) {

        model.addAttribute("filter", filter);

        if (filter != null && !filter.isEmpty())
            model.addAttribute("messages", messageRepository.findAllByTag(filter));
        else
            model.addAttribute("messages", messageRepository.findAll());

        return "main";

    }

    @GetMapping()                // аналог: @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

//    @RequestParam - выдергивает get/post параметры запроса
    @PostMapping("/main")
    public String addMessage(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag,
            Map<String, Object> model){

        Message newMessage = new Message();
        newMessage.setText(text);
        newMessage.setTag(tag);
        newMessage.setAuthor(user);
        messageRepository.save(newMessage);

        return "redirect:/main";

    }

}
