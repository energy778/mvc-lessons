package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("main")           // если не указывать, то по умолчанию '/'
    public String main(Map<String, Object> model) {
        model.put("messages", messageRepository.findAll());
        return "main";
    }

    @GetMapping()                // аналог: @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

//    @RequestParam - выдергивает get/post параметры запроса
    @PostMapping("main")
    public String addMessage(@RequestParam String text,
                             @RequestParam String tag,
                             Map<String, Object> model){

        Message newMessage = new Message();
        newMessage.setText(text);
        newMessage.setTag(tag);
        messageRepository.save(newMessage);

        return "redirect:/main";

    }

    //    @RequestParam - выдергивает get или post параметры запроса
    @PostMapping(value = "filter")
    public String filter(@RequestParam String filter,
                             Map<String, Object> model){

        if (filter != null && !filter.isEmpty())
            model.put("messages", messageRepository.findAllByTag(filter));
        else
            model.put("messages", messageRepository.findAll());

        return "main";

    }

}
