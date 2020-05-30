package com.example.sweater;

import com.example.sweater.domain.Message;
import com.example.sweater.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class GreetingController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping()           // если не указывать, то по умолчанию '/'
    public String main(Map<String, Object> model) {
        model.put("messages", messageRepository.findAll());
        return "main";
    }

    @GetMapping("greeting")
    public String greeting(
            @RequestParam(name="name", required=false, defaultValue="World") String name,
            Map<String, Object> model
    ) {
        model.put("name", name);
        return "greeting";
    }

//    @RequestParam - выдергивает get/post параметры запроса
    @PostMapping
    public String addMessage(@RequestParam String text,
                             @RequestParam String tag,
                             Map<String, Object> model){

        Message newMessage = new Message();
        newMessage.setText(text);
        newMessage.setTag(tag);
        messageRepository.save(newMessage);

        return "redirect:";

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
