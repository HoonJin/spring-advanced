package com.example.spring.thymeleaf.basic;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping("/basic")
public class BasicController {

    @GetMapping("/text-basic")
    public String textBasic(Model model) {
        model.addAttribute("data", "HELLO SPRING!!");
        return "basic/text-basic";
    }

    @GetMapping("/text-unescaped")
    public String textUnescaped(Model model) {
        model.addAttribute("data", "HELLO <b>SPRING!</b>!!");
        return "basic/text-unescaped";
    }

    @GetMapping("/variable")
    public String variable(Model model) {
        User userA = new User("ABC", 10L);
        User userB = new User("CBA", 20L);

        ArrayList<User> users = new ArrayList<>();
        users.add(userA);
        users.add(userB);

        HashMap<String, User> map = new HashMap<>();
        map.put("userA", userA);
        map.put("userB", userB);

        model.addAttribute("user", userA);
        model.addAttribute("users", users);
        model.addAttribute("userMap", map);
        return "/basic/variable";
    }

    @Data
    static class User {
        private final String username;
        private final Long age;
    }

    @GetMapping("basic-objects")
    public String basicObjects(HttpSession session) {
        session.setAttribute("sessionData", "hello session");
        return "/basic/basic-objects";
    }

    @Component("helloBean")
    static class HelloBean {
        public String hello(String data) {
            return "hello " + data;
        }
    }

    @GetMapping("date")
    public String date(Model model) {
        model.addAttribute("localDateTime", LocalDateTime.now());
        return "/basic/date";
    }

    @GetMapping("/link")
    public String link(Model model) {
        model.addAttribute("param1", "data1");
        model.addAttribute("param2", "data2");
        return "/basic/link";
    }

    @GetMapping("literal")
    public String literal(Model model) {
        model.addAttribute("data", "Spring");
        return "/basic/literal";
    }

}
